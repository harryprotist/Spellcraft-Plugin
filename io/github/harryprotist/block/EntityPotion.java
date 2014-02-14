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

public class EntityPotion extends EntityFunction
{
	public final static int ARGC = 2;
	
	// private Map<Integer, Integer> btp; // block (value) to potion
	static {
	//	btp = new HashMap<Integer, Integer>();
	//	btp.put(new Integer(), new Integer()); // enter all effects here
	}

	public EntityPotion(ArrayList<Integer> a, Player c, Location l, Spell sp, ArrayList<Entity> eL) {
		super(a, c, l, eL);	
//		this.sp = sp;
	}

	public boolean isValid() {
		return (argv.size() == ARGC);
	}
	public int getManaCost() {
		return 1;
	}
	public void runFunction() {
		return;
	}
}
