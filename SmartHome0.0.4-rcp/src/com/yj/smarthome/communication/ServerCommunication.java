package com.yj.smarthome.communication;

import com.yj.smarthome.util.SystemUtil;

/**
 * 与远程服务器通信的类
 * 
 * @author yinger
 * 
 */
public class ServerCommunication extends AbstractCommunication {

	//这个在类初始化时执行，在类对象实例化之前
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
		serverCommunication = null;//这个是必须的！用于保证下面的getInstance
		return true;
	}

	public static ServerCommunication getInstance() throws Exception {
		if (serverCommunication == null) {
			serverCommunication = new ServerCommunication();
		}
		return serverCommunication;
	}

}
