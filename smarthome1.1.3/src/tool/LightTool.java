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
 * �ƿ�xml������
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
	 * ���صƿ��豸
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
	 * ��������downĿ¼�µĵƿ��豸
	 */
	private void loadDownControls(List<LightControl> controls) throws Exception {
		String filePath = FileUtil.DOWN_DIRECTORY + FileUtil.LIGHT_XML;
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			if(AppliactionUtil.DEBUG) System.out.println("�ļ� " + filePath + " �����ڣ�");
			throw new Exception("�ļ� " + filePath + " �����ڣ�");// tested
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
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");// tested
		}
	}

	/**
	 * ��������localĿ¼�µĵƿ��豸
	 */
	private void loadLocalControls(List<LightControl> controls) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.LIGHT_XML);
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
						controls.get(j).setRoomid(node.elementTextTrim(ROOMID));
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");// tested
		}
	}

	/**
	 * �������豸---�ļ�Ϊlocal/lightcontrolview.xml
	 */
	public void rename(LightControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.LIGHT_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
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
	 * ���뷿��---�ļ�Ϊlocal/lightcontrolview.xml
	 */
	public void enterRoom(LightControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.LIGHT_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
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
					node.element(ROOMID).setText(control.getRoomid());
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
	 * �˳�����---�ļ�Ϊlocal/lightcontrolview.xml
	 */
	public void quitRoom(LightControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.LIGHT_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ������ڣ������ڼ����˳���
			return;
		}
		try {// �ļ�����
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
					break;// һ��Ҫbreak
				}
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
	 * �½�һ��localĿ¼�µ�lightcontrolview.xml�ļ�
	 */
	private void newLocalXml(LightControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.LIGHT_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			try {
				file.createNewFile();// �ļ������ھ��½�һ��
			} catch (IOException e) {
				throw new Exception("�����ļ�" + filePath + "ʧ�ܣ�");// һ�㲻��ʧ��
			}
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(NODES);
		writeNewDevice(document, control);// ���Ԫ��
		try {// д�뵽�ļ���
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
