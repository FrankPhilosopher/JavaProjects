/**
 * @Author��������  
 * @CreateTime��2011-9-16 ����02:30:25
 * @Description��
 */

package com.yinger.download;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class DownloadUtil {

	/**
	 * ����URL���ӣ�����UrlResult�������
	 * @param url http://zhaoxin.chinadxscy.com/images/logo_no.gif
	 * @return
	 */
	public static DownloadInfo testTask(String url,String saveFilePath,int threadNum) {
		DownloadInfo downloadInfo = new DownloadInfo();
		try {
			URL tempUrl = new URL(url); // MalformedURLException  ע�⣬���ﲢ�����жϳ������url��ַָ����Ƿ���һ���ļ���Դ
			HttpURLConnection http = (HttpURLConnection) tempUrl.openConnection(); // IOException
			if (http.getResponseCode() >= 400) {
				downloadInfo.setMyResult(-1);
				downloadInfo.setResultMsg("��������Ӧ����\n");
				return downloadInfo;
			} else {
				downloadInfo.setResultMsg("���������ӳɹ���\n");
				File tempFile = new File(saveFilePath);
				if (tempFile.isDirectory()) {//�����һ���ļ��У���ô�����       C:\directory  C:\directory\ ���Ǵ����
					downloadInfo.setMyResult(-4);
					downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"�ļ�����ʽ���Ϸ���");
					return downloadInfo;
				}
				downloadInfo.setFileLength(http.getContentLength());
				if (downloadInfo.getFileLength() == -1) {//�ж��ļ��Ĵ�С������Ǹ�������ô����һ������
					downloadInfo.setMyResult(-5);
					downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"�ļ���С�޷���ȡ��");
					return downloadInfo;
				} else {
					downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"�ļ��Ĵ�С�ǣ�" + Math.round(downloadInfo.getFileLength() / 1024 * 100) / 100.0f + "kb\n");
				}
				//������ӳɹ������һ�ȡ�����ļ���С
				downloadInfo.setMyResult(1);
				downloadInfo.setUrl(tempUrl);
				downloadInfo.setDownProgress();
				downloadInfo.setSaveFilePath(getSaveFilePath(saveFilePath));
				downloadInfo.setThreadNum(getThreadNumber(threadNum));
				downloadInfo.setSaveFileName(getSaveFileName(saveFilePath));//�õ������ļ�������
				downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"�߳���Ŀ�ǣ�"+downloadInfo.getThreadNum()+"\n");
				downloadInfo.setDownPos(); // �������Ҫ������������Щ����֮��������DownloadInfo����Ϣ��ȫ����ʼ��
				return downloadInfo;
			}
		} catch (MalformedURLException e) {
			downloadInfo.setMyResult(-2);
			downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"URL��ʽ����");
		} catch (IOException e) {
			downloadInfo.setMyResult(-3);
			downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"��Դ���Ӵ���");
		}
		return downloadInfo;
	}

	/**
	 * ���ñ����ļ���λ�ã�����Ϊ����·��
	 * @param saveFilePath
	 * @return
	 */
	private static String getSaveFilePath(String saveFilePath) {
		File file = new File(saveFilePath);
		return file.getAbsolutePath();
	}

	/**
	 * ��ȡ�����ļ�����
	 * @param http
	 * @return
	 */
	public static String getSaveFileName(String saveFilePath) {
		return saveFilePath.substring(saveFilePath.lastIndexOf("\\") + 1); // ע��б�ߵķ���
	}

	/**
	 * �������õ��߳���Ŀֵ�õ�һ��������߳���Ŀ ����Ҫ��ʱǿ������Ϊ 3
	 * @param threadNum
	 * @return
	 */
	public static int getThreadNumber(int threadNum) {
		if (threadNum < 0 || threadNum >= 10) {
			return 3;
		} else {
			return threadNum;
		}
	}
	
	/**
	 * ͨ��HttpURLConnection��ȡԶ���ļ�����[����ʹ��]
	 * @param http
	 * @return
	 */
	public static String getFileName(HttpURLConnection http) {
		String fileName = http.getURL().getFile();
		return fileName.substring(fileName.lastIndexOf("\\") + 1); 
	}

	/**
	 * ��ȡURL����ʱ��MIME��Ϣ[����ʹ��]
	 * @param http
	 * @return
	 */
	public static HashMap<String, String> getMIME(HttpURLConnection http) {
		HashMap<String, String> http_mime = new HashMap<String, String>();
		for (int i = 0;; i++) {
			String mime = http.getHeaderField(i);
			if (mime == null) {
				break;
			}
			http_mime.put(http.getHeaderFieldKey(i), mime);
		}
		return http_mime;
	}
	
	/**
	 * �ж��Ƿ���һ���Ϸ���URL��ַ[����ʹ��]
	 * @param urlString
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static boolean isURL(String urlString) throws Exception {
		URL url = new URL(urlString);
		return true;
	}

	/**
	 * �ж��Ƿ���һ���Ϸ����ļ�����ʽ(���԰���·��)[����ʹ��]
	 * ����ļ�û�����ú�׺��Ҳ�ǿ��Եģ�
	 * @param fileName
	 * @return
	 */
	public static boolean isFile(String fileName) {
		File tempFile = new File(fileName);
		if (tempFile.isDirectory()) {//�����һ���ļ��У���ô�����       C:\directory  C:\directory\ ���Ǵ����
			System.out.println("�ļ�����ʽ���Ϸ���");
			return false;
		} else {
			return true;
		}
	}
	
}
