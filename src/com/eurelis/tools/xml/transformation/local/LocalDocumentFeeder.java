/*
 * Copyright (c) Eurelis. All rights reserved. CONFIDENTIAL - Use is subject to license terms.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are not permitted without prior written permission of Eurelis.
 */

package com.eurelis.tools.xml.transformation.local;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.eurelis.tools.xml.transformation.DocumentFeeder;
import com.eurelis.tools.xml.transformation.DocumentSource;

/**
 * The Class LocalDocumentFeeder.
 */
public class LocalDocumentFeeder implements DocumentFeeder {

	
	/** The local document source list. */
	private List<LocalDocumentSource> localDocumentSourceList = new ArrayList<LocalDocumentSource>();
	
	/** An iterator for the document source list. */
	private Iterator<LocalDocumentSource> iterator = null;
	
	/**
	 * Instantiates a new local document feeder.
	 */
	public LocalDocumentFeeder() {
		
	}
	
	/**
	 * Adds a local document source from a File object
	 *
	 * @param file the file
	 * @param ignoreInitialValidation true if we don't want to check if the document is already valid before processing the transformation
	 * @return true, if the document has been successfuly added
	 */
	public boolean addLocalDocumentSource(File file, boolean ignoreInitialValidation) {
		boolean success = false;
		
		if (iterator == null) {
			// on ne peut ajouter que si on n'a pas commence a iterer
			try {
				LocalDocumentSource lds = new LocalDocumentSource(file, ignoreInitialValidation);
				localDocumentSourceList.add(lds);
				success = true;
			}
			catch (Exception e) {
			
			}
		}
		
		return success;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.eurelis.tools.xml.transformation.DocumentFeeder#hasNext()
	 */
	public boolean hasNext() {
		
		if (iterator == null) {
			iterator = localDocumentSourceList.iterator();
		}
		
		return iterator.hasNext();
	}


	/* (non-Javadoc)
	 * @see com.eurelis.tools.xml.transformation.DocumentFeeder#nextDocumentSource()
	 */
	public DocumentSource nextDocumentSource() {
		return (iterator == null)?null:iterator.next();
	}

/*	
	private Set<String> alreadyResolvedEntites = new HashSet<String>();
	
	public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
		
		//System.out.printf("%s%n", systemId);
		
		if (systemId.startsWith("opencms://")) {
			alreadyResolvedEntites.add(systemId);
			String workSystemId = systemId.substring("opencms://".length());
			
			File file = null;
			
			if (workSystemId.equalsIgnoreCase("opencms-xmlcontent.xsd")) {
				file = new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/opencms-xmlcontent.xsd");
			}
			else if (workSystemId.startsWith("system/modules/com.arkema.galaxy.opencms.core/schemas/editorial/")) {
				String relativeSystemId = workSystemId.substring("system/modules/com.arkema.galaxy.opencms.core/schemas/editorial/".length());
				
				file = new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/" + relativeSystemId);	
			}
			else if (workSystemId.startsWith("system/modules/com.arkema.galaxy.opencms.core/schemas/generic-nested/")) {
				String relativeSystemId = workSystemId.substring("system/modules/com.arkema.galaxy.opencms.core/schemas/".length());
				
				file = new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/" + relativeSystemId);
			}
			
			if (file != null && file.exists() && file.canRead()) {
				
				LSInput input = new DOMInputImpl();
		        try {
		        	
		        	
		        	FileReader fileReader = new FileReader(file);
		        	BufferedReader br = new BufferedReader(fileReader);
		            
		        	StringBuilder sb = new StringBuilder();
		        	
		        	String line = null;
		        	
		        	
		        	while ((line = br.readLine()) != null) {
		        		for (String schemaLocation : alreadyResolvedEntites) {
		        			line = line.replace(String.format("<xsd:include schemaLocation=\"%s\"/>", schemaLocation), "");
		        		}
		        		sb.append(line + '\n');
		        	}
		        	
		        	br.close();
		        	fileReader.close();
		        	
		        	StringReader sr = new StringReader(sb.toString());
		        	
		        	input.setCharacterStream(sr);
		        	sr.close();
		        	
		        	return input;
		        	
		        } catch (FileNotFoundException ex) {
		            return null;
		        } catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
			
			
		}
		
		
		
		return null;
	}
*/

	/** Set of entities already resolved, used to remove further references of xsd files. */
	private Set<String> resolvedEntitiesSystemId = new HashSet<String>();
	
	
	
	/* (non-Javadoc)
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
	 */
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		
		if (systemId.startsWith("opencms://")) {
			resolvedEntitiesSystemId.add(systemId);
			
			String workSystemId = systemId.substring("opencms://".length());
			
			File file = null;
			
			if (workSystemId.equalsIgnoreCase("opencms-xmlcontent.xsd")) {
				file = new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/opencms-xmlcontent.xsd");
			}
			else if (workSystemId.startsWith("system/modules/com.arkema.galaxy.opencms.core/schemas/editorial/")) {
				String relativeSystemId = workSystemId.substring("system/modules/com.arkema.galaxy.opencms.core/schemas/editorial/".length());
				
				file = new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/" + relativeSystemId);
				
			}
			else if (workSystemId.startsWith("system/modules/com.arkema.galaxy.opencms.core/schemas/generic-nested/")) {
				String relativeSystemId = workSystemId.substring("system/modules/com.arkema.galaxy.opencms.core/schemas/".length());
				
				file = new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/" + relativeSystemId);
			}
			
			if (file != null && file.exists() && file.canRead()) {
				
				
				
		        try {
		            
		        	FileReader fileReader = new FileReader(file);
		        	BufferedReader br = new BufferedReader(fileReader);
		            
		        	StringBuilder sb = new StringBuilder();
		        	
		        	String line = null;
		        	
		        	
		        	while ((line = br.readLine()) != null) {
		        		for (String schemaLocation : resolvedEntitiesSystemId) {
		        			// we remove inclusion of already read xsd files
		        			line = line.replace(String.format("<xsd:include schemaLocation=\"%s\"/>", schemaLocation), "");
		        		}
		        		sb.append(line + '\n');
		        	}
		        	
		        	br.close();
		        	fileReader.close();
		        	
		        	StringReader sr = new StringReader(sb.toString());
		        	
		            return new InputSource(sr);
		        } catch (FileNotFoundException ex) {
		        	
		            return null;
		        }
				
				
			}
			
			
		}
		
		
		
		return null;
	}


	/* (non-Javadoc)
	 * @see com.eurelis.tools.xml.transformation.DocumentFeeder#resetEntityResolver()
	 */
	public void resetEntityResolver() {
		resolvedEntitiesSystemId = new HashSet<String>();
		//alreadyResolvedEntites = new HashSet<String>();
	}

}
