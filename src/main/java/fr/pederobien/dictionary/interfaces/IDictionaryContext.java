package fr.pederobien.dictionary.interfaces;

public interface IDictionaryContext {

	/**
	 * Register the given dictionary for its locales. If a dictionary is already registered for locales supported by the given
	 * dictionary, the new dictionary is concatenated to the old one.
	 * 
	 * @param dictionary The dictionary used to get message when an {@link IMessageEvent} arrives.
	 * 
	 * @return This dictionary context to register dictionaries easier.
	 */
	IDictionaryContext register(IDictionary dictionary);

	/**
	 * Unregister the given dictionary for the given plugin. If this dictionary is already concatenated to another one, then only its
	 * messages are removed from the global dictionary.
	 * 
	 * @param dictionary The dictionary to remove.
	 * 
	 * @return This dictionary context to unregister dictionaries easier.
	 */
	IDictionaryContext unregister(IDictionary dictionary);
}
