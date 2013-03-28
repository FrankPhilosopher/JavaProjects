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
 * 应用系统信息类
 * 
 * @author yinger
 * 
 */
public class SystemTool {

	private static SystemTool instance;
	private FileUtil fileUtil;

	public static User CURRENT_USER;// 当前登录用户

	public static int[][] ALL_DEVICE_STATE = new int[10][256];// A[B]=C A设备类型，B设备编号，C设备状态

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

	// net paramaters 默认值
	private int server_port = 6000; // 服务器端口
	private String server_ip = "www.iever.cn"; // 服务器ip
	private int server_downport = 80; // 服务器下载端口
	private int gateway_port = 5000; // 网关访问端口
	private String gateway_ip = "192.168.1.232"; // 网关访问ip
	private int gateway_downport = 80; // 网关下载端口
	private String cachedip = ""; // 缓存的远程访问下的网关ip
	private String version = "1.1.3"; // 当前的版本号
	
	//新加的cachename
	private String cachedname = "";  //当前缓存IP的用户名

	private SystemTool() {
		fileUtil = FileUtil.getInstance();// 注意，系统启动时systemtool一定会初始化，也就导致了fileutil的初始化，从而创建没有的目录
	}

	public static SystemTool getInstance() {
		if (instance == null) {
			instance = new SystemTool();
		}
		return instance;
	}

	/**
	 * 读取系统配置xml文件
	 */
	public void readSystemXml() throws Exception {
		File file = new File(FileUtil.SYSTEM_XML);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在// 文件不存在表示还没有系统配置信息，那么就创建新的配置文件
			instance.newSystemXml();// 创建的新的配置信息就是用当前的默认的数据
			return;
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");// 用指定的编码方式读取文件
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			Element node = document.getRootElement();
//			server_ip = node.element(STRING_SERVERIP).getText().trim();
//			server_port = Integer.parseInt(node.element(STRING_SERVERPORT).getText().trim());
//			server_downport = Integer.parseInt(node.element(STRING_SERVERDOWNPORT).getText().trim());
//			gateway_ip = node.element(STRING_GATEWAYIP).getText().trim();
//			gateway_port = Integer.parseInt(node.element(STRING_GATEWAYPORT).getText().trim());
//			gateway_downport = Integer.parseInt(node.element(STRING_GATEWAYDOWNPORT).getText().trim());
//			//多用户登录问题，处理方法（在cachedip里加上用户q）
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
			
			//得到cache的name
			setCachedname(node.element(STRING_CACHEDIP).attributeValue(STRING_CACHEDNAME));
			
			setCachedip(node.element(STRING_CACHEDIP).getTextTrim());
		} catch (Exception e) {
			throw new Exception("读取系统配置文件失败！");
		}
	}

	/**
	 * 创建新的系统配置xml文件
	 */
	public void newSystemXml() throws Exception {
		File file = new File(FileUtil.SYSTEM_XML);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			file.createNewFile();
		}
		// 写入document
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
		
		element.addAttribute(STRING_CACHEDNAME, cachedname);  //写入cahchname
		
		element.setText(cachedip);
		element = root.addElement(STRING_VERSION);
		element.setText(version);
		// 写入到文件中
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 注意一定要是utf-8格式
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			throw new Exception("创建系统配置文件失败！");
		}
	}

	/**
	 * 初始化所有设备的状态
	 */
	public void initDeviceState() throws Exception {
		String filePath = fileUtil.getStateXmlFile();
		File file = new File(filePath);
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			throw new Exception("设备状态文件不存在！");
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");// 用指定的编码方式读取文件
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			// 这里是灯控
			List lightList = document.selectNodes(STATE_LIGHTBUTTON);
			for (int i = 0; i < lightList.size(); i++) {
				Element node = (Element) lightList.get(i);
				ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_LIGHT][Integer.parseInt(node.attributeValue(NODEID))] = Integer
						.parseInt(node.getText().trim());// 修改设备的状态值
			}

			// 这里是门窗
			List doorList = document.selectNodes(STATE_DOORWINDOW);
			for (int i = 0; i < doorList.size(); i++) {
				Element node = (Element) doorList.get(i);
				ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_DOOR][Integer.parseInt(node.attributeValue(NODEID))] = Integer
						.parseInt(node.getText().trim());// 修改设备的状态值
			}

			// 这里是安防
			List securityList = document.selectNodes(STATE_SECURITY);
			for (int i = 0; i < securityList.size(); i++) {
				Element node = (Element) securityList.get(i);
				ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_SECURITY][Integer.parseInt(node.attributeValue(NODEID))] = Integer
						.parseInt(node.getText().trim());// 修改设备的状态值
			}

			// 这里是家电
			List applianceList = document.selectNodes(STATE_APPLIANCE);
			for (int i = 0; i < applianceList.size(); i++) {
				Element node = (Element) applianceList.get(i);
				ALL_DEVICE_STATE[ProtocolUtil.DEVICETYPE_APPLIANCE][Integer.parseInt(node.attributeValue(NODEID))] = Integer
						.parseInt(node.getText().trim());// 修改设备的状态值
			}
		} catch (Exception e) {
			throw new Exception("读取设备状态文件失败！");
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
