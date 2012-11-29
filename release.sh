#!/bin/bash
owner=gagern
passwd=$1
repo=Cindy3D
release=$2
if [[ -z $release ]]; then
    release="$(git describe)"
fi
set -e

ant -Drelease="-${release}" -f Cindy3D/build.xml bundles

github-upload.py -u "${owner}" -p "${passwd}" -r "${repo}" \
-t application/zip \
-d "Cindy3D ${release} compiled for Windows" \
"Cindy3D/bundles/Cindy3D-${release}-windows-i586.zip"

github-upload.py -u "${owner}" -p "${passwd}" -r "${repo}" \
-t application/zip \
-d "Cindy3D ${release} compiled for OS X" \
"Cindy3D/bundles/Cindy3D-${release}-macosx-universal.zip"

github-upload.py -u "${owner}" -p "${passwd}" -r "${repo}" \
-t application/zip \
-d "Cindy3D ${release} compiled for Linux (32bit)" \
"Cindy3D/bundles/Cindy3D-${release}-linux-i586.zip"

github-upload.py -u "${owner}" -p "${passwd}" -r "${repo}" \
-t application/zip \
-d "Cindy3D ${release} compiled for Linux (64bit)" \
"Cindy3D/bundles/Cindy3D-${release}-linux-amd64.zip"
