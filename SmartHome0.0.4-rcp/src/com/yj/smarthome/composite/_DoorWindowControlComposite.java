package com.yj.smarthome.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.beans.DoorWindow;
import com.yj.smarthome.dialog.RenameDialog;
import com.yj.smarthome.interfaces.IResponse;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.util.SystemUtil;
import com.yj.smarthome.util.XmlUtil;
import com.yj.smarthome.xmlImpls.DoorControlXmlImpl;

/**
 * �Ŵ��������
 * 
 * @author yinger
 * 
 */
public class _DoorWindowControlComposite extends Composite implements IResponse {

	private DoorWindow doorWindow;
	private Button button_open;
	private Button button_close;
	private Label label_state;
	private Canvas canvas_state;
	private Image image;
	private Button button_stop;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public _DoorWindowControlComposite(Composite parent, int style, DoorWindow doorWindow) {
		super(parent, style);
		setLayout(null);
		setDoorWindow(doorWindow);
		//���ó�ʼ״̬
		doorWindow.setState(SystemUtil.ALL_DEVICE_STATE[doorWindow.DeviceType_ID][doorWindow.getId()]);

		//��������һ�µ�ǰ�Ƶ�״̬����ͼƬ
		if (this.doorWindow.getState() == DoorWindow.STATE_ON) {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.DOOR_OPEN);
		} else {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.DOOR_CLOSE);
		}
		initUI();
//		changeButtonState(this.doorWindow.getState());
	}

	private void initUI() {
		final Group group_control = new Group(this, SWT.NONE);
//		group.setText("\u5927\u95E8");
		group_control.setText(doorWindow.getClientName());
		group_control.setBounds(10, 10, 430, 200);

		canvas_state = new Canvas(group_control, SWT.DOUBLE_BUFFERED);
		canvas_state.setBounds(256, 20, 164, 170);
		canvas_state.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
//				image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageManager.Light_ON);
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, canvas_state.getBounds().width,
						canvas_state.getBounds().height);
//				image.dispose();//��������һ�䣬��Ȼֻ�е�һ��ͼƬ
			}
		});

		Group group_display = new Group(group_control, SWT.NONE);
		group_display.setText("\u663E\u793A\u63A7\u5236");
		group_display.setBounds(13, 20, 98, 113);

		Button button_rename = new Button(group_display, SWT.NONE);
		button_rename.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//�޸�����
				String oldName = doorWindow.getClientName();
				RenameDialog renameDialog = new RenameDialog(getShell(), doorWindow);
				renameDialog.open();
				String newName = doorWindow.getClientName();
				if (!newName.equals(oldName)) {
					//��һ��Ҫ��������Ƶ��޸ı��浽xml�ļ���
					try {
//						XmlUtil.renamedoorWindow(doorWindow, ProtocolUtil.DeviceType_Door);
						XmlUtil.changeDeviceName(DoorControlXmlImpl.getInstance(), doorWindow);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//�ڶ��������޸�UI��ʾ����Ϊ�ļ����޸ĳɹ��˲���ɹ�
					group_control.setText(doorWindow.getClientName());
				}
			}
		});
		button_rename.setBounds(10, 72, 80, 27);
		button_rename.setText("\u4FEE\u6539\u540D\u79F0");

		Group group_onoff = new Group(group_control, SWT.NONE);
		group_onoff.setText("\u5F00\u5173\u63A7\u5236");
		group_onoff.setBounds(130, 20, 98, 148);

		button_open = new Button(group_onoff, SWT.NONE);
		button_open.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//��
				sendCommand(doorWindow.getCommand_buttonon());
			}
		});
		button_open.setBounds(10, 25, 80, 27);
		button_open.setText("\u6253\u5F00");

		button_close = new Button(group_onoff, SWT.NONE);
		button_close.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//�ر�
				sendCommand(doorWindow.getCommand_buttonoff());
			}
		});
		button_close.setBounds(10, 111, 80, 27);
		button_close.setText("\u5173\u95ED");

		button_stop = new Button(group_onoff, SWT.NONE);
		button_stop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//��ͣ
				sendCommand(doorWindow.getCommand_buttonstop());
			}
		});
		button_stop.setText("\u6682\u505C");
		button_stop.setBounds(10, 68, 80, 27);

		Label label = new Label(group_control, SWT.NONE);
		label.setBounds(13, 151, 61, 17);
		label.setText("\u72B6\u6001\u53CD\u9988\uFF1A");

		label_state = new Label(group_control, SWT.NONE);
		label_state.setBounds(13, 173, 61, 17);
		label_state.setText("\u6682\u65E0");
	}

	//�ı䰴ť��״̬
	private void changeButtonState(int state) {
		if (state == DoorWindow.STATE_ON) {
			button_close.setEnabled(true);
			button_open.setEnabled(false);
		} else {
			button_close.setEnabled(false);
			button_open.setEnabled(true);
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public DoorWindow getDoorWindow() {
		return doorWindow;
	}

	public void setDoorWindow(DoorWindow doorWindow) {
		this.doorWindow = doorWindow;
	}

	@Override
	//�������Ҫ�����ڴ�������Ϣ�ķ���
	public void dealWithResponse(byte[] response) {
		//���ȼ���Ƿ������ع���
		byte state = response[8];
		if (state == ProtocolUtil.GATEWAY_ERROR) {
			label_state.setText("���ع��ϣ�");
			return;
		} else if (state == ProtocolUtil.INSTRUCTION_OK) {
			label_state.setText("���ͳɹ���");
		} else if (state == ProtocolUtil.INSTRUCTION_FAIL) {
			label_state.setText("����ʧ�ܣ�");
			return;
		}
		//ֻ�з��ͳɹ��˲Ž����޸ģ�
		//���ŵõ���ʲô����
		boolean lightState = false;
		byte[] bytes = { response[4], response[5], response[6], response[7] };
		int command = ProtocolUtil.convertByteArrayToInt(bytes);
		if (command == doorWindow.getCommand_buttonon()) {//����ǿ�������
			lightState = true;
		}
		//��������ʾ״̬
		if (lightState) {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.DOOR_OPEN);
			doorWindow.setState(DoorWindow.STATE_ON);
		} else {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.DOOR_CLOSE);
			doorWindow.setState(DoorWindow.STATE_OFF);
		}
		canvas_state.redraw();
//		changeButtonState(doorWindow.getState());
		//���浽�ļ��У�
		try {
//			XmlUtil.saveDoorWindowState(doorWindow);
			XmlUtil.changeDeviceState(DoorControlXmlImpl.getInstance(), doorWindow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendCommand(int command) {
		//���ȵõ�Ҫ���͵�����
		byte[] data = ProtocolUtil.packInstructionRequest(command);
		//Ȼ�����ݴ���ͨ����
		try {
			SystemUtil.communication.sendData(data);
		} catch (Exception e1) {
			e1.printStackTrace();
			//��ʾ����
		}
	}

}
