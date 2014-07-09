package me.online.TCL.Commands;

import me.online.TCL.TogglableCommandLibrary;
import me.online.TCL.Utils.Lang;
import me.online.TCL.Utils.Settings;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TCLReload implements CommandExecutor{
	
	private TogglableCommandLibrary plugin;
	public TCLReload(TogglableCommandLibrary instance){
		this.plugin = instance;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(command.getName().equalsIgnoreCase("treload")){
			if(!sender.hasPermission("tcl.reload")){
				Settings.sendLang(sender, Lang.NO_PERMS);
				return true;
			}
			plugin.reloadEnabledCommands();
			plugin.reloadPlayerData();
			plugin.reloadSettings();
			plugin.reloadConfig();
			Settings.sendLang(sender, "&aAll configs reloaded successfully!");
		}
		
		return false;
	}

}
