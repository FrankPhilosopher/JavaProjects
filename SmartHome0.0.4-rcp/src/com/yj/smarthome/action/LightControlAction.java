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
 * �ƹ���Ƶ�action
 * 
 * @author yinger
 * 
 */
public class LightControlAction extends Action implements IWorkbenchAction {

	public static final String ID = LightControlAction.class.getName();
	private IWorkbenchWindow window;

	public LightControlAction(IWorkbenchWindow window) {
		this.window = window;
//		setText("�ƹؿ���");
//		setToolTipText("�ƹؿ���");
		setToolTipText(SystemUtil.LIGHT_CONTROL);
//		setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));

		//���������ò��û�ã����������
//		setHoverImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageManager.LightIconHover));
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, ImageKeyManager.LIGHT_ICON));
	}

	@Override
	public void run() {
		//�򿪱༭��
		IEditorInput editorInput = LightControlEditorInput.lcei;
		IWorkbenchPage workbenchPage = window.getActivePage();
		// ͨ��editorinput���ҵ�editor
		IEditorPart editorPart = workbenchPage.findEditor(editorInput);
		if (editorPart != null) {//�Ѿ���������ı༭��
			workbenchPage.bringToTop(editorPart);
		} else {//û�д򿪾ʹ���
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
