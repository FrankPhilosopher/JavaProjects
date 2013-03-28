package com.rcp.wxh.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/*
 * yinger
 */
public class ImageDialog extends Dialog {
	private Image image;

	public ImageDialog(Shell parent) {
		super(parent);
	}

	public void open() {
		Shell parent = getParent();
		Shell shell = new Shell(parent, SWT.SHELL_TRIM);// ɾ���� SWT.APPLICATION_MODAL ����ģʽ��ֻ�йر������ſ��Լ�������
		shell.setText("�볡ͼƬ");
		shell.setSize(400, 400);

		createContents(shell);

		shell.setLayout(new FillLayout());// ��ס��һ��Ҫ��layout��
		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	private void createContents(Shell shell) {
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());

		final ImageCanvas canvas = new ImageCanvas(composite, SWT.NONE);
		canvas.setImage(image);
		canvas.setLayout(new FillLayout());

	}
}
