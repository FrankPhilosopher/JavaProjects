package org.eclipse.wb.swt;

import org.eclipse.swt.widgets.Display;

public abstract class RcpTask {

	public static final String RESULT_OK = "OK";// 成功
	public static final String RESULT_FAIL = "FAIL";// 失败

	private StringBuffer message;

	public void execTask() {
		doBeforeTask();
		message = new StringBuffer("");
		// 开启线程后台执行耗时任务
		new Thread(new Runnable() {
			public void run() {
				synchronized (message) {
					doTaskInBackground(message);
					message.notify();
				}
			}
		}).start();

		// 等待后台线程执行完成
		new Thread(new Runnable() {
			public void run() {
				synchronized (message) {
					if ("".equalsIgnoreCase(message.toString())) {
						try {
							message.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						doAfterTask(message.toString());
					}
				});
			}
		}).start();

	}

	protected abstract void doBeforeTask();// 任务开始之前执行，前台执行

	protected abstract void doTaskInBackground(StringBuffer message);// 后台任务，执行完成之后设置message的值

	protected abstract void doAfterTask(String result);// 得到任务的message值，前台执行

}
