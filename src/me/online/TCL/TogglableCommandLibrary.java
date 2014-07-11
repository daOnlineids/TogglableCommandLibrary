package me.online.TCL;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import me.online.TCL.Commands.TCLFly;
import me.online.TCL.Commands.TCLHelp;
import me.online.TCL.Commands.TCLReload;
import me.online.TCL.Commands.TCLSmelt;
import me.online.TCL.Commands.TCLStaff;
import me.online.TCL.Utils.Commands;
import me.online.TCL.Utils.Lang;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class TogglableCommandLibrary extends JavaPlugin{
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		getCommand("thelp").setExecutor(new TCLHelp(this));
		getCommand("fly").setExecutor(new TCLFly(this));
		getCommand("treload").setExecutor(new TCLReload(this));
		getCommand("staff").setExecutor(new TCLStaff(this));
		getCommand("smelt").setExecutor(new TCLSmelt(this));
		Bukkit.getPluginManager().registerEvents(new Listeners(this), this);
		loadLang();
		if (customConfig == null) {
			reloadEnabledCommands();
		}
		loadEnabledCommands();
		saveEnabledCommands();
		if (customConfig1 == null) {
			reloadPlayerData();
		}
		savePlayerData();
		if (customConfig2 == null) {
			reloadSettings();
		}
		customConfig2.options().copyDefaults(true);
		saveSettings();
	}
	public static YamlConfiguration LANG;
	public static File LANG_FILE;
	@SuppressWarnings("static-access")
	public void loadLang() {
	    File lang = new File(getDataFolder(), "lang.yml");
	    if (!lang.exists()) {
	        try {
	            getDataFolder().mkdir();
	            lang.createNewFile();
	            InputStream defConfigStream = this.getResource("lang.yml");
	            if (defConfigStream != null) {
	                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	                defConfig.save(lang);
	                Lang.setFile(defConfig);
	                return;
	            }
	        } catch(IOException e) {
	            e.printStackTrace(); // So they notice
	            getLogger().severe("[TogglableCommandLibrary] Couldn't create language file.");
	            getLogger().severe("[TogglableCommandLibrary] This is a fatal error. Now disabling");
	            this.setEnabled(false); // Without it loaded, we can't send them messages
	        }
	    }
	    YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
	    for(Lang item:Lang.values()) {
	        if (conf.getString(item.getPath()) == null) {
	            conf.set(item.getPath(), item.getDefault());
	        }
	    }
	    Lang.setFile(conf);
	    this.LANG = conf;
	    this.LANG_FILE = lang;
	    try {
	        conf.save(getLangFile());
	    } catch(IOException e) {
	    	getLogger().log(Level.WARNING, "TogglableCommandLibrary: Failed to save lang.yml.");
	        getLogger().log(Level.WARNING, "TogglableCommandLibrary: Report this stack trace to <your name>.");
	        e.printStackTrace();
	    }
	}
	public YamlConfiguration getLang() {
	    return LANG;
	}
	 
	/**
	* Get the lang.yml file.
	* @return The lang.yml file.
	*/
	public File getLangFile() {
	    return LANG_FILE;
	}

	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	private FileConfiguration customConfig1 = null;
	private File customConfigFile1 = null;
	private FileConfiguration customConfig2 = null;
	private File customConfigFile2 = null;
	public void reloadEnabledCommands() {
		if (customConfigFile == null) {
			customConfigFile = new File(getDataFolder(), "enabledCommands.yml");
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

		InputStream defConfigStream = this.getResource("enabledCommands.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			customConfig.setDefaults(defConfig);
		}
	}
	public FileConfiguration getEnabledCommands() {
		if (customConfig == null) {
			reloadEnabledCommands();
		}
		return customConfig;
	}
	public void saveEnabledCommands() {
		if (customConfig == null || customConfigFile == null) {
			return;
		}
		try {
			getEnabledCommands().save(customConfigFile);
		} catch (IOException ex) {
			getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
		}
	}
	public void loadEnabledCommands(){
		for(Commands cmd : Commands.values()){
			String path = cmd.toString().toLowerCase() + "-enabled";
			if (getEnabledCommands().get(path) == null) {
				getEnabledCommands().set(path, true);
	        }
		}
	}
	public boolean isEnabled(Commands cmd){
		String path = cmd.toString().toLowerCase() + "-enabled";
		if(getEnabledCommands().getBoolean(path)){
			return true;
		}else{
			return false;
		}
	}
	public void reloadPlayerData() {
		if (customConfigFile1 == null) {
			customConfigFile1 = new File(getDataFolder(), "playerData.yml");
		}
		customConfig1 = YamlConfiguration.loadConfiguration(customConfigFile1);

		InputStream defConfigStream = this.getResource("playerData.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			customConfig1.setDefaults(defConfig);
		}
	}
	public FileConfiguration getPlayerData() {
		if (customConfig1 == null) {
			reloadPlayerData();
		}
		return customConfig1;
	}
	public void savePlayerData() {
		if (customConfig1 == null || customConfigFile1 == null) {
			return;
		}
		try {
			getPlayerData().save(customConfigFile1);
		} catch (IOException ex) {
			getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile1, ex);
		}
	}
	public void reloadSettings() {
		if (customConfigFile2 == null) {
			customConfigFile2 = new File(getDataFolder(), "settings.yml");
		}
		customConfig2 = YamlConfiguration.loadConfiguration(customConfigFile2);

		InputStream defConfigStream = this.getResource("settings.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			customConfig2.setDefaults(defConfig);
		}
	}
	public FileConfiguration getSettings() {
		if (customConfig2 == null) {
			reloadSettings();
		}
		return customConfig2;
	}
	public void saveSettings() {
		if (customConfig2 == null || customConfigFile2 == null) {
			return;
		}
		try {
			getSettings().save(customConfigFile2);
		} catch (IOException ex) {
			getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile2, ex);
		}
	}
	public Map<Material, String> smeltableItems = new HashMap<Material, String>();
	
	@SuppressWarnings("deprecation")
	public ItemStack getItemStack(String typeId, int amount){
		if(typeId.contains(":")){
			String id = typeId.split(":")[0];
			String data = typeId.split(":")[1];
			if(!isNumber(data)) return null;
			if(!isNumber(id)) return null;
			if(Material.getMaterial(Integer.parseInt(id)) == null) return null;
			ItemStack i = new ItemStack(Material.getMaterial(Integer.parseInt(id)), amount);
			i.setDurability((short) Short.parseShort(data));
			return i;
		}else{
			String id = typeId;
			if(!isNumber(id)) return null;
			if(Material.getMaterial(Integer.parseInt(id)) == null) return null;
			ItemStack i = new ItemStack(Material.getMaterial(Integer.parseInt(id)), amount);
			return i;
		}
	}
	public boolean isNumber(String s){
		try{
			Integer.parseInt(s);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
}
