package com.yj.smarthome.util;

import java.io.File;

import com.yj.smarthome.beans.User;
import com.yj.smarthome.communication.GatewayCommunication;
import com.yj.smarthome.communication.ICommunication;
import com.yj.smarthome.communication.ServerCommunication;
import com.yj.smarthome.xmlImpls.SystemXmlTool;

/**
 * 
 * 系统配置信息类
 * 
 * @author yinger
 * 
 */
public class SystemUtil {

	//系统的配置信息，本来是打算使用properties的，但是考虑到数据的冗余，还是使用XML存储
//	public static Properties systemProperties = new Properties();

	public static final String SERVERIP_STRING = "ServerIP";
	public static final String SERVERPORT_STRING = "ServerPort";
	public static final String GATEWAYIP_STRING = "GatewayIP";
	public static final String GATEWAYPORT_STRING = "GatewayPort";

	//这些量都是static，不知道行不行，但是不是final的
	public static int SERVER_PORT;
	public static int GATEWAY_PORT;
	public static String SERVER_IP;
	public static String GATEWAY_IP;

	public static User LOGIN_USER;//这里保存当前登录的用户
	public static String LOGIN_OK = "loginok";//这个最好是统一写死，不然容易造成手误导致问题
	public static StringBuffer MESSAGE = new StringBuffer("");//保存一个信息，初始时是空的，它可以用在很多的地方！

	//所有设备的状态数组
	public static int[][] ALL_DEVICE_STATE = new int[100][100];//设备类型，设备编号，设备状态

	//设置一些不变的字符串
	public static final String GATEWAY_NAME = "网关服务器";
	public static final String SERVER_NAME = "远程服务器";

	//各种控制
	public static final String DOOR_CONTROL = "门窗控制";
	public static final String LIGHT_CONTROL = "灯光控制";
	public static final String APPLIANCE_CONTROL = "家电控制";
	public static final String SECURITY_CONTROL = "安防控制";
	public static final String SENSOR_CONTROL = "传感器控制";
	public static final String CAMERAMONITOR_CONTROL = "DVR摄像头视频监控";
	public static final String IPCAMERAMONITOR_CONTROL = "IP摄像头视频监控";
	public static final String SCENEMODE = "情景模式";
	public static final String REFRESHDEVICIES = "刷新设备";

	public static final String DEVICENAMEARRAY[] = { "", "", LIGHT_CONTROL, DOOR_CONTROL, SECURITY_CONTROL, SENSOR_CONTROL,
			APPLIANCE_CONTROL };

	//静态代码块
	//需要注意的是，使用中如果修改了设置，那么要将修改映射到相应的通信类
	static {
//		//系统启动时这里要读取本地的配置文件，给上面的属性赋上初始值，目前是直接赋值
//		//如果没有的话就用这个默认值，有的话就会被赋上新的值
		SERVER_PORT = 8080;
		SERVER_IP = "192.168.0.232";
		GATEWAY_PORT = 6000;
		GATEWAY_IP = "192.168.0.100";
	}

	//系统启动时会给它赋值，如果没有，那么就是默认值
	public static ICommunication communication;//默认的通信类是和远程服务器连接的通信类

	//从文件中获取系统的配置信息
	public static void loadSystemSettings() throws Exception {
		File file = new File(FileUtil.SYSTEM_XML);
		if (!file.exists()) {//文件不存在表示还没有系统配置信息，那么就创建新的配置文件
			SystemXmlTool.getInstance().newSystemXml();
			return;
		}
		//如果文件存在的话，就读取文件并保存到相应的字段
		SystemXmlTool.getInstance().readSystemSetting();
	}

	//保存配置信息，这里暂时的只是重新建立配置文件
	public static void saveSystemSettings() throws Exception {
		SystemXmlTool.getInstance().newSystemXml();
	}

	//根据传入的type类型建立连接
	public static void buildCommunication(String type) throws Exception {
		if (type.equals(SystemUtil.GATEWAY_NAME)) {
			if (SystemUtil.communication != null) {
				if (SystemUtil.communication instanceof ServerCommunication) {
					SystemUtil.communication.closeConnection();//首先关闭原来的连接
					GatewayCommunication gatewayCommunication = GatewayCommunication.getInstance();
					SystemUtil.communication = gatewayCommunication;
				}
				//否则就是GatewayCommunication
			} else {
				GatewayCommunication gatewayCommunication = GatewayCommunication.getInstance();
				SystemUtil.communication = gatewayCommunication;
			}
		} else {
			if (SystemUtil.communication != null) {
				if (SystemUtil.communication instanceof GatewayCommunication) {
					SystemUtil.communication.closeConnection();//首先关闭原来的连接
					ServerCommunication serverCommunication = ServerCommunication.getInstance();
					SystemUtil.communication = serverCommunication;
				}
				//否则就是serverCommunication
			} else {
				ServerCommunication serverCommunication = ServerCommunication.getInstance();
				SystemUtil.communication = serverCommunication;
			}
		}
	}

	//重新建立连接
	public static void resetCommunication() throws Exception {
		SystemUtil.communication.restartConnection();
	}

	//初始化系统配置
//	private static void initSystemSettings() {
//		Server_Port = Integer.parseInt(systemProperties.getProperty(ServerPort));
//		Server_IP = systemProperties.getProperty(ServerIP);
//		Gateway_Port = Integer.parseInt(systemProperties.getProperty(GatewayPort));
//		Gateway_IP = systemProperties.getProperty(GatewayIP);
//	}

}
