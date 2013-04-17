package com.eurelis.tools.xml.transformation.nodeselection;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class SXPathNode.
 */
public class SXPathNode {
	
	/** The name of this SXPathNode. */
	private String name;
	
	/** The attributes. */
	private List<SXPathNodeAttribute> attributes;

	/**
	 * Instantiates a new SXPathNode.
	 *
	 * @param name the node name for this SXPathNode
	 */
	public SXPathNode(String name) {
		this.name = name;
		this.attributes = new ArrayList<SXPathNodeAttribute>();
	}

	/**
	 * Method to compare this SXPathNode against a dom4j element
	 *
	 * @param xmlNode the Element to test against
	 * @return true, if this SXPathNode instance and the Element node have the same name and if the SXPathAttributes associated to this SXPathNode have equivalents in the tested Element 
	 */
	public boolean equals(Element xmlNode) {
		boolean isEquivalent = true;
		
		if (this.name.equals(xmlNode.getName())) {
		
			for (SXPathNodeAttribute attribute : attributes) {
				String eValue = xmlNode.attributeValue(attribute.getName());
				if (eValue == null || !eValue.equals(attribute.getValue())) {
					isEquivalent = false;
				}
			}
			
		}
		else {
			isEquivalent = false;
		}
		
		return isEquivalent;
	}

	/**
	 * Adds a SXPathNodeAttribute.
	 *
	 * @param name the name of the SXPathNodeAttribute
	 * @param value the value of the SXPathNodeAttribute
	 */
	public void addAttribute(String name, String value) {
		this.attributes.add(new SXPathNodeAttribute(name, value));
	}

	/**
	 * Gets the name of the node represented by this SXPathNode.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public List<SXPathNodeAttribute> getAttributes() {
		return attributes;
	}
	
}