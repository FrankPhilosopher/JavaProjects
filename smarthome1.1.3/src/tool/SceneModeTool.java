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
 * �龰ģʽxml������
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
	 * ��ʼ�������̶����龰ģʽ
	 */
	public void initSceneMode() {
		if (SystemTool.CURRENT_USER.isFirstRun()) {// ��һ��ʹ��
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
			e.printStackTrace();// TODO:��ô�죿
		}
	}

	public static SceneModeTool getInstance() {
		
		if (instance == null) {
			instance = new SceneModeTool();
		}
		return instance;
	}

	/**
	 * �����µ��龰ģʽxml�ļ�
	 */
	public void newSceneMode(SceneMode sceneMode) throws Exception {
		File file = new File(fileUtil.getUserDirectory(FileUtil.SCENE_DIRECTORY) + sceneMode.getName() + ".xml");
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			file.createNewFile();
		}
		// else {
		// throw new Exception("�����龰ģʽ " + sceneMode.getName() + " ʧ�ܣ�"); //TODO:�������ǻᱨ�� java.lang.Exception: �����龰ģʽ
		// ���ģʽ ʧ�ܣ�
		// // return;// ע�⣬�������ǰ��̫һ���������ظ��ͷ��أ�����һ�����ڵ������龰ģʽ��������
		// }
		Document document = DocumentHelper.createDocument();
		writeSceneMode(document, sceneMode);// д�뵽�ļ���// ���Ԫ��
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// ע��һ��Ҫ��utf-8��ʽ
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			throw new Exception("�����ļ�" + file.getName() + "ʧ�ܣ�");
		}
	}

	/**
	 * д��һ���龰ģʽ���ļ���
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
	 * �������е��龰ģʽ
	 */
	public List<SceneMode> loadAllSceneMode() throws Exception {
		List<SceneMode> sceneModes = new ArrayList<SceneMode>();
		File dir = new File(fileUtil.getUserDirectory(FileUtil.SCENE_DIRECTORY));// sceneĿ¼�����е��ļ�
		File[] files = dir.listFiles();
		if(AppliactionUtil.DEBUG) System.out.println(dir.getName()+"  path :"+dir.getPath());
		for (int i = 0, length = files.length; i < length; i++) {
			if(AppliactionUtil.DEBUG) System.out.println(files[i].getName());
			sceneModes.add(loadOneSceneMode(files[i]));
		}
		return sceneModes;
	}

	/**
	 * ����ָ�����龰ģʽ�ļ�
	 */
	private SceneMode loadOneSceneMode(File file) throws Exception {
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			throw new Exception("�ļ�" + file.getName() + "�����ڣ�");
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			return readSceneMode(document);
		} catch (Exception e) {
			throw new Exception("��ȡ�ļ�" + file.getName() + "ʧ�ܣ�");
		}
	}

	/**
	 * ��document�����ж�ȡ��scenemode
	 */
	private SceneMode readSceneMode(Document document) {
		SceneMode sceneMode = new SceneMode();
		Element root = document.getRootElement();
		sceneMode.setName(root.element(NAME).getTextTrim());
		sceneMode.setIcon(root.element(ICON).getTextTrim());
		List<Element> itemElements = document.selectNodes(SCENEMODE_ITEM_PATH);// ע�������д����
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
	 * ɾ��һ���龰ģʽ��Ҳ����ɾ��ģʽ�ļ�
	 */
	public void deleteSceneMode(SceneMode sceneMode) {
		File file = new File(fileUtil.getUserDirectory(FileUtil.SCENE_DIRECTORY) + sceneMode.getName() + ".xml");
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * ������scenemode---ֱ����ɾ��ԭ�����ļ���Ȼ�����´���һ���ļ�
	 */
	public void rename(SceneMode sceneMode, String name) throws Exception {
		deleteSceneMode(sceneMode);
		sceneMode.setName(name);
		newSceneMode(sceneMode);
	}

	/**
	 * ����sceneMode
	 */
	public void save(SceneMode sceneMode) throws Exception {
		deleteSceneMode(sceneMode);
		newSceneMode(sceneMode);
	}
	
	public void refresh(SceneMode sceneMode) throws Exception{
		String filePath = fileUtil.getUserDirectory(FileUtil.SCENE_DIRECTORY) + sceneMode.getName() + ".xml";
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������//�ļ�������
			try {
				file.createNewFile();// �ļ������ھ��½�һ��
			} catch (IOException e) {
				throw new Exception("�����ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
			newSceneMode(sceneMode);
			return;
		}
		try {// �ļ�����
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			Element root = document.getRootElement();
			Element icon = root.element("icon");
			if(AppliactionUtil.DEBUG) System.out.println("6666");
			icon.setText(sceneMode.getIcon());
			if(AppliactionUtil.DEBUG) System.out.println("33333333333333333");
			try {
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// ע��һ��Ҫ��utf-8��ʽ
				writer.write(document);
				writer.close();
			} catch (Exception ex) {
				throw new Exception("д���ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
		} catch (Exception e) {
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
		}
	}


}
