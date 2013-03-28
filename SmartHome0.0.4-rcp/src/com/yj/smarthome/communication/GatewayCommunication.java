package com.yj.smarthome.communication;

import com.yj.smarthome.util.SystemUtil;

/**
 * 与网关通信的类
 * 
 * @author yinger
 * 
 */
public class GatewayCommunication extends AbstractCommunication {

	//这个在类初始化时执行，在类对象实例化之前
	//这样不行，如果第一次设置错了，下次连接时就还是连接不上去
//	static {
//		IP = SystemUtil.Gateway_IP;
//		PORT = SystemUtil.Gateway_Port;
//	}

	private static GatewayCommunication gatewayCommunication;

	private GatewayCommunication() throws Exception {
		super(SystemUtil.GATEWAY_IP, SystemUtil.GATEWAY_PORT);//这种方式就解决了上面的问题
		startConnection();
	}

	@Override
	public boolean closeConnection() throws Exception {
		super.closeConnection();
		gatewayCommunication = null;//这个是必须的！用于保证下面的getInstance
		return true;
	}

	public static GatewayCommunication getInstance() throws Exception {
		if (gatewayCommunication == null) {
			gatewayCommunication = new GatewayCommunication();
		}
		return gatewayCommunication;
	}

}
