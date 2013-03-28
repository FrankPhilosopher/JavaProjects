package communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import tool.SystemTool;
import util.AppliactionUtil;
import util.ProtocolUtil;
import util.UiUtil;

/**
 * 通信工具
 * 
 * @author yinger
 * 
 */
public class CommunicationUtil {

	private static CommunicationUtil instance;
	private UiUtil uiUtil;
	private SystemTool systemTool;
	private GlobalReadThread globalreadthread = null;

	// 登录的方式
	public static int LOGIN_TYPE = 0;
	public static final int GATEWAY_LOGIN = 0;
	public static final int SERVER_LOGIN = 1;

	private StringBuffer rempteip = new StringBuffer(""); // 用于获取cached ip
	private StringBuffer loginresult = new StringBuffer(""); // 用于获取登录结果
	private String loginok = "OK";// 登录成功的字符串

	private String ip;// IP地址
	private int port;// 通信端口号
	private Socket socket;// 建立的连接的socket
	private OutputStream os;// 对应socket的输出流
	private InputStream is;// 对应socket的输入流
	
	private  Timer timer;

	private CommunicationUtil() {
		systemTool = SystemTool.getInstance();
		uiUtil = UiUtil.getInstance();
	}

	public static CommunicationUtil getInstance() {
		if (instance == null)
			instance = new CommunicationUtil();
		return instance;
	}

