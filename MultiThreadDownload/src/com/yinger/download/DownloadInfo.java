/**
 * @Author��������  
 * @CreateTime��2011-9-16 ����02:19:23
 * @Description��
 */

package com.yinger.download;

import java.io.Serializable;
import java.net.URL;

/**
 * ����DownloadInfo �� resultMsg
 */
public class DownloadInfo implements Serializable {

	private static final long serialVersionUID = -6628237677712230747L;
	private int myResult = 0;// ���ԵĽ��ֵ��ֻ��1�ű�ʾ�ɹ���
	private String resultMsg = "";// ��ʼ��ʱ�ǿ��ַ���
	private boolean isFinished;// �Ƿ��������
	private boolean isError;// �Ƿ���ִ���
	private URL url;// ���ص�url--->URL �������л� Serializable
	private float downSpeed;// �����ٶ�
	private int threadNum;// �߳���Ŀ
	private long fileLength;// Ҫ���ص��ļ��ĳ���
	private long downLength = 0;// �����ص��ļ����ܳ��ȣ�������һ�Σ�
	private long predownLength = 0;// ֮ǰ���صĳ��ȣ���һ��֮ǰ��
	private String saveFileName;// ���ֵ��ļ�����
	private String saveFilePath;// ����֮��ı����ļ�
	private long[][] downPos;// ���̵߳�����״̬
	private int downProgress;

	// ���ø��������߳������Ϣ(���ص���ʼ��ͳ���)���������Ҫ��
	public void setDownPos() {
		downPos = new long[threadNum][2];
		long length = this.fileLength;// �ܳ���
		System.out.println("length:" + length);
		long block = this.fileLength / this.threadNum + 1;// ÿ��Ĵ�С(�������һ�鲻��)
		System.out.println("block:" + block);
		// �������һ��֮�⣬�����Ĳ��ֶ��ǿ��Կ�����block���С
		for (int i = 0; i < threadNum; i++) {
			downPos[i][0] = i * block;
			downPos[i][1] = block;
			if (i == threadNum - 1) {
				downPos[i][1] = length - i * block - 1;
			}
		}
	}

	// �õ��ļ����صĽ���
	public int getDownProgress() {
		return this.downProgress;
	}

	public void setDownProgress() {
		this.downProgress = Math.round(100.0f * this.getDownLength() / this.getFileLength());//int
	}

	// ���ñ�����ļ�����
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
		// ������û��ǲ�Ҫʹ������Ĵ��룬��Ȼÿ�ζ�������Ŀ�ĸ�Ŀ¼���洴��һ����Ӧ���ļ�
		// File file = new File(saveFileName);
		// try {
		// RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
		// randomAccessFile.close();// ���ﴴ��һ��RandomAccessFileֻ�����ڲ����Ƿ񱣴��λ���Ƿ����
		// this.saveFileName = saveFileName;//ͬʱҪע�⣬����Ҫclose���ӣ���Ȼ��������֮���ܴ����鿴
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// System.out.println("�ļ������·�����ԣ�");
		// } catch (IOException e) {
		// e.printStackTrace();
		// System.out.println("�ļ��������");
		// }
	}

	// ���������˵ĳ��ȣ������Ǽ��ϲ�����ֵ
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

	// �������ص��ٶ�
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
