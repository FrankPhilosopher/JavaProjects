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
import util.AutoLoginUtil;
import util.FileUtil;
import util.ResouceManager;
import beans.SceneMode;
import beans.SceneModeItem;

/**
 * 情景模式xml处理类
 * 
 * @author yinger
 * 
 */
public class SceneModeTool {

	private static SceneModeTool instance;
	private FileUtil fileUtil;
	private AutoLoginUtil autoLoginUtil;

	private final String ICON = "icon";
	private final String STATE = "state";
	private final String ON = "on";
	private final String OFF = "off";
	private final String DEVICEID = "id";
	private final String TYPEID = "typeid";
	private final String ITEM = "item";
	private final String ITEMS = "items";
	private final String SCENEMODE = "scenemode";
	private final String NAME = "name";
	private final String SCENEMODE_ITEM_PATH = "//scenemode/items/item";

	private SceneModeTool() {
		fileUtil = FileUtil.getInstance();
		autoLoginUtil = AutoLoginUtil.getInstance();
		initSceneMode();
	}

	/**
	 * 初始化两个固定的情景模式
	 */
	public void initSceneMode() {
		if (SystemTool.CURRENT_USER.isFirstRun()) {// 第一次使用
			resetSceneMode();
			SystemTool.CURRENT_USER.setFirstRun(false);
			try {
				autoLoginUtil.writeObject(SystemTool.CURRENT_USER);
			} catch (Exception e) {
				e.printStackTrace();// TODO:
			}
		}
	}

	public void resetSceneMode() {
		SceneMode homeSceneMode = new SceneMode();
		homeSceneMode.setName(SceneMode.HOME);
		SceneMode outSceneMode = new SceneMode();
		outSceneMode.setName(SceneMode.OUT);
		SceneMode guestSceneMode = new SceneMode();
		guestSceneMode.setName(SceneMode.GUEST);
		SceneMode leaveSceneMode = new SceneMode();
		leaveSceneMode.setName(SceneMode.LEAVE);
		if(SystemTool.CURRENT_USER.isFirstRun()){
			homeSceneMode.setIcon(ResouceManager.SCENE_DEFAULT);
			outSceneMode.setIcon(ResouceManager.SCENE_OUT);
			guestSceneMode.setIcon(ResouceManager.SCENE_GUEST);
			leaveSceneMode.setIcon(ResouceManager.SCENE_LEAVE);
		}
		try {
			newSceneMode(outSceneMode);
			newSceneMode(guestSceneMode);
			newSceneMode(homeSceneMode);
			newSceneMode(leaveSceneMode);
		} catch (Exception e) {
			e.printStackTrace();// TODO:怎么办？
		}
	}

	public static SceneModeTool getInstance() {
		
		if (instance == null) {
			instance = new SceneModeTool();
		}
		return instance;
	}

