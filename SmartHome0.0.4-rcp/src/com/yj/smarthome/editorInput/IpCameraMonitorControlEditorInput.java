package com.yj.smarthome.editorInput;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.yj.smarthome.util.SystemUtil;

/**
 * IPÉãÏñÍ·ÊÓÆµ¼à¿Ø±à¼­Æ÷input
 * 
 * @author yinger
 * 
 */
public class IpCameraMonitorControlEditorInput implements IEditorInput {

	public static final IpCameraMonitorControlEditorInput ipcmcei = new IpCameraMonitorControlEditorInput();

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

		return SystemUtil.IPCAMERAMONITOR_CONTROL;
	}

	@Override
	public IPersistableElement getPersistable() {

		return null;
	}

	@Override
	public String getToolTipText() {

		return SystemUtil.IPCAMERAMONITOR_CONTROL;
	}

}
