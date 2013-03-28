package com.yj.smarthome.camera;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.yj.smarthome.dialog.ImageDialog;
import com.yj.smarthome.http.HttpRequestComponment;

public class IpCameraMonitorComposite extends Composite {

	private Text text_ip;
	private Text txt_user;
	private Text text_pwd;
	private Text text_port;
	private Image curImage;
	private Canvas canvas;
	private HttpRequestComponment hrc;
	private VedioStreamThread captureImageThread;
	private boolean isDisplay = false;

	private int currentCommand = -1;
	private static final int DOWN = 0;
	private static final int STOPDOWN = 1;
	private static final int UP = 2;
	private static final int STOPUP = 3;
	private static final int RIGHT = 4;
	private static final int STOPRIGHT = 5;
	private static final int LEFT = 6;
	private static final int STOPLEFT = 7;
	private static final int UPDOWN = 26;
	private static final int STOPUPDOWN = 27;
	private static final int LEFTRIGHT = 28;
	private static final int STOPLEFTRIGHT = 29;

	public IpCameraMonitorComposite(Composite parent, int style) {
		super(parent, style);
//		setLayout(new GridLayout(1, false));
		hrc = new HttpRequestComponment(this);
		createContents(this);
	}

	//用于重画canvas
	class MypaintListener implements PaintListener {
		@Override
		public void paintControl(PaintEvent e) {
			System.out.println("************" + curImage);
			GC gc = e.gc;
			if (curImage != null)
				gc.drawImage(curImage, 0, 0, curImage.getBounds().width, curImage.getBounds().height, 0, 0, canvas.getBounds().width,
						canvas.getBounds().height);
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Composite parent) {
//		parent = new Shell();
//		parent.setSize(686, 486);
//		parent.setText("SWT Application");

		parent.setLayout(new GridLayout(2, false));

		canvas = new Canvas(parent, SWT.BORDER | SWT.DOUBLE_BUFFERED);
//		canvas.setBounds(10, 20, 481, 444);
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		canvas.addPaintListener(new MypaintListener());

		Composite composite_control = new Composite(this, SWT.TOP);
//		composite_control.setBounds(497, 20, 163, 444);
		composite_control.setLayoutData(new GridData(160, SWT.DEFAULT));
		composite_control.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		Label lblIp = new Label(composite_control, SWT.NONE);
		lblIp.setBounds(0, 0, 151, 13);
		lblIp.setText("IP\u5730\u5740\uFF1A");

		text_ip = new Text(composite_control, SWT.BORDER);
		text_ip.setBounds(0, 19, 163, 23);
		text_ip.setText("192.168.0.178");

		Label label_1 = new Label(composite_control, SWT.NONE);
		label_1.setBounds(0, 112, 151, 13);
		label_1.setText("\u7528\u6237\u540D:");
//		label_1.setFont(SWTResourceManager.getFont("华文新魏", 16, SWT.NORMAL));

		txt_user = new Text(composite_control, SWT.BORDER);
		txt_user.setBounds(0, 131, 163, 23);
		txt_user.setText("admin");

		Label label_2 = new Label(composite_control, SWT.NONE);
		label_2.setBounds(0, 168, 151, 13);
		label_2.setText("\u5BC6\u7801\uFF1A");
//		label_2.setFont(SWTResourceManager.getFont("华文新魏", 16, SWT.NORMAL));

		text_pwd = new Text(composite_control, SWT.BORDER | SWT.PASSWORD);
		text_pwd.setBounds(0, 187, 163, 23);
		text_pwd.setText("123456");

		Label label_3 = new Label(composite_control, SWT.NONE);
		label_3.setBounds(0, 56, 151, 13);
		label_3.setText("\u7AEF\u53E3\uFF1A");
//		label_3.setFont(SWTResourceManager.getFont("华文新魏", 16, SWT.NORMAL));

		text_port = new Text(composite_control, SWT.BORDER);
		text_port.setBounds(0, 75, 163, 23);
		text_port.setText("80");

		Button button_up = new Button(composite_control, SWT.NONE);
		button_up.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//向上
				if (isDisplay) {
					new CommandThread(getCommandUrl(UP)).start();
				}
			}
		});
		button_up.setBounds(54, 235, 51, 23);
		button_up.setText("\u4E0A");

		Button button_down = new Button(composite_control, SWT.NONE);
		button_down.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//向下
				if (isDisplay) {
					new CommandThread(getCommandUrl(DOWN)).start();
				}
			}
		});
		button_down.setBounds(54, 293, 51, 23);
		button_down.setText("\u4E0B");

		Button button_left = new Button(composite_control, SWT.NONE);
		button_left.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//向左
				if (isDisplay) {
					new CommandThread(getCommandUrl(LEFT)).start();
				}
			}
		});
		button_left.setBounds(10, 264, 51, 23);
		button_left.setText("\u5DE6");

		Button button_right = new Button(composite_control, SWT.NONE);
		button_right.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//向右
				if (isDisplay) {
					new CommandThread(getCommandUrl(RIGHT)).start();
				}
			}
		});
		button_right.setBounds(99, 264, 51, 23);
		button_right.setText("\u53F3");

		Button btn_updown = new Button(composite_control, SWT.NONE);
		btn_updown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//上下巡视
				if (isDisplay) {
					new CommandThread(getCommandUrl(UPDOWN)).start();
				}
			}
		});
		btn_updown.setBounds(0, 338, 71, 23);
		btn_updown.setText("\u4E0A\u4E0B\u5DE1\u89C6");

		Button btn_leftright = new Button(composite_control, SWT.NONE);
		btn_leftright.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//左右巡视
				if (isDisplay) {
					new CommandThread(getCommandUrl(LEFTRIGHT)).start();
				}
			}
		});
		btn_leftright.setBounds(89, 338, 74, 23);
		btn_leftright.setText("\u5DE6\u53F3\u5DE1\u89C6");

		final Button button_display = new Button(composite_control, SWT.NONE);
		button_display.setBounds(26, 381, 113, 27);
		button_display.setText("\u9884    \u89C8");
		button_display.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//进行 预览！
				if (isDisplay) {
					button_display.setText("预　　览");
					hrc.setConnect(false);
					isDisplay = false;
					if (captureImageThread != null) {
						captureImageThread.stopMe();
					}
				} else {
					button_display.setText("停　　止");
					hrc.setConnect(true);
					isDisplay = true;
					final String url = getVideoStreamUrl();
					captureImageThread = new VedioStreamThread(url);
					captureImageThread.start();
				}
			}
		});

		Button button_pic = new Button(composite_control, SWT.NONE);
		button_pic.setBounds(26, 414, 113, 27);
		button_pic.setText("\u62CD\u3000\u7167");
		button_pic.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//拍照
				if (isDisplay) {
					new CapturePicThread(getCapturePicUrl()).start();
				}
			}
		});

	}

	//设置图片，这个是异步执行的！因为它是在HttpRequestComponment中调用的
	public void setImage(final ByteBuffer bb) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (canvas != null && !canvas.isDisposed()) {
					ByteArrayInputStream bais = new ByteArrayInputStream(bb.array());
					JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(bais);
					InputStream ins = decoder.getInputStream();
					curImage = new Image(Display.getDefault(), ins);//如果这里是一直new Image的话会出现错误！no more handles！
//					curImage = image;
					canvas.redraw();
//					curImage.dispose();//这样子不行，会报错，Graphices disposed
				}
			}
		});
	}

	//获取视频流的线程
	class VedioStreamThread extends Thread {
		private volatile boolean finished = false;
		private String url;

		VedioStreamThread(String url) {
			this.url = url;
		}

		public void stopMe() {
			finished = true;
		}

		@Override
		public void run() {
			while (!finished) {
				try {
					hrc.getVideoStream(url);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}

	}

	//发送命令的线程
	class CommandThread extends Thread {
		private String url;

		CommandThread(String url) {
			this.url = url;
		}

		@Override
		public void run() {
			try {
				hrc.sendCommandGet(url);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	//实现拍照功能的线程
	class CapturePicThread extends Thread {
		private String url;

		CapturePicThread(String url) {
			this.url = url;
		}

		@Override
		public void run() {
			try {
				final Image image = hrc.capturePic(url);
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						ImageDialog dialog = new ImageDialog(Display.getDefault().getActiveShell(), image);
						dialog.open();
					}
				});
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	//销毁时要结束线程
	@Override
	public void dispose() {
		captureImageThread.stopMe();
		super.dispose();
	}

	//得到视频流的url地址
	public String getVideoStreamUrl() {
		String ip = text_ip.getText();
		String username = txt_user.getText();
		String pwd = text_pwd.getText();
		final String url = "http://" + ip + "/videostream.cgi?user=" + username + "&pwd=" + pwd + "&resolution=320*240&rate=3";
		System.out.println("getVideoStreamUrl:" + url);
		return url;
	}

	//得到命令的url地址
	public String getCommandUrl(int command) {
		String ip = text_ip.getText();
		String username = txt_user.getText();
		String pwd = text_pwd.getText();
		final String url = "http://" + ip + "/decoder_control.cgi?command=" + command + "&onestep=1&user=" + username + "&pwd=" + pwd;
		System.out.println("getCommandUrl:" + url);
		return url;
	}

	//得到截图命令的url地址
	public String getCapturePicUrl() {
		String ip = text_ip.getText();
		String username = txt_user.getText();
		String pwd = text_pwd.getText();
		final String url = "http://" + ip + "/snapshot.cgi?user=" + username + "&pwd=" + pwd;
		System.out.println("getCapturePicUrl:" + url);
		return url;
	}
}
