package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;

public class SetTargetLooking extends BlockFunction
{
	private static final int ARGC = 1;

	public SetTargetLooking(ArrayList<Integer> a, Player c, Location l) {
		
		super(a, c, l);
	}

	public boolean isValid() {

		return argv.size() == ARGC;
	}
	public int getManaCost() {
	
		return (argv.get(0) / 2) + 1;
	}
	public void runFunction() {
	
		Location t = caster.getTargetBlock(null, argv.get(0).intValue() ).getLocation();
		
		loc.setX( t.getX() );
		loc.setY( t.getY() );
		loc.setZ( t.getZ() );
	}
}
