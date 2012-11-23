---
layout: main
title: About Scriptish
keywords: scriptish, about
---

## About Cindy 3D ## {#about}

Cindy 3D is a Plug-In to the dynamic geometry software [Cinderella][].
It was created by [Matthias Reitinger][] and Jan Sommer in 2010 and 2011,
and is currently maintained by [Martin von Gagern][].
The 3D rendering is done through OpenGL using [JOGL][].
The project is licensed under the [BSD two-clause license][license].

 [Cinderella]: http://www.cinderella.de/
 [Matthias Reitinger]: https://github.com/reima
 [Martin von Gagern]: https://github.com/gagern
 [JOGL]: http://jogamp.org/jogl/www/
 [license]: https://github.com/gagern/Cindy3D/blob/jogl2/LICENSE.txt

## Installation ## {#install}

**Binary bundles of the software are available from the
[downloads page of the project][downloads].**
You can simply grab the bundle appropriate for your platform.
It will be a ZIP file containing a single directory called `Cindy3D`.
Simply copy that directory into the `Plugins` directory
of your Cinderella installation.

On Windows and Linux, you will see a `Plugins` directory
within the directory to which you installed Cinderella.
On OS X, you will have to view the content of the application bundle.
To do so, right-click on the application in finder and select the
“Show Package Contents” item of the context menu.
In the resulting directory structure, you will find
`Contents:Resources:app:Plugins` which is the directory
to which you want to copy the `Cindy3D` directory.

Cindy3D comes with its own version of JOGL,
and will locate that from the plugin directory.
If you have a different version of JOGL (and GlueGen) installed
into the Java runtime environment (JRE) of your machine,
these two might conflict, causing the plugin to fail.
In that case, please either remove the part from your JRE,
or install an appropriate version.

 [downloads]: https://github.com/gagern/Cindy3D/downloads

## Building from source ## {#build}

In order to build Cindy3D from source,
obtain a copy of the working tree from GitHub.
You can do so either by downloading a zip or tar file from this page here,
or by cloning the git repository.
The `Cindy3D` subdirectory of the working tree
(which will probably be called `Cindy3D` as well,
so you have two directories of the same name nested one inside the other)
contains a `build.xml` file for [Apache Ant][].
There are several supported targets, which you can list using `ant -p`.
The most important target is `bundles`.
So by running `ant -p bundles` in that directory,
you should be able to create a bunch of zip files
like the ones described above, located in a subdirectory called `bundles`.
The build process will download all required dependencies
from the Internet automatically.

 [Apache Ant]: http://ant.apache.org/

## Using the plugin ## {#use}

Documentation on how to use this plugin is currently under construction.

## Bugs and feature requests ## {#bugs}

Bugs and feature requests are handled through the
[issue tracker of this project][issues].
So if anything does not work as it should,
and you believe that the cause lies with this plugin,
please report this issue so we can have a look at it.
Also, if there is any feature at all you're missing,
feel free to report that either so we can at least keep track of whishes.

 [issues]: https://github.com/gagern/Cindy3D/issues
