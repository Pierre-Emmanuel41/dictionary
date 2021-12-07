package fr.pederobien.dictionary.impl;

import fr.pederobien.dictionary.interfaces.ICode;

public class MessageCode implements ICode {
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

		if (!(obj instanceof ICode))
			return false;

		ICode other = (ICode) obj;
		return value.equals(other.value());
	}
}
