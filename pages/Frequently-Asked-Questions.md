---
title: CheerpJ FAQs
---

## What is CheerpJ?

CheerpJ is a solution for converting unmodified Java client applications into browser-based HTML5/JavaScript web applications. CheerpJ consists of an ahead-of-time compiler from Java bytecode to JavaScript, of a full Java runtime environment in JavaScript, and of a on-the-fly compiler for dynamic class generation, to be deployed alongside the application.

## What does the CheerpJ compiler do?

The CheerpJ compiler, based on LLVM/Clang, as well as on parts of [Cheerp](https://github.com/leaningtech/cheerp-meta), converts Java bytecode into JavaScript, without requiring the Java source. CheerpJ can be invoked on whole Java archives (.jar) or on single .class files, and generates a .jar.js (or .js) output.

## What parts of the Java SE runtime are supported?

The CheerpJ runtime environment is a full Java SE runtime in JavaScript. Differently from other technologies which provide a partial re-implementation written manually in JavaScript, we opted to convert the entire OpenJDK Java SE runtime to JavaScript using CheerpJ. The CheerpJ runtime is constituted of both JavaScript files and .jar archives. All CheerpJ runtime components are dynamically downloaded on demand by the application to minimise total download size. The CheerpJ runtime library is hosted by us on a dedicated CDN-backed domain, and we invite users to link to it in order to take advantage of caching and cross-application resource sharing.

## Can I use CheerpJ to convert my legacy Java application? I have no longer access to the source code.

Yes, you can convert any Java SE application with CheerpJ without touching the source code. You only need all the .jar archives of your application.

## Can I use CheerpJ to convert Java libraries and integrate them in my HTML5 application?

Yes. Java methods can be exposed to JavaScript with an asynchronous interface. A synchronous-looking construct is provided to minimise verbosity when multiple methods are invoked. 

## Can I call JavaScript libraries or web APIs from Java?

Yes, CheerpJ allows you to interoperate with any JavaScript or browser API.

## How does CheerpJ support reflection?

In order to support reflection, CheerpJ, similarly to a JVM, utilizes the metadata available in the original .jar file. A converted application, to be deployed on a web server, comprises both the converted .jar.js JavaScript files and the .jar archives. After having converted a .jar archive, it is possible to remove all the bytecode from them prior to deployment, in order to minimize download time (we provide a simple tool to do so). The combined size of the pruned .jar archive and the output JavaScript, after compression, is comparable to the original .jar. 

Optionally, .jar archives can be split into multiple segments (size to be defined at compile time) before being deployed. The application will only load the required segments at run time, thus further reducing download time.

## How does CheerpJ support dynamic class generation?

One component of CheerpJ is the CheerpJ on-the-fly compiler (cheerpJ.js), a minimalistic Java-bytecode-to-JavaScript compiler written in C++ and compiled to JavaScript. CheerpJ.js needs to be distributed alongside any converted Java application that makes use of dynamic constructs such as proxy classes, which get compiled on the fly at run time directly on the browser.

## What is the size of the output of CheerpJ

The combined size of the .jar to be served (pruned of its bytecode) and of the resulting JavaScript is similar to that of the original .jar archive. Anecdotally, an overhead of 20% seems to be the average.

## The size of the output is too big! Why doesn't CheerpJ remove "dead code"?

In Java there is no "dead code". Java supports reflection, so all code and classes can be potentially used at runtime. For this reason CheerpJ cannot automatically remove any code.

This said, depending on the application, it is often possible to remove a lot of code using ProGuard: an industry standard open source tool. CheerpJ provides support to automatically generate a ProGuard configuration file to make sure that classes used via reflection are not removed. For more information see: [here](Startup-time-optimization#use-proguard-to-remove-unused-code)

## Can JavaScript code produced by Cheerp be plugged into Node.js?

Yes, it should. However, this has not been one of our areas of focus so far.

## When compiling my application I see the message ```Failure compiling MyFile.class```, but cheerpjfy continues to execute with no errors

This means that it was not possible to use the new codegen. Cheerpj will use, for this class, the legacy codegen. This might happen for multiple classes in the same .jar, 

## My Java application needs to get data over the network, but I only get ```SocketException```s

In the browser environment it is not possible to use sockets to connect to arbitrary ports. As a special exception CheerpJ provides a custom HTTP/HTTPS handler (based on XHR) that can be used to get data over HTTP and use REST APIs. To enable this handler please set the property ```java.protocol.handler.pkgs=com.leaningtech.handlers``` during the ```cheerpjInit``` call, for example:

```cheerpjInit({javaProperties:["java.protocol.handler.pkgs=com.leaningtech.handlers"]});```

Please note that when using CheerpJ to run applets the custom handlers are enabled by default.

## When I run an application compiled with CheerpJ I see 404 errors in the browser console. What's going on?

Ignore those errors. CheerpJ provides a FileSystem implementation on top of HTTP. In this context it is absolutely ok for some files to be missing. CheerpJ will correctly interpret 404 errors as a file not found condition.

## My application compiled with CheerpJ does not work and I just see the "CheerpJ runtime ready" on the top of the screen. What's going on?

Many first time users get stuck at this point. The most common issues are:

* Opening the HTML page directly from disk: The URL in the browser should always start with http:// or https://, if it starts with file:// CheerpJ will not work. You need to use a local web server during testing.
* Forgetting to add "/app/" prefix to the JAR files used in Web page. CheerpJ implements a virtual fileystem with multiple mount points, the "/app/" prefix is required.
* More in general, you can use the "Network tab" of the developer tools in the browser to check if the JAR is being correctly downloaded. If the JAR is never downloaded, or a 404 error is returned, something is wrong with the JAR path. If you don't see anything in the "Network tab", please reload the page while keeping the developer tools open.
* When converting obfuscated JARs on MacOS and Windows there might be collisions between classes due to the case-insensitive nature of the filesystem. For example ```a.class``` and ```A.class``` will be considered the same. Always try to convert the JAR using a Linux machine before reporting a bug when converting obfuscated JARs.

## My application compiled with CheerpJ does not work and I see a cross origin error to a Google service in the console. What's going on?

The cross origin message you see happens as part of our automatic bug reporting system and it is not the real error. Something else is making your application crash, please report a bug here: https://github.com/leaningtech/cheerpj-meta/issues

## Can I play Old School RuneScape using CheerpJ or the CheerpJ Applet Runner extension?

Not yet. The main problem is that RuneScape requires low level network connections primitives (sockets) which are not provided by browsers at this time due to security concerns. In the future we might provide a paid add-on to the CheerpJ Applet Runner extension to support this use case via tunneling.

## What is the status of CheerpJ?

CheerpJ 2.2, was released on 14th May, 2021. CheerpJ is actively developed by [Leaning Technologies Ltd](https://leaningtech.com), a British-Dutch company focused on compile-to-JavaScript and compile-to-WebAssembly solutions.
