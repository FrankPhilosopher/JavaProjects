/**
 * @Author��������  
 * @CreateTime��2011-9-16 ����07:25:24
 * @Description��
 */

package com.yinger.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;

public class DownloadThread extends Thread {

	private long start;// ������ʼ��
	private long block;// ���صĴ�С
	private int threadId;// �̱߳��
	private InputStream in;// ���������ļ�������
	private RandomAccessFile out;// �����ļ������
	private DownloadInfo downloadInfo;// ���ص���Դ����Ϣ
	private boolean isFinished = false;// �Ƿ����
	private boolean isError = false;// �Ƿ����

	// ���캯��
	public DownloadThread(DownloadInfo downloadInfo, int threadId) {
		this.downloadInfo = downloadInfo;
		this.threadId = threadId;
		this.start = downloadInfo.getDownPos()[threadId][0];
		System.out.println(this.start);
		this.block = downloadInfo.getDownPos()[threadId][1];
		System.out.println(this.block);
	}

	// ���̵߳�run����
	@Override
	public void run() {
		HttpURLConnection http;
		try {// ���Ƚ�������
			http = (HttpURLConnection) downloadInfo.getDownUrl().openConnection();
			String property = "bytes=" + start + "-";//ע�⣺bytes֮���ǡ�=����
			http.addRequestProperty("Range", property);// �������ص���ʼλ��
			in = http.getInputStream();
			out = new RandomAccessFile(downloadInfo.getSaveFilePath(), "rw");//getSaveFilePath
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("�߳�" + threadId + "����ʧ��");
			isError = true;
			isFinished = true;
			return;
		}
		// ��ȡ�����������뵽�ļ���
		byte[] buffer = new byte[1024];
		int offset = 0;
		long step = 0;
		long localSize = 0;
		try {
			out.seek(start);
			step = block > 1024 ? 1024 : block;
			while ((offset = in.read(buffer, 0, (int) step)) > 0 && localSize <= block) {
				if (downloadInfo.isError()) { // �ж��������߳��Ƿ�����ˣ���������˾��˳�
					break;
				}
				out.write(buffer, 0, offset);// д�뵽�ļ���
				localSize += offset;
				downloadInfo.getDownPos()[threadId][0] = start + localSize;// �޸ĸ��̵߳��������ݵ���Ϣ
				downloadInfo.getDownPos()[threadId][1] = block - localSize;
				step = block - localSize;
				step = step > 1024 ? 1024 : step;
				downloadInfo.setDownLength(offset); // �����Ѿ������˵ĳ���
			}
			in.close();
			out.close();
			http.disconnect();
			if (!downloadInfo.isError()) {// ���û�г��ִ���ô��ǰ�̵߳������������
				isFinished = true;
				System.out.println("�߳�" + threadId + "�������!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("�߳�" + threadId + "���س���!");
			isError = true;
			isFinished = false;
			try {//�����쳣Ҫ�ر����������
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
