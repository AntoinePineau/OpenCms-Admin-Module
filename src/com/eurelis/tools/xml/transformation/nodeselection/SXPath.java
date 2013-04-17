package com.eurelis.tools.xml.transformation.nodeselection;

import java.util.List;
import java.util.ArrayList;

/**
 * The Class SXPath.
 */
public class SXPath {
	
	/** The SXPathNodes : the successive indications to tell how to select or create a node from a current one. */
	private List<SXPathNode> nodes;
	
	/** The last element : empty, a node name or an attribute */
	private String lastElement = null;
	
	
	/**
	 * Instantiates a new SXPath.
	 */
	public SXPath() {
		nodes = new ArrayList<SXPathNode>();
	}
	
	

	/**
	 * Adds the parent path node.
	 *
	 * @param node the node
	 */
	public void addParentPathNode(SXPathNode node) {
		nodes.add(node);
	}

	/**
	 * Adds a child SXPathNode.
	 *
	 * @param node the SXPathNode to add
	 */
	public void addChildPathNode(SXPathNode node) {
		nodes.add(0, node);
	}

	
	/**
	 * Gets the SXPathNodes.
	 *
	 * @return the SXPathNodes
	 */
	public List<SXPathNode> getNodes() {
		return this.nodes;
	}




	/**
	 * Gets the last element.
	 *
	 * @return the last element
	 */
	public String getLastElement() {
		return lastElement;
	}

	/**
	 * Sets the last element.
	 *
	 * @param lastElement the last element
	 */
	public void setLastElement(String lastElement) {
		this.lastElement = lastElement;
	}
	
	
}