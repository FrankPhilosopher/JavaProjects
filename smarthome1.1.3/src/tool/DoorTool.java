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
 * �Ŵ�xml������
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
	 * �����Ŵ��豸
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
	 * ��������downĿ¼�µ��Ŵ��豸
	 */
	private void loadDownControls(List<DoorControl> controls) throws Exception {
		String filePath = FileUtil.DOWN_DIRECTORY + FileUtil.DOOR_XML;
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
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");// tested
		}
	}

	/**
	 * ��������localĿ¼�µ��Ŵ��豸
	 */
	private void loadLocalControls(List<DoorControl> controls) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.DOOR_XML);
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			// System.out.println("�ļ� " + filePath + " �����ڣ�");//tested
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
	public void rename(DoorControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.DOOR_XML);
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
	private void newLocalXml(DoorControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.DOOR_XML);
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
		// д�뵽�ļ���
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
	private void writeNewDevice(Document document, DoorControl control) {
		Element root = document.getRootElement();
		Element element = root.addElement(NODE);
		element.addAttribute(ID, String.valueOf(control.getId()));
		Element name = element.addElement(NAME);
		name.setText(control.getName());
	}

}
