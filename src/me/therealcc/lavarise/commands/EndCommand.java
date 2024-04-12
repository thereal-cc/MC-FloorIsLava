package me.therealcc.lavarise.commands;

import me.therealcc.lavarise.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndCommand implements CommandExecutor {
	
	private Main plugin;
	
	public EndCommand(Main plugin) {
		this.plugin = plugin;
        plugin.getCommand("endlava").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		p.getServer().broadcastMessage("Lava has stopped!");
		p.getServer().getScheduler().cancelTasks(this.plugin);
	    return true;
	}

}
