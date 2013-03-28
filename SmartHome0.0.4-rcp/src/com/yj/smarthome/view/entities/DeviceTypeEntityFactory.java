package com.yj.smarthome.view.entities;

import java.util.ArrayList;
import java.util.List;

import com.yj.smarthome.editorInput.DoorControlEditorInput;
import com.yj.smarthome.editorInput.LightControlEditorInput;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.util.SystemUtil;

/**
 * 用于生成导航视图中的各项导航的工厂类
 * 
 * @author yinger
 * 
 */
public class DeviceTypeEntityFactory {

	//TODO:这里也是要改进的
	public static List<DeviceTypeEntity> createDeviceTypeEntities() {
		List<DeviceTypeEntity> list = new ArrayList<DeviceTypeEntity>();

		DeviceTypeEntity entity1 = new DeviceTypeEntity(SystemUtil.DEVICENAMEARRAY[ProtocolUtil.DEVICETYPE_LIGHT],
				LightControlEditorInput.lcei);
		DeviceTypeEntity entity2 = new DeviceTypeEntity(SystemUtil.DEVICENAMEARRAY[ProtocolUtil.DEVICETYPE_DOOR],
				DoorControlEditorInput.dcei);

		list.add(entity1);
		list.add(entity2);

		return list;
	}

}
