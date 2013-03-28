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
import cn.edu.csu.dbhospital.editor.UserManageEditor;
import cn.edu.csu.dbhospital.editor.UserManageEditorInput;
import cn.edu.csu.dbhospital.editor.WorkerManageEditor;
import cn.edu.csu.dbhospital.editor.WorkerManageEditorInput;

/**
 * 
 * �û�����action
 * 
 */
public class UserManageAction extends Action {

	private final IWorkbenchWindow window;

	public UserManageAction(IWorkbenchWindow window) {
		this.window = window;
		// ���ò˵����ı��������ò˵�����ӿ�ݼ��Լ�����
		this.setText("�û�����");
		// ����������ʾ����Ϣ
		this.setToolTipText("�û�����");
		// ��ӹ�����ͼ�ΰ�ť
		this.setImageDescriptor(Activator.getImageDescriptor("/icons/User.ico"));
	}

	public void run() {
		if (window != null) {
			IEditorInput input = UserManageEditorInput.EDITOR_INPUT;
			IWorkbenchPage workbenchPage = window.getActivePage();
			IEditorPart editor = workbenchPage.findEditor(input);
			String editorID = UserManageEditor.ID;
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
