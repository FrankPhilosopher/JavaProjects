package mydip.dialog;

import mydip.widgets.ImageCanvas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ImageDialog extends Dialog {
	private Image image;

	public ImageDialog(Shell parent) {
		super(parent);
	}

	public void open() {
		Shell parent = getParent();
		Shell shell = new Shell(parent, SWT.SHELL_TRIM);// 删掉了 SWT.APPLICATION_MODAL 这种模式下只有关闭了它才可以继续操作
		shell.setText("Image Information");
		shell.setSize(400, 400);

		createContents(shell);

		shell.setLayout(new FillLayout());// 记住，一定要有layout！
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
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite_1.setLayoutData(new RowData(380, 190));

		final ImageCanvas canvas = new ImageCanvas(composite_1, SWT.NONE);
		canvas.setImage(image);

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite_2.setLayoutData(new RowData(380, 170));

		Group grpImageInformation = new Group(composite_2, SWT.NONE);
		grpImageInformation.setText("Image Information");

		Label lblType = new Label(grpImageInformation, SWT.NONE);
		lblType.setBounds(10, 42, 61, 17);
		lblType.setText("Type:");

		Text textType = new Text(grpImageInformation, SWT.BORDER);
		textType.setBounds(77, 36, 73, 23);
		textType.setText(String.valueOf(image.getImageData().type));

		Label lblWidth = new Label(grpImageInformation, SWT.NONE);
		lblWidth.setBounds(10, 97, 61, 17);
		lblWidth.setText("Width:");

		Text textWidth = new Text(grpImageInformation, SWT.BORDER);
		textWidth.setBounds(77, 91, 73, 23);
		textWidth.setText(String.valueOf(image.getImageData().width));

		Text textHeight = new Text(grpImageInformation, SWT.BORDER);
		textHeight.setBounds(77, 120, 73, 23);
		textHeight.setText(String.valueOf(image.getImageData().height));

		Label lblDepth = new Label(grpImageInformation, SWT.NONE);
		lblDepth.setBounds(10, 68, 61, 17);
		lblDepth.setText("Depth:");

		Text textDepth = new Text(grpImageInformation, SWT.BORDER);
		textDepth.setBounds(77, 65, 73, 23);
		textDepth.setText(String.valueOf(image.getImageData().depth));

		Label lblHeight = new Label(grpImageInformation, SWT.NONE);
		lblHeight.setText("Height:");
		lblHeight.setBounds(10, 123, 61, 17);

	}
}
