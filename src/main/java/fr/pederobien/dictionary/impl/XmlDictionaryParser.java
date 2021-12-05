package fr.pederobien.dictionary.impl;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.persistence.impl.Persistences;
import fr.pederobien.persistence.impl.xml.XmlPersistence;

public class XmlDictionaryParser implements IDictionaryParser {
	private XmlPersistence<IDictionary> persistence;

	public XmlDictionaryParser() {
		this.persistence = Persistences.xmlPersistence();
		persistence.register(persistence.adapt(new DictionarySerializer()));
	}

	@Override
	public IDictionary parse(Path path) throws FileNotFoundException {
		IDictionary dictionary = new Dictionary();
		persistence.deserialize(dictionary, path.toString());
		return dictionary;
	}

	/**
	 * @return The persistence used to serialize or deserialize the content of a dictionary.
	 */
	public XmlPersistence<IDictionary> getPersistence() {
		return persistence;
	}
}
