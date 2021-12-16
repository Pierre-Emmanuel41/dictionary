package fr.pederobien.dictionary.exceptions;

import java.util.Locale;

import fr.pederobien.dictionary.interfaces.IDictionary;

public class SecondTryMessageNotFoundException extends DictionaryException {
	private static final long serialVersionUID = 1L;

	public SecondTryMessageNotFoundException(IDictionary dictionary, Locale locale, String code) {
		super("Message associated to " + code + " not found neither for locale '" + locale.getDisplayLanguage() + "' nor for locale 'English'", dictionary);
	}
}
