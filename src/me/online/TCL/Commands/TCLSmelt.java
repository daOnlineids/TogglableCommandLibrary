package me.online.TCL.Commands;

import java.util.HashMap;
import java.util.Map;

import me.online.TCL.TogglableCommandLibrary;
import me.online.TCL.Utils.Commands;
import me.online.TCL.Utils.Lang;
import me.online.TCL.Utils.Settings;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TCLSmelt implements CommandExecutor{
	
	private TogglableCommandLibrary plugin;
	public TCLSmelt(TogglableCommandLibrary instance){
		this.plugin = instance;
	}
	public void onEnable(){
		loadSmeltableItems();
	}
	Map<Material, String> a = new HashMap<Material, String>();
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(!plugin.isEnabled(Commands.SMELT)){
			sender.sendMessage(Lang.UNKNOWN_COMMAND.toString());
			return false;
		}
		if(command.getName().equalsIgnoreCase("smelt")){
			if(!sender.hasPermission(Commands.SMELT.getPerm())){
				Settings.sendLang(sender, Lang.NO_PERMS);
				return true;
			}
			if(!(sender instanceof Player)){
				Settings.sendLang(sender, Lang.PLAYER_ONLY);
				return true;
			}
			Player p = (Player) sender;
			if(args.length == 1){
				loadSmeltableItems();
				if(args[0].equalsIgnoreCase("all")){
					
				}else if(args[0].equalsIgnoreCase("hand")){
					ItemStack item = p.getItemInHand();
					if(item == null || item.getType().equals(Material.AIR)){
						Settings.sendLang(sender, Lang.NO_SMELT_HAND);
						return true;
					}
					if(!a.containsKey(item.getType())){
						Bukkit.broadcastMessage(item.getType().toString());
						Settings.sendLang(sender, Lang.NO_SMELT_HAND);
						return true;
					}
					int amount = item.getAmount();
					ItemStack newItem = plugin.getItemStack(a.get(item.getType()), amount);
					p.getInventory().remove(item);
					p.getInventory().addItem(newItem);
					Settings.sendLang(sender, Lang.SMELT_SUCCESS.toString().replace("{AMOUNT}", String.valueOf(amount)));
				}else{
					Settings.Usage(sender, Commands.SMELT);
				}
				
			}else{
				Settings.Usage(sender, Commands.SMELT);
			}
			
		}
	return false;	
	}
	public void loadSmeltableItems(){
		a.put(Material.IRON_ORE, "265");
		a.put(Material.GOLD_ORE, "266");
		a.put(Material.COAL_ORE, "263");
		a.put(Material.DIAMOND_ORE, "264");
		a.put(Material.EMERALD_ORE, "388");
		a.put(Material.REDSTONE_ORE, "331");
		a.put(Material.LAPIS_ORE, "351:4");
		a.put(Material.QUARTZ_ORE, "406");
		a.put(Material.LOG, "263:1");
		a.put(Material.LOG_2, "263:1");
		a.put(Material.NETHERRACK, "405");
		a.put(Material.CLAY_BALL, "336");
		a.put(Material.CACTUS, "351:2");
		a.put(Material.RAW_BEEF, "364");
		a.put(Material.RAW_FISH, "350");
		a.put(Material.RAW_CHICKEN, "366");
		a.put(Material.POTATO_ITEM, "393");
		a.put(Material.PORK, "320");
		a.put(Material.COBBLESTONE, "1");
		a.put(Material.SAND, "20");

	}

}
