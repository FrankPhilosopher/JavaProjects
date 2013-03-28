package cn.edu.csu.dbhospital.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * 信息窗口消息工具
 * 
 */
public class MessageDialogUtil {

	/**
	 * 警告信息对话框
	 * 
	 * @param shell
	 * @param message
	 */
	public static void showWarningMessage(Shell shell, String message) {
		MessageBox mb = new MessageBox(shell, SWT.OK | SWT.ICON_WARNING);
		mb.setMessage(message);
		mb.setText("警告");
		mb.open();
	}

	/**
	 * 消息提示对话框
	 * 
	 * @param shell
	 * @param message
	 */
	public static void showInfoMessage(Shell shell, String message) {
		MessageBox mb = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
		mb.setMessage(message);
		mb.setText("信息");
		mb.open();
	}

	/**
	 * 错误消息提示框
	 * 
	 * @param shell
	 * @param message
	 */
	public static void showErrorMessage(Shell shell, String message) {
		MessageBox mb = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
		mb.setMessage(message);
		mb.setText("错误");
		mb.open();
	}

	/**
	 * 消息确认对话框
	 * 
	 * @param shell
	 * @param message
	 */
	public static int showConfirmMessage(Shell shell, String message) {
		MessageBox mb = new MessageBox(shell, SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION);
		mb.setMessage(message);
		mb.setText("提示");
		return mb.open();
	}

}
