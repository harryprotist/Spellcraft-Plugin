package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;
import io.github.harryprotist.Spellcraft;

public class GiveMana extends BlockFunction
{
	private static final int ARGC = 0;
	private Spellcraft plugin;

	public GiveMana(ArrayList<Integer> a, Player c, Location l,
	Spellcraft s) {

		super(a, c, l);

		plugin = s;
	}

	public boolean isValid() {
		
		return argv.size() == ARGC;
	}	
	public int getManaCost() {
		
		return 1;
	}
	public void runFunction() {
		
		if (plugin == null) return;
		
		Integer pmana = (Integer)plugin.getMeta(caster, "mana");
		if (pmana == null) pmana = new Integer(0);

		plugin.setMeta(caster, "mana", new Integer(pmana.intValue() + 1));
	}
}
