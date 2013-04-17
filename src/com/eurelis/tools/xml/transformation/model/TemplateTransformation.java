package com.eurelis.tools.xml.transformation.model;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * Class used to describe a template transformation.
 */
public class TemplateTransformation {
	
	/** The template. */
	private String template;

	/** The parameters. */
	public List<TemplateParameter> parameters = new ArrayList<TemplateParameter>();
	
	
	/**
	 * Instantiates a new template transformation.
	 *
	 * @param template the template
	 * @throws DocumentException the document exception
	 */
	public TemplateTransformation(String template) throws DocumentException {
		this.template = template;
		
		DocumentHelper.parseText(template);
	}
	
	/**
	 * Instantiates a new template transformation.
	 */
	public TemplateTransformation() {
		
	}
	
	/**
	 * Adds a template parameter.
	 *
	 * @param parameter the parameter
	 */
	public void addParameter(TemplateParameter parameter) {
		parameters.add(parameter);
	}
	
	/**
	 * Gets the template as a string.
	 *
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}
	
	/**
	 * Sets the template.
	 *
	 * @param template the new template
	 * @throws DocumentException a document exception is thrown if the template can't be parsed to a valid xml dom4j document
	 */
	public void setTemplate(String template) throws DocumentException {
		this.template = template;
		
		DocumentHelper.parseText(template);
	}
}