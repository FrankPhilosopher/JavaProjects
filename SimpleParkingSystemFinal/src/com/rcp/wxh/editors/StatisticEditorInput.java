package com.rcp.wxh.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class StatisticEditorInput implements IEditorInput {

	public static final StatisticEditorInput sei = new StatisticEditorInput();

	public boolean exists() {
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "数据统计";
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return "数据统计";
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

}
