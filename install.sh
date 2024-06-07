#!/bin/bash

mvn clean && bash ./release.sh && bash ./docs.sh && mvn install:install-file \
-Dfile=./target/jrouterfx-0.0.1.jar \
-DpomFile=./pom.xml \
-Dsources=./target/jrouterfx-0.0.1-sources.jar \
-Djavadoc=./target/jrouterfx-0.0.1-javadoc.jar \
-DgroupId=io.github.andy030124 \
-DartifactId=jrouterfx \
-Dversion=0.0.1 \
-Dpackaging=jar \
-DcreateChecksum=true
