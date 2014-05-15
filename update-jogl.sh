#!/bin/bash

set -e
cd Cindy3D
rm -vf lib/3rd-party/{gluegen,jogl}*.jar
ant deps.jogl
