package com.eurelis.tools.xml.transformation.processors;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.eurelis.tools.xml.transformation.DocumentFeeder;
import com.eurelis.tools.xml.transformation.Journal;
import com.eurelis.tools.xml.transformation.model.UnitaryTransformation;
import com.eurelis.tools.xml.transformation.model.XMLTransformation;

/**
 * Processor class for xml document level.
 */
public class XMLTransformationProcessor extends Processor {

	/** The xml transformation. */
	XMLTransformation xmlTransformation = null;
	
	/**
	 * Instantiates a new XML transformation processor.
	 *
	 * @param journal the journal where to put events
	 * @param xmlTransformation the description of the xmlTransformation to apply
	 */
	public XMLTransformationProcessor(Journal journal, XMLTransformation xmlTransformation) {
		super(journal, null);
		this.xmlTransformation = xmlTransformation;
	}

	/**
	 * Process transformation on a document.
	 *
	 * @param source the source document
	 * @return the document created after the transformation
	 */
	public Document processTransformation(Document source) {
		
		Document destinationDocument = this.createDestinationDocument(source);
		journal.info(this, "processing did start on document");		
		
		for (UnitaryTransformation ut : this.xmlTransformation.getTransformations()) {
			
			UnitaryTransformationProcessor utp = new UnitaryTransformationProcessor(journal, this, ut);
			
			utp.processTransformation(source, destinationDocument);
			
		}
		journal.info(this, "processing did end on document");
		
		return destinationDocument;
	}

	/**
	 * Retrieve the source node list.
	 *
	 * @param source the source document
	 * @return the list of all nodes that will serve as source node for unitary transformations
	 */
	public List<Node> retrieveSourceNodeList(Document source) {
		
		List<Node> elementList = new ArrayList<Node>();
		
		for (UnitaryTransformation ut : this.xmlTransformation.getTransformations()) {
			XPath sourceXpath = ut.getSource();
			
			if (sourceXpath != null) {
				@SuppressWarnings("unchecked")
				List<Node> nodeList = sourceXpath.selectNodes(source);
				elementList.addAll(nodeList);
			}
		}
		
		return elementList;
	}

	/**
	 * Creates the destination document.
	 *
	 * @param source the source document
	 * @return a new document created by removing every source node from a clone of the source document
	 */
	public Document createDestinationDocument(Document source) {
		Document dstDoc = (Document)source.clone();
		
		List<Node> nodesToDetach = retrieveSourceNodeList(dstDoc);
		
		for (Node n : nodesToDetach) {
			n.detach();
		}
		
		
		return dstDoc;
	}

	/**
	 * Validate document.
	 *
	 * @param source the document to validate
	 * @param documentFeeder the document feeder, to serve as an EntityResolver
	 * @param initialPass if true validations errors won't be added to the journal
	 * @return true, if the validation succeeded, false otherwise
	 */
	public boolean validateDocument(Document source, DocumentFeeder documentFeeder, boolean initialPass) {
		boolean documentIsValid = false;
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		
		SAXParser parser;
		SAXReader reader;
		
		try {
			parser = factory.newSAXParser();
			
			
			reader = new SAXReader(parser.getXMLReader());
			
			JournalErrorHandler journalErrorHandler = new JournalErrorHandler(journal, this, initialPass);
			
			reader.setFeature("http://xml.org/sax/features/validation", true);
			reader.setFeature("http://apache.org/xml/features/validation/schema", true);
			
			reader.setErrorHandler(journalErrorHandler);
			reader.setValidation(true);
			reader.setIncludeInternalDTDDeclarations(false);
			reader.setEntityResolver(documentFeeder);
			Reader xmlStringReader = new StringReader(source.asXML());
			reader.read(xmlStringReader);
			
			documentFeeder.resetEntityResolver();
			
			documentIsValid = !journalErrorHandler.hasErrorBeenEncountered();
		
		} catch (ParserConfigurationException e) {
			journal.error(this, e.getMessage());
		} catch (SAXException e) {
			journal.error(this, e.getMessage());
		} catch (DocumentException e) {
			journal.error(this, e.getMessage());
		}
		
		
		return documentIsValid;
	}

	/* (non-Javadoc)
	 * @see com.eurelis.tools.xml.transformation.processors.Processor#getName()
	 */
	public String getName() {
		return "XMLTransformation";
	}
}


/**
 * Nested class to write validation errors in a journal
 */
class JournalErrorHandler implements ErrorHandler {
	
	/**
	 * The journal where to write on
	 */
	private Journal journal = null;
	
	/**
	 * Has an error been encountered yet
	 */
	private boolean errorEncountered = false;
	
	/**
	 * The xmlTranformationProcessor in which this instance is used
	 */
	private XMLTransformationProcessor processor = null;
	
	/**
	 * should we disable journal writing
	 */
	private boolean initialPass;
	
	/**
	 * 
	 * Instantiate a JournalErrorHandler
	 * 
	 * @param journal the journal where to write on
	 * @param processor the xmlTransformationProcessor in which this instance is used
	 * @param initialPass true to disable journal writing
	 */
	public JournalErrorHandler(Journal journal, XMLTransformationProcessor processor, boolean initialPass) {
		this.journal = journal;
		this.processor = processor;
		this.initialPass = initialPass;
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#error()
	 */
	public void error(SAXParseException exception) throws SAXException {
		if (!initialPass) {
			journal.error(processor, exception.getMessage());
		}
		errorEncountered = true;
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#fatalError()
	 */
	public void fatalError(SAXParseException exception) throws SAXException {
		if (!initialPass) {
			journal.error(processor, exception.getMessage());
		}
		errorEncountered = true;
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#warning()
	 */
	public void warning(SAXParseException exception) throws SAXException {
		if (!initialPass) {
			journal.warning(processor, exception.getMessage());
		}
	}
	
	/**
	 * Getter to know if an error has been encountered yet
	 * @return true if an error has already been encountered
	 */
	public boolean hasErrorBeenEncountered() {
		return errorEncountered;
	}
	
	
	
}