/**
 * @Author��������  
 * @CreateTime��2011-9-16 ����08:03:49
 * @Description��
 */

package com.yinger.download;

import com.yinger.gui.MainFrame;

public class MultiThreadDownload {

	private DownloadInfo downloadInfo;
	private DownloadThread[] threads;
	private MainFrame frame;

	public MultiThreadDownload(DownloadInfo downloadInfo, MainFrame frame) {
		this.downloadInfo = downloadInfo;
		this.frame = frame;
	}

	// ��ʼ�����ļ�,���ȶ��߳�����
	public void startDownFile() {
		threads = new DownloadThread[downloadInfo.getThreadNum()];
		for (int i = 0; i < downloadInfo.getThreadNum(); i++) {
			threads[i] = new DownloadThread(downloadInfo, i);
			threads[i].setPriority(7);
			threads[i].start();
		}
		new ThreadListener(downloadInfo, threads, frame);
	}

}
