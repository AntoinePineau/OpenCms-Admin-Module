package com.eurelis.tools.xml.transformation.processors;

import com.eurelis.tools.xml.transformation.Journal;

/**
 * An abstract class to describe a processor
 */
public abstract class Processor {
	protected Journal journal;
	private Processor parentProcessor;

	public Processor(Journal journal, Processor parent) {
		this.journal = journal;
		this.parentProcessor = parent;
	}

	public Processor getParent() {
		return parentProcessor;
	}
	
	public abstract String getName();
}