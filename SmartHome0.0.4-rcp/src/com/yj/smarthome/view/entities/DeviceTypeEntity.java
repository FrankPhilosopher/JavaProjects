package com.yj.smarthome.view.entities;

import org.eclipse.ui.IEditorInput;

/**
 * 导航视图中的设备类型实体
 * 
 * @author yinger
 */
public class DeviceTypeEntity {

	private String name;//设备名称

	private IEditorInput input;//对应的IEditorInput

	public DeviceTypeEntity(String name, IEditorInput input) {
		this.name = name;
		this.input = input;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IEditorInput getInput() {
		return input;
	}

	public void setInput(IEditorInput input) {
		this.input = input;
	}

}
