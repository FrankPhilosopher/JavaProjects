package com.yj.smarthome.interfaces;

import java.io.File;
import java.util.Map;

import org.dom4j.Document;

/**
 * 处理XML文件的接口
 * 
 * @author yinger
 * 
 */
public interface IXmlTool {

	//加载服务器端的设备，document是xml文件，map是返回结果
	public void loadServerDevices(Document document, Map<Integer, Object> map);

	//加载客户端的灯控设备
	public void loadClinetDevices(Document document, Map<Integer, Object> map);

	//得到设备类型编号
	public int getDeviceTypeId();

	//创建一个新的xml文件，并且保存一个对象实例的信息
	public void createClientControlXml(File file, IRenameable renameObject) throws Exception;

	//添加新的设备到本地的设备类型文件中
	public void writeNewDevice(Document document, IRenameable renameObject);

	//修改指定设备的名称
	public void changeName(Document document, IRenameable renameObject);

	//修改指定设备的状态
	public void changeState(Document document, IStateable stateObject);

}
