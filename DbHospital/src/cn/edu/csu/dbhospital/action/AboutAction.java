package cn.edu.csu.dbhospital.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;

import cn.edu.csu.dbhospital.Activator;
import cn.edu.csu.dbhospital.dialog.AboutDialog;

/**
 * 
 * 关于action
 * 
 */
public class AboutAction extends Action {

	private final IWorkbenchWindow window;

	public AboutAction(IWorkbenchWindow window) {
		this.window = window;
		// 设置菜单项文本，并给该菜单项添加快捷键以及键绑定
		this.setText("关于");
		// 工具栏上提示性信息
		this.setToolTipText("关于");
		// 添加工具栏图形按钮
		this.setImageDescriptor(Activator.getImageDescriptor("/icons/Home.ico"));
	}

	public void run() {
		if (window != null) {
			AboutDialog aboutDialog = new AboutDialog(window.getShell());
			aboutDialog.open();
		}
	}

}
