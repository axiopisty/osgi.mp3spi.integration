#!/usr/bin/env bash
set -x
mvn clean install
rm -rf felix/felix-cache
rm felix/bundle/com.github.axiopisty.osgi.mp3spi.integration.*
cp com.github.axiopisty.osgi.mp3spi.integration.mp3player/target/com.github.axiopisty.osgi.mp3spi.integration.mp3player-1.0.0-SNAPSHOT.jar felix/bundle/
cp com.github.axiopisty.osgi.mp3spi.integration.bundle.activator/target/com.github.axiopisty.osgi.mp3spi.integration.bundle.activator-1.0.0-SNAPSHOT.jar felix/bundle/
pushd felix
java -jar bin/felix.jar
popd
