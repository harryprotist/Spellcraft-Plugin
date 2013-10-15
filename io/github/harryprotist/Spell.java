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
import java.io.*;

// this class is the real meat of the plugin
public class Spell {

	private static Map<Integer, Integer> Functions = new HashMap<Integer, Integer>();
	private static Map<Integer, Integer> Values = new HashMap<Integer, Integer>();
	public static boolean Load() {
		
		BufferedReader file;
		try { file = new BufferedReader(new FileReader("spell.txt") ); }
		catch (FileNotFoundException f) { return false; }
		String line;
		
		try {
		while ( (line = file.readLine()) != null ) {

			String[] data = line.split(" ");
			if (data.length != 3) return false;

			Integer key;
			Integer fun;
			Integer val;

			try {
				key = new Integer(data[0]);
				fun = new Integer(data[1]);
				val = new Integer(data[2]);	

			} catch (NumberFormatException e) {
				return false;
			}

			// the values are good, let's add 'em
			Functions.put	(key, fun);
			Values.put	(key, val);
		}
		}
		catch (IOException e) {
			return true;
		}		

		return true;
	}
	public static Integer getFunction(Integer key) {
		return Functions.get(key);
	}
	public static Integer getValue(Integer key) {
		return Values.get(key);
	}

	public static String dumpMaps() {
		String ret = "Functions:\n";

		for (Object s : Functions.keySet().toArray() ) {
			Integer S = (Integer)s;
			ret += S.toString() + "\t" + getFunction(S).toString() + "\n";
		}
		
		ret += "\nValues:\n";
		for (Object s : Values.keySet().toArray() ) {
			Integer S = (Integer)s;
			ret += S.toString() + "\t" + getValue(S).toString() + "\n";
		}	

		return ret;
	}

	// script is a list of functions
	private ArrayList<Integer> Script;
	public Spell(ArrayList<Integer> script) {
		Script = script;
	}

	public int Excecute(int manaSource, Player caster) {

		int manaUsed = 0; 	
		ArrayList<Integer> mem;

		World w = caster.getWorld();

		Location loc = caster.getLocation();
		// harmonic sounds cooler than 'next'
		//int harC = 0; // harmonic clockwise
		//int harO = 1; // harmonic outward

		// oh god, here goes...	
		SPELL:
		for (int i = 0; i < Script.size();) {

			int cmd = Script.get(i).intValue();
			int argc = 0;
			
			if (cmd > 0) {
				i++;
				argc = Script.get(i).intValue();		
			}

			//caster.sendMessage(cmd + ", with " + argc + " args");
			//caster.sendMessage(getFunction(cmd).toString() + " is the corresponding function");

			if (cmd < 0) {
				
				i = -cmd;
				continue;
			}

			if (getFunction(cmd) == null) break;
			if (getValue(cmd) == null) break;

			switch (getFunction(cmd).intValue()) {
				case 1:
				// Sets target to where you're looking
					if (argc != 1) break SPELL;
					loc = caster.getTargetBlock(null, Script.get(i + 1).intValue()).getLocation();

					manaUsed += (Script.get(i + 1).intValue() / 2);
					if (manaSource - manaUsed <= 0) return 0;
				break;
				case 2:
				// Sets current block on fire, if it's air
					if (argc != 0) break SPELL;

					manaUsed += 64;
					if (manaSource - manaUsed <= 0) return 0;

					if (w.getBlockAt(loc).getType() == Material.AIR) {
						w.getBlockAt(loc).setType(Material.FIRE);
					}
				break;	
				case 3:
				// Moves location down 1
					if (argc != 0) break SPELL;

					loc.add(0.0, -1.0, 0.0);

					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;
				break;
				case 4:
				// Moves location up 1
					if (argc != 0) break SPELL;

					loc.add(0.0, 1.0, 0.0);

					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;
				break;
				case 5:
				// Moves location +1 x
					if (argc != 0) break SPELL;

					loc.add(1.0, 0.0, 0.0);

					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;
				break;
				case 6:
				// Moves location -1 x
					if (argc != 0) break SPELL;

					loc.add(-1.0, 0.0, 0.0);

					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;
				break;	
				case 7:
				// Moves location +1 z
					if (argc != 0) break SPELL;

					loc.add(0.0, 0.0, 1.0);

					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;
				break;
				case 8:
				// Moves location -1 z
					if (argc != 0) break SPELL;

					loc.add(0.0, 0.0, -1.0);

					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;
				break;
				case 9:
				// Takes an arg for explosion power
					if (argc != 1) break SPELL;
					
					manaUsed += Math.pow(Script.get(i + 1).intValue(), 3);
					//caster.sendMessage(manaUsed + " is apparently too much");
					if (manaSource - manaUsed <= 0) return 0;

					w.createExplosion(loc, Script.get(i + 1).intValue());
				break;
				case 10:
				// same as before, only a FIERY explosion
					if (argc != 1) break SPELL;

					manaUsed += Math.pow(2 * Script.get(i + 1).intValue(), 3);
					if (manaSource - manaUsed <= 0) return 0;

					w.createExplosion(loc, Script.get(i + 1).intValue(), true);
				break;
				case 11:
				// create a block, only it uses a ton of mana, only makes blocks in air
					if (argc != 1) break SPELL;
					
					Integer val = getValue(Script.get(i + 1).intValue());
					if (val == null) val = new Integer(10);

					manaUsed += Math.pow(val, 2);
					if (manaSource - manaUsed <= 0) return 0;

					if (w.getBlockAt(loc).getType() == Material.AIR) {
						w.getBlockAt(loc).setTypeId(Script.get(i + 1).intValue());
					}
				break;
				case 12:
				// breaks a block at loc
					if (argc > 1) break SPELL;	
					
					if ((argc > 1 && w.getBlockAt(loc).getType().getId() == Script.get(i + 1).intValue()) ||
						argc == 0) {
						
						w.getBlockAt(loc).breakNaturally();

						manaUsed += w.getBlockAt(loc).getType().getMaxDurability();
					}
					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;
				break;
				default:
					break SPELL;
			}
			i += argc + 1;

			//caster.sendMessage("Done with " + i + " out of " + Script.size() );
		}

		return manaSource - manaUsed;	
	}
}
































