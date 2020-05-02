#!/bin/sh

cd ../app
chmod +x gradlew

echo "Compilation et lancement de la javadoc, merci de patienter"

./gradlew javadoc

echo "Javadoc générer"
echo "Lien vers la Javadoc : file://$(pwd |sed -E 's/ /%20/g')/build/docs/javadoc/index.html"
