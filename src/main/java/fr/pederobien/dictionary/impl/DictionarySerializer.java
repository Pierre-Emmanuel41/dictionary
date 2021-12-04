package fr.pederobien.dictionary.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessage;
import fr.pederobien.dictionary.interfaces.IMessageCode;
import fr.pederobien.persistence.impl.xml.AbstractXmlSerializer;

public class DictionarySerializer extends AbstractXmlSerializer<IDictionary> {

	public DictionarySerializer() {
		super(1.0);
	}

	@Override
	public boolean serialize(IDictionary element, Element root) {
		// Serializing locales
		Element localesElement = createElement(DictionaryXmlTag.LOCALES);
		for (Locale locale : element.getLocales()) {
			Element localeElement = createElement(DictionaryXmlTag.LOCALE);
			localeElement.appendChild(createTextNode(locale.toLanguageTag()));
			localesElement.appendChild(localeElement);
		}
		root.appendChild(localesElement);

		// Serializing messages
		Element messagesElement = createElement(DictionaryXmlTag.MESSAGES);
		for (IMessage message : element.getMessages()) {
			Element messageElement = createElement(DictionaryXmlTag.MESSAGE);

			Element messageCodeElement = createElement(DictionaryXmlTag.MESSAGE_CODE);
			messageCodeElement.appendChild(createTextNode(message.getCode().value()));
			messageElement.appendChild(messageCodeElement);

			Element messageValueElement = createElement(DictionaryXmlTag.MESSAGE_VALUE);
			messageValueElement.appendChild(createTextNode(message.getFormat()));
			messageElement.appendChild(messageValueElement);

			messagesElement.appendChild(messageElement);
		}
		root.appendChild(messagesElement);
		return true;
	}

	@Override
	public boolean deserialize(IDictionary element, Element root) {
		// Getting dictionary's locales
		NodeList locales = getElementsByTagName(root, DictionaryXmlTag.LOCALE);
		List<Locale> list = new ArrayList<Locale>();
		for (int i = 0; i < locales.getLength(); i++)
			list.add(Locale.forLanguageTag(locales.item(i).getChildNodes().item(0).getNodeValue()));

		element.getLocales().addAll(list);

		// Getting dictionary's messages
		NodeList messages = getElementsByTagName(root, DictionaryXmlTag.MESSAGE);
		for (int i = 0; i < messages.getLength(); i++) {
			Node code = getElementsByTagName((Element) messages.item(i), DictionaryXmlTag.MESSAGE_CODE).item(0);
			IMessageCode dictionaryCode = new MessageCode(code.getChildNodes().item(0).getNodeValue());
			Node message = getElementsByTagName((Element) messages.item(i), DictionaryXmlTag.MESSAGE_VALUE).item(0);
			String dictionaryMessage = message.getChildNodes().item(0).getNodeValue();
			element.register(new Message(dictionaryCode, dictionaryMessage));
		}
		return true;
	}
}
