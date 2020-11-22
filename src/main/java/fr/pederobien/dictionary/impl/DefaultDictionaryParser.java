package fr.pederobien.dictionary.impl;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import fr.pederobien.dictionary.impl.persistence.DictionaryPersistence;
import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.persistence.interfaces.IPersistence;

public class DefaultDictionaryParser implements IDictionaryParser {
	private IPersistence<IDictionary> persistence;

	public DefaultDictionaryParser() {
		persistence = new DictionaryPersistence(null);
	}

	@Override
	public IDictionary parse(Path path) throws FileNotFoundException {
		persistence.setPath(path.getParent());
		String fileName = path.getFileName().toString();
		persistence.load(fileName.substring(0, fileName.lastIndexOf('.')));
		return persistence.get();
	}
}
