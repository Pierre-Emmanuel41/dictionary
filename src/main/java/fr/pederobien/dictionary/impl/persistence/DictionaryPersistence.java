package fr.pederobien.dictionary.impl.persistence;

import java.io.IOException;
import java.nio.file.Path;

import org.w3c.dom.Document;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.persistence.impl.xml.AbstractXmlPersistence;

public class DictionaryPersistence extends AbstractXmlPersistence<IDictionary> {

	public DictionaryPersistence(Path path) {
		super(path);
		register(new DictionaryPersistenceLoader());
	}

	@Override
	protected Document createDoc(Object... objects) throws IOException {
		return parseFromFileName((String) objects[0]);
	}

	@Override
	public boolean save() {
		return false;
	}
}
