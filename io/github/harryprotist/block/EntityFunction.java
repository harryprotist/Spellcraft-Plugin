package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;

public abstract class EntityFunction extends BlockFunction
{
	protected ArrayList<Entity> entList;

	public EntityFunction(ArrayList<Integer> a, Player c, Location l, ArrayList<Entity> eL) {
		
		super(a, c, l);
		this.entList = eL;
	}
}
