package fr.pederobien.dictionary.interfaces;

import java.nio.file.Path;

public interface IDictionaryContext {

	/**
	 * Set the dictionary parser for this context. This parser is used to create a dictionary from a file.
	 * 
	 * @param parser The parser used to create a dictionary.
	 * 
	 * @return This dictionary context to register dictionaries easier.
	 */
	IDictionaryContext setParser(IDictionaryParser parser);

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
	 * Parse the file associated to the given path using the specified parser, and register it for this context.
	 * 
	 * @param parser The parser used to create a dictionary.
	 * @param path   The path to the dictionary file.
	 * 
	 * @return This parsed dictionary.
	 */
	IDictionary register(IDictionaryParser parser, Path path);

	/**
	 * Parse the file associated to the given path using the parser specified by {@link #setParser(IDictionaryParser)}. If any parser
	 * has been furnished, the method do nothing.
	 * 
	 * @param path The path to the dictionary file.
	 * 
	 * @return The parsed dictionary if a parser has been furnished, null otherwise.
	 */
	IDictionary register(Path path);

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
