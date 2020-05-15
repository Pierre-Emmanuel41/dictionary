package fr.pederobien.dictionary.interfaces;

import java.util.Locale;

public interface IMessageEvent {

	/**
	 * @return The locale used to know in which dictionary find the translation associated to the code.
	 * 
	 * @see #getCode()
	 */
	Locale getLocale();

	/**
	 * @return The code used to get the associated {@link IMessage}.
	 */
	IMessageCode getCode();

	/**
	 * @return An array of arguments used for {@link IMessage#getMessage(String...)}.
	 */
	Object[] getArgs();
}
