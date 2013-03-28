package com.yj.smarthome.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yj.smarthome.interfaces.IRenameable;

/**
 * 通用的修改名称的Dialog
 * 
 * @author yinger
 * 
 */
public class RenameDialog extends TitleAreaDialog {
	private Text text;
	private IRenameable renameObject;//可以修改名称

	/**
	 * Create the dialog.
	 */
	public RenameDialog(Shell parentShell, IRenameable renameObject) {
		super(parentShell);
		this.setRenameObject(renameObject);
		setHelpAvailable(false);//不显示帮助
	}

	/**
	 * Create contents of the dialog.
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("请输入要显示的名称");//image
		setTitle("\u4FEE\u6539\u540D\u79F0");//
//		setTitleImage(newTitleImage);//
		Composite area = (Composite) super.createDialogArea(parent);
//		area.setBackgroundImage(Activator.getImageDescriptor("icons/login/login.jpg").createImage());
		Composite container = new Composite(area, SWT.BORDER);
		container.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label label = new Label(container, SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		label.setBounds(36, 32, 84, 24);
		label.setText("\u65B0\u7684\u540D\u79F0");

		text = new Text(container, SWT.BORDER);
		text.setBounds(127, 33, 146, 23);
		text.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		text.setText(renameObject.getClientName());//得到原来的名称

		return area;
	}

	//按钮按下之后触发的方法
	@Override
	protected void buttonPressed(int buttonId) {
		// 单击确定
		if (IDialogConstants.OK_ID == buttonId) {
			String rename = text.getText();
			if (null == rename || "".equalsIgnoreCase(rename)) {
				MessageDialog.openWarning(getParentShell(), "Warning", "不能为空");
				//这里最好还是放在当前的dialog上显示错误信息
			} else {
				//设置新的名称，但是具体的保存回到原来的界面！
				renameObject.setClientName(text.getText().trim());
			}
			okPressed();
		}
		// 单击取消
		if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();
		}
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("\u4FEE\u6539\u540D\u79F0");
		//这里可以修改shell的样式
	}

	/**
	 * Create contents of the button bar.
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		ok.setImage(ResourceManager.getPluginImage("com.yj.smarthome0.0.3", "icons/icos/ok.ico"));
		ok.setText("\u786E\u5B9A");
		Button cancel = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		cancel.setImage(ResourceManager.getPluginImage("com.yj.smarthome0.0.3", "icons/icos/cancle.ico"));
		cancel.setText("\u53D6\u6D88");
	}

	@Override
	protected Point getInitialSize() {
		return new Point(433, 231);
	}

	public IRenameable getRenameObject() {
		return renameObject;
	}

	public void setRenameObject(IRenameable renameObject) {
		this.renameObject = renameObject;
	}

}
