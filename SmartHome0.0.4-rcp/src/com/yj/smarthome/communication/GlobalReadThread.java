package com.yj.smarthome.communication;

import java.io.InputStream;

import com.yj.smarthome.util.ProtocolUtil;

/**
 * ȫ�ֵ���Ϣ������
 * 
 * @author yinger
 * 
 */
public class GlobalReadThread extends Thread {

//	private Socket socket;
	private InputStream in;
	private byte[] response = new byte[256];//���ﶨΪ256���ֽڣ�Ϊ�˷�ֹ�������ͬʱ���͹�����

	public GlobalReadThread(InputStream in) throws Exception {
//		this.socket = socket;
//		in = socket.getInputStream();
		this.in = in;
		this.setName("���߳�");
	}

	@Override
	public void run() {
		while (in != null) {
			try {
				System.out.println("reading......");
				//�ڵ�¼ʱ�������û��л��˶��Ҫ���ӵķ�����������ÿ�ε���˵�¼������߳̾������ˣ�����ȡ�������µ�¼ʱ
				//������ӹر��ˣ��������ﻹ��֪����in����read�����ͻᱨ��
				//��Ϊֻ��in�ڶ���out��д�����Զ���in��out��˵�����ǲ�������ڶ��̵߳Ļ�����
//				if (in != null) {
//					synchronized (in) {
//						if (in!=null) {
				int length = in.read(response);//length��ָʵ�ʶ���ĳ���
				if (length == -1) {//�������Ҫ������������������Ͽ������ӵĻ����õ������ݳ�����-1����ʱ��Ҫ�˳���
					break;
				}
//						}
//					}
//				} else {
//					break;
//				}
				ProtocolUtil.processData(response);//����Э�鹤�����������ص���Ϣ

			} catch (Exception e) {
				e.printStackTrace();
				break;//�׳��쳣Ҳ��Ҫ�˳���
			}
		}
	}

}
