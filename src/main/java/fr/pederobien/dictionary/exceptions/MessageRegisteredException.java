package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IDictionary;

public class MessageRegisteredException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private String code;

	public MessageRegisteredException(IDictionary dictionary, String code) {
		super("A message is already registered for " + code, dictionary);
		this.code = code;
	}

	/**
	 * @return The code used to register a message in a dictionary.
	 */
	public String getCode() {
		return code;
	}
}
