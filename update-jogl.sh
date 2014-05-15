#!/bin/bash

urlbase=http://jogamp.org/deployment/jogamp-current/jar
files=( )
for i in Cindy3D/lib/3rd-party/{gluegen,jogl}*.jar; do
    [[ -e "${i}.tmp" ]] && rm "${i}.tmp"
    wget -O "${i}.tmp" "${urlbase}/${i##*/}" || exit $?
    files+=( "${i}" )
done
for i in "${files[@]}"; do
    mv "${i}.tmp" "${i}"
done
