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

	private ArrayList<String> Spells;		
	private Spellcraft Plugin;

	public SpellList(Spellcraft p) {

		Spells = new ArrayList<String>();
		Spells.add("burn");
		Spells.add("slay");
		Spells.add("haste");

		Plugin = p;	
	}	
	public boolean lookup(String spell) {
		return (Spells.contains(spell));
	}
	

	public boolean cast(String spell, Player p) {
		int cost = 0;
		int mana = (Integer)Plugin.getMeta(p, "mana");
		int s = -1;

		if		(spell.equals("burn") ) {
				s = burn(p, mana);
		} else if	(spell.equals("slay") ) {

		} else if	(spell.equals("haste") ) {

		} 

		if (s < 0) { 
			return false;	
		}
		Plugin.setMeta(p, "mana", new Integer(s));
		return true;
	}
	
	/////////////////////////////////////////////////////
	/*
	   Sets a block on fire and makes an explosion
	*/
	public int burn(Player p, int mana) {

		int remain;
		if (mana > 16) remain = mana - 16;
		else return -1;
		
		Block b = p.getTargetBlock(null, 50);
		World w = b.getWorld();

		Block f = w.getBlockAt(b.getX(), b.getY() + 1, b.getZ() );	
		if (f.getType() == Material.AIR) {

			f.setType(Material.FIRE);
			w.createExplosion(	(double)f.getX(),
						(double)f.getY(),
						(double)f.getZ(),
						0.25f,
						false, // no fire
						false // doesn't break blocks
			);	
		}	

		return remain;
	}
}
