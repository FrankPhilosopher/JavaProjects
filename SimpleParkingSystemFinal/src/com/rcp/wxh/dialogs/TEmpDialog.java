package com.rcp.wxh.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.rcp.wbw.skin.LookAndFeel;
import com.rcp.wxh.pojo.TEmp;
import com.rcp.wxh.service.impl.EmpService;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * 新增员工信息对话框
 * 
 * @author wuxuehong 2011-11-16
 * 
 */
public class TEmpDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public TEmpDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/EMP_MANAGE.png"));
		shell.setSize(422, 448);
		shell.setLocation(getParent().getLocation().x + 300, getParent().getLocation().y + 150);
		shell.setText("\u65B0\u589E\u5458\u5DE5");
		shell.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		Group group = new Group(shell, SWT.NONE);
		group.setText("\u586B\u5199\u5458\u5DE5\u4FE1\u606F");
		group.setBounds(10, 10, 396, 374);

		Label lblid = new Label(group, SWT.NONE);
		lblid.setBounds(51, 42, 70, 18);
		lblid.setText("\u5458\u5DE5ID\uFF1A");

		text = new Text(group, SWT.BORDER);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text.setBounds(132, 42, 166, 18);
		text.setTextLimit(29); // ID

		Label label = new Label(group, SWT.NONE);
		label.setBounds(51, 75, 70, 18);
		label.setText("\u5458\u5DE5\u59D3\u540D\uFF1A");

		text_1 = new Text(group, SWT.BORDER);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_1.setBounds(132, 75, 166, 18);
		text_1.setTextLimit(29); // 姓名

		Label label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(51, 109, 70, 18);
		label_1.setText("\u64CD\u4F5C\u6743\u9650\uFF1A");

		final Combo combo = new Combo(group, SWT.NONE);
		combo.setItems(new String[] { "\u7BA1\u7406\u5458", "\u64CD\u4F5C\u5458" });
		combo.setBounds(132, 107, 87, 20);
		combo.setText("\u64CD\u4F5C\u5458");

		Label label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(51, 143, 70, 18);
		label_2.setText("\u8BC1\u4EF6\u53F7\u7801\uFF1A");

		text_2 = new Text(group, SWT.BORDER);
		text_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_2.setBounds(132, 143, 166, 18);
		text_2.setTextLimit(29);

		Label label_3 = new Label(group, SWT.NONE);
		label_3.setBounds(51, 177, 70, 18);
		label_3.setText("\u8054\u7CFB\u65B9\u5F0F\uFF1A");

		text_3 = new Text(group, SWT.BORDER);
		text_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_3.setBounds(132, 177, 166, 18);
		text_3.setTextLimit(29);

		Label label_4 = new Label(group, SWT.NONE);
		label_4.setBounds(51, 210, 70, 18);
		label_4.setText("\u4F4F  \u5740\uFF1A");

		text_4 = new Text(group, SWT.BORDER);
		text_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_4.setBounds(132, 210, 166, 18);
		text_4.setTextLimit(70);

		Label label_5 = new Label(group, SWT.NONE);
		label_5.setBounds(51, 242, 70, 18);
		label_5.setText("\u5907\u6CE8\uFF1A");

		text_5 = new Text(group, SWT.BORDER | SWT.MULTI | SWT.WRAP);
		text_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_5.setBounds(132, 242, 166, 122);
		text_5.setTextLimit(99);

		Button button = new Button(shell, SWT.NONE);
		button.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/add.ico"));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 提交新增员工
				String id = text.getText().trim();
				System.out.println(id);
				if ("".equals(id)) {
					MessageDialogUtil.showWarningMessage(shell, "请输入员工ID");
					return;
				}
				// 验证该ID是否已经注册
				EmpService es = new EmpService();
				TEmp emp = null;
				try {
					emp = es.getEmpById(id);
				} catch (Exception e1) {
					MessageDialogUtil.showWarningMessage(shell, "ID验证失败!");
					return;
				}
				if (emp != null) {
					MessageDialogUtil.showWarningMessage(shell, "该员工ID已经注册，请更换!");
					return;
				}
				emp = new TEmp(); // 创建一个员工实例
				int priority = -1;
				if ("操作员".equals(combo.getText())) {
					priority = TEmp.OPERATOR;
				} else if ("管理员".equals(combo.getText())) {
					priority = TEmp.ADMINSTRATOR;
				}
				emp.setOperatorid(id);
				emp.setOperatorname(text_1.getText());
				emp.setPriority(priority);
				emp.setIdentification(text_2.getText());
				emp.setPhonenumber(text_3.getText());
				emp.setAddress(text_4.getText());
				emp.setRemark(text_5.getText());
				emp.setPassword(id); // 初始化密码与用户名一致

				try {
					es.addEmp(emp);
				} catch (Exception e1) {
					MessageDialogUtil.showWarningMessage(shell, "新增员工信息失败!");
					return;
				}
				MessageDialogUtil.showInfoMessage(shell, "员工信息新增成功,初始登录密码与用户名一致!");
				result = emp;//result 保存为新增的用户
				shell.dispose();
			}
		});
		button.setBounds(241, 390, 72, 22);
		button.setText("\u65B0  \u589E");

		Button button_1 = new Button(shell, SWT.NONE);
		button_1.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/cancel.ico"));
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		button_1.setBounds(334, 390, 72, 22);
		button_1.setText("\u53D6  \u6D88");

	}
}
