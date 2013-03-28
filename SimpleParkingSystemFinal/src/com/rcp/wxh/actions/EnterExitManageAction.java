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
 * 车辆出入场管理 响应 //有出有进
 * 
 * @author Administrator
 * 
 */
public class EnterExitManageAction extends Action implements IWorkbenchAction {

	public static final String ID = EnterExitManageAction.class.getName();

	private final IWorkbenchWindow window;

	public EnterExitManageAction(IWorkbenchWindow window, String label, String imagePath) {
		this.window = window;
		setText(label);
		setId(ID);
		setImageDescriptor(Activator.getImageDescriptor(imagePath));
	}

	/**
	 * 打开相应视图
	 */
	public void run() {
		IWorkbenchPage workbenchPage = window.getActivePage();
		IEditorPart editor = workbenchPage.findEditor(EnterEditorInput.eei); // 获取编辑器
		if (editor != null) { // 如果编辑器已经打开
			if (EnterEditor.STYLE == EnterEditor.ENTER_EXIT) // 如果当前的编辑器是 入场控制
				workbenchPage.bringToTop(editor); // 则显示该编辑器
			else {
				workbenchPage.closeEditor(editor, true); // 关闭当前编辑器 并重新打开
				EnterEditor.STYLE = EnterEditor.ENTER_EXIT;
				try {
					workbenchPage.openEditor(EnterEditorInput.eei, EnterEditor.ID);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		} else { // 重新初始化该编辑器
			EnterEditor.STYLE = EnterEditor.ENTER_EXIT;
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
