package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;

public class PlaceBlock extends BlockFunction
{
	private static final int ARGC = 1;

	public PlaceBlock(ArrayList<Integer> a, Player c, Location l) {

		super(a, c, l);
	}

	public boolean isValid() {

		return argv.size() == ARGC;
	}
	public int getManaCost() {

		return (int)Math.pow(argv.get(0) + 3, 3);	
	}
	public void runFunction() {
		
		World w = caster.getWorld();

		if (w.getBlockAt(loc).getType() == Material.AIR) {
		
			w.getBlockAt(loc).setType(Spell.getValueMaterial(argv.get(0)));
		}
	}
}
