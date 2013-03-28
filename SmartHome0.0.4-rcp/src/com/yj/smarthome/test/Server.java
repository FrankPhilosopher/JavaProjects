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
		//1. 通过制定IP和Port，构造服务器的监听Socket对象。需要说明的是，在ServerSocket构造后
		//该Socket对象即已经针对该IP和Port处于bind和listen状态了。
		ServerSocket ssock = new ServerSocket(port, 20, saddr);
		int recvMsgSize;
		byte[] receiveBuf = new byte[BUFSIZE];
		while (true) {
			//2. accept()将阻塞在这里等待客户端的连接。直到有新的客户端连接进来的时候
			//该函数才正常返回，返回的Socket对象就是之后和客户端进行通信的Socket对象。
			Socket csock = ssock.accept();
			//这里可以通过accept返回的Socket对象获取当前正在连接的客户端的IP地址。
			SocketAddress caddr = csock.getRemoteSocketAddress();
			System.out.println("Handling client at " + caddr);
			//3. 这里和客户端的代码一致，通过Socket返回的输入和输出流对象来进行数据传输。
			InputStream in = csock.getInputStream();
			OutputStream out = csock.getOutputStream();
			//4. 这里和客户端的读取机制大体相同，如果read返回-1，表示客户端主动关闭了。
			while ((recvMsgSize = in.read(receiveBuf)) != -1) {
				out.write(receiveBuf, 0, recvMsgSize);
			}
			//发送完毕后服务器主动关闭客户端Socket对象。
			csock.close();
		}
	}
}