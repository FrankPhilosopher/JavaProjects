package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import util.FileUtil;
import util.ProtocolUtil;
import beans.User;

/**
 * 
 * Ӧ��ϵͳ��Ϣ��
 * 
 * @author yinger
 * 
 */
public class SystemTool {

	private static SystemTool instance;
	private FileUtil fileUtil;

	public static User CURRENT_USER;// ��ǰ��¼�û�

	public static int[][] ALL_DEVICE_STATE = new int[10][256];// A[B]=C A�豸���ͣ�B�豸��ţ�C�豸״̬

	private final String NODEID = "id";
	private final String STATE_LIGHTBUTTON = "//state/lightcontrolview/lightbutton";
	private final String STATE_DOORWINDOW = "//state/doorwindowsview/dw";
	private final String STATE_SECURITY = "//state/securityview/security";
	private final String STATE_APPLIANCE = "//state/applianceview/appliance";

	// xml elements
	private final String ROOT_STRING = "SYSTEM";
	private final String STRING_SERVERIP = "SERVERIP";
	private final String STRING_SERVERPORT = "SERVERPORT";
	private final String STRING_SERVERDOWNPORT = "SERVERDOWNPORT";
	private final String STRING_GATEWAYIP = "GATEWAYIP";
	private final String STRING_GATEWAYPORT = "GATEWAYPORT";
	private final String STRING_GATEWAYDOWNPORT = "GATEWAYDOWNPORT";
	private       String STRING_CACHEDIP = "CACHEDIP";
	private final String STRING_VERSION = "VERSION";
	
	private final String STRING_CACHEDNAME = "CACHEDNAME";

	// net paramaters Ĭ��ֵ
	private int server_port = 6000; // �������˿�
	private String server_ip = "www.iever.cn"; // ������ip
	private int server_downport = 80; // ���������ض˿�
	private int gateway_port = 5000; // ���ط��ʶ˿�
	private String gateway_ip = "192.168.1.232"; // ���ط���ip
	private int gateway_downport = 80; // �������ض˿�
	private String cachedip = ""; // �����Զ�̷����µ�����ip
	private String version = "1.1.3"; // ��ǰ�İ汾��
	
	//�¼ӵ�cachename
	private String cachedname = "";  //��ǰ����IP���û���

	private SystemTool() {
		fileUtil = FileUtil.getInstance();// ע�⣬ϵͳ����ʱsystemtoolһ�����ʼ����Ҳ�͵�����fileutil�ĳ�ʼ�����Ӷ�����û�е�Ŀ¼
	}

	public static SystemTool getInstance() {
		if (instance == null) {
			instance = new SystemTool();
		}
		return instance;
	}

