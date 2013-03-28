package com.rcp.wxh.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import parkingsystem.Activator;

import com.rcp.wxh.editors.EnterEditor;
import com.rcp.wxh.editors.EnterEditorInput;

/**
 * 车辆出场监控响应
 * 
 * @author wuxuehong 2011-11-23
 * 
 */
public class LeaveManageAction extends Action implements IWorkbenchAction {

	public static final String ID = LeaveManageAction.class.getName();

	private IWorkbenchWindow window;

	public LeaveManageAction(IWorkbenchWindow window, String label, String image) {
		this.window = window;
		setText(label);
		setImageDescriptor(Activator.getImageDescriptor(image));
		setId(ID);
	}

	public void run() {
		IWorkbenchPage workbenchPage = window.getActivePage();
		IEditorPart editor = workbenchPage.findEditor(EnterEditorInput.eei); // 获取编辑器
		if (editor != null) { // 如果编辑器已经打开
			if (EnterEditor.STYLE == EnterEditor.EXIT_ONLY) // 如果当前的编辑器是 出场控制
				workbenchPage.bringToTop(editor); // 则显示该编辑器
			else {
				workbenchPage.closeEditor(editor, true); // 关闭当前编辑器 并重新打开
				EnterEditor.STYLE = EnterEditor.EXIT_ONLY;
				try {
					workbenchPage.openEditor(EnterEditorInput.eei, EnterEditor.ID);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		} else { // 重新初始化该编辑器
			EnterEditor.STYLE = EnterEditor.EXIT_ONLY;
			try {
				workbenchPage.openEditor(EnterEditorInput.eei, EnterEditor.ID);
			} catch (PartInitException ei) {
				ei.printStackTrace();
			}
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
