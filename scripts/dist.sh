#!/bin/sh

distPath="dist"
appPath="app"
mainClass="magic_book.Main"
zipName="MagicBook"

cd ..

baseDir="$(pwd)"
distDir="$baseDir/$distPath"
appDir="$baseDir/$appPath"
gradleDistDir="$baseDir/$appPath/build/distributions"

if [ -d "$distDir" ]; then
	rm -rf "$distDir"
fi

mkdir "$distDir"

cd "$appDir"
chmod +x gradlew

echo "Compilation et lancement du projet, merci de patienter"

./gradlew build

if [ $? -ne 0 ]; then
	exit 1
fi

echo "Copie des fichiers"
cd $gradleDistDir

if [ -d "$gradleDistDir/$zipName" ]; then
	rm -rf "$gradleDistDir/$zipName"
fi

unzip "$gradleDistDir/$zipName".zip 1>/dev/null

cp "$gradleDistDir/$zipName"/lib/* "$distDir"
cp "$appDir"/livres "$distDir" -r

cd "$distDir"
echo "#!/bin/sh" >> start.sh
echo "" >> start.sh
echo "java -cp \"gson-2.8.6.jar:MagicBook.jar\" $mainClass" >> start.sh

echo "Jar généré ici : $baseDir/dist"
