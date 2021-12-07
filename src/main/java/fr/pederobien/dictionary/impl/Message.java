package fr.pederobien.dictionary.impl;

import fr.pederobien.dictionary.interfaces.IMessage;
import fr.pederobien.dictionary.interfaces.ICode;

public class Message implements IMessage {
	private ICode code;
	private String format;

	/**
	 * Creates a message. A message is the association of a code and a format.
	 * 
	 * @param code   The message code.
	 * @param format The message format.
	 */
	public Message(ICode code, String format) {
		this.code = code;
		this.format = format;
	}

	/**
	 * Creates a message. A message is the association of a code and a format.
	 * 
	 * @param code   The message code.
	 * @param format The message format.
	 */
	public Message(String code, String format) {
		this(new MessageCode(code), format);
	}

	@Override
	public ICode getCode() {
		return code;
	}

	@Override
	public String getMessage(Object... args) {
		return String.format(format, args);
	}

	@Override
	public String getFormat() {
		return format;
	}

	@Override
	public String toString() {
		return String.format("code=%s, format=%s", code, format);
	}
}
