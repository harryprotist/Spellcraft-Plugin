#!/bin/bash

cd /home/$USER/Documents/Programming/Java/My\ Plugins/Spellcraft

echo Compiling classes...
javac -cp bukkit-1.7.9-R0.2.jar \
	io/github/harryprotist/*.java \
	io/github/harryprotist/block/*.java \
|| exit

echo Compiling jar...
jar -cf Spellcraft.jar \
	io/github/harryprotist/*.class \
	io/github/harryprotist/block/*.class \
	plugin.yml \
|| exit

echo Installing plugin...
cp Spellcraft.jar ../BukkitTest/plugins
cp spell.txt ../BukkitTest/

echo Cleaning up...
rm io/github/harryprotist/*.class
rm io/github/harryprotist/block/*.class
