package fr.pederobien.dictionary.event;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IDictionaryContext;

public class DictionaryUnregisterPostEvent extends DictionaryEvent {
	private IDictionaryContext context;

	/**
	 * Creates an event thrown when a dictionary has been unregistered from a dictionary context.
	 * 
	 * @param dictionary The removed dictionary.
	 * @param context    The context from which a dictionary has been removed.
	 */
	public DictionaryUnregisterPostEvent(IDictionary dictionary, IDictionaryContext context) {
		super(dictionary);
		this.context = context;
	}

	/**
	 * @return The context from which a dictionary has been removed.
	 */
	public IDictionaryContext getContext() {
		return context;
	}
}
