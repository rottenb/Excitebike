#!/bin/bash
javac -Xlint:unchecked -cp . -d . ../src/excitebike/*.java
#echo "create jar..."
#jar cfm excitebike.jar Manifest.txt ./excitebike/*.class ./res/*.png
