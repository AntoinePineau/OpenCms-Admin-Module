package com.eurelis.tools.xml.transformation.nodeselection;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.xpath.DefaultXPath;

/**
 * The Class XPATHParser.
 */
public class XPATHParser {

	/** The singleton. */
	private static XPATHParser SINGLETON = null;
	
	/**
	 * Instantiates a new XPATH parser.
	 */
	private XPATHParser() {
		
	}
	
	/**
	 * Gets the single instance of XPATHParser.
	 *
	 * @return single instance of XPATHParser
	 */
	public static synchronized XPATHParser getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new XPATHParser();
		}
		return SINGLETON;
	}
	
	
	/**
	 * Parses the SXPath 
	 *
	 * @param path the string representation of the SXPath
	 * @return the constructed SXPath
	 */
	public SXPath parseFailOverPath(String path) {
		SXPath sxPath = new SXPath();
		path = path.trim();
		
		String lastElement = "";
		
		if (!path.isEmpty()) {
			List<String> decomposedNodeSections = decomposeNodeSections(path);
			
			if (!decomposedNodeSections.isEmpty()) {
				List<String> reducedNodeSections = null;
				
				if (path.charAt(path.length() - 1) == '/') {
					reducedNodeSections = decomposedNodeSections;
					sxPath.setLastElement("");
				}
				
				else {
					reducedNodeSections = decomposedNodeSections.subList(0, decomposedNodeSections.size() - 1);
					lastElement = decomposedNodeSections.get(decomposedNodeSections.size() - 1);
					sxPath.setLastElement(lastElement);
				}
				
			
				for (String nodeSection : reducedNodeSections) {
					sxPath.addParentPathNode(parseFailOverSection(nodeSection));
				}
			
			}
			
			
			
			
		}
		
		
		
		return sxPath;
	
	}
	
	/**
	 * Parses a SXPathNode from a section : a string representation of a XPath intended to select a direct sub node of the current one
	 *
	 * @param section the section
	 * @return the constructed SXPathNode
	 */
	protected SXPathNode parseFailOverSection(String section) throws IllegalArgumentException {
		
		String nodeName = section;
		
		int filterBeginIndex = section.lastIndexOf('[');
		int filterEndIndex = section.lastIndexOf(']');
		
		List<SXPathNodeAttribute> attributeList = null;
		
		if (filterBeginIndex != -1) {
			nodeName = nodeName.substring(0, filterBeginIndex);
		
			attributeList = parseFilterSection(section.substring(filterBeginIndex + 1, filterEndIndex));
		
		}
		
		
		SXPathNode sxPathNode = new SXPathNode(nodeName);
		
		if (attributeList != null) {
			for (SXPathNodeAttribute attribute : attributeList) {
				
				sxPathNode.addAttribute(attribute.getName(), attribute.getValue());
				
			}
			
		}
		
		return sxPathNode;
	}
	
	/**
	 * Parses the filter section : construct SXPathNodeAttributes from a AND aggregation of equality conditions on attributes.
	 *
	 * @param filterSection the filter section
	 * @return the list
	 */
	protected List<SXPathNodeAttribute> parseFilterSection(String filterSection) throws IllegalArgumentException {
		List<SXPathNodeAttribute> attributeList = new ArrayList<SXPathNodeAttribute>();
		
		String workString = filterSection.trim();
		boolean continueCondition = true;
		
		do {
			
			if (workString.charAt(0) == '@') {
				int firstSpaceIndex = workString.indexOf(' ');
				int firstEqualIndex = workString.indexOf('=');
				
				String attributeName = null;
				String attributeValue = null;
				
				if (firstSpaceIndex != -1 && firstEqualIndex != -1) {
					int endIndex = Math.min(firstSpaceIndex, firstEqualIndex);
					
					attributeName = workString.substring(1, endIndex);
					
					workString = workString.substring(firstEqualIndex + 1).trim();
					
				}
				else if (firstEqualIndex != -1) {
					attributeName = workString.substring(1, firstEqualIndex);
					
					workString = workString.substring(firstEqualIndex + 1); // pas besoin de faire de trim, il n'y a pas d'espace
					
				}
				else {
					continueCondition = false; // on a pas trouve de nom d'attribut
				}
				
				
				if (continueCondition) {
					
					if (workString.length() > 1) {
						char firstChar = workString.charAt(0);
					
						
						if (firstChar == '\'' || firstChar == '"') {
							char currentChar = workString.charAt(1);
							char previousChar = firstChar;
							int stringLength = workString.length();
							int workIndex = 1;
					
							while ((workIndex + 1) < stringLength && (currentChar != firstChar || previousChar == '\\')) {
								previousChar = currentChar;
								currentChar = workString.charAt(++workIndex);
							}
							
							
							if (currentChar == firstChar && previousChar != '\\') {
								// on est bien sorti grace a la detection de la fin
								attributeValue = workString.substring(1, workIndex);
								
								if ((workIndex + 1) < stringLength) {
									workString = workString.substring(workIndex + 1).trim();
									
									
									if (workString.length() > 3) {
										if (workString.substring(0, 3).equalsIgnoreCase("and")) {
											workString = workString.substring(3).trim();
										}
										
									}
									
									else {
										continueCondition = false;
									}
									
								}
								
								else {
									workString = "";
								}
 								
							}
							
						}
						
						
						
					}
					else {
						continueCondition = false;
					}
					
					
					
					
				}
				
				if (attributeName != null && !attributeName.isEmpty() && attributeValue != null && !attributeValue.isEmpty()) {
					attributeList.add(new SXPathNodeAttribute(attributeName, attributeValue));
				}
				else {
					continueCondition = false;
				}
				
				
				
			}
			else {
				continueCondition = false;
			}
			
			
			
			
			if (workString.isEmpty()) {
				continueCondition = false;
			}
			
		} while(continueCondition);
		
		
		if (!workString.isEmpty()) {
			throw new java.lang.IllegalArgumentException();
		}
		
		
		
		return attributeList;
	}
	
	
	

	/**
	 * Create an XPathDecomposition from a string xpath representation.
	 *
	 * @param xpath the xpath string representation
	 * @return the XPATH decomposition
	 */
	public XPATHDecomposition decomposeXpath(String xpath) {
		XPATHDecomposition decomposition = new XPATHDecomposition();
		xpath = xpath.trim();
		
		String lastElement = null;
		
		if (!xpath.isEmpty()) {
			List<String> decomposedNodeSections = decomposeNodeSections(xpath);
		
			if (!decomposedNodeSections.isEmpty()) {
				
				List<String> reducedNodeSections = null;
				
				String firstSection = decomposedNodeSections.get(0);
				int firstSectionIndex = xpath.indexOf(firstSection);
				
				String startPath = xpath.substring(0, firstSectionIndex + firstSection.length());
				
				if (decomposedNodeSections.size() == 1) {
					reducedNodeSections = new ArrayList<String>();
					
				}
				else {
					
					if (xpath.charAt(xpath.length() - 1) == '/') {
						lastElement = "";
						reducedNodeSections = decomposedNodeSections.subList(1, decomposedNodeSections.size());
					}
					else {
						reducedNodeSections = decomposedNodeSections.subList(1, decomposedNodeSections.size() - 1);
						lastElement = decomposedNodeSections.get(decomposedNodeSections.size() - 1);
					}
				}
				
				decomposition.setFirstElement(new DefaultXPath(startPath));
				decomposition.setLastElement(lastElement);
				
				for (String section : reducedNodeSections) {
					decomposition.addXPathElement(new DefaultXPath(section));
				}
				
			}
			
		}
		
		
		return decomposition;
	}
	
	
	
	/**
	 * Decompose a string XPath representation to a list of string each representing how to select a direct sub node from a current one
	 *
	 * @param fullPath the string XPath representation
	 * @return the list of xpath sections
	 */
	public List<String> decomposeNodeSections(String fullPath) {
		List<String> nodeSectionList = new ArrayList<String>();
		
		boolean continueCondition = true;
		
		do {
		
			int filterBeginIndex = fullPath.lastIndexOf('[');
			int filterEndIndex = fullPath.lastIndexOf(']');
			int nodeSectionIndex = fullPath.lastIndexOf('/');
		
			if (nodeSectionIndex == -1) {
				continueCondition = false;
			}
			else {
				
				if ((filterBeginIndex == -1 && filterEndIndex == -1) || !(nodeSectionIndex < filterEndIndex && nodeSectionIndex > filterBeginIndex)) {
					String nodeSection = fullPath.substring(nodeSectionIndex + 1);
					
					if (!nodeSection.isEmpty()) {
						nodeSectionList.add(0, nodeSection);
					}
					
					fullPath = fullPath.substring(0, nodeSectionIndex);
					
				}
				
				else if (nodeSectionIndex < filterEndIndex && nodeSectionIndex > filterBeginIndex){
					nodeSectionIndex = fullPath.lastIndexOf('/', filterBeginIndex);
					
					if (nodeSectionIndex != -1) {
						String nodeSection = fullPath.substring(nodeSectionIndex + 1);
						if (!nodeSection.isEmpty()) {
							nodeSectionList.add(0, nodeSection);
						}
						
						fullPath = fullPath.substring(0, nodeSectionIndex);
					}
					
					else {
						continueCondition = false;
					}
					
				}
				
				else {
					throw new java.lang.IllegalArgumentException();
					//continueCondition = false;
				}
				
			}
			
		
		
		} while(continueCondition);
		
		if (!fullPath.isEmpty()) {
			nodeSectionList.add(0, fullPath);
		}
		
		
		return nodeSectionList;	
	}
	
	
	
	
	
}