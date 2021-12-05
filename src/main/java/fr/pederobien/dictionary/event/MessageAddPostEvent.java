package fr.pederobien.dictionary.event;

import java.util.Locale;
import java.util.StringJoiner;

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
