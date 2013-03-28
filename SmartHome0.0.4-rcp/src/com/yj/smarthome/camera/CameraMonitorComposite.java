package com.yj.smarthome.camera;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;

import javax.swing.tree.DefaultMutableTreeNode;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.examples.win32.W32API.HWND;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.yj.smarthome.communication.ServerCommunication;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.util.SystemUtil;

public class CameraMonitorComposite extends Composite {

	public CameraMonitorComposite(Composite parent, int style) {
		super(parent, style);
		initValues();//初始化参数
		createContents(this);//创建内容
	}

	private Text text_IP;
	private Text text_Port;
	private Text text_user;
	private Text text_password;

	private java.awt.Panel panelRealplay;//视频播放面板
	protected boolean bRealPlay;//是否正在播放

	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
	static PlayCtrl playControl = PlayCtrl.INSTANCE;

	HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//设备信息
	HCNetSDK.NET_DVR_IPPARACFG m_strIpparaCfg;//IP参数
	HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;//用户参数

	String m_sDeviceIP;//已登录设备的IP地址

	NativeLong lUserID;//用户句柄
	NativeLong lPreviewHandle;//预览句柄
	NativeLongByReference m_lPort;//回调预览时播放库端口指针

//	NativeLong lAlarmHandle;//报警布防句柄
//	NativeLong lListenHandle;//报警监听句柄

	int m_iTreeNodeNum;//通道树节点数目
	private Combo combo;
	private Button button_display;

	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			shell.setSize(800, 599);
			shell.setText("视频监控测试");
			CameraMonitorComposite demo = new CameraMonitorComposite(shell, SWT.NONE);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//初始化参数值
	private void initValues() {
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		if (initSuc != true) {
			MessageDialog.openError(new Shell(), "错误", "初始化 HCNetSDK 失败");
		}
		lUserID = new NativeLong(-1);
		lPreviewHandle = new NativeLong(-1);
//		lAlarmHandle = new NativeLong(-1);
//		lListenHandle = new NativeLong(-1);
		m_lPort = new NativeLongByReference(new NativeLong(-1));
		m_iTreeNodeNum = 0;
	}

	protected void createContents(Composite parent) {
//		setLayout(new RowLayout(SWT.HORIZONTAL));
		setLayout(new GridLayout(2, false));

		Composite composite = new Composite(parent, SWT.BORDER);
//		composite.setLayoutData(new RowData(SWT.DEFAULT, 540));
		composite.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		Label lblIp = new Label(composite, SWT.NONE);
		lblIp.setBounds(10, 40, 48, 17);
		lblIp.setText("IP\u5730\u5740");

		text_IP = new Text(composite, SWT.BORDER);
		text_IP.setText("192.168.0.60");
		text_IP.setBounds(10, 63, 116, 23);

		Label label = new Label(composite, SWT.NONE);
		label.setBounds(10, 92, 61, 17);
		label.setText("\u7AEF\u53E3\u53F7");

		text_Port = new Text(composite, SWT.BORDER);
		text_Port.setText("8000");
		text_Port.setBounds(10, 115, 116, 23);

		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setBounds(10, 144, 61, 17);
		label_1.setText("\u7528\u6237\u540D");

		text_user = new Text(composite, SWT.BORDER);
		text_user.setText("admin");
		text_user.setBounds(10, 167, 116, 23);

		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setBounds(10, 196, 61, 17);
		label_2.setText("\u5BC6\u7801");

		text_password = new Text(composite, SWT.BORDER);
		text_password.setText("12345");
		text_password.setBounds(10, 219, 116, 23);
		text_password.setEchoChar('*');

		Button button_reg = new Button(composite, SWT.NONE);
		button_reg.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {//注册
				register();
			}
		});
		button_reg.setBounds(10, 262, 80, 27);
		button_reg.setText("\u83B7\u53D6\u901A\u9053");

		combo = new Combo(composite, SWT.NONE);
		combo.setBounds(10, 326, 106, 25);

		Label label_3 = new Label(composite, SWT.NONE);
		label_3.setBounds(10, 303, 61, 17);
		label_3.setText("\u9009\u62E9\u901A\u9053");

		button_display = new Button(composite, SWT.NONE);
		button_display.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//预览
				preview();
			}
		});
		button_display.setBounds(10, 357, 80, 27);
		button_display.setText("\u9884\u89C8");

		Button btn_getIp = new Button(composite, SWT.NONE);
		btn_getIp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//获取IP地址
				try {
					requestCameraIp();
				} catch (Exception e1) {
					e1.printStackTrace();
					//提示错误
				}
			}
		});
		btn_getIp.setBounds(10, 10, 80, 27);
		btn_getIp.setText("\u83B7\u53D6IP\u5730\u5740");

//		Button button_stop = new Button(composite, SWT.NONE);
//		button_stop.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {//停止
//
//			}
//		});
//		button_stop.setBounds(10, 376, 80, 27);
//		button_stop.setText("\u505C\u6B62");

		Composite awtComposite = new Composite(parent, SWT.BORDER | SWT.EMBEDDED);
