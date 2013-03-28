package com.yj.smarthome.composite;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.beans.SceneMode;
import com.yj.smarthome.beans.SceneModeCommand;
import com.yj.smarthome.beans.SceneModeItem;
import com.yj.smarthome.util.ProtocolUtil;

/**
 * �龰ģʽ�Ի����е�һ��TabItem�����һ�����豸���
 * 
 * @author yinger
 * 
 */
public class _SceneModeItemComposite extends Composite {

	private SceneModeItem item;//����Ǵӱ��ض�ȡ����������豸�������������������
	private SceneModeItem sceneModeItem;//����Ǵ򿪵��龰ģʽ��������½��ģ���ô����null
	private SceneMode sceneMode;
	private Group group_command;//������
	private Canvas canvas;
	private Image image;

	public _SceneModeItemComposite(Composite parent, int style, SceneModeItem item, SceneModeItem sceneModeItem, SceneMode sceneMode) {
		super(parent, style);
		this.item = item;
		this.sceneModeItem = sceneModeItem;//��������null��������龰ģʽ��û������豸�������ô����null
		this.sceneMode = sceneMode;//���������scenemode�϶�����null��һ���������ƣ����ǲ�һ����items
		initImage();
		initUI();
		initCommands();
	}

	//��ʼ����������
	private void initCommands() {
		//���������ȡ��������һ��Ҫ�У�����ѡ�����ѡ������ô��û���ٳ���ѡ��״̬
		Button button_cancle = new Button(group_command, SWT.PUSH);
//		button_cancle.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button_cancle.setText("ȫ��ȡ��");
		button_cancle.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//����¼�����--ȡ��ѡ��
				Control[] controls = group_command.getChildren();
				for (Control control : controls) {
					if (control instanceof Button) {
						((Button) control).setSelection(false);//�����е�button����Ϊδѡ��״̬
					}
				}
				//�����ǰ��ģʽ�а�����������ô��Ҫɾ����
				if (sceneMode.getItems().contains(item)) {//�����һ��Ҫ�жϵģ���Ϊ�����û��ĵ����������ܻᷢ���仯
					sceneMode.getItems().remove(item);//�ж���ͬ�Ǹ���typeId���豸id		
				}
			}
		});

		//���һ��������label
		Label label = new Label(group_command, SWT.NONE);
		label.setText("");

		List<SceneModeCommand> commands = item.getCommands();
		SceneModeCommand command;
		for (int i = 0, size = commands.size(); i < size; i++) {
			command = commands.get(i);
			final Button button = new Button(group_command, SWT.RADIO);
			button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			button.setText(command.getCommandName());
			button.setData("command", command.getCommandCode());
			//�����ǰ��ģʽ��������������ô�ͽ����button����Ϊѡ��״̬
			if (sceneModeItem != null && sceneModeItem.getCommands().contains(command)) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				button.setSelection(true);
			}
			button.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {//����¼�����
					SceneModeCommand command = new SceneModeCommand((Integer) button.getData("command"), button.getText());
					//�����ǰ��ģʽ�а�����������ô��Ҫɾ����
					if (sceneMode.getItems().contains(sceneModeItem)) {//�����һ��Ҫ�жϵģ���Ϊ�����û��ĵ����������ܻᷢ���仯
						sceneMode.getItems().remove(sceneModeItem);//�ж���ͬ�Ǹ���typeId���豸id	
					}
					sceneModeItem = new SceneModeItem(item.getTypeId(), item.getDeviceId(), item.getDeviceName(), Arrays.asList(command));
					sceneMode.getItems().add(sceneModeItem);
//					System.out.println(button.getData("command"));
					//�������ַ�ʽ�����У�����ʵ��ѡ��ת��ԭ��Ӧ���Ǻ�RADIO�й�
//					if (button.getSelection()) {
//						button.setSelection(false);
//					}else{
//						button.setSelection(true);
//					}
//					button.setSelection(!button.getSelection());
				}
			});
		}
		group_command.layout();//�ڶ�̬�ĸı����֮������Ǽ���layout������һЩ�����ʾ������
	}

	//��ʼ��Ҫ��ʾ��ͼƬ
	private void initImage() {
		switch (item.getTypeId()) {
		case ProtocolUtil.DEVICETYPE_LIGHT:
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.LIGHT_ICON);
			break;
		case ProtocolUtil.DEVICETYPE_DOOR:
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.DOOR_ICON);
			break;
		case ProtocolUtil.DEVICETYPE_APPLIANCE:
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.APPLIANCE_ICON);
			break;
		default:
			break;
		}
	}

	//��ʼ������
	private void initUI() {
		Group group = new Group(this, SWT.NONE);
		group.setText("\u8BBE\u5907\u540D\u79F0");
		group.setBounds(10, 10, 510, 170);
		group.setText(item.getDeviceName());//�豸������  TODO:����豸���Ƹı����أ�

		canvas = new Canvas(group, SWT.NONE);
		canvas.setBounds(10, 27, 133, 133);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, canvas.getBounds().width,
						canvas.getBounds().height);
			}
		});

		group_command = new Group(group, SWT.NONE);
		group_command.setText("\u9009\u62E9\u63A7\u5236\u547D\u4EE4");
		group_command.setBounds(149, 27, 351, 133);
		group_command.setLayout(new GridLayout(2, true));
	}
}
