package me.online.TCL;

import java.util.ArrayList;
import java.util.List;

import me.online.TCL.Utils.Lang;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener{
	private TogglableCommandLibrary plugin;
	public Listeners(TogglableCommandLibrary instance){
		this.plugin = instance;
	}
	public List<String> falling = new ArrayList<String>();
	@EventHandler
	public void onInvalidCommand(PlayerCommandPreprocessEvent e) {
		String command = e.getMessage().split(" ")[0];
		if(Bukkit.getHelpMap().getHelpTopic(command) == null) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Lang.UNKNOWN_COMMAND.toString());
		}
	}
	@EventHandler
	public void onFall(PlayerMoveEvent e) {
		if(e.getFrom().getBlockY() > e.getTo().getBlockY()) {
			if(e.getTo().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
				if(falling.contains(e.getPlayer().getName())){
					falling.remove(e.getPlayer().getName());
					e.getPlayer().setAllowFlight(true);
				}
			}
		}
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		ConfigurationSection pcs = plugin.getPlayerData().getConfigurationSection(e.getPlayer().getUniqueId().toString());
		boolean inFlight = false;
		if(pcs != null){
			inFlight = pcs.getBoolean("flying");
		}else{
			pcs = plugin.getPlayerData().createSection(e.getPlayer().getUniqueId().toString());
			pcs.set("flying", false);
			pcs.set("ghost", false);
			pcs.set("godmode", false);
			plugin.savePlayerData();
		}
		e.getPlayer().setAllowFlight(inFlight);
		if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
			falling.add(e.getPlayer().getName());
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		boolean removeFly = false;
		FileConfiguration cs = plugin.getSettings();
		ConfigurationSection pcs = plugin.getPlayerData().getConfigurationSection(e.getPlayer().getUniqueId().toString());
		boolean inFlight = false;
		if(cs != null){
			removeFly = cs.getBoolean("remove-fly-on-leave");
		}
		if(pcs != null){
			inFlight = pcs.getBoolean("flying");
		}
		if(removeFly){
			if(inFlight){
				pcs.set("flying", false);
				plugin.savePlayerData();
			}

		}
	}
}
