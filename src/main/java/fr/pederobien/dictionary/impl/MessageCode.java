package fr.pederobien.dictionary.impl;

import fr.pederobien.dictionary.interfaces.IMessageCode;

public class MessageCode implements IMessageCode {
	private String value;

	/**
	 * Creates a message code based on the given value.
	 * 
	 * @param value The code value.
	 */
	public MessageCode(String value) {
		this.value = value;
	}

	@Override
	public String value() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof IMessageCode))
			return false;

		IMessageCode other = (IMessageCode) obj;
		return value.equals(other.value());
	}
}