	/**
	 * ��ȡϵͳ����xml�ļ�
	 */
	public void readSystemXml() throws Exception {
		File file = new File(FileUtil.SYSTEM_XML);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������// �ļ������ڱ�ʾ��û��ϵͳ������Ϣ����ô�ʹ����µ������ļ�
			instance.newSystemXml();// �������µ�������Ϣ�����õ�ǰ��Ĭ�ϵ�����
			return;
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");// ��ָ���ı��뷽ʽ��ȡ�ļ�
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			Element node = document.getRootElement();
//			server_ip = node.element(STRING_SERVERIP).getText().trim();
//			server_port = Integer.parseInt(node.element(STRING_SERVERPORT).getText().trim());
//			server_downport = Integer.parseInt(node.element(STRING_SERVERDOWNPORT).getText().trim());
//			gateway_ip = node.element(STRING_GATEWAYIP).getText().trim();
//			gateway_port = Integer.parseInt(node.element(STRING_GATEWAYPORT).getText().trim());
//			gateway_downport = Integer.parseInt(node.element(STRING_GATEWAYDOWNPORT).getText().trim());
//			//���û���¼���⣬����������cachedip������û�q��
//			
//			cachedip = node.element(STRING_CACHEDIP).getTextTrim();
//			version = node.element(STRING_VERSION).getTextTrim();
			
			setServer_ip(node.element(STRING_SERVERIP).getText().trim());
			setServer_port(Integer.parseInt(node.element(STRING_SERVERPORT).getText().trim()));
			setServer_downport(Integer.parseInt(node.element(STRING_SERVERDOWNPORT).getText().trim()));
			setGateway_ip(node.element(STRING_GATEWAYIP).getText().trim());
			setGateway_port(Integer.parseInt(node.element(STRING_GATEWAYPORT).getText().trim()));
			setGateway_downport(Integer.parseInt(node.element(STRING_GATEWAYDOWNPORT).getText().trim()));
			setVersion(version);
			
			//�õ�cache��name
			setCachedname(node.element(STRING_CACHEDIP).attributeValue(STRING_CACHEDNAME));
			
			setCachedip(node.element(STRING_CACHEDIP).getTextTrim());
		} catch (Exception e) {
			throw new Exception("��ȡϵͳ�����ļ�ʧ�ܣ�");
		}
	}

	/**
	 * �����µ�ϵͳ����xml�ļ�
	 */
	public void newSystemXml() throws Exception {
		File file = new File(FileUtil.SYSTEM_XML);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			file.createNewFile();
		}
		// д��document
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(ROOT_STRING);
		Element element = root.addElement(STRING_SERVERIP);
		element.setText(server_ip);
		element = root.addElement(STRING_SERVERPORT);
		element.setText(String.valueOf(server_port));
		element = root.addElement(STRING_SERVERDOWNPORT);
		element.setText(String.valueOf(server_downport));
		element = root.addElement(STRING_GATEWAYIP);
		element.setText(gateway_ip);
		element = root.addElement(STRING_GATEWAYPORT);
		element.setText(String.valueOf(gateway_port));
		element = root.addElement(STRING_GATEWAYDOWNPORT);
		element.setText(String.valueOf(gateway_downport));
		element = root.addElement(STRING_CACHEDIP);
		
		element.addAttribute(STRING_CACHEDNAME, cachedname);  //д��cahchname
		
		element.setText(cachedip);
		element = root.addElement(STRING_VERSION);
		element.setText(version);
		// д�뵽�ļ���
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// ע��һ��Ҫ��utf-8��ʽ
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			throw new Exception("����ϵͳ�����ļ�ʧ�ܣ�");
		}
	}

	/**
	 * ��ʼ�������豸��״̬
	 */
	public void initDeviceState() throws Exception {
		String filePath = fileUtil.getStateXmlFile();
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			throw new Exception("�豸״̬�ļ������ڣ�");
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");// ��ָ���ı��뷽ʽ��ȡ�ļ�
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			// �����ǵƿ�
			List lightList = document.selectNodes(STATE_LIGHTBUTTON);
			for (int i = 0; i < lightList.size(); i++) {
				Element node = (Element) lightList.get(i);
				ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_LIGHT][Integer.parseInt(node.attributeValue(NODEID))] = Integer
						.parseInt(node.getText().trim());// �޸��豸��״ֵ̬
			}

			// �������Ŵ�
			List doorList = document.selectNodes(STATE_DOORWINDOW);
			for (int i = 0; i < doorList.size(); i++) {
				Element node = (Element) doorList.get(i);
				ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_DOOR][Integer.parseInt(node.attributeValue(NODEID))] = Integer
						.parseInt(node.getText().trim());// �޸��豸��״ֵ̬
			}

			// �����ǰ���
			List securityList = document.selectNodes(STATE_SECURITY);
			for (int i = 0; i < securityList.size(); i++) {
				Element node = (Element) securityList.get(i);
				ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_SECURITY][Integer.parseInt(node.attributeValue(NODEID))] = Integer
						.parseInt(node.getText().trim());// �޸��豸��״ֵ̬
			}

			// �����Ǽҵ�
			List applianceList = document.selectNodes(STATE_APPLIANCE);
			for (int i = 0; i < applianceList.size(); i++) {
				Element node = (Element) applianceList.get(i);
				ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_APPLIANCE][Integer.parseInt(node.attributeValue(NODEID))] = Integer
						.parseInt(node.getText().trim());// �޸��豸��״ֵ̬
			}
		} catch (Exception e) {
			throw new Exception("��ȡ�豸״̬�ļ�ʧ�ܣ�");
		}
	}

	public int getServer_port() {
		return server_port;
	}

	public String getServer_ip() {
		return server_ip;
	}

	public int getServer_downport() {
		return server_downport;
	}

	public int getGateway_port() {
		return gateway_port;
	}

	public String getGateway_ip() {
		return gateway_ip;
	}

	public int getGateway_downport() {
		return gateway_downport;
	}

	public void setServer_port(int server_port) {
		this.server_port = server_port;
	}

	public void setServer_ip(String server_ip) {
		this.server_ip = server_ip;
	}

	public void setServer_downport(int server_downport) {
		this.server_downport = server_downport;
	}

	public void setGateway_port(int gateway_port) {
		this.gateway_port = gateway_port;
	}

	public void setGateway_ip(String gateway_ip) {
		this.gateway_ip = gateway_ip;
	}

	public void setGateway_downport(int gateway_downport) {
		this.gateway_downport = gateway_downport;
	}

	public void setCachedip(String cachedip) {
		this.cachedip = cachedip;
	}

	public String getCachedip() {
		return cachedip;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCachedname() {
		return cachedname;
	}

	public void setCachedname(String cachedname) {
		this.cachedname = cachedname;
	}
	
	

}
