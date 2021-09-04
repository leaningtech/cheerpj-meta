---
title: Startup time optimization
---

This page is a collection of different steps to reduce the startup time of a Java application converted with CheerpJ. 

# Overview

Traditionally, users had to have Java preinstalled on their computer in order to run Java applications and applets. CheerpJ converts Java to HTML5/JavaScript, allowing to run applications and applets on browser without users having to install any additional dependency on their computer. Similarly to their JVM counterparts, applications converted to JavaScript with CheerpJ require runtime components to be loaded during execution. In CheerpJ, runtime components are JavaScript modules that are loaded on demand, only if required. 

The CheerpJ runtime is highly optimised to minimise the total download size of an 'average' application, totalling 10-20MB of data for a typical download (as a point of comparison, the approximate size of the Java runtime installer is over 60MB). All downloaded components of CheerpJ are cached by the browser, which reduces the download time in subsequent executions of a same application.

This page provides a list of recommendations to reduce the one-time download size, and the resulting application first startup time.

# Gzip compression of JavaScript on server

In our experience it is not uncommon to see that most of the loading time is spent downloading the main ```jar.js``` file for the application (i.e. not from the runtime). **It is critical that the Web server on which your application is deployed has gzip compression enabled for the application's JavaScript files**. 

If you are self-hosting the CheerpJ runtime (most likely you will not), please make sure to enable compression on these components as well. The CheerpJ cloud runtime has compression enabled by default.

# Use ProGuard to remove unused code

ProGuard is an industry standard open-source tool to optimize and obfuscate Java bytecode. ProGuard, by itself, can automatically trace the classes used from the application entry point and automatically remove unused classes, methods and field. This can enable a very significant reduction of download size and startup time, especially with applications using multiple third party libraries.

Since Java allows reflection at runtime, ProGuard may end up removing code which is actually used, causing unexpected errors. To help in this scenario CheerpJ supports a special option:

```cheerpjInit({enableProguardTrace:true})```

When initialized with this option CheerpJ will keep track of the classes used at runtime, including classes which are accessed via reflection. Follow these steps to safely optimize an application using proguard.

1. Build a single ```uber-JAR``` by merging the application and dependencies
2. Convert the new JAR to JS using ```cheerpjfy.py```
3. Run the application using CheerpJ with the ```enableProguardTrace:true``` option
4. Fully test the application, making sure that all possible scenarios are executed and all needed classes are loaded
5. From the browser console call ```cjGetProguardConfiguration()```, a proguard configuration file (```cheerpj.pro```) will be automatically generated and downloaded
6. Run ```proguard``` on the ```uber-JAR``` using the generated configuration file to generate a smaller JAR with only the needed classes. ```proguard -dontwarn -injars uber.jar -outjars uber-stripped.jar -libraryjars ~/cheerpj_1.2/rt.jar @cheerpj.pro```
7. Convert this new JAR using ```cheerpjfy.py```

# Reduce the size of the JAR file

```cheerpjfy.py``` supports a command line option (```--pack-jar```) to generate a minimised JAR for deployment. 

This smaller JAR is stripped of all original Java bytecode and can no longer be used to run the application on the JVM. The JAR is however necessary for CheerpJ to support Java reflection. 

More details are available [here](https://github.com/leaningtech/cheerpj-meta/wiki/Command-Line-Options#--pack-jarpackjar)

# Preload resources

CheerpJ cannot predict which runtime resources will be required by an arbitrary application. CheerpJ runtime resources are therefore loaded on demand, one after the other, depending on the requirements of the application at run time. 

To take advantage of parallel downloads, and reduce download and startup time of a specific application in production, CheerpJ allows to pre-specify a list of resources (CheerpJ runtime modules) to be loaded at startup.

This list of resources is to be specified manually when starting the CheerpJ environment in an HTML page. We also provide a simple profiling tool to automatically record and output a list of used resources during the execution of an application.

By combining the use of this profiler together with the preloader, one can highly optimise the initial download and startup time of a converted application. Taking advantage of this is a simple 2-step process: 

1. Run the application normally using CheerpJ. After the application is loaded, open the JavaScript console of the browser (e.g. Ctrl+Shift+I on many browsers), and type:

```cjGetRuntimeResources()```

The result will look like this: 

```["/lts/file1","/lt/file2"]``` 

The JavaScript console may enclose the string between quotes (```"```), which you should ignore. See [here](https://github.com/leaningtech/cheerpj-meta/wiki/Runtime-API#cjgetruntimeresources) for more information.

2. Modify the CheerpJ integration to enable preloading. You will only need to change the ```cheerpjInit``` call, to pass the ```preloadResources``` option. For example: 

```cheerpjInit({preloadResources:["/lts/file1","/lt/file2"]});``` 

See [here](https://github.com/leaningtech/cheerpj-meta/wiki/Runtime-API#preloadresources) for more information.

When preloading is enabled CheerpJ will be able to download multiple resources in parallel with the execution of the program. This will greatly improve loading time.
