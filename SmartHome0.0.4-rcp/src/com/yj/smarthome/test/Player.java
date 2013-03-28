package com.yj.smarthome.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Player {
	protected Shell shell;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Player window = new Player();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window
	 */
	public void open() {
		final Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		System.out.println("OK");
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setLayout(new FillLayout());
		shell.setSize(500, 375);
		shell.setText("SWT Application");
		final WMP composite = new WMP(shell, SWT.NONE);
//		composite.play("New Stories (Highway Blues).wma");   
//		composite.play("http://192.168.0.178/videostream.asf?user=admin&pwd=123456&resolution=320*240&rate=11");
		composite.play("C:/test.asf");
	}
}
