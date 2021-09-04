---
title: Command line options
---

The basic usage of the ```cheerpjfy.py``` build script is:

```
./cheerpjfy.py application.jar
```

The script also accept various command line options to customize the JAR.JS compilation process.

### --help

Shows all the command line options

### -v

Shows the CheerpJ version and the recommend ```loader.js``` to use in deployment

### --deps=DEPSPATHS

List of ```:``` separated JARs that this JAR depends on. Please note that all the listed JAR paths should be either relative to the target JAR or absolute.

###   --pack-jar=PACKJAR

Generate a packed version of the input JAR. Debug information and all code are removed.

### -j NUMJOBS

Number of parallel compilation jobs

### --work-dir=WORKDIRPATH

A directory where all the JARs are unpacked. This is useful to speed up multiple compilations of the same JARs and to select a different disk when not enough free space is available in the temporary directory. Keep in mind that the directory passed to the option must be manually created _before_ the command is run.

### --natives=NATIVESPATH

Root of the native JS implementations for classes in this JAR file

### --stub-natives=NATIVESPATH

Generate stubs for all native methods from classes in this JAR. The parameter must be an existing directory, it will be populated with new JavaScript files for each class having native methods. **Note**: Existing files in the passed directory will be overwritten.
