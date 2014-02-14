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

public class EntityForce extends EntityFunction
{
	public final static int ARGC = 2;
	
	private Spell sp;

	public EntityForce(ArrayList<Integer> a, Player c, Location l, Spell sp, ArrayList<Entity> eL) {
		super(a, c, l, eL);	
		this.sp = sp;
	}

	public boolean isValid() {
		return (argv.size() == ARGC || argv.size() == ARGC - 1);
	}
	public int getManaCost() {

		double force = (double)argv.get(1);
		return (int)(force * 2 * entList.size());
	}
	public void runFunction() {

		double force = (double)argv.get(1) / 100.0;

		double away, up, right;
		double x, y, z;

		int func = sp.getFunction(sp.getValueMaterial(argv.get(0)));
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
		Vector vel = new Vector(x * force, y * force, z * force);

		for (Entity e : entList) {

			Vector ev = e.getVelocity();
			ev.add(vel);
			e.setVelocity(ev);
		}
	}
}
