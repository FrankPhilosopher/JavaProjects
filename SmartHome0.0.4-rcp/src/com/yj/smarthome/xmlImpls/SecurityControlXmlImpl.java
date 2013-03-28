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

import com.yj.smarthome.beans.SceneModeCommand;
import com.yj.smarthome.beans.SceneModeItem;
import com.yj.smarthome.beans.Security;
import com.yj.smarthome.interfaces.IRenameable;
import com.yj.smarthome.interfaces.IStateable;
import com.yj.smarthome.interfaces.IXmlTool;
import com.yj.smarthome.util.FileUtil;
import com.yj.smarthome.util.ProtocolUtil;

/**
 * ������XML�ļ������� ����ģʽ
 * 
 * @author yinger
 * 
 */
public class SecurityControlXmlImpl implements IXmlTool {

	public static final String COMMAND_CLOSE = "close";
	public static final String COMMAND_OPEN = "open";
	public static final String NODE = "security";
	public static final String NODES = "securitys";
	public static final String STATENODE = "//state/securityview/security";
	public static final String DOWNNODE = "//securitys/security";
	public static SecurityControlXmlImpl securityControlXmlImpl;

	private SecurityControlXmlImpl() {

	}

	public static SecurityControlXmlImpl getInstance() {
		if (securityControlXmlImpl == null) {
			securityControlXmlImpl = new SecurityControlXmlImpl();
		}
		return securityControlXmlImpl;
	}

	@Override
	//���ط������˵��豸��document��xml�ļ���map�Ƿ��ؽ��
	public void loadServerDevices(Document document, Map<Integer, Object> map) {
		List list = document.selectNodes(DOWNNODE);
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			int id = Integer.parseInt(node.attributeValue("id"));

			Element subElementName = node.element("name");
			String name = subElementName.getText().trim();
			Element command_open = node.element(COMMAND_OPEN);
			int open = Integer.parseInt(command_open.getText().trim());
			Element command_close = node.element(COMMAND_CLOSE);
			int close = Integer.parseInt(command_close.getText().trim());

			Security security = new Security(id, name, open, close);
			map.put(id, security);
		}
	}

	@Override
	//���ؿͻ��˵��豸
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
			Security security = (Security) map.get(id);
			security.setClientSecurity(name, icon);
			map.put(id, security);//��ȡ�ļ��������ݷŵ�map��
		}
	}

	@Override
	//�õ��豸���ͱ��
	public int getDeviceTypeId() {
		return ProtocolUtil.DEVICETYPE_SECURITY;
	}

	@Override
	//����һ���µ�xml�ļ������ұ���һ������ʵ������Ϣ
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
	//�޸�ָ���豸������
	public void changeName(Document document, IRenameable renameObject) {
		//LightButton lightButton = (LightButton) renameObject;//���Ƚ���һ��ת�ͣ�������Բ���
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
	//����µ��豸�����ص��豸�����ļ���
	public void writeNewDevice(Document document, IRenameable renameObject) {
		Security security = (Security) renameObject;//���Ƚ���һ��ת�ͣ���Ϊд��ʱ��Ҫ�������Ϣ
		Element root = document.getRootElement();
		Element element = root.addElement(NODE);
		element.addAttribute("id", String.valueOf(security.getId()));
		Element name = element.addElement("name");
		name.setText(security.getClientName());
		Element icon = element.addElement("icon");
		icon.setText(security.getIcon());
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
	public List<SceneModeItem> getSecurityItems(int typeId) throws Exception {
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

				Element subElementOn = node.element(COMMAND_OPEN);
				int on = Integer.parseInt(subElementOn.getText().trim());
				SceneModeCommand commandon = new SceneModeCommand(on, "��");
				Element subElementOff = node.element(COMMAND_CLOSE);
				int off = Integer.parseInt(subElementOff.getText().trim());
				SceneModeCommand commandoff = new SceneModeCommand(off, "�ر�");
				SceneModeItem item = new SceneModeItem(typeId, id, name, Arrays.asList(commandon, commandoff));
				items.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");
		}
		return items;
	}

}
