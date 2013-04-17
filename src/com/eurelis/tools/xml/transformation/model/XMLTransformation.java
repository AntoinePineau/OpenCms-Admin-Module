package com.eurelis.tools.xml.transformation.model;

import java.util.ArrayList;
import java.util.List;


/**
 * This class describe unitary transformations to apply to xml documents.
 */
public class XMLTransformation {
	
	
	/** The unitary transformations. */
	private List<UnitaryTransformation> transformations = new ArrayList<UnitaryTransformation>();

	
	/**
	 * Instantiates a new XML transformation.
	 */
	public XMLTransformation() {

	}
	
	/**
	 * Adds a unitary transformation.
	 *
	 * @param unitaryTransformation the unitary transformation
	 */
	public void addUnitaryTransformation(UnitaryTransformation unitaryTransformation) {
		transformations.add(unitaryTransformation);
	}
	
	

	/**
	 * Gets the transformations.
	 *
	 * @return the transformations
	 */
	public List<UnitaryTransformation> getTransformations() {
		return transformations;
	}
}