package com.yj.smarthome.editorInput;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.yj.smarthome.util.SystemUtil;

/**
 * µÆ¿Ø±à¼­Æ÷input
 * 
 * @author yinger
 * 
 */
public class ApplianceControlEditorInput implements IEditorInput {

	public static final ApplianceControlEditorInput acei = new ApplianceControlEditorInput();

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {

		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return null;
	}

	@Override
	public String getName() {

		return SystemUtil.APPLIANCE_CONTROL;
	}

	@Override
	public IPersistableElement getPersistable() {

		return null;
	}

	@Override
	public String getToolTipText() {

		return SystemUtil.APPLIANCE_CONTROL;
	}

}