	/**
	 * 创建新的情景模式xml文件
	 */
	public void newSceneMode(SceneMode sceneMode) throws Exception {
		File file = new File(fileUtil.getUserDirectory(FileUtil.SCENE_DIRECTORY) + sceneMode.getName() + ".xml");
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			file.createNewFile();
		}
		// else {
		// throw new Exception("创建情景模式 " + sceneMode.getName() + " 失败！"); //TODO:这里总是会报错 java.lang.Exception: 创建情景模式
		// 外出模式 失败！
		// // return;// 注意，这里和以前不太一样！名称重复就返回，避免一定存在的两个情景模式被覆盖了
		// }
		Document document = DocumentHelper.createDocument();
		writeSceneMode(document, sceneMode);// 写入到文件中// 添加元素
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 注意一定要是utf-8格式
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			throw new Exception("创建文件" + file.getName() + "失败！");
		}
	}

	/**
	 * 写入一个情景模式到文件中
	 */
	private void writeSceneMode(Document document, SceneMode sceneMode) {
		Element root = document.addElement(SCENEMODE);
		Element name = root.addElement(NAME);
		name.setText(sceneMode.getName());
		Element icon = root.addElement(ICON);
		icon.setText(sceneMode.getIcon());
		Element items = root.addElement(ITEMS);
		List<SceneModeItem> itemList = sceneMode.getItems();
		SceneModeItem item;
		Element itemElement;
		for (int i = 0; i < itemList.size(); i++) {
			item = itemList.get(i);
			itemElement = items.addElement(ITEM);
			itemElement.addElement(TYPEID).setText(String.valueOf(item.getTypeId()));
			itemElement.addElement(DEVICEID).setText(String.valueOf(item.getDeviceId()));
			itemElement.addElement(ON).setText(String.valueOf(item.getCommand_on()));
			itemElement.addElement(OFF).setText(String.valueOf(item.getCommand_off()));
			itemElement.addElement(STATE).setText(String.valueOf(item.getState()));
		}
	}

	/**
	 * 加载所有的情景模式
	 */
	public List<SceneMode> loadAllSceneMode() throws Exception {
		List<SceneMode> sceneModes = new ArrayList<SceneMode>();
		File dir = new File(fileUtil.getUserDirectory(FileUtil.SCENE_DIRECTORY));// scene目录下所有的文件
		File[] files = dir.listFiles();
		if(AppliactionUtil.DEBUG) System.out.println(dir.getName()+"  path :"+dir.getPath());
		for (int i = 0, length = files.length; i < length; i++) {
			if(AppliactionUtil.DEBUG) System.out.println(files[i].getName());
			sceneModes.add(loadOneSceneMode(files[i]));
		}
		return sceneModes;
	}

	/**
	 * 加载指定的情景模式文件
	 */
	private SceneMode loadOneSceneMode(File file) throws Exception {
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			throw new Exception("文件" + file.getName() + "不存在！");
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			return readSceneMode(document);
		} catch (Exception e) {
			throw new Exception("读取文件" + file.getName() + "失败！");
		}
	}

	/**
	 * 从document对象中读取出scenemode
	 */
	private SceneMode readSceneMode(Document document) {
		SceneMode sceneMode = new SceneMode();
		Element root = document.getRootElement();
		sceneMode.setName(root.element(NAME).getTextTrim());
		sceneMode.setIcon(root.element(ICON).getTextTrim());
		List<Element> itemElements = document.selectNodes(SCENEMODE_ITEM_PATH);// 注意这里的写法！
		Element itemElement;
		SceneModeItem sceneModeItem;
		for (int i = 0; i < itemElements.size(); i++) {
			itemElement = itemElements.get(i);
			sceneModeItem = new SceneModeItem();
			sceneModeItem.setTypeId(Integer.parseInt(itemElement.element(TYPEID).getTextTrim()));
			sceneModeItem.setDeviceId(Integer.parseInt(itemElement.element(DEVICEID).getTextTrim()));
			sceneModeItem.setCommand_on(Integer.parseInt(itemElement.element(ON).getTextTrim()));
			sceneModeItem.setCommand_off(Integer.parseInt(itemElement.element(OFF).getTextTrim()));
			sceneModeItem.setState(Integer.parseInt(itemElement.element(STATE).getTextTrim()));
			sceneMode.getItems().add(sceneModeItem);
		}
		return sceneMode;
	}

	/**
	 * 删除一个情景模式，也就是删除模式文件
	 */
	public void deleteSceneMode(SceneMode sceneMode) {
		File file = new File(fileUtil.getUserDirectory(FileUtil.SCENE_DIRECTORY) + sceneMode.getName() + ".xml");
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 重命名scenemode---直接先删除原来的文件，然后重新创建一个文件
	 */
	public void rename(SceneMode sceneMode, String name) throws Exception {
		deleteSceneMode(sceneMode);
		sceneMode.setName(name);
		newSceneMode(sceneMode);
	}

	/**
	 * 保存sceneMode
	 */
	public void save(SceneMode sceneMode) throws Exception {
		deleteSceneMode(sceneMode);
		newSceneMode(sceneMode);
	}
	
	public void refresh(SceneMode sceneMode) throws Exception{
		String filePath = fileUtil.getUserDirectory(FileUtil.SCENE_DIRECTORY) + sceneMode.getName() + ".xml";
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在//文件不存在
			try {
				file.createNewFile();// 文件不存在就新建一个
			} catch (IOException e) {
				throw new Exception("创建文件" + filePath + "失败！");// 一般不会失败
			}
			newSceneMode(sceneMode);
			return;
		}
		try {// 文件存在
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			Element root = document.getRootElement();
			Element icon = root.element("icon");
			if(AppliactionUtil.DEBUG) System.out.println("6666");
			icon.setText(sceneMode.getIcon());
			if(AppliactionUtil.DEBUG) System.out.println("33333333333333333");
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


}
