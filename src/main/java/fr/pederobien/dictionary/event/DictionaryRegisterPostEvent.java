package fr.pederobien.dictionary.event;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IDictionaryContext;

public class DictionaryRegisterPostEvent extends DictionaryEvent {
	private IDictionaryContext context;

	/**
	 * Creates an event thrown when a dictionary has been registered to a dictionary context.
	 * 
	 * @param dictionary The added dictionary.
	 * @param context    The context to which a dictionary has been added.
	 */
	public DictionaryRegisterPostEvent(IDictionary dictionary, IDictionaryContext context) {
		super(dictionary);
		this.context = context;
	}

	/**
	 * @return The context to which a dictionary has been added.
	 */
	public IDictionaryContext getContext() {
		return context;
	}
}
