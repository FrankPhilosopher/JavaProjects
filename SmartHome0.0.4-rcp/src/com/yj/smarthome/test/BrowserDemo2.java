package com.yj.smarthome.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class BrowserDemo2 {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, true));

		final Browser browser;
		try {
			browser = new Browser(shell, SWT.NONE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			display.dispose();
			return;
		}
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		browser.setLayoutData(data);

		Button headersButton = new Button(shell, SWT.PUSH);
		headersButton.setText("Send custom headers");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		headersButton.setLayoutData(data);
		headersButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				browser.setUrl("http://www.ericgiguere.com/tools/http-header-viewer.html", null, new String[] { "User-agent: SWT Browser",
						"Custom-header: this is just a demo" });
			}
		});
		Button postDataButton = new Button(shell, SWT.PUSH);
		postDataButton.setText("Send post data");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		postDataButton.setLayoutData(data);
		postDataButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				browser.setUrl("https://bugs.eclipse.org/bugs/buglist.cgi",
						"emailassigned_to1=1&bug_severity=enhancement&bug_status=NEW&email1=platform-swt-inbox&emailtype1=substring", null);
			}
		});

		shell.setBounds(10, 10, 600, 600);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}