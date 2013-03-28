/**
 * @Author：胡家威  
 * @CreateTime：2011-9-16 下午07:25:24
 * @Description：
 */

package com.yinger.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;

public class DownloadThread extends Thread {

	private long start;// 下载起始点
	private long block;// 下载的大小
	private int threadId;// 线程编号
	private InputStream in;// 网络下载文件输入流
	private RandomAccessFile out;// 下载文件输出流
	private DownloadInfo downloadInfo;// 下载的资源的信息
	private boolean isFinished = false;// 是否完成
	private boolean isError = false;// 是否出错

	// 构造函数
	public DownloadThread(DownloadInfo downloadInfo, int threadId) {
		this.downloadInfo = downloadInfo;
		this.threadId = threadId;
		this.start = downloadInfo.getDownPos()[threadId][0];
		System.out.println(this.start);
		this.block = downloadInfo.getDownPos()[threadId][1];
		System.out.println(this.block);
	}

	// 该线程的run方法
	@Override
	public void run() {
		HttpURLConnection http;
		try {// 首先建立连接
			http = (HttpURLConnection) downloadInfo.getDownUrl().openConnection();
			String property = "bytes=" + start + "-";//注意：bytes之后是“=”！
			http.addRequestProperty("Range", property);// 设置下载的起始位置
			in = http.getInputStream();
			out = new RandomAccessFile(downloadInfo.getSaveFilePath(), "rw");//getSaveFilePath
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("线程" + threadId + "下载失败");
			isError = true;
			isFinished = true;
			return;
		}
		// 读取输入流，存入到文件中
		byte[] buffer = new byte[1024];
		int offset = 0;
		long step = 0;
		long localSize = 0;
		try {
			out.seek(start);
			step = block > 1024 ? 1024 : block;
			while ((offset = in.read(buffer, 0, (int) step)) > 0 && localSize <= block) {
				if (downloadInfo.isError()) { // 判断其他的线程是否出错了，如果出错了就退出
					break;
				}
				out.write(buffer, 0, offset);// 写入到文件中
				localSize += offset;
				downloadInfo.getDownPos()[threadId][0] = start + localSize;// 修改该线程的下载内容的信息
				downloadInfo.getDownPos()[threadId][1] = block - localSize;
				step = block - localSize;
				step = step > 1024 ? 1024 : step;
				downloadInfo.setDownLength(offset); // 更改已经下载了的长度
			}
			in.close();
			out.close();
			http.disconnect();
			if (!downloadInfo.isError()) {// 如果没有出现错，那么当前线程的下载任务完成
				isFinished = true;
				System.out.println("线程" + threadId + "下载完成!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("线程" + threadId + "下载出错!");
			isError = true;
			isFinished = false;
			try {//出现异常要关闭输入输出流
				in.close();
				out.close();
				http.disconnect();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getBlock() {
		return block;
	}

	public void setBlock(long block) {
		this.block = block;
	}

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public RandomAccessFile getOut() {
		return out;
	}

	public void setOut(RandomAccessFile out) {
		this.out = out;
	}

	public DownloadInfo getDownloadInfo() {
		return downloadInfo;
	}

	public void setDownloadInfo(DownloadInfo downloadInfo) {
		this.downloadInfo = downloadInfo;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

}
