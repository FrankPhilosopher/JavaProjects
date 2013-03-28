package com.yj.smarthome.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import com.yj.smarthome.util.SystemUtil;

/**
 * 抽象的通信类<br/>
 * 这个类是不可以实例化的，所以不能直接调用的 <br/>
 * 它是通信的通用实现类
 * 
 * @author yinger
 * 
 */
public abstract class AbstractCommunication implements ICommunication {

	protected static String IP;//两者应该都有默认值
	protected static int PORT;
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private byte[] response = new byte[1024];//这里定为1024个字节，1024就足够大了

	//这个构造方法在子类中被调用
	public AbstractCommunication(String ip, int port) {
		AbstractCommunication.IP = ip;
		AbstractCommunication.PORT = port;
	}

	@Override
	//只有成功了才会返回true，否则会抛出异常由前台捕获
	public boolean startConnection() throws Exception {
		//输出ip和端口
		System.out.println("IP:" + IP + "\tPort:" + PORT);
		InetSocketAddress isa = new InetSocketAddress(IP, PORT);
		socket = new Socket();
		socket.connect(isa, 2000);//这里很重要，给一个连接建立时间限制，这样保证界面不会等待太久，不会假死！
		in = socket.getInputStream();
		out = socket.getOutputStream();

		//启动全局的用于监听服务器发来的数据的线程
		new GlobalReadThread(in).start();

		//启动全局的用于发送心跳包的线程
		new GlobalWriteThread(out).start();

		return true;
	}

	@Override
	public boolean closeConnection() throws Exception {
		if (socket != null) {
			socket.close();//关闭socket，然后关闭输入输出
			out = null;
			in = null;
		}
		return true;
	}

	@Override
	public boolean restartConnection() throws Exception {
		closeConnection();
		startConnection();
		return true;
	}

	@Override
	public boolean checkLogin(byte[] data) throws Exception {
		System.out.println("send data start");
		System.out.println(Arrays.toString(data));

		if (out != null) {
			System.out.println(out == null);
			synchronized (out) {
				System.out.println(out == null);
				out.write(data);//不一样的是，如果这里
			}
		}

		System.out.println("send data finish");
		return true;
	}

	@Override
	public boolean sendData(byte[] data) throws Exception {
		//如果服务器端断了的话（例如：服务器重启了），这里要重新连接
		//下面这些方法都检测不出来！只有通过报出了异常才可以
//		System.out.println(socket==null);
//		System.out.println(socket.isClosed());
//		System.out.println(socket.isConnected());
//		System.out.println(in==null);
//		System.out.println(out==null);

		System.out.println(SystemUtil.communication == null);

		System.out.println("send data start");
		System.out.println(Arrays.toString(data));

		if (out != null) {//双检锁形式，保证不会write出问题
			System.out.println(out == null);
			synchronized (out) {
				System.out.println(out == null);
				if (out != null) {
					try {
						out.write(data);
					} catch (IOException e) {//输出流不为null，但是发送失败了，也要重新连接
						e.printStackTrace();
						restartConnection();
						sendData(data);
					}
				}
			}
		} else {//输出流为null，重新连接
			restartConnection();
			sendData(data);
		}

		System.out.println("send data finish");
		return true;
	}

}
