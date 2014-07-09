package me.online.TCL.Utils;



public enum Commands {
	
	HELP("/thelp (Page #)", "Help page for Togglable Command Library", "tcl.help");
	
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
