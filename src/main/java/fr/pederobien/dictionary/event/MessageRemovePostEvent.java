package fr.pederobien.dictionary.event;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessage;

public class MessageRemovePostEvent extends DictionaryEvent {
	private IMessage message;

	/**
	 * Creates an event thrown when a message has been removed from a dictionary.
	 * 
	 * @param dictionary The dictionary from which a message has been removed.
	 * @param message    The removed message.
	 */
	public MessageRemovePostEvent(IDictionary dictionary, IMessage message) {
		super(dictionary);
		this.message = message;
	}

	/**
	 * @return The removed message.
	 */
	public IMessage getMessage() {
		return message;
	}
}
