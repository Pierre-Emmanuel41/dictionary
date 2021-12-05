package fr.pederobien.dictionary.interfaces;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import fr.pederobien.dictionary.event.DictionaryRegisterPostEvent;
import fr.pederobien.dictionary.event.DictionaryUnregisterPostEvent;
import fr.pederobien.dictionary.exceptions.DictionaryNotFoundException;
import fr.pederobien.dictionary.exceptions.SecondTryMessageNotFoundException;

public interface IDictionaryContext {

	/**
	 * Set the dictionary parser for this context. This parser is used to create a dictionary from a file. If the given parser is
	 * null, then the parser is the default dictionary parser.
	 * 
	 * @param parser The parser used to create a dictionary.
	 * 
	 * @return This dictionary context to register dictionaries easier.
	 */
	IDictionaryContext setParser(IDictionaryParser parser);

	/**
	 * Register the given dictionary for its locales. If a dictionary is already registered for locales supported by the given
	 * dictionary, the new dictionary is concatenated to the old one. This method should throw a {@link DictionaryRegisterPostEvent}.
	 * 
	 * @param dictionary The dictionary used to get message when an {@link IMessageEvent} arrives.
	 * 
	 * @return This dictionary context to register dictionaries easier.
	 */
	IDictionaryContext register(IDictionary dictionary);

	/**
	 * Parse the file associated to the given path using the parser specified by {@link #setParser(IDictionaryParser)}. This method
	 * should throw a a {@link DictionaryRegisterPostEvent}
	 * 
	 * @param path The path to the dictionary file.
	 * 
	 * @return This dictionary context to register dictionaries easier.
	 * 
	 * @throws FileNotFoundException If the path does not represent a file.
	 */
	IDictionaryContext register(Path path) throws FileNotFoundException;

	/**
	 * Unregister the given dictionary. If this dictionary is already concatenated to another one, then only its messages are removed
	 * from the global dictionary. This method should throw a {@link DictionaryUnregisterPostEvent}
	 * 
	 * @param dictionary The dictionary to remove.
	 * 
	 * @return This dictionary context to unregister dictionaries easier.
	 */
	IDictionaryContext unregister(IDictionary dictionary);

	/**
	 * Get the dictionary associated to the given {@link Locale}.
	 * 
	 * @param locale The locale used as key to get a dictionary.
	 * 
	 * @return An optional that contains a dictionary if there is a registered dictionary for The given locale, an empty optional
	 *         otherwise.
	 */
	Optional<IDictionary> getDictionary(Locale locale);

	/**
	 * @return An unmodifiable map that contains all registered dictionaries for this context.
	 */
	Map<Locale, IDictionary> getDictionaries();

	/**
	 * Get the message associated to the code from the event. First, the method try to get the dictionary associated to this local. If
	 * no dictionary is registered for this locale, the method try to find the dictionary associated to {@link Locale#ENGLISH}. If
	 * there is any English dictionary, the method throws an {@link DictionaryNotFoundException}. However, if a dictionary associated
	 * to the event's locale has been found, then it try to get the message associated to the code from the event. If no message is
	 * registered for this code, the method try to get the message from the English dictionary. When there is any message associated
	 * to the code, the method throws an {@link SecondTryMessageNotFoundException}.
	 * 
	 * @param event The used to get a message stored in a dictionary.
	 * @return The message associated to the message code.
	 * 
	 * @see IMessageCode
	 * @see IDictionary
	 * 
	 * @throws DictionaryNotFoundException       if there is no registered dictionary for the locale.
	 * @throws SecondTryMessageNotFoundException if any has been found in the player's dictionary and in the English dictionary.
	 */
	String getMessage(IMessageEvent event);
}
