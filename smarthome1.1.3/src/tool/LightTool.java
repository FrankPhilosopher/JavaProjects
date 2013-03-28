package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import util.AppliactionUtil;
import util.FileUtil;
import util.ProtocolUtil;
import beans.LightControl;

/**
 * 灯控xml处理类
 * 
 * @author yinger
 * 
 */
public class LightTool {

	private static LightTool instance;
	private FileUtil fileUtil;

	private final String ID = "id";
	private final String NAME = "name";
	private final String ROOMID = "roomid";
	private final String TYPE = "light";
	private final String HASINTENSITY = "hasi";
	private final String BUTTONDOWN = "buttondown";
	private final String BUTTONUP = "buttonup";
	private final String BUTTONOFF = "buttonoff";
	private final String BUTTONON = "buttonon";
	private final String NODE = "lightbutton";
	private final String NODES = "lightbuttons";
	private final String NODEPATH = "//lightbuttons/lightbutton";

	private LightTool() {
		fileUtil = FileUtil.getInstance();
	}

	public static LightTool getInstance() {
		if (instance == null) {
			instance = new LightTool();
		}
		return instance;
	}

	/**
	 * 加载灯控设备
	 */
	public List<LightControl> loadControls() throws Exception {
		List<LightControl> controls = new ArrayList<LightControl>();
		loadDownControls(controls);
		if (controls != null && controls.size() > 0) {
			loadLocalControls(controls);
		}
		return controls;
	}

	/**
	 * 加载下载down目录下的灯控设备
	 */
	private void loadDownControls(List<LightControl> controls) throws Exception {
		String filePath = FileUtil.DOWN_DIRECTORY + FileUtil.LIGHT_XML;
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			if(AppliactionUtil.DEBUG) System.out.println("文件 " + filePath + " 不存在！");
			throw new Exception("文件 " + filePath + " 不存在！");// tested
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			List list = document.selectNodes(NODEPATH);
			Element node;
			LightControl control;
			for (int i = 0; i < list.size(); i++) {
				node = (Element) list.get(i);
				control = new LightControl();
				control.setId(Integer.parseInt(node.attributeValue(ID)));
				control.setName(node.element(NAME).getTextTrim());
				control.setType(Integer.parseInt(node.element(TYPE).getTextTrim()));
				control.setHasIntensity(Integer.parseInt(node.element(HASINTENSITY).getTextTrim()));
				control.setCommand_buttonon(Integer.parseInt(node.element(BUTTONON).getTextTrim()));
				control.setCommand_buttonoff(Integer.parseInt(node.element(BUTTONOFF).getTextTrim()));
				control.setCommand_buttonup(Integer.parseInt(node.element(BUTTONUP).getTextTrim()));
				control.setCommand_buttondown(Integer.parseInt(node.element(BUTTONDOWN).getTextTrim()));
				control.setState(SystemTool.ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_LIGHT][control.getId()]);
				controls.add(control);
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// tested
		}
	}

	/**
	 * 加载下载local目录下的灯控设备
	 */
	private void loadLocalControls(List<LightControl> controls) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.LIGHT_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			return;
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			List list = document.selectNodes(NODEPATH);
			Element node;
			for (int i = 0; i < list.size(); i++) {
				node = (Element) list.get(i);
				for (int j = 0; j < controls.size(); j++) {// 遍历--找到id相同的，设置name
					if (Integer.parseInt(node.attributeValue(ID)) == controls.get(j).getId()) {
						controls.get(j).setName(node.elementTextTrim(NAME));
						controls.get(j).setRoomid(node.elementTextTrim(ROOMID));
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// tested
		}
	}

	/**
	 * 重命名设备---文件为local/lightcontrolview.xml
	 */
	public void rename(LightControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.LIGHT_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			try {
				file.createNewFile();// 文件不存在就新建一个
			} catch (IOException e) {
				throw new Exception("创建文件" + filePath + "失败！");// 一般不会失败
			}
			newLocalXml(control);
			return;
		}
		try {// 文件存在
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			List list = document.selectNodes(NODEPATH);
			Element node;
			int i = 0;
			for (; i < list.size(); i++) {
				node = (Element) list.get(i);
				if (Integer.parseInt(node.attributeValue(ID)) == control.getId()) {
					node.element(NAME).setText(control.getName());
					break;// 一定要break
				}
			}
			if (i == list.size()) {// 如果没有的话，那么就要新增一个node
				writeNewDevice(document, control);
			}
			try {
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 注意一定要是utf-8格式
				writer.write(document);
				writer.close();
			} catch (Exception ex) {
				throw new Exception("写入文件" + filePath + "失败！");// 一般不会失败
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// 一般不会失败
		}
	}

	/**
	 * 加入房间---文件为local/lightcontrolview.xml
	 */
	public void enterRoom(LightControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.LIGHT_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			try {
				file.createNewFile();// 文件不存在就新建一个
			} catch (IOException e) {
				throw new Exception("创建文件" + filePath + "失败！");// 一般不会失败
			}
			newLocalXml(control);
			return;
		}
		try {// 文件存在
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			List list = document.selectNodes(NODEPATH);
			Element node;
			int i = 0;
			for (; i < list.size(); i++) {
				node = (Element) list.get(i);
				if (Integer.parseInt(node.attributeValue(ID)) == control.getId()) {
					node.element(ROOMID).setText(control.getRoomid());
					break;// 一定要break
				}
			}
			if (i == list.size()) {// 如果没有的话，那么就要新增一个node
				writeNewDevice(document, control);
			}
			try {
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 注意一定要是utf-8格式
				writer.write(document);
				writer.close();
			} catch (Exception ex) {
				throw new Exception("写入文件" + filePath + "失败！");// 一般不会失败
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// 一般不会失败
		}
	}

	/**
	 * 退出房间---文件为local/lightcontrolview.xml
	 */
	public void quitRoom(LightControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.LIGHT_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在，不存在即可退出了
			return;
		}
		try {// 文件存在
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			Element root = document.getRootElement();
			List list = document.selectNodes(NODEPATH);
			Element node;
			int i = 0;
			for (; i < list.size(); i++) {
				node = (Element) list.get(i);
				if (Integer.parseInt(node.attributeValue(ID)) == control.getId()) {
					root.remove(node);
					break;// 一定要break
				}
			}
			try {
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 注意一定要是utf-8格式
				writer.write(document);
				writer.close();
			} catch (Exception ex) {
				throw new Exception("写入文件" + filePath + "失败！");// 一般不会失败
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// 一般不会失败
		}
	}

	/**
	 * 新建一个local目录下的lightcontrolview.xml文件
	 */
	private void newLocalXml(LightControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.LIGHT_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			try {
				file.createNewFile();// 文件不存在就新建一个
			} catch (IOException e) {
				throw new Exception("创建文件" + filePath + "失败！");// 一般不会失败
			}
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(NODES);
		writeNewDevice(document, control);// 添加元素
		try {// 写入到文件中
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 注意一定要是utf-8格式
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			throw new Exception("创建文件" + file.getName() + "失败！");// 一般不会失败
		}
	}

	/**
	 * 添加新的设备名称到本地的设备类型文件中
	 */
	private void writeNewDevice(Document document, LightControl control) {
		Element root = document.getRootElement();
		Element element = root.addElement(NODE);
		element.addAttribute(ID, String.valueOf(control.getId()));
		Element name = element.addElement(NAME);
		name.setText(control.getName());
		Element room = element.addElement(ROOMID);
		room.setText(control.getRoomid());
	}

}
