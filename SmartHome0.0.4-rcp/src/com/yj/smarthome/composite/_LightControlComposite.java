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
import com.yj.smarthome.beans.LightButton;
import com.yj.smarthome.dialog.RenameDialog;
import com.yj.smarthome.interfaces.IResponse;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.util.SystemUtil;
import com.yj.smarthome.util.XmlUtil;
import com.yj.smarthome.xmlImpls.LightControlXmlImpl;

/**
 * 灯控组件，它可以响应服务器端的信息
 * 
 * @author yinger
 * 
 */
public class _LightControlComposite extends Composite implements IResponse {

//	public static final int DeviceType_ID = PluginIDUtil.DeviceType_Light;//组件-灯//目前是多余的
//	private int Device_ID;//对应的灯编号
//	private String name = "大厅灯控";//它的名字由本地的xml文件决定

//	private ClientLightButton lightButton;//原始方式，改成了使用FullLightButton

	private LightButton lightButton;
	private Image image;
	private Label label_state;
	private Canvas canvas_state;
	private Button button_lightup;
	private Button button_lightdown;
	private Button button_on;
	private Button button_off;

	public _LightControlComposite(Composite parent, int style, LightButton lightButton) {
		super(parent, style);
		setLayout(null);
		setLightButton(lightButton);
		//设置初始状态
		lightButton.setState(SystemUtil.ALL_DEVICE_STATE[lightButton.DeviceType_ID][lightButton.getId()]);

		//首先设置一下当前灯的状态，即图片
		if (this.lightButton.getState() == LightButton.STATE_ON) {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.LIGHT_ON);
		} else {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.LIGHT_OFF);
		}
		//建立UI
		initUI();
		//修改设置
		changeButtonState(this.lightButton.getState());
	}

	private void initUI() {
		final Group group_control = new Group(this, SWT.NONE);
		group_control.setBounds(10, 0, 530, 120);
		group_control.setText(lightButton.getClientName());//设置名称

		canvas_state = new Canvas(group_control, SWT.DOUBLE_BUFFERED);
		canvas_state.setLocation(413, 20);
		canvas_state.setSize(107, 90);
		canvas_state.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
//				image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageManager.Light_ON);
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, canvas_state.getBounds().width,
						canvas_state.getBounds().height);
//				image.dispose();//不能有这一句，不然只有第一个图片
			}
		});

		Group group_setting = new Group(group_control, SWT.NONE);
		group_setting.setText("\u663E\u793A\u8BBE\u7F6E");
		group_setting.setBounds(10, 20, 183, 90);

		Button button_rename = new Button(group_setting, SWT.NONE);
		button_rename.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//修改名称
				String oldName = lightButton.getClientName();
				RenameDialog renameDialog = new RenameDialog(getShell(), lightButton);
				renameDialog.open();
				String newName = lightButton.getClientName();
				if (!newName.equals(oldName)) {
					//第一步要把这个名称的修改保存到xml文件中
					try {
//						XmlUtil.renameLightButton(lightButton, ProtocolUtil.DeviceType_Light);
						XmlUtil.changeDeviceName(LightControlXmlImpl.getInstance(), lightButton);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//第二步才是修改UI显示，因为文件中修改成功了才算成功
					group_control.setText(lightButton.getClientName());
				}
			}
		});
		button_rename.setText("\u4FEE\u6539\u540D\u79F0");
		button_rename.setBounds(10, 24, 80, 27);

		Label label = new Label(group_setting, SWT.NONE);
		label.setBounds(10, 63, 61, 17);
		label.setText("\u53CD\u9988\u72B6\u6001\uFF1A");

		label_state = new Label(group_setting, SWT.NONE);
		label_state.setBounds(77, 63, 96, 17);
		label_state.setText("\u6682\u65E0");

		Group group_intensity = new Group(group_control, SWT.NONE);
		group_intensity.setText("\u4EAE\u5EA6\u8C03\u8282");
		group_intensity.setBounds(199, 20, 101, 90);

		button_lightup = new Button(group_intensity, SWT.NONE);
		button_lightup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//调高亮度 //下面的四个方法可以提取出来放在一个方法里
				sendCommand(lightButton.getCommand_buttonup());
			}
		});
		button_lightup.setText("\u8C03\u4EAE");
		button_lightup.setBounds(10, 20, 80, 27);

		button_lightdown = new Button(group_intensity, SWT.NONE);
		button_lightdown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sendCommand(lightButton.getCommand_buttondown());
			}
		});
		button_lightdown.setText("\u8C03\u6697");
		button_lightdown.setBounds(10, 53, 80, 27);

		//如果没有亮度调节功能
		if (lightButton.getHasIntensity() == LightButton.INTENSITY_NO) {
			button_lightup.setEnabled(false);
			button_lightdown.setEnabled(false);
		}

		Group group_onoff = new Group(group_control, SWT.NONE);
		group_onoff.setText("\u5F00\u5173\u8C03\u8282");
		group_onoff.setBounds(306, 20, 101, 90);

		button_on = new Button(group_onoff, SWT.NONE);
		button_on.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sendCommand(lightButton.getCommand_buttonon());
			}
		});
		button_on.setBounds(10, 20, 80, 27);
		button_on.setText("\u6253\u5F00");

		button_off = new Button(group_onoff, SWT.NONE);
		button_off.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sendCommand(lightButton.getCommand_buttonoff());
			}
		});
		button_off.setBounds(10, 53, 80, 27);
		button_off.setText("\u5173\u95ED");
	}

	//改变按钮的状态
	private void changeButtonState(int state) {
		if (state == LightButton.STATE_ON) {
			button_off.setEnabled(true);
			button_on.setEnabled(false);
		} else {
			button_off.setEnabled(false);
			button_on.setEnabled(true);
		}
	}

	public LightButton getLightButton() {
		return lightButton;
	}

	public void setLightButton(LightButton lightButton) {
		this.lightButton = lightButton;
	}

	@Override
	//这里很重要，用于处理反馈信息的方法
	public void dealWithResponse(byte[] response) {
		//首先检测是否是网关故障
		byte state = response[8];
		if (state == ProtocolUtil.GATEWAY_ERROR) {
			label_state.setText("网关故障！");
			return;
		} else if (state == ProtocolUtil.INSTRUCTION_OK) {
			label_state.setText("发送成功！");
		} else if (state == ProtocolUtil.INSTRUCTION_FAIL) {
			label_state.setText("发送失败！");
			return;
		}
		//只有发送成功了才进行修改！
		//接着得到是什么命令
		boolean lightState = false;
		byte[] bytes = { response[4], response[5], response[6], response[7] };
		int command = ProtocolUtil.convertByteArrayToInt(bytes);
		if (command == lightButton.getCommand_buttonon()) {//如果是开灯命令
			lightState = true;
		}
		//最后更改显示状态
		if (lightState) {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.LIGHT_ON);
			lightButton.setState(LightButton.STATE_ON);
//			SystemUtil.All_Device_State[ProtocolUtil.DeviceType_Light][lightButton.getId()] = FullLightButton.STATE_ON;
		} else {
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.LIGHT_OFF);
			lightButton.setState(LightButton.STATE_OFF);
//			SystemUtil.All_Device_State[ProtocolUtil.DeviceType_Light][lightButton.getId()] = FullLightButton.STATE_OFF;
		}
		canvas_state.redraw();
		changeButtonState(lightButton.getState());//修改按钮的状态
		//保存到文件中！
		try {
//			XmlUtil.saveLightButtonState(lightButton);
			XmlUtil.changeDeviceState(LightControlXmlImpl.getInstance(), lightButton);
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
