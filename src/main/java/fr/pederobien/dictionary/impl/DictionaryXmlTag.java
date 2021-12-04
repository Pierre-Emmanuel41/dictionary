package fr.pederobien.dictionary.impl;

public enum DictionaryXmlTag {
	LOCALES("locales"), LOCALE("locale"), MESSAGES("messages"), MESSAGE("message"), MESSAGE_CODE("code"), MESSAGE_VALUE("value");

	private String name;

	private DictionaryXmlTag(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
