---
title: Runtime API
---

CheerpJ exposes a simple API to interact with a Java application converted to JavaScript. This API can be use to initialise CheerpJ, invoke Java methods, convert data and to enable/disable certain debugging features.

# Integrating an application converted with CheerpJ in a HTML page

A basic HTML file to load a CheerpJ application will look as follows:

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>CheerpJ test</title>
    <script src="https://cjrtnc.leaningtech.com/2.2/loader.js"></script>
  </head>
  <body>
  </body>
  <script>
      cheerpjInit();
      cheerpjCreateDisplay(800,600);
      cheerpjRunMain("AppClass", "/app/my_application_archive.jar:/app/my_dependency_archive.jar"); 
  </script>
</html>
```

# Loading the CheerpJ runtime

To load the most recent runtime, use the following link:
```html
<script src="https://cjrtnc.leaningtech.com/2.2/loader.js"></script>
```

More in general, you can use this line:
```html
<script src="https://cjrtnc.leaningtech.com/version/loader.js"></script>
```
where version is the specific runtime version you want to link to.

# cheerpjInit

```cheerpjInit``` must be called once in the page to setup and initialise the CheerpJ runtime environment. ```cheerpjInit``` accepts an optional object argument which can be used to set options. 

This method is to be invoked as follows: ```cheerpjInit({option:"value"});```

All the supported options are described below.

## ```clipboardMode```

By default CheerpJ supports an internal clipboard which is local to the Java application and is not integrated with the system clipboard. To change this behaviour you can initialize CheerpJ in the following way:

```
cheerpjInit({clipboardMode:"system"});
```

In ```system``` mode CheerpJ will share the clipboard with the system. Browsers enforce serious limitations on how the system clipboard can be accessed. In practice it is generally accessible when the ```Ctrl+C``` and ```Ctrl+V``` shortcuts are used (```Cmd+C``` and ```Cmd+V``` on MacOSX). Due to these limitations the UX when using ```clipboardMode:"system"``` is:

- ```Ctrl+C```/```Cmd+C```: the user has to press the shortcut twice to give CheerpJ access to the system clipboard. CheerpJ will block the execution while waiting for the second ```Ctrl+C```.
- ```Ctrl+V```/```Cmd+V```: this shortcut behaves normally, there is no difference with native execution.
- Menu based Copy/Paste: ```Ctrl+C```/```Ctrl+V``` are needed to access the clipboard. CheerpJ will block the execution while waiting the appropriate shortcut.

Commercial users adopting this integration have so far reported that this change in UX is not a significant burden for users. Moreover, in the future we plan to add an additional clipboard mode to take advantage of a new permission-based browser API which is currently being standardized. This future mode will provide native like user experience in all cases.

## ```disableErrorReporting```

CheerpJ automatically reports errors at runtime. Setting this option to ```true``` disables this system.

Example:

```
cheerpjInit({disableErrorReporting:true});
```

## ```disableLoadTimeReporting```

CheerpJ automatically get data about loading time. Setting this option to ```true``` disables this system.

Example:

```
cheerpjInit({disableLoadTimeReporting:true});
```

## ```enableInputMethods```

When this option is set to ```true``` CheerpJ will be able to receive text input from the input method framework of the platform. This is useful to support text input for languages such as Chinese, Japanese and Korean.

## ```enableProguardTrace```

When this option is set to ```true``` CheerpJ will automatically keep track of the classes actually used at runtime. Moreover, it will also keep track of classes which are accessed by reflection. After the application has been fully tested you can use the ```cjGetProguardConfiguration()``` function from the browser console to download a proguard configuration file (```cheerpj.pro```) that you can directly use with proguard to remove unneeded classes, methods and fields from the application, greatly reducing the download size and startup time.

## ```javaProperties```

An array of Java properties in the form ```"key=value"```. They will be defined on the System object (System properties). This option should be used if command line arguments in the form ```-Dkey=value``` are required when using native Java.

Example usage:

```
cheerpjInit({javaProperties:["prop1=value1","prop2=value2"]});
```

## ```listener```

An object containing callbacks that CheerpJ will use to report various information to the user. Currently only the ```jsLoadReason``` and ```preloadProgress``` callbacks are supported.

### ```jsLoadReason(scriptName, directReason, userReason)```

**Please note that enabling this listener may have significant performance impact and should not be used in production**

For each new .jar.js to be loaded, CheerpJ will call this function. This can be useful to debug the reason why some parts of the runtime are loaded, if unexpected.

* ```scriptName```: The name of the JS file being loaded
* ```directReason```: A CheerpJ uncompressed and mangled symbol. The method that most directly caused the load. This might not be very useful since it will most often be a ```java.lang.ClassLoader``` method. Can be ```(Internal)``` if it could not be detected.
* ```userReason```: A CheerpJ uncompressed and mangled symbol. The last non-runtime method in the stack before the loading. This should be more useful in understanding the user code that introduces the dependency. Can be ```(Internal)``` if it could not be detected.

Example usage:

```
var cheerpjListener = {jsLoadReason:function(scriptName, directReason, userReason){ ... }};
cheerpjInit({listener:cheerpjListener});
```

### ```preloadProgress(loadedFiles, totalFiles)```

This listener may be used in combination with [[ preloading support | Startup-time-optimization#Preload-resources ]] to monitor the loading of an application. The information provided is useful, for example, to display a loading/progress bar.

* ```loadedFiles```: How many files have been preloaded so far
* ```totalFiles```: How many files needs to be preloaded in total. This number may increment during the loading phase. CheerpJ has a prediction mechanism and may preload additional resources depending on application behaviour

Example usage:

```
function showPreloadProgress(loadedFiles, totalFiles)
{
    console.log("Percentage loaded "+(loadedFiles*100/totalFiles));
}
var cheerpjListener = {preloadProgress:showPreloadProgress};
cheerpjInit({listener:cheerpjListener});
```

## ```logCanvasUpdates```

When set to ```true```, it enables logs on the console about the display areas which are being updated. Useful to debug overdrawing.

Example:

```
cheerpjInit({logCanvasUpdates:true});
```

## ```overrideShortcuts```

Some applications needs to internally handle keyboard shortcuts which are also used by the browser, for example Ctrl+F. Most users expect the standard browser behavior for these shortcuts and CheerpJ does not, by default, override them in any way.

A CheerpJ-converted application can take control of additional shortcuts by providing a callback function as the ```overrideShortcuts``` options of ```cheerpjInit```. This callback receives the ```KeyboardEvent``` coming from the browser and should return ```true``` if the default browser behaviour should be prevented.

Whenever possible we recommend _not_ to use browser reserved shortcuts, to maintain a consistent user experience. In any case, the following limitations apply:

* Some shortcuts (Ctrl+T, Ctrl+N, Ctrl+W) are reserved by the browser and never received by the page itself. These _cannot_ be overridden
* Overriding (Ctrl+C/Ctrl+V) will prevent ```clipboardMode:"system"``` from working correctly.

Example:

```
cheerpjInit({overrideShortcuts:function(e)
{
    // Let Java handle Ctrl+F
    if(e.ctrlKey && e.keyCode == 70)
      return true;
    return false;
}});
```

## ```preloadResources```<a name="preloadResources"></a>

By using ```preloadResources```, you can provide CheerpJ with a list of runtime files which you know in advance will be  required for the specific application. The list should be given as a JavaScript array of strings.

Example:

```
cheerpjInit({preloadResources:["/lts/file1","/lt/file2"]});
```

See also [cjGetRuntimeResources](#cjGetRuntimeResources).

## ```status```

This option determines the level of verbosity of CheerpJ in reporting status updates.

* ```"default"```: Enables status reporting during initialization and short-lived "Loading..." messages every time new runtime code is being downloaded.
* ```"splash"```: Enabled status reporting only during initialization. There will be no feedback after the first window is shown on screen.
* ```"none"```: Disable all status reporting.

## ```appletParamFilter```

Some applications may need to have some parameter modified before getting those inside the applet.

Example:

```
cheerpjInit({appletParamFilter:function(name, value)
{
    if(name==="httpServer")
        return value.replace("http", "https");
    return value;
}});
```

# cheerpjCreateDisplay

This method will create the HTML element that will contain all Java windows. It is only required to run graphical applications.

```cheerpjCreateDisplay(width, height, /*optional*/parent)```

The ```width``` and ```height``` parameter represent the display area size in CSS pixels. It is also possible to specify a parent element if required, if a parent element is not specified the display area will be appended to the page ```body``` element. If a parent is specified it is also possible to pass ```-1``` to both ```width``` and ```height```, in that case the size will correspond to the parent size and it will also change dynamically if the parent is modified by either CSS changes or browser window resize.

# Running applications and JARs

**Warning**: CheerpJ does not support opening the HTML pages directly from disk. If the URL in your browser starts with ```file://```, CheerpJ will not run. You _must_ use a local Web server.

