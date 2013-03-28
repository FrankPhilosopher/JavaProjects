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
 * 灯控的xml文件处理类 单例模式
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

			if (map.get(id) == null) {//这个节点可能真的是没有，因为版本的更新
				continue;
			}
			Appliance appliance = (Appliance) map.get(id);
			appliance.setClientAppliance(name, icon);//设置client模式的门窗
			map.put(id, appliance);//读取文件，将内容放到map中
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
		//如果没有找到这个id的对象，那么就要新建一个元素
		if (i == list.size()) {
			writeNewDevice(document, renameObject);
		}
	}

	@Override
	//修改指定设备的状态
	public void changeState(Document document, IStateable stateObject) {
		List list = document.selectNodes("//state/applianceview/appliance");
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
	public List<SceneModeItem> getApplianceItems(int typeId) throws Exception {
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
			throw new Exception("读取文件" + filePath + "失败！");
		}

		return items;
	}

}
