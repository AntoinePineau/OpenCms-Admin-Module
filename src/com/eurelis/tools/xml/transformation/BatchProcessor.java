package com.eurelis.tools.xml.transformation;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;

import com.eurelis.tools.xml.transformation.model.XMLTransformation;
import com.eurelis.tools.xml.transformation.processors.XMLTransformationProcessor;


/**
 * The Class BatchProcessor.
 */
public class BatchProcessor {
	
	/** The document feeder that will provide the xml documents to process */
	private DocumentFeeder documentFeeder;
	
	/** The description of the transformations we want to apply to xml documents */
	private XMLTransformation transformation;
	
	
	
	/**
	 * Instantiates a new batch processor.
	 *
	 * @param feeder the document feeder
	 * @param transformation the description of the transformations we want to apply
	 */
	public BatchProcessor(DocumentFeeder feeder, XMLTransformation transformation) {
		this.documentFeeder = feeder;
		this.transformation = transformation;
	}
	
	
	/**
	 * The run method is used to process the transformation on every xml document provided by the feeder
	 *
	 * @param testMode if true xml documents won't be saved after the processing
	 * @return a list of journal objects describing what appened during each processing
	 */
	public List<Journal> run(boolean testMode) {
		List<Journal> journalList = new ArrayList<Journal>();
		
		while(documentFeeder.hasNext()) {
			DocumentSource docSource = documentFeeder.nextDocumentSource();
			String documentID = docSource.documentIdentifier();
			Journal journal = new Journal(documentID);
			journalList.add(journal);
			
			boolean ignoreInitialCheck = docSource.ignoreInitialValidation();
			
			Document dom4jDocument = docSource.document();
			
			XMLTransformationProcessor xmlTransformationProcessor = new XMLTransformationProcessor(journal, transformation);
			
			boolean initialCheck = true;
			
			if (!ignoreInitialCheck) {
				documentFeeder.resetEntityResolver();
				initialCheck = !xmlTransformationProcessor.validateDocument(dom4jDocument, documentFeeder, true);
			
				if (!initialCheck) {
					journal.error(xmlTransformationProcessor, "le document est deja valide");
					
				}
				
			}
			
			
			Document resultDoc = xmlTransformationProcessor.processTransformation(dom4jDocument);
			
			
			boolean secondCheck = false;
			
			documentFeeder.resetEntityResolver();
			secondCheck = xmlTransformationProcessor.validateDocument(resultDoc, documentFeeder, false);
			
			if (!secondCheck) {
				journal.error(xmlTransformationProcessor, "le document apres transformation n'est pas valide");

			}
			
			else 
				journal.info(xmlTransformationProcessor, "le document est valide !");
				
				
				if (!testMode) {
					docSource.setNewDocument(resultDoc);
				}
			
			}
		
		
		
		return journalList;
	}
	
	
	
}