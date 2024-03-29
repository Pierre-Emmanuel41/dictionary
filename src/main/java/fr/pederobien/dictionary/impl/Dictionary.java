package fr.pederobien.dictionary.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingFormatArgumentException;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import fr.pederobien.dictionary.event.DictionaryUnregisterPostEvent;
import fr.pederobien.dictionary.event.MessageAddPostEvent;
import fr.pederobien.dictionary.event.MessageRemovePostEvent;
import fr.pederobien.dictionary.exceptions.MessageNotFoundException;
import fr.pederobien.dictionary.exceptions.MessageRegisteredException;
import fr.pederobien.dictionary.exceptions.NotEnoughArgumentsException;
import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessage;
import fr.pederobien.dictionary.interfaces.IMessageEvent;
import fr.pederobien.utils.event.Event;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class Dictionary implements IDictionary, IEventListener {
	private boolean silent;
	private List<Locale> locales;
	private Map<String, IMessage> messages;

	private Dictionary(boolean silent, Locale... locales) {
		this.silent = silent;
		this.locales = new ArrayList<Locale>();
		for (Locale locale : locales)
			this.locales.add(locale);

		messages = new LinkedHashMap<String, IMessage>();
		EventManager.registerListener(this);
	}

	/**
	 * Creates a dictionary associated to the given locales. This dictionary throw no dictionary events.
	 * 
	 * @param locales The locale associated to this dictionary.
	 */
	protected Dictionary(Locale... locales) {
		this(true, locales);
	}

	/**
	 * Creates a dictionary associated to the given locale. This dictionary throw dictionary events.
	 * 
	 * @param locales The locale associated to this dictionary.
	 */
	public Dictionary(Locale locale) {
		this(false, locale);
	}

	@Override
	public List<Locale> getLocales() {
		return locales;
	}

	@Override
	public String getMessage(IMessageEvent event) {
		Iterator<Entry<String, IMessage>> iterator = messages.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, IMessage> entry = iterator.next();
			if (entry.getKey().equals(event.getCode())) {
				try {
					return entry.getValue().getMessage(event.getArgs());
				} catch (MissingFormatArgumentException e) {
					throw new NotEnoughArgumentsException(this, entry.getValue());
				}
			}
		}

		// When no message is registered for the code
		throw new MessageNotFoundException(this, event.getCode());
	}

	@Override
	public IDictionary register(IMessage message) {
		IMessage registered = messages.get(message.getCode());
		if (registered != null)
			throw new MessageRegisteredException(this, message.getCode(), registered, message);

		messages.put(message.getCode(), message);
		callEvent(new MessageAddPostEvent(this, message));
		return this;
	}

	@Override
	public IDictionary unregister(String code) {
		IMessage message = messages.remove(code);
		if (message != null)
			callEvent(new MessageRemovePostEvent(this, message));
		return this;
	}

	@Override
	public List<IMessage> getMessages() {
		return Collections.unmodifiableList(messages.values().stream().collect(Collectors.toList()));
	}

	@Override
	public IDictionary clone(Locale locale) {
		IDictionary dictionary = new Dictionary(locale);
		for (IMessage message : messages.values())
			dictionary.register(message);
		return dictionary;
	}

	@Override
	public String toString() {
		StringJoiner global = new StringJoiner(", ", "{", "}");
		StringJoiner localeJoiner = new StringJoiner(", ", "languages={", "}");
		for (Locale locale : locales)
			localeJoiner.add(locale.toString());
		global.add(localeJoiner.toString());
		StringJoiner messageJoiner = new StringJoiner(", ", "messages={", "}");
		for (Map.Entry<String, IMessage> entry : messages.entrySet())
			messageJoiner.add("{" + entry.getValue().toString() + "}");
		global.add(messageJoiner.toString());
		return global.toString();
	}

	@EventHandler
	private void onDictionaryUnregistered(DictionaryUnregisterPostEvent event) {
		if (!event.getDictionary().equals(this))
			return;

		EventManager.unregisterListener(this);
	}

	private void callEvent(Event event) {
		if (!silent)
			EventManager.callEvent(event);
	}
}
