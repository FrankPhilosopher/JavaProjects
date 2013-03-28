package com.yj.smarthome.dialog;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;

/**
 * 显示图片的shell
 * 
 * @author yinger
 * 
 */
public class ImageDialog extends Dialog {
	private Image image;
	private Shell shell;
	private ViewForm viewForm;

	public ImageDialog(Shell parent, Image image) {
		super(parent);
		this.image = image;
	}

	public void open() {
		Display display = getParent().getDisplay();
		shell = new Shell(getParent(), SWT.SHELL_TRIM);// 删掉了 SWT.APPLICATION_MODAL 这种模式下只有关闭了它才可以继续操作
		shell.setText("\u67E5\u770B\u622A\u56FE");
		shell.setSize(640, 480);
		createContents();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	//创建内容区域
	private void createContents() {
		shell.setLayout(new FillLayout());
		viewForm = new ViewForm(shell, SWT.NONE);
		viewForm.setLayout(new FillLayout());

		// 添加编辑器的工具栏，包括了 打开，保存，操作 三个按钮
		ToolBar toolBar = new ToolBar(viewForm, SWT.FLAT);
		ToolBarManager toolBarManager = new ToolBarManager(toolBar);
		toolBarManager.add(new SaveImageAction());
		toolBarManager.update(true);
		viewForm.setTopLeft(toolBar);

		Composite composite = new Composite(viewForm, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
//		ImageCanvas canvas = new ImageCanvas(composite, SWT.NONE);
		final Canvas canvas = new Canvas(composite, SWT.DOUBLE_BUFFERED);
		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, canvas.getBounds().width,
						canvas.getBounds().height);
			}
		});
//		canvas.setImage(image);
		viewForm.setContent(composite);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	// 保存图像的操作
	public class SaveImageAction extends Action {
		public SaveImageAction() {
			setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "/icons/icos/save.ico"));
			setToolTipText("保存图片");
		}

		@Override
		public void run() {
			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { image.getImageData() };
			FileDialog fileDialog = new FileDialog(Display.getDefault().getShells()[0], SWT.SAVE);
			fileDialog.setFilterExtensions(new String[] { "*.jpg" });
			String filepath = fileDialog.open();
			if (filepath != null) {
				imageLoader.save(filepath, SWT.IMAGE_JPEG);// 保存为和源图像相同的格式
			}
		}
	}
}
