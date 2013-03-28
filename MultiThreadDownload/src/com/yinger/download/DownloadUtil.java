/**
 * @Author：胡家威  
 * @CreateTime：2011-9-16 下午02:30:25
 * @Description：
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
	 * 建立URL连接，返回UrlResult结果类型
	 * @param url http://zhaoxin.chinadxscy.com/images/logo_no.gif
	 * @return
	 */
	public static DownloadInfo testTask(String url,String saveFilePath,int threadNum) {
		DownloadInfo downloadInfo = new DownloadInfo();
		try {
			URL tempUrl = new URL(url); // MalformedURLException  注意，这里并不能判断出输入的url地址指向的是否是一个文件资源
			HttpURLConnection http = (HttpURLConnection) tempUrl.openConnection(); // IOException
			if (http.getResponseCode() >= 400) {
				downloadInfo.setMyResult(-1);
				downloadInfo.setResultMsg("服务器响应错误！\n");
				return downloadInfo;
			} else {
				downloadInfo.setResultMsg("服务器连接成功！\n");
				File tempFile = new File(saveFilePath);
				if (tempFile.isDirectory()) {//如果是一个文件夹，那么会出错       C:\directory  C:\directory\ 都是错误的
					downloadInfo.setMyResult(-4);
					downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"文件名格式不合法！");
					return downloadInfo;
				}
				downloadInfo.setFileLength(http.getContentLength());
				if (downloadInfo.getFileLength() == -1) {//判读文件的大小，如果是负数，那么返回一个错误
					downloadInfo.setMyResult(-5);
					downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"文件大小无法获取！");
					return downloadInfo;
				} else {
					downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"文件的大小是：" + Math.round(downloadInfo.getFileLength() / 1024 * 100) / 100.0f + "kb\n");
				}
				//如果连接成功，并且获取到了文件大小
				downloadInfo.setMyResult(1);
				downloadInfo.setUrl(tempUrl);
				downloadInfo.setDownProgress();
				downloadInfo.setSaveFilePath(getSaveFilePath(saveFilePath));
				downloadInfo.setThreadNum(getThreadNumber(threadNum));
				downloadInfo.setSaveFileName(getSaveFileName(saveFilePath));//得到保存文件的名称
				downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"线程数目是："+downloadInfo.getThreadNum()+"\n");
				downloadInfo.setDownPos(); // 这个很重要，经过上面这些步骤之后就完成了DownloadInfo的信息的全部初始化
				return downloadInfo;
			}
		} catch (MalformedURLException e) {
			downloadInfo.setMyResult(-2);
			downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"URL格式错误！");
		} catch (IOException e) {
			downloadInfo.setMyResult(-3);
			downloadInfo.setResultMsg(downloadInfo.getResultMsg()+"资源连接错误！");
		}
		return downloadInfo;
	}

	/**
	 * 设置保存文件的位置，设置为绝对路径
	 * @param saveFilePath
	 * @return
	 */
	private static String getSaveFilePath(String saveFilePath) {
		File file = new File(saveFilePath);
		return file.getAbsolutePath();
	}

	/**
	 * 获取保存文件名称
	 * @param http
	 * @return
	 */
	public static String getSaveFileName(String saveFilePath) {
		return saveFilePath.substring(saveFilePath.lastIndexOf("\\") + 1); // 注意斜线的方向
	}

	/**
	 * 根据设置的线程数目值得到一个合理的线程数目 不合要求时强行设置为 3
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
	 * 通过HttpURLConnection获取远程文件名称[不再使用]
	 * @param http
	 * @return
	 */
	public static String getFileName(HttpURLConnection http) {
		String fileName = http.getURL().getFile();
		return fileName.substring(fileName.lastIndexOf("\\") + 1); 
	}

	/**
	 * 获取URL连接时的MIME信息[不再使用]
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
	 * 判断是否是一个合法的URL地址[不再使用]
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
	 * 判断是否是一个合法的文件名格式(可以包含路径)[不再使用]
	 * 如果文件没有设置后缀名也是可以的！
	 * @param fileName
	 * @return
	 */
	public static boolean isFile(String fileName) {
		File tempFile = new File(fileName);
		if (tempFile.isDirectory()) {//如果是一个文件夹，那么会出错       C:\directory  C:\directory\ 都是错误的
			System.out.println("文件名格式不合法！");
			return false;
		} else {
			return true;
		}
	}
	
}
