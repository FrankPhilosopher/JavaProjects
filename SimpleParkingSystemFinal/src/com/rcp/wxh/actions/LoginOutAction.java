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
 * ע����½��Ӧ
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

	// ע����¼ͬʱ����ȡ���Զ���¼����
	public void run() {
		int r = MessageDialogUtil.showConfirmMessage(window.getShell(), "ȷ��Ҫע����¼ô?");
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
