#!/usr/bin/python3

import docutils.core
import docutils.io
import os.path
import re
import shutil
import sys

settings_overrides={'output_encoding': 'unicode',
                    'embed_stylesheet': False,
                    }
output, pub = docutils.core.publish_programmatically(
    source_class=docutils.io.FileInput,
    source=None,
    source_path='CommandReference.rst',
    destination_class=docutils.io.StringOutput,
    destination=None,
    destination_path=None,
    reader=None, reader_name='standalone',
    parser=None, parser_name='restructuredtext',
    writer=None, writer_name='html',
    settings=None,
    settings_spec=None,
    settings_overrides=settings_overrides,
    config_section=None,
    enable_exit_status=False,
    )
m = re.search(r'<body[^>]*>(.*)</body>', output,
              re.IGNORECASE|re.DOTALL)
body = m.group(1)
m = re.search(r'<title[^>]*>(.*)</title>', output,
              re.IGNORECASE|re.DOTALL)
title = m.group(1)
m = re.search('<link\s+rel=(["\'])stylesheet\\1\s+href=(["\'])([^"\']*)\\2',
              output, re.IGNORECASE)
css = m.group(3)
cssn = os.path.join('..', 'stylesheets', os.path.basename(css))
os.chdir(sys.argv[1])
shutil.copyfile(css, cssn)
with open('CommandReference.html', 'w', encoding='utf-8') as f:
    f.write('''\
---
layout: main
title: Cindy 3D {title}
styles: [{cssn}, ../stylesheets/docutils-overrides.css]
topdir: ../
---
{body}'''.format_map(locals()))
