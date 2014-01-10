package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.*;

import io.github.harryprotist.Spell;

public class ForceEntity extends BlockFunction
{
	public final static int ARGC = 4;

	public ForceEntity(ArrayList<Integer> a, Player c, Location l) {
		super(a, c, l);	
	}

	public boolean isValid() {
		return (argv.size() == ARGC - 1 || argv.size() == ARGC);
	}
	public int getManaCost() {

		int radius = (argv.size() == ARGC)? argv.get(3):1;
		int force = argv.get(0);

		return ((int)Math.pow(force, 2)) + ((int)Math.pow(radius, 3));
	}
	public void runFunction() {

		double radius = (double)((argv.size() == ARGC)? argv.get(3):1.0);
		double force = (double)argv.get(0);

		// create a dummy entity
		// use getnearby
		// addforce on all in list
		Entity ent = caster.getWorld().spawnEntity(loc, EntityType.SNOWBALL);
		Vector vel = new Vector(
				argv.get(0),
				argv.get(1),
				argv.get(2)
		);
		caster.sendMessage(argv.get(0) + " " + argv.get(1) + " " + argv.get(2));

		List<Entity> ents = ent.getNearbyEntities(radius, radius, radius);
		for (Entity e : ents) {

			e.setVelocity(vel);
		}

		ent.remove();
	}
}
