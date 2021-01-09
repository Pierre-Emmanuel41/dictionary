package fr.pederobien.dictionary.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import fr.pederobien.dictionary.exceptions.MessageNotFoundException;
import fr.pederobien.dictionary.exceptions.NotEnoughArgumentsException;
import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessage;
import fr.pederobien.dictionary.interfaces.IMessageCode;
import fr.pederobien.dictionary.interfaces.IMessageEvent;

public class AbstractDictionary implements IDictionary {
	private List<Locale> locales;
	private Map<IMessageCode, IMessage> messages;
	private List<IMessage> messageValues;

	public AbstractDictionary(Locale... locales) {
		this.locales = Arrays.asList(locales);
		messages = new HashMap<IMessageCode, IMessage>();
		messageValues = new ArrayList<IMessage>();
	}

	@Override
	public List<Locale> getLocales() {
		return Collections.unmodifiableList(locales);
	}

	@Override
	public String getMessage(IMessageEvent event) {
		Iterator<Entry<IMessageCode, IMessage>> iterator = messages.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<IMessageCode, IMessage> entry = iterator.next();
			if (entry.getKey().equals(event.getCode())) {
				try {
					return entry.getValue().getMessage(event.getArgs());
				} catch (IndexOutOfBoundsException e) {
					throw new NotEnoughArgumentsException(event, this, entry.getValue());
				}
			}
		}

		// When any messages is registered for the code
		throw new MessageNotFoundException(event, this);
	}

	@Override
	public IDictionary register(IMessage message) {
		messages.put(message.getCode(), message);
		updateMessageValues();
		return this;
	}

	@Override
	public IDictionary unregister(IMessageCode code) {
		if (messages.remove(code) != null)
			updateMessageValues();
		return this;
	}

	@Override
	public List<IMessage> getMessages() {
		return Collections.unmodifiableList(messageValues);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{Dictionary={languages={", "}}}");
		for (Locale locale : locales)
			joiner.add(locale.toString());
		return joiner.toString();
	}

	private void updateMessageValues() {
		messageValues = messages.values().stream().collect(Collectors.toList());
	}
}
