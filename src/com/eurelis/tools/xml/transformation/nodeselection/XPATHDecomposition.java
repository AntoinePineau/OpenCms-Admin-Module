package com.eurelis.tools.xml.transformation.nodeselection;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.XPath;

/**
 * This Class is used to represent a decomposed XPATH with :
 * - it's first element used to start selecting nodes
 * - a list of XPath elements used to select the next level of nodes
 * - a last element : an attribute, nothing or the name of a node.
 */
public class XPATHDecomposition {
	
	/** The XPath to select the first element. */
	private XPath firstElement;
	
	/** The different XPath used to select the next node starting from the nextElement. */
	private List<XPath> elements;
	
	/** The last element : the node or attribute to be selected at the end. */
	private String lastElement;
	
	/**
	 * Instantiates a new xPATH decomposition.
	 */
	public XPATHDecomposition() {
		this.elements = new ArrayList<XPath>();
	}
	

	/**
	 * Gets the XPath to select the first element.
	 *
	 * @return the first element XPath selector
	 */
	public XPath getFirstElement() {
		return this.firstElement;
	}
	
	/**
	 * Sets the first element XPath selector
	 *
	 * @param firstElement the new first element XPath selector
	 */
	public void setFirstElement(XPath firstElement) {
		this.firstElement = firstElement;
	}

	/**
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	public List<XPath> getElements() {
		return this.elements;
	}
	
	/**
	 * Adds a XPath to select a node level
	 *
	 * @param xpathElement the xpath element
	 */
	public void addXPathElement(XPath xpathElement) {
		this.elements.add(xpathElement);
	}

	/**
	 * Gets the last element.
	 *
	 * @return the last element
	 */
	public String getLastElement() {
		return this.lastElement;
	}
	
	/**
	 * Sets the last element.
	 *
	 * @param lastElement the new last element
	 */
	public void setLastElement(String lastElement) {
		this.lastElement = lastElement;
	}
}