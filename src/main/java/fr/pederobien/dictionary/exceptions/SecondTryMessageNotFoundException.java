package fr.pederobien.dictionary.exceptions;

import java.util.Locale;
import java.util.StringJoiner;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessageEvent;

public class SecondTryMessageNotFoundException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private IDictionary secondDictionary;
	private Locale firstLocale, secondLocale;

	public SecondTryMessageNotFoundException(IMessageEvent event, IDictionary dictionary, IDictionary secondDictionary, Locale firstLocale, Locale secondLocale) {
		super(event, dictionary);
		this.secondDictionary = secondDictionary;
		this.firstLocale = firstLocale;
		this.secondLocale = secondLocale;
	}

	@Override
	public String getMessage() {
		StringJoiner joiner = new StringJoiner(", ");
		joiner.add("Message not found twice");
		joiner.add(getEvent().toString());
		joiner.add("\n{First try: Locale={" + getFirstLocale() + "}, dictionary=" + getDictionary() + "}");
		joiner.add("\n{Second try: Locale={" + getSecondLocale() + "}, dictionary=" + getSecondDictionary() + "}");
		return joiner.toString();
	}

	/**
	 * @return The second dictionary used to find the message. This dictionary could be null if it was not found.
	 */
	public IDictionary getSecondDictionary() {
		return secondDictionary;
	}

	/**
	 * @return The player locale used to find a message translated in its language.
	 */
	public Locale getFirstLocale() {
		return firstLocale;
	}

	/**
	 * @return Generally equals to {@link Locale#ENGLISH}, this locale is used when no message has been found in the dictionary that
	 *         contains messages in the player language.
	 */
	public Locale getSecondLocale() {
		return secondLocale;
	}
}
