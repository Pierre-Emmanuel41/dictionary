package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessageCode;

public class MessageRegisteredException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private IMessageCode code;

	public MessageRegisteredException(IDictionary dictionary, IMessageCode code) {
		super("A message is already registered for " + code, dictionary);
		this.code = code;
	}

	/**
	 * @return The code used to register a message in a dictionary.
	 */
	public IMessageCode getCode() {
		return code;
	}
}
