package fr.pederobien.dictionary.impl;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import fr.pederobien.dictionary.impl.persistence.JarDictionaryPersistence;
import fr.pederobien.dictionary.interfaces.IDictionary;
import fr.pederobien.dictionary.interfaces.IDictionaryContext;
import fr.pederobien.dictionary.interfaces.IDictionaryParser;

public class JarDictionaryParser implements IDictionaryParser {
	private JarDictionaryPersistence persistence;

	/**
	 * Create a dictionary parser when the dictionary file is in a jar file. There are two cases when registering a dictionary from a
	 * jar in interface {@link IDictionaryContext}.</br>
	 * First, the dictionary is in the root folder : The dictionary <code>name</code> is the name of the dictionary.</br>
	 * Second, the dictionary is in another folder : The dictionary <code>name</code> begin with the name of the first folder
	 * following by separators.</br>
	 * </br>
	 * For example, if a dictionary named <b>French.xml</b> is in the folder <b>resources/dictionaries</b> in jar <b>plugin.jar</b>
	 * then to register the dictionary using method {@link IDictionaryContext#register(IDictionaryParser, Path)}, the dictionary name
	 * is <code>"resources/dictionary/French.xml"</code> and the value of the <code>path</code> is the path leading to the jar
	 * <b>plugin.jar</b>
	 * 
	 * @param internalPath The path in the jar to the dictionary.
	 */
	public JarDictionaryParser(String internalPath) {
		persistence = new JarDictionaryPersistence(internalPath);
	}

	@Override
	public IDictionary parse(Path path) throws FileNotFoundException {
		persistence.setPath(path);
		persistence.load(path.getFileName().toString());
		return persistence.get();
	}

	/**
	 * Set the name of the dictionary to parse. The given name should correspond to the path leading to the dictionary.
	 * 
	 * @param name The name of the dictionary to parse.
	 * 
	 * @see #JarMinecraftDictionaryParser(String)
	 */
	public IDictionaryParser setName(String name) {
		persistence.setName(name);
		return this;
	}

}
