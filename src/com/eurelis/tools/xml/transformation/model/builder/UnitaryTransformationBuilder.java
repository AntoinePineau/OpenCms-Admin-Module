package com.eurelis.tools.xml.transformation.model.builder;

import org.dom4j.XPath;

import com.eurelis.tools.xml.transformation.model.SXPathDestination;
import com.eurelis.tools.xml.transformation.model.TemplateTransformation;
import com.eurelis.tools.xml.transformation.model.UnitaryTransformation;
import com.eurelis.tools.xml.transformation.model.XPathDestination;

/**
 * Class used to instantiate UnitaryTransformation objects.
 */
public class UnitaryTransformationBuilder {
	
	/** The unitary transformation. */
	private UnitaryTransformation unitaryTransformation;
	
	/** The template transformation builder. */
	private TemplateTransformationBuilder templateTransformationBuilder;
	
	
	/**
	 * Creates the instance.
	 *
	 * @return the unitary transformation builder
	 */
	public static UnitaryTransformationBuilder createInstance() {
		return new UnitaryTransformationBuilder();
	}
	
	/**
	 * Instantiates a new unitary transformation builder.
	 */
	private UnitaryTransformationBuilder() {
		this.unitaryTransformation = new UnitaryTransformation();
	}
	
	/**
	 * Sets the XPath to select source(s) node(s).
	 *
	 * @param source the xpath
	 * @return this unitary transformation builder instance
	 */
	public UnitaryTransformationBuilder setSource(XPath source) {
		unitaryTransformation.setSource(source);
		return this;
	}

	/**
	 * Sets a template transformation.
	 *
	 * @param templateTransformation the template transformation
	 * @return this unitary transformation builder instance
	 */
	public UnitaryTransformationBuilder setTemplateTransformation(TemplateTransformation templateTransformation) {
		this.unitaryTransformation.setTemplateTransformation(templateTransformation);
		return this;
	}

	/**
	 * Gets a template transformation builder.
	 *
	 * @return a new or the current template transformation builder
	 */
	public TemplateTransformationBuilder getTemplateTransformationBuilder() {
		if (templateTransformationBuilder == null) {
			templateTransformationBuilder = TemplateTransformationBuilder.createInstance();
			this.unitaryTransformation.setTemplateTransformation(templateTransformationBuilder.build());
		}
		
		return this.templateTransformationBuilder;
	}

	/**
	 * Sets the destination as a SXPath.
	 *
	 * @param destination the SXPath destination string representation
	 * @return this unitary transformation builder instance
	 */
	public UnitaryTransformationBuilder setSXPathDestination(String destination) {
		this.unitaryTransformation.setDestination(new SXPathDestination(destination));
		return this;
		
	}

	/**
	 * Sets the destination as a XPath
	 *
	 * @param destination the XPath destination string representation
	 * @return this unitary transformation builder instance
	 */
	public UnitaryTransformationBuilder setXPathDestination(String destination) {
		this.unitaryTransformation.setDestination(new XPathDestination(destination));
		return this;
	}
	
	
	/**
	 * Builds the unitary transformation object.
	 *
	 * @return a unitary transformation
	 */
	public UnitaryTransformation build() {
		return this.unitaryTransformation;
	}
}