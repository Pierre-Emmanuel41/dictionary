package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessage;

public class NotEnoughArgumentsException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private IMessage message;

	public NotEnoughArgumentsException(IDictionary dictionary, IMessage message) {
		super("There is not enough argument for message " + message, dictionary);
		this.message = message;
	}

	/**
	 * @return The message that is already registered in a dictionary.
	 */
	public IMessage getRegisteredMessage() {
		return message;
	}
}
