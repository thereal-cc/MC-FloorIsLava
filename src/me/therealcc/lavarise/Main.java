package me.therealcc.lavarise;

import org.bukkit.plugin.java.JavaPlugin;

import me.therealcc.lavarise.commands.EndCommand;
import me.therealcc.lavarise.commands.LavaRiseCommand;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new LavaRiseCommand(this);
		new EndCommand(this);
	}

}
