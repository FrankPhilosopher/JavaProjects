/**
 * @Author：胡家威  
 * @CreateTime：2011-9-16 下午08:04:20
 * @Description：
 */

package com.yinger.download;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.widgets.Display;

import com.yinger.gui.MainFrame;

/**
 * 线程监听类 监听所有的线程，查看是否都已经完成了下载
 */
public class ThreadListener {

	private DownloadInfo downloadInfo;
	private DownloadThread[] threads;
	private boolean isFinished;
	private boolean isError;
	private Timer timer;
	private MainFrame frame;

	// 构造函数
	public ThreadListener(DownloadInfo downloadInfo, DownloadThread[] threads, MainFrame frame) {
		this.frame = frame;
		this.threads = threads;
		this.downloadInfo = downloadInfo;
		this.timer = new Timer();
		timer.schedule(new MyTask(), 0, 1000);// 制定一个立即执行的任务，每过1秒钟执行一次
	}

	// 已经解决了：
	// Bug：如果用户新建了下载任务，还没等下载完成，用户点击了其他的下载项，
	// 那么其他的下载项的下载进度会变成和正在下载的一样！
	// 而且每次新建了一个任务，那么进度条就变成了0，那么所有的进度条都变成了0！
	// 自定义一个任务
	class MyTask extends TimerTask {
		@Override
		public void run() {
			listenFinished();
			if (isFinished) {// 文件下载完毕
				timer.cancel();// 注意，cancel的是Timer
				downloadInfo.setFinished(true);
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {// 这里要修改下载状态和下载进度
						if (frame.getDownloadInfo().equals(downloadInfo)) {// 如果正在显示的是正在下载的那个！
							frame.getLabel_downloadState2().setText("下载完毕");
							frame.getLabel_downloadState2().setToolTipText("下载完毕");
							frame.getProgressBar_downloadPro2().setSelection(100);
							frame.getProgressBar_downloadPro2().setToolTipText("已经下载了 " + downloadInfo.getDownProgress() + " %");
						}
						// 暂时还是不要展示
						//new InfoDialog(frame.getShell(), SWT.NULL, "文件下载完毕！", true).open();
					}
				});
			} else {
				if (isError) {// 文件下载出错
					timer.cancel();
					downloadInfo.setError(true);
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							if (frame.getDownloadInfo().equals(downloadInfo)) {// 如果正在显示的是正在下载的那个！
								frame.getLabel_downloadState2().setText("文件下载出错");
							}
						}
					});
				} else {
					downloadInfo.setDownSpeed();
					downloadInfo.setDownProgress();
					System.out.println("文件下载的速度是：" + downloadInfo.getDownSpeed());
					// Display.getCurrent().asyncExec(new Runnable()
					// {//Display.getCurrent不行！
					// public void run() {
					// frame.getLabel_downloadSpeed2().setText(String.valueOf(downloadInfo.getDownSpeed())
					// + " kb/s");
					// }
					// });
					Display.getDefault().asyncExec(new Runnable() {// 使用这个getDefault行！可以修改界面的值
								@Override
								public void run() {// 这里要修改速度和进度
									if (frame.getDownloadInfo().equals(downloadInfo)) {// 如果正在显示的是正在下载的那个！
										frame.getLabel_downloadSpeed2().setText(String.valueOf(downloadInfo.getDownSpeed()) + " kb/s");
										frame.getLabel_downloadSpeed2().setToolTipText(String.valueOf(downloadInfo.getDownSpeed()) + " kb/s");
										frame.getProgressBar_downloadPro2().setSelection(downloadInfo.getDownProgress());
										frame.getProgressBar_downloadPro2().setToolTipText("已经下载了 " + downloadInfo.getDownProgress() + " %");
									}
								}
							});
				}
			}
		}
	}

	// 监听所有的线程判断是否都下载完成了
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
