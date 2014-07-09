package me.online.TCL.Commands;

import me.online.TCL.TogglableCommandLibrary;
import me.online.TCL.Utils.Commands;
import me.online.TCL.Utils.Lang;
import me.online.TCL.Utils.Settings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.ChatPaginator;

public class TCLHelp implements CommandExecutor{
	private TogglableCommandLibrary plugin;
	public TCLHelp(TogglableCommandLibrary instance){
		this.plugin = instance;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(!plugin.isEnabled(Commands.HELP)){
			sender.sendMessage(Lang.UNKNOWN_COMMAND.toString());
			return false;
		}
		if(command.getName().equalsIgnoreCase("thelp")){
			if(!sender.hasPermission(Commands.HELP.getPerm())){
				Settings.sendLang(sender, Lang.NO_PERMS);
				return true;
			}
			if(args.length == 0){
				help(sender, 1);
			}else if(args.length == 1){
				String page = args[0];
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
				help(sender, pageNum);
			}else{
				Settings.Usage(sender, Commands.HELP);
			}
		}
		return false;
	}
	
	public void help(CommandSender s, int pageNum){
		StringBuilder pl = new StringBuilder();
		for(Commands cmd : Commands.values()){
			pl.append(ChatColor.GOLD + cmd.getUsage() + ChatColor.GRAY + " : " + ChatColor.DARK_AQUA + cmd.getDescription());
			pl.append("\n");
		}
		int pageHeight = ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 3;
		int pageWidth = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH;
		ChatPaginator.ChatPage page = ChatPaginator.paginate(pl.toString(), pageNum, pageWidth, pageHeight);
		s.sendMessage(ChatColor.GRAY + "------ " + ChatColor.DARK_AQUA + "TCL " + ChatColor.GOLD + "Help" + ChatColor.GRAY + " ------ " + ChatColor.GOLD + "Page (" + ChatColor.RED + page.getPageNumber() + ChatColor.GOLD + "/" + ChatColor.RED + page.getTotalPages() + ChatColor.GOLD + ")" + ChatColor.GRAY + " ------");
		for(String str : page.getLines()){
			s.sendMessage(str);
		}
	}

}
