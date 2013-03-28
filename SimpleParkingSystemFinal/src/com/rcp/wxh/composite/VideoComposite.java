package com.rcp.wxh.composite;

import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import parkingsystem.Activator;
import platedsp.MyDSP;

/**
 * ��Ƶ��ʾģ��
 */
public class VideoComposite extends Composite {

	private Frame frame;
	private Canvas canvas;
	private ViewForm viewForm;
	private CardLayout cardLayout;
	private Composite videoComposite;
	private Panel videoPanel = new Panel();
	private Panel imagePanel = new Panel();
	private MyDSP dsp = new MyDSP();

	public VideoComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		viewForm = new ViewForm(this, SWT.NONE);
		viewForm.setLayout(new FillLayout());
		initImage();
		initVideo();
		viewForm.setContent(canvas);
	}

	// ��ʼ������ͷ����
	private void initVideo() {
		videoComposite = new Composite(viewForm, SWT.NONE);
		videoComposite.setLayout(new FillLayout());
		Composite composite_1 = new Composite(videoComposite, SWT.EMBEDDED);
		composite_1.setLayout(new FillLayout());

		frame = SWT_AWT.new_Frame(composite_1);
		cardLayout = new CardLayout();
		frame.setLayout(cardLayout);// ʹ�ÿ�Ƭ���ֹ�����
		frame.add(videoPanel, "video");
		frame.add(imagePanel, "image");// ����һ���հ׵�panel����������ʾ�����������������Ͳ�����ֹرպ����´�ʱ�Ĵ���

		dsp.Open("");
		dsp.ResetImageDisplayWindow(videoPanel);
		videoPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				dsp.Open("");
				dsp.ResetImageDisplayWindow(videoPanel);
//				dsp.AfterImageSizeChanged(panel.getWidth(), panel.getHeight());
			}
		});
	}

	// ��ʼ��ͼƬ����
	private void initImage() {
		canvas = new Canvas(viewForm, SWT.NONE);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				Image image = Activator.getImageDescriptor("icons/video.png").createImage();
				gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0,
						canvas.getBounds().width, canvas.getBounds().height);
			}
		});
	}

	public void dispose() {
		super.dispose();
		dsp.Close();
	}

	/**
	 * ��ʾ��Ƶ
	 */
	public void showVideo() {
		viewForm.setContent(videoComposite);
		cardLayout.show(frame, "video");
		dsp.VideoSetDeviceIndex(0);
		dsp.VideoSetConnected(1);
	}

	/**
	 * �ر���Ƶ
	 */
	public void closeVideo() {
		cardLayout.show(frame, "image");
		dsp.VideoSetConnected(0);
//		dsp.Close();
//		initVideo();
		viewForm.setContent(canvas);
	}

	/**
	 * ץȡͼƬ
	 */
	public boolean captureImage(String fileName) {
		if (videoPanel == null) {// TODO��yinger ����Ҳ���ǲ��еģ�����Ӧ����size
			return false;
		}
		try {
			Robot robot = new Robot();
			Rectangle rectangle = new Rectangle(videoPanel.getLocationOnScreen(), videoPanel.getSize());// getLocationOnScreen:�õ��������Ļ�ϵ�����λ�ã�
			java.awt.Image tempLocalImage = robot.createScreenCapture(rectangle);
//			String fileName = PATH_STRING + new Date().getTime() + ".jpg";// abstract pathname
			ImageIO.write((BufferedImage) tempLocalImage, "JPG", new File(fileName));
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * ������Ƶ�Ƿ�ת
	 */
	public void setImageTurnOver(boolean t) {
	}

	public MyDSP getDsp() {
		return dsp;
	}

	public void setDsp(MyDSP dsp) {
		this.dsp = dsp;
	}

}
