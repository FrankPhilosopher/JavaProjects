package com.rcp.wxh.actions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import parkingsystem.Activator;

import com.rcp.wxh.dialogs.TExceptionDialog;

/**
 * 异常信息记录查询
 * 
 * @author wuxuehong 2011-11-14
 * 
 */
public class ExceptionManageAction extends ValidateStatusAction implements IWorkbenchAction {

	public static String ID = ExceptionManageAction.class.getName(); // unique ID

	private IWorkbenchWindow window;

	public ExceptionManageAction(IWorkbenchWindow window, String label, String image) {
		this.window = window;
		this.setText(label);
		setImageDescriptor(Activator.getImageDescriptor(image));
		this.setId(ID); // 一定要记住
	}

	/**
	 * 响应执行
	 */
	public void run() {
//		MessageDialogUtil.showInfoMessage(window.getShell(), "异常信息");
		TExceptionDialog ted = new TExceptionDialog(window.getShell(), SWT.NONE);
		ted.open();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
