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
	public String dumpScript() {

		if (Script == null) return "";	
		
		String ret = "";
		for (Integer i : Script) {

			if (i.intValue() < 0) {
				ret += (new Integer(i.intValue() - 4)).toString() + ", ";
				continue;
			}

			ret += i.toString() + ", ";	
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

		// maps the ID of a block to the value assigned to it
		Map<Integer, Integer> mem = new HashMap<Integer, Integer>();

		World w = caster.getWorld();

		Location loc = caster.getLocation();

		caster.sendMessage(dumpScript());

		SPELL:
		for (int i = 0; i < Script.size();) {

			int cmd = Script.get(i).intValue();
			int argc = 0;
			
			ArrayList<Integer> argv = new ArrayList<Integer>();
			if (cmd > 0) {

				i++;
				argc = Script.get(i).intValue();		

				caster.sendMessage(cmd + " " + argc);

				for (int j = 1; j <= argc; j++) {

					if (mem.containsKey(Script.get(i + j) ) ) {
						argv.add(mem.get(Script.get(i + j) ) );
					} else {
						argv.add(Script.get(i + j));
					}
				}

			}
			

			//caster.sendMessage(cmd + ", with " + argc + " args");
			//caster.sendMessage(getFunction(cmd).toString() + " is the corresponding function");

			if (cmd < 0) {
				
				caster.sendMessage((cmd - 4) + " ");
				i = -(cmd) + 4;
				continue SPELL;
			}

			if (getFunction(cmd) == null) break;
			if (getValue(cmd) == null) break;

			SWITCH:
			switch (getFunction(cmd).intValue()) {
				case 1:
				// Sets target to where you're looking
					if (argc != 1) break SPELL;
					loc = caster.getTargetBlock(null, argv.get(0).intValue()).getLocation();

					manaUsed += (argv.get(0).intValue() / 2) + 1;
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
					
					manaUsed += Math.pow(2 * argv.get(0).intValue(), 3);
					//caster.sendMessage(manaUsed + " is apparently too much");
					if (manaSource - manaUsed <= 0) return 0;

					w.createExplosion(loc, argv.get(0).intValue());
				break;
				case 10:
				// same as before, only a FIERY explosion
					if (argc != 1) break SPELL;

					manaUsed += Math.pow(4 * argv.get(0).intValue(), 3);
					if (manaSource - manaUsed <= 0) return 0;

					w.createExplosion(loc, argv.get(0).intValue(), true);
				break;
				case 11:
				// create a block, only it uses a ton of mana, only makes blocks in air
					if (argc != 1) break SPELL;
					
					Integer val = getValue(argv.get(0));
					if (val == null) val = new Integer(10);

					manaUsed += Math.pow(val, 2);
					if (manaSource - manaUsed <= 0) return 0;

					if (w.getBlockAt(loc).getType() == Material.AIR) {
						w.getBlockAt(loc).setTypeId(argv.get(0).intValue());
					}
				break;
				case 12:
				// breaks a block at loc
					if (argc > 1) break SPELL;	
					
					if ((argc > 1 && w.getBlockAt(loc).getType().getId() == argv.get(0).intValue()) ||
						argc == 0) {
						
						manaUsed += w.getBlockAt(loc).getType().getMaxDurability();
						if (manaSource - manaUsed <= 0) return 0;

						w.getBlockAt(loc).breakNaturally();
					}
					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;
				break;
				case 13:
				// sets memory address of arg 1 to the ID of arg 2
					if (argc != 2) break SPELL;	

					// gets block ID from Script, to get address and not value
					Integer memorym = Script.get(i + 1);
					Integer mvalue = argv.get(1);

					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;
					
					mem.put(memorym, mvalue);
				break;
				case 14:
				// adds arg 2 to mem address of 1st arg
					if (argc != 2) break SPELL;	
			
					Integer memorya = Script.get(i + 1);
					Integer avalue = argv.get(1);

					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;

					mem.put(memorya, argv.get(0) + avalue); // argv[0] is the value of the block stored at memID
				break;
				case 15:
				// subs arg 2 from mem address of 1st arg (min 0)
					if (argc != 2) break SPELL;	
					
					Integer memorys = Script.get(i + 1);
					Integer svalue = argv.get(1);

					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;

					// argv[0] is the value of the block stored at memID
					Integer newVal = new Integer(argv.get(0) - svalue);
					mem.put(memorys, ((newVal.intValue() > 0) ? (newVal):(new Integer(0)) )); 
				break;
				case 16:
				// stops the rune if arg is 0
					if (argc != 1) break SPELL;	
					
					manaUsed += 1;
					if (manaSource - manaUsed <= 0) return 0;

					if (argv.get(0).intValue() == 0) break SPELL;
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
































