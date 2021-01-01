package fr.pederobien.dictionary.impl.persistence;

import java.io.IOException;
import java.util.jar.JarFile;

import org.w3c.dom.Document;

import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.persistence.impl.xml.AbstractXmlPersistence;

public class JarDictionaryPersistence extends AbstractXmlPersistence<IDictionary> {
	private String name;

	public JarDictionaryPersistence(String name) {
		super(null);
		this.name = name;
		register(new DictionaryPersistenceLoader());
	}

	@Override
	public boolean save() {
		return false;
	}

	@Override
	protected Document createDoc(Object... objects) throws IOException {
		JarFile jar = null;
		try {
			jar = new JarFile(getPath().toFile());
			return parse(jar.getInputStream(jar.getJarEntry(name)));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (jar != null)
				try {
					jar.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	/**
	 * Set the name of the dictionary to parse;
	 * 
	 * @param name The name of the dictionary to launch.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
