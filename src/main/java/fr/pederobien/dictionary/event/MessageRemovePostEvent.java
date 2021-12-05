package fr.pederobien.dictionary.event;

import java.util.Locale;
import java.util.StringJoiner;

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

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		StringJoiner localeJoiner = new StringJoiner(", ", "languages={", "}");
		for (Locale locale : getDictionary().getLocales())
			localeJoiner.add(locale.toString());
		joiner.add(localeJoiner.toString());
		joiner.add("message=" + getMessage());
		return String.format("%s_%s", getName(), joiner);
	}
}
