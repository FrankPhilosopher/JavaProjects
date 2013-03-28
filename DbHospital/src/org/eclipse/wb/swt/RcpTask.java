package org.eclipse.wb.swt;

import org.eclipse.swt.widgets.Display;

public abstract class RcpTask {

	public static final String RESULT_OK = "OK";// �ɹ�
	public static final String RESULT_FAIL = "FAIL";// ʧ��

	private StringBuffer message;

	public void execTask() {
		doBeforeTask();
		message = new StringBuffer("");
		// �����̺߳�ִ̨�к�ʱ����
		new Thread(new Runnable() {
			public void run() {
				synchronized (message) {
					doTaskInBackground(message);
					message.notify();
				}
			}
		}).start();

		// �ȴ���̨�߳�ִ�����
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

	protected abstract void doBeforeTask();// ����ʼ֮ǰִ�У�ǰִ̨��

	protected abstract void doTaskInBackground(StringBuffer message);// ��̨����ִ�����֮������message��ֵ

	protected abstract void doAfterTask(String result);// �õ������messageֵ��ǰִ̨��

}
