package com.yj.smarthome.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.editor.SceneModeEditor;
import com.yj.smarthome.editorInput.SceneModeEditorInput;
import com.yj.smarthome.util.SystemUtil;

public class SceneModeAction extends Action {

	public static final String ID = SceneModeAction.class.getName();
	private IWorkbenchWindow window;

	public SceneModeAction(IWorkbenchWindow window) {
		this.window = window;
		this.setText(SystemUtil.SCENEMODE);
		this.setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageKeyManager.SENCEMODE_ICON));
	}

	@Override
	public void run() {
		//打开编辑器
		IEditorInput editorInput = SceneModeEditorInput.smei;
		IWorkbenchPage workbenchPage = window.getActivePage();
		// 通过editorinput来找到editor
		IEditorPart editorPart = workbenchPage.findEditor(editorInput);
		if (editorPart != null) {//已经打开了所需的编辑器
			workbenchPage.bringToTop(editorPart);
		} else {//没有打开就打开来
			try {
				String editorID = SceneModeEditor.ID;
				editorPart = workbenchPage.openEditor(editorInput, editorID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
