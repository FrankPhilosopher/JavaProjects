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
 * 灯控的xml文件处理类 单例模式
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

			if (map.get(id) == null) {//这个节点可能真的是没有，因为版本的更新
				continue;
			}
			DoorWindow doorwindow = (DoorWindow) map.get(id);
			doorwindow.setClientDoorWindow(name, icon);//设置client模式的门窗
			map.put(id, doorwindow);//读取文件，将内容放到map中
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
		//添加元素
		writeNewDevice(document, renameObject);
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
		//如果没有找到这个id的对象，那么就要新建一个元素
		if (i == list.size()) {
			writeNewDevice(document, renameObject);
		}
	}

	@Override
	//修改指定设备的状态
	public void changeState(Document document, IStateable stateObject) {
		List list = document.selectNodes(STATENODE);
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			int xmlid = Integer.parseInt(node.attributeValue("id"));
			if (xmlid == stateObject.getId()) {
				node.setText(String.valueOf(stateObject.getState()));
				break;//退出吧
			}
		}
	}

	//获取设备的情景模式信息
	public List<SceneModeItem> getDoorItems(int typeId) throws Exception {
		List<SceneModeItem> items = new ArrayList<SceneModeItem>();
		String filePath = FileUtil.getDownXmlFile(typeId);
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("文件 " + filePath + " 不存在！");//区别不同的异常提示
		}
		System.out.println(filePath);
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();//注意：我的eclipse默认是GBK编码，而xml却是utf-8编码的！
			Document document = reader.read(isReader);//直接read(file)会报错！

			List list = document.selectNodes(DOWNNODE);
			for (int i = 0, size = list.size(); i < size; i++) {
				Element node = (Element) list.get(i);
				int id = Integer.parseInt(node.attributeValue("id"));
				Element subElementName = node.element("name");
				String name = subElementName.getText().trim();

				Element subElementOn = node.element("open");
				int on = Integer.parseInt(subElementOn.getText().trim());
				SceneModeCommand commandon = new SceneModeCommand(on, "打开");
				Element subElementOff = node.element("close");
				int off = Integer.parseInt(subElementOff.getText().trim());
				SceneModeCommand commandoff = new SceneModeCommand(off, "关闭");
				Element subElementStop = node.element("stop");
				int stop = Integer.parseInt(subElementStop.getText().trim());
				SceneModeCommand commandstop = new SceneModeCommand(stop, "暂停");
				SceneModeItem item = new SceneModeItem(typeId, id, name, Arrays.asList(commandon, commandoff, commandstop));
				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("读取文件" + filePath + "失败！");
		}

		return items;
	}

}
