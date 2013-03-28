package com.yj.smarthome.communication;

import java.io.InputStream;

import com.yj.smarthome.util.ProtocolUtil;

/**
 * 全局的信息接受类
 * 
 * @author yinger
 * 
 */
public class GlobalReadThread extends Thread {

//	private Socket socket;
	private InputStream in;
	private byte[] response = new byte[256];//这里定为256个字节，为了防止多个反馈同时发送过来！

	public GlobalReadThread(InputStream in) throws Exception {
//		this.socket = socket;
//		in = socket.getInputStream();
		this.in = in;
		this.setName("读线程");
	}

	@Override
	public void run() {
		while (in != null) {
			try {
				System.out.println("reading......");
				//在登录时，可能用户切换了多次要连接的服务器，但是每次点击了登录，这个线程就启动了，但是取消后重新登录时
				//这个连接关闭了，但是这里还不知道，in调用read方法就会报错
				//因为只有in在读，out在写，所以对于in和out老说，它们并不会出在多线程的环境中
//				if (in != null) {
//					synchronized (in) {
//						if (in!=null) {
				int length = in.read(response);//length是指实际读入的长度
				if (length == -1) {//这里很重要，如果服务器端主动断开了连接的话，得到的数据长度是-1！此时就要退出！
					break;
				}
//						}
//					}
//				} else {
//					break;
//				}
				ProtocolUtil.processData(response);//交给协议工具类来处理返回的信息

			} catch (Exception e) {
				e.printStackTrace();
				break;//抛出异常也是要退出的
			}
		}
	}

}
