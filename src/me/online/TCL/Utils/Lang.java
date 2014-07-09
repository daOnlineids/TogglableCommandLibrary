package me.online.TCL.Utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {

	PREFIX("prefix", "&c[&6TCL&c]"),
    INVALID_ARGS("invalid-args", "&cInvalid args! For help do /thelp"),
    PLAYER_ONLY("player-only", "&cThat command is for players only!"),
    MUST_BE_NUMBER("must-be-number", "&c{ARG} is not a number!"),
    NO_PERMS("no-perm", "&cNo Permission"),
    PROPER_USAGE("proper-usage", "&cProper Usage: {USAGE}"),
	IMPROPER_PAGE("improper-page", "&c{ARG} is not a proper page number!");
	private String path;
	private String def;
	private static YamlConfiguration LANG;

	/**
	 * Lang enum constructor.
	 * @param path The string path.
	 * @param start The default string.
	 */
	Lang(String path, String start) {
		this.path = path;
		this.def = start;
	}

	/**
	 * Set the {@code YamlConfiguration} to use.
	 * @param config The config to set.
	 */
	public static void setFile(YamlConfiguration config) {
		LANG = config;
	}

	@Override
	public String toString() {
		if (this == PREFIX)
			return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
		return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
	}

	/**
	 * Get the default value of the path.
	 * @return The default value of the path.
	 */
	public String getDefault() {
		return this.def;
	}

	/**
	 * Get the path to the string.
	 * @return The path to the string.
	 */
	public String getPath() {
		return this.path;
	}
}
