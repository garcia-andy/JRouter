#!/bin/bash

mvn clean && bash ./release.sh && mvn install:install-file \
-Dfile=./target/jrouter-0.1.0.jar \
-DpomFile=./pom.xml \
-DgroupId=jrouter \
-DartifactId=jrouter \
-Dversion=0.1.0 \
-Dpackaging=jar \
-DcreateChecksum=true
