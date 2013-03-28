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

import com.yj.smarthome.beans.LightButton;
import com.yj.smarthome.beans.SceneModeCommand;
import com.yj.smarthome.beans.SceneModeItem;
import com.yj.smarthome.interfaces.IRenameable;
import com.yj.smarthome.interfaces.IStateable;
import com.yj.smarthome.interfaces.IXmlTool;
import com.yj.smarthome.util.FileUtil;
import com.yj.smarthome.util.ProtocolUtil;

/**
 * �ƿص�XML�ļ������� ����ģʽ
 * 
 * @author yinger
 * 
 */
public class LightControlXmlImpl implements IXmlTool {

	public static final String BUTTONDOWN = "buttondown";
	public static final String BUTTONUP = "buttonup";
	public static final String BUTTONOFF = "buttonoff";
	public static final String BUTTONON = "buttonon";
	public static final String NODE = "lightbutton";
	public static final String NODES = "lightbuttons";
	public static final String STATENODE = "//state/lightcontrolview/lightbutton";
	public static final String DOWNNODE = "//lightbuttons/lightbutton";
	public static LightControlXmlImpl lightControlXmlImpl;

	private LightControlXmlImpl() {

	}

	public static LightControlXmlImpl getInstance() {
		if (lightControlXmlImpl == null) {
			lightControlXmlImpl = new LightControlXmlImpl();
		}
		return lightControlXmlImpl;
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
			Element subElementHasi = node.element("hasi");
			int hasi = Integer.parseInt(subElementHasi.getText().trim());
			Element subElementUp = node.element(BUTTONUP);
			int up = Integer.parseInt(subElementUp.getText().trim());
			Element subElementDown = node.element(BUTTONDOWN);
			int down = Integer.parseInt(subElementDown.getText().trim());
			Element subElementOn = node.element(BUTTONON);
			int on = Integer.parseInt(subElementOn.getText().trim());
			Element subElementOff = node.element(BUTTONOFF);
			int off = Integer.parseInt(subElementOff.getText().trim());

			LightButton lightButton = new LightButton(id, name, hasi, up, down, on, off);
			map.put(id, lightButton);
		}
	}

	@Override
	//���ؿͻ��˵ĵƿ��豸
	public void loadClinetDevices(Document document, Map<Integer, Object> map) {
		List list = document.selectNodes(DOWNNODE);
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			int id = Integer.parseInt(node.attributeValue("id"));

			Element subElementName = node.element("name");
			String name = subElementName.getText().trim();
			Element subElementIcon = node.element("icon");
			String icon = subElementIcon.getText().trim();
			Element subElementIntensity = node.element("intensity");
			int intensity = Integer.parseInt(subElementIntensity.getText().trim());

			if (map.get(id) == null) {//����ڵ���������û�У���Ϊ�汾�ĸ���
				continue;
			}
			LightButton lightButton = (LightButton) map.get(id);
			lightButton.setClientLightButton(name, icon, intensity);//����clientģʽ�ĵƿ�
			map.put(id, lightButton);//��ȡ�ļ��������ݷŵ�map��
		}
	}

	@Override
	//�õ��豸���ͱ��
	public int getDeviceTypeId() {
		return ProtocolUtil.DEVICETYPE_LIGHT;
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
		LightButton lightButton = (LightButton) renameObject;//���Ƚ���һ��ת�ͣ���Ϊд��ʱ��Ҫ�������Ϣ
		Element root = document.getRootElement();
		Element element = root.addElement(NODE);
		element.addAttribute("id", String.valueOf(lightButton.getId()));
		Element name = element.addElement("name");
		name.setText(lightButton.getClientName());
		Element icon = element.addElement("icon");
		icon.setText(lightButton.getIcon());
		Element intensity = element.addElement("intensity");
		intensity.setText(String.valueOf(lightButton.getIntensity()));
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
	public List<SceneModeItem> getLightItems(int typeId) throws Exception {
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

				Element subElementOn = node.element(BUTTONON);
				int on = Integer.parseInt(subElementOn.getText().trim());
				SceneModeCommand commandon = new SceneModeCommand(on, "��");
				Element subElementOff = node.element(BUTTONOFF);
				int off = Integer.parseInt(subElementOff.getText().trim());
				SceneModeCommand commandoff = new SceneModeCommand(off, "�ر�");
				Element subElementUp = node.element(BUTTONUP);
				int up = Integer.parseInt(subElementUp.getText().trim());//TODO��������Ҫע��һ�㣬ֻ�ڵƹ��ǿ��Ե��ڵģ�
				SceneModeCommand commandup = new SceneModeCommand(up, "����");
				Element subElementDown = node.element(BUTTONDOWN);
				int down = Integer.parseInt(subElementDown.getText().trim());
				SceneModeCommand commanddown = new SceneModeCommand(down, "����");
				SceneModeItem item = new SceneModeItem(typeId, id, name, Arrays.asList(commandon, commandoff, commandup, commanddown));
				items.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");
		}

		return items;
	}

}
