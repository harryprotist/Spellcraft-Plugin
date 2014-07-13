package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;

public class EntityTeleport extends EntityFunction
{
	public EntityTeleport(ArrayList<Integer> a, Player c, Location l, ArrayList<Entity> eL) {
		super(a, c, l, eL);
	}

	public boolean isValid() {
		return (loc.getBlockY() >= 6) && (argv.size() == 0);
	}

	public int getManaCost() {
		int totalMana = 0;
		for (Entity e : entList) {
			Location el = e.getLocation();
			double psy = (el.distanceSquared(loc));
			// int congroo
			totalMana += (int)psy;
		}
		return totalMana;	
	}

	public void runFunction() {
		for (Entity e : entList) {
			e.teleport(loc);
		}
	}
}
