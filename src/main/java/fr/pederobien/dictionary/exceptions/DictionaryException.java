package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessageEvent;

public abstract class DictionaryException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private IMessageEvent event;
	private IDictionary dictionary;

	protected DictionaryException(IMessageEvent event, IDictionary dictionary) {
		this.event = event;
		this.dictionary = dictionary;
	}

	/**
	 * @return The event responsible of this exception.
	 */
	public IMessageEvent getEvent() {
		return event;
	}

	/**
	 * @return The dictionary in which the messages is registered.
	 */
	public IDictionary getDictionary() {
		return dictionary;
	}
}
