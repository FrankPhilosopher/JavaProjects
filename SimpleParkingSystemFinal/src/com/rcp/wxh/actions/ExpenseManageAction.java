package com.rcp.wxh.actions;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import parkingsystem.Activator;

import com.rcp.wxh.editors.ExpenseEditor;
import com.rcp.wxh.editors.ExpenseEditorInput;

/**
 * 缴费记录查询
 * 
 * @author wuxuehong 2011-11-14
 * 
 */
public class ExpenseManageAction extends ValidateStatusAction implements IWorkbenchAction {

	public static String ID = ExpenseManageAction.class.getName(); // unique ID

	private IWorkbenchWindow window;

	public ExpenseManageAction(IWorkbenchWindow window, String label, String image) {
		this.window = window;
		this.setText(label);
		setImageDescriptor(Activator.getImageDescriptor(image));
		this.setId(ID); // 一定要记住
	}

	public void run() {
//		TExpenseDialog ted = new TExpenseDialog(window.getShell(), SWT.NONE);
//		ted.open();
		IWorkbenchPage workbenchPage = window.getActivePage();
		IEditorPart editor = workbenchPage.findEditor(ExpenseEditorInput.eei); // 获取编辑器
		if (editor != null) { // 如果编辑器已经打开
			workbenchPage.bringToTop(editor); // 则显示该编辑器
		} else { // 重新初始化该编辑器
			try {
				editor = workbenchPage.openEditor(ExpenseEditorInput.eei, ExpenseEditor.ID);
			} catch (PartInitException ei) {
				ei.printStackTrace();
			}
		}
	}

	public void dispose() {

	}

}
