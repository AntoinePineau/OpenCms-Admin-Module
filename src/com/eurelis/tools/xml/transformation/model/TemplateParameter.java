package com.eurelis.tools.xml.transformation.model;

import org.dom4j.XPath;

// TODO: Auto-generated Javadoc
/**
 * Class used to describe parameters used by the template transformation mechanism.
 */
public class TemplateParameter {
	
	/** The name of the parameter. */
	private String name;
	
	/** The xpath used to select the value for this parameter. */
	private XPath xpath;

	/**
	 * Instantiates a new template parameter.
	 *
	 * @param name the name
	 * @param xpath the xpath
	 */
	public TemplateParameter(String name, XPath xpath) {
		this.name = name;
		this.xpath = xpath;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the xpath.
	 *
	 * @return the xpath where to find the value
	 */
	public XPath getXpath() {
		return xpath;
	}
	
}