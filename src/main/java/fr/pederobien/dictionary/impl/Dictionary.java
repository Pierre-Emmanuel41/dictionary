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
import fr.pederobien.dictionary.interfaces.ICode;
import fr.pederobien.dictionary.interfaces.IMessageEvent;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class Dictionary implements IDictionary, IEventListener {
	private List<Locale> locales;
	private Map<ICode, IMessage> messages;

	public Dictionary(Locale... locales) {
		this.locales = new ArrayList<Locale>();
		for (Locale locale : locales)
			this.locales.add(locale);

		messages = new LinkedHashMap<ICode, IMessage>();
		EventManager.registerListener(this);
	}

	@Override
	public List<Locale> getLocales() {
		return locales;
	}

	@Override
	public String getMessage(IMessageEvent event) {
		Iterator<Entry<ICode, IMessage>> iterator = messages.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<ICode, IMessage> entry = iterator.next();
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
			throw new MessageRegisteredException(this, message.getCode());

		messages.put(message.getCode(), message);
		EventManager.callEvent(new MessageAddPostEvent(this, message));
		return this;
	}

	@Override
	public IDictionary unregister(ICode code) {
		IMessage message = messages.remove(code);
		if (message != null)
			EventManager.callEvent(new MessageRemovePostEvent(this, message));
		return this;
	}

	@Override
	public List<IMessage> getMessages() {
		return Collections.unmodifiableList(messages.values().stream().collect(Collectors.toList()));
	}

	@Override
	public String toString() {
		StringJoiner global = new StringJoiner(", ", "{", "}");
		StringJoiner localeJoiner = new StringJoiner(", ", "languages={", "}");
		for (Locale locale : locales)
			localeJoiner.add(locale.toString());
		global.add(localeJoiner.toString());
		StringJoiner messageJoiner = new StringJoiner(", ", "messages={", "}");
		for (Map.Entry<ICode, IMessage> entry : messages.entrySet())
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
}
