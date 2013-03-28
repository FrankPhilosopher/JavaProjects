/**
 * @Author：胡家威  
 * @CreateTime：2011-9-16 下午02:19:23
 * @Description：
 */

package com.yinger.download;

import java.io.Serializable;
import java.net.URL;

/**
 * 整合DownloadInfo 和 resultMsg
 */
public class DownloadInfo implements Serializable {

	private static final long serialVersionUID = -6628237677712230747L;
	private int myResult = 0;// 测试的结果值，只有1才表示成功了
	private String resultMsg = "";// 初始化时是空字符串
	private boolean isFinished;// 是否下载完成
	private boolean isError;// 是否出现错误
	private URL url;// 下载的url--->URL 可以序列化 Serializable
	private float downSpeed;// 下载速度
	private int threadNum;// 线程数目
	private long fileLength;// 要下载的文件的长度
	private long downLength = 0;// 已下载的文件的总长度（包括这一次）
	private long predownLength = 0;// 之前下载的长度（这一次之前）
	private String saveFileName;// 保持的文件名称
	private String saveFilePath;// 下载之后的保存文件
	private long[][] downPos;// 各线程的下载状态
	private int downProgress;

	// 设置各个下载线程类的信息(下载的起始点和长度)，这个很重要！
	public void setDownPos() {
		downPos = new long[threadNum][2];
		long length = this.fileLength;// 总长度
		System.out.println("length:" + length);
		long block = this.fileLength / this.threadNum + 1;// 每块的大小(可能最后一块不是)
		System.out.println("block:" + block);
		// 除了最后一个之外，其他的部分都是可以看作是block块大小
		for (int i = 0; i < threadNum; i++) {
			downPos[i][0] = i * block;
			downPos[i][1] = block;
			if (i == threadNum - 1) {
				downPos[i][1] = length - i * block - 1;
			}
		}
	}

	// 得到文件下载的进度
	public int getDownProgress() {
		return this.downProgress;
	}

	public void setDownProgress() {
		this.downProgress = Math.round(100.0f * this.getDownLength() / this.getFileLength());//int
	}

	// 设置保存的文件名称
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
		// 这里最好还是不要使用下面的代码，不然每次都会在项目的根目录下面创建一个对应的文件
		// File file = new File(saveFileName);
		// try {
		// RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
		// randomAccessFile.close();// 这里创建一个RandomAccessFile只是用于测试是否保存的位置是否可行
		// this.saveFileName = saveFileName;//同时要注意，这里要close连接，不然下载完了之后不能打开来查看
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// System.out.println("文件保存的路径不对！");
		// } catch (IOException e) {
		// e.printStackTrace();
		// System.out.println("文件输出出错！");
		// }
	}

	// 设置下载了的长度，这里是加上参数的值
	public void setDownLength(long downLength) {
		this.downLength += downLength;
	}

	public URL getDownUrl() {
		return url;
	}

	public void setDownUrl(URL downUrl) {
		this.url = downUrl;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public long getDownLength() {
		return downLength;
	}

	public long getPredownLength() {
		return predownLength;
	}

	public void setPredownLength(long predownLength) {
		this.predownLength = predownLength;
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

	public float getDownSpeed() {
		return downSpeed;
	}

	// 设置下载的速度
	public void setDownSpeed() {
		this.downSpeed = Math.round((downLength - predownLength) / 1024 * 100) / 100.0f;
		predownLength = downLength;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public long[][] getDownPos() {
		return downPos;
	}

	public long getFileLength() {
		return fileLength;
	}

	public int getMyResult() {
		return myResult;
	}

	public void setMyResult(int myResult) {
		this.myResult = myResult;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

}
