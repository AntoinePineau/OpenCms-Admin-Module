package com.eurelis.tools.xml.transformation;

import java.util.ArrayList;
import java.util.List;

import com.eurelis.tools.xml.transformation.processors.Processor;


/**
 * The Class Journal.
 */
public class Journal {
	
	/** The unique identifier for the document whose transformation is logged in this journal. */
	private String documentId;
	
	/** The journal entries. */
	private List<JournalEntry> entries = new ArrayList<JournalEntry>();
	
	
	/**
	 * Instantiates a new journal.
	 *
	 * @param documentId the document id
	 */
	public Journal(String documentId) {
		this.documentId = documentId;
	}
	
	
	/**
	 * Info.
	 *
	 * @param writer the processor instance at the origin of the message
	 * @param message the info message
	 */
	public void info(Processor writer, String message) {
		JournalEntry newJournalEntry = new JournalEntry(JournalEntry.EntryKind.INFO, message, writer, this.documentId);
		
		this.entries.add(newJournalEntry);
	}
	

	/**
	 * Warning.
	 *
	 * @param writer the processor instance at the origin of the message
	 * @param message the warning message
	 */
	public void warning(Processor writer, String message) {
		JournalEntry newJournalEntry = new JournalEntry(JournalEntry.EntryKind.WARNING, message, writer, this.documentId);
		
		this.entries.add(newJournalEntry);
	}
	
	
	/**
	 * Error.
	 *
	 * @param writer the processor instance at the origin of the message
	 * @param message the error message
	 */
	public void error(Processor writer, String message) {
		JournalEntry newJournalEntry = new JournalEntry(JournalEntry.EntryKind.ERROR, message, writer, this.documentId);
		
		this.entries.add(newJournalEntry);
	}
	
	
	/**
	 * Gets the all entries.
	 *
	 * @return the all entries
	 */
	public List<JournalEntry> getAllEntries() {
		return this.entries;
	}
	
	
	/**
	 * Gets the entries.
	 *
	 * @param minimumLevel the minimum level
	 * @return the entries
	 */
	public List<JournalEntry> getEntries(JournalEntry.EntryKind minimumLevel) {
		if (minimumLevel == null || minimumLevel == JournalEntry.EntryKind.INFO) {
			return this.getAllEntries();
		}
		else {
			List<JournalEntry> filteredList = new ArrayList<JournalEntry>();
			
			if (minimumLevel == JournalEntry.EntryKind.WARNING) {
				
				for (JournalEntry je : this.entries) {
					if (je.getKind() != JournalEntry.EntryKind.INFO) {
						// if the kind isn't INFO, then it is either WARNING or ERROR
						filteredList.add(je);
					}
				}
				
			}
			else {
				// ERROR
				for (JournalEntry je : this.entries) {
					if (je.getKind() == JournalEntry.EntryKind.ERROR) {
						filteredList.add(je);
					}
				}
			}
			
			return filteredList;
		}
		
	}
	

	
}