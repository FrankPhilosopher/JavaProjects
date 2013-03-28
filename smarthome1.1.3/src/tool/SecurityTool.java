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
import beans.SecurityControl;

/**
 * �����豸xml������
 * 
 * @author yinger
 * 
 */
public class SecurityTool {

	private static SecurityTool instance;
	private FileUtil fileUtil;

	private final String ID = "id";
	private final String NAME = "name";
	private final String BUTTONOFF = "close";
	private final String BUTTONON = "open";
	private final String NODE = "security";
	private final String NODES = "securitys";
	private final String NODEPATH = "//securitys/security";

	private SecurityTool() {
		fileUtil = FileUtil.getInstance();
	}

	public static SecurityTool getInstance() {
		if (instance == null) {
			instance = new SecurityTool();
		}
		return instance;
	}

	/**
	 * ���ذ����豸
	 */
	public List<beans.SecurityControl> loadControls() throws Exception {
		List<SecurityControl> controls = new ArrayList<SecurityControl>();
		loadDownControls(controls);
		if (controls != null && controls.size() > 0) {
			loadLocalControls(controls);
		}
		return controls;
	}

	/**
	 * ��������downĿ¼�µİ����豸
	 */
	private void loadDownControls(List<SecurityControl> controls) throws Exception {
		String filePath = FileUtil.DOWN_DIRECTORY + FileUtil.SECURITY_XML;
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
			SecurityControl control;
			for (int i = 0; i < list.size(); i++) {
				node = (Element) list.get(i);
				control = new SecurityControl();
				control.setId(Integer.parseInt(node.attributeValue(ID)));
				control.setName(node.element(NAME).getTextTrim());
				control.setCommand_open(Integer.parseInt(node.element(BUTTONON).getTextTrim()));
				control.setCommand_close(Integer.parseInt(node.element(BUTTONOFF).getTextTrim()));
				control.setState(SystemTool.ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_SECURITY][control.getId()]);
				controls.add(control);
			}
		} catch (Exception e) {
			throw new Exception("��ȡ�ļ�" + filePath + "ʧ�ܣ�");// tested
		}
	}

	/**
	 * ��������localĿ¼�µİ����豸
	 */
	private void loadLocalControls(List<SecurityControl> controls) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.SECURITY_XML);
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
	public void rename(SecurityControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.SECURITY_XML);
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
	 * �½�һ��localĿ¼�µ�securitycontrolview.xml�ļ�
	 */
	private void newLocalXml(SecurityControl control) throws Exception {
		String filePath = fileUtil.getUserFile(FileUtil.SECURITY_XML);
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
	private void writeNewDevice(Document document, SecurityControl control) {
		Element root = document.getRootElement();
		Element element = root.addElement(NODE);
		element.addAttribute(ID, String.valueOf(control.getId()));
		Element name = element.addElement(NAME);
		name.setText(control.getName());
	}

}
