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
import com.yj.smarthome.editor.ApplianceControlEditor;
import com.yj.smarthome.editorInput.ApplianceControlEditorInput;
import com.yj.smarthome.util.SystemUtil;

/**
 * �ҵ���Ƶ�action
 * 
 * @author yinger
 * 
 */
public class ApplianceControlAction extends Action implements IWorkbenchAction {

	public static final String ID = ApplianceControlAction.class.getName();
	private IWorkbenchWindow window;

	public ApplianceControlAction(IWorkbenchWindow window) {
		this.window = window;
//		setText("�ƹؿ���");
//		setToolTipText("�ƹؿ���");
		setToolTipText(SystemUtil.APPLIANCE_CONTROL);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageKeyManager.APPLIANCE_ICON));
	}

	@Override
	public void run() {
		//�򿪱༭��
		IEditorInput editorInput = ApplianceControlEditorInput.acei;
		IWorkbenchPage workbenchPage = window.getActivePage();
		// ͨ��editorinput���ҵ�editor
		IEditorPart editorPart = workbenchPage.findEditor(editorInput);
		if (editorPart != null) {//�Ѿ���������ı༭��
			workbenchPage.bringToTop(editorPart);
		} else {//û�д򿪾ʹ���
			try {
				String editorID = ApplianceControlEditor.ID;
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
