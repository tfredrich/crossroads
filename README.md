CrossRoads Internationalization (I18n) Features for Java
========================================================

A small, lightweight Java library for i18n (Internationalization) / L10n (localization) of text messages. Simply adds to the capabilities of Java message bundles.

This library wraps Java ResourceBundle instances and the MessageFormat utility class to create a single point of configuration of resource bundles and internationalization of string patterns.

So, why not just use Java I18n functionality of plain-old resource bundles? Great question! Here are the features CrossRoads supports over and above simple Java resource bundles:

1. Parameter substitution in resource bundle messages. CrossRoads will substitute strings, numbers and dates, formatting them correctly for the locale.
Java resource bundles simply return strings for a given token, with no parameter substitution or replaceable portion of the string.
2. Load message bundles from the file-system or class path. Loading bundles from the file system enables message bundle upgrades across server farms after the bundles come back from
the translator and after the software has already been released. Java resource bundles are meant to be deployed with your application (e.g. in the war file) and only loaded via the classpath,
making it hard to upgrade resource bundles on the fly.

Usage
=====

###Configuring CrossRoads



###Building Resource Bundle Files

CrossRoads uses the java.text.MessageFormat class to perform parameter substitution in messages. Therefore, the messages in the resource bundle file must conform to the MessageFormat
formatting rules. Some examples are:

```
s6 = At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.
s7 = The currency amount is {0,number,currency} this month.
s8 = The currency amount is {0,number,\u00A4#,###.###} this month.
```

###Localizing Messages


Maven Usage
===========
Stable:
```xml
		<dependency>
		    <groupId>com.strategicgains</groupId>
		    <artifactId>CrossRoads</artifactId>
		    <version>1.0</version>
		</dependency>
```
Development:
```xml
		<dependency>
		    <groupId>com.strategicgains</groupId>
		    <artifactId>CrossRoads</artifactId>
		    <version>1.1-SNAPSHOT</version>
		</dependency>
```
Or download the jar directly from: 
http://search.maven.org/#search%7Cga%7C1%7Ccom.strategicgains.crossroads

Note that to use the SNAPSHOT version, you must enable snapshots and a repository in your pom (or settings.xml) file as follows:
```xml
  <profiles>
    <profile>
       <id>allow-snapshots</id>
          <activation><activeByDefault>true</activeByDefault></activation>
       <repositories>
         <repository>
           <id>snapshots-repo</id>
           <url>https://oss.sonatype.org/content/repositories/snapshots</url>
           <releases><enabled>false</enabled></releases>
           <snapshots><enabled>true</enabled></snapshots>
         </repository>
       </repositories>
     </profile>
  </profiles>
```
