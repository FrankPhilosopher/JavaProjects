package com.yj.smarthome.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		String server = "192.168.1.108";
		byte[] data = "HelloWorld".getBytes();
		int port = 5050;
		//1. ��ָ����IP��Port�����������Socket����������ﲻ����server��port�Ļ���
		//�������Ҫ��ʾ�ĵ���socket.connect()�����ڸ÷����д���������������
		Socket socket = new Socket(server, port);
		System.out.println("Connected to server... sending to string");
		//2. ���ڸ�Socket�����ϵ�����ͨ������ȡ�������������󣬱���֮������ݶ�ȡ��д����
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		//3. д���ݵ��������ˡ�
		out.write(data);
		int totalBytesRcvd = 0;
		int bytesRcvd;
		//4. �ӷ������˶�ȡӦ�����ݣ�ֱ������������������ݶ����ء����read����-1����ʾ�������ر��˸��׽��֡�
		while (totalBytesRcvd < data.length) {
			if ((bytesRcvd = in.read(data, totalBytesRcvd, data.length - totalBytesRcvd)) == -1)
				throw new SocketException("Connection closed prematurely.");
			totalBytesRcvd += bytesRcvd;
		}
		System.out.println("Received: " + new String(data));
		//5. �رո�Socket����
		socket.close();
	}
}