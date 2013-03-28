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
 * 视频显示模块
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

	// 初始化摄像头部分
	private void initVideo() {
		videoComposite = new Composite(viewForm, SWT.NONE);
		videoComposite.setLayout(new FillLayout());
		Composite composite_1 = new Composite(videoComposite, SWT.EMBEDDED);
		composite_1.setLayout(new FillLayout());

		frame = SWT_AWT.new_Frame(composite_1);
		cardLayout = new CardLayout();
		frame.setLayout(cardLayout);// 使用卡片布局管理器
		frame.add(videoPanel, "video");
		frame.add(imagePanel, "image");// 增加一个空白的panel，它不会显示出来，但是有了它就不会出现关闭后重新打开时的错误！

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

	// 初始化图片部分
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
	 * 显示视频
	 */
	public void showVideo() {
		viewForm.setContent(videoComposite);
		cardLayout.show(frame, "video");
		dsp.VideoSetDeviceIndex(0);
		dsp.VideoSetConnected(1);
	}

	/**
	 * 关闭视频
	 */
	public void closeVideo() {
		cardLayout.show(frame, "image");
		dsp.VideoSetConnected(0);
//		dsp.Close();
//		initVideo();
		viewForm.setContent(canvas);
	}

	/**
	 * 抓取图片
	 */
	public boolean captureImage(String fileName) {
		if (videoPanel == null) {// TODO：yinger 这样也还是不行的，或许应该用size
			return false;
		}
		try {
			Robot robot = new Robot();
			Rectangle rectangle = new Rectangle(videoPanel.getLocationOnScreen(), videoPanel.getSize());// getLocationOnScreen:得到组件在屏幕上的坐标位置！
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
	 * 设置视频是否翻转
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
