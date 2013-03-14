#!/bin/bash

# This script will parse the list of apk on device and
# pull them in a directory named apk-dump/

# Create directory to dump apks
mkdir apk-dump

# dump apks :
# - List all apks (-f for the file absolute name)
# - Grep the apk absolute name
# - pull the apk in apk-dump
adb shell pm list packages -f -3 | \
	egrep -o "/.[^=]*" | \
	xargs -I pkg adb pull pkg apk-dump
