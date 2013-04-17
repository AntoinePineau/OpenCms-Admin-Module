package com.eurelis.tools.xml.transformation.nodeselection;

import org.dom4j.Attribute;

/**
 * The Class SXPathNodeAttribute.
 */
public class SXPathNodeAttribute {
	
	/** The name of the attribute. */
	private String name;
	
	/** The value of the attribute. */
	private String value;

	/**
	 * Instantiates a new SXPathNodeAttribute.
	 *
	 * @param name the name of the attribute
	 * @param value the value of the attribute
	 */
	public SXPathNodeAttribute(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Method to compare this SXPathNodeAttribute against a dom4j attribute
	 *
	 * @param attribute the attribute to test against
	 * @return true, if the attribute has the same name and value than this SXPathNodeAttribute instance
	 */
	public boolean equals(Attribute attribute) {
		boolean isEquivalent = false;
		
		if (attribute.getName().equals(name)) {
			if (attribute.getValue().equals(value)) {
				isEquivalent = true;
			}	
		}
		
		return isEquivalent;
	}

	/**
	 * Gets the name of this SXPathNodeAttribute
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the value of this SXPathNodeAttribute
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}