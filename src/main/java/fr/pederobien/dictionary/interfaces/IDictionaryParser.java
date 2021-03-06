package fr.pederobien.dictionary.interfaces;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public interface IDictionaryParser {

	/**
	 * Parse the file associated to the given path to create a dictionary.
	 * 
	 * @param path The path to the dictionary file.
	 * @return A dictionary.
	 * 
	 * @throws FileNotFoundException If the path does not represent a file.
	 */
	IDictionary parse(Path path) throws FileNotFoundException;
}