	/**
	 * 检查并且关闭连接
	 */
	public void closeSocket() {
		if(globalreadthread != null){
			globalreadthread.closeThread();
		}
		//停止心跳包
		if(SystemTool.CURRENT_USER == null && timer != null){
			timer.cancel();
		}
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
			}
			//os = null;
		}
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
			}
			//is = null;
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
			}
			//socket = null;
		}
		
		
	}

	// 根据传入的type类型建立连接，如果与网关的连接建立成功的话返回true，否则返回false，然后由前面去发送请求ip
	// 1 --- login success
	// 0 ---- login fail
	// 2 ---- 账户不存大在
	// 3 ---- 网关不在线
	// 4 ---- 用户名密码错误
	public int buildCommunication(int type, String name, String pwd) throws Exception {
		LOGIN_TYPE = type;
		// System.out.println(type);//
		closeSocket();// 建立连接前需要检查并且重置连接 防止多个连接发生
		switch (type) {// 登录方式
		case GATEWAY_LOGIN:
			ip = systemTool.getGateway_ip(); // 网关ip
			port = systemTool.getGateway_port(); // 网关端口
			if (connect(ip, port)) {
				is = socket.getInputStream();
				os = socket.getOutputStream();
				// new Thread(new GlobalReadThread(is)).start(); // 启动全局读取数据线程
				//new GlobalReadThread(is).start();
				globalreadthread = new GlobalReadThread(is);
				globalreadthread.start();
				heartStart(); // 开始发送心跳包
				return 1;
			} else {
				return 0;
			}
		case SERVER_LOGIN:
			//SystemTool.CURRENT_USER.setName(name);
			String cahcedname = systemTool.getCachedname();  //得到cachedname
			
			ip = systemTool.getCachedip(); // 获取缓存ip
			port = systemTool.getGateway_port(); // 获取网关端口
			if ( cahcedname.equals(name) && !("").equals(ip) &&connect(ip, port) ) {  //cachedname和name相等才可以用缓存ip
				is = socket.getInputStream();
				os = socket.getOutputStream();
				rempteip.append(ip);
				// new Thread(new GlobalReadThread(is)).start(); // 启动全局读取数据线程
				//new GlobalReadThread(is).start();
				globalreadthread = new GlobalReadThread(is);
				globalreadthread.start();
				heartStart(); // 开始发送心跳包
				return 1;
			} else {
				if(AppliactionUtil.DEBUG) System.out.println("使用缓存IP:" + ip + "连接失败！");//
				ip = systemTool.getServer_ip(); // 远程服务器IP
				port = systemTool.getServer_port(); // 远程服务器端口
				if(AppliactionUtil.DEBUG) System.err.println(ip + "***************************远程IP*****************************");
				InetSocketAddress isa = new InetSocketAddress(ip, port);
				Socket s = new Socket();
				s.connect(isa, 3000);// java.net.SocketTimeoutException: connect timed out
				rempteip = new StringBuffer("");
				// new Thread(new GetGatewayIpThread(name, pwd, s)).start();
				//加个保险，如果超过五秒刚返回
				long time0 = System.currentTimeMillis();
				new GetGatewayIpThread(name, pwd, s).start();
				synchronized (rempteip) {
					while (rempteip.toString().length() == 0) {
//						if(System.currentTimeMillis() - time0 > 5000)
//							return 0;
						if(AppliactionUtil.DEBUG) System.out.println("wait");
						rempteip.wait();
					}
				}
				if(AppliactionUtil.DEBUG) System.err.println(rempteip + "****************************远程网关IP**************************");
				
				if(rempteip.toString().equals("0")){
					//uiUtil.showSystemExceptionMessage("用户名或密码错误");
					return 4;
				}else if(rempteip.toString().equals("2")){
					if(AppliactionUtil.DEBUG) System.out.println("账户不存在");
					//uiUtil.showSystemExceptionMessage("账户不存在");
					
					return 2;
				}else if(rempteip.toString().equals("3")){
					//uiUtil.showSystemExceptionMessage("网关不在线");
					return 3;
				}else if(rempteip.toString().equals("5")){  //在子线程中处理异常，在GetGatewayIpThread(String, String, Socket)中
					//uiUtil.showSystemExceptionMessage("网关不在线");
					return 0;
				}
				
				
				
				if (connect(rempteip.toString(), systemTool.getGateway_port())) {
					if(AppliactionUtil.DEBUG) System.out.println("使用得到的远程IP:" + rempteip + "连接成功！");//
					ip = rempteip.toString();
					port = systemTool.getGateway_port();
					is = socket.getInputStream();
					os = socket.getOutputStream();
					// new Thread(new GlobalReadThread(is)).start(); // 启动全局读取数据线程
					//new GlobalReadThread(is).start();
					globalreadthread = new GlobalReadThread(is);
					globalreadthread.start();
					heartStart(); // 开始发送心跳包
					systemTool.setCachedip(rempteip.toString());//将此登陆成功的IP赋值给缓存IP
					
					//写入cachename
					systemTool.setCachedname(name);
					
					systemTool.newSystemXml();// 将缓存IP保存下来
					return 1;
				} else {
					return 0;
				}
			}
		}
		return 0;//
	}

	/**
	 * 与指定ip和port建立连接
	 */
	private boolean connect(String ip, int port) {
		if(AppliactionUtil.DEBUG) System.out.println("IP=" + ip + "  PORT=" + port);
		InetSocketAddress isa = new InetSocketAddress(ip, port);
		socket = new Socket();
		try {
			socket.connect(isa, 3000);// 限定连接时限
		} catch (IOException e) {
			closeSocket();
			return false;
		}
		return true;
	}

	/**
	 * 发送心跳包
	 */
	private void heartStart() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				//if (os != null) {
					try {
						//if(AppliactionUtil.DEBUG) System.out.println("心跳包");
						
						os.write(ProtocolUtil.getInstance().packHeartPackage());
					} catch (Exception e) {
						closeSocket(); // 释放socket资源
						reconnect();
						// timer.cancel();// 释放定时器资源 定时器不会停止
					}
				}
			//}
		}, 10000, 10000);
	}

	/**
	 * 重新连接
	 */
	public void reconnect() {
		connect(ip, port);
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			// new GlobalReadThread(is).start();
			globalreadthread = new GlobalReadThread(is);
			globalreadthread.start();
		} catch (Exception e) {
			// e.printStackTrace();
			uiUtil.showSystemExceptionMessage("连接发生了异常，请等待片刻或者您重新启动！");
		}
	}

	/**
	 * 发送数据
	 */
	public void sendData(byte[] data) {
		if (os != null) {
			try {
				if(AppliactionUtil.DEBUG) System.out.println("发送数据：" + Arrays.toString(data));
				os.write(data);
				os.flush();
				if(AppliactionUtil.DEBUG) System.out.println("数据发送成功");
			} catch (IOException e) {
				e.printStackTrace();// 这里不能都是重新连接
			}
		}
	}

	/**
	 * 处理登录结果
	 */
	public void processCheckLogin(String result) {
		synchronized (loginresult) {
			loginresult.append(result);
			loginresult.notifyAll(); // 唤醒线程
		}
	}

	/**
	 * 处理获取远程网关IP 地址结果
	 */
	public void processGetGatewayIp(String result) {
		synchronized (rempteip) {
			
			rempteip.append(result);
			if(AppliactionUtil.DEBUG) System.out.println("le:"+rempteip.length());
			rempteip.notifyAll(); // 唤醒线程
		}
	}

	/**
	 * 检测登录是否成功
	 */
	public boolean isLoginSuccess(String name, String pwd) {
		loginresult = new StringBuffer("");// 初始化登录反馈结果
		sendData(ProtocolUtil.getInstance().packCheckLogin(name, pwd));// 发送登录请求
		synchronized (loginresult) {
			while (loginresult.toString().length() == 0)
				try {
					loginresult.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		if (loginresult == null || "".equals(loginresult)) {
			return false;
		}
		return loginok.equals(loginresult.toString());
	}

	/**
	 * 获取网关ip的线程
	 */
	class GetGatewayIpThread extends Thread {
		private String name;
		private String pwd;
		private Socket s;

		public GetGatewayIpThread(String name, String pwd, Socket s) {
			this.name = name;
			this.pwd = pwd;
			this.s = s;
		}

		// 首先是建立远程服务器的连接，然后发送数据包，然后等待着直到反馈数据返回！

		public void run() {
			byte [] buffer  = null;
			int len = 0;
			try {
				s.getOutputStream().write(ProtocolUtil.getInstance().packGetGatewayIp(name, pwd));
				buffer = new byte[256];
				is = s.getInputStream();
				len = is.read(buffer);
				ProtocolUtil.getInstance().processResponseData(buffer, 0, len);
			} catch (Exception e) {
				e.printStackTrace();
				processGetGatewayIp("5");  //异常处理，发生异常时去这
			}finally{
				if (len == -1) {
					closeSocket();
				}				
			}
		}
	}

	public StringBuffer getRempteip() {
		return rempteip;
	}

	public void setRempteip(StringBuffer rempteip) {
		this.rempteip = rempteip;
	}

	public StringBuffer getLoginresult() {
		return loginresult;
	}

	public void setLoginresult(StringBuffer loginresult) {
		this.loginresult = loginresult;
	}
}
