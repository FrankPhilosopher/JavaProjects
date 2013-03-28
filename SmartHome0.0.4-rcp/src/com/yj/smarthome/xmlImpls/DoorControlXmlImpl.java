package com.yj.smarthome.xmlImpls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.yj.smarthome.beans.DoorWindow;
import com.yj.smarthome.beans.SceneModeCommand;
import com.yj.smarthome.beans.SceneModeItem;
import com.yj.smarthome.interfaces.IRenameable;
import com.yj.smarthome.interfaces.IStateable;
import com.yj.smarthome.interfaces.IXmlTool;
import com.yj.smarthome.util.FileUtil;
import com.yj.smarthome.util.ProtocolUtil;

/**
 * �ƿص�xml�ļ������� ����ģʽ
 * 
 * @author yinger
 * 
 */
public class DoorControlXmlImpl implements IXmlTool {

	public static final String NODE = "dw";

	public static final String NODES = "doorwindows";

	public static final String STATENODE = "//state/doorwindowsview/dw";

	public static final String DOWNNODE = "//doorwindows/dw";

	public static DoorControlXmlImpl doorControlXmlImpl;

	private DoorControlXmlImpl() {

	}

	public static DoorControlXmlImpl getInstance() {
		if (doorControlXmlImpl == null) {
			doorControlXmlImpl = new DoorControlXmlImpl();
		}
		return doorControlXmlImpl;
	}

	@Override
	public void loadServerDevices(Document document, Map<Integer, Object> map) {
		List list = document.selectNodes(DOWNNODE);
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			int id = Integer.parseInt(node.attributeValue("id"));

			Element subElementName = node.element("name");
			String name = subElementName.getText().trim();
			Element subElementOn = node.element("open");
			int on = Integer.parseInt(subElementOn.getText().trim());
			Element subElementOff = node.element("close");
			int off = Integer.parseInt(subElementOff.getText().trim());
			Element subElementStop = node.element("stop");
			int stop = Integer.parseInt(subElementStop.getText().trim());

			DoorWindow doorWindow = new DoorWindow(id, name, on, off, stop);
			map.put(id, doorWindow);
		}
	}

	@Override
	public void loadClinetDevices(Document document, Map<Integer, Object> map) {
		List list = document.selectNodes(DOWNNODE);
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			int id = Integer.parseInt(node.attributeValue("id"));

			Element subElementName = node.element("name");
			String name = subElementName.getText().trim();
			Element subElementIcon = node.element("icon");
			String icon = subElementIcon.getText().trim();

			if (map.get(id) == null) {//����ڵ���������û�У���Ϊ�汾�ĸ���
				continue;
			}
			DoorWindow doorwindow = (DoorWindow) map.get(id);
			doorwindow.setClientDoorWindow(name, icon);//����clientģʽ���Ŵ�
			map.put(id, doorwindow);//��ȡ�ļ��������ݷŵ�map��
		}
	}

	@Override
	public int getDeviceTypeId() {
		return ProtocolUtil.DEVICETYPE_DOOR;
	}

	@Override
	public void createClientControlXml(File file, IRenameable renameObject) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(NODES);
		//���Ԫ��
		writeNewDevice(document, renameObject);
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

	@Override
	public void writeNewDevice(Document document, IRenameable renameObject) {
		DoorWindow doorWindow = (DoorWindow) renameObject;
		Element root = document.getRootElement();
		Element element = root.addElement(NODE);
		element.addAttribute("id", String.valueOf(doorWindow.getId()));
		Element name = element.addElement("name");
		name.setText(doorWindow.getClientName());
		Element icon = element.addElement("icon");
		icon.setText(doorWindow.getIcon());
	}

	@Override
	public void changeName(Document document, IRenameable renameObject) {
		List list = document.selectNodes(DOWNNODE);
		int i = 0;
		for (i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			int id = Integer.parseInt(node.attributeValue("id"));
			if (id == renameObject.getId()) {
				Element subElementName = node.element("name");
//				System.out.println(subElementName.getText());
				subElementName.setText(renameObject.getClientName());
//				System.out.println(subElementName.getText());
				break;
			}
		}
		//���û���ҵ����id�Ķ�����ô��Ҫ�½�һ��Ԫ��
		if (i == list.size()) {
			writeNewDevice(document, renameObject);
		}
	}

	@Override
	//�޸�ָ���豸��״̬
	public void changeState(Document document, IStateable stateObject) {
		List list = document.selectNodes(STATENODE);
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			int xmlid = Integer.parseInt(node.attributeValue("id"));
			if (xmlid == stateObject.getId()) {
				node.setText(String.valueOf(stateObject.getState()));
				break;//�˳���
			}
		}
	}

	//��ȡ�豸���龰ģʽ��Ϣ
	public List<SceneModeItem> getDoorItems(int typeId) throws Exception {
		List<SceneModeItem> items = new ArrayList<SceneModeItem>();
		String filePath = FileUtil.getDownXmlFile(typeId);
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("�ļ� " + filePath + " �����ڣ�");//����ͬ���쳣��ʾ
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();//ע�⣺�ҵ�eclipseĬ����GBK���룬��xmlȴ��utf-8����ģ�
			Document document = reader.read(isReader);//ֱ��read(file)�ᱨ��

			List list = document.selectNodes(DOWNNODE);
			for (int i = 0, size = list.size(); i < size; i++) {
				Element node = (Element) list.get(i);
				int id = Integer.parseInt(node.attributeValue("id"));
				Element subElementName = node.element("name");
				String name = subElementName.getText().trim();

				Element subElementOn = node.element("open");
				int on = Integer.parseInt(subElementOn.getText().trim());
				SceneModeCommand commandon = new SceneModeCommand(on, "��");
				Element subElementOff = node.element("close");
				int off = Integer.parseInt(subElementOff.getText().trim());
				SceneModeCommand commandoff = new SceneModeCommand(off, "�ر�");
				Element subElementStop = node.element("stop");
				int stop = Integer.parseInt(subElementStop.getText().trim());
				SceneModeCommand commandstop = new SceneModeCommand(stop, "��ͣ");
				SceneModeItem item = new SceneModeItem(typeId, id, name, Arrays.asList(commandon, commandoff, commandstop));
				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");
		}

		return items;
	}

}
