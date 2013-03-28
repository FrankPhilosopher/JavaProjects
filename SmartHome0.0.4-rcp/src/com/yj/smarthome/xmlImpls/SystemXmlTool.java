package com.yj.smarthome.xmlImpls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.yj.smarthome.util.FileUtil;
import com.yj.smarthome.util.SystemUtil;

/**
 * 系统xml文件的操作类 单例模式
 * 
 * @author yinger
 * 
 */
public class SystemXmlTool {

	public static SystemXmlTool systemXmlTool;

	private SystemXmlTool() {

	}

	public static SystemXmlTool getInstance() {
		if (systemXmlTool == null) {
			systemXmlTool = new SystemXmlTool();
		}
		return systemXmlTool;
	}

	//创建新的系统配置xml文件
	public void newSystemXml() throws Exception {
		File file = new File(FileUtil.SYSTEM_XML);
		if (!file.exists()) {
			file.createNewFile();
		}
		//写入document
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("system");
		Element element = root.addElement(SystemUtil.SERVERIP_STRING);
		element.setText(SystemUtil.SERVER_IP);
		element = root.addElement(SystemUtil.SERVERPORT_STRING);
		element.setText(String.valueOf(SystemUtil.SERVER_PORT));
		element = root.addElement(SystemUtil.GATEWAYIP_STRING);
		element.setText(SystemUtil.GATEWAY_IP);
		element = root.addElement(SystemUtil.GATEWAYPORT_STRING);
		element.setText(String.valueOf(SystemUtil.GATEWAY_PORT));
		//写入到文件中
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));//注意一定要是utf-8格式
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("创建文件" + file.getName() + "失败！");
		}
	}

	//读取系统配置信息
	public void readSystemSetting() throws Exception {
		String filePath = FileUtil.SYSTEM_XML;
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
			Element node = document.getRootElement();
			Element sip = node.element(SystemUtil.SERVERIP_STRING);
			SystemUtil.SERVER_IP = sip.getText().trim();
			Element sport = node.element(SystemUtil.SERVERPORT_STRING);
			SystemUtil.SERVER_PORT = Integer.parseInt(sport.getText().trim());
			Element gip = node.element(SystemUtil.GATEWAYIP_STRING);
			SystemUtil.GATEWAY_IP = gip.getText().trim();
			Element gport = node.element(SystemUtil.GATEWAYPORT_STRING);
			SystemUtil.GATEWAY_PORT = Integer.parseInt(gport.getText().trim());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("读取文件 " + filePath + " 失败！");//区别不同的异常提示
		}
	}

}
