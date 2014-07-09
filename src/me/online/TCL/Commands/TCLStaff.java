package me.online.TCL.Commands;

import java.util.ArrayList;
import java.util.List;

import me.online.TCL.TogglableCommandLibrary;
import me.online.TCL.Utils.Commands;
import me.online.TCL.Utils.Lang;
import me.online.TCL.Utils.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

public class TCLStaff implements CommandExecutor{

	private TogglableCommandLibrary plugin;
	public TCLStaff(TogglableCommandLibrary instance){
		this.plugin = instance;
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(!plugin.isEnabled(Commands.STAFF)){
			sender.sendMessage(Lang.UNKNOWN_COMMAND.toString());
			return false;
		}
		if(command.getName().equalsIgnoreCase("staff")){
			if(!sender.hasPermission(Commands.STAFF.getPerm())){
				Settings.sendLang(sender, Lang.NO_PERMS);
				return true;
			}
			if(args.length == 0){
				Settings.Usage(sender, Commands.STAFF);
				return true;
			}else if(args.length == 1){
				if(args[0].equalsIgnoreCase("add")){
					if(!sender.hasPermission("tcl.staff.add")){
						Settings.sendLang(sender, Lang.NO_PERMS);
						return true;
					}
					Settings.Usage(sender, Commands.STAFF);
					return true;
				}else if(args[0].equalsIgnoreCase("remove")){
					if(!sender.hasPermission("tcl.staff.remove")){
						Settings.sendLang(sender, Lang.NO_PERMS);
						return true;
					}
					Settings.Usage(sender, Commands.STAFF);
					return true;
				}else if(args[0].equalsIgnoreCase("list")){
					if(!sender.hasPermission("tcl.staff.list")){
						Settings.sendLang(sender, Lang.NO_PERMS);
						return true;
					}
					listStaff(sender, 1);
				}else if(args[0].equalsIgnoreCase("chat")){
					if(!sender.hasPermission("tcl.staff.chat")){
						Settings.sendLang(sender, Lang.NO_PERMS);
						return true;
					}
					Settings.Usage(sender, Commands.STAFF);
					return true;
				}else{
					Settings.Usage(sender, Commands.STAFF);
					return true;
				}
			}else if(args.length == 2){
				if(args[0].equalsIgnoreCase("add")){
					if(!sender.hasPermission("tcl.staff.add")){
						Settings.sendLang(sender, Lang.NO_PERMS);
						return true;
					}
					List<String> cs = plugin.getConfig().getStringList("staff");
					if(cs == null){
						cs = new ArrayList<String>();
					}
					OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
					if(p == null || !p.hasPlayedBefore()){
						Settings.sendLang(sender, Lang.NEVER_PLAYED.toString().replace("{PLAYER}", args[1]));
						return true;
					}
					if(cs.contains(p.getName())){
						Settings.sendLang(sender, Lang.ALREADY_STAFF.toString().replace("{PLAYER}", p.getName()));
						return true;
					}
					cs.add(p.getName());
					plugin.getConfig().set("staff", cs);
					plugin.saveConfig();
					Settings.sendLang(sender, Lang.STAFF_ADDED.toString().replace("{PLAYER}", p.getName()));
				}else if(args[0].equalsIgnoreCase("remove")){
					if(!sender.hasPermission("tcl.staff.remove")){
						Settings.sendLang(sender, Lang.NO_PERMS);
						return true;
					}
					List<String> cs = plugin.getConfig().getStringList("staff");
					if(cs == null){
						cs = new ArrayList<String>();
					}
					OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
					if(p == null || !p.hasPlayedBefore()){
						Settings.sendLang(sender, Lang.NEVER_PLAYED.toString().replace("{PLAYER}", args[1]));
						return true;
					}
					if(!cs.contains(p.getName())){
						Settings.sendLang(sender, Lang.NOT_STAFF.toString().replace("{PLAYER}", p.getName()));
						return true;
					}
					cs.remove(p.getName());
					plugin.getConfig().set("staff", cs);
					plugin.saveConfig();
					Settings.sendLang(sender, Lang.STAFF_REMOVED.toString().replace("{PLAYER}", p.getName()));
				}else if(args[0].equalsIgnoreCase("list")){
					if(!sender.hasPermission("tcl.staff.list")){
						Settings.sendLang(sender, Lang.NO_PERMS);
						return true;
					}
					String page = args[1];
					try{
						Integer.parseInt(page);
					}catch(NumberFormatException e){
						Settings.sendLang(sender, Lang.MUST_BE_NUMBER.toString().replace("{ARG}", page));
						return true;
					}
					int pageNum = Integer.parseInt(page);
					if(pageNum <= 0){
						Settings.sendLang(sender, Lang.IMPROPER_PAGE.toString().replace("{ARG}", page));
						return true;
					}
					listStaff(sender, pageNum);
				}else if(args[0].equalsIgnoreCase("chat")){
					if(!(sender instanceof Player)){
						Settings.sendLang(sender, Lang.PLAYER_ONLY);
						return true;
					}
					Player pl = (Player) sender;
					if(!sender.hasPermission("tcl.staff.chat")){
						Settings.sendLang(sender, Lang.NO_PERMS);
						return true;
					}
					List<String> cs = plugin.getConfig().getStringList("staff");
					if(cs == null){
						cs = new ArrayList<String>();
					}
					if(!cs.contains(pl.getName())){
						Settings.sendLang(sender, "&cYou must be staff to use this!");
						return true;
					}
					StringBuilder sb = new StringBuilder();
					for(int i = 1; i < args.length; i++){
						sb.append(args[i]);
						sb.append(" ");
					}
					for(String s : plugin.getConfig().getStringList("staff")){
						Player p = Bukkit.getPlayerExact(s);
						if(p == null)continue;
						p.sendMessage(Lang.STAFF_CHAT_FORMAT.toString().replace("{PLAYER}", pl.getName()).replace("{MESSAGE}", sb.toString()));
					}
				
				}else{
					Settings.Usage(sender, Commands.STAFF);
					return true;
				}
			}else if(args.length > 2){
				if(args[0].equalsIgnoreCase("chat")){
					if(!(sender instanceof Player)){
						Settings.sendLang(sender, Lang.PLAYER_ONLY);
						return true;
					}
					Player pl = (Player) sender;
					if(!sender.hasPermission("tcl.staff.chat")){
						Settings.sendLang(sender, Lang.NO_PERMS);
						return true;
					}
					List<String> cs = plugin.getConfig().getStringList("staff");
					if(cs == null){
						cs = new ArrayList<String>();
					}
					if(!cs.contains(pl.getName())){
						Settings.sendLang(sender, "&cYou must be staff to use this!");
						return true;
					}
					StringBuilder sb = new StringBuilder();
					for(int i = 1; i < args.length; i++){
						sb.append(args[i]);
						sb.append(" ");
					}
					for(String s : plugin.getConfig().getStringList("staff")){
						Player p = Bukkit.getPlayerExact(s);
						if(p == null)continue;
						p.sendMessage(Lang.STAFF_CHAT_FORMAT.toString().replace("{PLAYER}", pl.getName()).replace("{MESSAGE}", sb.toString()));
					}
				}
			}else{
				Settings.Usage(sender, Commands.STAFF);
				return true;
			}
		}


		return false;
	}
	public void listStaff(CommandSender s, int pageNum){
		StringBuilder pl = new StringBuilder();
		for(String str : plugin.getConfig().getStringList("staff")){
			pl.append(ChatColor.GOLD + str);
			pl.append("\n");

		}
		int pageHeight = ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 3;
		int pageWidth = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH;
		ChatPaginator.ChatPage page = ChatPaginator.paginate(pl.toString(), pageNum, pageWidth, pageHeight);
		s.sendMessage(ChatColor.GRAY + "------ " + ChatColor.GOLD + "Staff " + ChatColor.GOLD + "List" + ChatColor.GRAY + " ------ " + ChatColor.GOLD + "Page (" + ChatColor.RED + page.getPageNumber() + ChatColor.GOLD + "/" + ChatColor.RED + page.getTotalPages() + ChatColor.GOLD + ")" + ChatColor.GRAY + " ------");
		for(String str : page.getLines()){
			s.sendMessage(str);
		}
	}

}
