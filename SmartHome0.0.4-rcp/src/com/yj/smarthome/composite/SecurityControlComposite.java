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
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.beans.Security;
import com.yj.smarthome.dialog.RenameDialog;
import com.yj.smarthome.interfaces.IResponse;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.util.SystemUtil;
import com.yj.smarthome.util.XmlUtil;
import com.yj.smarthome.xmlImpls.SecurityControlXmlImpl;

/**
 * �����������������Ӧ�������˵���Ϣ
 * 
 * @author yinger
 * 
 */
public class SecurityControlComposite extends Composite implements IResponse {

	private Security security;
	private Image image;
	private Canvas canvas_state;
	private Button button_on;
	private Button button_off;

	public SecurityControlComposite(Composite parent, int style, Security security) {
		super(parent, style);
		setSecurity(security);
		//���ó�ʼ״̬
		security.setState(SystemUtil.ALL_DEVICE_STATE[security.DeviceType_ID][security.getId()]);

		//��������һ�µ�ǰ�Ƶ�״̬����ͼƬ
		if (this.security.getState() == Security.STATE_ON) {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.SECURITY_OPEN);
		} else {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.SECURITY_CLOSE);
		}
		//����UI
		initUI();
		//�޸�����
		changeButtonState(this.security.getState());
	}

	private void initUI() {
		final Group group_control = new Group(this, SWT.NONE);
		group_control.setBounds(20, 10, 197, 202);
		group_control.setText(security.getClientName());//��������

		canvas_state = new Canvas(group_control, SWT.DOUBLE_BUFFERED);
		canvas_state.setLocation(45, 33);
		canvas_state.setSize(109, 87);
		canvas_state.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, canvas_state.getBounds().width,
						canvas_state.getBounds().height);
			}
		});

		Button button_rename = new Button(group_control, SWT.NONE);
		button_rename.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.RENAME_ICON));
		button_rename.setBounds(10, 136, 84, 24);
		button_rename.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//�޸�����
				String oldName = security.getClientName();
				RenameDialog renameDialog = new RenameDialog(getShell(), security);
				renameDialog.open();
				String newName = security.getClientName();
				if (!newName.equals(oldName)) {
					//��һ��Ҫ��������Ƶ��޸ı��浽xml�ļ���
					try {
						XmlUtil.changeDeviceName(SecurityControlXmlImpl.getInstance(), security);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//�ڶ��������޸�UI��ʾ����Ϊ�ļ����޸ĳɹ��˲���ɹ�
					group_control.setText(security.getClientName());
				}
			}
		});
		button_rename.setText("\u4FEE\u6539\u540D\u79F0");

		button_on = new Button(group_control, SWT.NONE);
		button_on.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.OPEN_ICON));
		button_on.setBounds(10, 165, 84, 24);
		button_on.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//��
				sendCommand(security.getCommand_open());
			}
		});
		button_on.setText("\u6253\u5F00");

		button_off = new Button(group_control, SWT.NONE);
		button_off.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.CLOSE_ICON));
		button_off.setBounds(100, 165, 84, 24);
		button_off.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//�ر�
				sendCommand(security.getCommand_close());
			}
		});
		button_off.setText("\u5173\u95ED");

	}

	//�ı䰴ť��״̬
	private void changeButtonState(int state) {
		if (state == Security.STATE_ON) {
			button_off.setEnabled(true);
			button_on.setEnabled(false);
		} else {
			button_off.setEnabled(false);
			button_on.setEnabled(true);
		}
	}

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	@Override
	//�������Ҫ�����ڴ�������Ϣ�ķ���
	public void dealWithResponse(byte[] response) {
		//���ȼ���Ƿ������ع���
		byte state = response[8];
//		if (state == ProtocolUtil.Gateway_Error) {
//			label_state.setText("���ع��ϣ�");
//			return;
//		} else if (state == ProtocolUtil.Instruction_Ok) {
//			label_state.setText("���ͳɹ���");
//		} else if (state == ProtocolUtil.Instruction_Fail) {
//			label_state.setText("����ʧ�ܣ�");
//			return;
//		}
		//ֻ�з��ͳɹ��˲Ž����޸ģ�
		//���ŵõ���ʲô����
		boolean lightState = false;
		byte[] bytes = { response[4], response[5], response[6], response[7] };
		int command = ProtocolUtil.convertByteArrayToInt(bytes);
		if (command == security.getCommand_open()) {//����ǿ�������
			lightState = true;
		}
		//��������ʾ״̬
		if (lightState) {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.SECURITY_OPEN);
			security.setState(Security.STATE_ON);
//			SystemUtil.All_Device_State[ProtocolUtil.DeviceType_Light][security.getId()] = FullSecurity.STATE_ON;
		} else {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.SECURITY_CLOSE);
			security.setState(Security.STATE_OFF);
//			SystemUtil.All_Device_State[ProtocolUtil.DeviceType_Light][security.getId()] = FullSecurity.STATE_OFF;
		}
		canvas_state.redraw();
		changeButtonState(security.getState());//�޸İ�ť��״̬
		//���浽�ļ��У�
		try {
			XmlUtil.changeDeviceState(SecurityControlXmlImpl.getInstance(), security);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendCommand(int command) {
		if (command == ProtocolUtil.NO_COMMAND) {
			return;
		}
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
