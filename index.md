---
layout: main
title: Cinderella 3D
---

## About Cindy 3D ## {#about}

Cindy 3D is a Plug-In to the dynamic geometry software [Cinderella][].
It was created by [Matthias Reitinger][] and Jan Sommer between 2010 and 2012,
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

[Instructions on building the plugin from source][readme]
are available from the [GitHub repository][repository].

 [readme]: https://github.com/gagern/Cindy3D/blob/jogl2/README.md
 [repository]: https://github.com/gagern/Cindy3D

## Using the plugin ## {#use}

Perhaps the most useful piece of documentation which is available
right now is the [command reference][refhtml],
which is also [available as pdf][refpdf].
It might be slightly outdated, but in general it should describe
all the commands the plugin does provide.
There also is a [project documentation][doc], available as pdf,
but it is incomplete in many places and already outdated in others.

We intend to update all of these documents eventually,
so hopefully we will be able to provide up-to-date and
easy-to-use documentation one day.

 [refhtml]: Reference/CommandReference.html
 [refpdf]: Reference/CommandReference.pdf
 [doc]: Documentation/Cindy3D_doc.pdf

## Bugs and feature requests ## {#bugs}

Bugs and feature requests are handled through the
[issue tracker of this project][issues].
So if anything does not work as it should,
and you believe that the cause lies with this plugin,
please report this issue so we can have a look at it.
Also, if there is any feature at all you're missing,
feel free to report that either so we can at least keep track of whishes.

 [issues]: https://github.com/gagern/Cindy3D/issues
