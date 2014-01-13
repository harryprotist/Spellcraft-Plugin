package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;

public class EntitySelectArea extends EntityFunction
{
	public static final int ARGC = 1;

	public EntitySelectArea(ArrayList<Integer> a, Player c, Location l, ArrayList<Entity> eL) {
		
		super(a, c, l, eL);
	}

	public boolean isValid() {

		return argv.size() == ARGC;
	}
	public int getManaCost() {

		double radius = argv.get(0);
		return (int)Math.pow(radius, 3);
	}
	public void runFunction() {

		double radius = argv.get(0);
		Entity ent = caster.getWorld().spawnEntity(loc, EntityType.SNOWBALL);

		List<Entity> neL = ent.getNearbyEntities(radius, radius, radius);
		entList.clear();
		entList.addAll(neL);
	}
}
