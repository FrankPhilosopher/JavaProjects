package com.yj.smarthome.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Vector;

public class Protrol {
	public static byte[] head = { 0x59, 0x4a }; //协议头
	public static byte logintype = (byte) 0xf0; //登录类型
	public static byte requesttype = (byte) 0xf1; //请求指令响应
	public static byte reqresponsetype = (byte) 0xf2; //请求状态返回
	public static byte end = 0x01;
	public static FeedBackInfo feedbackinfo;
	private Socket client;
	private OutputStream os;
	private InputStream is;
	PrintStream out;
	BufferedReader in;
	public static byte fail = (byte) 0xe0;
	public static byte success = (byte) 0xe1;
	public static byte userexists = (byte) 0xe2;
	public static byte error = (byte) 0xee;
	public static boolean iss = true;
	public static Vector<FeedBackInfo> save = new Vector<FeedBackInfo>();

	public Protrol(String a) {
		try {

			InetSocketAddress isa = new InetSocketAddress("192.168.1.145", 4900);
			client = new Socket();
			client.connect(isa);
			os = client.getOutputStream();
			is = client.getInputStream();

			//new Thread(new readThread()).start();
			//new Thread(new NewThread()).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Protrol() {

	}

	public static void main(String args[]) throws IOException {
		final Protrol a = new Protrol("");
		byte[] loginbag = a.getLoginBag("a0001", "123456");
		byte[] reqbag = a.getRequestBag("131586");
//		for (int i = 0; i < loginbag.length; i++)
//			System.out.println(loginbag[i]);
//		for (int i = 0; i < reqbag.length; i++)
//			System.out.println(reqbag[i]);

		//a.sendToServer("wbw");
		//ClientSocket.init().connectToServer("192.168.1.145", "4900");

		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader bin = new BufferedReader(in);
		a.StartServerListener();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (iss) {
					System.out.print("hre");
					try {
						for (int i = 0; i < save.size(); i++) {
							System.out.println("状态是什么：zhuangtaishi " + save.get(i).getFeedBackState());
						}

						Thread.sleep(1000);
						//iss=false;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}).start();

		while (true) {
			//a.sendToServer("wbw");
			String s = bin.readLine();
			a.sendToServer(s);
			a.sendToServer(loginbag);
		}

		//Socket socket = new Socket("192.168.1.145", 4800);
		//socket.getInputStream().read();
		//new Protrol();
	}

	class readThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					int a = is.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class NewThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					os.write("1".getBytes());
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}

	//在发消息之前，先要验证是否是连接状态,引用时验证，这里不验证
	public void sendToServer(String msg) {
		try {
			os.write(msg.getBytes());
			System.out.println("this send message::" + msg);
			System.out.println("send heart...");
		} catch (Exception e) {
			//Log.i("sendmessage","send message dissuccess ;");

		}
	}

	//在发消息之前，先要验证是否是连接状态,引用时验证，这里不验证
	public void sendToServer(byte[] mes) {
		try {
			os.write(mes);
			System.out.println("this send message::" + mes);
			System.out.println("send heart...");
		} catch (Exception e) {
			//Log.i("sendmessage","send message dissuccess ;");

		}

	}

	//验证是否是连接状态
	public boolean isConnect() {
		return client.isConnected();
	}

	private void StartServerListener() {
		// TODO Auto-generated method stub
		ServerListener a = new ServerListener();
		a.start();
		System.out.println("start listen");
	}

	public class ServerListener extends Thread {
		@Override
		public void run() {
			while (true) {
				try {

					byte[] buffer = new byte[1024];
					int l = is.read(buffer);
					for (int i = 0; i < l; i++)
						System.out.println("--" + buffer[i]);
					byte[] aa = new byte[l];
					System.arraycopy(buffer, 0, aa, 0, l);
					for (int i = 0; i < aa.length; i++)
						System.out.println("--|" + aa[i]);

					String result = new String(buffer, 0, l);
					System.out.println(result + "----shou");
					save = decodeBag(aa);
					iss = true;
					System.out.println("iss is shenme" + String.valueOf(iss));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	public int bytesToInt(byte[] intByte) {
		int fromByte = 0;
		for (int i = 0; i < 2; i++) {
			int n = (intByte[i] < 0 ? intByte[i] + 256 : (int) intByte[i]) << (8 * i);
			System.out.println(n);
			fromByte += n;
		}
		return fromByte;
	}

	public byte[] intToBytes(int num) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (num >>> (24 - i * 8));
		}
		return b;
	}

	/**
	 * 构造登录包
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public byte[] getLoginBag(String username, String password) {
		byte[] name = username.getBytes();
		byte[] pass = password.getBytes();

		byte[] logincontent = new byte[name.length + pass.length + 2];
		logincontent[0] = (byte) name.length;
		System.arraycopy(name, 0, logincontent, 1, name.length);

		logincontent[name.length + 1] = (byte) pass.length;
		System.arraycopy(pass, 0, logincontent, name.length + 2, pass.length);
		byte[] loginbag = new byte[5 + logincontent.length];
		System.arraycopy(head, 0, loginbag, 0, head.length);
		loginbag[head.length] = logintype;
		loginbag[head.length + 1] = (byte) logincontent.length;
		System.arraycopy(logincontent, 0, loginbag, head.length + 2, logincontent.length);
		loginbag[loginbag.length - 1] = end;
		return loginbag;
	}

	/**
	 * 构造数据请求包，传入一个数字字符串
	 * 
	 * @param data
	 * @return
	 */
	public byte[] getRequestBag(String data) {
		byte[] reqcontent = intToBytes(Integer.valueOf(data));
		byte[] reqbag = new byte[reqcontent.length + 5];
		System.arraycopy(head, 0, reqbag, 0, head.length);
		reqbag[head.length] = requesttype;
		reqbag[head.length + 1] = (byte) reqcontent.length;
		System.arraycopy(reqcontent, 0, reqbag, head.length + 2, reqcontent.length);
		reqbag[reqbag.length - 1] = end;
		return reqbag;
	}

	public Vector<FeedBackInfo> decodeBag(byte[] mes) {
		//System.out.println("---------decodeing---message is:"+message);
		//byte[] mes = message.getBytes();
		byte[][] everybag = resolveBag(mes, 0, mes.length);
		System.out.println("everybag de shu" + everybag.length);
		Vector<FeedBackInfo> feedbackinfolist = new Vector<FeedBackInfo>();
		for (int i = 0; i < everybag.length; i++) {
			FeedBackInfo fbinfo = isValidate(everybag[i]);
			feedbackinfolist.add(fbinfo);
			//save.add(fbinfo);
		}
		return feedbackinfolist;

	}

	class FeedBackInfo {
		int FeedBackType; //请求信息的种类0为登录，1为请求指令响应，2为请求状态反馈
		int FeedBackState; //0为失败，1为成功，2为故障,3为用户名不存在
		int FeedBackReqContent; //请求时发送的数据内容，为四个字节的int类型
		String FeedBackPostData; //一个字节的系数
		String FeedBackData; //返回的数据

		public FeedBackInfo() {

		}

		public int getFeedBackType() {
			return FeedBackType;
		}

		public void setFeedBackType(int feedBackType) {
			FeedBackType = feedBackType;
		}

		public int getFeedBackState() {
			return FeedBackState;
		}

		public void setFeedBackState(int feedBackState) {
			FeedBackState = feedBackState;
		}

		public int getFeedBackReqContent() {
			return FeedBackReqContent;
		}

		public void setFeedBackReqContent(int feedBackReqContent) {
			FeedBackReqContent = feedBackReqContent;
		}

		public String getFeedBackPostData() {
			return FeedBackPostData;
		}

		public void setFeedBackPostData(String feedBackPostData) {
			FeedBackPostData = feedBackPostData;
		}

		public String getFeedBackData() {
			return FeedBackData;
		}

		public void setFeedBackData(String feedBackData) {
			FeedBackData = feedBackData;
		}

	}

	public byte[][] resolveBag(byte[] data, int offset, int len) {
		System.out.println("YJProtocolService:------------------------------>resorve the protocol!");
		byte[][] command = null; //return result
		int size = 0;
		int start = offset;

		//calculate the number of bags!
		for (int i = offset; i < len; i++) {
			if (data[i] == end) {
				size++;
			}
		}
		System.out.println("size is " + size);
		//instance
		if (size != 0)
			command = new byte[size][];
		size = 0;

		for (int i = offset; i < len; i++) {
			if (data[i] == end) {
				//command[size++] = Arrays.copyOfRange(data, start, i);
				byte[] aa = new byte[i - start + 1];
				System.arraycopy(data, start, aa, 0, i - start + 1);
				System.out.println("wwqwq");
				command[size++] = aa;
				start = i + 1;
			}
		}
		return command;
	}

	/**
	 * validate the bag by protocol
	 */
	public FeedBackInfo isValidate(byte[] data) {
		// TODO Auto-generated method stub

		feedbackinfo = new FeedBackInfo();
		//feedbackinfo = new FeedBackInfo();
		if (data.length >= 4 && data[0] == head[0] && data[1] == head[1]) {
			if (data[2] == logintype) {
				feedbackinfo.setFeedBackType(0);
				if (data[4] == fail)
					feedbackinfo.setFeedBackState(0);
				if (data[4] == success)
					feedbackinfo.setFeedBackState(1);
				if (data[4] == userexists)
					feedbackinfo.setFeedBackState(3);
				if (data[4] == error)
					feedbackinfo.setFeedBackState(2);
				return feedbackinfo;
			} else if (data[2] == requesttype) {
				feedbackinfo.setFeedBackType(1);
				feedbackinfo.setFeedBackReqContent(Integer.valueOf(new String(data, 4, 4)));
				if (data[8] == fail)
					feedbackinfo.setFeedBackState(0);
				if (data[8] == success)
					feedbackinfo.setFeedBackState(1);
				if (data[8] == error)
					feedbackinfo.setFeedBackState(2);
				return feedbackinfo;
			} else if (data[2] == reqresponsetype) {
				feedbackinfo.setFeedBackType(2);
				feedbackinfo.setFeedBackReqContent(Integer.valueOf(new String(data, 4, 4)));
				if (data[8] == fail)
					feedbackinfo.setFeedBackState(0);
				if (data[8] == success)
					feedbackinfo.setFeedBackState(1);
				if (data[8] == error)
					feedbackinfo.setFeedBackState(2);
				feedbackinfo.setFeedBackPostData(new String(data, 9, 1));
				feedbackinfo.setFeedBackData(new String(data, 10, 2));
				return feedbackinfo;
			} else {
				return null;
			}
		} else {
			//错误包
			return null;
		}
	}

}
