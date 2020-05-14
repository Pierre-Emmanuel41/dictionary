package fr.pederobien.dictionary.interfaces;

import java.nio.file.Path;

public interface IDictionaryParser {

	/**
	 * Parse the file associated to the given path to create a dictionary.
	 * 
	 * @param path The path to the dictionary file.
	 * @return A dictionary.
	 */
	IDictionary parse(Path path);
}
