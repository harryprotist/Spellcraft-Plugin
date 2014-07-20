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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
		if ((a == Action.LEFT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR) ) {
		// check for autocasting

			Boolean mo = (Boolean)Plugin.getMeta(p, "manaon");
			if (mo == null) mo = new Boolean(false);

			if (mo.booleanValue() && p.isSneaking()) {
	
				Spell spell = (Spell)Plugin.getMeta(p, "lastspell");			
				if (spell == null) { 
			
					p.sendMessage("No Last Spell");
				
				} else {
	
					Integer m = (Integer)Plugin.getMeta(p, "mana");
					if (m == null) m = new Integer(1);
	
					//p.sendMessage("Casting last spell. Current MP: " + m.toString());			
					
					int nm = spell.Excecute(m.intValue(), p);
					Plugin.setMeta(p, "mana", new Integer(nm));
				}
		
			} else {
	
				ItemStack is = p.getInventory().getItemInHand();
			
				if (is.hasItemMeta()) {
		
					ItemMeta im = is.getItemMeta();
					List<String> lore = im.getLore();
		
					if (lore != null && lore.size() == 3 && lore.get(0).equals("SPELLCRAFT") ) {
		
						Integer m = new Integer(lore.get(1));
						Spell sp = new Spell(Spell.parseString(lore.get(2)), Plugin);
		
						m = new Integer(sp.Excecute(m.intValue(), p) );
		
						ArrayList<String> newLore = new ArrayList<String>();
						newLore.add("SPELLCRAFT");
						newLore.add(m.toString());
						newLore.add(sp.dumpScript());
		
						if (m.intValue() > 0) {
							im.setLore(newLore);
						} else {
							im.setLore(null);
						}
						is.setItemMeta(im);
					}
		
				}
			}

		} else if (a == Action.RIGHT_CLICK_BLOCK) {
		// check to see if a rune is being activated

			Block b = event.getClickedBlock();
			World w = b.getWorld();

			// now make sure the block is in rune configuration
			if (isRune(b) ) {

				p.sendMessage("activating rune");
				Plugin.getLogger().info("It's time to kick ass and activate runes.And I'm all outta kick ass");
			
				Spell sp = Rune.parseRune(p, b);
				Plugin.setMeta(p, "lastspell", sp);
			}
		}

		// check whether player has died of mana loss
		Integer m = (Integer)Plugin.getMeta(p, "mana");
		if (m == null) {
			m = new Integer(1);
		} else if (m == 0) {
			p.setHealth(0.0);
		}
	}
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		
		Player p = event.getEntity();		
		Integer m = (Integer)Plugin.getMeta(p, "mana");
		if (m == null) {
			m = new Integer(1);
		} else if (m == 0) {
			p.setHealth(0.0);
		}
	}
	

	private boolean isRune(Block b) {
		World w = b.getWorld();
		Material m = Material.MOSSY_COBBLESTONE;
		if (	w.getBlockAt(b.getX() + 1, b.getY(), b.getZ()).getType() == m &&
			w.getBlockAt(b.getX() - 1, b.getY(), b.getZ()).getType() == m &&
			w.getBlockAt(b.getX(), b.getY(), b.getZ() + 1).getType() == m &&
			w.getBlockAt(b.getX(), b.getY(), b.getZ() - 1).getType() == m ) {

			return true;				
		}
		return false;
	}

	// CALLED FROM PLUGIN FOR MANAGING MANA

	private static final int RUNE_RANGE = 3;	

	public void register(Player p, String s) {
		
		World w = p.getWorld();	
		Block b = p.getTargetBlock(null, RUNE_RANGE);
		
		if (isRune(b) ) {

			Spell sp = Rune.parseRune(p, b);

			if (Plugin.Spells.register(s, sp) ) {
				p.sendMessage(s + " is now a spell");
				return;
			}
		}
	}

	public void imbue(Player p, String m) {
		
		ItemStack is = p.getInventory().getItemInHand(); 
		Block b = p.getTargetBlock(null, RUNE_RANGE);

		Integer n;
		try {
			n = new Integer(m); // just checks is m can be an int
		} catch (NumberFormatException e) {
			return;
		}

		if (isRune(b) && !is.getType().isBlock() && is.getAmount() == 1) {

			Integer pn = (Integer)Plugin.getMeta(p, "mana");
			if (pn == null) pn = new Integer(0);

			if (pn.compareTo(n) <= 0 || n.intValue() <= 0) {
				return;	
			} else {
				Plugin.setMeta(p, "mana", new Integer(pn - n));
			}			

			Spell sp = Rune.parseRune(p, b);
			String sps = sp.dumpScript();

			ItemMeta im = is.getItemMeta();

			if (im.hasLore()) {
				List<String> lore = im.getLore();
				// check if the spell is the same and then if it is, stack the mana	
				if (lore.size() == 3 && lore.get(0).equals("SPELLCRAFT")) {
					if (lore.get(2).equals(sp.dumpScript())) {
						p.sendMessage("added " + m + " mana to your item");
						m = (new Integer((new Integer(lore.get(1))).intValue() + n.intValue())).toString();
					}
				}
			}

			ArrayList<String> newLore = new ArrayList<String>();
			newLore.add("SPELLCRAFT"); // so I can tell it's from my plugin // never mind that for now
			newLore.add(m);
			newLore.add(sps);

			im.setLore(newLore);
			is.setItemMeta(im);
			p.sendMessage("your item has been imbued with magic");
		}
	}

	public void perform(Player p) {

		World w = p.getWorld();
		Block b = p.getTargetBlock(null, RUNE_RANGE);

		if (isRune(b) ) {

			Spell sp = new Spell(Rune.parseRune(p, b), Plugin);
			Integer m = Spell.getValue(b.getType()) * 10;	

			p.sendMessage("Performing ritual");		

			b.setType(Material.AIR);
			sp.Excecute(m.intValue(), p);	
		}
	}
}
