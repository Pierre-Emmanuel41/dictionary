package fr.pederobien.dictionary.impl;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import fr.pederobien.dictionary.exceptions.AnyRegisteredDictionaryException;
import fr.pederobien.dictionary.exceptions.DictionaryNotFoundException;
import fr.pederobien.dictionary.exceptions.MessageNotFoundException;
import fr.pederobien.dictionary.exceptions.SecondTryMessageNotFoundException;
import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IDictionaryContext;
import fr.pederobien.dictionary.interfaces.IDictionaryManager;
import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.dictionary.interfaces.IMessage;
import fr.pederobien.dictionary.interfaces.IMessageEvent;

public class DictionaryManager implements IDictionaryManager {
	private Map<Locale, IDictionary> dictionaries;
	private IDictionaryParser parser;

	public DictionaryManager() {
		dictionaries = new HashMap<Locale, IDictionary>();
	}

	@Override
	public IDictionaryContext setParser(IDictionaryParser parser) {
		this.parser = parser;
		return this;
	}

	@Override
	public IDictionaryContext register(IDictionary dictionary) {
		// Register the dictionary for all supported locales.
		for (Locale locale : dictionary.getLocales()) {
			IDictionary localDictionary = dictionaries.get(locale);
			if (localDictionary != null)
				for (IMessage message : dictionary.getMessages())
					localDictionary.register(message);
			else
				dictionaries.put(locale, dictionary);
		}
		return this;
	}

	@Override
	public IDictionary register(IDictionaryParser parser, Path path) throws FileNotFoundException {
		IDictionary dictionary = parser.parse(path);
		register(dictionary);
		return dictionary;
	}

	@Override
	public IDictionary register(Path path) throws FileNotFoundException {
		if (parser == null)
			return null;
		return register(parser, path);
	}

	@Override
	public IDictionaryContext unregister(IDictionary dictionary) {
		for (Locale locale : dictionary.getLocales()) {
			IDictionary localDictionary = dictionaries.get(locale);
			if (localDictionary == null)
				continue;

			for (IMessage message : dictionary.getMessages())
				localDictionary.unregister(message.getCode());
		}
		return this;
	}

	@Override
	public String getMessage(IMessageEvent event) {
		IDictionary dictionary = dictionaries.get(event.getLocale());

		try {
			return dictionary.getMessage(event);
		} catch (MessageNotFoundException | DictionaryNotFoundException e) {

			// Dictionary that contains messages in English
			IDictionary secondDictionary = dictionaries.get(Locale.ENGLISH);
			try {
				return secondDictionary.getMessage(event);
			} catch (MessageNotFoundException e1) {
				throw new SecondTryMessageNotFoundException(event, dictionary, secondDictionary, event.getLocale(), Locale.ENGLISH);
			} catch (NullPointerException e1) {
				throw new AnyRegisteredDictionaryException(new MessageEvent(Locale.ENGLISH, event.getCode(), event.getArgs()));
			}
		} catch (NullPointerException e) {
			throw new AnyRegisteredDictionaryException(event);
		}
	}
}
