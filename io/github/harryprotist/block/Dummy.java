package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.*;

import io.github.harryprotist.Spell;

public class Dummy extends BlockFunction // Dummy blockfunction with cost of 0
{
	public Dummy() {
		super(null, null, null);
	}

	public boolean isValid() { 
		return true; 
	}
	public int getManaCost() {
		return 0;
	}
	public void runFunction() {
		return;
	}
}
