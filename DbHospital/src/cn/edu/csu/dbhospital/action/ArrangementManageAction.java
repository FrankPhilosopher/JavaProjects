package cn.edu.csu.dbhospital.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import cn.edu.csu.dbhospital.Activator;
import cn.edu.csu.dbhospital.editor.ArrangementManageEditor;
import cn.edu.csu.dbhospital.editor.ArrangementManageEditorInput;

/**
 * 
 * �Ű����action
 * 
 */
public class ArrangementManageAction extends Action {

	private final IWorkbenchWindow window;

	public ArrangementManageAction(IWorkbenchWindow window) {
		this.window = window;
		// ���ò˵����ı��������ò˵�����ӿ�ݼ��Լ�����
		this.setText("�Ű����");
		// ����������ʾ����Ϣ
		this.setToolTipText("�Ű����");
		// ��ӹ�����ͼ�ΰ�ť
		this.setImageDescriptor(Activator.getImageDescriptor("/icons/Temporary.ico"));
	}

	public void run() {
		if (window != null) {
			IEditorInput input = ArrangementManageEditorInput.EDITOR_INPUT;
			IWorkbenchPage workbenchPage = window.getActivePage();
			IEditorPart editor = workbenchPage.findEditor(input);
			String editorID = ArrangementManageEditor.ID;
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
