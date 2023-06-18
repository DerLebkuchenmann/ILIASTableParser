#!/bin/zsh

# Version einlesen
echo "Versionsnummer eingeben:"
read version
echo Version $version wird erstellt

jpackage -i ./../jar --name ILIASTableParser -d ./../builds/ --main-jar IliasTableParser.jar --main-class main.IliasTableParser --mac-package-name 'Table Parser' --app-version $version
