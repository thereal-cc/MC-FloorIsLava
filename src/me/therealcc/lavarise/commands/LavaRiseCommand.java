package me.therealcc.lavarise.commands;

import me.therealcc.lavarise.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Arrays;

public class LavaRiseCommand implements CommandExecutor {
	
	private Main plugin;
	
	public LavaRiseCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("beginlava").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Parameters
		Player p = (Player) sender;
		Location loc = p.getLocation();
		World w = p.getWorld();

		// Locations and Block Types
		Location bLoc;
		Location bLoc2;
		Block cBlock;
		Block cBlock2;

		// Defaults
		int size = 50;
		long time = 100L; // 5 Seconds * 20 TPS
		long tps = 20L;

		p.getServer().broadcastMessage(Arrays.toString(args));

		// Check Size and Time
		if (args.length > 0) {
			int sizeArg = Integer.parseInt(args[0]);
			if (sizeArg > 1) size = sizeArg;

			int timeArg = Integer.parseInt(args[1]);
			if (timeArg > 1) time = timeArg * tps;
		}

		double halfSize = size / 2.0;
		
		p.getServer().broadcastMessage("Lava Is Beginning to Rise, Go!");


		//Setting the X & Z Values
		double startX = loc.getX() - halfSize;
		double startZ = loc.getZ() - halfSize;
		
		// Wall on X-Axis
		for (double x = startX; x < startX + size; x++) {
			for (double y = 1; y < w.getMaxHeight(); y++) {
				// Spawn Barrier if block is air
				bLoc = new Location(w, x, y, startZ);
				bLoc2 = new Location(w, x, y, startZ + size);
				cBlock = w.getBlockAt(bLoc);
				cBlock2 = w.getBlockAt(bLoc2);
				if (cBlock.getType() != Material.BEDROCK) cBlock.setType(Material.BARRIER);
				if (cBlock2.getType() != Material.BEDROCK) cBlock2.setType(Material.BARRIER);
			}
		}

		// Wall on Z Axis
		for (double z = startZ; z < startZ + size; z++) {
			for (double y = 1; y < w.getMaxHeight(); y++) {
				// Spawn Barrier if block is air
				bLoc = new Location(w, startX, y, z);
				bLoc2 = new Location(w, startX + size, y, z);
				cBlock = w.getBlockAt(bLoc);
				cBlock2 = w.getBlockAt(bLoc2);
				if (cBlock.getType() != Material.BEDROCK) cBlock.setType(Material.BARRIER);
				if (cBlock2.getType() != Material.BEDROCK) cBlock2.setType(Material.BARRIER);
			}
		}

		double lavaStartX = startX + 1;
		double lavaStartZ = startZ + 1;

		int finalSize = size;
		// Set scheduler
		BukkitScheduler sched = p.getServer().getScheduler();
		sched.scheduleSyncRepeatingTask(this.plugin, new Runnable() {

			double y = 1;
			
			@Override
			public void run() {

				Location cLoc;
				Block cBlock;
				
				for (double x = lavaStartX; x < lavaStartX + finalSize - 1; x++) {
						for (double z = lavaStartZ; z < lavaStartZ + finalSize - 1; z++) {
							cLoc = new Location(w, x, y, z);
							cBlock = w.getBlockAt(cLoc);
							cBlock.setType(Material.LAVA);
						}
					}
				  y++;
				  p.getServer().broadcastMessage("Lava at Y-Axis: " + y);
				}
			
		}, 0L, time);
		
		return true;
	}
}