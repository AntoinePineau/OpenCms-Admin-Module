package com.eurelis.tools.xml.transformation.model;

import org.dom4j.Document;
import org.dom4j.Node;

import com.eurelis.tools.xml.transformation.processors.DestinationProcessor;

// TODO: Auto-generated Javadoc
/**
 * Class to describe how to look for destination node by selecting an existing node using xpath.
 */
public class XPathDestination extends Destination {

	/* (non-Javadoc)
	 * @see com.eurelis.tools.xml.transformation.model.Destination#beProcessed(org.dom4j.Document, org.dom4j.Node, com.eurelis.tools.xml.transformation.processors.DestinationProcessor)
	 */
	public Node beProcessed(Document target, Node node, DestinationProcessor processor) {
		return processor.processXPathDestination(target, node, destination);
	}

	/**
	 * Instantiates a new XPath destination.
	 *
	 * @param dst the destination
	 */
	public XPathDestination(String dst) {
		super(dst);
	}
}