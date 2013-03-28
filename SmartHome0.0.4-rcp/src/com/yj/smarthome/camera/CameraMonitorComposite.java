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
		initValues();//��ʼ������
		createContents(this);//��������
	}

	private Text text_IP;
	private Text text_Port;
	private Text text_user;
	private Text text_password;

	private java.awt.Panel panelRealplay;//��Ƶ�������
	protected boolean bRealPlay;//�Ƿ����ڲ���

	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
	static PlayCtrl playControl = PlayCtrl.INSTANCE;

	HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//�豸��Ϣ
	HCNetSDK.NET_DVR_IPPARACFG m_strIpparaCfg;//IP����
	HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;//�û�����

	String m_sDeviceIP;//�ѵ�¼�豸��IP��ַ

	NativeLong lUserID;//�û����
	NativeLong lPreviewHandle;//Ԥ�����
	NativeLongByReference m_lPort;//�ص�Ԥ��ʱ���ſ�˿�ָ��

//	NativeLong lAlarmHandle;//�����������
//	NativeLong lListenHandle;//�����������

	int m_iTreeNodeNum;//ͨ�����ڵ���Ŀ
	private Combo combo;
	private Button button_display;

	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			shell.setSize(800, 599);
			shell.setText("��Ƶ��ز���");
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

	//��ʼ������ֵ
	private void initValues() {
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		if (initSuc != true) {
			MessageDialog.openError(new Shell(), "����", "��ʼ�� HCNetSDK ʧ��");
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
			public void widgetSelected(SelectionEvent e) {//ע��
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
			public void widgetSelected(SelectionEvent e) {//Ԥ��
				preview();
			}
		});
		button_display.setBounds(10, 357, 80, 27);
		button_display.setText("\u9884\u89C8");

		Button btn_getIp = new Button(composite, SWT.NONE);
		btn_getIp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//��ȡIP��ַ
				try {
					requestCameraIp();
				} catch (Exception e1) {
					e1.printStackTrace();
					//��ʾ����
				}
			}
		});
		btn_getIp.setBounds(10, 10, 80, 27);
		btn_getIp.setText("\u83B7\u53D6IP\u5730\u5740");

//		Button button_stop = new Button(composite, SWT.NONE);
//		button_stop.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {//ֹͣ
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

		//�������
		text_user.setText(SystemUtil.LOGIN_USER.getName());
		text_password.setText(SystemUtil.LOGIN_USER.getPassword());
		text_IP.setText("192.168.0.60");
		text_Port.setText("8000");
		button_display.setEnabled(false);
	}

	//��������ͷIP��ַ
	protected void requestCameraIp() throws Exception {
//		SystemUtil.communication.sendData(ProtocolUtil.packGetIp());
		//������Զ�̷�����
//		new ServerCommunication(SystemUtil.Server_IP, SystemUtil.Server_Port).sendData(ProtocolUtil.packGetIp());
		ServerCommunication.getInstance().sendData(ProtocolUtil.packGetIp());
//		button_display.setEnabled(false);
//		GetIpThread getIpThread = new GetIpThread();
//		getIpThread.start();
	}

	//Ԥ��
	protected void preview() {
		if (lUserID.intValue() == -1) {
			MessageDialog.openError(new Shell(), "����", "����ע��");
			return;
		}

		//���Ԥ������û��,����Ԥ��
		if (bRealPlay == false) {
			//��ȡ���ھ��
			HWND hwnd = new HWND(Native.getComponentPointer(panelRealplay));

			//��ȡͨ����
			int iChannelNum = getChannelNumber();//ͨ����
			if (iChannelNum == -1) {
				MessageDialog.openWarning(new Shell(), "��ʾ", "��ѡ��ҪԤ����ͨ��");
				return;
			}

			m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
			m_strClientInfo.lChannel = new NativeLong(iChannelNum);

			//ֱ��Ԥ��
			m_strClientInfo.hPlayWnd = hwnd;
			lPreviewHandle = hCNetSDK.NET_DVR_RealPlay_V30(lUserID, m_strClientInfo, null, null, true);

			long previewSucValue = lPreviewHandle.longValue();

			//Ԥ��ʧ��ʱ:
			if (previewSucValue == -1) {
				MessageDialog.openError(new Shell(), "����", "Ԥ��ʧ��");
				return;
			}

			//Ԥ���ɹ��Ĳ���
			button_display.setText("ֹͣ");
			bRealPlay = true;

		} else {//�����Ԥ��,ֹͣԤ��,�رմ���
			hCNetSDK.NET_DVR_StopRealPlay(lPreviewHandle);
			button_display.setText("Ԥ��");
			bRealPlay = false;
			if (m_lPort.getValue().intValue() != -1) {
				playControl.PlayM4_Stop(m_lPort.getValue());
				m_lPort.setValue(new NativeLong(-1));
			}
			panelRealplay.repaint();
		}
	}

	//�õ�ͨ����
	private int getChannelNumber() {
		int iChannelNum = -1;
		String sChannelName = combo.getText();
		if (sChannelName != null) {
			if (sChannelName.charAt(0) == 'C') {//Camara��ͷ��ʾģ��ͨ��
				//���ַ����л�ȡͨ����
				iChannelNum = Integer.parseInt(sChannelName.substring(6));
			} else {
				if (sChannelName.charAt(0) == 'I') {//IPCamara��ͷ��ʾIPͨ��
					//���ַ����л�ȡͨ����,IPͨ����Ҫ��32
					iChannelNum = Integer.parseInt(sChannelName.substring(8)) + 32;
				} else {
					return -1;
				}
			}
		}
		return iChannelNum;
	}

	//ע��
	protected void register() {
		//Ԥ������²���ע��
		if (bRealPlay) {
			MessageDialog.openWarning(new Shell(), "��ʾ", "ע�����û�����ֹͣ��ǰԤ��!");
			return;
		}

		//��ע��
		if (lUserID.longValue() > -1) {
			hCNetSDK.NET_DVR_Logout_V30(lUserID);
			lUserID = new NativeLong(-1);
			m_iTreeNodeNum = 0;
			combo.removeAll();
//			m_DeviceRoot.removeAllChildren();
		}

		//ע��
		m_sDeviceIP = text_IP.getText().trim();//�豸ip��ַ
		m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();//�豸��Ϣ
		int iPort = Integer.parseInt(text_Port.getText().trim());
		lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP, (short) iPort, text_user.getText(), text_password.getText(), m_strDeviceInfo);
		long userID = lUserID.longValue();
		if (userID == -1) {
			m_sDeviceIP = "";//��¼δ�ɹ�,IP��Ϊ��
			MessageDialog.openError(new Shell(), "����", "ע��ʧ��");
//			MessageBox box = new MessageBox(new Shell());
//			box.setMessage("ע��ʧ�ܣ�");
//			box.open();
		} else {
			CreateDeviceTree();
		}

	}

	//�����豸ͨ����
	private void CreateDeviceTree() {
		IntByReference ibrBytesReturned = new IntByReference(0);//��ȡIP�������ò���
		boolean bRet = false;

		m_strIpparaCfg = new HCNetSDK.NET_DVR_IPPARACFG();
		m_strIpparaCfg.write();
		Pointer lpIpParaConfig = m_strIpparaCfg.getPointer();
		bRet = hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_IPPARACFG, new NativeLong(0), lpIpParaConfig,
				m_strIpparaCfg.size(), ibrBytesReturned);
		m_strIpparaCfg.read();

