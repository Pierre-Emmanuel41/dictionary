package fr.pederobien.dictionary.impl;

import java.nio.file.Path;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.persistence.impl.Persistences;
import fr.pederobien.persistence.impl.xml.JarXmlPersistence;

public class JarXmlDictionaryParser implements IDictionaryParser {
	private JarXmlPersistence<IDictionary> persistence;

	/**
	 * Creates a parser for dictionaries saved with the XML format. This parser can parse XML files registered in a jar file.
	 * 
	 * @param jarPath The path leading to the jar file.
	 */
	public JarXmlDictionaryParser(Path jarPath) {
		this.persistence = Persistences.jarXmlPersistence(jarPath);
		persistence.register(persistence.adapt(new DictionarySerializer()));
	}

	/**
	 * {@inheritDoc}.</br>
	 * 
	 * @param path The path to the dictionary file in the jar file..
	 */
	@Override
	public IDictionary parse(String path) throws Exception {
		IDictionary dictionary = new Dictionary();
		persistence.deserialize(dictionary, path);
		return dictionary;
	}

	/**
	 * @return The persistence used to deserialize the content of a dictionary.
	 */
	public JarXmlPersistence<IDictionary> getPersistence() {
		return persistence;
	}
}
