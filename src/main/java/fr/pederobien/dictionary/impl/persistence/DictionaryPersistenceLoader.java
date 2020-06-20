package fr.pederobien.dictionary.impl.persistence;

import java.util.Locale;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.pederobien.dictionary.impl.AbstractDictionary;
import fr.pederobien.dictionary.impl.AbstractMessage;
import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IMessageCode;
import fr.pederobien.persistence.impl.xml.AbstractXmlPersistenceLoader;
import fr.pederobien.persistence.interfaces.xml.IXmlPersistenceLoader;

public class DictionaryPersistenceLoader extends AbstractXmlPersistenceLoader<IDictionary> {
	private IDictionary dictionary;

	protected DictionaryPersistenceLoader() {
		super(1.0);
	}

	@Override
	protected IDictionary create() {
		return null;
	}

	@Override
	public IXmlPersistenceLoader<IDictionary> load(Element root) {
		// Getting dictionary's name
		Node name = getElementsByTagName(root, DictionaryXmlTag.NAME).item(0);
		String dictionaryName = name.getChildNodes().item(0).getNodeValue();

		// Getting dictionary's locales
		NodeList locales = getElementsByTagName(root, DictionaryXmlTag.LOCALE);
		Locale[] dictionaryLocales = new Locale[locales.getLength()];
		for (int i = 0; i < locales.getLength(); i++)
			dictionaryLocales[i] = Locale.forLanguageTag(locales.item(i).getChildNodes().item(0).getNodeValue());

		dictionary = new AbstractDictionary(dictionaryName, dictionaryLocales);

		// Getting dictionary's messages
		NodeList messages = getElementsByTagName(root, DictionaryXmlTag.MESSAGE);
		for (int i = 0; i < messages.getLength(); i++) {
			Node code = getElementsByTagName((Element) messages.item(i), DictionaryXmlTag.CODE).item(0);
			IMessageCode dictionaryCode = new MessageCode(code.getChildNodes().item(0).getNodeValue());
			Node message = getElementsByTagName((Element) messages.item(i), DictionaryXmlTag.MESSAGE_VALUE).item(0);
			String dictionaryMessage = message.getChildNodes().item(0).getNodeValue();
			dictionary.register(new Message(dictionaryCode, dictionaryMessage));
		}
		return this;
	}

	@Override
	public IDictionary get() {
		return dictionary;
	}

	private class Message extends AbstractMessage {
		private String message;

		protected Message(IMessageCode code, String message) {
			super(code);
			this.message = message;
		}

		@Override
		public String getMessage(Object... args) {
			return String.format(message, args);
		}

		@Override
		public String toString() {
			return "Code=" + getCode().value() + ", message=" + message;
		}
	}

	private class MessageCode implements IMessageCode {
		private String value;

		protected MessageCode(String value) {
			this.value = value;
		}

		@Override
		public String value() {
			return value;
		}

		@Override
		public String toString() {
			return value();
		}

		@Override
		public int hashCode() {
			return value.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof IMessageCode))
				return false;
			IMessageCode other = (IMessageCode) obj;
			return value.equals(other.value());
		}
	}
}
