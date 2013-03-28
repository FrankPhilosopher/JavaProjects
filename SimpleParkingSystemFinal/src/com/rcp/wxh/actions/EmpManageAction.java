package com.rcp.wxh.actions;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import parkingsystem.Activator;

import com.rcp.wxh.editors.EmpEditor2;
import com.rcp.wxh.editors.EmpEditorInput;

/**
 * Ա������ ����Ա��Ӧ
 * 
 * @author AB 2011-11-2
 * 
 */
public class EmpManageAction extends ValidateStatusAction implements IWorkbenchAction {
	public static final String ID = StatisticManageAction.class.getName();

	private IWorkbenchWindow window;

	public EmpManageAction(IWorkbenchWindow window, String label, String image) {
		this.window = window;
		setText(label);
		setImageDescriptor(Activator.getImageDescriptor(image));
		setId(ID);
	}

	public void run() {
		IWorkbenchPage workbenchPage = window.getActivePage();
		IEditorPart editor = workbenchPage.findEditor(EmpEditorInput.eei); // ��ȡ�༭��
		if (editor != null) { // ����༭���Ѿ���
			workbenchPage.bringToTop(editor); // ����ʾ�ñ༭��
		} else { // ���³�ʼ���ñ༭��
			try {
				workbenchPage.openEditor(EmpEditorInput.eei, EmpEditor2.ID);// yinger �򿪵���2
			} catch (PartInitException ei) {
				ei.printStackTrace();
			}
		}
	}

	@Override
	public void dispose() {

	}
}
