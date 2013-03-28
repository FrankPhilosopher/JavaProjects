package com.rcp.wxh.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * 车辆出入记录 input
 * 
 * @author wuxuehong 2011-11-16
 * 
 */
public class CarRecordInput implements IEditorInput {

	public static final CarRecordInput cri = new CarRecordInput();

	public boolean exists() {
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "车辆出入记录";
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return "车辆出入记录信息";
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

}
