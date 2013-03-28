/**
 * @Author��������  
 * @CreateTime��2011-9-16 ����08:04:20
 * @Description��
 */

package com.yinger.download;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.widgets.Display;

import com.yinger.gui.MainFrame;

/**
 * �̼߳����� �������е��̣߳��鿴�Ƿ��Ѿ����������
 */
public class ThreadListener {

	private DownloadInfo downloadInfo;
	private DownloadThread[] threads;
	private boolean isFinished;
	private boolean isError;
	private Timer timer;
	private MainFrame frame;

	// ���캯��
	public ThreadListener(DownloadInfo downloadInfo, DownloadThread[] threads, MainFrame frame) {
		this.frame = frame;
		this.threads = threads;
		this.downloadInfo = downloadInfo;
		this.timer = new Timer();
		timer.schedule(new MyTask(), 0, 1000);// �ƶ�һ������ִ�е�����ÿ��1����ִ��һ��
	}

	// �Ѿ�����ˣ�
	// Bug������û��½����������񣬻�û��������ɣ��û�����������������
	// ��ô����������������ؽ��Ȼ��ɺ��������ص�һ����
	// ����ÿ���½���һ��������ô�������ͱ����0����ô���еĽ������������0��
	// �Զ���һ������
	class MyTask extends TimerTask {
		@Override
		public void run() {
			listenFinished();
			if (isFinished) {// �ļ��������
				timer.cancel();// ע�⣬cancel����Timer
				downloadInfo.setFinished(true);
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {// ����Ҫ�޸�����״̬�����ؽ���
						if (frame.getDownloadInfo().equals(downloadInfo)) {// ���������ʾ�����������ص��Ǹ���
							frame.getLabel_downloadState2().setText("�������");
							frame.getLabel_downloadState2().setToolTipText("�������");
							frame.getProgressBar_downloadPro2().setSelection(100);
							frame.getProgressBar_downloadPro2().setToolTipText("�Ѿ������� " + downloadInfo.getDownProgress() + " %");
						}
						// ��ʱ���ǲ�Ҫչʾ
						//new InfoDialog(frame.getShell(), SWT.NULL, "�ļ�������ϣ�", true).open();
					}
				});
			} else {
				if (isError) {// �ļ����س���
					timer.cancel();
					downloadInfo.setError(true);
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							if (frame.getDownloadInfo().equals(downloadInfo)) {// ���������ʾ�����������ص��Ǹ���
								frame.getLabel_downloadState2().setText("�ļ����س���");
							}
						}
					});
				} else {
					downloadInfo.setDownSpeed();
					downloadInfo.setDownProgress();
					System.out.println("�ļ����ص��ٶ��ǣ�" + downloadInfo.getDownSpeed());
					// Display.getCurrent().asyncExec(new Runnable()
					// {//Display.getCurrent���У�
					// public void run() {
					// frame.getLabel_downloadSpeed2().setText(String.valueOf(downloadInfo.getDownSpeed())
					// + " kb/s");
					// }
					// });
					Display.getDefault().asyncExec(new Runnable() {// ʹ�����getDefault�У������޸Ľ����ֵ
								@Override
								public void run() {// ����Ҫ�޸��ٶȺͽ���
									if (frame.getDownloadInfo().equals(downloadInfo)) {// ���������ʾ�����������ص��Ǹ���
										frame.getLabel_downloadSpeed2().setText(String.valueOf(downloadInfo.getDownSpeed()) + " kb/s");
										frame.getLabel_downloadSpeed2().setToolTipText(String.valueOf(downloadInfo.getDownSpeed()) + " kb/s");
										frame.getProgressBar_downloadPro2().setSelection(downloadInfo.getDownProgress());
										frame.getProgressBar_downloadPro2().setToolTipText("�Ѿ������� " + downloadInfo.getDownProgress() + " %");
									}
								}
							});
				}
			}
		}
	}

	// �������е��߳��ж��Ƿ����������
	private void listenFinished() {
		System.out.println("listenFinished");
		boolean flag = true;
		for (int i = 0; i < threads.length; i++) {
			if (threads[i].isFinished() == false) {
				isFinished = false;
				flag = false;
			}
			if (threads[i].isError() == true) {
				isError = true;
				flag = false;
				break;
			}
		}
		isFinished = flag;
	}

	public DownloadInfo getDownloadInfo() {
		return downloadInfo;
	}

	public void setDownloadInfo(DownloadInfo downloadInfo) {
		this.downloadInfo = downloadInfo;
	}

	public DownloadThread[] getThreads() {
		return threads;
	}

	public void setThreads(DownloadThread[] threads) {
		this.threads = threads;
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

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}
