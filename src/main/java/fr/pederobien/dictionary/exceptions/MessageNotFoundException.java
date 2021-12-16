package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IDictionary;

public class MessageNotFoundException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private String code;

	public MessageNotFoundException(IDictionary dictionary, String code) {
		super("There is no message for code " + code, dictionary);
		this.code = code;
	}

	/**
	 * @return The code used to get a message in a dictionary.
	 */
	public String getCode() {
		return code;
	}
}
