package communication;

import java.io.IOException;
import java.io.InputStream;

import tool.SystemTool;
import util.AppliactionUtil;
import util.ProtocolUtil;

/**
 * 全局的信息接收类
 * 
 * @author yinger
 * 
 */
public class GlobalReadThread extends Thread {

	private InputStream in;
	private byte[] response = new byte[1024];// 这里定为1024个字节，为了防止多个反馈同时发送过来！
	private ProtocolUtil protocolUtil;
	private boolean flag = true;
	private Thread thread = null;

	public GlobalReadThread(InputStream in) throws Exception {
		this.in = in;
		this.protocolUtil = ProtocolUtil.getInstance();
		//thread = Thread.currentThread();
		thread = this;
	}

	@Override
	public void run() {
		
		while (in != null && flag) {
			try {
				
				if(AppliactionUtil.DEBUG){
					System.out.println("开始接收数据！");
					System.out.println(Thread.currentThread().getName());
				}
				int length = in.read(response);// length是指实际读入的长度
				if (length == -1) {// 这里很重要，如果服务器端主动断开了连接的话，得到的数据长度是-1！此时就要退出！
					break;
				}
				protocolUtil.processResponseData(response, 0, length);// 交给协议工具类来处理返回的信息
			} catch (Exception e) {
				if(AppliactionUtil.DEBUG) System.err.println("消息接收线程停止!");
				//
				if(SystemTool.CURRENT_USER != null)
					CommunicationUtil.getInstance().reconnect();
				e.printStackTrace();
				break;// 抛出异常也是要退出的
			}
		}
	}
	
	public void closeThread(){
		//没能停止
		flag = false;
		if(thread != null)
			thread.interrupt();
	}

}
