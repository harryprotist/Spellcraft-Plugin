package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.*;

import io.github.harryprotist.Spell;

public class ShiftLoc extends BlockFunction
{
	private static final int ARGC = 0;

	private double away; 
	private double up;
	private double right;

	public ShiftLoc(ArrayList<Integer> a, Player c, Location l, double away, double up, double right) {
	
		super(a, c, l);			

		this.away = away;
		this.up = up;
		this.right = right;
	}

	public boolean isValid() {

		return argv.size() == ARGC;
	}
	public int getManaCost() {
		
		return (int)(away + up + right);	
	}
	public void runFunction() {
		
		double x, y, z;

		Vector v = caster.getLocation().getDirection().normalize();
		if (v.getX() > v.getZ()) {
			x = away;
			z = right;
		} else {
			z = away;
			x = right;
		}
		y = up;

		loc.add(x, y, z);	
	}
}
