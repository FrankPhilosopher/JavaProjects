package com.rcp.wxh.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ExpenseEditorInput implements IEditorInput {

	public static final ExpenseEditorInput eei = new ExpenseEditorInput();

	private ExpenseEditorInput() {
	}

	public boolean exists() {
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "缴费记录管理";
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return "缴费记录管理";
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

}
