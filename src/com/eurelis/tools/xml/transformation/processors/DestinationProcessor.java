package com.eurelis.tools.xml.transformation.processors;

import org.dom4j.Document;
import org.dom4j.Node;

public interface DestinationProcessor {

	public Node processXPathDestination(Document target, Node source, String dst);

	public Node processSXPathDestination(Document target, Node source, String dst);
}