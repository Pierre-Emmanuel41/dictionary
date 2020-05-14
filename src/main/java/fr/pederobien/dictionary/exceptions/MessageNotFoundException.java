package fr.pederobien.dictionary.exceptions;

import java.util.StringJoiner;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessageEvent;

public class MessageNotFoundException extends DictionaryException {
	private static final long serialVersionUID = 1L;

	public MessageNotFoundException(IMessageEvent event, IDictionary dictionary) {
		super(event, dictionary);
	}

	@Override
	public String getMessage() {
		StringJoiner joiner = new StringJoiner(", ");
		joiner.add("No associated message");
		joiner.add(getEvent().toString());
		joiner.add(getDictionary().toString());
		return joiner.toString();
	}
}
