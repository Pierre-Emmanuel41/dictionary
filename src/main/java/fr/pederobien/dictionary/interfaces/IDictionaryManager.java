package fr.pederobien.dictionary.interfaces;

import java.util.Locale;

import fr.pederobien.dictionary.exceptions.AnyRegisteredDictionaryException;
import fr.pederobien.dictionary.exceptions.DictionaryNotFoundException;
import fr.pederobien.dictionary.exceptions.SecondTryMessageNotFoundException;

public interface IDictionaryManager extends IDictionaryContext {

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
	 * @throws AnyRegisteredDictionaryException  if there is any registered dictionary for plugin from the given event.
	 * @throws SecondTryMessageNotFoundException if any has been found in the player's dictionary and in the English dictionary.
	 */
	String getMessage(IMessageEvent event);
}
