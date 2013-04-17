package com.eurelis.tools.xml.transformation.model.builder;

import com.eurelis.tools.xml.transformation.model.XMLTransformation;

// TODO: Auto-generated Javadoc
/**
 * Class used to instantiate ContentTransformation objects.
 */
public class XMLTransformationBuilder {
	
	/** The xml transformation. */
	private XMLTransformation xmlTransformation;

	/**
	 * Instantiates a new XML transformation builder.
	 */
	private XMLTransformationBuilder() {
		this.xmlTransformation = new XMLTransformation();
	}
	
	/**
	 * Creates and returns a new XMLTransformationBuilder instance
	 *
	 * @return a new XML transformation builder
	 */
	public static XMLTransformationBuilder newInstance() {
		return new XMLTransformationBuilder();
	}

	
	/**
	 * Creates and returns a new unitary transformation builder to add a unitary transformation to the XMLTransformation being build
	 *
	 * @return an unitary transformation builder
	 */
	public UnitaryTransformationBuilder newUnitaryTransformationBuilder() {
		UnitaryTransformationBuilder utb = UnitaryTransformationBuilder.createInstance();
		xmlTransformation.addUnitaryTransformation(utb.build());
		return utb;
	}

	/**
	 * Builds the XMLTransformation
	 *
	 * @return the XML transformation
	 */
	public XMLTransformation build() {
		return xmlTransformation;
	}
}