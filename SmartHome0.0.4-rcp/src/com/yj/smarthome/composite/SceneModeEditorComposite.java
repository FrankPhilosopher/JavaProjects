package com.yj.smarthome.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.beans.SceneMode;
import com.yj.smarthome.beans.SceneModeItem;
import com.yj.smarthome.dialog.RenameDialog;
import com.yj.smarthome.dialog.SceneModeDialog;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.util.SystemUtil;
import com.yj.smarthome.xmlImpls.SceneModeXmlTool;

/**
 * ��ʾ���龰ģʽ�༭���е��龰ģʽ���
 * 
 * @author yinger
 * 
 */
public class SceneModeEditorComposite extends Composite {

	private SceneMode sceneMode;//��Ӧ���龰ģʽ
	private Image image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.SCENEMODE_IMAGE);//TODO���龰ģʽ��Ӧ��ͼƬ
	private Composite container;
	private Group group;

	public SceneModeEditorComposite(Composite parent, int style, SceneMode sceneMode) {
		super(parent, style);
		this.container = parent;
		this.sceneMode = sceneMode;//���������scenemode�϶�����null��һ���������ƣ����ǲ�һ����items
		initUI();
	}

	private void initUI() {
		group = new Group(this, SWT.NONE);
		group.setText("\u60C5\u666F\u6A21\u5F0F\u540D\u79F0");
		group.setBounds(10, 10, 224, 261);
		if (sceneMode != null) {
			group.setText(sceneMode.getClientName());
		}

		final Canvas canvas = new Canvas(group, /* SWT.BORDER | */SWT.DOUBLE_BUFFERED);
		canvas.setBounds(10, 21, 204, 171);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, canvas.getBounds().width,
						canvas.getBounds().height);
			}
		});
		canvas.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseHover(MouseEvent e) {
				canvas.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));//��ָ��
			}
		});
		canvas.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {//�����������
				SceneModeDialog dialog = new SceneModeDialog(getShell(), sceneMode);
				dialog.open();
			}
		});

		Button button_rename = new Button(group, SWT.NONE);
		button_rename.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.RENAME_ICON));
		button_rename.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//�޸�����
				String oldName = sceneMode.getClientName();
				RenameDialog renameDialog = new RenameDialog(getShell(), sceneMode);
				renameDialog.open();
				String newName = sceneMode.getClientName();
				if (!newName.equals(oldName)) {
					//��һ��Ҫ��������Ƶ��޸ı��浽xml�ļ���
					try {
						SceneModeXmlTool.getInstance().renameSceneMode(oldName, sceneMode);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//�ڶ��������޸�UI��ʾ����Ϊ�ļ����޸ĳɹ��˲���ɹ�
					group.setText(sceneMode.getClientName());
				}
			}
		});
		button_rename.setBounds(10, 198, 90, 24);
		button_rename.setText("\u4FEE\u6539\u540D\u79F0");

		Button button_set = new Button(group, SWT.NONE);
		button_set.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.SETTING_ICON));
		button_set.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//�޸�����
				SceneModeDialog dialog = new SceneModeDialog(getShell(), sceneMode);
				dialog.open();
			}
		});
		button_set.setBounds(124, 198, 90, 24);
		button_set.setText("\u4FEE\u6539\u8BBE\u7F6E");

		Button button_del = new Button(group, SWT.NONE);
		button_del.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.CLOSE_ICON));
		button_del.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//ɾ��ģʽ
				//���Ƚ��ļ�ɾ��
				SceneModeXmlTool.getInstance().deleteSceneMode(sceneMode);
				//Ȼ��ɾ�����
				group.getParent().dispose();
				container.layout();//���²��֣����򿴲���Ч��
			}
		});
		button_del.setBounds(10, 228, 90, 24);
		button_del.setText("\u5220\u9664\u6A21\u5F0F");

		Button button_start = new Button(group, SWT.NONE);
		button_start.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.OPEN_ICON));
		button_start.setText("\u542F\u52A8\u6A21\u5F0F");
		button_start.setBounds(124, 228, 90, 24);
		button_start.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//����ģʽ
				startSceneMode();
			}
		});
	}

	//����ģʽ
	protected void startSceneMode() {
		SceneModeItem sceneModeItem;//���û�У�items��sizeΪ0���Ͳ��÷�������
//		SceneModeCommand command;
		for (int i = 0, size = sceneMode.getItems().size(); i < size; i++) {
			sceneModeItem = sceneMode.getItems().get(i);
			for (int j = 0, size2 = sceneModeItem.getCommands().size(); j < size2; j++) {
				sendCommand(sceneModeItem.getCommands().get(j).getCommandCode());
			}
		}
	}

	//��������
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
