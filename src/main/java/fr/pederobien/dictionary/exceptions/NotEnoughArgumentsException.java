package fr.pederobien.dictionary.exceptions;

import java.util.StringJoiner;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessage;
import fr.pederobien.dictionary.interfaces.IMessageEvent;

public class NotEnoughArgumentsException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private IMessage message;

	public NotEnoughArgumentsException(IMessageEvent event, IDictionary dictionary, IMessage message) {
		super(event, dictionary);
		this.message = message;
	}

	@Override
	public String getMessage() {
		StringJoiner joiner = new StringJoiner(", ");
		joiner.add("Not enough arguments");
		joiner.add(getEvent().toString());
		joiner.add(getDictionary().toString());
		joiner.add("\n{Message=" + getDictionaryMessage().getClass().getSimpleName() + "}");
		return joiner.toString();
	}

	/**
	 * @return The message found in the dictionary.
	 */
	public IMessage getDictionaryMessage() {
		return message;
	}
}
