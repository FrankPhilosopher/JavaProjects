package com.yinger.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.resource.SWTResourceManager;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class AboutDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Label label_aboutimg;
	private Label label_myblog;
	private Link link_blog;

	private String aboutMsg;
	private Label label_about;

	/**
	 * Auto-generated main method to display this org.eclipse.swt.widgets.Dialog
	 * inside a new Shell.
	 */
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			AboutDialog inst = new AboutDialog(shell, SWT.NULL, "");
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AboutDialog(Shell parent, int style, String message) {
		super(parent, style);
		this.aboutMsg = message;
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			{
				SWTResourceManager.registerResourceUser(dialogShell);
			}

			dialogShell.setLayout(new FormLayout());
			dialogShell.setImage(SWTResourceManager.getImage("images/Home.ico"));
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(361, 224);
			dialogShell.setText("About");
			{
				label_about = new Label(dialogShell, SWT.NONE);
				FormData label_aboutLData = new FormData();
				label_aboutLData.width = 222;
				label_aboutLData.height = 130;
				label_aboutLData.left = new FormAttachment(0, 1000, 86);
				label_aboutLData.top = new FormAttachment(0, 1000, 12);
				label_about.setLayoutData(label_aboutLData);
				label_about.setText(aboutMsg);
			}
			{
				label_myblog = new Label(dialogShell, SWT.NONE);
				label_myblog.setText("MyBlog:");
				FormData label_blogLData = new FormData();
				label_blogLData.width = 54;
				label_blogLData.height = 17;
				label_blogLData.left = new FormAttachment(0, 1000, 86);
				label_blogLData.top = new FormAttachment(0, 1000, 154);
				label_myblog.setLayoutData(label_blogLData);

			}
			{
				link_blog = new Link(dialogShell, SWT.NONE);
				link_blog.setText("<a href=\"http://www.cnblogs.com/yinger\">http://www.cnblogs.com/yinger</a>");
				FormData link_blogLData = new FormData();
				link_blogLData.width = 182;
				link_blogLData.height = 17;
				link_blogLData.left = new FormAttachment(0, 1000, 139);
				link_blogLData.top = new FormAttachment(0, 1000, 154);
				link_blogLData.right = new FormAttachment(1000, 1000, -24);
				link_blogLData.bottom = new FormAttachment(1000, 1000, -15);
				link_blog.setLayoutData(link_blogLData);
				link_blog.setToolTipText("\u6211\u7684\u535a\u5ba2\u5730\u5740");
				link_blog.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						link_blogWidgetSelected(evt);
					}
				});
			}

			{
				label_aboutimg = new Label(dialogShell, SWT.NONE);
				FormData label_aboutLData = new FormData();
				label_aboutLData.width = 44;
				label_aboutLData.height = 76;
				label_aboutLData.left = new FormAttachment(0, 1000, 18);
				label_aboutLData.top = new FormAttachment(0, 1000, 43);
				label_aboutLData.right = new FormAttachment(1000, 1000, -283);
				label_aboutLData.bottom = new FormAttachment(1000, 1000, -67);
				label_aboutimg.setLayoutData(label_aboutLData);
				label_aboutimg.setImage(SWTResourceManager.getImage("images/Download.ico"));
			}
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void link_blogWidgetSelected(SelectionEvent evt) {
		System.out.println("link_blog.widgetSelected, event=" + evt);
		// 第一个使用默认的浏览器打开
		try {
//			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://www.cnblogs.com/yinger");
			Runtime.getRuntime().exec("explorer.exe http://www.cnblogs.com/yinger" );
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 第二个，IE程序打开 [我测试的结果是依然是使用默认的浏览器]
		// Runtime.getRuntime().exec("explorer.exe http://www.cnblogs.com/yinger" );
	}

}
