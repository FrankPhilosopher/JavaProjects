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
import com.yj.smarthome.editor.LightControlEditor;
import com.yj.smarthome.editorInput.LightControlEditorInput;
import com.yj.smarthome.util.SystemUtil;

/**
 * 灯光控制的action
 * 
 * @author yinger
 * 
 */
public class LightControlAction extends Action implements IWorkbenchAction {

	public static final String ID = LightControlAction.class.getName();
	private IWorkbenchWindow window;

	public LightControlAction(IWorkbenchWindow window) {
		this.window = window;
//		setText("灯关控制");
//		setToolTipText("灯关控制");
		setToolTipText(SystemUtil.LIGHT_CONTROL);
//		setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));

		//这个设置了貌似没用，会产生错误！
//		setHoverImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageManager.LightIconHover));
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageKeyManager.LIGHT_ICON));
	}

	@Override
	public void run() {
		//打开编辑器
		IEditorInput editorInput = LightControlEditorInput.lcei;
		IWorkbenchPage workbenchPage = window.getActivePage();
		// 通过editorinput来找到editor
		IEditorPart editorPart = workbenchPage.findEditor(editorInput);
		if (editorPart != null) {//已经打开了所需的编辑器
			workbenchPage.bringToTop(editorPart);
		} else {//没有打开就打开来
			try {
				String editorID = LightControlEditor.ID;
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
