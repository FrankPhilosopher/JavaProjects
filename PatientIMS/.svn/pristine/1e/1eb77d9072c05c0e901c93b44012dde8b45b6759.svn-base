package com.yinger.patientims;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.yinger.patientims.dialogs.LoginDialog;
import com.yinger.patientims.model.Doctor;
import com.yinger.patientims.util.DBUtil;

/**
 * This class controls all aspects of the application's execution
 * Application类是RCP应用程序的主程序类，相当于控制器，类似于Main（）方法
 * 该类负责创建一个工作台（Workbench），并且与ApplicationWorkbenchAdvisor类连接
 * 该类实现了IApplication接口,启动RCP应用程序之后该类首先被加载。 如果系统需要登录，通常是在这个类中插入登录模块 通常情况下这个类不需要改动
 */
public class Application implements IApplication {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.
	 * IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		Display display = PlatformUI.createDisplay(); // 创建Display对象
		try {
			// 启动之前先进行登录操作
			if (!login()) {
				return IApplication.EXIT_OK; // 没有登陆成功就正常退出
			}
			// 启动工作台，从而打开应用程序窗口
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART)
				return IApplication.EXIT_RESTART;
			else
				return IApplication.EXIT_OK;
		} finally {
			display.dispose();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning())
			return;
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed()) // 关闭窗口之前先判断display是否被销毁了
					workbench.close();
			}
		});
	}

	// 登录验证
	public boolean login() {
		boolean flag = false;// 标志
		int tryCount = 0;//登录的次数
		while (!flag) {
			LoginDialog loginDialog = new LoginDialog(null);// null是可以的！
			Doctor doctor = new Doctor();
			loginDialog.setDoctor(doctor);// 这里很重要
			if (tryCount == 3) {//尝试登录了三次没有成功就退出
//				loginDialog.setErrorMessage("Wrong Login Information!");
				return false;
			}
			int res = loginDialog.open();// 注意，可能用户只是点击了关闭！
			if (res == IDialogConstants.ABORT_ID || res == IDialogConstants.CANCEL_ID) {
				return false; // 关闭或者退出了就直接退出程序
			}
			if (res == IDialogConstants.OK_ID) {// Dialog返回之前会设置好内部的doctor对象
				doctor = DBUtil.getLoginInfo(doctor);// 此时右边的doctor对象有了username和password
			}
			// doctor肯定不为null(每次都是new Doctor())，那么就要看doctor的属性
			if (doctor.getName() != null) {// 如果用户输入了name和password之后，但是信息无效就会提示这个或者用户直接点击了关闭登录
				// MessageDialog.openInformation(null,
				// "Error Information", "Wrong Login Information!");
				// loginDialog.setErrorMessage("Wrong Login Information!");
				flag = true;// 这种情况下才是登录成功，否则继续进行登录
			}else {
				tryCount++;
			}
		}
		return true;
	}

}
