package com.yj.smarthome.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.editor.SensorControlEditor;
import com.yj.smarthome.editorInput.SensorControlEditorInput;
import com.yj.smarthome.util.SystemUtil;

public class SensorControlAction extends Action {

	public static final String ID = SensorControlAction.class.getName();
	private IWorkbenchWindow window;

	public SensorControlAction(IWorkbenchWindow window) {
		this.window = window;
		this.setText(SystemUtil.SENSOR_CONTROL);
		this.setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageKeyManager.SENSOR_ICON));
	}

	@Override
	public void run() {
		//打开编辑器
		IEditorInput editorInput = SensorControlEditorInput.scei;
		IWorkbenchPage workbenchPage = window.getActivePage();
		// 通过editorinput来找到editor
		IEditorPart editorPart = workbenchPage.findEditor(editorInput);
		if (editorPart != null) {//已经打开了所需的编辑器
			workbenchPage.bringToTop(editorPart);
		} else {//没有打开就打开来
			try {
				String editorID = SensorControlEditor.ID;
				editorPart = workbenchPage.openEditor(editorInput, editorID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
