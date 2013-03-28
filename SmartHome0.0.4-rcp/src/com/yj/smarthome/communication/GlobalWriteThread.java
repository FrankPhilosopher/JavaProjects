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
		this.setName("д�߳�");
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
				System.out.println("���������﷢���˴���");
				e.printStackTrace();
				break;//�׳��쳣Ҳ��Ҫ�˳���
				//��������������׳����쳣��Ҫ��������
				//��һ�����ﱨ���쳣��Ҫ�������ӣ���¼ʱ����һ�����ӣ��û�����ֻ���л������ӵķ�����
				//Ҳ���Կ����ڵ�¼����֮���������ſ�ʼ��������
			}
		}
	}

}
