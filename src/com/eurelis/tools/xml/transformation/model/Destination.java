package com.eurelis.tools.xml.transformation.model;

import org.dom4j.Document;
import org.dom4j.Node;

import com.eurelis.tools.xml.transformation.processors.DestinationProcessor;

/**
 * Abstract class to describe how to look for destination node.
 */
public abstract class Destination {
	
	/** The string representation of the destination, might be a XPath or a SXPath */
	protected String destination;

	/**
	 * Instantiates a new destination.
	 *
	 * @param dst the destination path
	 */
	public Destination(String dst) {
		this.destination = dst;
	}

	/**
	 * Be processed, callback method used by the processor to determine if the destination string should be processed has a XPath or a SXPath
	 *
	 * @param target the target document
	 * @param node the node we are processing the destination from
	 * @param processor the destination processor implementation
	 * @return the node found after having processed the destination
	 */
	public abstract Node beProcessed(Document target, Node node, DestinationProcessor processor);


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "destination " + destination;
	}

}