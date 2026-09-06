#!/usr/bin/env sh
set -eu
test -f app/src/main/AndroidManifest.xml
test -f app/src/main/assets/index.html
test -f app/src/main/assets/app.js
test -f server/src/index.js
grep -q 'LEANBACK_LAUNCHER' app/src/main/AndroidManifest.xml
grep -q '/organize' server/src/index.js
grep -q 'Organize with AI' app/src/main/assets/index.html
echo 'Source verification passed.'
