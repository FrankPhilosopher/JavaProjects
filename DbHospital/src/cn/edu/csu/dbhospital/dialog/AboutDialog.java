package cn.edu.csu.dbhospital.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class AboutDialog extends Dialog {

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AboutDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());
		
		Canvas canvas = new Canvas(container, SWT.NONE);
		canvas.setBackgroundImage(ResourceManager.getPluginImage("cn.edu.csu.dbhospital", "icons/hospital.jpg"));
		FormData fd_canvas = new FormData();
		fd_canvas.top = new FormAttachment(0, 22);
		fd_canvas.left = new FormAttachment(0, 10);
		fd_canvas.bottom = new FormAttachment(100, -29);
		fd_canvas.right = new FormAttachment(0, 222);
		canvas.setLayoutData(fd_canvas);
		
		Composite composite = new Composite(container, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(0, 186);
		fd_composite.right = new FormAttachment(canvas, 162, SWT.RIGHT);
		fd_composite.top = new FormAttachment(0, 22);
		fd_composite.left = new FormAttachment(canvas, 6);
		composite.setLayoutData(fd_composite);
		
		Label label = new Label(composite, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		label.setBounds(10, 20, 115, 17);
		label.setText("\u4F5C\u8005\uFF1A\u80E1\u5BB6\u5A01");
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_MAGENTA));
		label_1.setBounds(10, 58, 136, 17);
		label_1.setText("\u65F6\u95F4\uFF1A2013-1-17");
		
		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setForeground(SWTResourceManager.getColor(153, 50, 204));
		label_2.setBounds(10, 98, 136, 17);
		label_2.setText("\u7248\u672C\uFF1A1.0\u7248\u672C");
		
		Label label_3 = new Label(composite, SWT.NONE);
		label_3.setForeground(SWTResourceManager.getColor(238, 130, 238));
		label_3.setBounds(10, 137, 136, 17);
		label_3.setText("\u8F6F\u4EF6\uFF1A\u5999\u624B\u533B\u9662\u6302\u53F7\u7CFB\u7EDF");

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		button.setText("\u786E\u5B9A");
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(400, 300);
	}
}
