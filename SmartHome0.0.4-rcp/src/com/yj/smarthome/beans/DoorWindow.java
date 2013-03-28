package com.yj.smarthome.beans;

import com.yj.smarthome.interfaces.IRenameable;
import com.yj.smarthome.interfaces.IStateable;
import com.yj.smarthome.util.ProtocolUtil;

/**
 * 门窗实体类
 * 
 * @author yinger
 * 
 */
public class DoorWindow implements IRenameable, IStateable {

	public int DeviceType_ID = ProtocolUtil.DEVICETYPE_DOOR;//门控设备

	public static int STATE_OFF = 0;//两种状态
	public static int STATE_ON = 1;

	private int id;
	private int state = 0;//其实是没有默认值的

	//client
	private String clientName;
	private String icon = "icon";

	//server
	private String serverName;
	private int command_buttonon;
	private int command_buttonoff;
	private int command_buttonstop;

	//构建服务器端doorwindow
	public DoorWindow(int id, String serverName, int command_buttonon, int command_buttonoff, int command_buttonstop) {
		this.id = id;
		this.serverName = serverName;
		this.command_buttonon = command_buttonon;
		this.command_buttonoff = command_buttonoff;
		this.command_buttonstop = command_buttonstop;
	}

	//构建client端的doorwindow
	public DoorWindow(int id, String clientName, String icon) {
		this.id = id;
		this.clientName = clientName;
		this.icon = icon;
	}

	//设置client端doorwindow参数
	public void setClientDoorWindow(String clientName, String icon) {
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

	public int getCommand_buttonon() {
		return command_buttonon;
	}

	public void setCommand_buttonon(int command_buttonon) {
		this.command_buttonon = command_buttonon;
	}

	public int getCommand_buttonoff() {
		return command_buttonoff;
	}

	public void setCommand_buttonoff(int command_buttonoff) {
		this.command_buttonoff = command_buttonoff;
	}

	public int getCommand_buttonstop() {
		return command_buttonstop;
	}

	public void setCommand_buttonstop(int command_buttonstop) {
		this.command_buttonstop = command_buttonstop;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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
