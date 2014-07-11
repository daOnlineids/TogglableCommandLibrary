package me.online.TCL.Utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {

	PREFIX("prefix", "&c[&6TCL&c]"),
    INVALID_ARGS("invalid-args", "&cInvalid args! For help do /thelp"),
    PLAYER_NOT_ONLINE("player-not-online", "&6{PLAYER} &cis not online!"),
    PLAYER_ONLY("player-only", "&cThat command is for players only!"),
    MUST_BE_NUMBER("must-be-number", "&c{ARG} is not a number!"),
    FLIGHT_TOGGLE_OTHERS_ON("flight-toggle-others-on", "&6Flight &aenabled &6for {PLAYER}"),
    FLIGHT_TOGGLE_OTHERS_OFF("flight-toggle-others-off", "&6Flight &cdisabled &6for {PLAYER}"),
    FLIGHT_TOGGLE_ON("flight-toggle-on", "&6Flight &aenabled!"),
    FLIGHT_TOGGLE_OFF("flight-toggle-off", "&6Flight &cdisabled!"),
    NO_PERMS("no-perm", "&cNo Permission"),
    NOT_STAFF("not-staff", "&6{PLAYER} &cis not staff!"),
    STAFF_ADDED("staff-added", "&6{PLAYER} &ais now staff!"),
    STAFF_CHAT_FORMAT("staff-chat-format", "&6{PLAYER}: &3{MESSAGE}"),
    STAFF_REMOVED("staff-removed", "&6{PLAYER} &cis no longer staff!"),
    NEVER_PLAYED("never-played", "&6{PLAYER} &chas never played before!"),
    ALREADY_STAFF("already-staff", "&6{PLAYER} &cis already staff!"),
    PROPER_USAGE("proper-usage", "&cProper Usage: {USAGE}"),
    NO_SMELT_HAND("no-smelt-hand", "&cThe item you are holding isn't smeltable!"),
    NO_SMELT_INVENTORY("no-smelt-inv", "&cYou have no smeltable items!"),
    SMELT_SUCCESS("smelt-success", "&6{AMOUNT} &aitems smelted!"),
    UNKNOWN_COMMAND("unknown-command", "Unknown command. Type 'help' for help."),
	IMPROPER_PAGE("improper-page", "&c{ARG} is not a proper page number!"),
	NO_PERM_FLIGHT_TOGGLE_OTHERS("no-perm-flight-toggle-others", "&cYou don't have permission to toggle flight for others!");
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
