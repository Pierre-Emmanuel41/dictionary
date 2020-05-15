package fr.pederobien.dictionary.impl;

import java.util.Locale;
import java.util.StringJoiner;

import fr.pederobien.dictionary.interfaces.IMessageCode;
import fr.pederobien.dictionary.interfaces.IMessageEvent;

public class MessageEvent implements IMessageEvent {
	private Locale locale;
	private IMessageCode code;
	private Object[] args;

	/**
	 * Create a message event. This event is used to be send to a dictionary to get the translation associated to the given code.
	 * 
	 * @param locale Used as key to find the right dictionary.
	 * @param code   Used as key to get the right message in the right dictionary.
	 * @param args   Some arguments (optional) used for dynamic messages.
	 * 
	 * @return A message event based on the given parameter.
	 */
	public MessageEvent(Locale locale, IMessageCode code, Object[] args) {
		this.locale = locale;
		this.code = code;
		this.args = args;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public IMessageCode getCode() {
		return code;
	}

	@Override
	public Object[] getArgs() {
		return args;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{MessageEvent=", "}");
		joiner.add("{Locale=" + locale + "}");
		joiner.add("{Code=" + getCode() + "}");
		StringJoiner joinerBis = new StringJoiner(" ", "{Args={", "}}");
		for (Object arg : getArgs())
			joinerBis.add(arg.toString());
		return joiner.add(joinerBis.toString()).toString();
	}
}
