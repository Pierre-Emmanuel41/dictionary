package fr.pederobien.dictionary.impl.persistence;

public enum DictionaryXmlTag {
	NAME("name"), LOCALES("locales"), LOCALE("locale"), MESSAGES("messages"), MESSAGE("message"), CODE("code"), MESSAGE_VALUE("value");

	private String name;

	private DictionaryXmlTag(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
