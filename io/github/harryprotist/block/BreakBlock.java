package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;

public class BreakBlock extends BlockFunction
{
	private static final int ARGC = 1;	
	private Block target;

	public BreakBlock(ArrayList<Integer> a, Player c, Location l) {
		
		super(a, c, l);	
		
		World w = c.getWorld();
		target = w.getBlockAt(l);
	}

	public boolean isValid() {

		return argv.size() <= ARGC;
	}
	public int getManaCost() {
		
		if (argv.size() == 1 && target.getType().getId() != argv.get(0).intValue()) {
			return 1;
		} else {
			return target.getType().getMaxDurability() + 1;
		}
	}
	public void runFunction() {
		
		World w = caster.getWorld();
	
		if ( argv.size() == 0 ||
		( argv.size() > 1 && target.getType().getId() == argv.get(0).intValue()) &&
		( target.getType() != Material.BEDROCK )) {

			target.breakNaturally();
		}
	}
}
