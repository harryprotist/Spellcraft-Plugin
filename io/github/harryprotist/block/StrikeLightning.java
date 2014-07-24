package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;

public class StrikeLightning extends BlockFunction
{
	public static final int ARGC = 0;
	
	public StrikeLightning(ArrayList<Integer> a, Player c, Location l) {

		super(a, c, l);
	}
	
	public boolean isValid() {

		return argv.size() == ARGC;
	}
	public int getManaCost() {
		
		return 50000;
	}
	public void runFunction() {
		
		World w = caster.getWorld();

		w.strikeLightning(loc);
	}
}
