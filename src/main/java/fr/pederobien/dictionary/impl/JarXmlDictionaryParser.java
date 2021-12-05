package fr.pederobien.dictionary.impl;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.persistence.impl.Persistences;
import fr.pederobien.persistence.impl.xml.JarXmlPersistence;

public class JarXmlDictionaryParser implements IDictionaryParser {
	private JarXmlPersistence<IDictionary> persistence;

	public JarXmlDictionaryParser(Path jarPath) {
		this.persistence = Persistences.jarXmlPersistence(jarPath);
		persistence.register(persistence.adapt(new DictionarySerializer()));
	}

	@Override
	public IDictionary parse(Path path) throws FileNotFoundException {
		IDictionary dictionary = new Dictionary();
		persistence.deserialize(dictionary, path.toString());
		return dictionary;
	}

	/**
	 * @return The persistence used to deserialize the content of a dictionary.
	 */
	public JarXmlPersistence<IDictionary> getPersistence() {
		return persistence;
	}
}
