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
 * ͨ�Ź���
 * 
 * @author yinger
 * 
 */
public class CommunicationUtil {

	private static CommunicationUtil instance;
	private UiUtil uiUtil;
	private SystemTool systemTool;
	private GlobalReadThread globalreadthread = null;

	// ��¼�ķ�ʽ
	public static int LOGIN_TYPE = 0;
	public static final int GATEWAY_LOGIN = 0;
	public static final int SERVER_LOGIN = 1;

	private StringBuffer rempteip = new StringBuffer(""); // ���ڻ�ȡcached ip
	private StringBuffer loginresult = new StringBuffer(""); // ���ڻ�ȡ��¼���
	private String loginok = "OK";// ��¼�ɹ����ַ���

	private String ip;// IP��ַ
	private int port;// ͨ�Ŷ˿ں�
	private Socket socket;// ���������ӵ�socket
	private OutputStream os;// ��Ӧsocket�������
	private InputStream is;// ��Ӧsocket��������
	
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
	 * ��鲢�ҹر�����
	 */
	public void closeSocket() {
		if(globalreadthread != null){
			globalreadthread.closeThread();
		}
		//ֹͣ������
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

	// ���ݴ����type���ͽ������ӣ���������ص����ӽ����ɹ��Ļ�����true�����򷵻�false��Ȼ����ǰ��ȥ��������ip
	// 1 --- login success
	// 0 ---- login fail
	// 2 ---- �˻��������
	// 3 ---- ���ز�����
	// 4 ---- �û����������
	public int buildCommunication(int type, String name, String pwd) throws Exception {
		LOGIN_TYPE = type;
		// System.out.println(type);//
		closeSocket();// ��������ǰ��Ҫ��鲢���������� ��ֹ������ӷ���
		switch (type) {// ��¼��ʽ
		case GATEWAY_LOGIN:
			ip = systemTool.getGateway_ip(); // ����ip
			port = systemTool.getGateway_port(); // ���ض˿�
			if (connect(ip, port)) {
				is = socket.getInputStream();
				os = socket.getOutputStream();
				// new Thread(new GlobalReadThread(is)).start(); // ����ȫ�ֶ�ȡ�����߳�
				//new GlobalReadThread(is).start();
				globalreadthread = new GlobalReadThread(is);
				globalreadthread.start();
				heartStart(); // ��ʼ����������
				return 1;
			} else {
				return 0;
			}
		case SERVER_LOGIN:
			//SystemTool.CURRENT_USER.setName(name);
			String cahcedname = systemTool.getCachedname();  //�õ�cachedname
			
			ip = systemTool.getCachedip(); // ��ȡ����ip
			port = systemTool.getGateway_port(); // ��ȡ���ض˿�
			if ( cahcedname.equals(name) && !("").equals(ip) &&connect(ip, port) ) {  //cachedname��name��Ȳſ����û���ip
				is = socket.getInputStream();
				os = socket.getOutputStream();
				rempteip.append(ip);
				// new Thread(new GlobalReadThread(is)).start(); // ����ȫ�ֶ�ȡ�����߳�
				//new GlobalReadThread(is).start();
				globalreadthread = new GlobalReadThread(is);
				globalreadthread.start();
				heartStart(); // ��ʼ����������
				return 1;
			} else {
				if(AppliactionUtil.DEBUG) System.out.println("ʹ�û���IP:" + ip + "����ʧ�ܣ�");//
				ip = systemTool.getServer_ip(); // Զ�̷�����IP
				port = systemTool.getServer_port(); // Զ�̷������˿�
				if(AppliactionUtil.DEBUG) System.err.println(ip + "***************************Զ��IP*****************************");
				InetSocketAddress isa = new InetSocketAddress(ip, port);
				Socket s = new Socket();
				s.connect(isa, 3000);// java.net.SocketTimeoutException: connect timed out
				rempteip = new StringBuffer("");
				// new Thread(new GetGatewayIpThread(name, pwd, s)).start();
				//�Ӹ����գ������������շ���
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
				if(AppliactionUtil.DEBUG) System.err.println(rempteip + "****************************Զ������IP**************************");
				
				if(rempteip.toString().equals("0")){
					//uiUtil.showSystemExceptionMessage("�û������������");
					return 4;
				}else if(rempteip.toString().equals("2")){
					if(AppliactionUtil.DEBUG) System.out.println("�˻�������");
					//uiUtil.showSystemExceptionMessage("�˻�������");
					
					return 2;
				}else if(rempteip.toString().equals("3")){
					//uiUtil.showSystemExceptionMessage("���ز�����");
					return 3;
				}else if(rempteip.toString().equals("5")){  //�����߳��д����쳣����GetGatewayIpThread(String, String, Socket)��
					//uiUtil.showSystemExceptionMessage("���ز�����");
					return 0;
				}
				
				
				
				if (connect(rempteip.toString(), systemTool.getGateway_port())) {
					if(AppliactionUtil.DEBUG) System.out.println("ʹ�õõ���Զ��IP:" + rempteip + "���ӳɹ���");//
					ip = rempteip.toString();
					port = systemTool.getGateway_port();
					is = socket.getInputStream();
					os = socket.getOutputStream();
					// new Thread(new GlobalReadThread(is)).start(); // ����ȫ�ֶ�ȡ�����߳�
					//new GlobalReadThread(is).start();
					globalreadthread = new GlobalReadThread(is);
					globalreadthread.start();
					heartStart(); // ��ʼ����������
					systemTool.setCachedip(rempteip.toString());//���˵�½�ɹ���IP��ֵ������IP
					
					//д��cachename
					systemTool.setCachedname(name);
					
					systemTool.newSystemXml();// ������IP��������
					return 1;
				} else {
					return 0;
				}
			}
		}
		return 0;//
	}

	/**
	 * ��ָ��ip��port��������
	 */
	private boolean connect(String ip, int port) {
		if(AppliactionUtil.DEBUG) System.out.println("IP=" + ip + "  PORT=" + port);
		InetSocketAddress isa = new InetSocketAddress(ip, port);
		socket = new Socket();
		try {
			socket.connect(isa, 3000);// �޶�����ʱ��
		} catch (IOException e) {
			closeSocket();
			return false;
		}
		return true;
	}

	/**
	 * ����������
	 */
	private void heartStart() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				//if (os != null) {
					try {
						//if(AppliactionUtil.DEBUG) System.out.println("������");
						
						os.write(ProtocolUtil.getInstance().packHeartPackage());
					} catch (Exception e) {
						closeSocket(); // �ͷ�socket��Դ
						reconnect();
						// timer.cancel();// �ͷŶ�ʱ����Դ ��ʱ������ֹͣ
					}
				}
			//}
		}, 10000, 10000);
	}

	/**
	 * ��������
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
			uiUtil.showSystemExceptionMessage("���ӷ������쳣����ȴ�Ƭ�̻���������������");
		}
	}

	/**
	 * ��������
	 */
	public void sendData(byte[] data) {
		if (os != null) {
			try {
				if(AppliactionUtil.DEBUG) System.out.println("�������ݣ�" + Arrays.toString(data));
				os.write(data);
				os.flush();
				if(AppliactionUtil.DEBUG) System.out.println("���ݷ��ͳɹ�");
			} catch (IOException e) {
				e.printStackTrace();// ���ﲻ�ܶ�����������
			}
		}
	}

	/**
	 * �����¼���
	 */
	public void processCheckLogin(String result) {
		synchronized (loginresult) {
			loginresult.append(result);
			loginresult.notifyAll(); // �����߳�
		}
	}

	/**
	 * �����ȡԶ������IP ��ַ���
	 */
	public void processGetGatewayIp(String result) {
		synchronized (rempteip) {
			
			rempteip.append(result);
			if(AppliactionUtil.DEBUG) System.out.println("le:"+rempteip.length());
			rempteip.notifyAll(); // �����߳�
		}
	}

	/**
	 * ����¼�Ƿ�ɹ�
	 */
	public boolean isLoginSuccess(String name, String pwd) {
		loginresult = new StringBuffer("");// ��ʼ����¼�������
		sendData(ProtocolUtil.getInstance().packCheckLogin(name, pwd));// ���͵�¼����
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
	 * ��ȡ����ip���߳�
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

		// �����ǽ���Զ�̷����������ӣ�Ȼ�������ݰ���Ȼ��ȴ���ֱ���������ݷ��أ�

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
				processGetGatewayIp("5");  //�쳣���������쳣ʱȥ��
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
