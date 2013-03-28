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
 * XML�ļ�����Ĺ�����
 * 
 * @author yinger
 */
public class XmlUtil {

	//�����豸
	public static Map<Integer, Object> loadDevices(IXmlTool xmlTool) throws Exception {
		Map<Integer, Object> map = new HashMap<Integer, Object>();//������豸��ӳ���
		loadDevicesFromDown(xmlTool, map);//���ߵ�˳��Ҫ�����ı��ˣ�
		if (map.isEmpty()) {//���map�ǿյĻ���˵�����ص�û�ж��ı�Ҫ��
			return map;
		}
		loadDevicesFromLocal(xmlTool, map);
		return map;
	}

	//��down�ļ��м����豸
	private static void loadDevicesFromDown(IXmlTool xmlTool, Map<Integer, Object> map) throws Exception {
		String filePath = FileUtil.getDownXmlFile(xmlTool.getDeviceTypeId());
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("�ļ� " + filePath + " �����ڣ�");//����ͬ���쳣��ʾ
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();//ע�⣺�ҵ�eclipseĬ����GBK���룬��xmlȴ��utf-8����ģ�
			Document document = reader.read(isReader);//ֱ��read(file)�ᱨ��

			//��һ���汾��Ҫ�ж��豸���͵ģ��������ﲻ���ˣ���������֪����˭��������
			xmlTool.loadServerDevices(document, map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");
		}
	}

	//��local�ļ��м����豸
	private static void loadDevicesFromLocal(IXmlTool xmlTool, Map<Integer, Object> map) throws Exception {
		String filePath = FileUtil.getLocalXmlFile(xmlTool.getDeviceTypeId());
		File file = new File(filePath);
		if (!file.exists()) {
//			throw new Exception("�ļ� " + filePath + " �����ڣ�");//����ͬ���쳣��ʾ
			return;//��ȡ�����ļ�����������ڵĻ���ô�ͷ��أ������׳��쳣��
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");//��ָ���ı��뷽ʽ��ȡ�ļ�
			SAXReader reader = new SAXReader();//ע�⣺�ҵ�eclipseĬ����GBK���룬��xmlȴ��utf-8����ģ�
			Document document = reader.read(isReader);//ֱ��read(file)�ᱨ��

			xmlTool.loadClinetDevices(document, map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");
		}
	}

	//�޸Ŀͻ����豸������
	public static void changeDeviceName(IXmlTool xmlTool, IRenameable renameObject) throws Exception {
		String filePath = FileUtil.getLocalXmlFile(xmlTool.getDeviceTypeId());
		File file = new File(filePath);
		if (!file.exists()) {
//			throw new Exception("�ļ� " + filePath + " �����ڣ�");
			//�������ʱ�ļ������ڵĻ���Ҫ����һ���ļ�������д����Ӧ������
			file.createNewFile();
			xmlTool.createClientControlXml(file, renameObject);
		} else {//����ļ����ڵĻ�����ô�����޸��ļ��е���Ϣ
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
				throw new Exception("�޸��ļ�" + file.getName() + "ʧ�ܣ�");
			}
		}
	}

	//��version.xml�ж�ȡ��Ҫ���ص�xml�ļ�
	public static void getDownloadFilesFromVersion(List<String> files) throws Exception {
		String filePath = FileUtil.getDownVersionXmlFile();
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("�ļ� " + filePath + " �����ڣ�");//����ͬ���쳣��ʾ
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");//��ָ���ı��뷽ʽ��ȡ�ļ�
			SAXReader reader = new SAXReader();//ע�⣺�ҵ�eclipseĬ����GBK���룬��xmlȴ��utf-8����ģ�
			Document document = reader.read(isReader);//ֱ��read(file)�ᱨ��
//			List list = document.selectNodes("//version");
//			Node node = document.selectSingleNode("//version");
//			document.getRootElement();
			Element root = document.getRootElement();
			List list = root.elements();
//			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.size(); i++) {
				Element subnode = (Element) list.get(i);
				//���ȵõ�id��ֻ��id��view��β�Ŵ���
				String id = subnode.attributeValue("id");
				if (id.endsWith("view")) {//��view��β������.xml�Ϳ��Եõ��ļ���
					files.add(id + ".xml");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");
		}
	}

