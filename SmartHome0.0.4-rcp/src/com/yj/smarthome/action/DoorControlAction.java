package com.yj.smarthome.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.editor.DoorControlEditor;
import com.yj.smarthome.editorInput.DoorControlEditorInput;
import com.yj.smarthome.util.SystemUtil;

/**
 * �Ŵ����Ƶ�action
 * 
 * @author yinger
 * 
 */
public class DoorControlAction extends Action implements IWorkbenchAction {

	public static final String ID = DoorControlAction.class.getName();
	private IWorkbenchWindow window;

	public DoorControlAction(IWorkbenchWindow window) {
		this.window = window;
//		setToolTipText("�Ŵ�����");
		setToolTipText(SystemUtil.DOOR_CONTROL);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageKeyManager.DOOR_ICON));
	}

	@Override
	public void run() {
		//�򿪱༭��
		IEditorInput editorInput = DoorControlEditorInput.dcei;
		IWorkbenchPage workbenchPage = window.getActivePage();
		// ͨ��editorinput���ҵ�editor
		IEditorPart editorPart = workbenchPage.findEditor(editorInput);
		if (editorPart != null) {//�Ѿ���������ı༭��
			workbenchPage.bringToTop(editorPart);
		} else {//û�д򿪾ʹ���
			try {
				String editorID = DoorControlEditor.ID;
				editorPart = workbenchPage.openEditor(editorInput, editorID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void dispose() {

	}

}
