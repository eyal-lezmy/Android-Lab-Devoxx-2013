#! /bin/bash

# This script decompile an apk in Java

# unzip the apk
unzip org.adblockplus.android-1.apk -d apk-unzip

# Decompile .dex in .jar
d2j-dex2jar.sh --output ./apk-unzip/classes.jar ./apk-unzip/classes.dex

# Display .jar in .java
jd-gui ./apk-unzip/classes.jar
