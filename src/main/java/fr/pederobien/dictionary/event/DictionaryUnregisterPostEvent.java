package fr.pederobien.dictionary.event;

import java.util.Locale;
import java.util.StringJoiner;

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

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "languages={", "}");
		for (Locale locale : getDictionary().getLocales())
			joiner.add(locale.toString());
		return String.format("%s_%s", getName(), joiner);
	}
}
