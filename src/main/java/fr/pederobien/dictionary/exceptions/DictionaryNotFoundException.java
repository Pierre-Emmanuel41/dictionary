package fr.pederobien.dictionary.exceptions;

import java.util.Locale;

import fr.pederobien.dictionary.interfaces.IMessageEvent;

public class DictionaryNotFoundException extends MessageEventException {
	private static final long serialVersionUID = 1L;
	private Locale locale;

	public DictionaryNotFoundException(IMessageEvent event, Locale locale) {
		super(event);
		this.locale = locale;
	}

	/**
	 * @return The locale used as key to find the dictionary.
	 */
	public Locale getLocale() {
		return locale;
	}

	@Override
	public String getMessage() {
		return "No Dictionary found associated to local " + getLocale();
	}
}
