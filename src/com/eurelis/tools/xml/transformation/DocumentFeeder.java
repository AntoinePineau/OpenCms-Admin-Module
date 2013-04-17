package com.eurelis.tools.xml.transformation;


/**
 * The Interface DocumentFeeder.
 */
public interface DocumentFeeder extends org.xml.sax.EntityResolver {

	/**
	 * Returns true if the feeder has more document sources.
	 *
	 * @return true if the feeder has more document sources
	 */
	public boolean hasNext();

	/**
	 * Returns the next document sources for this feeder.
	 *
	 * @return the next document sources for this feeder.
	 */
	public DocumentSource nextDocumentSource();
	
	/**
	 * Reset entity resolver. Method called after each document validation in ordre to reset schema validation mechanism
	 */
	public void resetEntityResolver();
	
}