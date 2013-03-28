package com.rcp.wxh.dialogs;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * ��ʾͼƬ��Canvas
 * 
 * @author hjw
 */
public class ImageCanvas extends Canvas {

	private Image image;

	public ImageCanvas(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				drawImage(e.gc);
			}
		});
	}

	// ÿ���ػ�ʱ��Ҫ���õķ���
	private void drawImage(GC gc) {
		if (image == null) {
			return;
		}
		Rectangle imageBounds = image.getBounds();
		Rectangle drawingBounds = this.getBounds();

		// ��ͼƬ��ʾ��canvas�Ļ�ͼ������
		gc.drawImage(image, 0, 0, imageBounds.width, imageBounds.height, drawingBounds.x, drawingBounds.y,
				drawingBounds.width, drawingBounds.height);
	}

	public void setImage(Image image) {
		this.image = image;
		redraw();
	}

	public Image getImage() {
		return image;
	}

}
