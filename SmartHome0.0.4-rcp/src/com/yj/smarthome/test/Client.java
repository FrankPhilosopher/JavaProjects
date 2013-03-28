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
		//1. 用指定的IP和Port构造服务器的Socket对象。如果这里不给出server和port的话，
		//后面就需要显示的调用socket.connect()，并在该方法中传入这两个参数。
		Socket socket = new Socket(server, port);
		System.out.println("Connected to server... sending to string");
		//2. 基于该Socket对象上的数据通道，获取输入和输出流对象，便于之后的数据读取和写出。
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		//3. 写数据到服务器端。
		out.write(data);
		int totalBytesRcvd = 0;
		int bytesRcvd;
		//4. 从服务器端读取应答数据，直到本次命令的所有数据都返回。如果read返回-1，表示服务器关闭了该套接字。
		while (totalBytesRcvd < data.length) {
			if ((bytesRcvd = in.read(data, totalBytesRcvd, data.length - totalBytesRcvd)) == -1)
				throw new SocketException("Connection closed prematurely.");
			totalBytesRcvd += bytesRcvd;
		}
		System.out.println("Received: " + new String(data));
		//5. 关闭该Socket对象。
		socket.close();
	}
}