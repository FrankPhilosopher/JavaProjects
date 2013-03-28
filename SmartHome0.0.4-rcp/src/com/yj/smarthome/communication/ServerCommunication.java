package com.yj.smarthome.communication;

import com.yj.smarthome.util.SystemUtil;

/**
 * ��Զ�̷�����ͨ�ŵ���
 * 
 * @author yinger
 * 
 */
public class ServerCommunication extends AbstractCommunication {

	//��������ʼ��ʱִ�У��������ʵ����֮ǰ
//	static {
//		IP = SystemUtil.Server_IP;
//		PORT = SystemUtil.Server_Port;
//	}

	private static ServerCommunication serverCommunication;

	private ServerCommunication() throws Exception {
		super(SystemUtil.SERVER_IP, SystemUtil.SERVER_PORT);
		startConnection();
	}

	@Override
	public boolean closeConnection() throws Exception {
		super.closeConnection();
		serverCommunication = null;//����Ǳ���ģ����ڱ�֤�����getInstance
		return true;
	}

	public static ServerCommunication getInstance() throws Exception {
		if (serverCommunication == null) {
			serverCommunication = new ServerCommunication();
		}
		return serverCommunication;
	}

}
