#! /bin/bash

# This script retrieve the framework from an Android device and
# decompile it in every format possible

# Verify if api-level parameter is present
if [ $# -eq 0 ]
then
  echo "Please invoke this script with api-level argument : [1-17]"
  exit $E_NO_ARGS
fi

# Dump framework .odex from device
adb pull /system/framework/ ./odex

# Decompile .odex in .smali
baksmali --api-level $1 --deodex ./odex/framework.odex --bootclasspath-dir ./odex/ --output ./smali/

# Recompile .smali in .dex
mkdir dex
smali --api-level $1 --output ./dex/framework.dex ./smali/

# Decompile .dex in .jar
d2j-dex2jar.sh --output ./jar/framework.jar ./dex/framework.dex

# Display .jar in .java
jd-gui ./jar/framework.jar
