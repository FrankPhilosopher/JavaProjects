package com.yj.smarthome.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class YingerCameraTest extends Shell {
	private Text text_url;
	private Browser browser;
	private Composite composite;
	private Composite composite_browser;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			YingerCameraTest shell = new YingerCameraTest(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public YingerCameraTest(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new GridLayout(1, false));

		composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));

		text_url = new Text(composite, SWT.BORDER);
		text_url.setBounds(73, 4, 480, 21);
//		text_url.setText("http://192.168.0.178/videostream.cgi?user=admin&pwd=123456&resolvtion=320*240&rate=11");
		text_url.setText("http://192.168.0.178/videostream.asf?user=admin&pwd=123456&resolution=320*240&rate=11");

		Button button_go = new Button(composite, SWT.NONE);
		button_go.setBounds(559, 0, 107, 25);
		button_go.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//½øÈëÍøÖ·
				browser.setUrl(text_url.getText().trim());
			}
		});
		button_go.setText("\u8FDB\u53BB\u770B\u770B\u5427\uFF01");

		Label label = new Label(composite, SWT.NONE);
		label.setBounds(0, 10, 55, 15);
		label.setText("\u89C6\u9891\u7F51\u5740\uFF1A");

		composite_browser = new Composite(this, SWT.BORDER);
//		composite_browser.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		composite_browser.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite_browser.setLayout(new GridLayout(1, false));

		browser = new Browser(composite_browser, SWT.NONE);
		browser.setLayoutData(new GridData(GridData.FILL_BOTH));
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("\u6D4B\u8BD5SWT\u6D4F\u89C8\u5668\u4E2D\u64AD\u653E\u89C6\u9891");
		setSize(700, 480);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
