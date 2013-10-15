#!/bin/bash

cd /home/$USER/Documents/Programming/Java/My\ Plugins/Spellcraft

echo Compiling classes...
javac -cp bukkit-1.6.2-R1.0.jar io/github/harryprotist/*.java || exit

echo Compiling jar...
jar -cf Spellcraft.jar io/github/harryprotist/*.class plugin.yml || exit

echo Installing plugin...
cp Spellcraft.jar ../BukkitTest/plugins

echo Cleaning up...
rm io/github/harryprotist/*.class
