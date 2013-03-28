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
		//�򿪱༭��
		IEditorInput editorInput = SensorControlEditorInput.scei;
		IWorkbenchPage workbenchPage = window.getActivePage();
		// ͨ��editorinput���ҵ�editor
		IEditorPart editorPart = workbenchPage.findEditor(editorInput);
		if (editorPart != null) {//�Ѿ���������ı༭��
			workbenchPage.bringToTop(editorPart);
		} else {//û�д򿪾ʹ���
			try {
				String editorID = SensorControlEditor.ID;
				editorPart = workbenchPage.openEditor(editorInput, editorID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
