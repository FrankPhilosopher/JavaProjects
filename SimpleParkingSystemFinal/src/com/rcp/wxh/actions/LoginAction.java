package com.rcp.wxh.actions;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.rcp.wxh.pojo.TEmp;
import com.rcp.wxh.utils.AutomLoginUtil;
import com.rcp.wxh.utils.MessageDialogUtil;

/**
 * 系统登录响应
 * 
 * TODO：yinger   这个应该是没有用的！和注销登录差不多
 */
public class LoginAction extends ValidateStatusAction implements IWorkbenchAction {

	public static final String ID = LoginAction.class.getName();

	private IWorkbenchWindow window;

	public LoginAction(IWorkbenchWindow window, String label) {
		this.window = window;
		this.setText(label);
		this.setId(ID); // 一定要记住
	}

	public void run() {
//		MessageDialog.openInformation(window.getShell(), "测试", "只有管理员能点击这个，有木有?");
		int r = MessageDialogUtil.showConfirmMessage(window.getShell(), "确定要注销登录么?");
		if (r == SWT.OK) {
			AutomLoginUtil alu = new AutomLoginUtil();
			TEmp emp = (TEmp) alu.readObject();
			if (emp != null) {
				alu.setAutLogin(false, emp);//？ 没有保存设置！
			}
			System.exit(0);
		}
	}

	public void dispose() {
	}

}
