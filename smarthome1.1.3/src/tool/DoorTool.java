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

import util.FileUtil;
import util.ProtocolUtil;
import beans.DoorControl;

/**
 * 门窗xml处理类
 * 
 * @author yinger
 * 
 */
public class DoorTool {

	private static DoorTool instance;
	private FileUtil fileUtil;

	private final String ID = "id";
	private final String NAME = "name";
	private final String BUTTONOFF = "stop";
	private final String BUTTONCLOSE = "close";
	private final String BUTTONON = "open";
	private final String NODE = "dw";
	private final String NODES = "doorwindows";
	private final String DOORORWINDOW = "doororwindow";
	private final String NODEPATH = "//doorwindows/dw";

	private DoorTool() {
		fileUtil = FileUtil.getInstance();
	}

	public static DoorTool getInstance() {
		if (instance == null) {
			instance = new DoorTool();
		}
		return instance;
	}

	/**
	 * 加载门窗设备
	 */
	public List<DoorControl> loadControls() throws Exception {
		List<DoorControl> controls = new ArrayList<DoorControl>();
		loadDownControls(controls);
		if (controls != null && controls.size() > 0) {
			loadLocalControls(controls);
		}
		return controls;
	}

	/**
	 * 加载下载down目录下的门窗设备
	 */
	private void loadDownControls(List<DoorControl> controls) throws Exception {
		String filePath = FileUtil.DOWN_DIRECTORY + FileUtil.DOOR_XML;
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			throw new Exception("文件 " + filePath + " 不存在！");// tested
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			List list = document.selectNodes(NODEPATH);
			Element node;
			DoorControl control;
			for (int i = 0; i < list.size(); i++) {
				node = (Element) list.get(i);
				control = new DoorControl();
				control.setId(Integer.parseInt(node.attributeValue(ID)));
				control.setName(node.element(NAME).getTextTrim());
				control.setType(Integer.parseInt(node.element(DOORORWINDOW).getTextTrim()));
				control.setCommand_buttonopen(Integer.parseInt(node.element(BUTTONON).getTextTrim()));
				control.setCommand_buttonclose(Integer.parseInt(node.element(BUTTONCLOSE).getTextTrim()));
				control.setCommand_buttonstop(Integer.parseInt(node.element(BUTTONOFF).getTextTrim()));
				control.setState(SystemTool.ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_DOOR][control.getId()]);
				controls.add(control);
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// tested
		}
	}

	/**
	 * 加载下载local目录下的门窗设备
	 */
	private void loadLocalControls(List<DoorControl> controls) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.DOOR_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			// System.out.println("文件 " + filePath + " 不存在！");//tested
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
	public void rename(DoorControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.DOOR_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在//文件不存在
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
	 * 新建一个local目录下的doorcontrolview.xml文件
	 */
	private void newLocalXml(DoorControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.DOOR_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在//文件不存在
			try {
				file.createNewFile();// 文件不存在就新建一个
			} catch (IOException e) {
				throw new Exception("创建文件" + filePath + "失败！");// 一般不会失败
			}
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(NODES);
		writeNewDevice(document, control);// 添加元素
		// 写入到文件中
		try {
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
	private void writeNewDevice(Document document, DoorControl control) {
		Element root = document.getRootElement();
		Element element = root.addElement(NODE);
		element.addAttribute(ID, String.valueOf(control.getId()));
		Element name = element.addElement(NAME);
		name.setText(control.getName());
	}

}
