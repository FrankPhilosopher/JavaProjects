package com.yj.smarthome.beans;

import java.util.ArrayList;
import java.util.List;

import com.yj.smarthome.interfaces.IRenameable;
import com.yj.smarthome.interfaces.IStateable;
import com.yj.smarthome.util.ProtocolUtil;

/**
 * �ҵ���
 * 
 * @author yinger
 * 
 */
public class Appliance implements IRenameable, IStateable {

	public int DeviceType_ID = ProtocolUtil.DEVICETYPE_APPLIANCE;//�ҵ��豸

	public static int STATE_OFF = 0;//����״̬
	public static int STATE_ON = 1;

	public static int COMMAND_ON = 2;//�������������
	public static int COMMAND_OFF = 3;

	private int id;
	private int state = 0;//��ʵ��û��Ĭ��ֵ��

	//client
	private String clientName;
	private String icon = "icon";//������������Ĭ��ֵ

	//server
	private String serverName;

	//�������豸��ͬ���ǣ�����������Ŀ�ǲ�ȷ����
	private List<ApplianceCommand> commands = new ArrayList<ApplianceCommand>();

	//�����������˵�appliance
	public Appliance(int id, String serverName) {
		this.id = id;
		this.serverName = serverName;
	}

	//�ͻ��˵�
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

	//��ס����Ҫ�ı�һ��
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
