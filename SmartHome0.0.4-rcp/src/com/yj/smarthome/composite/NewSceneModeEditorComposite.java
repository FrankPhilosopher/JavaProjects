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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.beans.SceneMode;
import com.yj.smarthome.xmlImpls.SceneModeXmlTool;

/**
 * ��ʾ���龰ģʽ�༭���е��龰ģʽ���
 * 
 * @author yinger
 * 
 */
public class NewSceneModeEditorComposite extends Composite {

	private SceneMode sceneMode;//��Ӧ���龰ģʽ
	private Image image = ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.NEW_SCENEMODE_IMAGE);//TODO���龰ģʽ��Ӧ��ͼƬ
	private Group group;
	private Text text_name;
	private Composite container;//���������������Ҫ��

	public NewSceneModeEditorComposite(Composite parent, int style) {
		super(parent, style);
		this.container = parent;
		initUI();
	}

	private void initUI() {
		group = new Group(this, SWT.NONE);
		group.setText("\u6DFB\u52A0\u60C5\u666F\u6A21\u5F0F");
		group.setBounds(10, 10, 223, 255);
		if (sceneMode != null) {
			group.setText(sceneMode.getClientName());
		}

		final Canvas canvas = new Canvas(group, /* SWT.BORDER | */SWT.DOUBLE_BUFFERED);
		canvas.setBounds(10, 21, 204, 171);
		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, canvas.getBounds().width,
						canvas.getBounds().height);
			}
		});

		Label label = new Label(group, SWT.NONE);
		label.setBounds(10, 198, 108, 18);
		label.setText("\u65B0\u7684\u60C5\u666F\u6A21\u5F0F\u540D\u79F0");

		text_name = new Text(group, SWT.BORDER);
		text_name.setBounds(10, 223, 114, 24);
		text_name.setFont(SWTResourceManager.getFont("΢���ź�", 10, SWT.NORMAL));

		Button button_add = new Button(group, SWT.NONE);
		button_add.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.ADD_ICON));
		button_add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {//����µ��龰ģʽ
				//����ɾ��ԭ���������龰ģʽ��Ȼ������µ��龰ģʽ����������һ�������龰ģʽ���
				SceneMode sceneMode = new SceneMode(text_name.getText().trim());
				Control[] controls = container.getChildren();
				for (Control control : controls) {
					if (control instanceof NewSceneModeEditorComposite) {
						control.dispose();
					}
				}
				new SceneModeEditorComposite(container, SWT.NONE, sceneMode);
				try {
					SceneModeXmlTool.getInstance().newSceneModeXml(sceneMode);
				} catch (Exception e1) {
					e1.printStackTrace();//TODO:����
				}
				new NewSceneModeEditorComposite(container, SWT.NONE);
				container.layout();
			}
		});
		button_add.setBounds(130, 223, 84, 24);
		button_add.setText("\u6DFB\u52A0");

	}
}
