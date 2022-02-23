---
title: DOM and JavaScript interoperability
---

CheerpJ allows users to interact with the browser DOM directly from Java, without overhead. To achieve this we provide an additional jar (```cheerpj-dom.jar```) in the CheerpJ downloadable archive.

This JAR provides declarations for all of the relevant Java interfaces and classes. In particular you will find them wrapped in the ```com.leaningtech.client``` package, for example the ```Document``` interface of the browser (documented [here](https://developer.mozilla.org/en-US/docs/Web/API/Document)) becomes ```com.leaningtech.client.Document``` with CheerpJ.

The ```com.leaningtech.client.Global``` is a representation of the global namespace in the browser context. It only contains static methods and fields

## Basic example

```java
import com.leaningtech.client.Document;
import com.leaningtech.client.Element;
import com.leaningtech.client.Global;
import com.leaningtech.client.JSString;

public class DomExample
{
        public static void main(String[] a)
        {
                // Retrieve the global document object, it comes from the global namespace of the browser.
                Document d = Global.document;
                // Retries a known element from the page using it's id
                // NOTE: Java Strings must be converted to JavaScript string before being used
                Element e = d.getElementById(Global.JSString("existingNode"));
                // Currently, setter/getters must be used to access properties
                e.set_textContent(Global.JSString("sometext"));
                Element newDiv = Global.document.createElement(Global.JSString("p"));
                // Initialize the new element with text derived from the previous one
                newDiv.set_textContent(e.get_textContent().substring(3).toUpperCase())
                // Add it to the document body
                Global.document.get_body().appendChild(newDiv);
                JSString divContent = newDiv.get_textContent();
                // This logs directly to the browser console
                Global.console.log(divContent);
        }
}
```

## Using Strings

It's important to keep in mind that Java Strings are not JavaScript Strings. To avoid confusion, in CheerpJ the ```JSString``` name is used for the JS version. The static ```Global.JSString``` utility function can be used to create ```JSString```s from Java ```String```s. If a ```JSString``` needs to be used many times it could be useful to cache it. Similarly the ```Global.JavaString``` function can be used to convert back from ```JSString``` to normal Java ```String```.

## Calling JS methods

The ```Global``` class provides a few static methods that can be used to call arbitrary JS functions in the global scope.

```java
public static Object jsCall(String funcName, Object... arg);
public static int jsCallI(String funcName, Object... arg);
public static double jsCallD(String funcName, Object... arg);
public static JSString jsCallS(String funcName, Object... arg);
```

The various methods behave the same, with the only difference being the expected return type. As JavaScript functions are untyped CheerpJ does not have enough information to auto-box the returned values, so you need to use the right return type on the call site. Java Strings parameters will be automatically converted to JavaScript Strings.

## Building the code

Assuming the example above is contained in ```DomExample.java```, you need to first build the program using the standard ```javac``` compiler, create a JAR and then create the JAR.JS from it using CheerpJ. In both steps the ```cheerpj-dom.jar``` must be explicitly added to the command line as a dependency.

```
# The CHEERPJ_INSTALL_PATH is assumed to be set to the location where the CheerpJ archive has been installed
javac -cp $CHEERPJ_INSTALL_PATH/cheerpj-dom.jar DomExample.java
jar cvf domexample.jar DomExample.class
$CHEERPJ_INSTALL_PATH/cheerpjfy.py --deps $CHEERPJ_INSTALL_PATH/cheerpj-dom.jar domexample.jar
```

See the [Getting Started](Getting-Started) page to see how to run the compiled Java code in an HTML page.
