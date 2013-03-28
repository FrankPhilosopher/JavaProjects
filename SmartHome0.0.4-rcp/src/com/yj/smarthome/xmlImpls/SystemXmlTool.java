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
 * ϵͳxml�ļ��Ĳ����� ����ģʽ
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

	//�����µ�ϵͳ����xml�ļ�
	public void newSystemXml() throws Exception {
		File file = new File(FileUtil.SYSTEM_XML);
		if (!file.exists()) {
			file.createNewFile();
		}
		//д��document
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
		//д�뵽�ļ���
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));//ע��һ��Ҫ��utf-8��ʽ
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("�����ļ�" + file.getName() + "ʧ�ܣ�");
		}
	}

	//��ȡϵͳ������Ϣ
	public void readSystemSetting() throws Exception {
		String filePath = FileUtil.SYSTEM_XML;
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("�ļ� " + filePath + " �����ڣ�");//����ͬ���쳣��ʾ
			//����쳣Ҫ����
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");//��ָ���ı��뷽ʽ��ȡ�ļ�
			SAXReader reader = new SAXReader();//ע�⣺�ҵ�eclipseĬ����GBK���룬��xmlȴ��utf-8����ģ�
			Document document = reader.read(isReader);//ֱ��read(file)�ᱨ��
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
			throw new Exception("��ȡ�ļ� " + filePath + " ʧ�ܣ�");//����ͬ���쳣��ʾ
		}
	}

}
