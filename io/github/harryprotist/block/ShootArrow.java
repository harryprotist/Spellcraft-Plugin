package io.github.harryprotist.block;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.*;

import io.github.harryprotist.Spell;

public class ShootArrow extends BlockFunction
{
	public ShootArrow(ArrayList<Integer> a, Player c, Location l) {
		
		super(a, c, l);
	}

	public boolean isValid() {
		
		return argv.size() == 2 || argv.size() == 1;
	}
	public int getManaCost() {
		
		return (Spell.getValue(Material.ARROW.getId()) * 16 + argv.get(0) );
	}
	public void runFunction() {
	
		int power = argv.get(0);

		int spread = 0;
		if (argv.size() > 1) spread = argv.get(1);

		Location pLoc = caster.getLocation();
		pLoc.add(0.0,1.0,0.0);

		Location tLoc = loc.clone();
		tLoc.add(0.5, 0.5, 0.5);

		Vector dir = new Vector();
		dir.setX( tLoc.getX() - pLoc.getX() );
		dir.setY( tLoc.getY() - pLoc.getY() );
		dir.setZ( tLoc.getZ() - pLoc.getZ() );
		dir.normalize();

		World w = caster.getWorld();
		
		w.spawnArrow(	pLoc,
				dir,
				(float)argv.get(0) / 10.0f,
				spread	
		);	
	}
}
