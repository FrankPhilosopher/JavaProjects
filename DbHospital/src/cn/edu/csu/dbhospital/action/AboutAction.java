package cn.edu.csu.dbhospital.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;

import cn.edu.csu.dbhospital.Activator;
import cn.edu.csu.dbhospital.dialog.AboutDialog;

/**
 * 
 * ����action
 * 
 */
public class AboutAction extends Action {

	private final IWorkbenchWindow window;

	public AboutAction(IWorkbenchWindow window) {
		this.window = window;
		// ���ò˵����ı��������ò˵�����ӿ�ݼ��Լ�����
		this.setText("����");
		// ����������ʾ����Ϣ
		this.setToolTipText("����");
		// ��ӹ�����ͼ�ΰ�ť
		this.setImageDescriptor(Activator.getImageDescriptor("/icons/Home.ico"));
	}

	public void run() {
		if (window != null) {
			AboutDialog aboutDialog = new AboutDialog(window.getShell());
			aboutDialog.open();
		}
	}

}
