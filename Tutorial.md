# How to use this API

This simple tool allows you to easily create, load, register and/or unregister dictionaries in which messages for users are stored. First we need a <code>DictionaryContext</code>. This object allows to store dictionaries for different languages. Finally, a <code>Dictionary</code> stores messages for users according to their code. Thus, the dictionary context allows to know in which language the message must be translated and the code allows to know which is the associated translation.

# Dictionary files

This API provides you the possibility to store dictionaries in files, to parse and register them using a special <code>IDictionaryParser</code>. Its also possible to use the default dictionary parser furnished by this API but the file has to be well formed in order to be parsed successfully. If you want to use this default parser, the dictionary should looks like the following :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<dictionary>
	<version>1.0</version>
	<locales>
		<locale>en</locale>
		<locale>en-EN</locale>
		<locale>en-GB</locale>
		<locale>en-UK</locale>
		<locale>en-US</locale>
		<locale>en-CA</locale>
	</locales>
	<messages>
		<message>
			<code>CODE_1</code>
			<value>Static Message 1</value>
		</message>
		<message>
			<code>CODE_2</code>
			<value>Dynamic Message : %s</value>
		</message>
	</messages>
</dictionary>
```

Be careful, the value associated to the tag "locale" should correspond to the standard [IETF BCP 47](https://tools.ietf.org/html/bcp47) in order to be well interpreted by the java [Locale](https://docs.oracle.com/javase/7/docs/api/java/util/Locale.html) class (see forLanguageTag).

```java
public static void main(String[] args) {
	IDictionaryContext context = new DictionaryContext();
	try {
		context.register(Paths.get(Main.class.getResource("/Dictionary.xml").toURI()));
		System.out.println(center.getDictionaryContext().getDictionary(Locale.ENGLISH).get());
	} catch (FileNotFoundException | URISyntaxException e) {
		e.printStackTrace();
	}
}
```

For this example, I put the file Dictionary.xml in the folder src/main/resources. The output for this program is the following line :

```
{languages={en, en_US, en_GB}, messages={{code=Code_1, format=Static Message 1}, {code=Code_2, format=Dynamic Message : %s}}}
```

This means that the dictionary has been successfully created and parsed, and is associated to the following java Locale : en, en_EN, en_GB, en_UK etc.. are supported by this dictionary.

If the dictionary format is different from above, you can still provide your own dictionary parser :

```java
// The parser and the path has to be defined to avoid compilation errors
// parser: in charge of reading the file associated to the given path and creating the associated dictionary
// path: path leading to the dictionary file.
context.register(parser.parse(path));
```

There are by default two different parser implemented for XML format: <code>XmlDictionaryParser</code> used to parse xml files registered on the system and the <code>JarXmlDictionaryParser</code> used to parse xml files registered in a jar file.  

Until now, we can register dictionary from different type of file. But we cannot get access to the stored messages in order to display translated messages to the user.

# Translated messages

To get messages, wee need to use a <code>MessageEvent</code>. This event provides all needed informations for the dictionary context, the dictionary and the stored messages. But the MessageEvent constructor need an IMessageCode as parameter so we need a class or an enumeration to implement this interface. For me, the best implementation is an enumeration. It stores each code associated to user messages. Below is the interface implementation.

```java
public enum EMessageCode implements IMessageCode {
	CODE_1, CODE_2;

	@Override
	public String value() {
		return toString();
	}
}
```

Now, to get a translated message, we simply need to call the method <code>getMessage(IMessageEvent event)</code> and display the result :

```java
public static void main(String[] args) {
	IDictionaryContext context = DictionaryContext.getInstance();
	try {
		context.register(Paths.get(Main.class.getResource("/DictionaryName.xml").toURI()));
		System.out.println(center.getDictionaryContext().getMessage(new MessageEvent(Locale.ENGLISH, EMessageCode.CODE_1)));
	} catch (FileNotFoundException | URISyntaxException e) {
		e.printStackTrace();
	}
}
```

The output for this program is the following line :

```
Static Message 1
```

I bet you remark for the second message in the dictionary, I put a "%s". This is useful if your messages need parameters. In that case, you can provides them using the String array in the MessageEvent class :

```java
public static void main(String[] args) {
	IDictionaryContext context = DictionaryContext.getInstance();
	try {
		context.register(Paths.get(Main.class.getResource("/DictionaryName.xml").toURI()));
		System.out.println(center.getDictionaryContext().getMessage(new MessageEvent(Locale.ENGLISH, EMessageCode.CODE_2, "Hello world")));
	} catch (FileNotFoundException | URISyntaxException e) {
		e.printStackTrace();
	}
}
```

Here is the output :

```
Dynamic Message : Hello world
```

We can do exactly the same with a French dictionary :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<dictionary>
	<version>1.0</version>
	<locales>
		<locale>fr</locale>
		<locale>fr-FR</locale>
		<locale>fr-CA</locale>
	</locales>
	<messages>
		<message>
			<code>CODE_1</code>
			<value>Message statique 1</value>
		</message>
		<message>
			<code>CODE_2</code>
			<value>Message dynamique : %s</value>
		</message>
	</messages>
</dictionary>
```

And here is the main method :

``` java
public static void main(String[] args) {
	IDictionaryContext context = new DictionaryContext();
	try {
		context.register(Paths.get(Main.class.getResource("/English.xml").toURI()));
		context.register(Paths.get(Main.class.getResource("/French.xml").toURI()));
		System.out.println(center.getDictionaryContext().getMessage(new MessageEvent(Locale.ENGLISH, EMessageCode.CODE_1)));
		System.out.println(center.getDictionaryContext().getMessage(new MessageEvent(Locale.ENGLISH, EMessageCode.CODE_2, "Hello world")));
		System.out.println(center.getDictionaryContext().getMessage(new MessageEvent(Locale.FRENCH, EMessageCode.CODE_1)));
		System.out.println(center.getDictionaryContext().getMessage(new MessageEvent(Locale.FRENCH, EMessageCode.CODE_2, "Bonjour tout le monde")));
	} catch (FileNotFoundException | URISyntaxException e) {
		e.printStackTrace();
	}
}
```
