package io.github.harryprotist;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

public class Rune {

	public static Spell parseRune(Player act, Block center) {
	// this only worries about the orbitals and such
	// and generates a spell based on the values		

		double orbC = 0.0; // clockwise
		double orbO = 2.0; // outward
 
		World w	= center.getWorld();
 
		ArrayList<Integer> spell = new ArrayList<Integer>();	
		ArrayList<String> usedOrb = new ArrayList<String>();; // orbitals that have occured before to be imp.

	// this goes in a 4 part cycle, +x, -z, -x and +z	
	READ:
	while (true) {

		Integer[] acs = {0, 0, 0, 0}; // holds the argcs to determine orbitals

		for (int i = 0; i < 4; i++) {

			Location loc = center.getLocation();
			switch (i) {
				case 0: loc.add(orbO, 0.0, -orbC); break;
				case 1: loc.add(-orbC, 0.0, -orbO); break;
				case 2: loc.add(-orbO, 0.0, orbC); break;
				case 3: loc.add(orbC, 0.0, orbO); break;	
			}

			Block b = w.getBlockAt(loc);
			if (b.getType() == Material.AIR) break READ;
			else {
				spell.add(Spell.getFunction(b.getType()));
				usedOrb.add("0");
			}

			// now look for the args
			int argc = 0;

			ArrayList<Integer> argv = new ArrayList<Integer>();

			loc.add(0.0, 1.0, 0.0);
			while (w.getBlockAt(loc).getType() != Material.AIR) {
				argv.add(new Integer(Spell.getValue(w.getBlockAt(loc).getType()) ));
				loc.add(0.0, 1.0, 0.0);
				argc++;
				
				// make sure usedOrb has same indexes as spell
				usedOrb.add("0");
			} 
			acs[i] = new Integer(argc); 

			spell.add(new Integer(argc));
			spell.addAll(argv);			
			// make sure usedOrb has same indexes as spell
			usedOrb.add("0");

			//act.sendMessage("Read " + b.getType().getId() + " with " + argc + " args");
		}
		orbO += acs[0].compareTo(acs[2]);
		orbC += acs[1].compareTo(acs[3]);
		//act.sendMessage("Orbital is " + orbO + " out, and " + orbC + " counterclockwise.");

		String orbId = orbO + " " + orbC;
		if (usedOrb.contains(orbId) ) {
			spell.add(new Integer( -(usedOrb.indexOf(orbId) + 1)));	
			break;
		}
		usedOrb.remove(usedOrb.size() - 1);
		usedOrb.add(orbId);
	}

	return new Spell(spell);
	}
}

