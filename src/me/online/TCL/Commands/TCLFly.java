package me.online.TCL.Commands;

import me.online.TCL.TogglableCommandLibrary;
import me.online.TCL.Utils.Commands;
import me.online.TCL.Utils.Lang;
import me.online.TCL.Utils.Settings;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class TCLFly implements CommandExecutor{
	
	private TogglableCommandLibrary plugin;
	public TCLFly(TogglableCommandLibrary instance){
		this.plugin = instance;
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(!plugin.isEnabled(Commands.FLY)){
			sender.sendMessage(Lang.UNKNOWN_COMMAND.toString());
			return false;
		}
		if(command.getName().equalsIgnoreCase("fly")){
			if(!sender.hasPermission(Commands.FLY.getPerm())){
				Settings.sendLang(sender, Lang.NO_PERMS);
				return true;
			}
			if(args.length == 0){
				if(!(sender instanceof Player)){
					Settings.sendLang(sender, Lang.PLAYER_ONLY);
					return true;
				}
				Player target = (Player) sender;
				ConfigurationSection pcs = plugin.getPlayerData().getConfigurationSection(target.getUniqueId().toString());
				if(pcs == null){
					pcs = plugin.getPlayerData().createSection(target.getUniqueId().toString());
					pcs.set("flying", false);
					pcs.set("ghost", false);
					pcs.set("godmode", false);
				}
				boolean flying = pcs.getBoolean("flying");
				if(flying == true){
					Settings.sendLang(sender, Lang.FLIGHT_TOGGLE_OFF);
					pcs.set("flying", false);
					target.setAllowFlight(false);
				}else{
					Settings.sendLang(sender, Lang.FLIGHT_TOGGLE_ON);
					pcs.set("flying", true);
					target.setAllowFlight(true);
				}
				plugin.savePlayerData();
			}else if(args.length == 1){
				if(!sender.hasPermission("tcl.fly.others")){
					Settings.sendLang(sender, Lang.NO_PERM_FLIGHT_TOGGLE_OTHERS);
					return true;
				}
				Player target = Bukkit.getPlayerExact(args[0]);
				if(target == null){
					Settings.sendLang(sender, Lang.PLAYER_NOT_ONLINE.toString().replace("{PLAYER}", args[0]));
					return true;
				}
				ConfigurationSection pcs = plugin.getPlayerData().getConfigurationSection(target.getUniqueId().toString());
				if(pcs == null){
					pcs = plugin.getPlayerData().createSection(target.getUniqueId().toString());
					pcs.set("flying", false);
					pcs.set("ghost", false);
					pcs.set("godmode", false);
					plugin.savePlayerData();
				}
				boolean flying = pcs.getBoolean("flying");
				if(flying == true){
					Settings.sendLang(sender, Lang.FLIGHT_TOGGLE_OTHERS_OFF.toString().replace("{PLAYER}", target.getName()));
					Settings.sendLang((CommandSender) target, Lang.FLIGHT_TOGGLE_OFF);
					pcs.set("flying", false);
					target.setAllowFlight(false);
				}else{
					Settings.sendLang(sender, Lang.FLIGHT_TOGGLE_OTHERS_ON.toString().replace("{PLAYER}", target.getName()));
					Settings.sendLang((CommandSender) target, Lang.FLIGHT_TOGGLE_ON);
					pcs.set("flying", true);
					target.setAllowFlight(true);
				}
				plugin.savePlayerData();
			}else{
				Settings.Usage(sender, Commands.FLY);
			}
		}
		
		return false;
	}

}
