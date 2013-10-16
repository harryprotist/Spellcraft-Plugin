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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.*;
import java.lang.reflect.*;

public final class Mana implements Listener {
	
	private Spellcraft Plugin;
	public Mana(Spellcraft p) {

		Plugin = p;
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	} 

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {

		Player p = event.getPlayer();		

		Action a = event.getAction();
		if ((a == Action.LEFT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR) && p.isSneaking()) {
		// check for autocasting

			Boolean mo = (Boolean)Plugin.getMeta(p, "manaon");
			if (mo == null || !mo.booleanValue() ) {
				return;
			
			} else if (mo.booleanValue()) {
				
				Spell spell = (Spell)Plugin.getMeta(p, "lastspell");			
				if (spell == null) { 
			
					p.sendMessage("No Last Spell");
					return;
				
				} else {
					

					Integer m = (Integer)Plugin.getMeta(p, "mana");
					if (m == null) m = new Integer(0);

					//p.sendMessage("Casting last spell. Current MP: " + m.toString());			
					
					int nm = spell.Excecute(m.intValue(), p);
					Plugin.setMeta(p, "mana", new Integer(nm));
				}
			/*	} if (Plugin.Spells.lookup(spell) ) {
	
					if (!Plugin.Spells.cast(spell, p) ) {
						p.sendMessage("Casting Failed");
					}
				} else {
	
					p.sendMessage("Casting Failed");
				}
			*/
			}
		} else if (a == Action.RIGHT_CLICK_BLOCK) {
		// check to see if a rune is being activated

			Block b = event.getClickedBlock();
			World w = b.getWorld();

			// now make sure the block is in rune configuration
			if (	w.getBlockAt(b.getX() + 1, b.getY(), b.getZ()).getType() == Material.REDSTONE_BLOCK &&
				w.getBlockAt(b.getX() - 1, b.getY(), b.getZ()).getType() == Material.REDSTONE_BLOCK &&
				w.getBlockAt(b.getX(), b.getY(), b.getZ() + 1).getType() == Material.REDSTONE_BLOCK &&
				w.getBlockAt(b.getX(), b.getY(), b.getZ() - 1).getType() == Material.REDSTONE_BLOCK ) {

				Plugin.getLogger().info("It's time to kick ass and activate runes.\nAnd I'm all outta kick ass");
			
				// let's get this sh*t going
				Spell sp = Rune.parseRune(p, b);
				Plugin.setMeta(p, "lastspell", sp);
			}
		}
	}
}
