package com.eurelis.tools.xml.transformation;

import org.dom4j.Document;

/**
 * The Interface DocumentSource.
 */
public interface DocumentSource {

	/**
	 * Returns the dom4j xml document associated with this source
	 *
	 * @return the dom4j xml document
	 */
	public Document document();

	/**
	 * Ignore initial validation.
	 *
	 * @return true if the first validation pass should be ignored for this document
	 */
	public boolean ignoreInitialValidation();

	/**
	 * Returns a documentIdentifier to be used for the journal
	 *
	 * @return a unique identifier for this document
	 */
	public String documentIdentifier();

	/**
	 * Sets the new document. Method to be used to save the result of the transformation
	 *
	 * @param newDoc the processed value of the document
	 */
	public void setNewDocument(Document newDoc);
}