# dictionary

Nowadays, it is very nice when an application can support several languages in order to be used all around the world. Each company/developers has its own way to do so but, most of the time, the displayed messages are hardcoded and it is not easy to add another language. This project is a simple tool that provides an easy way to register/load dictionary for multi-language application. The goal is to use code that represent a message on user screen. Using this, the developer does'nt know which message is displayed, but only the code associated to message.

# Register as maven dependency

It is easy to register this project as dependency for your own project. To do so, you need to download this project.
First, you need to download this project on your machine.

The easiest way to do so is to use the following git command line 

```git
git clone https://github.com/Pierre-Emmanuel41/dictionary.git --recursive
```
Indeed, this project depends on the project [persistence](https://github.com/Pierre-Emmanuel41/persistence) and need to be downloaded on your machine to avoid compilation errors.

Then, you need to run the following maven command line twice : 

```maven
mvn clean package install

```
The first one in the project folder : <code>/dictionary</code> and the second one in the folder <code>/dictionary/persistence</code>. This will create the archive of the two projects in your .m2 folder.

Finally, in the pom.xml of your project, you have to add the following lines :

```xml
<dependency>
	<groupId>fr.pederobien</groupId>
	<artifactId>dictionary</artifactId>
	<version>1.0</version>
</dependency>
```
You can now use features provided by this api in you project.

To see how you can use thoses features, please have a look to [This tutorial](https://github.com/Pierre-Emmanuel41/dictionary/blob/master/Tutorial.md)
