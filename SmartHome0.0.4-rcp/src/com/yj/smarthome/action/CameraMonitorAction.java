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
		//打开编辑器
		IEditorInput editorInput = CameraMonitorControlEditorInput.cmcei;
		IWorkbenchPage workbenchPage = window.getActivePage();
		// 通过editorinput来找到editor
		IEditorPart editorPart = workbenchPage.findEditor(editorInput);
		if (editorPart != null) {//已经打开了所需的编辑器
			workbenchPage.bringToTop(editorPart);
		} else {//没有打开就打开来
			try {
				String editorID = CameraMonitorEditor.ID;
				editorPart = workbenchPage.openEditor(editorInput, editorID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
