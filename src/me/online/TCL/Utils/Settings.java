package me.online.TCL.Utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Settings {
	
	public static void sendLang(CommandSender s, Lang l){
		s.sendMessage(Lang.PREFIX.toString() + l.toString());
	}
	public static void sendLang(CommandSender s, String l){
		s.sendMessage(Lang.PREFIX.toString() + ChatColor.translateAlternateColorCodes('&', l));
	}
	public static void Usage(CommandSender s, Commands cmd){
		sendLang(s, Lang.PROPER_USAGE.toString().replace("{USAGE}", cmd.getUsage()));
	}
	public static String unknownCommand(){
		return Lang.UNKNOWN_COMMAND.toString();
	}

}
