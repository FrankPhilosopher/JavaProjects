package com.yj.smarthome.beans;

import java.util.List;

/**
 * 情景模式下对应的一个设备
 * 
 * @author yinger
 * 
 */
public class SceneModeItem {

	private int typeId;//设备类型id
	private int deviceId;//设备id
	private String deviceName;//设备名称，本地的
	private List<SceneModeCommand> commands;//设备的所有命令---这里这样设计是为了以后出现一个设备有多个可同时选择的命令

	public SceneModeItem(int typeId, int deviceId, String deviceName, List<SceneModeCommand> commands) {
		this.typeId = typeId;
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.commands = commands;
	}

	@Override
	//重写这个类的equals方法，目的是为了后面的比较，因为list的contains方法的比较用的就是这个方法结果
	public boolean equals(Object obj) {
		if (obj instanceof SceneModeItem) {
			SceneModeItem item = (SceneModeItem) obj;
			if (item.typeId == this.typeId && item.deviceId == this.deviceId) {
				return true;
			}
		}
		return false;
	}

	public List<SceneModeCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<SceneModeCommand> commands) {
		this.commands = commands;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

}
