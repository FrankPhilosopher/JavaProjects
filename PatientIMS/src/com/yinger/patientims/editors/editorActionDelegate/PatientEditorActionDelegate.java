package com.yinger.patientims.editors.editorActionDelegate;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import com.yinger.patientims.sigleton.PatientFactory;
import com.yinger.patientims.wizards.AddPatientInfoWizard;

/**
 * 编辑器动作的代理类
 * 
 * 具体的设置在plugin.xml文件中
 * 设置了action在菜单栏，工具栏的位置，以及要调用的动作代理类（也就是本类）
 * 它不是一个action，但是作用相当于一个action，它可以关联到一个编辑器
 * 当这个编辑器editor激活时，相应的动作action就会呈现出来，具体的执行操作还是在这里实现的
 * 
 * TODO:内有一个IEditorPart对象，但是具体的作用？
 *
 */
public class PatientEditorActionDelegate implements IEditorActionDelegate {
	
	private IEditorPart editorPart;

	@Override
	public void run(IAction action) {
		//创建自定义的向导
		AddPatientInfoWizard wizard = new AddPatientInfoWizard();
		//注意：每次打开向导页面时都要对PatientFactory的instance进行设置，不然会出现紊乱
		PatientFactory.setInstance(null);	
		//显示向导对话框
		WizardDialog dialog = new WizardDialog(Display.getDefault().getShells()[0], wizard);
		//设置对话框大小
		dialog.setPageSize(-1, 150);//注意：高度要足够的大
		//打开
		dialog.open();	
//		editorPart.getEditorInput().
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.editorPart = targetEditor;
	}

}
