package beans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import util.ProtocolUtil;

/**
 * �龰ģʽitem
 * 
 * @author yinger
 * 
 */
public class SceneModeItem {

	private int typeId;// �豸����id
	private int deviceId;// �豸id
	private int command_on;// ��
	private int command_off;// ��
	private IntegerProperty state = new SimpleIntegerProperty(ProtocolUtil.STATE_EMPTY);// STATE_EMPTY default

	private String name;
	private int detailType;// ������豸����--�����龰ģʽitem�е�ͼƬ����

	public SceneModeItem() {
	}

	// ��д������equals������Ŀ����Ϊ�˺���ıȽ�// ÿ���޸�ʱҪ�鿴���item�Ƿ�������龰ģʽ��
	public boolean equals(Object obj) {
		if (obj instanceof SceneModeItem) {
			SceneModeItem item = (SceneModeItem) obj;
			if (item.typeId == this.typeId && item.deviceId == this.deviceId) {
				return true;
			}
		}
		return false;
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

	public int getCommand_on() {
		return command_on;
	}

	public void setCommand_on(int command_on) {
		this.command_on = command_on;
	}

	public int getCommand_off() {
		return command_off;
	}

	public void setCommand_off(int command_off) {
		this.command_off = command_off;
	}

	public void setState(int value) {
		state.set(value);
	}

	public int getState() {
		return state.get();
	}

	public IntegerProperty stateProperty() {
		return state;
	}

	public int getDetailType() {
		return detailType;
	}

	public void setDetailType(int detailType) {
		this.detailType = detailType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
