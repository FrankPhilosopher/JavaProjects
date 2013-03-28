package com.yj.smarthome.beans;

import com.yj.smarthome.interfaces.IRenameable;
import com.yj.smarthome.interfaces.IStateable;
import com.yj.smarthome.util.ProtocolUtil;

/**
 * 真正的的完整的灯控按钮
 * 
 * @author yinger
 * 
 */
public class LightButton implements IRenameable, IStateable {

	public int DeviceType_ID = ProtocolUtil.DEVICETYPE_LIGHT;//灯控设备

	public static int STATE_OFF = 0;//两种状态
	public static int STATE_ON = 1;

	public static int INTENSITY_NO = 0;//是否存在亮度调节
	public static int INTENSITY_YES = 1;

	private int id;
	private int state = 0;//其实是没有默认值的

	//client
	private String clientName;
	private String icon = "icon";//这两个都给个默认值
	private int intensity = 100;

	//server
	private String serverName;
	private int hasIntensity;
	private int command_buttonup;
	private int command_buttondown;
	private int command_buttonon;
	private int command_buttonoff;

	//构建client模式的灯控
	public LightButton(int id, String clientName, String icon, int intensity) {
		this.id = id;
		this.clientName = clientName;
		this.icon = icon;
		this.intensity = intensity;
	}

	//构建server模式的灯控
	public LightButton(int id, String serverName, int hasIntensity, int command_buttonup, int command_buttondown, int command_buttonon,
			int command_buttonoff) {
		this.id = id;
		this.serverName = serverName;
		this.hasIntensity = hasIntensity;
		this.command_buttonup = command_buttonup;
		this.command_buttondown = command_buttondown;
		this.command_buttonon = command_buttonon;
		this.command_buttonoff = command_buttonoff;
	}

	//设置client模式的灯控
	public void setClientLightButton(String clientName, String icon, int intensity) {
		setIcon(icon);
		setIntensity(intensity);
		setClientName(clientName);
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

	//clientname在没有的情况下是和servername一样的！
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

	//icon有一个默认值
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
//		if (null == icon || "".equalsIgnoreCase(icon)) {
//			icon = new String("icon");
//		}
		this.icon = icon;
	}

	//intensity有个默认值
	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
//		if (intensity == 0) {
//			intensity = 100;
//		}
		this.intensity = intensity;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getHasIntensity() {
		return hasIntensity;
	}

	public void setHasIntensity(int hasIntensity) {
		this.hasIntensity = hasIntensity;
	}

	public int getCommand_buttonup() {
		return command_buttonup;
	}

	public void setCommand_buttonup(int command_buttonup) {
		this.command_buttonup = command_buttonup;
	}

	public int getCommand_buttondown() {
		return command_buttondown;
	}

	public void setCommand_buttondown(int command_buttondown) {
		this.command_buttondown = command_buttondown;
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

}
