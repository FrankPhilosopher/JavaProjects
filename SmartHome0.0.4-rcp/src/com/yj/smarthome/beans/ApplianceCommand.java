package com.yj.smarthome.beans;

/**
 * �ҵ��Ӧ��������
 * 
 * @author yinger
 * 
 */
public class ApplianceCommand {
	private String id;//�����id
	private String name;//���������
	private String command;//������

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