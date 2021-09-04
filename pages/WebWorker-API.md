---
title: WebWorker API
---

CheerpJ supports running Java code in the background using WebWorkers. To use this functionality you need to include the ```loader.js``` script as usual. (e.g. ```https://cjrtnc.leaningtech.com/latest/loader.js```). The script exposes the APIs described below. It is allowed, but not necessary, to also use CheerpJ in the main thread at the same time.

All code in a Worker runs in parallel and asynchronously with the main thread. All the methods below return standard JavaScript ```Promise```s, and you can use ```.then(...)```, ```.catch(...)``` and ```async/await``` with them.

We recommend reading the [[Runtime API]] documentation before proceeding on this page.

## Creating and initializing a CheerpJ worker

The main entry point for CheerpJ workers is the ```CheerpJWorker``` JS interface. It is a normal JS object and it is possible to instantiate it multiple times.

```
var w = new CheerpJWorker();
w.cheerpjInit().then(function(e) { console.log("CheerpJWorker is ready"); });
```

This starts the WebWorker and initializes CheerpJ in that context. All workers need to be initialized in this way. As a general rule the ```CheerpJWorker``` exposes the same API as CheerpJ in the main thread. You can read details about the main thread API in the [[Runtime API]] page.

## Parameters and return values

WebWorkers do not share any memory with the main threads, and all interactions happen through messages. This imposes limitations on the type of data that can be passed around.

|Data type                                   |Limitations                                 |
|--------------------------------------------|--------------------------------------------|
|byte/short/char/int/float/double            |Fully supported in params and return values |
|byte[]/short[]/char[]/int[]/float[]/double[]|Fully supported in params and return values |
|JavaScript String                           |Supported in params, not return values      |
|Any Java object                             |Not supported in params or return values    |

Java arrays can either come from another Java method or they can be generated from a JS TypedArray using [cjTypedArrayToJava](Runtime-API#cjtypedarraytojava).

It is possible to move Java arrays from the main thread and others ```CheerpJWorker```s. Please note that Java arrays are not copied, but _transferred_ across contexts. This increases efficiency, but also means that the data is not available any more from the calling thread. If the data needs be preserved you must manually make a copy.

Java Strings, being Java objects, cannot be passed or returned. But JavaScript strings can be used as parameters and will be converted to Java Strings directly in the WebWorker context.

## CheerpJWorker.cheerpjRunMain

Runs a Java main method in the WebWorker context

```
w.cheerpjRunMain("ClassName", classPath, arg1, arg2).then(...)
```

## CheerpJWorker.cjCall

Executes a static Java method in the WebWorker contet

```
w.cjCall("ClassName", "methodName", arg1, arg2).then(...)
```

## CheerpJWorker.cjResolveCall

Uses Java reflection to resolve the method and returns an opaque handle to it. This handle can then be used multiple times without using Java reflection again.

```
w.cjResolveCall("ClassName", "methodName", /*Or array of parameter types if methodName is not unique*/null).then(function(resolvedMethod) { w.cjCall(resolvedMethod, arg1, arg2); ... });
```

# Java API for Workers

CheerpJ exposes a custom API to access this feature directly from Java code. The API is equivalent in terms of capabilities. This API is blocking, so to actually take advantage of concurrency between the main thread and Web Workers it is necessary to use this API from a Java thread.

```java
package com.leaningtech.cheerpj;

public class Worker
{
        // Initialize the Worker object, this method is blocking
        public Worker();
        // Runs the main method of the given class in the Web Worker context, this method is blocking
        public void runMain(String className, String classPath, Object... arg);
        // Runs the given static method in the Web Worker context, this method is blocking
        public Object call(String className, String methodName, Object... arg);
        // Same as "call". These should be used when primitives are expected.
        public int callI(String className, String methodName, Object... arg);
        public double callD(String className, String methodName, Object... arg);
        public long callL(String className, String methodName, Object... arg);
	// Returns an handle to a resolved method, this method is blocking
	public Object resolveCall(String className, String methodName, String[] types);
	// Run the given resolved method handle in the Web Worker context, this method is blocking
	public Object call(Object resolvedFunc, Object... arg);
	public int callI(Object resolvedFunc, Object... arg);
	public double callD(Object resolvedFunc, Object... arg);
	public long callL(Object resolvedFunc, Object... arg);
}
```

The Java version of the API is also extended to support ```long```s in parameters and returned values. Currently they are converted to native JS values when passed to Workers, so their range is limited to +/-2^52.

Example usage:

```java
import com.leaningtech.cheerpj.Worker;

public class WW
{
        public static void main(String[] args)
        {
                Worker w = new Worker();
                w.runMain("Hello", "");
        }
}
```

To build the class you need to add ```cheerpj-public.jar``` to the classpath

```
javac -cp cheerpj_install_dir/cheerpj-public.jar WW.java
```
