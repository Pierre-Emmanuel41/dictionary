package fr.pederobien.dictionary.impl;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.persistence.impl.Persistences;
import fr.pederobien.persistence.impl.xml.XmlPersistence;

public class XmlDictionaryParser implements IDictionaryParser {
	private XmlPersistence<IDictionary> persistence;

	/**
	 * Creates a parser for dictionaries saved with the XML format. This parser can parse XML files registered on the system.
	 */
	public XmlDictionaryParser() {
		this.persistence = Persistences.xmlPersistence();
		persistence.register(persistence.adapt(new DictionarySerializer()));
	}

	@Override
	public IDictionary parse(String path) throws Exception {
		IDictionary dictionary = new Dictionary();
		persistence.deserialize(dictionary, path);
		return dictionary;
	}

	/**
	 * @return The persistence used to serialize or deserialize the content of a dictionary.
	 */
	public XmlPersistence<IDictionary> getPersistence() {
		return persistence;
	}
}
