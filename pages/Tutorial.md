---
title: Tutorial
---

CheerpJ is very easy to use, this tutorial will guide you step by step into compiling an unmodified JAR file to a JAR.JS file. We will also create a basic HTML file integrated with the CheerpJ loader to run the Java application in the browser.

# Download and install CheerpJ

Visit [our download page](https://leaningtech.com/download-cheerpj/) and download the CheerpJ archive for your platform. CheerpJ is available for Linux, Mac OS X and Windows.

CheerpJ is distributed as an archive for all the platforms, you can unpack the archive anywhere in the system. During the tutorial we will assume that CheerpJ has been unpacked in the home directory and its root is ```~/cheerpj_2.3/```. Please keep in mind to use a different path in the following commands if you have chosen a different position or you are using a different version of CheerpJ.

# Build or download the JAR file

CheerpJ compiles unmodified JAR files to JavaScript so that they can run in the browser. Java source code is not needed to use CheerpJ. If you are building your own application you should already have its JAR file. For this example we will download a basic Swing example. Download the [TextDemo.jar](https://docs.oracle.com/javase/tutorialJWS/samples/uiswing/TextDemoProject/TextDemo.jar) file into a new directory. Below we will assume that this new directory is ```~/cheerpj_tutorial/```

# Build the JAR.JS file

CheerpJ provides a convenient python program to convert whole JARs to JavaScript: ```cheerpjfy.py```. It supports several options for advanced users, but it's basic syntax is very simple. The following command will generate ```TextDemo.jar.js```

```
cd ~/cheerpj_tutorial/
~/cheerpj_2.3/cheerpjfy.py TextDemo.jar
```

**NOTE**: ```cheerpjfy.py``` it's a python3 program, you need to have python3 installed on your system.
**NOTE**: On windows you should prefix the command with the ```py``` launcher to use the correct version of python.

# Create an HTML page

Copy the following HTML code into ```~/cheerpj_tutorial/cheerpj_tutorial.html```

```
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>CheerpJ test</title>
    <script src="https://cjrtnc.leaningtech.com/2.3/loader.js"></script>
  </head>
  <body>
  </body>
  <script>
      cheerpjInit();
      cheerpjCreateDisplay(800,600);
      cheerpjRunJar("/app/TextDemo.jar"); 
  </script>
</html>
```

Let's break down what is going on:

* We first include the CheerpJ loader from our cloud runtime. (https://cjrtnc.leaningtech.com/2.3/loader.js). This file is the only script that needs to be loaded to use CheerpJ. CheerpJ will _automatically_ load all other files, including the ```TextDemo.jar.js``` we generated above.
* We initialize CheerpJ using the ```cheerpjInit()``` API. See [Runtime API](Runtime-API) for more information.
* We want to run a graphical application (i.e. a Swing or AWT application), so we need to initialize a _virtual display_ in the page. CheerpJ will render all Java windows into this display.
* We can now start the JAR file. CheerpJ will _automatically_ download the ```TextDemo.jar.js``` file as soon as the first application class is loaded

***NOTE***: The ```/app/``` prefix use in cheerpjRunJar is something that many first time users find confusing. CheerpJ implements a UNIX style virtual filesystem internally, with several _mount points_. For example

* ```/lt/``` -> CheerpJ cloud runtime
* ```/files/``` -> An IndexedDB based, persistent, file storage
* ```/app/``` -> An HTTP based filesystem, used to access JARs and data from your local server.

The ```/app/``` directory is virtual, it only exists inside of CheerpJ and it's needed to distinguish files from the local server from runtime files and files stored in the browser database. The ```/app/``` directory actually refers to the _root_ of your web server. So, assuming that your web server is available at ```http://127.0.0.1:8080/```, here are some example file mappings:

* ```/app/TextDemo.jar``` -> ```http://127.0.0.1:8080/TextDemo.jar```
* ```/app/subdirectory/data.txt``` -> ```http://127.0.0.1:8080/subdirectory/data.txt```

# Run the application in the browser

To test CheerpJ you _must_ use a local Web Server. Opening the ```cheerpj_tutorial.html``` page directly from the disk (for example, by double-clicking on it) is ***not supported***. This is a very common mistake for first time users.

***TIP***: There are many different Web servers that you can use, and all of them should work. For a quick test we recommand:

* Python2: ```python2 -m SimpleHTTPServer 8080``` 
* Python3: ```python3 -m http.server 8080```
* NPM (http-server): ```http-server -p 8080```

To run TextDemo.jar in the browser using CheerpJ, do the following

```
cd ~/cheerpj_tutorial/
python3 -m http.server 8080
```

Now open your favourite browser and enter the following URL ```http://127.0.0.1:8080/cheerpj_tutorial.html```. You will see the CheerpJ spinner during a brief loading phase. Then the Java window will appear and it will look identical to the native version.

# The end!

Congratulations, you have successfully compiled and run your first Java application using CheerpJ. For more information, please read our in-depth [Getting Started](Getting-Started) page.
