package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessage;

public class MessageRegisteredException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private IMessage registered, newMessage;

	public MessageRegisteredException(IDictionary dictionary, String code, IMessage registered, IMessage newMessage) {
		super(String.format("A message is already registered for %s (registered: %s, new: %s)", code, registered, newMessage), dictionary);
		this.registered = registered;
	}

	/**
	 * @return The message already registered in the dictionary.
	 */
	public IMessage getRegistered() {
		return registered;
	}

	/**
	 * @return The message that should be added to the dictionary.
	 */
	public IMessage getNewMessage() {
		return newMessage;
	}
}
