package com.yj.smarthome.beans;

import java.util.List;

/**
 * �龰ģʽ�¶�Ӧ��һ���豸
 * 
 * @author yinger
 * 
 */
public class SceneModeItem {

	private int typeId;//�豸����id
	private int deviceId;//�豸id
	private String deviceName;//�豸���ƣ����ص�
	private List<SceneModeCommand> commands;//�豸����������---�������������Ϊ���Ժ����һ���豸�ж����ͬʱѡ�������

	public SceneModeItem(int typeId, int deviceId, String deviceName, List<SceneModeCommand> commands) {
		this.typeId = typeId;
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.commands = commands;
	}

	@Override
	//��д������equals������Ŀ����Ϊ�˺���ıȽϣ���Ϊlist��contains�����ıȽ��õľ�������������
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
