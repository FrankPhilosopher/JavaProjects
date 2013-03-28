package com.yj.smarthome.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.yj.smarthome.beans.SceneMode;
import com.yj.smarthome.stw.AbstractSTWDemoFrame;
import com.yj.smarthome.stw.SceneModeSTWFrame;
import com.yj.smarthome.xmlImpls.SceneModeXmlTool;

/**
 * 情景模式对话框
 * 
 * @author yinger
 * 
 */
public class SceneModeDialog extends TitleAreaDialog {

	private Button okButton;
	private Button cancelButton;
	private AbstractSTWDemoFrame frame;
	private SceneMode sceneMode;//对应的情景模式

	public SceneModeDialog(Shell parentShell, SceneMode sceneMode) {
		super(parentShell);
		this.sceneMode = sceneMode;//传入进来的scenemode肯定不是null，一定是有名称，但是不一定有items
		setHelpAvailable(false);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("情景模式设置");
		setMessage("请选中设备的控制命令设计您自定义的情景模式");
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		frame = new SceneModeSTWFrame(sceneMode);
		frame.init(container);
		return container;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			try {
				SceneModeXmlTool.getInstance().newSceneModeXml(sceneMode);
			} catch (Exception e) {
				e.printStackTrace();
			}
			okPressed();
		}
		if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		okButton.setText("\u786E\u5B9A");
		cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		cancelButton.setText("\u53D6\u6D88");
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("情景模式设置");
		//使登录界面居中显示
		Display display = getParentShell().getDisplay();
		Rectangle displayBounds = display.getPrimaryMonitor().getBounds();
		Rectangle shellBounds = newShell.getBounds();
		int x = displayBounds.x + (displayBounds.width - shellBounds.width) >> 1;
		int y = displayBounds.y + (displayBounds.height - shellBounds.height) >> 1;
		newShell.setLocation(x, y);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(740, 600);
	}

	public SceneMode getSceneMode() {
		return sceneMode;
	}

	public void setSceneMode(SceneMode sceneMode) {
		this.sceneMode = sceneMode;
	}

}
