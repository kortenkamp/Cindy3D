#!/bin/bash
owner=gagern
repo=Cindy3D
release=$1
if [[ -z $release ]]; then
    release="$(git describe)"
fi
set -e

ant -f Cindy3D/build.xml clean
ant -Drelease="-${release}" -f Cindy3D/build.xml bundles

upload() {
    local arch=$1 platform=$2
    # github-upload can be found at https://github.com/gagern/github-tools
    github-upload.py -u "${owner}" -r "${repo}" \
        -m application/zip -t "${release}" -d "${platform}" \
        "Cindy3D/bundles/Cindy3D-${release}-${arch}.zip"
}

upload linux-amd64 "Linux (64bit JVM)"
upload linux-i586 "Linux (32bit JVM)"
upload macosx-universal "OS X"
upload windows-i586 "Windows"
