package com.yj.smarthome.util;

import java.io.File;

import com.yj.smarthome.beans.User;
import com.yj.smarthome.communication.GatewayCommunication;
import com.yj.smarthome.communication.ICommunication;
import com.yj.smarthome.communication.ServerCommunication;
import com.yj.smarthome.xmlImpls.SystemXmlTool;

/**
 * 
 * ϵͳ������Ϣ��
 * 
 * @author yinger
 * 
 */
public class SystemUtil {

	//ϵͳ��������Ϣ�������Ǵ���ʹ��properties�ģ����ǿ��ǵ����ݵ����࣬����ʹ��XML�洢
//	public static Properties systemProperties = new Properties();

	public static final String SERVERIP_STRING = "ServerIP";
	public static final String SERVERPORT_STRING = "ServerPort";
	public static final String GATEWAYIP_STRING = "GatewayIP";
	public static final String GATEWAYPORT_STRING = "GatewayPort";

	//��Щ������static����֪���в��У����ǲ���final��
	public static int SERVER_PORT;
	public static int GATEWAY_PORT;
	public static String SERVER_IP;
	public static String GATEWAY_IP;

	public static User LOGIN_USER;//���ﱣ�浱ǰ��¼���û�
	public static String LOGIN_OK = "loginok";//��������ͳһд������Ȼ�����������������
	public static StringBuffer MESSAGE = new StringBuffer("");//����һ����Ϣ����ʼʱ�ǿյģ����������ںܶ�ĵط���

	//�����豸��״̬����
	public static int[][] ALL_DEVICE_STATE = new int[100][100];//�豸���ͣ��豸��ţ��豸״̬

	//����һЩ������ַ���
	public static final String GATEWAY_NAME = "���ط�����";
	public static final String SERVER_NAME = "Զ�̷�����";

	//���ֿ���
	public static final String DOOR_CONTROL = "�Ŵ�����";
	public static final String LIGHT_CONTROL = "�ƹ����";
	public static final String APPLIANCE_CONTROL = "�ҵ����";
	public static final String SECURITY_CONTROL = "��������";
	public static final String SENSOR_CONTROL = "����������";
	public static final String CAMERAMONITOR_CONTROL = "DVR����ͷ��Ƶ���";
	public static final String IPCAMERAMONITOR_CONTROL = "IP����ͷ��Ƶ���";
	public static final String SCENEMODE = "�龰ģʽ";
	public static final String REFRESHDEVICIES = "ˢ���豸";

	public static final String DEVICENAMEARRAY[] = { "", "", LIGHT_CONTROL, DOOR_CONTROL, SECURITY_CONTROL, SENSOR_CONTROL,
			APPLIANCE_CONTROL };

	//��̬�����
	//��Ҫע����ǣ�ʹ��������޸������ã���ôҪ���޸�ӳ�䵽��Ӧ��ͨ����
	static {
//		//ϵͳ����ʱ����Ҫ��ȡ���ص������ļ�������������Ը��ϳ�ʼֵ��Ŀǰ��ֱ�Ӹ�ֵ
//		//���û�еĻ��������Ĭ��ֵ���еĻ��ͻᱻ�����µ�ֵ
		SERVER_PORT = 8080;
		SERVER_IP = "192.168.0.232";
		GATEWAY_PORT = 6000;
		GATEWAY_IP = "192.168.0.100";
	}

	//ϵͳ����ʱ�������ֵ�����û�У���ô����Ĭ��ֵ
	public static ICommunication communication;//Ĭ�ϵ�ͨ�����Ǻ�Զ�̷��������ӵ�ͨ����

	//���ļ��л�ȡϵͳ��������Ϣ
	public static void loadSystemSettings() throws Exception {
		File file = new File(FileUtil.SYSTEM_XML);
		if (!file.exists()) {//�ļ������ڱ�ʾ��û��ϵͳ������Ϣ����ô�ʹ����µ������ļ�
			SystemXmlTool.getInstance().newSystemXml();
			return;
		}
		//����ļ����ڵĻ����Ͷ�ȡ�ļ������浽��Ӧ���ֶ�
		SystemXmlTool.getInstance().readSystemSetting();
	}

	//����������Ϣ��������ʱ��ֻ�����½��������ļ�
	public static void saveSystemSettings() throws Exception {
		SystemXmlTool.getInstance().newSystemXml();
	}

	//���ݴ����type���ͽ�������
	public static void buildCommunication(String type) throws Exception {
		if (type.equals(SystemUtil.GATEWAY_NAME)) {
			if (SystemUtil.communication != null) {
				if (SystemUtil.communication instanceof ServerCommunication) {
					SystemUtil.communication.closeConnection();//���ȹر�ԭ��������
					GatewayCommunication gatewayCommunication = GatewayCommunication.getInstance();
					SystemUtil.communication = gatewayCommunication;
				}
				//�������GatewayCommunication
			} else {
				GatewayCommunication gatewayCommunication = GatewayCommunication.getInstance();
				SystemUtil.communication = gatewayCommunication;
			}
		} else {
			if (SystemUtil.communication != null) {
				if (SystemUtil.communication instanceof GatewayCommunication) {
					SystemUtil.communication.closeConnection();//���ȹر�ԭ��������
					ServerCommunication serverCommunication = ServerCommunication.getInstance();
					SystemUtil.communication = serverCommunication;
				}
				//�������serverCommunication
			} else {
				ServerCommunication serverCommunication = ServerCommunication.getInstance();
				SystemUtil.communication = serverCommunication;
			}
		}
	}

	//���½�������
	public static void resetCommunication() throws Exception {
		SystemUtil.communication.restartConnection();
	}

	//��ʼ��ϵͳ����
//	private static void initSystemSettings() {
//		Server_Port = Integer.parseInt(systemProperties.getProperty(ServerPort));
//		Server_IP = systemProperties.getProperty(ServerIP);
//		Gateway_Port = Integer.parseInt(systemProperties.getProperty(GatewayPort));
//		Gateway_IP = systemProperties.getProperty(GatewayIP);
//	}

}
