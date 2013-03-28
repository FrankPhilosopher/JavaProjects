package com.yj.smarthome.communication;

import com.yj.smarthome.util.SystemUtil;

/**
 * ������ͨ�ŵ���
 * 
 * @author yinger
 * 
 */
public class GatewayCommunication extends AbstractCommunication {

	//��������ʼ��ʱִ�У��������ʵ����֮ǰ
	//�������У������һ�����ô��ˣ��´�����ʱ�ͻ������Ӳ���ȥ
//	static {
//		IP = SystemUtil.Gateway_IP;
//		PORT = SystemUtil.Gateway_Port;
//	}

	private static GatewayCommunication gatewayCommunication;

	private GatewayCommunication() throws Exception {
		super(SystemUtil.GATEWAY_IP, SystemUtil.GATEWAY_PORT);//���ַ�ʽ�ͽ�������������
		startConnection();
	}

	@Override
	public boolean closeConnection() throws Exception {
		super.closeConnection();
		gatewayCommunication = null;//����Ǳ���ģ����ڱ�֤�����getInstance
		return true;
	}

	public static GatewayCommunication getInstance() throws Exception {
		if (gatewayCommunication == null) {
			gatewayCommunication = new GatewayCommunication();
		}
		return gatewayCommunication;
	}

}
