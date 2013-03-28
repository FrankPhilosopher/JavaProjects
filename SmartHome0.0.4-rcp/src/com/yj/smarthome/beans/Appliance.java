package com.yj.smarthome.beans;

import java.util.ArrayList;
import java.util.List;

import com.yj.smarthome.interfaces.IRenameable;
import com.yj.smarthome.interfaces.IStateable;
import com.yj.smarthome.util.ProtocolUtil;

/**
 * 家电类
 * 
 * @author yinger
 * 
 */
public class Appliance implements IRenameable, IStateable {

	public int DeviceType_ID = ProtocolUtil.DEVICETYPE_APPLIANCE;//家电设备

	public static int STATE_OFF = 0;//两种状态
	public static int STATE_ON = 1;

	public static int COMMAND_ON = 2;//两种特殊的命令
	public static int COMMAND_OFF = 3;

	private int id;
	private int state = 0;//其实是没有默认值的

	//client
	private String clientName;
	private String icon = "icon";//这两个都给个默认值

	//server
	private String serverName;

	//和其他设备不同的是，它的命令数目是不确定的
	private List<ApplianceCommand> commands = new ArrayList<ApplianceCommand>();

	//构建服务器端的appliance
	public Appliance(int id, String serverName) {
		this.id = id;
		this.serverName = serverName;
	}

	//客户端的
	public Appliance(int id, String clientName, String icon) {
		this.id = id;
		this.clientName = clientName;
		this.icon = icon;
	}

	public void setClientAppliance(String clientName, String icon) {
		setClientName(clientName);
		setIcon(icon);
	}

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getState() {
		return state;
	}

	@Override
	public void setState(int state) {
		this.state = state;
	}

	//记住这里要改变一下
	@Override
	public String getClientName() {
		if (clientName == null || "".equalsIgnoreCase(clientName)) {
			clientName = serverName;
		}
		return clientName;
	}

	@Override
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public List<ApplianceCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<ApplianceCommand> commands) {
		this.commands = commands;
	}

}
