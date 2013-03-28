package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tool.SystemTool;

import communication.CommunicationUtil;

/**
 * ����HTTP���������ļ��Ĺ�����
 * 
 * @author yinger
 * 
 */
public class HttpUtil {

	private static HttpUtil instance;
	private StringBuffer downloadUrl;

	private StringBuffer downloadResult = new StringBuffer("");// ���ؽ��
	private StringBuffer updateResult = new StringBuffer("");// ���½��

	private HttpUtil() {// ����Ĳ����ǹ����ģ����ڹ��캯���г�ʼ��
	}

	public static HttpUtil getInstance() {
		if (instance == null)
			instance = new HttpUtil();
		return instance;
	}

	/**
	 * �����ļ��б�
	 */
	public void downloadFiles() {
		// if (SystemTool.CURRENT_USER.isAutodownable()) {// ���û��Ƿ��Զ�����---����������ܷ��������Ϊ�ֶ�����ʱ����������޹�
		downloadResult = new StringBuffer("");
		List<String> files = new ArrayList<String>();// Ҫ���ص��ļ��б�
		files.add(FileUtil.VERSION_XML);
		files.add(FileUtil.STATE_XML);
		files.add(FileUtil.LIGHT_XML);
		files.add(FileUtil.DOOR_XML);
		files.add(FileUtil.SECURITY_XML);
		files.add(FileUtil.APPLIANCE_XML);
		files.add(FileUtil.SENSOR_XML);
		if(AppliactionUtil.DEBUG) System.out.println("���ؿ�ʼ������������");
		for (int i = 0; i < files.size(); i++) {
			try {
				downloadFile(FileUtil.DOWN_DIRECTORY + files.get(i), files.get(i));
			} catch (Exception e) {
				processDownloadResult(e.getMessage());// ĳ���ļ�����ʧ��
				if(AppliactionUtil.DEBUG) System.out.println(files.get(i) +" ����ʧ��");
				//if(i == files.size() - 1)  //����ԭ����ֻҪһ������ʧ�ܾͷ��أ��ָ�Ϊ���һ������ʧ�ܷ���
					return;
			}
		}
		// ȫ���������
		if(AppliactionUtil.DEBUG) System.out.println("������ɡ�����������");
		processDownloadResult("OK");
	}

	/**
	 * �������ؽ��
	 */
	protected void processDownloadResult(String result) {
		synchronized (downloadResult) {
			downloadResult.append(result);
			downloadResult.notifyAll(); // �����߳�
		}
	}

	/**
	 * ��������ļ����ؽ��
	 */
	protected void processUpdateResult(String result) {
		synchronized (updateResult) {
			updateResult.append(result);
			updateResult.notifyAll(); // �����߳�
		}
	}

	/**
	 * ���ص����ļ���ǰ�����ļ������·����������Ҫ���ص��ļ�����----�ļ����Ǵ����������صģ�
	 */
	private void downloadFile(String saveFilePath, String filename) throws Exception {
		File file = new File(saveFilePath);
		OutputStream outputStream = new FileOutputStream(file);
		downloadUrl = new StringBuffer();
		downloadUrl.append("http://");// �ļ����Ǵ����������صģ������������IP��ʱ�Ǳ��صģ���ʱ��Զ�̵�
		if (CommunicationUtil.LOGIN_TYPE == CommunicationUtil.GATEWAY_LOGIN) {
			downloadUrl.append(SystemTool.getInstance().getGateway_ip() + ":"
					+ SystemTool.getInstance().getGateway_downport() + "/load?filename=");
		} else {
			//��������ض˿����������ص����ض˿ڣ���Ϊ�������ɴ���������
			downloadUrl.append(SystemTool.getInstance().getCachedip() + ":"
					+ SystemTool.getInstance().getGateway_downport() + "/load?filename=");
		}
		downloadUrl.append(filename);
		if(AppliactionUtil.DEBUG) System.out.println("���ص�ַ��" + downloadUrl.toString());// TODO�����ص�ַ
		try {
		URL url = new URL(downloadUrl.toString());// ���ص����ӵ�ַ�ǹ̶���
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// ����http����
		connection.connect();// java.net.ConnectException: Connection refused: connect
		connection.setConnectTimeout(10000);
		connection.setReadTimeout(3000);
			InputStream inputStream = connection.getInputStream();
			byte[] buffer = new byte[1024 * 4];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {// ѭ����ȡ����
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			if(AppliactionUtil.DEBUG) System.out.println("���سɹ���" + saveFilePath);// TODO:�������������ʾ��
		} catch (Exception e) {
			throw new Exception("�ļ� " + filename + " ����ʧ�ܣ�");
		}
	}

	/**
	 * ����pc������汾�ļ�
	 */
	public void downloadPcVersionFile() throws Exception {
		updateResult = new StringBuffer("");
		String filename = FileUtil.PCVERSION_XML;
		File file = new File(FileUtil.DOWN_DIRECTORY + filename);
		OutputStream outputStream = new FileOutputStream(file);
		StringBuffer downloadUrl = new StringBuffer();
		downloadUrl.append("http://");//
		downloadUrl.append(SystemTool.getInstance().getServer_ip() + ":"
				+ SystemTool.getInstance().getServer_downport() + "/YJdown/");// http://www.iever.cn:80/YJdown/pcversion.xml
		downloadUrl.append(filename);
		if(AppliactionUtil.DEBUG) System.out.println("���ص�ַ��" + downloadUrl.toString());// TODO�����ص�ַ
		URL url = new URL(downloadUrl.toString());// ���ص����ӵ�ַ�ǹ̶���
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// ����http����
		connection.connect();// java.net.ConnectException: Connection refused: connect
		try {
			InputStream inputStream = connection.getInputStream();
			byte[] buffer = new byte[1024 * 4];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {// ѭ����ȡ����
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			if(AppliactionUtil.DEBUG) System.out.println("���سɹ���" + filename);// TODO:�������������ʾ��
			processUpdateResult("OK");
		} catch (Exception e) {
			throw new Exception("�ļ� " + filename + " ����ʧ�ܣ�");
		}
	}

	public StringBuffer getUpdateResult() {
		return updateResult;
	}

	public void setUpdateResult(StringBuffer updateResult) {
		this.updateResult = updateResult;
	}

	public StringBuffer getDownloadResult() {
		return downloadResult;
	}

	public void setDownloadResult(StringBuffer downloadResult) {
		this.downloadResult = downloadResult;
	}

	// ����������ص��ļ��Ƿ�����Ŀ�е�down�ļ�����
	public static void main(String[] args) {
		try {
			HttpUtil.getInstance().downloadPcVersionFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
