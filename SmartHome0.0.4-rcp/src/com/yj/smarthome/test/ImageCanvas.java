package com.yj.smarthome.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * 改进版本的ImageViewer类 去掉了属性：stayClosed
 */
public class ImageCanvas extends Canvas {

	private Image image;
	protected boolean showingEnlargedImage = false;// 是否显示大图像

	public ImageCanvas(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	public ImageCanvas(Composite parent, int style, Image image) {
		super(parent, style);
		this.image = image;
		initialize();
	}

	private void initialize() {
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				drawImage(e.gc);
			}
		});
		addListener(SWT.MouseDoubleClick, new Listener() {
			public void handleEvent(Event event) {
				showEnlargedImage();
			}
		});
	}

	// 每次重画时都要调用的方法
	private void drawImage(GC gc) {
		if (image == null)
			return;
		Rectangle imageBounds = image.getBounds();
		Rectangle drawingBounds = getDrawingBounds();

		// 将图片显示在canvas的绘图区域中
		gc.drawImage(image, 0, 0, imageBounds.width, imageBounds.height, drawingBounds.x, drawingBounds.y, drawingBounds.width,
				drawingBounds.height);
	}

	// 得到一个最适合显示这个图片的位置
	private Rectangle getDrawingBounds() {
		Rectangle imageBounds = image.getBounds();
		Rectangle canvasBounds = getBounds();

		// 计算出水平和竖直方向的比例
		double hScale = (double) canvasBounds.width / imageBounds.width;
		double vScale = (double) canvasBounds.height / imageBounds.height;

		double scale = Math.min(1.0d, Math.min(hScale, vScale));// 加入1.0d保证图像不会放大

		int width = (int) (imageBounds.width * scale);// 图像改变大小
		int height = (int) (imageBounds.height * scale);

		int x = (canvasBounds.width - width) / 2;// 让图像居中显示
		int y = (canvasBounds.height - height) / 2;

		return new Rectangle(x, y, width, height);
	}

	private void showEnlargedImage() {
		if (showingEnlargedImage)
			return;// 如果不要显示放大的图像，那么就退出
		if (image == null)
			return;

		final Rectangle imageBounds = image.getBounds();
		Rectangle canvasBounds = getBounds();

		// 如果图像不大，就没有放大的必要
		if (imageBounds.width < canvasBounds.width & imageBounds.height < canvasBounds.height)
			return;

		Rectangle displayBounds = getDisplay().getBounds();
		int x = (canvasBounds.width - imageBounds.width) / 2;
		int y = (canvasBounds.height - imageBounds.height) / 2;
		Point where = toDisplay(new Point(x, y));

		x = Math.max(0, Math.min(where.x, displayBounds.width - imageBounds.width));
		y = Math.max(0, Math.min(where.y, displayBounds.height - imageBounds.height));

		// final Shell shell = new Shell(getShell(), SWT.NO_TRIM);
		final Shell shell = new Shell(getShell(), SWT.SHELL_TRIM);
		shell.setBounds(x - 1, y - 1, imageBounds.width + 2, imageBounds.height + 2);
		// 显示
		shell.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, 1, 1);
				e.gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
				// e.gc.drawRectangle(0, 0, imageBounds.width+1, imageBounds.height+1);
			}
		});
		shell.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event event) {
				shell.close();
			}
		});
		shell.open();
	}

	//设置要显示的图像
	public void setImage(Image image) {
		this.image = image;
		redraw();
	}

	public Image getImage() {
		return image;
	}

}
