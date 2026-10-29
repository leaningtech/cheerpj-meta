<p align="center">
  <a href="https://cheerpj.com">
    <img src="assets/cheerpj-logo-dark.svg#gh-light-mode-only" alt="CheerpJ" width="280">
    <img src="assets/cheerpj-logo-light.svg#gh-dark-mode-only" alt="CheerpJ" width="280">
  </a>
</p>

<h3 align="center">Real Java in the browser.</h3>

<p align="center">
  A Java runtime built from OpenJDK and compiled to WebAssembly.<br>
  Build with Java, or run the applications you already have, from unmodified JARs.
</p>

<p align="center">
  <a href="https://cheerpj.com/docs/changelog"><img src="https://img.shields.io/badge/CheerpJ-4.3-ff7c00?labelColor=1e1e1e" alt="CheerpJ 4.3"></a>
  <a href="https://cheerpj.com/compatibility/"><img src="https://img.shields.io/badge/Java_8-8u492-e5e5d6?labelColor=1e1e1e" alt="Java 8: OpenJDK 8u492"></a>
  <a href="https://cheerpj.com/compatibility/"><img src="https://img.shields.io/badge/Java_11-11.0.31-e5e5d6?labelColor=1e1e1e" alt="Java 11: OpenJDK 11.0.31"></a>
  <a href="https://cheerpj.com/compatibility/"><img src="https://img.shields.io/badge/Java_17-17.0.19-e5e5d6?labelColor=1e1e1e" alt="Java 17: OpenJDK 17.0.19"></a>
  <a href="https://discord.leaningtech.com"><img src="https://img.shields.io/discord/988743885121548329?color=%237289DA&logo=discord&logoColor=ffffff&labelColor=1e1e1e" alt="Discord"></a>
</p>

<p align="center">
  <b><a href="https://cheerpj.com/docs/getting-started">Get started</a></b> ·
  <a href="https://cheerpj.com/docs/">Docs</a> ·
  <a href="#examples">Examples</a> ·
  <a href="https://javafiddle.leaningtech.com/">Playground</a> ·
  <a href="https://discord.leaningtech.com">Discord</a> ·
  <a href="https://github.com/leaningtech/cheerpj-meta/issues/new/choose">Report a bug</a>
</p>

```html
<script src="https://cjrtnc.leaningtech.com/4.3/loader.js"></script>
```

CheerpJ is a Java runtime built from OpenJDK and compiled to WebAssembly. It runs Java applications, applets, Java Web Start apps and libraries on Java 8, 11 and 17, inside the browser tab. There is no Java to install, no plug-in and no server component.

> [!NOTE]
> This repository is CheerpJ's public **issue tracker** and its **[examples](examples)**. CheerpJ itself is delivered from a CDN, so there is nothing to build here. The documentation is at [cheerpj.com/docs](https://cheerpj.com/docs/).

## Two ways to use CheerpJ

