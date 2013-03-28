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
import com.yj.smarthome.editor.SecurityControlEditor;
import com.yj.smarthome.editorInput.SecurityControlEditorInput;
import com.yj.smarthome.util.SystemUtil;

/**
 * �������Ƶ�action
 * 
 * @author yinger
 * 
 */
public class SecurityControlAction extends Action implements IWorkbenchAction {

	public static final String ID = SecurityControlAction.class.getName();
	private IWorkbenchWindow window;

	public SecurityControlAction(IWorkbenchWindow window) {
		this.window = window;
		setToolTipText(SystemUtil.SECURITY_CONTROL);

		//���������ò��û�ã����������
//		setHoverImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageManager.LightIconHover));
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageKeyManager.SECURITY_ICON));
	}

	@Override
	public void run() {
		//�򿪱༭��
		IEditorInput editorInput = SecurityControlEditorInput.scei;
		IWorkbenchPage workbenchPage = window.getActivePage();
		// ͨ��editorinput���ҵ�editor
		IEditorPart editorPart = workbenchPage.findEditor(editorInput);
		if (editorPart != null) {//�Ѿ���������ı༭��
			workbenchPage.bringToTop(editorPart);
		} else {//û�д򿪾ʹ���
			try {
				String editorID = SecurityControlEditor.ID;
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
