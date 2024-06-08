#!/bin/bash

mvn clean && bash ./release.sh && mvn install:install-file \
-Dfile=./target/jrouterfx-0.0.2.jar \
-DpomFile=./pom.xml \
-DgroupId=jrouterfx \
-DartifactId=jrouterfx \
-Dversion=0.0.2 \
-Dpackaging=jar \
-DcreateChecksum=true
