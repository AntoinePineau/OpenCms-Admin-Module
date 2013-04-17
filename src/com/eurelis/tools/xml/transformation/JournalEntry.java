package com.eurelis.tools.xml.transformation;

import com.eurelis.tools.xml.transformation.processors.Processor;


/**
 * The Class JournalEntry.
 */
public class JournalEntry {
	
	
	/** The level of error of this entry. */
	private EntryKind kind;
	
	/** The message. */
	private String message;
	
	/** The processor instance that sent this message. */
	private Processor writer;
	
	/** The sender, in most cases the document which was being processed when this entry has been added. */
	private String sender;

	
	/**
	 * Instantiates a new journal entry.
	 *
	 * @param kind the error level of this entry
	 * @param message the message
	 * @param writer the processor instance that sent this message
	 * @param sender the sender
	 */
	public JournalEntry(EntryKind kind, String message, Processor writer, String sender) {
		this.kind = kind;
		this.message = message;
		this.writer = writer;
		this.sender = sender;
	}

	
	/**
	 * Returns a description of the entry.
	 *
	 * @return the string
	 */
	public String description() {
		String levelKind = "";
		if (kind == EntryKind.INFO) {
			levelKind = "INFO";
		}
		else if (kind == EntryKind.WARNING) {
			levelKind = "WARNING";
		}
		else if (kind == EntryKind.ERROR) {
			levelKind = "ERROR";
		}
		
		return String.format("%s - %s : %s, %s", levelKind, this.writer.getName(), this.sender, this.message);
	}
	
	
	/**
	 * Enumeration used to describe the level of a journal entry.
	 */
	public enum EntryKind {
		/** The info level. */
		INFO, 
		/** The warning level. */
		WARNING, 
		/** The error level. */
		ERROR;
	}
	

	/**
	 * Gets the error level.
	 *
	 * @return the level
	 */
	public EntryKind getKind() {
		return kind;
	}
}