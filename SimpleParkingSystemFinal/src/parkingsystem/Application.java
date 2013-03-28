package parkingsystem;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.rcp.wxh.dialogs.LoginDialog;
import com.rcp.wxh.event.CarParkListener;
import com.rcp.wxh.event.ExceptionListener;
import com.rcp.wxh.event.ExpenseListener;
import com.rcp.wxh.event.Notifier;
import com.rcp.wxh.event.SystemOperationListener;
import com.rcp.wxh.utils.InstanceControlUtil;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		Display display = PlatformUI.createDisplay();
		// 添加事件监听器
		CarParkListener parkListener = new CarParkListener();
		ExceptionListener exceptionListener = new ExceptionListener();
		ExpenseListener expenseListener = new ExpenseListener();
		SystemOperationListener systemListener = new SystemOperationListener();
		Notifier.getInstance().addListener(parkListener);
		Notifier.getInstance().addListener(exceptionListener);
		Notifier.getInstance().addListener(expenseListener);
		Notifier.getInstance().addListener(systemListener);

		// 启动前先判断该系统是否已经启动 ，如果已经启动则退出。
		InstanceControlUtil icu = new InstanceControlUtil();
		if (icu.isRunning()) {
			MessageDialog.openWarning(new Shell(), "提示", "系统已经在运行中!");
			return IApplication.EXIT_OK;
		}

		// 在系统工作台启动前 打开登录窗口
		LoginDialog loginDialog = new LoginDialog(new Shell(), SWT.NONE);
		int R = (Integer) loginDialog.open();
		// 如果取消登录了
		if (R != 1) {
			return IApplication.EXIT_OK; // 退出系统
		}

		try {
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
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
