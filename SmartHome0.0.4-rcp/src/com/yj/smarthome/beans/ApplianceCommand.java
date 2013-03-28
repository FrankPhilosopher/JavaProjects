package com.yj.smarthome.beans;

/**
 * 家电对应的命令类
 * 
 * @author yinger
 * 
 */
public class ApplianceCommand {
	private String id;//命令的id
	private String name;//命令的名称
	private String command;//命令码

	public ApplianceCommand(String id, String name, String command) {
		this.id = id;
		this.name = name;
		this.command = command;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}