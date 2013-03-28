package com.yinger.patientims.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.yinger.patientims.Activator;
import com.yinger.patientims.util.PluginUtil;

//自定义的action
public class OpenNavigatorViewAction extends Action {
	
	private final IWorkbenchWindow window;
	
	public OpenNavigatorViewAction(IWorkbenchWindow window){
		this.window = window;
		//设置菜单项文本，并给该菜单项添加快捷键以及键绑定
		this.setText("&Navigator@ Ctrl+N");
		//工具栏上提示性信息
		this.setToolTipText("Open Navigator View");
		//添加工具栏图形按钮
		this.setImageDescriptor(Activator.getImageDescriptor("/icons/Stats.ico"));
	}
	
	public void run(){
		if (window!=null) {
			try {
				//打开视图
				window.getActivePage().showView(PluginUtil.NavigatorView_ID);
			} catch (Exception e) {
				//创建错误对话框
				MessageDialog.openError(window.getShell(), "Error", "Error in opening view :"+e.getLocalizedMessage());
			}
		}
	}

}
