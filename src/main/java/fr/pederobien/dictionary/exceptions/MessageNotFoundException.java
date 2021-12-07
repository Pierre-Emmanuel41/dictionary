package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.ICode;

public class MessageNotFoundException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private ICode code;

	public MessageNotFoundException(IDictionary dictionary, ICode code) {
		super("There is no message for code " + code, dictionary);
		this.code = code;
	}

	/**
	 * @return The code used to get a message in a dictionary.
	 */
	public ICode getCode() {
		return code;
	}
}
