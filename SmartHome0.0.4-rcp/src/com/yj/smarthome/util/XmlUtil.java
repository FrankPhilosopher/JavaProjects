package com.yj.smarthome.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.yj.smarthome.interfaces.IRenameable;
import com.yj.smarthome.interfaces.IStateable;
import com.yj.smarthome.interfaces.IXmlTool;

/**
 * XML文件处理的工具类
 * 
 * @author yinger
 */
public class XmlUtil {

	//加载设备
	public static Map<Integer, Object> loadDevices(IXmlTool xmlTool) throws Exception {
		Map<Integer, Object> map = new HashMap<Integer, Object>();//编号与设备的映射表
		loadDevicesFromDown(xmlTool, map);//两者的顺序要发生改变了！
		if (map.isEmpty()) {//如果map是空的话，说明本地的没有读的必要了
			return map;
		}
		loadDevicesFromLocal(xmlTool, map);
		return map;
	}

	//从down文件中加载设备
	private static void loadDevicesFromDown(IXmlTool xmlTool, Map<Integer, Object> map) throws Exception {
		String filePath = FileUtil.getDownXmlFile(xmlTool.getDeviceTypeId());
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("文件 " + filePath + " 不存在！");//区别不同的异常提示
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();//注意：我的eclipse默认是GBK编码，而xml却是utf-8编码的！
			Document document = reader.read(isReader);//直接read(file)会报错！

			//上一个版本是要判断设备类型的，但是这里不用了，传过来就知道是谁来处理了
			xmlTool.loadServerDevices(document, map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("读取文件" + filePath + "失败！");
		}
	}

