package fr.pederobien.dictionary.impl;

import fr.pederobien.dictionary.interfaces.IDictionaryContext;
import fr.pederobien.dictionary.interfaces.INotificationCenter;

public class NotificationCenter implements INotificationCenter {
	private IDictionaryContext dictionaryContext;

	private NotificationCenter() {
		dictionaryContext = new DictionaryContext();
	}

	public static INotificationCenter getInstance() {
		return SingletonHolder.CENTER;
	}

	private static class SingletonHolder {
		public static final INotificationCenter CENTER = new NotificationCenter();
	}

	@Override
	public IDictionaryContext getDictionaryContext() {
		return dictionaryContext;
	}
}
