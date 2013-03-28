package com.yj.smarthome.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import com.yj.smarthome.util.SystemUtil;

/**
 * �����ͨ����<br/>
 * ������ǲ�����ʵ�����ģ����Բ���ֱ�ӵ��õ� <br/>
 * ����ͨ�ŵ�ͨ��ʵ����
 * 
 * @author yinger
 * 
 */
public abstract class AbstractCommunication implements ICommunication {

	protected static String IP;//����Ӧ�ö���Ĭ��ֵ
	protected static int PORT;
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private byte[] response = new byte[1024];//���ﶨΪ1024���ֽڣ�1024���㹻����

	//������췽���������б�����
	public AbstractCommunication(String ip, int port) {
		AbstractCommunication.IP = ip;
		AbstractCommunication.PORT = port;
	}

	@Override
	//ֻ�гɹ��˲Ż᷵��true��������׳��쳣��ǰ̨����
	public boolean startConnection() throws Exception {
		//���ip�Ͷ˿�
		System.out.println("IP:" + IP + "\tPort:" + PORT);
		InetSocketAddress isa = new InetSocketAddress(IP, PORT);
		socket = new Socket();
		socket.connect(isa, 2000);//�������Ҫ����һ�����ӽ���ʱ�����ƣ�������֤���治��ȴ�̫�ã����������
		in = socket.getInputStream();
		out = socket.getOutputStream();

		//����ȫ�ֵ����ڼ������������������ݵ��߳�
		new GlobalReadThread(in).start();

		//����ȫ�ֵ����ڷ������������߳�
		new GlobalWriteThread(out).start();

		return true;
	}

	@Override
	public boolean closeConnection() throws Exception {
		if (socket != null) {
			socket.close();//�ر�socket��Ȼ��ر��������
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
				out.write(data);//��һ�����ǣ��������
			}
		}

		System.out.println("send data finish");
		return true;
	}

	@Override
	public boolean sendData(byte[] data) throws Exception {
		//����������˶��˵Ļ������磺�����������ˣ�������Ҫ��������
		//������Щ��������ⲻ������ֻ��ͨ���������쳣�ſ���
//		System.out.println(socket==null);
//		System.out.println(socket.isClosed());
//		System.out.println(socket.isConnected());
//		System.out.println(in==null);
//		System.out.println(out==null);

		System.out.println(SystemUtil.communication == null);

		System.out.println("send data start");
		System.out.println(Arrays.toString(data));

		if (out != null) {//˫������ʽ����֤����write������
			System.out.println(out == null);
			synchronized (out) {
				System.out.println(out == null);
				if (out != null) {
					try {
						out.write(data);
					} catch (IOException e) {//�������Ϊnull�����Ƿ���ʧ���ˣ�ҲҪ��������
						e.printStackTrace();
						restartConnection();
						sendData(data);
					}
				}
			}
		} else {//�����Ϊnull����������
			restartConnection();
			sendData(data);
		}

		System.out.println("send data finish");
		return true;
	}

}
