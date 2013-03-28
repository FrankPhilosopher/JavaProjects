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
import beans.ApplianceAction;
import beans.ApplianceControl;
import beans.SubAction;

/**
 * 家电xml处理类
 * 
 * @author yinger
 * 
 */
public class ApplianceTool {

	private static ApplianceTool instance;
	private FileUtil fileUtil;

	private final String ID = "id";
	private final String NAME = "name";
	private final String ICON = "icon";

	private final String ACTIONID = "aid";
	private final String ACTION = "action";
	private final String STYLE = "style";

	private final String SUBACTIONID = "sid";
	private final String SUBACTION = "subaction";

	private final String NODE = "appliance";
	private final String NODES = "appliances";
	private final String NODEPATH = "//appliances/appliance";

	private ApplianceTool() {
		fileUtil = FileUtil.getInstance();
	}

	public static ApplianceTool getInstance() {
		if (instance == null) {
			instance = new ApplianceTool();
		}
		return instance;
	}

	/**
	 * 加载家电设备
	 */
	public List<ApplianceControl> loadControls() throws Exception {
		List<ApplianceControl> controls = new ArrayList<ApplianceControl>();
		loadDownControls(controls);
		if (controls != null && controls.size() > 0) {
			loadLocalControls(controls);
		}
		return controls;
	}

	/**
	 * 加载下载down目录下的家电设备
	 */
	private void loadDownControls(List<ApplianceControl> controls) throws Exception {
		String filePath = FileUtil.DOWN_DIRECTORY + FileUtil.APPLIANCE_XML;
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
			Element actionElement;
			int style = 0;
			ApplianceControl control;
			ApplianceAction action;
			for (int i = 0; i < list.size(); i++) {// 遍历家电列表
				node = (Element) list.get(i);
				control = new ApplianceControl();
				control.setId(Integer.parseInt(node.attributeValue(ID)));
				control.setName(node.attributeValue(NAME));
				List actionList = node.elements(ACTION);
				for (int j = 0; j < actionList.size(); j++) {// 遍历家电的action列表
					actionElement = (Element) actionList.get(j);
					style = Integer.parseInt(actionElement.attributeValue(STYLE));
					action = new ApplianceAction();
					action.setStyle(style);// common part
					action.setName(actionElement.attributeValue(NAME));
					action.setAid(Integer.parseInt(actionElement.attributeValue(ACTIONID)));
					if (style == ApplianceAction.ACTIONTYPE_SINGLE) {// 单键响应
						action.setCode(Integer.parseInt(actionElement.getTextTrim()));
						if (action.getAid() == ProtocolUtil.COMMAND_ON) {// 设置开和关命令
							control.setCommand_open(action.getCode());
						} else if (action.getAid() == ProtocolUtil.COMMAND_OFF) {
							control.setCommand_close(action.getCode());
						}
					} else if (style == ApplianceAction.ACTIONTYPE_CYCLE) {// 循环响应
						addSubActions(actionElement, action);
					} else if (style == ApplianceAction.ACTIONTYPE_INCREASE) {// 递增响应
						addSubActions(actionElement, action);
					} else if (style == ApplianceAction.ACTIONTYPE_DECREASE) {// 递减响应
						ApplianceAction increaseAction = null;
						for (int k = 0; k < control.getActions().size(); k++) {
							increaseAction = control.getActions().get(k);
							if (increaseAction.getAid() == action.getAid()) {// 找到aid相同的action
								action.setSubActions(increaseAction.getSubActions());
								// 绑定递增递减响应的creaseIndex的值，这里很重要！
								action.creaseIndexProperty().bindBidirectional(increaseAction.creaseIndexProperty());
								// 绑定btntext属性
								action.btntextProperty().bindBidirectional(increaseAction.btntextProperty());
							}
						}
					}
					control.getActions().add(action);
				}
				controls.add(control);
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// tested
		}
	}

	/**
	 * 添加element下的子响应到响应action中
	 */
	private void addSubActions(Element actionElement, ApplianceAction action) {
		List subList = actionElement.elements(SUBACTION);
		Element subElement;
		SubAction subAction;
		for (int k = 0; k < subList.size(); k++) {// 遍历子响应操作
			subElement = (Element) subList.get(k);
			subAction = new SubAction();
			subAction.setSid(Integer.parseInt(subElement.attributeValue(SUBACTIONID)));
			subAction.setName(subElement.attributeValue(NAME));
			subAction.setCode(Integer.parseInt(subElement.getTextTrim()));
			action.getSubActions().add(subAction);
		}
	}

	/**
	 * 加载下载local目录下的家电设备
	 */
	private void loadLocalControls(List<ApplianceControl> controls) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.APPLIANCE_XML);
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
						controls.get(j).setIcon((node.elementTextTrim(ICON)));
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + filePath + "失败！");// tested
		}
	}

	/**
	 * 重命名设备还有修改图片---文件为local/appliancecontrolview.xml
	 */
	public void refresh(ApplianceControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.APPLIANCE_XML);
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
					node.element(ICON).setText(control.getIcon());
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
	private void newLocalXml(ApplianceControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.APPLIANCE_XML);
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
	private void writeNewDevice(Document document, ApplianceControl control) {
		Element root = document.getRootElement();
		Element element = root.addElement(NODE);
		element.addAttribute(ID, String.valueOf(control.getId()));
		Element name = element.addElement(NAME);
		name.setText(control.getName());
		Element icon = element.addElement(ICON);
		icon.setText(control.getIcon());
	}

}
