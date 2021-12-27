package fr.pederobien.dictionary.impl;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import fr.pederobien.dictionary.event.DictionaryRegisterPostEvent;
import fr.pederobien.dictionary.event.DictionaryUnregisterPostEvent;
import fr.pederobien.dictionary.exceptions.DictionaryNotFoundException;
import fr.pederobien.dictionary.exceptions.MessageNotFoundException;
import fr.pederobien.dictionary.exceptions.SecondTryMessageNotFoundException;
import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IDictionaryContext;
import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.dictionary.interfaces.IMessage;
import fr.pederobien.dictionary.interfaces.IMessageEvent;
import fr.pederobien.utils.event.EventManager;

public class DictionaryContext implements IDictionaryContext {
	private static final IDictionaryParser DEFAULT_PARSER = new XmlDictionaryParser();

	private Map<Locale, IDictionary> dictionaries;
	private IDictionaryParser parser;

	public DictionaryContext() {
		dictionaries = new HashMap<Locale, IDictionary>();
		parser = DEFAULT_PARSER;
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
			else {
				IDictionary clone = dictionary.clone(locale);
				dictionaries.put(locale, clone);
				EventManager.callEvent(new DictionaryRegisterPostEvent(clone, this));
			}
		}
		return this;
	}

	@Override
	public IDictionaryContext register(String path) throws FileNotFoundException {
		return register(getParser().parse(path));
	}

	@Override
	public IDictionaryContext unregister(IDictionary dictionary) {
		for (Locale locale : dictionary.getLocales()) {
			IDictionary localDictionary = dictionaries.get(locale);
			if (localDictionary == null)
				continue;

			for (IMessage message : dictionary.getMessages())
				localDictionary.unregister(message.getCode());

			if (localDictionary.getMessages().isEmpty())
				dictionaries.remove(locale);
		}
		EventManager.callEvent(new DictionaryUnregisterPostEvent(dictionary, this));
		return this;
	}

	@Override
	public Optional<IDictionary> getDictionary(Locale locale) {
		IDictionary dictionary = dictionaries.get(locale);
		return dictionary == null ? Optional.empty() : Optional.of(dictionary);
	}

	@Override
	public Map<Locale, IDictionary> getDictionaries() {
		return Collections.unmodifiableMap(dictionaries);
	}

	@Override
	public String getMessage(IMessageEvent event) {
		IDictionary dictionary = dictionaries.get(event.getLocale());

		try {
			return dictionary.getMessage(event);
		} catch (MessageNotFoundException | NullPointerException e) {

			// Dictionary that contains messages in English
			IDictionary secondDictionary = dictionaries.get(Locale.ENGLISH);
			try {
				return secondDictionary.getMessage(event);
			} catch (MessageNotFoundException e1) {
				throw new SecondTryMessageNotFoundException(dictionary, event.getLocale(), event.getCode());
			} catch (NullPointerException e1) {
				throw new DictionaryNotFoundException(Locale.ENGLISH);
			}
		}
	}

	/**
	 * @return The dictionary parser.
	 */
	private IDictionaryParser getParser() {
		return parser == null ? DEFAULT_PARSER : parser;
	}
}
