package me.online.TCL;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import me.online.TCL.Commands.TCLHelp;
import me.online.TCL.Utils.Commands;
import me.online.TCL.Utils.Lang;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class TogglableCommandLibrary extends JavaPlugin{
	@Override
	public void onEnable() {
		getCommand("thelp").setExecutor(new TCLHelp(this));
		loadLang();
		if (customConfig == null) {
			reloadEnabledCommands();
		}
		loadEnabledCommands();
		saveEnabledCommands();
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
}
