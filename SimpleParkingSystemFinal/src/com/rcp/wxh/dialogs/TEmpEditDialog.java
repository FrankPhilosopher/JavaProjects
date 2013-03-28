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
 * 员工信息编辑对话框
 * 
 * @author wuxuehong 2011-11-16
 * 
 */
public class TEmpEditDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Combo combo;
	private Button button;

	private TEmp emp; // 员工信息

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public TEmpEditDialog(Shell parent, int style, TEmp emp) {
		super(parent, style);
		this.emp = emp;
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		init(); // 初始化编辑对话框
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
		shell.setText("\u7F16\u8F91\u5458\u5DE5\u4FE1\u606F");
		shell.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		Group group = new Group(shell, SWT.NONE);
		group.setText("\u586B\u5199\u5458\u5DE5\u4FE1\u606F");
		group.setBounds(10, 10, 396, 344);

		Label lblid = new Label(group, SWT.NONE);
		lblid.setBounds(51, 42, 70, 18);
		lblid.setText("\u5458\u5DE5ID\uFF1A");

		text = new Text(group, SWT.BORDER);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text.setBounds(132, 42, 166, 18);
		text.setTextLimit(29); // ID
		text.setEnabled(false);

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

		combo = new Combo(group, SWT.NONE);
		combo.setItems(new String[] { "\u7BA1\u7406\u5458", "\u64CD\u4F5C\u5458" });
		combo.setBounds(132, 107, 87, 20);
		combo.setText("\u64CD\u4F5C\u5458");

		Label label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(51, 143, 70, 18);
		label_2.setText("\u8BC1\u4EF6\u53F7\u7801\uFF1A");

		text_2 = new Text(group, SWT.BORDER);
		text_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_2.setBounds(132, 143, 166, 18);
		text_2.setTextLimit(29); // 证件号

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

		text_5 = new Text(group, SWT.BORDER | SWT.MULTI | SWT.WRAP);//要能够支持换行！
		text_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_5.setBounds(132, 242, 166, 87);
		text_5.setTextLimit(99);

		button = new Button(shell, SWT.NONE);
		button.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/save.ico"));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 提交新增员工
				EmpService es = new EmpService();
				int priority = -1;
				if ("操作员".equals(combo.getText())) {
					priority = TEmp.OPERATOR;
				} else if ("管理员".equals(combo.getText())) {
					priority = TEmp.ADMINSTRATOR;
				}
				emp.setOperatorname(text_1.getText());
				emp.setPriority(priority);
				emp.setIdentification(text_2.getText());
				emp.setPhonenumber(text_3.getText());
				emp.setAddress(text_4.getText());
				emp.setRemark(text_5.getText());
				try {
					es.updateEmp(emp);
				} catch (Exception e1) {
					MessageDialogUtil.showWarningMessage(shell, "员工信息更新失败!");
					return;
				}
				MessageDialogUtil.showInfoMessage(shell, "员工信息更新成功!");
				result = emp;
				shell.dispose();
			}
		});
		button.setBounds(203, 360, 72, 22);
		button.setText("\u4FDD  \u5B58");

		Button button_1 = new Button(shell, SWT.NONE);
		button_1.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/cancel.ico"));
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		button_1.setBounds(309, 360, 72, 22);
		button_1.setText("\u53D6  \u6D88");

	}

	/**
	 * 由emp对象初始化编辑对话框
	 */
	public void init() {
		String[] pri = { "管理员", "操作员" };
		if (emp != null) {
			text.setText(emp.getOperatorid()); // ID
			text_1.setText(emp.getOperatorname()); // 姓名
			combo.setText(pri[emp.getPriority()]); // 权限
			text_2.setText(emp.getIdentification());// 证件号
			text_3.setText(emp.getPhonenumber()); // 联系方式
			text_4.setText(emp.getAddress());// 住址
			text_5.setText(emp.getRemark());// 备注信息
		} else {
			button.setEnabled(false);
		}
	}
}
