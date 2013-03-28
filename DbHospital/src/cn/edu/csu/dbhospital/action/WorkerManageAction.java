package cn.edu.csu.dbhospital.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import cn.edu.csu.dbhospital.Activator;
import cn.edu.csu.dbhospital.editor.DoctorManageEditor;
import cn.edu.csu.dbhospital.editor.DoctorManageEditorInput;
import cn.edu.csu.dbhospital.editor.WorkerManageEditor;
import cn.edu.csu.dbhospital.editor.WorkerManageEditorInput;

/**
 * 
 * ������Ա����action
 * 
 */
public class WorkerManageAction extends Action {

	private final IWorkbenchWindow window;

	public WorkerManageAction(IWorkbenchWindow window) {
		this.window = window;
		// ���ò˵����ı��������ò˵�����ӿ�ݼ��Լ�����
		this.setText("Ա������");
		// ����������ʾ����Ϣ
		this.setToolTipText("Ա������");
		// ��ӹ�����ͼ�ΰ�ť
		this.setImageDescriptor(Activator.getImageDescriptor("/icons/User.ico"));
	}

	public void run() {
		if (window != null) {
			IEditorInput input = WorkerManageEditorInput.EDITOR_INPUT;
			IWorkbenchPage workbenchPage = window.getActivePage();
			IEditorPart editor = workbenchPage.findEditor(input);
			String editorID = WorkerManageEditor.ID;
			if (editor != null) {
				workbenchPage.bringToTop(editor);
			} else {
				try {
					workbenchPage.openEditor(input, editorID);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
