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

import java.util.*;

//import SpellList;

public final class Spellcraft extends JavaPlugin {

//	private Map<String, Integer> mana; // holds how much mana each player has
		// TODO: Save to file and load from file
	public SpellList Spells;
	private Mana ManaEvents;

	// runs on plugin load
	public void onEnable () {
		getLogger().info("onEnable of Spellcraft functioning");

		Spells = new SpellList(this);
		ManaEvents = new Mana(this);

		if (Spell.Load()) {
			getLogger().info(Spell.dumpMaps());
		} else {
			getLogger().info("Failed to Load Spell");	
		}
	}

	// runs on exit
	public void onDisable () {
		getLogger().info("onDisable of Spellcraft functioning");
	}

	// handle commands
	public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args) {
		String cmdName = cmd.getName();
		
		if (cmdName.equalsIgnoreCase("spellCraftRunning") ) {
			sender.sendMessage("Running");

			String map;
			if (Spell.Load()) {
				sender.sendMessage(Spell.dumpMaps());
			} else {
				sender.sendMessage("Failed to Load Spell");	
			}
	
			return true;

		} else if (cmdName.equalsIgnoreCase("mana") && sender instanceof Player) {

			// the player wants to do something mana related
			Player p = (Player) sender;	

			// Mana commands: check, toggle on, register, imbue, perform, cast
			// register binds a spell to a word
			// imbue binds a spell and mana to an item
			// perform consumes a block and uses it to power a rune

			// ready to cast a spell
			if (args.length > 0 && args[0].length() > 0 && (args[0].charAt(0) == 'c' || args[0].charAt(0) == 'C')) {
								
				if (args.length > 1 && Spells.lookup(args[1])) {

					setMeta(p, "lastspell", Spells.get(args[1]));
				}	
			
			// toggle
			} else if (args.length > 0 && args[0].length() > 0 && (args[0].charAt(0) == 'o' || args[0].charAt(0) == 'O')) {
				
				Boolean b = (Boolean)getMeta(p, "manaon");
				if (b == null) b = false;				
		
				sender.sendMessage("Setting mana to " + (new Boolean(!b.booleanValue()).toString()) );

				setMeta(p, "manaon", new Boolean(!b.booleanValue()));

			// register a spell
			} else if (args.length > 0 && args[0].length() > 0 && (args[0].charAt(0) == 'r' || args[0].charAt(0) == 'O')) {

				if (args.length > 1) {
					ManaEvents.register(p, args[1]);
				}

			// imbue an item
			} else if (args.length > 0 && args[0].length() > 0 && (args[0].charAt(0) == 'i' || args[0].charAt(0) == 'I')) {

				if (args.length > 1) {
					ManaEvents.imbue(p, args[1]);
				}

			// perform a ritual
			} else if (args.length > 0 && args[0].length() > 0 && (args[0].charAt(0) == 'p' || args[0].charAt(0) == 'P')) {

				ManaEvents.perform(p);
			
			// otherwise print MP
			} else {
				Integer m = (Integer)getMeta(p, "mana");
				if (m == null) m = 0;
				sender.sendMessage(m + " MP");
			}
			
			return true;
		} else if (cmdName.equalsIgnoreCase("setmana") && sender instanceof Player) {
			
			Integer m = new Integer(0);
			if (args.length > 0) {
				try {
					m = new Integer(args[0]);	
				} catch (NumberFormatException e) {
					sender.sendMessage("Invalid Format");
				}
			}
			setMeta((Player)sender, "mana", m);
			sender.sendMessage("Set mana to " + m.toString());
		}
		return false;
	}

	public void setMeta(Player p, String k, Object v) {
		p.setMetadata(k, new FixedMetadataValue(this, v) );
	}
	public Object getMeta(Player p, String k) {
	 	List<MetadataValue> vs = p.getMetadata(k);
		for (MetadataValue v : vs) {
			if (v.getOwningPlugin().getDescription().getName().equals(this.getDescription().getName())) {
				return v.value();	
			}
		}
		return null;
	}
	// TODO:
	// add basic spell system
	// basic mana system
}
