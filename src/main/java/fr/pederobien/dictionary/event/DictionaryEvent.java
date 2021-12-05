package fr.pederobien.dictionary.event;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.utils.event.Event;

public class DictionaryEvent extends Event {
	private IDictionary dictionary;

	/**
	 * Creates a dictionary event.
	 * 
	 * @param dictionary The dictionary source involved in this event.
	 */
	public DictionaryEvent(IDictionary dictionary) {
		this.dictionary = dictionary;
	}

	/**
	 * @return The dictionary involved in this event.
	 */
	public IDictionary getDictionary() {
		return dictionary;
	}
}
