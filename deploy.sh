mvn clean install
java -jar com.github.axiopisty.osgi.mp3spi.integration.standalone/target/com.github.axiopisty.osgi.mp3spi.integration.standalone-1.0.0-SNAPSHOT.jar
rm -rf felix/felix-cache
rm felix/bundle/com.github.axiopisty.osgi.mp3spi.integration.mp3player/target/com.github.axiopisty.osgi.mp3spi.integration.mp3player-1.0.0-SNAPSHOT.jar
rm felix/bundle/com.github.axiopisty.osgi.mp3spi.integration.bundle.activator/target/com.github.axiopisty.osgi.mp3spi.integration.bundle.activator-1.0.0-SNAPSHOT.jar
cp com.github.axiopisty.osgi.mp3spi.integration.mp3player/target/com.github.axiopisty.osgi.mp3spi.integration.mp3player-1.0.0-SNAPSHOT.jar felix/bundle/
cp com.github.axiopisty.osgi.mp3spi.integration.bundle.activator/target/com.github.axiopisty.osgi.mp3spi.integration.bundle.activator-1.0.0-SNAPSHOT.jar felix/bundle/
pushd felix
java -jar bin/felix.jar
popd
