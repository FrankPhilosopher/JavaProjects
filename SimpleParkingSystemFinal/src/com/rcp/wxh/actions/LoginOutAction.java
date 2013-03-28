package com.rcp.wxh.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import parkingsystem.Activator;

import com.rcp.wxh.pojo.TEmp;
import com.rcp.wxh.utils.AutomLoginUtil;
import com.rcp.wxh.utils.MessageDialogUtil;

/**
 * 注销登陆响应
 */
public class LoginOutAction extends Action implements IWorkbenchAction {

	public static final String ID = LoginOutAction.class.getName();

	private IWorkbenchWindow window;

	public LoginOutAction(IWorkbenchWindow window, String label, String image) {
		this.window = window;
		setText(label);
		setImageDescriptor(Activator.getImageDescriptor(image));
		setId(ID);
	}

	// 注销登录同时可以取消自动登录功能
	public void run() {
		int r = MessageDialogUtil.showConfirmMessage(window.getShell(), "确定要注销登录么?");
		if (r == SWT.OK) {
			AutomLoginUtil alu = new AutomLoginUtil();
			TEmp emp = (TEmp) alu.readObject();
			if (emp != null) {
				alu.setAutLogin(false, emp);
				alu.writeObject(emp);
			}
			System.exit(0);
		}
	}

	@Override
	public void dispose() {

	}

}
