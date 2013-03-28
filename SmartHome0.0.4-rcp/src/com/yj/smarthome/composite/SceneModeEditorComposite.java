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
 * 显示在情景模式编辑器中的情景模式组件
 * 
 * @author yinger
 * 
 */
public class SceneModeEditorComposite extends Composite {

	private SceneMode sceneMode;//对应的情景模式
	private Image image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.SCENEMODE_IMAGE);//TODO：情景模式对应的图片
	private Composite container;
	private Group group;

	public SceneModeEditorComposite(Composite parent, int style, SceneMode sceneMode) {
		super(parent, style);
		this.container = parent;
		this.sceneMode = sceneMode;//传入进来的scenemode肯定不是null，一定是有名称，但是不一定有items
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
				canvas.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));//手指型
			}
		});
		canvas.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {//点击即可设置
				SceneModeDialog dialog = new SceneModeDialog(getShell(), sceneMode);
				dialog.open();
			}
		});

		Button button_rename = new Button(group, SWT.NONE);
		button_rename.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.RENAME_ICON));
		button_rename.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//修改名称
				String oldName = sceneMode.getClientName();
				RenameDialog renameDialog = new RenameDialog(getShell(), sceneMode);
				renameDialog.open();
				String newName = sceneMode.getClientName();
				if (!newName.equals(oldName)) {
					//第一步要把这个名称的修改保存到xml文件中
					try {
						SceneModeXmlTool.getInstance().renameSceneMode(oldName, sceneMode);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//第二步才是修改UI显示，因为文件中修改成功了才算成功
					group.setText(sceneMode.getClientName());
				}
			}
		});
		button_rename.setBounds(10, 198, 90, 24);
		button_rename.setText("\u4FEE\u6539\u540D\u79F0");

		Button button_set = new Button(group, SWT.NONE);
		button_set.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.SETTING_ICON));
		button_set.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//修改设置
				SceneModeDialog dialog = new SceneModeDialog(getShell(), sceneMode);
				dialog.open();
			}
		});
		button_set.setBounds(124, 198, 90, 24);
		button_set.setText("\u4FEE\u6539\u8BBE\u7F6E");

		Button button_del = new Button(group, SWT.NONE);
		button_del.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.CLOSE_ICON));
		button_del.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//删除模式
				//首先将文件删除
				SceneModeXmlTool.getInstance().deleteSceneMode(sceneMode);
				//然后删除组件
				group.getParent().dispose();
				container.layout();//重新布局，否则看不到效果
			}
		});
		button_del.setBounds(10, 228, 90, 24);
		button_del.setText("\u5220\u9664\u6A21\u5F0F");

		Button button_start = new Button(group, SWT.NONE);
		button_start.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.OPEN_ICON));
		button_start.setText("\u542F\u52A8\u6A21\u5F0F");
		button_start.setBounds(124, 228, 90, 24);
		button_start.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//启动模式
				startSceneMode();
			}
		});
	}

	//启动模式
	protected void startSceneMode() {
		SceneModeItem sceneModeItem;//如果没有，items的size为0，就不用发送命令
//		SceneModeCommand command;
		for (int i = 0, size = sceneMode.getItems().size(); i < size; i++) {
			sceneModeItem = sceneMode.getItems().get(i);
			for (int j = 0, size2 = sceneModeItem.getCommands().size(); j < size2; j++) {
				sendCommand(sceneModeItem.getCommands().get(j).getCommandCode());
			}
		}
	}

	//发送命令
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
