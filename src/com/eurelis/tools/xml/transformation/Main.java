/*
 * Copyright (c) Eurelis. All rights reserved. CONFIDENTIAL - Use is subject to license terms.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are not permitted without prior written permission of Eurelis.
 */

package com.eurelis.tools.xml.transformation;

import java.io.File;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.xpath.DefaultXPath;

import com.eurelis.tools.xml.transformation.JournalEntry.EntryKind;
import com.eurelis.tools.xml.transformation.local.LocalDocumentFeeder;
import com.eurelis.tools.xml.transformation.model.XMLTransformation;
import com.eurelis.tools.xml.transformation.model.builder.TemplateTransformationBuilder;
import com.eurelis.tools.xml.transformation.model.builder.UnitaryTransformationBuilder;
import com.eurelis.tools.xml.transformation.model.builder.XMLTransformationBuilder;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LocalDocumentFeeder ldf = new LocalDocumentFeeder();
		ldf.addLocalDocumentSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xml/old/articles/batteries-li-ion.xml"), false);
		
		
		XMLTransformationBuilder xtb = XMLTransformationBuilder.newInstance();
		
		//xtb.addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/article.xsd")));
		/*
			.addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/opencms-xmlcontent.xsd")))
		   .addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/nested/advanced-link-list.xsd")))
		   .addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/nested/intro-media.xsd")))
		   .addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/nested/paragraph.xsd")))
		   .addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/nested/video.xsd")))
		   .addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/nested/paragraph-column.xsd")))
		   .addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/nested/paragraph-column-element.xsd")))
		   .addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/nested/paragraph-column-elementtype.xsd")))
		   .addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/nested/quote.xsd")))
		   .addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/nested/image-hd-link.xsd")))
		   .addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/new/generic-nested/advanced-link.xsd")));
		*/
		
		//xtb.addValidationSource(new StreamSource(new File("/Users/eurelis/Downloads/eurelis-xmltransormation/xsd/test.xsd")));
		
		UnitaryTransformationBuilder utb = xtb.newUnitaryTransformationBuilder();
		utb.setSource(new DefaultXPath("/Articles/Article/Paragraph/Column/Element/ElementType/AdvancedLinksList/Link/Resource/Resource"));
		utb.setXPathDestination("/Articles/Article/Paragraph/Column/Element/ElementType/AdvancedLinksList/Link/Resource");
		
		
		UnitaryTransformationBuilder utb2 = xtb.newUnitaryTransformationBuilder();
		utb2.setSource(new DefaultXPath("/Articles/Article/Paragraph/Column/Element/ElementType/AdvancedLinksList/Link/Resource/Pointer"));
		utb2.setXPathDestination("/Articles/Article/Paragraph/Column/Element/ElementType/AdvancedLinksList/Link/Resource");
		
		UnitaryTransformationBuilder utb3 = xtb.newUnitaryTransformationBuilder();
		utb3.setSource(new DefaultXPath("/Articles/Article/Headline/@name"));
		utb3.setXPathDestination("/Articles/Article/Headline/@nom");
		
		UnitaryTransformationBuilder utb4 = xtb.newUnitaryTransformationBuilder();
		utb4.setSource(new DefaultXPath("/Articles/Article[@language='en']/Paragraph/Column/Element/ElementType/Quote/Label"));
		utb4.setSXPathDestination("/Articles/Article[@language='en']/Paragraph/Column/Element/ElementType/Quote2/Label[@language='en' and @test='true']/victoire");
		
		UnitaryTransformationBuilder utb5 = xtb.newUnitaryTransformationBuilder();
		utb5.setSource(new DefaultXPath("/Articles/Article[@language='fr']/Paragraph/Column/Element/ElementType/Quote"));
		utb5.setXPathDestination("/Articles/Article[@language='fr']/Paragraph/Column/Element/ElementType");
		TemplateTransformationBuilder ttb = utb5.getTemplateTransformationBuilder();
		try {
			ttb.setTemplate("<MyQuote><MyLabel>$label</MyLabel><Author>$name $function</Author></MyQuote>");
			ttb.addParameter("label", new DefaultXPath("Label/text()"));
			ttb.addParameter("name", new DefaultXPath("Name/text()"));
			ttb.addParameter("function", new DefaultXPath("Function/text()"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
				
		
		XMLTransformation xmlTrans = xtb.build();
		
		BatchProcessor batchProcessor = new BatchProcessor(ldf, xmlTrans);
		List<Journal> journalList = batchProcessor.run(false);
		
		for (Journal journal : journalList) {
			System.out.println("----");
			
			for (JournalEntry je : journal.getEntries(EntryKind.WARNING)) {
				System.out.println(je.description());
				
			}
			
			
		}
		
		
		
	}

}