//		awtComposite.setLayoutData(new RowData(621, 558));
//		awtComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		awtComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
//		RowData rd_awtComposite = new RowData();
//		rd_awtComposite.height = 547;
//		rd_awtComposite.width = 600;
//		awtComposite.setLayoutData(rd_awtComposite);

		Frame frame = SWT_AWT.new_Frame(awtComposite);
//		frame.setLayout()
		Panel panel = new Panel();
		frame.add(panel);
		panel.setLayout(new BorderLayout());

		panelRealplay = new Panel();
		panel.add(panelRealplay, BorderLayout.CENTER);

		//界面参数
		text_user.setText(SystemUtil.LOGIN_USER.getName());
		text_password.setText(SystemUtil.LOGIN_USER.getPassword());
		text_IP.setText("192.168.0.60");
		text_Port.setText("8000");
		button_display.setEnabled(false);
	}

	//请求摄像头IP地址
	protected void requestCameraIp() throws Exception {
//		SystemUtil.communication.sendData(ProtocolUtil.packGetIp());
		//这里是远程服务器
//		new ServerCommunication(SystemUtil.Server_IP, SystemUtil.Server_Port).sendData(ProtocolUtil.packGetIp());
		ServerCommunication.getInstance().sendData(ProtocolUtil.packGetIp());
//		button_display.setEnabled(false);
//		GetIpThread getIpThread = new GetIpThread();
//		getIpThread.start();
	}

	//预览
	protected void preview() {
		if (lUserID.intValue() == -1) {
			MessageDialog.openError(new Shell(), "错误", "请先注册");
			return;
		}

		//如果预览窗口没打开,不在预览
		if (bRealPlay == false) {
			//获取窗口句柄
			HWND hwnd = new HWND(Native.getComponentPointer(panelRealplay));

			//获取通道号
			int iChannelNum = getChannelNumber();//通道号
			if (iChannelNum == -1) {
				MessageDialog.openWarning(new Shell(), "提示", "请选择要预览的通道");
				return;
			}

			m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
			m_strClientInfo.lChannel = new NativeLong(iChannelNum);

			//直接预览
			m_strClientInfo.hPlayWnd = hwnd;
			lPreviewHandle = hCNetSDK.NET_DVR_RealPlay_V30(lUserID, m_strClientInfo, null, null, true);

			long previewSucValue = lPreviewHandle.longValue();

			//预览失败时:
			if (previewSucValue == -1) {
				MessageDialog.openError(new Shell(), "错误", "预览失败");
				return;
			}

			//预览成功的操作
			button_display.setText("停止");
			bRealPlay = true;

		} else {//如果在预览,停止预览,关闭窗口
			hCNetSDK.NET_DVR_StopRealPlay(lPreviewHandle);
			button_display.setText("预览");
			bRealPlay = false;
			if (m_lPort.getValue().intValue() != -1) {
				playControl.PlayM4_Stop(m_lPort.getValue());
				m_lPort.setValue(new NativeLong(-1));
			}
			panelRealplay.repaint();
		}
	}

	//得到通道号
	private int getChannelNumber() {
		int iChannelNum = -1;
		String sChannelName = combo.getText();
		if (sChannelName != null) {
			if (sChannelName.charAt(0) == 'C') {//Camara开头表示模拟通道
				//子字符串中获取通道号
				iChannelNum = Integer.parseInt(sChannelName.substring(6));
			} else {
				if (sChannelName.charAt(0) == 'I') {//IPCamara开头表示IP通道
					//子字符创中获取通道号,IP通道号要加32
					iChannelNum = Integer.parseInt(sChannelName.substring(8)) + 32;
				} else {
					return -1;
				}
			}
		}
		return iChannelNum;
	}

	//注册
	protected void register() {
		//预览情况下不可注销
		if (bRealPlay) {
			MessageDialog.openWarning(new Shell(), "提示", "注册新用户请先停止当前预览!");
			return;
		}

		//先注销
		if (lUserID.longValue() > -1) {
			hCNetSDK.NET_DVR_Logout_V30(lUserID);
			lUserID = new NativeLong(-1);
			m_iTreeNodeNum = 0;
			combo.removeAll();
//			m_DeviceRoot.removeAllChildren();
		}

		//注册
		m_sDeviceIP = text_IP.getText().trim();//设备ip地址
		m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();//设备信息
		int iPort = Integer.parseInt(text_Port.getText().trim());
		lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP, (short) iPort, text_user.getText(), text_password.getText(), m_strDeviceInfo);
		long userID = lUserID.longValue();
		if (userID == -1) {
			m_sDeviceIP = "";//登录未成功,IP置为空
			MessageDialog.openError(new Shell(), "错误", "注册失败");
//			MessageBox box = new MessageBox(new Shell());
//			box.setMessage("注册失败！");
//			box.open();
		} else {
			CreateDeviceTree();
		}

	}

	//建立设备通道数
	private void CreateDeviceTree() {
		IntByReference ibrBytesReturned = new IntByReference(0);//获取IP接入配置参数
		boolean bRet = false;

		m_strIpparaCfg = new HCNetSDK.NET_DVR_IPPARACFG();
		m_strIpparaCfg.write();
		Pointer lpIpParaConfig = m_strIpparaCfg.getPointer();
		bRet = hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_IPPARACFG, new NativeLong(0), lpIpParaConfig,
				m_strIpparaCfg.size(), ibrBytesReturned);
		m_strIpparaCfg.read();

