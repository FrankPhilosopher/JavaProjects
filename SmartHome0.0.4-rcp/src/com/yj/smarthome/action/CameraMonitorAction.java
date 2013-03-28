package com.yj.smarthome.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.editor.CameraMonitorEditor;
import com.yj.smarthome.editorInput.CameraMonitorControlEditorInput;
import com.yj.smarthome.util.SystemUtil;

public class CameraMonitorAction extends Action {

	public static final String ID = CameraMonitorAction.class.getName();
	private IWorkbenchWindow window;

	public CameraMonitorAction(IWorkbenchWindow window) {
		this.window = window;
		this.setText(SystemUtil.CAMERAMONITOR_CONTROL);
		this.setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageKeyManager.CAMERA_MONITOR));
	}

	@Override
	public void run() {
		//�򿪱༭��
		IEditorInput editorInput = CameraMonitorControlEditorInput.cmcei;
		IWorkbenchPage workbenchPage = window.getActivePage();
		// ͨ��editorinput���ҵ�editor
		IEditorPart editorPart = workbenchPage.findEditor(editorInput);
		if (editorPart != null) {//�Ѿ���������ı༭��
			workbenchPage.bringToTop(editorPart);
		} else {//û�д򿪾ʹ���
			try {
				String editorID = CameraMonitorEditor.ID;
				editorPart = workbenchPage.openEditor(editorInput, editorID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
