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
 * 发送HTTP请求下载文件的工具类
 * 
 * @author yinger
 * 
 */
public class HttpUtil {

	private static HttpUtil instance;
	private StringBuffer downloadUrl;

	private StringBuffer downloadResult = new StringBuffer("");// 下载结果
	private StringBuffer updateResult = new StringBuffer("");// 更新结果

	private HttpUtil() {// 下面的部分是公共的，放在构造函数中初始化
	}

	public static HttpUtil getInstance() {
		if (instance == null)
			instance = new HttpUtil();
		return instance;
	}

	/**
	 * 下载文件列表
	 */
	public void downloadFiles() {
		// if (SystemTool.CURRENT_USER.isAutodownable()) {// 该用户是否自动下载---这个条件不能放在这里，因为手动更新时和这个配置无关
		downloadResult = new StringBuffer("");
		List<String> files = new ArrayList<String>();// 要下载的文件列表
		files.add(FileUtil.VERSION_XML);
		files.add(FileUtil.STATE_XML);
		files.add(FileUtil.LIGHT_XML);
		files.add(FileUtil.DOOR_XML);
		files.add(FileUtil.SECURITY_XML);
		files.add(FileUtil.APPLIANCE_XML);
		files.add(FileUtil.SENSOR_XML);
		if(AppliactionUtil.DEBUG) System.out.println("下载开始。。。。。。");
		for (int i = 0; i < files.size(); i++) {
			try {
				downloadFile(FileUtil.DOWN_DIRECTORY + files.get(i), files.get(i));
			} catch (Exception e) {
				processDownloadResult(e.getMessage());// 某个文件下载失败
				if(AppliactionUtil.DEBUG) System.out.println(files.get(i) +" 下载失败");
				//if(i == files.size() - 1)  //这里原来是只要一个下载失败就返回，现改为最后一个下载失败返回
					return;
			}
		}
		// 全部下载完成
		if(AppliactionUtil.DEBUG) System.out.println("下载完成。。。。。。");
		processDownloadResult("OK");
	}

	/**
	 * 处理下载结果
	 */
	protected void processDownloadResult(String result) {
		synchronized (downloadResult) {
			downloadResult.append(result);
			downloadResult.notifyAll(); // 唤醒线程
		}
	}

	/**
	 * 处理更新文件下载结果
	 */
	protected void processUpdateResult(String result) {
		synchronized (updateResult) {
			updateResult.append(result);
			updateResult.notifyAll(); // 唤醒线程
		}
	}

	/**
	 * 下载单个文件，前面是文件保存的路径，后面是要下载的文件名称----文件都是从网关上下载的！
	 */
	private void downloadFile(String saveFilePath, String filename) throws Exception {
		File file = new File(saveFilePath);
		OutputStream outputStream = new FileOutputStream(file);
		downloadUrl = new StringBuffer();
		downloadUrl.append("http://");// 文件都是从网关上下载的！但是这个网关IP有时是本地的，有时是远程的
		if (CommunicationUtil.LOGIN_TYPE == CommunicationUtil.GATEWAY_LOGIN) {
			downloadUrl.append(SystemTool.getInstance().getGateway_ip() + ":"
					+ SystemTool.getInstance().getGateway_downport() + "/load?filename=");
		} else {
			//这里的下载端口依旧用网关的下载端口，因为下载依旧从网关下载
			downloadUrl.append(SystemTool.getInstance().getCachedip() + ":"
					+ SystemTool.getInstance().getGateway_downport() + "/load?filename=");
		}
		downloadUrl.append(filename);
		if(AppliactionUtil.DEBUG) System.out.println("下载地址：" + downloadUrl.toString());// TODO：下载地址
		try {
		URL url = new URL(downloadUrl.toString());// 下载的链接地址是固定的
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 建立http连接
		connection.connect();// java.net.ConnectException: Connection refused: connect
		connection.setConnectTimeout(10000);
		connection.setReadTimeout(3000);
			InputStream inputStream = connection.getInputStream();
			byte[] buffer = new byte[1024 * 4];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {// 循环读取数据
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			if(AppliactionUtil.DEBUG) System.out.println("下载成功：" + saveFilePath);// TODO:这里可以用于提示！
		} catch (Exception e) {
			throw new Exception("文件 " + filename + " 下载失败！");
		}
	}

	/**
	 * 下载pc端软件版本文件
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
		if(AppliactionUtil.DEBUG) System.out.println("下载地址：" + downloadUrl.toString());// TODO：下载地址
		URL url = new URL(downloadUrl.toString());// 下载的链接地址是固定的
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 建立http连接
		connection.connect();// java.net.ConnectException: Connection refused: connect
		try {
			InputStream inputStream = connection.getInputStream();
			byte[] buffer = new byte[1024 * 4];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {// 循环读取数据
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			if(AppliactionUtil.DEBUG) System.out.println("下载成功：" + filename);// TODO:这里可以用于提示！
			processUpdateResult("OK");
		} catch (Exception e) {
			throw new Exception("文件 " + filename + " 下载失败！");
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

	// 这里测试下载的文件是放在项目中的down文件夹中
	public static void main(String[] args) {
		try {
			HttpUtil.getInstance().downloadPcVersionFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
