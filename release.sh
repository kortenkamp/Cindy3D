#!/bin/bash
owner=gagern
passwd=$1
repo=Cindy3D
release=$2
if [[ -z $release ]]; then
    release="$(git describe)"
fi
set -e

ant -f Cindy3D/build.xml clean
ant -Drelease="-${release}" -f Cindy3D/build.xml bundles

upload() {
    local arch=$1 platform=$2
    # github-upload can be found at https://github.com/gagern/github-tools
    github-upload.py -u "${owner}" -p "${passwd}" -r "${repo}" \
        -t application/zip \
        -d "Cindy3D ${release} compiled for ${platform}" \
        "Cindy3D/bundles/Cindy3D-${release}-${arch}.zip"
}

upload windows-i586 "Windows"
upload macosx-universal "OS X"
upload linux-i586 "Linux (32bit JVM)"
upload linux-amd64 "Linux (64bit JVM)"
