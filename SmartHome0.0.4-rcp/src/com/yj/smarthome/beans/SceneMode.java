package com.yj.smarthome.beans;

import java.util.ArrayList;
import java.util.List;

import com.yj.smarthome.interfaces.IRenameable;

/**
 * �龰ģʽ<br/>
 * һ���龰ģʽ��Ӧһ���ļ��������ǿ����޸ĵ�
 * 
 * @author yinger
 * 
 */
public class SceneMode implements IRenameable {

	private String name;//�龰ģʽ����
	private List<SceneModeItem> items = new ArrayList<SceneModeItem>();//���龰ģʽ�µ��豸����ﱣ֤��items����null

	public SceneMode(String name) {
		this.name = name;
	}

	public SceneMode(String name, List<SceneModeItem> items) {
		this.name = name;
		this.items = items;
	}

	public List<SceneModeItem> getItems() {
		return items;
	}

	public void setItems(List<SceneModeItem> items) {
		this.items = items;
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public void setClientName(String name) {
		this.name = name;
	}

	@Override
	public String getClientName() {
		return name;
	}

}
