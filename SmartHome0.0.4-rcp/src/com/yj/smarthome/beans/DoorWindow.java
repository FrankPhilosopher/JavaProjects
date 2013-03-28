package com.yj.smarthome.beans;

import com.yj.smarthome.interfaces.IRenameable;
import com.yj.smarthome.interfaces.IStateable;
import com.yj.smarthome.util.ProtocolUtil;

/**
 * �Ŵ�ʵ����
 * 
 * @author yinger
 * 
 */
public class DoorWindow implements IRenameable, IStateable {

	public int DeviceType_ID = ProtocolUtil.DEVICETYPE_DOOR;//�ſ��豸

	public static int STATE_OFF = 0;//����״̬
	public static int STATE_ON = 1;

	private int id;
	private int state = 0;//��ʵ��û��Ĭ��ֵ��

	//client
	private String clientName;
	private String icon = "icon";

	//server
	private String serverName;
	private int command_buttonon;
	private int command_buttonoff;
	private int command_buttonstop;

	//������������doorwindow
	public DoorWindow(int id, String serverName, int command_buttonon, int command_buttonoff, int command_buttonstop) {
		this.id = id;
		this.serverName = serverName;
		this.command_buttonon = command_buttonon;
		this.command_buttonoff = command_buttonoff;
		this.command_buttonstop = command_buttonstop;
	}

	//����client�˵�doorwindow
	public DoorWindow(int id, String clientName, String icon) {
		this.id = id;
		this.clientName = clientName;
		this.icon = icon;
	}

	//����client��doorwindow����
	public void setClientDoorWindow(String clientName, String icon) {
		this.clientName = clientName;
		this.icon = icon;
	}

	@Override
	//�������һ��Ҫ��
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//�������һ��Ҫ��
	@Override
	public int getState() {
		return state;
	}

	//�������һ��Ҫ��
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

	//clientname��û�е�������Ǻ�servernameһ���ģ�
	@Override
	public String getClientName() {
		if (null == clientName || "".equalsIgnoreCase(clientName)) {
			this.clientName = this.serverName;
		}
		return this.clientName;
	}

}
