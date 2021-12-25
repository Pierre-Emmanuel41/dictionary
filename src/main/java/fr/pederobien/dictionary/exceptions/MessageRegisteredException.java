package fr.pederobien.dictionary.exceptions;

import java.util.Locale;
import java.util.StringJoiner;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessage;

public class MessageRegisteredException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private IMessage registered, newMessage;

	public MessageRegisteredException(IDictionary dictionary, String code, IMessage registered, IMessage newMessage) {
		super(null, dictionary);
		this.registered = registered;
		this.newMessage = newMessage;
	}

	@Override
	public String getMessage() {
		StringJoiner localJoiner = new StringJoiner(", ", "{", "}");
		for (Locale locale : getDictionary().getLocales())
			localJoiner.add(locale.toLanguageTag());

		String format = "A message is already registered for %s in dictionary=%s%n(registered: %s, new: %s)";
		return String.format(format, getRegistered().getCode(), localJoiner, getRegistered(), getNewMessage());
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
