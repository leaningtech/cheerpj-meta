---
title: File System support
---

CheerpJ provides full filesystem support for converted Java applications. 

Read only and read/write filesystems are exposed in Java and can be used to read, write and manipulate files as normally when running on a JVM.

**Note**: CheerpJ provides access to a virtualized filesystem, which does not correspond to the local computer. Accessing local files from the browser it's forbidden for security reasons.

# File Systems in CheerpJ

CheerpJ implements three main filesystem concepts:
1. A read-only, HTTP-based filesystem
2. A read/write, IndexedDB-based, persistent filesystem
3. A read-only, memory based filesystem

CheerpJ filesystems are implemented as UNIX-style virtual filesystems with multiple mount points. The default mount points are defined as follows:
1. ```/app/``` → An HTTP-based read-only filesystem, used to access JARs and data from your local server.
2. ```/files/``` → An IndexedDB-based, persistent read-write file system
3. ```/lt/``` → Another HTTP-based read-only filesystem, pointing to the CheerpJ runtime
4. ```/str/``` → A read-only filesystem to easily share JavaScript Strings or binary data (an ```Uint8Array```) with Java code

# ```/app/``` mount point
The /app/ mount point corresponds to a virtual read-only, HTTP-based filesystem. ```/app/``` is used to access JAR files and data from your local server.

The ```/app/``` directory is virtual, and only exists inside of CheerpJ. It is required to distinguish files from the local server from runtime files and files stored in the browser database. 

The ```/app/``` directory refers to the root of your web server. So, assuming that your web server is available at ```http://127.0.0.1:8080/```, here are some example file mappings:

* ```/app/example.jar``` → ```http://127.0.0.1:8080/example.jar```
* ```/app/subdirectory/example.txt``` → ```http://127.0.0.1:8080/subdirectory/example.txt```

# ```/files/``` mount point
The ```/files/``` mount point corresponds to a virtual read-write, IndexedDB-based filesystem. ```/files/``` is used to store persistent data on the browser client.

The ```/files/``` directory is a virtual concept used by CheerpJ to store and refer to files. 

# ```/str/``` mount point
The ```/str/``` mount point is a simple read-only filesystem that can be populated from JavaScript to share data with Java code.

From JavaScript you can add files into the filesystem using the ```cheerpjAddStringFile``` API. Example:

```
cheerpjAddStringFile("/str/fileName.txt", "Some text in a JS String");
```

You can access this data from Java, for example:

```
import java.io.FileReader;
...

FileReader f = new FileReader("/str/fileName.txt")
...
```

The ```cheerpjAddStringFile``` API can be used with JavaScript ```String```s or ```Uint8Array```s. ```Uint8Array```s may be useful to provide binary data to the Java application, for example a user selected file coming from an HTML5 ```<input type="file">``` tag.
