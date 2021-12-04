package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IDictionary;

public abstract class DictionaryException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private IDictionary dictionary;

	protected DictionaryException(String message, IDictionary dictionary) {
		super(message);
		this.dictionary = dictionary;
	}

	/**
	 * @return The dictionary in which the messages is registered.
	 */
	public IDictionary getDictionary() {
		return dictionary;
	}
}
