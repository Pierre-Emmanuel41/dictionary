package fr.pederobien.dictionary.impl;

import java.util.Arrays;
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

import fr.pederobien.dictionary.exceptions.MessageNotFoundException;
import fr.pederobien.dictionary.exceptions.MessageRegisteredException;
import fr.pederobien.dictionary.exceptions.NotEnoughArgumentsException;
import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessage;
import fr.pederobien.dictionary.interfaces.IMessageCode;
import fr.pederobien.dictionary.interfaces.IMessageEvent;

public class Dictionary implements IDictionary {
	private List<Locale> locales;
	private Map<IMessageCode, IMessage> messages;

	public Dictionary(Locale... locales) {
		this.locales = Arrays.asList(locales);
		messages = new LinkedHashMap<IMessageCode, IMessage>();
	}

	@Override
	public List<Locale> getLocales() {
		return locales;
	}

	@Override
	public String getMessage(IMessageEvent event) {
		Iterator<Entry<IMessageCode, IMessage>> iterator = messages.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<IMessageCode, IMessage> entry = iterator.next();
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
		return this;
	}

	@Override
	public IDictionary unregister(IMessageCode code) {
		messages.remove(code);
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
		for (Map.Entry<IMessageCode, IMessage> entry : messages.entrySet())
			messageJoiner.add("{" + entry.getValue().toString() + "}");
		global.add(messageJoiner.toString());
		return global.toString();
	}
}