The most common way of starting an application is to use the ```cheerpjRunMain``` API, which lets you execute the static main method of a Java class in the classpath.

```
cheerpjRunMain("fully.qualified.class.name", "/app/my_application_archive.jar:/app/my_dependency_archive.jar", arg1, arg2);
```

Alternatively, if your JAR is designed to be executed with ```java -jar my_application_archive.jar```, you can use this simpler API.

```
cheerpjRunJar("/app/my_application_archive.jar", arg1, arg2);
```

Optionally, if your JAR also need additional dependencies, you can use.

```
cheerpjRunJarWithClasspath("/app/my_application_archive.jar", "/app/my_dependency_archive.jar", arg1, arg2);
```

In all cases the arguments should be JavaScript Strings.

# cjCall / cjNew

These functions make it possible to conveniently call Java code from JS. Java code is always run asynchronously, so the returned values are ```Promise```s. See below for details.

Calling Java constructors from JavaScript:
```JavaScript
/* Equivalent Java code: myClass object = com.my.Java.package.myClass(argument1)  */
var object = cjNew("com.my.Java.package.myClass", argument1);
```

Call static Java methods from JavaScript:
```JavaScript
/* Equivalent Java code: int returnVal = com.my.Java.package.myClass.method(argument1, argument2, argument3); */
var returnVal = cjCall("com.my.Java.package.myClass", "method", argument1, argument2, argument3);
```

