---
title: Getting started
---

This page will help you getting started with CheerpJ and converting your first Java application to JavaScript in no time.

To start, make sure to download the latest available version of CheerpJ [here](https://leaningtech.com/download-cheerpj/). Decompress the Cheerpj 2.3 archive anywhere, for example in ```~/cheerpj_2.3```.

**Important:** Converting an applet is documented at the bottom of this page.

# Converting a single JAR to a JAR.JS file

```cheerpjfy.py``` is an helper script that automatically takes care of unpacking, compiling and optimising a whole JAR archive. Using ```cheerpjfy.py``` is the recommended way of compiling applications and libraries using CheerpJ.

The basic usage is very simple:

```
~/cheerpj_2.3/cheerpjfy.py my_application_archive.jar
```

This command will generate a file called ```my_application_archive.jar.js```, which needs to be deployed in the same folder  of the original .JAR archive, and hosted on a web server. Instructions on how to serve the converted JavaScript on a web page are provided below.

**Important:** The files _must_ be accessed through a Web server. Trying to open the HTML page directly from the disk is not supported. The URL must look like ```http://127.0.0.1:8080/cheerpj_test.html```, if it looks like ```file://c/users/Test/cheerpj_test.html``` CheerpJ won't be able to start.

**Note to Windows users:** You will need to have python3 installed on the system. Python provides a launcher called ```py``` that will automatically detect and use the right version of python for a given script. To use ```cheerpjfy.py``` on Windows you need to prefix all the commands with ```py```, for example:

```
py c:\cheerpj_2.3\cheerpjfy.py application.jar
```

# Converting multiple JARs to JAR.JS's files

If your JAR has any dependencies in the form of further JAR archives, the ```cheerpjfy.py``` command line must be modified as follows:

```
~/cheerpj_2.3/cheerpjfy.py --deps my_dependency_archive.jar my_application_archive.jar
```

This command will generate ```my_application_archive.jar.js``` but **not** ```my_dependency_archive.jar.js```. Each archive should be compiled separately by invoking ```~/cheerpj_2.3/cheerpjfy.py my_dependency_archive.jar```.

It is in general safe to put the target JAR in the ```--deps``` list, although it is not required. If you have an application composed of many JARs you can do something like this:

```
for f in one.jar two.jar three.jar
do
    ~/cheerpj_2.3/cheerpjfy.py --deps one.jar:two.jar:three.jar $f
done
```

# Basic HTML page for testing a Java application

```html
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
      cheerpjRunMain("ChangeThisToYourClassName", "/app/my_application_archive.jar:/app/my_dependency_archive.jar"); 
  </script>
</html>
```
This page will initialize the CheerpJ system, create a graphical environment to contain all Java windows and then execute the ```main``` method of ```ChangeThisToYourClassName```. The second parameter of cheerpjRunMain is a ```:``` separated list of JARs where application classes can be found (the classpath). The ```/app/``` is a virtual file system mount point that reference the root of the web server this page is loaded from.

You can now serve this web page on a simple http server, such as the http-server utility.
```
http-server ~/cheerpj_2.3/
```

# Converting an applet

Applets can be run by Chrome users using the [CheerpJ Applet Runner](https://chrome.google.com/webstore/detail/cheerpj-applet-runner-bet/bbmolahhldcbngedljfadjlognfaaein) Chrome extension. You can also compile the applet ahead of time using the method described above.

To support all browsers, you can add the following tags to your page:

```
<script src="https://cjrtnc.leaningtech.com/2.3/loader.js"></script>
<script>cheerpjInit({enablePreciseAppletArchives:true});</script>
```

This should be sufficient to get the applet to run on any browser, with the pre-compiled JAR.JS's files deployed in the same directory of the the original JAR files. The ```cheerpjInit({enablePreciseAppletArchives:true});``` call can be done during page initialization.

To avoid potential conflicts with native Java we recommend replacing the original HTML tag with ```cheerpj-``` prefixed version. You should use ```<cheerpj-applet>```, ```<cheerpj-object>``` or ```<cheerpj-embed>``` depending on the original tag.

# Basic HTML page for testing a Java applet

```
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>CheerpJ applet test</title>
        <script src=" https://cjrtnc.leaningtech.com/2.3/loader.js"></script>
        <script>cheerpjInit({enablePreciseAppletArchives:true});</script>
    </head>
    <body>
        <cheerpj-applet archive="Example.jar" code="ExamplePath.ExampleApplet" height="900" width="900">
            <p>not able to load Java applet</p>
        </cheerpj-applet>
    </body>
</html>
```
