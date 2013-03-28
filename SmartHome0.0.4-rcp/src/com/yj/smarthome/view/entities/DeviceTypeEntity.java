package com.yj.smarthome.view.entities;

import org.eclipse.ui.IEditorInput;

/**
 * ������ͼ�е��豸����ʵ��
 * 
 * @author yinger
 */
public class DeviceTypeEntity {

	private String name;//�豸����

	private IEditorInput input;//��Ӧ��IEditorInput

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