Call Java methods from JavaScript:
```JavaScript
/* Equivalent Java code: int returnVal = object.method(argument1, argument2, argument3); */
var returnVal = cjCall(object, "method", argument1, argument2, argument3);
```

Both ```cjNew``` and ```cjCall``` return standard JavaScript ```Promise```s. They can be transparently used in other calls to ```cjNew/cjCall``` or you can use ```.then(...)``` and ```.catch(...)``` to access the resulting value and handle errors. It is also possible to use ```async/await``` (either natively or through a JS transpiler) to write sync-like code using ```cjNew/cjCall``` as async primitives.

## cjResolveCall / cjResolveNew

Using ```cjCall/cjNew``` is convenient, but under the hood Java reflection APIs are used, which may have a significant performance impact if used heavily. If you plan to use ```cjCall/cjNew``` many times it is convenient to go through reflection APIs only once by using the ```cjResolveCall/cjResolveNew``` API.

Examples:

```JavaScript
var promise1 = cjResolveCall("com.something.ClassName", "methodName", ["java.lang.String", "int", "double"]);
var promise2 = cjResolveNew("com.something.ClassName", ["java.lang.String", "int", "double"]);
```

```cjResolveCall``` supports both instance and static methods of classes. The third parameter (for both APIs) is an array of Java types and it is only required if the methodName is not unique in the class (i.e. it is overloaded). The third parameter can be omitted (be null) if the method name (or constructor) is unique. For example:

```JavaScript
var promise = cjResolveCall("com.something.ClassName", "uniqueMethodName", null);
```

```cjResolveCall/cjResolveNew``` are async, like most of CheerpJ's APIs. To get the actual result you can either use .then() or the async/await functionality of JS. For example:

```JavaScript
var resolvedMethod = await cjResolveCall("com.something.ClassName", "uniqueMethodName", null);
cjResolveCall("com.something.ClassName", "uniqueMethodName", null).then(function(resolvedMethod) { ... });
```

The returned value is an opaque handle to the desired method (or constructor), which can now be called an arbitrary number of times without going through Java reflection.

```JavaScript
cjCall(resolvedMethod, arg1, arg2, arg3);
cjNew(resolvedConstructor, arg1, arg2, arg3);
```

Alternatively resolvedMethod can also be used _directly as a function_, for example:

```JavaScript
resolvedMethod(arg1, arg2, arg3);
```

Please note that this convenient form can unfortunately only be used on the main thread, not on Workers. For more information see [[WebWorker API]]

# Data conversion

## cjStringJavaToJs(str) / cjStringJsToJava(str)

```JavaScript
var jsString = cjStringJavaToJs(javaString);
```
This converts a Java string into a JavaScript string. This operation implies a copy. 

```java
String javaString = cjStringJStoJava(jsString);
```

This converts a JavaScript string into a Java string. This operations also implies a copy. String parameters passed to ```cheerpjRunMain```, ```cjCall``` and ```cjNew``` are automatically converted so it is not necessary to use this methods in that case.

## cjTypedArrayToJava

Converts a TypedArray to a Java compatible primitive array. This operation implies a copy. Data is converted as follows:

| Typed Array | Java array |
| ----------- | ---------- |
| Int8Array   | byte[]     |
| Uint8Array  | byte[]     |
| Int16Array  | short[]    |
| Uint16Array | char[]     |
| Int32Array  | int[]      |
| Uint32Array | int[]      |
| Float32Array| float[]    |
| Float64Array| double[]   |

# Preloading APIs

## cjGetRuntimeResources<a name="cjGetRuntimeResources"></a>

Returns a JavaScript string representing the data that should be passed to [preloadResources](#preloadResources). It is a list of files that have been loaded from the runtime up to the time this function is called.
