package com.yj.smarthome.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 发送HTTP请求下载文件的工具类
 * 
 * @author yinger
 * 
 */
public class HttpUtil {

	//http://host:port/load?filename=你需要到文件
	//下载一个文件列表
	public static boolean downloaddFileList(List<String> files) throws Exception {
		for (int i = 0; i < files.size(); i++) {
			downloadFile(FileUtil.DOWN_DIRECTORY + files.get(i), files.get(i));
		}
		return true;
	}

	//下载某个设备类型的xml文件
	public static File downloadDeviceTypeXmlFile(int deviceTypeId) throws Exception {
		return downloadFile(FileUtil.DOWN_DIRECTORY + FileUtil.DEVICEID_XML.get(deviceTypeId), FileUtil.DEVICEID_XML.get(deviceTypeId));
	}

	//下载新的version.xml文件
	public static File downloadVersionXmlFile() throws Exception {
		return downloadFile(FileUtil.DOWN_DIRECTORY + FileUtil.VERSION_XML, FileUtil.VERSION_XML);
	}

	//下载单个文件
	public static File downloadFile(String saveFilePath, String filename) throws Exception {
		File file = new File(saveFilePath);
//		if (file!=null && file.exists()) {//如果文件存在，那么得到的文件就会重写覆盖原来的内容
//			
//		}
		//文件输出流
		OutputStream outputStream = new FileOutputStream(file);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("http://");
		stringBuffer.append(SystemUtil.SERVER_IP + ":" + SystemUtil.SERVER_PORT + "/load?filename=");
		stringBuffer.append(filename);
		URL url = new URL(stringBuffer.toString());//下载的链接地址是固定的
		//建立http连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		//首先判断这个文件长度！
		int contentLength = connection.getContentLength();
		//如果文件不存在就抛出异常
//		if (contentLength < 0) {//-1 if the content length is not known
//			throw new Exception("文件---" + filename + "---不存在！");//TODO:这种情况是不是指文件不存在？
//		}
		try {
			//输入流
			InputStream inputStream = connection.getInputStream();
			byte[] buffer = new byte[1024 * 4];
			int len = 0;
			//循环读取数据
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			//关闭流
			outputStream.close();
			inputStream.close();
		} catch (Exception e) {
			throw new Exception("文件---" + filename + "---下载失败！");
		}
		return file;
	}

	//这里测试下载的文件是放在项目中的down文件夹中
	public static void main(String[] args) {
		try {
			downloadFile(FileUtil.DOWN_DIRECTORY + FileUtil.VERSION_XML, FileUtil.VERSION_XML);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
