package fr.pederobien.dictionary.impl.persistence;

import java.nio.file.Path;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.persistence.impl.xml.AbstractXmlPersistence;

public class DictionaryPersistence extends AbstractXmlPersistence<IDictionary> {

	public DictionaryPersistence(Path path) {
		super(path);
		register(new DictionaryPersistenceLoader());
	}

	@Override
	public boolean save() {
		return false;
	}
}
