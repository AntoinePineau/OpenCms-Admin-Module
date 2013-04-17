package com.eurelis.tools.xml.transformation.model.builder;

import org.dom4j.DocumentException;
import org.dom4j.XPath;

import com.eurelis.tools.xml.transformation.model.TemplateParameter;
import com.eurelis.tools.xml.transformation.model.TemplateTransformation;

/**
 * Class used to instantiate TemplateTransformation objects.
 */
public class TemplateTransformationBuilder {
	
	/** The template transformation. */
	private TemplateTransformation templateTransformation;

	/**
	 * Instantiates a new template transformation builder.
	 */
	private TemplateTransformationBuilder() {
		templateTransformation = new TemplateTransformation();
	}
	
	/**
	 * Creates an instance.
	 *
	 * @return a template transformation builder
	 */
	public static TemplateTransformationBuilder createInstance() {
		return new TemplateTransformationBuilder();
	}

	/**
	 * Sets a template.
	 *
	 * @param template a template
	 * @return a template transformation builder
	 * @throws DocumentException a document exception if the template provided isn't a valid xml
	 */
	public TemplateTransformationBuilder setTemplate(String template) throws DocumentException {
		templateTransformation.setTemplate(template);
		return this;
	}

	/**
	 * Adds a template parameter.
	 *
	 * @param name the parameter name
	 * @param xpath the parameter xpath value
	 * @return this template transformation builder
	 */
	public TemplateTransformationBuilder addParameter(String name, XPath xpath) {
		templateTransformation.addParameter(new TemplateParameter(name, xpath));
		return this;
	}

	/**
	 * Builds the template transformation
	 *
	 * @return a template transformation
	 */
	public TemplateTransformation build() {
		return templateTransformation;
	}
}