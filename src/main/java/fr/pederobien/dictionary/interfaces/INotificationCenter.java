package fr.pederobien.dictionary.interfaces;

public interface INotificationCenter {

	/**
	 * @return The context to register or unregister a dictionary.
	 */
	IDictionaryContext getDictionaryContext();
}
