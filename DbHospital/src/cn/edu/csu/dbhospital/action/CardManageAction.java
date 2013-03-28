package cn.edu.csu.dbhospital.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import cn.edu.csu.dbhospital.Activator;
import cn.edu.csu.dbhospital.editor.CardManageEditor;
import cn.edu.csu.dbhospital.editor.CardManageEditorInput;

/**
 * 
 * ���ƿ�����action
 * 
 */
public class CardManageAction extends Action {

	private final IWorkbenchWindow window;

	public CardManageAction(IWorkbenchWindow window) {
		this.window = window;
		// ���ò˵����ı��������ò˵�����ӿ�ݼ��Լ�����
		this.setText("���ƿ�����");
		// ����������ʾ����Ϣ
		this.setToolTipText("���ƿ�����");
		// ��ӹ�����ͼ�ΰ�ť
		this.setImageDescriptor(Activator.getImageDescriptor("/icons/Mail.ico"));
	}

	public void run() {
		if (window != null) {
			IEditorInput input = CardManageEditorInput.EDITOR_INPUT;
			IWorkbenchPage workbenchPage = window.getActivePage();
			IEditorPart editor = workbenchPage.findEditor(input);
			String editorID = CardManageEditor.ID;
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
