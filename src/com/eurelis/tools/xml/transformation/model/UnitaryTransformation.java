package com.eurelis.tools.xml.transformation.model;

import org.dom4j.XPath;

/**
 * Class used to describe an unitary transformation, that is to say it describes how to:
 * - delete
 * - move
 * - create
 * - transform
 * 
 * nodes.
 */
public class UnitaryTransformation {
	
	/** Xpath to select source(s) node(s) for this unitary transformation. */
	private XPath source;
	
	/** An optional template transformation. If not used the transformation is either a deletion, a move or a create one */
	private TemplateTransformation templateTransformation;
	
	/** An optional destination. If not used the transformation is either a deletion or a template one*/
	private Destination destination;

	
	/**
	 * Instantiates a new unitary transformation.
	 */
	public UnitaryTransformation() {
		
	}
	
	
	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public XPath getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(XPath source) {
		this.source = source;
	}

	/**
	 * Gets the destination.
	 *
	 * @return the destination
	 */
	public Destination getDestination() {
		return destination;
	}

	/**
	 * Sets the destination.
	 *
	 * @param dst the new destination
	 */
	public void setDestination(Destination dst) {
		this.destination = dst;
	}

	/**
	 * Gets the template transformation.
	 *
	 * @return the template transformation
	 */
	public TemplateTransformation getTemplateTransformation() {
		return templateTransformation;
	}

	/**
	 * Sets the template transformation.
	 *
	 * @param templateTransformation the new template transformation
	 */
	public void setTemplateTransformation(TemplateTransformation templateTransformation) {
		this.templateTransformation = templateTransformation;
	}
}