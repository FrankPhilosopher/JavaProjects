package com.yj.smarthome.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.ResourceManager;

import com.yj.smarthome.Activator;
import com.yj.smarthome.util.SystemUtil;

/**
 * 修改服务器参数配置的dialog
 * 
 * @author yinger
 * 
 */
public class ServerSettingDialog extends TitleAreaDialog {

	private Group group;
	private Text text_ip1;
	private Text text_port1;
	private Text text_ip2;
	private Text text_port2;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public ServerSettingDialog(Shell parentShell) {
		super(parentShell);
//		setErrorMessage(MessageUtil.ServerSettingDialog_this_errorMessage);
		setHelpAvailable(false);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("\u4E3A\u4E86\u4FDD\u8BC1\u8F6F\u4EF6\u6B63\u5E38\u8FDE\u63A5\u670D\u52A1\u5668\uFF0C\u8BF7\u786E\u4FDD\u670D\u52A1\u5668\u53C2\u6570\u914D\u7F6E\u6B63\u786E\uFF01");
		setTitle("\u670D\u52A1\u5668\u53C2\u6570\u914D\u7F6E");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.BORDER);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		FormData fd_group = new FormData();
		fd_group.bottom = new FormAttachment(0, 293);
		fd_group.right = new FormAttachment(0, 425);
		fd_group.top = new FormAttachment(0, 115);
		fd_group.left = new FormAttachment(0, 21);

		group = new Group(container, SWT.NONE);
		group.setLocation(34, 28);
		group.setSize(397, 187);
		group.setLayoutData(fd_group);
		group.setText("\u670D\u52A1\u5668\u53C2\u6570");

		Group group_1 = new Group(group, SWT.NONE);
		group_1.setText("\u7F51\u5173\u670D\u52A1\u5668");
		group_1.setBounds(10, 30, 177, 138);

		Label lblIp = new Label(group_1, SWT.NONE);
		lblIp.setBounds(10, 29, 61, 17);
		lblIp.setText("IP\u5730\u5740");

		text_ip1 = new Text(group_1, SWT.BORDER);
		text_ip1.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		text_ip1.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
			}
		});
		text_ip1.setBounds(10, 52, 137, 23);

		Label label_2 = new Label(group_1, SWT.NONE);
		label_2.setBounds(10, 81, 61, 17);
		label_2.setText("\u7AEF\u53E3\u53F7");

		text_port1 = new Text(group_1, SWT.BORDER);
		text_port1.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		text_port1.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
			}
		});
		text_port1.setBounds(10, 104, 137, 23);

		Group group_2 = new Group(group, SWT.NONE);
		group_2.setText("\u8FDC\u7A0B\u670D\u52A1\u5668");
		group_2.setBounds(206, 30, 177, 138);

		Label label_3 = new Label(group_2, SWT.NONE);
		label_3.setText("IP\u5730\u5740");
		label_3.setBounds(10, 29, 61, 17);

		text_ip2 = new Text(group_2, SWT.BORDER);
		text_ip2.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		text_ip2.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
			}
		});
		text_ip2.setBounds(10, 52, 137, 23);

		Label label_4 = new Label(group_2, SWT.NONE);
		label_4.setText("\u7AEF\u53E3\u53F7");
		label_4.setBounds(10, 81, 61, 17);

		text_port2 = new Text(group_2, SWT.BORDER);
		text_port2.setFont(ResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		text_port2.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
			}
		});
		text_port2.setBounds(10, 104, 137, 23);

		//设置各项初始值
		{
			text_ip1.setText(SystemUtil.GATEWAY_IP);
			text_port1.setText(String.valueOf(SystemUtil.GATEWAY_PORT));
			text_ip2.setText(SystemUtil.SERVER_IP);
			text_port2.setText(String.valueOf(SystemUtil.SERVER_PORT));
		}

		return area;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			//TODO:检查是否符合ip地址的形式在修改文本时就进行了
			//目前假设它们都是正确的形式
			SystemUtil.GATEWAY_IP = text_ip1.getText().trim();
			SystemUtil.SERVER_IP = text_ip2.getText().trim();
			SystemUtil.GATEWAY_PORT = Integer.parseInt(text_port1.getText().trim());
			SystemUtil.SERVER_PORT = Integer.parseInt(text_port2.getText().trim());
			try {
				SystemUtil.saveSystemSettings();
			} catch (Exception e) {
				e.printStackTrace();
				//提示保存出错
			}
			okPressed();
		}
		if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();
		}
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
//		getButtonBar().setOrientation(SWT.LEFT_TO_RIGHT);

		Button button = createButton(parent, IDialogConstants.OK_ID, "\u786E\u5B9A", true);
		button.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.OK_ICON));
		button.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID, "\u53D6\u6D88", false);
		button_1.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageKeyManager.CANCLE_ICON));
		button_1.setCursor(ResourceManager.getCursor(SWT.CURSOR_HAND));
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(472, 400);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("\u670D\u52A1\u5668\u53C2\u6570\u914D\u7F6E");
	}

}
