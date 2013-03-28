package com.yj.smarthome.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * ����HTTP���������ļ��Ĺ�����
 * 
 * @author yinger
 * 
 */
public class HttpUtil {

	//http://host:port/load?filename=����Ҫ���ļ�
	//����һ���ļ��б�
	public static boolean downloaddFileList(List<String> files) throws Exception {
		for (int i = 0; i < files.size(); i++) {
			downloadFile(FileUtil.DOWN_DIRECTORY + files.get(i), files.get(i));
		}
		return true;
	}

	//����ĳ���豸���͵�xml�ļ�
	public static File downloadDeviceTypeXmlFile(int deviceTypeId) throws Exception {
		return downloadFile(FileUtil.DOWN_DIRECTORY + FileUtil.DEVICEID_XML.get(deviceTypeId), FileUtil.DEVICEID_XML.get(deviceTypeId));
	}

	//�����µ�version.xml�ļ�
	public static File downloadVersionXmlFile() throws Exception {
		return downloadFile(FileUtil.DOWN_DIRECTORY + FileUtil.VERSION_XML, FileUtil.VERSION_XML);
	}

	//���ص����ļ�
	public static File downloadFile(String saveFilePath, String filename) throws Exception {
		File file = new File(saveFilePath);
//		if (file!=null && file.exists()) {//����ļ����ڣ���ô�õ����ļ��ͻ���д����ԭ��������
//			
//		}
		//�ļ������
		OutputStream outputStream = new FileOutputStream(file);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("http://");
		stringBuffer.append(SystemUtil.SERVER_IP + ":" + SystemUtil.SERVER_PORT + "/load?filename=");
		stringBuffer.append(filename);
		URL url = new URL(stringBuffer.toString());//���ص����ӵ�ַ�ǹ̶���
		//����http����
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		//�����ж�����ļ����ȣ�
		int contentLength = connection.getContentLength();
		//����ļ������ھ��׳��쳣
//		if (contentLength < 0) {//-1 if the content length is not known
//			throw new Exception("�ļ�---" + filename + "---�����ڣ�");//TODO:��������ǲ���ָ�ļ������ڣ�
//		}
		try {
			//������
			InputStream inputStream = connection.getInputStream();
			byte[] buffer = new byte[1024 * 4];
			int len = 0;
			//ѭ����ȡ����
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			//�ر���
			outputStream.close();
			inputStream.close();
		} catch (Exception e) {
			throw new Exception("�ļ�---" + filename + "---����ʧ�ܣ�");
		}
		return file;
	}

	//����������ص��ļ��Ƿ�����Ŀ�е�down�ļ�����
	public static void main(String[] args) {
		try {
			downloadFile(FileUtil.DOWN_DIRECTORY + FileUtil.VERSION_XML, FileUtil.VERSION_XML);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
