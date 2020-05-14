package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IMessageEvent;

public class AnyRegisteredDictionaryException extends MessageEventException {
	private static final long serialVersionUID = 1L;

	public AnyRegisteredDictionaryException(IMessageEvent event) {
		super(event);
	}

	@Override
	public String getMessage() {
		return "There is any registered dictionary for locale " + getEvent().getLocale();
	}
}
