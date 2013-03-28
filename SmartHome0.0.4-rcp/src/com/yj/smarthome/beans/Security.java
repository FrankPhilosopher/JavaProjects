package com.yj.smarthome.beans;

import com.yj.smarthome.interfaces.IRenameable;
import com.yj.smarthome.interfaces.IStateable;
import com.yj.smarthome.util.ProtocolUtil;

/**
 * 安防设备类
 * 
 * @author yinger
 * 
 */
public class Security implements IRenameable, IStateable {

	public int DeviceType_ID = ProtocolUtil.DEVICETYPE_SECURITY;//安防设备

	public static int STATE_OFF = 0;//两种状态
	public static int STATE_ON = 1;

	private int id;
	private int state = 0;//其实是没有默认值的

	//client
	private String clientName;
	private String icon = "icon";

	//server
	private String serverName;
	private int command_open;
	private int command_close;

	//构建服务器端doorwindow
	public Security(int id, String serverName, int command_open, int command_close) {
		this.id = id;
		this.serverName = serverName;
		this.command_open = command_open;
		this.command_close = command_close;
	}

	//构建client端的doorwindow
	public Security(int id, String clientName, String icon) {
		this.id = id;
		this.clientName = clientName;
		this.icon = icon;
	}

	//设置client端doorwindow参数
	public void setClientSecurity(String clientName, String icon) {
		this.clientName = clientName;
		this.icon = icon;
	}

	@Override
	//这个方法一定要有
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//这个方法一定要有
	@Override
	public int getState() {
		return state;
	}

	//这个方法一定要有
	@Override
	public void setState(int state) {
		this.state = state;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getCommand_open() {
		return command_open;
	}

	public void setCommand_open(int command_open) {
		this.command_open = command_open;
	}

	public int getCommand_close() {
		return command_close;
	}

	public void setCommand_close(int command_close) {
		this.command_close = command_close;
	}

	@Override
	public void setClientName(String name) {
		this.clientName = name;
	}

	//clientname在没有的情况下是和servername一样的！
	@Override
	public String getClientName() {
		if (null == clientName || "".equalsIgnoreCase(clientName)) {
			this.clientName = this.serverName;
		}
		return this.clientName;
	}

}