	//从local文件中加载设备
	private static void loadDevicesFromLocal(IXmlTool xmlTool, Map<Integer, Object> map) throws Exception {
		String filePath = FileUtil.getLocalXmlFile(xmlTool.getDeviceTypeId());
		File file = new File(filePath);
		if (!file.exists()) {
//			throw new Exception("文件 " + filePath + " 不存在！");//区别不同的异常提示
			return;//读取本地文件，如果不存在的话那么就返回，并不抛出异常！
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");//用指定的编码方式读取文件
			SAXReader reader = new SAXReader();//注意：我的eclipse默认是GBK编码，而xml却是utf-8编码的！
			Document document = reader.read(isReader);//直接read(file)会报错！

			xmlTool.loadClinetDevices(document, map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("读取文件" + filePath + "失败！");
		}
	}

	//修改客户端设备的名称
	public static void changeDeviceName(IXmlTool xmlTool, IRenameable renameObject) throws Exception {
		String filePath = FileUtil.getLocalXmlFile(xmlTool.getDeviceTypeId());
		File file = new File(filePath);
		if (!file.exists()) {
//			throw new Exception("文件 " + filePath + " 不存在！");
			//如果保存时文件不存在的话就要创建一个文件，并且写入相应的内容
			file.createNewFile();
			xmlTool.createClientControlXml(file, renameObject);
		} else {//如果文件存在的话，那么就是修改文件中的信息
			System.out.println(filePath);
			try {
				InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
				SAXReader reader = new SAXReader();
				Document document = reader.read(isReader);

				xmlTool.changeName(document, renameObject);

				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
				writer.write(document);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("修改文件" + file.getName() + "失败！");
			}
		}
	}

	//从version.xml中读取出要下载的xml文件
	public static void getDownloadFilesFromVersion(List<String> files) throws Exception {
		String filePath = FileUtil.getDownVersionXmlFile();
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("文件 " + filePath + " 不存在！");//区别不同的异常提示
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");//用指定的编码方式读取文件
			SAXReader reader = new SAXReader();//注意：我的eclipse默认是GBK编码，而xml却是utf-8编码的！
			Document document = reader.read(isReader);//直接read(file)会报错！
//			List list = document.selectNodes("//version");
//			Node node = document.selectSingleNode("//version");
//			document.getRootElement();
			Element root = document.getRootElement();
			List list = root.elements();
//			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.size(); i++) {
				Element subnode = (Element) list.get(i);
				//首先得到id，只有id以view结尾才处理
				String id = subnode.attributeValue("id");
				if (id.endsWith("view")) {//以view结尾，加上.xml就可以得到文件名
					files.add(id + ".xml");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("读取文件" + filePath + "失败！");
		}
	}

	//初始化所有设备的状态
	public static void initDeviceState() throws Exception {
		String filePath = FileUtil.getStateXmlFile();
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("文件 " + filePath + " 不存在！");//区别不同的异常提示
			//这个异常要考虑
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");//用指定的编码方式读取文件
			SAXReader reader = new SAXReader();//注意：我的eclipse默认是GBK编码，而xml却是utf-8编码的！
			Document document = reader.read(isReader);//直接read(file)会报错！

			//读取所有设备的状态！这里是灯控
			List lightList = document.selectNodes("//state/lightcontrolview/lightbutton");
			for (int i = 0; i < lightList.size(); i++) {
				Element node = (Element) lightList.get(i);
				int id = Integer.parseInt(node.attributeValue("id"));
				int state = Integer.parseInt(node.getText().trim());
				SystemUtil.ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_LIGHT][id] = state;//修改设备的状态值
			}

			//这里是门窗
			List doorList = document.selectNodes("//state/doorwindowsview/doorwindow");
			for (int i = 0; i < doorList.size(); i++) {
				Element node = (Element) doorList.get(i);
				int id = Integer.parseInt(node.attributeValue("id"));
				int state = Integer.parseInt(node.getText().trim());
				SystemUtil.ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_DOOR][id] = state;//修改设备的状态值
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("读取文件" + filePath + "失败！");
		}
	}

	//修改设备的状态
	public static void changeDeviceState(IXmlTool xmlTool, IStateable stateObject) throws Exception {
		//首先修改一下systemutil中数组的状态
		SystemUtil.ALL_DEVICE_STATE[xmlTool.getDeviceTypeId()][stateObject.getId()] = stateObject.getState();

		//然后再将状态保存到文件中
		String filePath = FileUtil.getStateXmlFile();
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("文件 " + filePath + " 不存在！");//区别不同的异常提示
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);

			xmlTool.changeState(document, stateObject);

			/* 格式化输出，指定XML编码，貌似格式化没有起到作用 */
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			writer.write(document);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//比较某一种设备类型的版本号
	public static int compareVersion(int deviceTypeId) throws Exception {
		String fileName = FileUtil.DEVICEID_XML.get(deviceTypeId);
		String subElement = fileName.substring(0, fileName.indexOf(".xml"));
		String filePath = FileUtil.getDownVersionXmlFile();
		int oldVersion = getDeviceVersion(subElement, FileUtil.getLocalVersionXmlFile());
		int newVersion = getDeviceVersion(subElement, FileUtil.getDownVersionXmlFile());
		if (oldVersion < newVersion) {
			return 1;//如果是版本要更新，返回1
		} else if (oldVersion == newVersion) {
			return 0;//如果版本相同，返回0
		} else {
//			return -1;//这种情况出现了异常！
			throw new Exception("版本更新时出现了异常！");
		}
	}

	//从一个version.xml文件中得到某个设备类型的版本
	private static int getDeviceVersion(String subElement, String filePath) throws Exception {
		int version = 0;
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("文件 " + filePath + " 不存在！");//区别不同的异常提示
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");//用指定的编码方式读取文件
			SAXReader reader = new SAXReader();//注意：我的eclipse默认是GBK编码，而xml却是utf-8编码的！
			Document document = reader.read(isReader);//直接read(file)会报错！
//			List list = document.selectNodes("//version");
//			Node node = document.selectSingleNode("//version");
//			document.getRootElement();
			Element root = document.getRootElement();
			List list = root.elements();

//			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.size(); i++) {
				Element subnode = (Element) list.get(i);
				String id = subnode.attributeValue("id");
				if (subElement.equalsIgnoreCase(id)) {//找到了这类设备的节点
					version = Integer.valueOf(subnode.getText().trim());//得到它的版本号
					break;//可以停止了
				}
			}
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("读取文件" + filePath + "失败！");
		}
	}

}
