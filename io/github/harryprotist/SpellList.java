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

//import Spellcraft;

import java.util.*;

public final class SpellList {

	private Map<String, Spell> Spells;
	private Spellcraft Plugin;

	public SpellList(Spellcraft p) {

		Spells = new HashMap<String, Spell>();

		Plugin = p;	
	}	
	public boolean lookup(String word) {
		return (Spells.containsKey(word));
	}
	
	public boolean register(String word, Spell spell) {

		if (Spells.get(word) != null || spell == null) {
			return false;
		}	
		
		Spells.put(word, spell);

		return true;
	}

	public Spell get(String word) {
		
		return Spells.get(word);
	}
}
