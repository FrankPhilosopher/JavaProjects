package com.yj.smarthome.composite;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
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
public class SceneModeItemComposite extends Composite {

	private SceneModeItem item;//����Ǵӱ��ض�ȡ����������豸�������������������
	private SceneModeItem sceneModeItem;//����Ǵ򿪵��龰ģʽ��������½��ģ���ô����null
	private SceneMode sceneMode;
	private Group group_command;//������
	private Canvas canvas;
	private Image image;
	private Composite composite_right;

	public SceneModeItemComposite(Composite parent, int style, SceneModeItem item, SceneModeItem sceneModeItem, SceneMode sceneMode) {
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
		List<SceneModeCommand> commands = item.getCommands();
		SceneModeCommand command;
		for (int i = 0, size = commands.size(); i < size; i++) {
			command = commands.get(i);
			final Button button = new Button(composite_right, SWT.RADIO);
//			button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
		case ProtocolUtil.DEVICETYPE_SECURITY:
			image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.SECURITY_ICON);
			break;
		default:
			break;
		}
	}

	//��ʼ������
	private void initUI() {
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		setLayout(new FormLayout());
		Group group = new Group(this, SWT.NONE);
		FormData fd_group = new FormData();
		fd_group.bottom = new FormAttachment(0, 106);
		fd_group.right = new FormAttachment(0, 646);
		fd_group.top = new FormAttachment(0, 10);
		fd_group.left = new FormAttachment(0, 10);
		group.setLayoutData(fd_group);
		FormLayout fl_group = new FormLayout();
		group.setLayout(fl_group);
		group.setText("\u8BBE\u5907\u540D\u79F0");
		group.setBounds(10, 10, 626, 109);
		group.setText(item.getDeviceName());//�豸������  TODO:����豸���Ƹı����أ����������⣡

		Composite leftComposite = new Composite(group, SWT.NONE);
		FormData fd_leftComposite = new FormData();
		fd_leftComposite.bottom = new FormAttachment(0, 62);
		fd_leftComposite.top = new FormAttachment(0, 10);
		fd_leftComposite.left = new FormAttachment(0, 10);
		leftComposite.setLayoutData(fd_leftComposite);
		//		leftComposite.setLayoutData(new RowData(77, 67));
		leftComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		canvas = new Canvas(leftComposite, SWT.DOUBLE_BUFFERED);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, canvas.getBounds().width,
						canvas.getBounds().height);
			}
		});

		group_command = new Group(group, SWT.NONE);
		FormData fd_group_command = new FormData();
		fd_group_command.right = new FormAttachment(100, -7);
		fd_group_command.left = new FormAttachment(leftComposite, 4);
		fd_group_command.bottom = new FormAttachment(100, -7);
		fd_group_command.top = new FormAttachment(0, 10);
		group_command.setLayoutData(fd_group_command);
		group_command.setLayout(new GridLayout(2, false));
		group_command.setText("\u9009\u62E9\u63A7\u5236\u547D\u4EE4");
		group_command.setBounds(80, 10, 546, 82);

		Composite composite_left = new Composite(group_command, SWT.NONE);
		composite_left.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));
		composite_left.setLayout(new RowLayout(SWT.HORIZONTAL));
		//���������ȡ��������һ��Ҫ�У�����ѡ�����ѡ������ô��û���ٳ���ѡ��״̬
		Button button_cancle = new Button(composite_left, SWT.PUSH);
		//button_cancle.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button_cancle.setText("ȫ��ȡ��");

		composite_right = new Composite(group_command, SWT.NONE);
		GridData gd_composite_right = new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1);
		gd_composite_right.heightHint = 40;
		gd_composite_right.widthHint = 453;
		composite_right.setLayoutData(gd_composite_right);
		composite_right.setLayout(new RowLayout(SWT.HORIZONTAL));
		button_cancle.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//����¼�����--ȡ��ѡ��
				Control[] controls = composite_right.getChildren();
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
	}
}
