package fr.pederobien.dictionary.exceptions;

import fr.pederobien.dictionary.interfaces.IMessageEvent;

public class MessageEventException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private IMessageEvent event;

	public MessageEventException(IMessageEvent event) {
		this.event = event;
	}

	/**
	 * @return The event responsible of this exception.
	 */
	public IMessageEvent getEvent() {
		return event;
	}
}
