package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;

public class MakeExplosion extends BlockFunction
{
	public static final int ARGC = 1;
	
	private boolean fire;

	public MakeExplosion(ArrayList<Integer> a, Player c, Location l, boolean fire) {

		super(a, c, l);
		this.fire = fire;
	}
	
	public boolean isValid() {

		return argv.size() == ARGC;
	}
	public int getManaCost() {
		
		int factor = fire? 4:2;
		return (int)Math.pow(factor * argv.get(0).intValue(), 2);
	}
	public void runFunction() {
		
		int power = argv.get(0);
		World w = caster.getWorld();

		w.createExplosion(loc, power, fire);
	}
}
