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
 * �ҵ�xml������
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
	 * ���ؼҵ��豸
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
	 * ��������downĿ¼�µļҵ��豸
	 */
	private void loadDownControls(List<ApplianceControl> controls) throws Exception {
		String filePath = FileUtil.DOWN_DIRECTORY + FileUtil.APPLIANCE_XML;
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			throw new Exception("�ļ� " + filePath + " �����ڣ�");// tested
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
			for (int i = 0; i < list.size(); i++) {// �����ҵ��б�
				node = (Element) list.get(i);
				control = new ApplianceControl();
				control.setId(Integer.parseInt(node.attributeValue(ID)));
				control.setName(node.attributeValue(NAME));
				List actionList = node.elements(ACTION);
				for (int j = 0; j < actionList.size(); j++) {// �����ҵ��action�б�
					actionElement = (Element) actionList.get(j);
					style = Integer.parseInt(actionElement.attributeValue(STYLE));
					action = new ApplianceAction();
					action.setStyle(style);// common part
					action.setName(actionElement.attributeValue(NAME));
					action.setAid(Integer.parseInt(actionElement.attributeValue(ACTIONID)));
					if (style == ApplianceAction.ACTIONTYPE_SINGLE) {// ������Ӧ
						action.setCode(Integer.parseInt(actionElement.getTextTrim()));
						if (action.getAid() == ProtocolUtil.COMMAND_ON) {// ���ÿ��͹�����
							control.setCommand_open(action.getCode());
						} else if (action.getAid() == ProtocolUtil.COMMAND_OFF) {
							control.setCommand_close(action.getCode());
						}
					} else if (style == ApplianceAction.ACTIONTYPE_CYCLE) {// ѭ����Ӧ
						addSubActions(actionElement, action);
					} else if (style == ApplianceAction.ACTIONTYPE_INCREASE) {// ������Ӧ
						addSubActions(actionElement, action);
					} else if (style == ApplianceAction.ACTIONTYPE_DECREASE) {// �ݼ���Ӧ
						ApplianceAction increaseAction = null;
						for (int k = 0; k < control.getActions().size(); k++) {
							increaseAction = control.getActions().get(k);
							if (increaseAction.getAid() == action.getAid()) {// �ҵ�aid��ͬ��action
								action.setSubActions(increaseAction.getSubActions());
								// �󶨵����ݼ���Ӧ��creaseIndex��ֵ���������Ҫ��
								action.creaseIndexProperty().bindBidirectional(increaseAction.creaseIndexProperty());
								// ��btntext����
								action.btntextProperty().bindBidirectional(increaseAction.btntextProperty());
							}
						}
					}
					control.getActions().add(action);
				}
				controls.add(control);
			}
		} catch (Exception e) {
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");// tested
		}
	}

	/**
	 * ���element�µ�����Ӧ����Ӧaction��
	 */
	private void addSubActions(Element actionElement, ApplianceAction action) {
		List subList = actionElement.elements(SUBACTION);
		Element subElement;
		SubAction subAction;
		for (int k = 0; k < subList.size(); k++) {// ��������Ӧ����
			subElement = (Element) subList.get(k);
			subAction = new SubAction();
			subAction.setSid(Integer.parseInt(subElement.attributeValue(SUBACTIONID)));
			subAction.setName(subElement.attributeValue(NAME));
			subAction.setCode(Integer.parseInt(subElement.getTextTrim()));
			action.getSubActions().add(subAction);
		}
	}

	/**
	 * ��������localĿ¼�µļҵ��豸
	 */
	private void loadLocalControls(List<ApplianceControl> controls) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.APPLIANCE_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
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
				for (int j = 0; j < controls.size(); j++) {// ����--�ҵ�id��ͬ�ģ�����name
					if (Integer.parseInt(node.attributeValue(ID)) == controls.get(j).getId()) {
						controls.get(j).setName(node.elementTextTrim(NAME));
						controls.get(j).setIcon((node.elementTextTrim(ICON)));
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");// tested
		}
	}

	/**
	 * �������豸�����޸�ͼƬ---�ļ�Ϊlocal/appliancecontrolview.xml
	 */
	public void refresh(ApplianceControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.APPLIANCE_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������//�ļ�������
			try {
				file.createNewFile();// �ļ������ھ��½�һ��
			} catch (IOException e) {
				throw new Exception("�����ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
			newLocalXml(control);
			return;
		}
		try {// �ļ�����
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
					break;// һ��Ҫbreak
				}
			}
			if (i == list.size()) {// ���û�еĻ�����ô��Ҫ����һ��node
				writeNewDevice(document, control);
			}
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

	/**
	 * �½�һ��localĿ¼�µ�doorcontrolview.xml�ļ�
	 */
	private void newLocalXml(ApplianceControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.APPLIANCE_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������//�ļ�������
			try {
				file.createNewFile();// �ļ������ھ��½�һ��
			} catch (IOException e) {
				throw new Exception("�����ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(NODES);
		writeNewDevice(document, control);// ���Ԫ��
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// ע��һ��Ҫ��utf-8��ʽ
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			throw new Exception("�����ļ�" + file.getName() + "ʧ�ܣ�");// һ�㲻��ʧ��
		}
	}

	/**
	 * ����µ��豸���Ƶ����ص��豸�����ļ���
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
