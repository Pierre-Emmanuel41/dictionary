package fr.pederobien.dictionary.exceptions;

import java.util.Locale;

public class DictionaryNotFoundException extends DictionaryException {
	private static final long serialVersionUID = 1L;
	private Locale locale;

	public DictionaryNotFoundException(Locale locale) {
		super("There is no dictionary registered for locale " + locale.getDisplayLanguage(), null);
		this.locale = locale;
	}

	/**
	 * @return The locale used to find a dictionary.
	 */
	public Locale getLocale() {
		return locale;
	}
}
