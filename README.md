# Cindy 3D – bringing 3D to Cinderella

This is the source code for Cindy 3D,
a 3D plugin for the [Cinderella][] interactive geometry software.
It was created by [Matthias Reitinger][] and Jan Sommer in 2010 and 2011,
and is currently maintained by [Martin von Gagern][].
The 3D rendering is done through OpenGL using [JOGL][].
The project is licensed under the [BSD two-clause license][license].

 [Cinderella]: http://www.cinderella.de/
 [Matthias Reitinger]: https://github.com/reima
 [Martin von Gagern]: https://github.com/gagern
 [JOGL]: http://jogamp.org/jogl/www/
 [license]: https://github.com/gagern/Cindy3D/blob/jogl2/LICENSE.txt

## For end users

For end users, the [homepage of the Cindy 3D project][homepage]
will likely be more useful than this source distribution.
Compiled versions of this plugin, ready for installation,
are available from the [downloads page][downloads].
You need the source code only if you want to (or have to) compile
the source code yourself.

 [homepage]: http://gagern.github.com/Cindy3D/
 [downloads]: https://github.com/gagern/Cindy3D/downloads

## How to compile the code

In order to build Cindy3D from source,
obtain a copy of the working tree from GitHub.
You can do so either by downloading a [zip][] or [tar][] file,
or by cloning the [git repository][].
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

 [zip]: https://github.com/gagern/Cindy3D/zipball/jogl2
 [tar]: https://github.com/gagern/Cindy3D/tarball/jogl2
 [git repository]: https://github.com/gagern/Cindy3D
 [Apache Ant]: http://ant.apache.org/

## Building the documentation

At the top level of the working tree, there is a make file.
Calling GNU make with no arguments will build
the bundles as described in the preceding paragraph,
but also create some documentation in various formats.
Additional tools might be required to perform this step.
If you only want to build some documentation,
have a closer look at the various make files involved.
