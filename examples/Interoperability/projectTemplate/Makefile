# Define variables
JAVAC = javac
JAR = jar
RM = rm -f

# File names
SOURCE = Example.java
MAIN_CLASS = com.example.Example
JAR_FILE = example.jar
MANIFEST = manifest.txt

# Targets
all: compile jar 

compile:
	$(JAVAC) -d . com/example/$(SOURCE)

jar:
	$(JAR) cvfm $(JAR_FILE) com/example/$(MANIFEST) com/example/*.class

