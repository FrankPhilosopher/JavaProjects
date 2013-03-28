package com.yj.smarthome.communication;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import com.yj.smarthome.util.ProtocolUtil;

public class GlobalWriteThread extends Thread {

//	private Socket socket;
	private OutputStream out;
	private byte[] data;

	public GlobalWriteThread(OutputStream out) throws Exception {
//		this.socket = socket;
//		out = socket.getOutputStream();
		this.out = out;
		data = ProtocolUtil.packHeartPackage();
		this.setName("写线程");
	}

	public void run() {
		while (out != null) {
			try {
				System.out.println("writing......");
				synchronized (out) {
					if (out != null) {
						try {
							out.write(data);
						} catch (IOException e) {
							e.printStackTrace();
							break;
//							SystemUtil.communication.restartConnection();
//							sendData();
						}
					}
				}
//				sendData();
//				out.write(data);
				System.out.println(Arrays.toString(data));
				Thread.sleep(2000);
			} catch (Exception e) {
				System.out.println("心跳包这里发生了错误");
				e.printStackTrace();
				break;//抛出异常也是要退出的
				//心跳包这里如果抛出了异常就要重新连接
				//不一定这里报了异常就要重新连接，登录时就是一个例子，用户可能只是切换了连接的服务器
				//也可以考虑在登录进入之后，心跳包才开始发送数据
			}
		}
	}

}
