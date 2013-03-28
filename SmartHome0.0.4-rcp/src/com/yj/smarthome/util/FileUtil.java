package com.yj.smarthome.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import org.eclipse.swt.graphics.Image;

/**
 * 专门用来处理文件的工具类<br/>
 * 同时它还保存了本项目的很多和文件及文件夹有关的固定的字符串
 * 
 * @author yinger
 * 
 */
public class FileUtil {

	public static HashMap<Integer, String> DEVICEID_XML;//它用来决定哪一类的设备id取什么名字的xml文件
	public static final String DOWN_DIRECTORY = "down" + File.separator;
	public static final String LOCAL_DIRECTORY = "local" + File.separator;
	public static final String SCENE_DIRECTORY = "scene" + File.separator;
	public static final String VERSION_XML = "version.xml";
	public static final String STATE_XML = "state.xml";
	public static final String SYSTEM_XML = "system.xml";//系统的配置文件

	//静态代码块，集中管理文件名，对比于分散管理，可以放在各自的bean中
	static {
		DEVICEID_XML = new HashMap<Integer, String>();
//		DeviceId_XmlFile.put(Version_Xml, "version.xml");
//		DeviceId_XmlFile.put(State_Xml, "state.xml");
		DEVICEID_XML.put(ProtocolUtil.DEVICETYPE_LIGHT, "lightcontrolview.xml");//2
		DEVICEID_XML.put(ProtocolUtil.DEVICETYPE_DOOR, "doorwindowsview.xml");//3
		DEVICEID_XML.put(ProtocolUtil.DEVICETYPE_SECURITY, "securityview.xml");//4
		DEVICEID_XML.put(ProtocolUtil.DEVICETYPE_SENSOR, "sensorview.xml");//5
		DEVICEID_XML.put(ProtocolUtil.DEVICETYPE_APPLIANCE, "applianceview.xml");//6
	}

	//得到本地的某中设备的xml文件
	public static String getLocalXmlFile(int id) {
		return LOCAL_DIRECTORY + DEVICEID_XML.get(id);//E:\Java_Study\Eclipse\Eclipse for rcp\eclipse_rcp\local\lightcontrolview.xml
	}

	//得到下载目录中的某种设备的xml文件
	public static String getDownXmlFile(int id) {
		return DOWN_DIRECTORY + DEVICEID_XML.get(id);//E:\Java_Study\Eclipse\Eclipse for rcp\eclipse_rcp\down\lightcontrolview.xml
	}

	//很多的静态方法都是为了方便维护的方便
	//得到状态文件，它一定是存放在down文件夹中  TODO：如果某一次下载没有成功，导致这个文件没了怎么办？状态默认值
	public static String getStateXmlFile() {
		return DOWN_DIRECTORY + STATE_XML;
	}

	//得到down中的version.xml
	public static String getDownVersionXmlFile() {
		return DOWN_DIRECTORY + VERSION_XML;
	}

	//得到local中的version.xml
	public static String getLocalVersionXmlFile() {
		return LOCAL_DIRECTORY + VERSION_XML;
	}

	//test
	public static void main(String[] args) {
//		try {
//			copyFile("down/state.xml", "local/state.xml");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		String fileName = FileUtil.DEVICEID_XML.get(ProtocolUtil.DEVICETYPE_LIGHT);
		System.out.println(fileName.substring(0, fileName.indexOf(".xml")));//lightcontrolview

	}

	//保存图像到指定的位置
	public static void saveImage(String path, Image image) {

	}

	//复制文件（源文件，目标文件）
	public static void copyFile(String srcFile, String desFile) throws Exception {
		try {
			FileInputStream fis = new FileInputStream(srcFile);//如果原文件不存在的话会抛出异常
			FileOutputStream fos = new FileOutputStream(desFile);//如果目标文件不存在的话会自动新建一个文件
			byte[] bytes = new byte[1024];
			int len = fis.read(bytes);
			while (len != -1) {//eof 返回值是-1，而不是0
				fos.write(bytes, 0, len);
				len = fis.read(bytes);
			}
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("文件" + srcFile + "复制失败!");
		}
	}

}
