package communication;

import java.io.IOException;
import java.io.InputStream;

import tool.SystemTool;
import util.AppliactionUtil;
import util.ProtocolUtil;

/**
 * ȫ�ֵ���Ϣ������
 * 
 * @author yinger
 * 
 */
public class GlobalReadThread extends Thread {

	private InputStream in;
	private byte[] response = new byte[1024];// ���ﶨΪ1024���ֽڣ�Ϊ�˷�ֹ�������ͬʱ���͹�����
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
					System.out.println("��ʼ�������ݣ�");
					System.out.println(Thread.currentThread().getName());
				}
				int length = in.read(response);// length��ָʵ�ʶ���ĳ���
				if (length == -1) {// �������Ҫ������������������Ͽ������ӵĻ����õ������ݳ�����-1����ʱ��Ҫ�˳���
					break;
				}
				protocolUtil.processResponseData(response, 0, length);// ����Э�鹤�����������ص���Ϣ
			} catch (Exception e) {
				if(AppliactionUtil.DEBUG) System.err.println("��Ϣ�����߳�ֹͣ!");
				//
				if(SystemTool.CURRENT_USER != null)
					CommunicationUtil.getInstance().reconnect();
				e.printStackTrace();
				break;// �׳��쳣Ҳ��Ҫ�˳���
			}
		}
	}
	
	public void closeThread(){
		//û��ֹͣ
		flag = false;
		if(thread != null)
			thread.interrupt();
	}

}
