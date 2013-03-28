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
import com.yj.smarthome.beans.DoorWindow;
import com.yj.smarthome.dialog.RenameDialog;
import com.yj.smarthome.interfaces.IResponse;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.util.SystemUtil;
import com.yj.smarthome.util.XmlUtil;
import com.yj.smarthome.xmlImpls.DoorControlXmlImpl;

/**
 * 门窗控制组件
 * 
 * @author yinger
 * 
 */
public class DoorWindowControlComposite extends Composite implements IResponse {

	private DoorWindow doorWindow;
	private Button button_open;
	private Button button_close;
	private Canvas canvas_state;
	private Image image;
	private Button button_stop;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DoorWindowControlComposite(Composite parent, int style, DoorWindow doorWindow) {
		super(parent, style);
		setLayout(null);
		setDoorWindow(doorWindow);
		//设置初始状态
		doorWindow.setState(SystemUtil.ALL_DEVICE_STATE[doorWindow.DeviceType_ID][doorWindow.getId()]);

		//首先设置一下当前灯的状态，即图片
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
//		group.setText("\u5924\u95E8");
		group_control.setText(doorWindow.getClientName());
		group_control.setBounds(20, 10, 200, 204);

		canvas_state = new Canvas(group_control, SWT.DOUBLE_BUFFERED);
		canvas_state.setBounds(43, 26, 114, 90);
		canvas_state.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, canvas_state.getBounds().width,
						canvas_state.getBounds().height);
			}
		});

		Button button_rename = new Button(group_control, SWT.NONE);
		button_rename.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.RENAME_ICON));
		button_rename.setBounds(10, 138, 84, 24);
		button_rename.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//修改名称
				String oldName = doorWindow.getClientName();
				RenameDialog renameDialog = new RenameDialog(getShell(), doorWindow);
				renameDialog.open();
				String newName = doorWindow.getClientName();
				if (!newName.equals(oldName)) {
					//第一步要把这个名称的修改保存到xml文件中
					try {
//						XmlUtil.renamedoorWindow(doorWindow, ProtocolUtil.DeviceType_Door);
						XmlUtil.changeDeviceName(DoorControlXmlImpl.getInstance(), doorWindow);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//第二步才是修改UI显示，因为文件中修改成功了才算成功
					group_control.setText(doorWindow.getClientName());
				}
			}
		});
		button_rename.setText("\u4FEE\u6539\u540D\u79F0");

		button_open = new Button(group_control, SWT.NONE);
		button_open.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.OPEN_ICON));
		button_open.setBounds(106, 138, 84, 24);
		button_open.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//打开
				sendCommand(doorWindow.getCommand_buttonon());
			}
		});
		button_open.setText("\u6253\u5F00");

		button_stop = new Button(group_control, SWT.NONE);
		button_stop.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.STOP_ICON));
		button_stop.setBounds(10, 171, 84, 24);
		button_stop.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//暂停
				sendCommand(doorWindow.getCommand_buttonstop());
			}
		});
		button_stop.setText("\u6682\u505C");

		button_close = new Button(group_control, SWT.NONE);
		button_close.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.CLOSE_ICON));
		button_close.setBounds(106, 171, 84, 24);
		button_close.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//关闭
				sendCommand(doorWindow.getCommand_buttonoff());
			}
		});
		button_close.setText("\u5173\u95ED");
	}

	//改变按钮的状态
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
	//这里很重要，用于处理反馈信息的方法
	public void dealWithResponse(byte[] response) {
		//首先检测是否是网关故障
		byte state = response[8];
//		if (state == ProtocolUtil.Gateway_Error) {
//			label_state.setText("网关故障！");
//			return;
//		} else if (state == ProtocolUtil.Instruction_Ok) {
//			label_state.setText("发送成功！");
//		} else if (state == ProtocolUtil.Instruction_Fail) {
//			label_state.setText("发送失败！");
//			return;
//		}
		//只有发送成功了才进行修改！
		//接着得到是什么命令
		boolean lightState = false;
		byte[] bytes = { response[4], response[5], response[6], response[7] };
		int command = ProtocolUtil.convertByteArrayToInt(bytes);
		if (command == doorWindow.getCommand_buttonon()) {//如果是开灯命令
			lightState = true;
		}
		//最后更改显示状态
		if (lightState) {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.DOOR_OPEN);
			doorWindow.setState(DoorWindow.STATE_ON);
		} else {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.DOOR_CLOSE);
			doorWindow.setState(DoorWindow.STATE_OFF);
		}
		canvas_state.redraw();
//		changeButtonState(doorWindow.getState());
		//保存到文件中！
		try {
//			XmlUtil.saveDoorWindowState(doorWindow);
			XmlUtil.changeDeviceState(DoorControlXmlImpl.getInstance(), doorWindow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendCommand(int command) {
		if (command == ProtocolUtil.NO_COMMAND) {
			return;
		}
		//首先得到要传送的数据
		byte[] data = ProtocolUtil.packInstructionRequest(command);
		//然后将数据传给通信类
		try {
			SystemUtil.communication.sendData(data);
		} catch (Exception e1) {
			e1.printStackTrace();
			//提示错误
		}
	}

}
