package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;

public class MemSet extends MemBlockFunction
{
	private static final int ARGC = 2;	

	public MemSet(ArrayList<Integer> a, Player c, Location l,
	Map<Integer, Integer> m, ArrayList<Integer> s, int i ) {

		super(a, c, l, m, s, i);	
	}

	public boolean isValid() {

		return argv.size() == ARGC;
	}
	public int getManaCost() {
		
		return 1;
	}
	public void runFunction() {

		mem.put( rargv.get(0), argv.get(1) );
	}
}
