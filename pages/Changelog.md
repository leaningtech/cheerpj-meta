---
title: Changelog
---
version 2.3 - April 29th, 2022:

	* Improved legacy code generation
	* Improved clipboard support
	* Improved exceptions for I/O handling
	* Add proper support to Synchronized methods
	* Made async fetch the default for any file
	* Minor fixes to AWT components

Version 2.2 - May 14th, 2021:

	* Optimized exception handling
	* Extended JNI support
	* Improved file system emulation
	* Improved HTTP networking support
	* Improved AWT support
	* Improved audio support
	* Improved reflection support
	* Improved applet parsing robustness
	* Improved correctness of 32-bit floating point
	* Improved correctness of class private methods

Version 2.1 - April 3rd, 2020

	* Optimized exception handling
	* Optimized reflection calls

	* Improved AWT support
	* Improved robustness of the AOT compiler
	* Further improvements to native Java reflection support
	* Improved JavaScript interaction robustness
 	* Fixed support for --stub-natives in cheerpjfy.py
	* Code size improvements

Version 2.0 - February 4th, 2020

	* Introduction of WebAssembly runtime modules
	* Extended file system support to read, write and delete (IndexedDB backend)
	* New read-only filesystem in /str/ for Strings or binary data
	* Improved event handling, mouse interaction and focus
	* Extend support for charsets
	* Improved robustness to invalid classes often present in real-world JAR archives
	* Improved isolation of CheerpJ symbols
	* Improved support to multi-threading, in particular Thread.interrupt
	* Use the CFGStackifier algorithm to synthesize control flow in generated code
	* New API to receive preloading notification (allows to build custom progress bars)
	* Improved detection of Applets on HTML pages
	* Improved packaging for MacOS

Version 1.4 - April 14th, 2019:

	* Support Thread.interrupt
	* Improved keycode conversion
	* Improved AWT TextArea support
	* Improved support for modal dialogs
	* Fix support for non-ASCII Java fields
	* Fix resolving of default methods from interfaces
	* Minor fixes to reflection and code generation

Version 1.3 - November 28th, 2018:

	* Significantly faster font rendering
	* Full rework of keyboard event handling
	* Support unicode output on the console
	* Support applets in frames and iframes
	* Support multiple applets on a single page
	* Improved class loader correctness
	* Improved XHR-based HTTP(S) handler
	* Improved AWT rendering
	* Improved reflection support
	* Improved serialization support
	* Improved threading correctness
	* Improved focus support
	* Improved mouse wheel support

Version 1.2 - Sep 6th, 2018:

	* Support Copy / Paste to system clipboard
	* Support ProGuard config generation
	* Support window resizing
	* Support sealed packages
	* Optimized JNI calls
	* Improved correctness of mouse events
	* Improved reflection support
	* Improved XHR-based HTTP(S) handler
	* Improved threading cleanup

Version 1.1 - Jul 23rd, 2018:

	* Introduced parallel preloading of resources that are known to be needed before starting the application (useful in production);
	* New, faster API for JavaScript to Java interoperability based on standard promises (allowing async/await)
	* Exception handling when calling Java from JavaScript
	* Improved support for reflection
	* Improved code generation, particularly for low-level graphical routines
	* Improved HTTP/HTTPs connections
	* Support for time zones
	* Improved startup time
	* Support for custom cursors

Version 1.0 - Mar 20th, 2018:

	* Full rework of filesystem support
	* Support Drag-and-Drop
	* Support Java clipboard
	* Support multi-window applets
	* Support WebWorkers
	* Support printing on Edge
	* Support JPEG writing
	* Improved input handling
	* Improved applet support
	* Improved AWT support
	* Improved JNLP support
	* Improved HTTP support
	* Improved reflection support
	* Improved audio support
	* Reduced runtime size

Version Beta 3 - Nov 11th, 2017:

	* Improved printing support with browser native printing
	* Full rework of graphical architecture
	* Code size optimisations
	* Runtime performance optimisations
	* Reduced size of the runtime
	* Improvements to startup time
	
	* Support for splitting application .jar.js files in multiple packages
	* Improved on-the-fly compiler speed (dynamic class generation)
	* Inline well-known system methods
	* Inline small statically resolved methods

Version Beta 2 - Oct 13th, 2017:

	* Support for printing (print to .ps files)
	* Initial support for audio (javax audio)
	* Support for Internet Explorer 11 and Edge

	* Reduced code size - up to 20% size reduction on large applications
	* Improved runtime performance
	* Optimized 64-bit arithmetic
	* Optimized exception handling
	* Improved window dragging
	* Improved popup support
	* Improved filesystem support
	* Improved cjNew overload resolution
	* Improved thread scheduling
	* Improved rendering performance
	* Improved reflection performance
	* Fixed display area detection
	* Fixed keyboard focus handling


Version Beta 1 - Sep 27th, 2017:

	* First public release

