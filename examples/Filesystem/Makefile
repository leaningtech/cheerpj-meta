# Define variables
JAVAC = javac
JAR = jar
RM = rm -f
# File names
SOURCE1 = com/download/DownloadExample.java
SOURCE2 = com/filechooser/FileChooserDownloadExample.java
SOURCE3 = com/reader/FileReaderExample.java

MAIN_CLASS1 = com.download.DownloadExample
MAIN_CLASS2 = com.filechooser.FileChooserDownloadExample
MAIN_CLASS3 = com.reader.FileReaderExample
JAR_FILE1 = download.jar
JAR_FILE2 = filechooser.jar
JAR_FILE3 = filereader.jar
MANIFEST1 = com/download/manifest.txt
MANIFEST2 = com/filechooser/manifest.txt
MANIFEST3 = com/reader/manifest.txt
# Targets
all: compile jar
compile:
	$(JAVAC) -d . $(SOURCE1) $(SOURCE2) $(SOURCE3)
jar:
	$(JAR) cvfm $(JAR_FILE1) $(MANIFEST1) com/download/*.class
	$(JAR) cvfm $(JAR_FILE2) $(MANIFEST2) com/filechooser/*.class
	$(JAR) cvfm $(JAR_FILE3) $(MANIFEST3) com/reader/*.class
clean:
	$(RM) com/download/*.class
	$(RM) com/filechooser/*.class
	$(RM) com/reader/*.class
	$(RM) $(JAR_FILE1)
	$(RM) $(JAR_FILE2)
	$(RM) $(JAR_FILE3)