	//��ʼ�������豸��״̬
	public static void initDeviceState() throws Exception {
		String filePath = FileUtil.getStateXmlFile();
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

			//��ȡ�����豸��״̬�������ǵƿ�
			List lightList = document.selectNodes("//state/lightcontrolview/lightbutton");
			for (int i = 0; i < lightList.size(); i++) {
				Element node = (Element) lightList.get(i);
				int id = Integer.parseInt(node.attributeValue("id"));
				int state = Integer.parseInt(node.getText().trim());
				SystemUtil.ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_LIGHT][id] = state;//�޸��豸��״ֵ̬
			}

			//�������Ŵ�
			List doorList = document.selectNodes("//state/doorwindowsview/doorwindow");
			for (int i = 0; i < doorList.size(); i++) {
				Element node = (Element) doorList.get(i);
				int id = Integer.parseInt(node.attributeValue("id"));
				int state = Integer.parseInt(node.getText().trim());
				SystemUtil.ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_DOOR][id] = state;//�޸��豸��״ֵ̬
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");
		}
	}

	//�޸��豸��״̬
	public static void changeDeviceState(IXmlTool xmlTool, IStateable stateObject) throws Exception {
		//�����޸�һ��systemutil�������״̬
		SystemUtil.ALL_DEVICE_STATE[xmlTool.getDeviceTypeId()][stateObject.getId()] = stateObject.getState();

		//Ȼ���ٽ�״̬���浽�ļ���
		String filePath = FileUtil.getStateXmlFile();
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("�ļ� " + filePath + " �����ڣ�");//����ͬ���쳣��ʾ
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);

			xmlTool.changeState(document, stateObject);

			/* ��ʽ�������ָ��XML���룬ò�Ƹ�ʽ��û�������� */
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			writer.write(document);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//�Ƚ�ĳһ���豸���͵İ汾��
	public static int compareVersion(int deviceTypeId) throws Exception {
		String fileName = FileUtil.DEVICEID_XML.get(deviceTypeId);
		String subElement = fileName.substring(0, fileName.indexOf(".xml"));
		String filePath = FileUtil.getDownVersionXmlFile();
		int oldVersion = getDeviceVersion(subElement, FileUtil.getLocalVersionXmlFile());
		int newVersion = getDeviceVersion(subElement, FileUtil.getDownVersionXmlFile());
		if (oldVersion < newVersion) {
			return 1;//����ǰ汾Ҫ���£�����1
		} else if (oldVersion == newVersion) {
			return 0;//����汾��ͬ������0
		} else {
//			return -1;//��������������쳣��
			throw new Exception("�汾����ʱ�������쳣��");
		}
	}

	//��һ��version.xml�ļ��еõ�ĳ���豸���͵İ汾
	private static int getDeviceVersion(String subElement, String filePath) throws Exception {
		int version = 0;
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("�ļ� " + filePath + " �����ڣ�");//����ͬ���쳣��ʾ
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");//��ָ���ı��뷽ʽ��ȡ�ļ�
			SAXReader reader = new SAXReader();//ע�⣺�ҵ�eclipseĬ����GBK���룬��xmlȴ��utf-8����ģ�
			Document document = reader.read(isReader);//ֱ��read(file)�ᱨ��
//			List list = document.selectNodes("//version");
//			Node node = document.selectSingleNode("//version");
//			document.getRootElement();
			Element root = document.getRootElement();
			List list = root.elements();

//			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.size(); i++) {
				Element subnode = (Element) list.get(i);
				String id = subnode.attributeValue("id");
				if (subElement.equalsIgnoreCase(id)) {//�ҵ��������豸�Ľڵ�
					version = Integer.valueOf(subnode.getText().trim());//�õ����İ汾��
					break;//����ֹͣ��
				}
			}
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");
		}
	}

}
