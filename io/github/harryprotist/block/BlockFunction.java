package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;

public abstract class BlockFunction
{
	protected ArrayList<Integer> argv;
	protected Player caster;
	protected Location loc; 

	public BlockFunction(ArrayList<Integer> argv, Player caster, Location loc) {
		
		this.argv = argv;
		this.caster = caster;
		this.loc = loc;
	}

	public abstract boolean isValid();
	public abstract int getManaCost();
	public abstract void runFunction();
}
