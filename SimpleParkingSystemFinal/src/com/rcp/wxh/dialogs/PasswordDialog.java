package com.rcp.wxh.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.rcp.wbw.skin.LookAndFeel;
import com.rcp.wxh.actions.ValidateStatusAction;
import com.rcp.wxh.service.impl.EmpService;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * 密码修改对话框
 * 
 * @author wuxuehong 2011-11-17
 * 
 */
public class PasswordDialog extends Dialog {
	protected Object result = 0;
	protected Shell shell;
	private Label label_1;
	private Text text_new;
	private Label label_2;
	private Text text_old;
	private String password_1;
	private String password_2;
	private Label label;
	private Text text_repeat;
	private Label label_3;

	public PasswordDialog(Shell parent, int style) {
		super(parent, style);
		setText("密码管理");
	}

	public Object open() {
		createContents();
		Display display = getParent().getDisplay();
//		Rectangle displayBounds = display.getPrimaryMonitor().getBounds();
//		Rectangle shellBounds = shell.getBounds();
//		int x = displayBounds.x + (displayBounds.width - shellBounds.width)>>1;
//		int y = displayBounds.y + (displayBounds.height - shellBounds.height)>>1;
		shell.setLocation(getParent().getLocation().x + 300, getParent().getLocation().y + 250);
//		shell.setLocation(x, y);
		shell.open();
		shell.layout();

		//TODO:yinger 没用
//		final MessageBox mess = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
//		mess.setText("对不起");
		// mess.setMessage("不允许有空值!");

		Button button_ok = new Button(shell, SWT.NONE);
		button_ok.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/confirm1.ico"));
		button_ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String old = getText(text_old).trim();
				if (!old.equals(ValidateStatusAction.user.getPassword())) { // 验证旧密码
					label_3.setText("原始密码错误!");
					return;
				}
				password_1 = getText(text_new);
				password_2 = getText(text_repeat);
				if ("".equals(password_1) || "".equals(password_2)) {
					label_3.setText("密码不可以为空!");
					return;
				} else {
					if (!password_1.equals(password_2)) {
						label_3.setText("两次密码不一致!");
						return;
					} else {
						EmpService es = new EmpService();
						ValidateStatusAction.user.setPassword(password_1);
						try {
							es.updateEmp(ValidateStatusAction.user);
						} catch (Exception e1) {
							MessageDialogUtil.showWarningMessage(shell, "密码修改失败!");
							ValidateStatusAction.user.setPassword(old);// 密码还原
							return;
						}
					}
				}
				MessageDialogUtil.showInfoMessage(shell, "密码修改成功!");
				shell.dispose();
			}
		});
		button_ok.setBounds(161, 131, 72, 22);
		button_ok.setText("\u786E\u5B9A");

		Button button_cancel = new Button(shell, SWT.NONE);
		button_cancel.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/cancel.ico"));
		button_cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		button_cancel.setBounds(239, 131, 72, 22);
		button_cancel.setText("\u53D6\u6D88 ");

		Group group = new Group(shell, SWT.NONE);
		group.setText("\u5BC6\u7801\u4FEE\u6539");
		group.setBounds(10, 10, 301, 108);

		label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(10, 49, 52, 12);
		label_2.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label_2.setText("\u65B0\u5BC6\u7801\uFF1A");

		label = new Label(group, SWT.NONE);
		label.setBounds(10, 21, 52, 12);
		label.setText("\u65E7\u5BC6\u7801:");
		label.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		text_old = new Text(group, SWT.BORDER | SWT.PASSWORD);
		text_old.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_old.setBounds(88, 19, 176, 18);

		text_new = new Text(group, SWT.BORDER | SWT.PASSWORD);
		text_new.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_new.setBounds(88, 47, 176, 18);

		label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(10, 74, 70, 12);
		label_1.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label_1.setText("\u786E\u8BA4\u65B0\u5BC6\u7801\uFF1A");

		text_repeat = new Text(group, SWT.BORDER | SWT.PASSWORD);
		text_repeat.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_repeat.setBounds(88, 72, 176, 18);

		label_3 = new Label(shell, SWT.NONE);
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_3.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label_3.setBounds(10, 122, 145, 31);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/PASSWORD_MANAGE.png"));
		shell.setSize(327, 195);
		shell.setText("\u5BC6\u7801\u7BA1\u7406");
		shell.setLayout(null);

		shell.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	public String getText(Text text) {
		String textstring;
		textstring = text.getText();
		return textstring;
	}
}
