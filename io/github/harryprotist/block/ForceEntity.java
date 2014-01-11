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
	public final static int ARGC = 3;
	
	private Spell sp;

	public ForceEntity(ArrayList<Integer> a, Player c, Location l, Spell sp) {
		super(a, c, l);	
		this.sp = sp;
	}

	public boolean isValid() {
		return (argv.size() == ARGC || argv.size() == ARGC - 1);
	}
	public int getManaCost() {

		double radius = (argv.size() == ARGC)? (double)argv.get(2):1.0;
		double force = (double)argv.get(1);

		return (int)(force * 2 * Math.pow(radius, 2));
	}
	public void runFunction() {

		double radius = (double)((argv.size() == ARGC)? argv.get(2):1.0);
		double force = (double)argv.get(1) / 100.0;

		double away, up, right;
		double x, y, z;

		int func = sp.getFunction(argv.get(0));
		//caster.sendMessage("function #: " + func);

		if (func == 3) {
			away  = 0.0; 
			up    = -1.0;
			right = 0.0;

		} else if (func == 4) {
			away  = 0.0; 
			up    = 1.0;
			right = 0.0;

		} else if (func == 5) {
			away  = -1.0; 
			up    = 0.0;
			right = 0.0;

		} else if (func == 6) {
			away  = 1.0; 
			up    = 0.0;
			right = 0.0;

		} else if (func == 7) {
			away  = 0.0; 
			up    = 0.0;
			right = 1.0;

		} else if (func == 8) {
			away  = 0.0; 
			up    = 0.0;
			right = -1.0;
		} else {
			away = 0.0;
			up = 0.0;
			right = 0.0;
		}
		
		Vector v = caster.getLocation().getDirection().normalize();
		if (Math.abs(v.getX()) > Math.abs(v.getZ())) {

			if (v.getX() > 0) {
				x = away;
				z = -right;
			} else {
				x = -away;
	 			z = right;
			}
		} else {

			if (v.getZ() > 0) {
				x = right; 
				z = away;
			} else {
				x = -right;
				z = -away;
			}
		}
		y = up;		

		// create a dummy entity
		// use getnearby
		// addforce on all in list
		Entity ent = caster.getWorld().spawnEntity(loc, EntityType.SNOWBALL);
		Vector vel = new Vector(x * force, y * force, z * force);

		List<Entity> ents = ent.getNearbyEntities(radius, radius, radius);
		for (Entity e : ents) {

			Vector ev = e.getVelocity();
			ev.add(vel);
			e.setVelocity(ev);
		}

		ent.remove();
	}
}
