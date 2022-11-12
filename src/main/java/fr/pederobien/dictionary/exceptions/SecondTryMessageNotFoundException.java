package fr.pederobien.dictionary.exceptions;

import java.util.Locale;

import fr.pederobien.dictionary.interfaces.ICode;
import fr.pederobien.dictionary.interfaces.IDictionary;

public class SecondTryMessageNotFoundException extends DictionaryException {
	private static final long serialVersionUID = 1L;

	public SecondTryMessageNotFoundException(IDictionary dictionary, Locale locale, ICode code) {
		super(String.format("Message associated to %s not found neither for locale '%s' nor for locale 'English'", code.getCode(), locale.getDisplayLanguage()),
				dictionary);
	}
}
