package fr.pederobien.dictionary.impl;

import fr.pederobien.dictionary.interfaces.IMessage;
import fr.pederobien.dictionary.interfaces.IMessageCode;

public abstract class AbstractMessage implements IMessage {
	private IMessageCode code;

	protected AbstractMessage(IMessageCode code) {
		this.code = code;
	}

	@Override
	public IMessageCode getCode() {
		return code;
	}
}
