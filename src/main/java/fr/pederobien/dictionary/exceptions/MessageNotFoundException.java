package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessageCode;

public class MessageNotFoundException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private IMessageCode code;

	public MessageNotFoundException(IDictionary dictionary, IMessageCode code) {
		super("There is no message for code " + code, dictionary);
		this.code = code;
	}

	/**
	 * @return The code used to get a message in a dictionary.
	 */
	public IMessageCode getCode() {
		return code;
	}
}
