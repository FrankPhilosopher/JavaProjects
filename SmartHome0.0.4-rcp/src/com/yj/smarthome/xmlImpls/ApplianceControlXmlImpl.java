package com.yj.smarthome.xmlImpls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.yj.smarthome.beans.Appliance;
import com.yj.smarthome.beans.ApplianceCommand;
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
public class ApplianceControlXmlImpl implements IXmlTool {

	public static final String DOWNNODE = "//appliances/appliance";
	public static ApplianceControlXmlImpl applianceControlXmlImpl;

	private ApplianceControlXmlImpl() {

	}

	public static ApplianceControlXmlImpl getInstance() {
		if (applianceControlXmlImpl == null) {
			applianceControlXmlImpl = new ApplianceControlXmlImpl();
		}
		return applianceControlXmlImpl;
	}

	@Override
	public void loadServerDevices(Document document, Map<Integer, Object> map) {
		List list = document.selectNodes(DOWNNODE);
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			int id = Integer.parseInt(node.attributeValue("id"));
			String name = node.attributeValue("name");
			Appliance appliance = new Appliance(id, name);
			List<Element> subnodes = node.elements();
			for (int j = 0; j < subnodes.size(); j++) {
				String aid = subnodes.get(j).attributeValue("aid");
				String aname = subnodes.get(j).attributeValue("name");
				System.out.println(aid + " " + aname);
				String command = subnodes.get(j).getText().trim();
				ApplianceCommand applianceCommand = new ApplianceCommand(aid, aname, command);
				appliance.getCommands().add(applianceCommand);
			}
			map.put(id, appliance);
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
			Appliance appliance = (Appliance) map.get(id);
			appliance.setClientAppliance(name, icon);//����clientģʽ���Ŵ�
			map.put(id, appliance);//��ȡ�ļ��������ݷŵ�map��
		}
	}

	@Override
	public int getDeviceTypeId() {
		return ProtocolUtil.DEVICETYPE_APPLIANCE;
	}

	@Override
	public void createClientControlXml(File file, IRenameable renameObject) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("appliances");
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
		Appliance appliance = (Appliance) renameObject;
		Element root = document.getRootElement();
		Element element = root.addElement("appliance");
		element.addAttribute("id", String.valueOf(appliance.getId()));
		Element name = element.addElement("name");
		name.setText(appliance.getClientName());
		Element icon = element.addElement("icon");
		icon.setText(appliance.getIcon());
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
				subElementName.setText(renameObject.getClientName());
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
		List list = document.selectNodes("//state/applianceview/appliance");
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
	public List<SceneModeItem> getApplianceItems(int typeId) throws Exception {
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
			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				int id = Integer.parseInt(node.attributeValue("id"));
				String name = node.attributeValue("name");

				List<SceneModeCommand> commands = new ArrayList<SceneModeCommand>();
				List<Element> subnodes = node.elements();
				for (int j = 0; j < subnodes.size(); j++) {
//					String aid = subnodes.get(j).attributeValue("aid");
					String aname = subnodes.get(j).attributeValue("name");
					int commandcode = Integer.parseInt(subnodes.get(j).getText().trim());
					SceneModeCommand command = new SceneModeCommand(commandcode, aname);
					commands.add(command);
				}
				SceneModeItem item = new SceneModeItem(typeId, id, name, commands);
				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");
		}

		return items;
	}

}