//		DefaultTreeModel TreeModel = ((DefaultTreeModel) jTreeDevice.getModel());//��ȡ��ģ��
		if (!bRet) {
			//�豸��֧��,���ʾû��IPͨ��
			for (int iChannum = 0; iChannum < m_strDeviceInfo.byChanNum; iChannum++) {
				String camerano = "Camera" + (iChannum + m_strDeviceInfo.byStartChan);
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(camerano);
				combo.add(camerano);
				combo.setData(camerano, newNode);
//				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Camera" + (iChannum + m_strDeviceInfo.byStartChan));
//				TreeModel.insertNodeInto(newNode, m_DeviceRoot, iChannum);
			}
		} else {
			//�豸֧��IPͨ��
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
		combo.select(0);//Ĭ��ѡ�е�һ��
		combo.update();
//		TreeModel.reload();//����ӵĽڵ���ʾ������
//		jTreeDevice.setSelectionInterval(1, 1);//ѡ�е�һ���ڵ�
	}

	//���˳�ʱҪ��һ���Ĵ���
	@Override
	public void dispose() {
		//�����Ԥ��,��ֹͣԤ��, �ͷž��
		if (lPreviewHandle.longValue() > -1) {
			hCNetSDK.NET_DVR_StopRealPlay(lPreviewHandle);
		}

//		//��������
//		if (lAlarmHandle.intValue() != -1) {
//			hCNetSDK.NET_DVR_CloseAlarmChan_V30(lAlarmHandle);
//		}
//		//ֹͣ����
//		if (lListenHandle.intValue() != -1) {
//			hCNetSDK.NET_DVR_StopListen_V30(lListenHandle);
//		}

		//����Ѿ�ע��,ע��
		if (lUserID.longValue() > -1) {
			hCNetSDK.NET_DVR_Logout_V30(lUserID);
		}
		//cleanup SDK
		hCNetSDK.NET_DVR_Cleanup();
		super.dispose();
	}

//	//��ȡIP��ַ�ĵȴ��߳�
//	class GetIpThread extends Thread {
//		@Override
//		public void run() {
//			synchronized (SystemUtil.message) {//��ס message
//				while ("".equals(SystemUtil.message.toString())) {//û����Ϣ
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
//			if (!"".equalsIgnoreCase(SystemUtil.message.toString())) {//����ɹ��Ļ���û��message����ip��ַ
//				Display.getDefault().asyncExec(new Runnable() {
//					public void run() {
//						MessageDialog.openInformation(Display.getDefault().getActiveShell(), "��ʾ", SystemUtil.message.toString());
//						text_IP.setText(SystemUtil.message.toString());
//						SystemUtil.message = new StringBuffer("");//����Ϣ��Ϊ��
//						return;//��¼�ɹ��ˣ��ȴ��̲߳ſ��Է��ؽ���
//					}
//				});
//			} else {
//				Display.getDefault().asyncExec(new Runnable() {
//					@Override
//					public void run() {
//						SystemUtil.message = new StringBuffer("");//����Ϣ��Ϊ��
//						return;//���ﲻ��return����Ȼ�߳̾ͽ�����
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
