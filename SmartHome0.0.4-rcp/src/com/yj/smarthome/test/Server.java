package com.yj.smarthome.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Server {
	private static final int BUFSIZE = 32;

	public static void main(String[] args) throws UnknownHostException, IOException {
		int port = 5050;
		InetAddress saddr = InetAddress.getByName("192.168.0.105");
		//1. ͨ���ƶ�IP��Port������������ļ���Socket������Ҫ˵�����ǣ���ServerSocket�����
		//��Socket�����Ѿ���Ը�IP��Port����bind��listen״̬�ˡ�
		ServerSocket ssock = new ServerSocket(port, 20, saddr);
		int recvMsgSize;
		byte[] receiveBuf = new byte[BUFSIZE];
		while (true) {
			//2. accept()������������ȴ��ͻ��˵����ӡ�ֱ�����µĿͻ������ӽ�����ʱ��
			//�ú������������أ����ص�Socket�������֮��Ϳͻ��˽���ͨ�ŵ�Socket����
			Socket csock = ssock.accept();
			//�������ͨ��accept���ص�Socket�����ȡ��ǰ�������ӵĿͻ��˵�IP��ַ��
			SocketAddress caddr = csock.getRemoteSocketAddress();
			System.out.println("Handling client at " + caddr);
			//3. ����Ϳͻ��˵Ĵ���һ�£�ͨ��Socket���ص������������������������ݴ��䡣
			InputStream in = csock.getInputStream();
			OutputStream out = csock.getOutputStream();
			//4. ����Ϳͻ��˵Ķ�ȡ���ƴ�����ͬ�����read����-1����ʾ�ͻ��������ر��ˡ�
			while ((recvMsgSize = in.read(receiveBuf)) != -1) {
				out.write(receiveBuf, 0, recvMsgSize);
			}
			//������Ϻ�����������رտͻ���Socket����
			csock.close();
		}
	}
}