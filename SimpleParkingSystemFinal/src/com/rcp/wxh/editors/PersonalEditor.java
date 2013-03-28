package com.rcp.wxh.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.rcp.wbw.skin.LookAndFeel;

/**
 * 
 * 个人信息管理编辑器
 * 
 * @author wuxuehong 2011-11-2
 * 
 */
public class PersonalEditor extends EditorPart {
	public PersonalEditor() {
	}

	public static final String ID = PersonalEditor.class.getName();

	public void doSave(IProgressMonitor monitor) {

	}

	public void doSaveAs() {
	}

	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	public boolean isDirty() {
		return false;
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	public void createPartControl(Composite parent) {
		parent.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		parent.setBackgroundMode(SWT.INHERIT_FORCE);

	}

	public void setFocus() {
	}

}
