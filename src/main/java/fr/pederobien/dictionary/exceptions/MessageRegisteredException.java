package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.ICode;

public class MessageRegisteredException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private ICode code;

	public MessageRegisteredException(IDictionary dictionary, ICode code) {
		super("A message is already registered for " + code, dictionary);
		this.code = code;
	}

	/**
	 * @return The code used to register a message in a dictionary.
	 */
	public ICode getCode() {
		return code;
	}
}