| I build software in Java | I have Java applications to run |
| --- | --- |
| Bring your Java application or library to the web from the same codebase: ship a web edition of your product, or call Java libraries from JavaScript. Load CheerpJ with one script tag and run your JARs as they are. | Applets, Java Web Start, Oracle Forms and EBS, or your own Java client. Install a [browser extension](https://cheerpj.com/extensions/) with no changes to the page, or put CheerpJ on your own server. Retire IE mode, the old browser or Citrix, and keep the application. |
| **[Build with CheerpJ →](https://cheerpj.com/cheerpj-core/)** | **[Run Java →](https://cheerpj.com/run-java/)** |

## Quick start

You need your JAR files, an HTML page and an HTTP server. No Java source code and no local Java install. The [getting started guides](https://cheerpj.com/docs/getting-started) cover each case step by step.

<details>
<summary><b>Run a Java UI in the browser</b></summary>

Put your JAR, or Oracle's [TextDemo.jar](https://docs.oracle.com/javase/tutorialJWS/samples/uiswing/TextDemoProject/TextDemo.jar) sample, in a folder with this `index.html`:

```html
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <script src="https://cjrtnc.leaningtech.com/4.3/loader.js"></script>
  </head>
  <body>
    <script type="module">
      await cheerpjInit();
      cheerpjCreateDisplay(800, 600);
      await cheerpjRunJar("/app/my_application.jar");
    </script>
  </body>
</html>
```

Serve the folder and open http://localhost:8080:

```sh
npx http-server -p 8080
```

- The script tag loads CheerpJ, and `cheerpjInit` starts it.
- `cheerpjCreateDisplay` creates the area that holds the Java windows.
- `cheerpjRunJar` runs the application, as `java -jar` does. For `java -cp … com.example.Main`, use [`cheerpjRunMain`](https://cheerpj.com/docs/getting-started/Java-app).
- `/app/` is the root of the web server the page comes from, as Java sees it. Forgetting the `/app/` prefix is the most common first mistake.

See [Run a Java application](https://cheerpj.com/docs/getting-started/Java-app).

</details>

<details>
<summary><b>Call a Java library from JavaScript</b></summary>

```js
await cheerpjInit();
const lib = await cheerpjRunLibrary("/app/pdfbox.jar");
const PDDocument = await lib.org.apache.pdfbox.pdmodel.PDDocument;

const doc = await new PDDocument();
console.log(await doc.getNumberOfPages());
```

See [Run a Java library](https://cheerpj.com/docs/getting-started/Java-library), [library mode](https://cheerpj.com/docs/guides/library-mode) and the [interoperability tutorial](https://cheerpj.com/docs/tutorials/interoperability-tutorial).

</details>

> [!IMPORTANT]
> Serve over HTTP(S), never `file://`: opening the page from disk doesn't work. The server must support HTTP Range requests; Python's `http.server` doesn't. See [server setup](https://cheerpj.com/docs/guides/basic-server-setup).

## Choose your path

| You have | Start here | Example |
| --- | --- | --- |
| A desktop application (Swing, AWT or console) | [Run a Java application](https://cheerpj.com/docs/getting-started/Java-app) | [SwingSet3](examples/SwingSet3), [Swing-Browser](examples/Swing-Browser) |
| A Java library to call from JavaScript or TypeScript | [Run a Java library](https://cheerpj.com/docs/getting-started/Java-library) | [Interoperability](examples/Interoperability) |
| An applet on a web page you control | [Run a Java applet](https://cheerpj.com/docs/getting-started/Java-applet) | [FoilSim](examples/FoilSim-Applet) |
| A Java Web Start (`.jnlp`) application | [Run a JNLP application](https://cheerpj.com/docs/getting-started/JNLP) | [Pitch](examples/Pitch-Applet) |
| Oracle Forms or Oracle E-Business Suite | [Oracle Forms](https://cheerpj.com/oracle-forms/) · [Oracle EBS](https://cheerpj.com/oracle-ebs/) | |
| An applet or Java Web Start app you use, on a site you don't control | [The CheerpJ browser extensions](https://cheerpj.com/extensions/), for Chrome and Edge | |

## How CheerpJ runs your Java

1. **Add CheerpJ to the page.** One script tag loads the JVM and the OpenJDK runtime, from our CDN or your own server. Or install a [browser extension](https://cheerpj.com/extensions/), with no changes to the page.
2. **Start the JVM and run your JAR.** CheerpJ reads your JARs and their dependencies as they are. An interpreter starts running the bytecode, and a JIT compiles the code that runs most to optimised JavaScript.
3. **It runs in the tab.** A virtualised window manager draws the Swing and AWT interface with HTML elements and canvases. Files and networking go through the browser, inside its sandbox.

More in [How CheerpJ works](https://cheerpj.com/how-cheerpj-works/) and the [architecture](https://cheerpj.com/docs/explanation/architecture) docs.

## Why CheerpJ

- **Real Java.** Built from OpenJDK: the OpenJDK class library itself, not a reimplementation. Java 8, 11 and 17. Your bytecode runs unmodified, including reflection, dynamic class loading, custom class loaders and obfuscated code. No source code is needed.
- **Runs what a Java client does.** Swing and AWT, files, networking and sockets, printing, audio, the clipboard and threads. Native methods can be [implemented in JavaScript](https://cheerpj.com/docs/guides/implementing-native-methods) or compiled to WebAssembly.
- **Runs on the user's machine.** Your application runs in the browser's sandbox on the user's own device, not on a server. CheerpJ is a set of static files: load it from our CDN, or host it on your own web server, including on networks with no internet access. A self-hosted deployment never needs to contact our servers.

## Compatibility

| | |
| --- | --- |
| **Java** | 8 (the default), 11 and 17. Choose with [`cheerpjInit({ version })`](https://cheerpj.com/docs/reference/cheerpjInit#version). The browser extensions run Java 8 |
| **Applications** | Desktop applications, applets, Java Web Start / JNLP, Oracle Forms and EBS, and libraries |
| **UI** | Swing and AWT |
| **Networking** | HTTP(S) through the browser, with its certificates and TLS policies. TCP and UDP sockets through [Tailscale](https://cheerpj.com/docs/guides/Networking) |
| **Graphics** | OpenGL through WebGL, for LWJGL 2 applications such as [Browsercraft](https://browsercraft.cheerpj.com/) |
| **Browsers** | Chrome, Edge, Firefox and Safari |
| **Not supported** | JavaFX (planned for 2027), LWJGL 3 (planned for December 2026), SWT, and pre-built native binaries (`.dll`, `.so`, `.dylib`) |

The full tables are on the [compatibility page](https://cheerpj.com/compatibility/).

## Examples

The [`examples`](examples) folder holds the projects used by the [tutorials](https://cheerpj.com/docs/tutorials).

| Example | What it shows | Tutorial |
| --- | --- | --- |
| [SwingSet3](examples/SwingSet3) | A full Swing application | [SwingSet3](https://cheerpj.com/docs/tutorials/swingset3) |
| [Swing-Browser](examples/Swing-Browser) | A Swing application with network access | [Java browser](https://cheerpj.com/docs/tutorials/java-browser) |
| [FoilSim-Applet](examples/FoilSim-Applet) | A Java applet on a web page | [Applet](https://cheerpj.com/docs/tutorials/applet) |
| [Pitch-Applet](examples/Pitch-Applet) | An applet launched from a JNLP file | [JNLP](https://cheerpj.com/docs/tutorials/jnlp) |
| [Server-Client](examples/Server-Client) | Two Java applications talking tab to tab | [Server and client](https://cheerpj.com/docs/tutorials/serverclient) |
| [Interoperability](examples/Interoperability) | Calling between Java and JavaScript | [Interoperability](https://cheerpj.com/docs/tutorials/interoperability-tutorial) |

## Try it without installing anything

- [JavaFiddle](https://javafiddle.leaningtech.com/): write and run Java in the browser.
- [Browsercraft](https://browsercraft.cheerpj.com/): the original Minecraft 1.2.5 client, running unmodified in a browser tab.
- [Showcase](https://labs.leaningtech.com/showcase?product=CheerpJ): projects built with CheerpJ, including FlatLaf, PhET, ImageJ.js and Logisim.

## Using a coding agent?

Install the [CheerpJ skill](https://github.com/leaningtech/skills). It covers converting `java` command lines, JARs, applets, JNLP, Oracle Forms and libraries, and the common errors:

```sh
npx skills add leaningtech/skills --skill cheerpj
```

In Claude Code, run `/plugin marketplace add leaningtech/skills`, then `/plugin install cheerpj@leaningtech-skills`. The [skills repo](https://github.com/leaningtech/skills#install) covers Codex and Gemini CLI. Or point your agent at [cheerpj.com/agents.md](https://cheerpj.com/agents.md) or [cheerpj.com/llms.txt](https://cheerpj.com/llms.txt).

## Getting help

| Where | Best for |
| --- | --- |
| [Discord](https://discord.leaningtech.com) | Questions, help getting started, and showing what you built. The CheerpJ team answers there |
| [GitHub issues](https://github.com/leaningtech/cheerpj-meta/issues/new/choose) | Bugs and errors in CheerpJ or the browser extensions |
| [Contact us](https://cheerpj.com/contact/) | Licensing, commercial support and enterprise deployments |

<details>
<summary><b>What to put in a bug report</b></summary>

- The CheerpJ version (from the loader URL), or the extension and its version
- The Java version you pass to `cheerpjInit`, if any
- Your browser, its version, and your OS
- A public URL or a small JAR that shows the problem
- The full browser console output, from page load to the error

Test without a local Java install and with other extensions disabled. Check the [FAQs](https://cheerpj.com/faqs/) first.

</details>

## Licensing

CheerpJ is free for personal, educational and hobby use, and for technical evaluation by organisations of any size. One-person companies and non-commercial open-source projects can use it free with a "Built with CheerpJ" credit.

Business use needs a licence. **Desktop licences** cover Java applications your organisation uses, such as Oracle Forms and EBS. **Application licences** cover software you build with CheerpJ for customers or the public. See [licensing and pricing](https://cheerpj.com/licensing/).

## Trusted in production

Over a million people ran a CheerpJ application in the last 12 months, and the CheerpJ browser extensions have more than 170,000 users (September 2026). CheerpJ is used by NASA, Ticketmaster, Moody's, ADP and [many other organisations](https://cheerpj.com/customers/).

---

<p align="center">
  <a href="https://cheerpj.com">cheerpj.com</a> ·
  <a href="https://discord.leaningtech.com">Discord</a> ·
  <a href="https://x.com/leaningtech">X</a> ·
  Made by <a href="https://leaningtech.com">Leaning Technologies</a>
</p>
