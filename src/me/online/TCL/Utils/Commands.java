package me.online.TCL.Utils;



public enum Commands {
	
	HELP("/thelp (Page #)", "Help page for TCL", "tcl.help"),
	FLY("/fly (Player)", "Toggle flight", "tcl.fly"),
	SMELT("/smelt <Hand/All>", "Smelt your items", "tcl.smelt"),
	STAFF("/staff <Add/Remove/List/Chat> (#/Player/Message)", "Manage or list server staff", "tcl.staff");
	
	private String usage;
	private String description;
	private String perm;
	Commands(String s, String s1, String s2){
		this.usage = s;
		this.description = s1;
		this.perm = s2;
	}
	public String getUsage(){
		return this.usage;
	}
	public String getPerm(){
		return this.perm;
	}
	public String getDescription(){
		return this.description;
	}

}
