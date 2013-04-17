package com.eurelis.tools.xml.transformation.model;

import org.dom4j.Document;
import org.dom4j.Node;

import com.eurelis.tools.xml.transformation.processors.DestinationProcessor;

/**
 * Class to describe how to create a destination node by using a simplified xpath.
 */
public class SXPathDestination extends Destination {

	/* (non-Javadoc)
	 * @see com.eurelis.tools.xml.transformation.model.Destination#beProcessed(org.dom4j.Document, org.dom4j.Node, com.eurelis.tools.xml.transformation.processors.DestinationProcessor)
	 */
	public Node beProcessed(Document target, Node node, DestinationProcessor processor) {
		return processor.processSXPathDestination(target, node, destination);
	}

	/**
	 * Instantiates a new Simplified XPath destination.
	 *
	 * @param dst the destination
	 */
	public SXPathDestination(String dst) {
		super(dst);
	}
}