package fr.pederobien.dictionary.event;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessage;

public class MessageAddPostEvent extends DictionaryEvent {
	private IMessage message;

	/**
	 * Creates an event thrown when a message has been added to a dictionary.
	 * 
	 * @param dictionary The dictionary to which a message has been added.
	 * @param message    The added message.
	 */
	public MessageAddPostEvent(IDictionary dictionary, IMessage message) {
		super(dictionary);
		this.message = message;
	}

	/**
	 * @return The added message.
	 */
	public IMessage getMessage() {
		return message;
	}
}