//		DefaultTreeModel TreeModel = ((DefaultTreeModel) jTreeDevice.getModel());//获取树模型
		if (!bRet) {
			//设备不支持,则表示没有IP通道
			for (int iChannum = 0; iChannum < m_strDeviceInfo.byChanNum; iChannum++) {
				String camerano = "Camera" + (iChannum + m_strDeviceInfo.byStartChan);
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(camerano);
				combo.add(camerano);
				combo.setData(camerano, newNode);
//				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Camera" + (iChannum + m_strDeviceInfo.byStartChan));
//				TreeModel.insertNodeInto(newNode, m_DeviceRoot, iChannum);
			}
		} else {
			//设备支持IP通道
			for (int iChannum = 0; iChannum < m_strDeviceInfo.byChanNum; iChannum++) {
				if (m_strIpparaCfg.byAnalogChanEnable[iChannum] == 1) {
					String camerano = "Camera" + (iChannum + m_strDeviceInfo.byStartChan);
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(camerano);
					combo.add(camerano);
					combo.setData(camerano, newNode);
					m_iTreeNodeNum++;
//					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Camera" + (iChannum + m_strDeviceInfo.byStartChan));
//					TreeModel.insertNodeInto(newNode, m_DeviceRoot, m_iTreeNodeNum);
				}
			}
			for (int iChannum = 0; iChannum < HCNetSDK.MAX_IP_CHANNEL; iChannum++)
				if (m_strIpparaCfg.struIPChanInfo[iChannum].byEnable == 1) {
					String camerano = "IPCamera" + (iChannum + m_strDeviceInfo.byStartChan);
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(camerano);
					combo.add(camerano);
					combo.setData(camerano, newNode);
					m_iTreeNodeNum++;
//					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Camera" + (iChannum + m_strDeviceInfo.byStartChan));
//					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("IPCamera" + (iChannum + m_strDeviceInfo.byStartChan));
//					TreeModel.insertNodeInto(newNode, m_DeviceRoot, m_iTreeNodeNum);
				}
		}
		combo.select(0);//默认选中第一个
		combo.update();
//		TreeModel.reload();//将添加的节点显示到界面
//		jTreeDevice.setSelectionInterval(1, 1);//选中第一个节点
	}

	//在退出时要做一定的处理
	@Override
	public void dispose() {
		//如果在预览,先停止预览, 释放句柄
		if (lPreviewHandle.longValue() > -1) {
			hCNetSDK.NET_DVR_StopRealPlay(lPreviewHandle);
		}

//		//报警撤防
//		if (lAlarmHandle.intValue() != -1) {
//			hCNetSDK.NET_DVR_CloseAlarmChan_V30(lAlarmHandle);
//		}
//		//停止监听
//		if (lListenHandle.intValue() != -1) {
//			hCNetSDK.NET_DVR_StopListen_V30(lListenHandle);
//		}

		//如果已经注册,注销
		if (lUserID.longValue() > -1) {
			hCNetSDK.NET_DVR_Logout_V30(lUserID);
		}
		//cleanup SDK
		hCNetSDK.NET_DVR_Cleanup();
		super.dispose();
	}

//	//获取IP地址的等待线程
//	class GetIpThread extends Thread {
//		@Override
//		public void run() {
//			synchronized (SystemUtil.message) {//锁住 message
//				while ("".equals(SystemUtil.message.toString())) {//没有信息
//					try {
//						System.out.println("waiting ip");
//						SystemUtil.message.wait();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			
//			System.out.println("getip");
//			if (!"".equalsIgnoreCase(SystemUtil.message.toString())) {//如果成功的话就没有message就是ip地址
//				Display.getDefault().asyncExec(new Runnable() {
//					public void run() {
//						MessageDialog.openInformation(Display.getDefault().getActiveShell(), "提示", SystemUtil.message.toString());
//						text_IP.setText(SystemUtil.message.toString());
//						SystemUtil.message = new StringBuffer("");//将信息设为空
//						return;//登录成功了，等待线程才可以返回结束
//					}
//				});
//			} else {
//				Display.getDefault().asyncExec(new Runnable() {
//					@Override
//					public void run() {
//						SystemUtil.message = new StringBuffer("");//将信息设为空
//						return;//这里不能return，不然线程就结束了
//					}
//				});
//			}
//		}
//	}

	public void setIp(String ip) {
		text_IP.setText(ip);
		button_display.setEnabled(true);
	}

}
