package com.yj.smarthome.beans;

import java.util.ArrayList;
import java.util.List;

import com.yj.smarthome.interfaces.IRenameable;

/**
 * 情景模式<br/>
 * 一种情景模式对应一个文件，名称是可以修改的
 * 
 * @author yinger
 * 
 */
public class SceneMode implements IRenameable {

	private String name;//情景模式名称
	private List<SceneModeItem> items = new ArrayList<SceneModeItem>();//该情景模式下的设备项，这里保证了items不是null

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
