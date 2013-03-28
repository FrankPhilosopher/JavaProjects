package com.yj.smarthome.test;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class TestGroup {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TestGroup window = new TestGroup();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		shell.setSize(518, 375);
		shell.setText("SWT Application");

		Group grpgroup = new Group(shell, SWT.NONE);
		grpgroup.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		grpgroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				MessageDialog.openInformation(shell, "hahh", "你点击了组件");
			}
		});
		grpgroup.setText("\u6D4B\u8BD5Group\u7EC4\u4EF6");
		grpgroup.setBounds(55, 58, 291, 191);

	}
}
