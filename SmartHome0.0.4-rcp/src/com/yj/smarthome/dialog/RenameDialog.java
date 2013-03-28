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
 * ͨ�õ��޸����Ƶ�Dialog
 * 
 * @author yinger
 * 
 */
public class RenameDialog extends TitleAreaDialog {
	private Text text;
	private IRenameable renameObject;//�����޸�����

	/**
	 * Create the dialog.
	 */
	public RenameDialog(Shell parentShell, IRenameable renameObject) {
		super(parentShell);
		this.setRenameObject(renameObject);
		setHelpAvailable(false);//����ʾ����
	}

	/**
	 * Create contents of the dialog.
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("������Ҫ��ʾ������");//image
		setTitle("\u4FEE\u6539\u540D\u79F0");//
//		setTitleImage(newTitleImage);//
		Composite area = (Composite) super.createDialogArea(parent);
//		area.setBackgroundImage(Activator.getImageDescriptor("icons/login/login.jpg").createImage());
		Composite container = new Composite(area, SWT.BORDER);
		container.setFont(SWTResourceManager.getFont("΢���ź�", 10, SWT.NORMAL));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label label = new Label(container, SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("΢���ź�", 10, SWT.NORMAL));
		label.setBounds(36, 32, 84, 24);
		label.setText("\u65B0\u7684\u540D\u79F0");

		text = new Text(container, SWT.BORDER);
		text.setBounds(127, 33, 146, 23);
		text.setFont(SWTResourceManager.getFont("΢���ź�", 10, SWT.NORMAL));
		text.setText(renameObject.getClientName());//�õ�ԭ��������

		return area;
	}

	//��ť����֮�󴥷��ķ���
	@Override
	protected void buttonPressed(int buttonId) {
		// ����ȷ��
		if (IDialogConstants.OK_ID == buttonId) {
			String rename = text.getText();
			if (null == rename || "".equalsIgnoreCase(rename)) {
				MessageDialog.openWarning(getParentShell(), "Warning", "����Ϊ��");
				//������û��Ƿ��ڵ�ǰ��dialog����ʾ������Ϣ
			} else {
				//�����µ����ƣ����Ǿ���ı���ص�ԭ���Ľ��棡
				renameObject.setClientName(text.getText().trim());
			}
			okPressed();
		}
		// ����ȡ��
		if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();
		}
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("\u4FEE\u6539\u540D\u79F0");
		//��������޸�shell����ʽ
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
