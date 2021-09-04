---
title: Implementing native methods
---

With CheerpJ, it is possible to implement Java 'native' methods (that would normally be implemented in C/C++ or other AOT-compiled language) in JavaScript, similarly to what would be done in regular Java using the Java Native Interface (JNI).

# Java Native Methods

Take as an example the following Java class

```java
public class SomeClass {
    public static void someStaticMethod() {
        ...
    }
    public float someInstanceMethod() {
        ...
    }
    public native int someNativeMethod();
}
```

Java will search for the implementation of ```someNativeMethod``` using the JNI. 

When compiling this class with CheerpJ, a JavaScript implementation of this method will need to be provided. Implementing native Java methods in JavaScript can be useful to use browser functionalities that are not currently exposed at the Java level.

# Java Native Methods in CheerpJ

Implementing native methods is simply a matter of adding a JavaScript function in the global scope with a correctly mangled signature. 

Since this is a rather involved process, the ```cheerpjfy.py``` script provides functionality to simplify the process by using the ```--stub-natives=destinationDir``` command line option.

Assume the previous class has been compiled and packaged in ```some.jar```, to generate a directory tree for JS native code you can do:

```
mkdir native/
cheerpjfy.py --stub-natives=native/ some.jar
```

This will generate a tree of directories under the ```native``` folder, which will replicate the Java package structure. Each class with at least one native method will generate a ```ClassName_native.js``` stub file with ready to be implemented.

Once all have been implemented, native methods can be packaged with the compiled code using the following command:

```
cheerpjfy.py --natives=native/ some.jar
```

## ```CHEERPJ_COMPRESS(x)``` macro

CheerpJ uses a compression scheme to encode mangled signatures. The ```CHEERPJ_COMPRESS(x)``` macro will encode the argument in parenthesis following such scheme. This macro is used automatically by the ```cheerpjfy.py --stub-natives=``` command, but can also be used manually.

## ```CHEERPJ_SET_CLASS(x)``` macro

Set the current internal class for resolving fields when using ```CHEERPJ_FIELD``` and ```CHEERPJ_STATIC_FIELD``` macros.

## ```CHEERPJ_FIELD(x)``` and ```CHEERPJ_STATIC_FIELD(x)``` macro

The compiler replaces this macro with the encoded field name, it assumes the current class has been set by ```CHEERPJ_SET_CLASS```.
