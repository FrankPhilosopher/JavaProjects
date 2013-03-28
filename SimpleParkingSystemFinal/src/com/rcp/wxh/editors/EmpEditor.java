package com.rcp.wxh.editors;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.rcp.wxh.actions.ValidateStatusAction;
import com.rcp.wxh.dialogs.PasswordDialog;
import com.rcp.wxh.pojo.TEmp;
import com.rcp.wxh.service.impl.EmpService;
import com.swtdesigner.SWTResourceManager;

/**
 * 
 * 员工信息管理编辑器
 * 
 * TODO:yinger 这个已经不再使用了！使用的是  EmpEditor2
 * @author wuxuehong 2011-11-2
 * 
 */
public class EmpEditor extends EditorPart {
	public EmpEditor() {

	}

	public static final String ID = EmpEditor.class.getName();

	private Text text;
	private Text text_1;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Table table;
	private Combo combo;
	private Combo combo_find;
	private String find_what;
	private String find_express;
	private TEmp user;
	private TEmp user_admin;
	public static final int ADMINSTRATOR = 0;
	public static final int OPERATOR = 1;
	private String operatorid;
	private String password;
	private String operatorname;
	private Integer priority;
	private String identification;
	private String phonenumber;
	private String address;
	private String remark;
	private EmpService empservice;
	private Table table_1;
	private Text text_find;

	public void doSave(IProgressMonitor monitor) {

	}

	public void doSaveAs() {
	}

	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	public boolean isDirty() {
		return false;
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	// 初始化String
	public void setStr(TEmp user) {
		operatorid = user.getOperatorid();
		password = user.getPassword();
		operatorname = user.getOperatorname();
		priority = user.getPriority();
		identification = user.getIdentification();
		phonenumber = user.getPhonenumber();
		address = user.getAddress();
		remark = user.getRemark();
	}

	// 初始化USER,返回新的USER(未设密码未验证是否重ID)
	// 添加用户时使用，要密码对话框的
	public TEmp setTEmp(Composite parent) {
		TEmp u = new TEmp();
		MessageBox mess = new MessageBox(parent.getShell(), SWT.OK | SWT.ICON_INFORMATION);
		mess.setText("对不起");
		mess.setMessage("不允许有空值!");
		if (text.getText() == "" || text_1.getText() == "" || text_3.getText() == "" || text_4.getText() == ""
				|| text_5.getText() == "" || text_6.getText() == "")
			mess.open();
		else {
			u.setOperatorid(text.getText());
			u.setOperatorname(text_1.getText());
			u.setIdentification(text_3.getText());
			u.setPhonenumber(text_4.getText());
			u.setAddress(text_5.getText());
			u.setRemark(text_6.getText());
			u.setPriority(combo.getSelectionIndex());
			TEmp emp = null;
			try {
				emp = empservice.getEmpById(text.getText()); // 数据库异常
			} catch (Exception e3) {
				e3.printStackTrace();
				;
			}
			if (emp == null) {
				PasswordDialog passworddia = new PasswordDialog(new Shell(), SWT.NONE);
				passworddia.open();
//			password = passworddia.getPassword();
//			password = PasswordDialog.password;
				u.setPassword(password);
			} else {
				MessageDialog.openInformation(parent.getShell(), "对不起", "己有同名ID，请换ID");
			}
			// 设置密码
			// InputDialog input = new InputDialog();

//			MessageBox messaaa = new MessageBox(parent.getShell(),SWT.OK|SWT.ICON_INFORMATION);
//			messaaa.setText("对不起");
//			messaaa.setMessage("55a");
		}
		return u;
	}

	// 初始化USER,返回新的USER(未设密码未验证是否重ID)
	// 修改用户时使用，不要密码对话框的
	public TEmp setTEmp(Composite parent, TEmp tem) {
		TEmp u = new TEmp();
		MessageBox mess = new MessageBox(parent.getShell(), SWT.OK | SWT.ICON_INFORMATION);
		mess.setText("对不起");
		if (text.getText() == "" || text_1.getText() == "" || text_3.getText() == "" || text_4.getText() == ""
				|| text_5.getText() == "" || text_6.getText() == "") {
			mess.setMessage("不允许有空值!");
			mess.open();
		} else {
			if (text.getText().equalsIgnoreCase(tem.getOperatorid())) {
				u.setOperatorid(tem.getOperatorid());
				u.setOperatorname(text_1.getText());
				u.setIdentification(text_3.getText());
				u.setPhonenumber(text_4.getText());
				u.setAddress(text_5.getText());
				u.setRemark(text_6.getText());
				u.setPriority(combo.getSelectionIndex());
				u.setPassword(tem.getPassword());
			} else {
				mess.setMessage("不允许修改主键ID!" + text.getText() + tem.getOperatorid());
				mess.open();
			}
		}
		return u;
	}

	// 初始化表格
	public void setTable(final TableViewer tv) {
		Table table = tv.getTable();
		TableColumn tc1 = new TableColumn(table, SWT.LEFT);
		TableColumn tc2 = new TableColumn(table, SWT.CENTER);
		TableColumn tc3 = new TableColumn(table, SWT.CENTER);
		TableColumn tc4 = new TableColumn(table, SWT.CENTER);
		TableColumn tc5 = new TableColumn(table, SWT.CENTER);
		tc1.setText("操作员ID");
		tc2.setText("操作员姓名");
		tc3.setText("操作员权限");
		tc4.setText("证件号码");
		tc5.setText("电话号码");
		tc1.setWidth(95);
		tc2.setWidth(110);
		tc3.setWidth(90);
		tc4.setWidth(200);
		tc5.setWidth(105);
		tc1.addSelectionListener(new SelectionAdapter() {
			boolean asc = true; // 记录上一次的排序方式，默认为升序

			public void widgetSelected(SelectionEvent e) {
				// asc=true则ID的升序排序器，否则用降序
				// tv.setSorter(asc ? Table_Emp_Sorter.ID_ASC : Table_Emp_Sorter.ID_DESC);
				asc = !asc;// 得到下一次排序方式
			}
		});
		tc2.addSelectionListener(new SelectionAdapter() {
			boolean asc = true; // 记录上一次的排序方式，默认为升序

			public void widgetSelected(SelectionEvent e) {
				// asc=true则ID的升序排序器，否则用降序
				// tv.setSorter(Table_Emp_Sorter.ID_DESC);
				asc = !asc;// 得到下一次排序方式
			}
		});

		tc3.addSelectionListener(new SelectionAdapter() {
			boolean asc = true; // 记录上一次的排序方式，默认为升序

			public void widgetSelected(SelectionEvent e) {
				// asc=true则ID的升序排序器，否则用降序
				// tv.setSorter(asc ? Table_Emp_Sorter.ID_ASC : Table_Emp_Sorter.ID_DESC);
				asc = !asc;// 得到下一次排序方式
			}
		});
	}

	// 初始化对话框
	public void setText() {
		text.setText(operatorid);
		if (operatorname != null)
			text_1.setText(operatorname);
		else
			text_1.setText("");
		if (identification != null)
			text_3.setText(identification);
		else
			text_3.setText("");
		if (phonenumber != null)
			text_4.setText(phonenumber);
		else
			text_4.setText("");
		if (address != null)
			text_5.setText(address);
		else
			text_5.setText("");
		if (remark != null)
			text_6.setText(remark);
		else
			text_6.setText("");
		combo.select(priority);
	}

	// 清空对话框
	public void setText_Null() {
		text.setText("");
		text_1.setText("");
		text_3.setText("");
		text_4.setText("");
		text_5.setText("");
		text_6.setText("");
		combo.select(priority);
		user = null;
	}

	// 初始化表格值
	public void setTableValue(Table table, TEmp temp) {
		TableItem newTableItem = new TableItem(table, SWT.BORDER);
		newTableItem.setText(0, temp.getOperatorid());// 表的第一字段填值
		if (temp.getOperatorname() != null)
			newTableItem.setText(1, temp.getOperatorname());
		if (temp.getPriority() == 0) {
			newTableItem.setText(2, "管理员");
		} else {
			newTableItem.setText(2, "操作员");
		}
		if (temp.getIdentification() != null)
			newTableItem.setText(3, temp.getIdentification());
		if (temp.getPhonenumber() != null)
			newTableItem.setText(4, temp.getPhonenumber());
	}

	public void refresh(Table table, List<Object> allEmp, Composite parent) { // 刷新
		table.clearAll();
		TableItem[] cols = table.getItems();
		for (int i = 0; i < cols.length; i++) {
			TableItem tc = cols[i];
			tc.dispose();
		}
		// empservice = new EmpService();
		try {
			if (allEmp.size() <= 0) {
				MessageDialog.openInformation(parent.getShell(), "对不起", "没有数据");
			}
			for (int i = 0; i < allEmp.size(); i++) {
				TEmp temp = (TEmp) allEmp.get(i);
				setTableValue(table, temp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createPartControl(final Composite parent) {
		parent.pack();
		user = ValidateStatusAction.user;
		user_admin = user;
		setStr(user);
		empservice = new EmpService();
		GridLayout gl_parent = new GridLayout(1, true);
		gl_parent.marginBottom = 3;
		gl_parent.marginTop = 4;
		gl_parent.marginLeft = 7;
		parent.setLayout(gl_parent);

		Group group = new Group(parent, SWT.BORDER);
		GridData gd_group = new GridData(GridData.FILL_HORIZONTAL);
		gd_group.verticalAlignment = SWT.FILL;
		group.setLayoutData(gd_group);
//		GridData gd_group = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
//		gd_group.heightHint = 434;
//		group.setLayoutData(gd_group);
		group.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		GridLayout gl_group = new GridLayout(1, true);
		gl_group.marginBottom = 1;
		gl_group.marginRight = 1;
		gl_group.marginTop = 1;
		gl_group.marginLeft = 1;
		gl_group.marginWidth = 7;
		group.setLayout(gl_group);

		Group group_2 = new Group(group, SWT.NONE);
		GridData gd_group_2 = new GridData(GridData.FILL_HORIZONTAL);
		gd_group_2.verticalAlignment = SWT.FILL;
		group_2.setLayoutData(gd_group_2);
		group_2.setLayout(new GridLayout(2, true));

		Composite composite_left = new Composite(group_2, SWT.NONE);
		GridLayout gl_composite_left = new GridLayout(2, false);
		gl_composite_left.horizontalSpacing = 10;
		gl_composite_left.marginTop = 3;
		gl_composite_left.marginLeft = 1;
		gl_composite_left.marginWidth = 9;
		gl_composite_left.marginHeight = 18;
		gl_composite_left.verticalSpacing = 18;
		composite_left.setLayout(gl_composite_left);
		// composite_left.setSize(320, 154);
		// composite_left.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		GridData gd_composite_left = new GridData(GridData.FILL_HORIZONTAL);
		gd_composite_left.widthHint = 300;
		gd_composite_left.verticalAlignment = SWT.CENTER;
		gd_composite_left.minimumHeight = 4;
		gd_composite_left.heightHint = 201;
		gd_composite_left.horizontalAlignment = SWT.CENTER;
		composite_left.setLayoutData(gd_composite_left);
		Label lbl_Id = new Label(composite_left, SWT.NONE);
		lbl_Id.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lbl_Id.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		lbl_Id.setText("\u64CD\u4F5C\u5458ID");

		text = new Text(composite_left, SWT.BORDER);
		GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text.widthHint = 172;
		text.setLayoutData(gd_text);

		Label label_name = new Label(composite_left, SWT.NONE);
		label_name.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label_name.setText("\u64CD\u4F5C\u5458\u59D3\u540D");

		text_1 = new Text(composite_left, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_1.widthHint = 173;
		text_1.setLayoutData(gd_text_1);

		Label label_ident = new Label(composite_left, SWT.NONE);
		label_ident.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label_ident.setText("\u8BC1\u4EF6\u53F7\u7801");

		text_3 = new Text(composite_left, SWT.BORDER);
		GridData gd_text_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_3.widthHint = 172;
		text_3.setLayoutData(gd_text_3);

		Label label_phone = new Label(composite_left, SWT.NONE);
		label_phone.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label_phone.setText("\u7535\u8BDD\u53F7\u7801");

		text_4 = new Text(composite_left, SWT.BORDER);
		GridData gd_text_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_4.widthHint = 172;
		text_4.setLayoutData(gd_text_4);

		Label label_address = new Label(composite_left, SWT.NONE);
		label_address.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label_address.setText("\u4F4F\u5740");

		text_5 = new Text(composite_left, SWT.BORDER);
		GridData gd_text_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_5.widthHint = 171;
		text_5.setLayoutData(gd_text_5);

		Composite composite_rigtht = new Composite(group_2, SWT.NONE);
		GridData gd_composite_rigtht = new GridData(GridData.FILL_HORIZONTAL);
		gd_composite_rigtht.horizontalAlignment = SWT.CENTER;
		composite_rigtht.setLayoutData(gd_composite_rigtht);
		composite_rigtht.setSize(230, 208);

		Label label_priority = new Label(composite_rigtht, SWT.NONE);
		label_priority.setBounds(10, 26, 54, 12);
		label_priority.setText("\u64CD\u4F5C\u6743\u9650");

		combo = new Combo(composite_rigtht, SWT.NONE);
		combo.setBounds(76, 23, 87, 20);
		combo.add("管理员", 0);
		combo.add("操作员", 1);
		combo.select(0);
		Label label_remark = new Label(composite_rigtht, SWT.NONE);
		label_remark.setBounds(10, 64, 76, 18);
		label_remark.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label_remark.setText("\u5907\u6CE8");

		text_6 = new Text(composite_rigtht, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		text_6.setBounds(10, 96, 220, 112);
		text_6.setText("无");
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// text_1.setText("你选的是"+combo.getSelectionIndex()+ combo.getText());
			}
		});
		new Label(group, SWT.NONE);

		Group group_1 = new Group(group, SWT.NONE);
		GridLayout gl_group_1 = new GridLayout(6, false);
		gl_group_1.marginWidth = 21;
		gl_group_1.horizontalSpacing = 10;
		gl_group_1.marginLeft = 10;
		group_1.setLayout(gl_group_1);
		group_1.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));

		Button button_refresh = new Button(group_1, SWT.CENTER);
		GridData gd_button_refresh = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 1);
		gd_button_refresh.widthHint = 67;
		button_refresh.setLayoutData(gd_button_refresh);
		button_refresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					List<Object> allEmp = empservice.getAllEmp();
					refresh(table, allEmp, parent);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_refresh.setText("\u5237\u65B0");

		Button button_reset = new Button(group_1, SWT.NONE);
		GridData gd_button_reset = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 1);
		gd_button_reset.widthHint = 69;
		button_reset.setLayoutData(gd_button_reset);
		button_reset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 清空
				setText_Null();

			}
		});
		button_reset.setText("\u6E05\u7A7A");

		Button button_add = new Button(group_1, SWT.NONE);
		GridData gd_button_add = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 1);
		gd_button_add.widthHint = 68;
		button_add.setLayoutData(gd_button_add);
		button_add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// empservice = new EmpService();
				TEmp u = null;
				u = setTEmp(parent);
				// String uid = u.getOperatorid();
				String pa = null;
				if (pa != "" && pa != null) {
					try {
						// Hibernate需要配置
						empservice.addEmp(u);
						MessageBox messaa = new MessageBox(parent.getShell(), SWT.OK | SWT.ICON_INFORMATION);
						messaa.setText("添加成功");
						messaa.setMessage("添加用户成功");
						messaa.open();
						List<Object> allEmp = empservice.getAllEmp();
						refresh(table, allEmp, parent);
						pa = "";
						// MessageDialog.openInformation(window.getShell(), "测试",
						// "卡片管理,只有管理员能点击这个，有木有?");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					MessageDialog.openInformation(parent.getShell(), "对不起", "请重新添加");
				}

//				new Thread(new Runnable(){
//					public void run() {
//					}
//				}).start();																				
			}
		});
		button_add.setText("\u6DFB\u52A0");

		Button button_modefy = new Button(group_1, SWT.NONE);
		GridData gd_button_modefy = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 1);
		gd_button_modefy.widthHint = 69;
		button_modefy.setLayoutData(gd_button_modefy);
		button_modefy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (user == null) {
					MessageDialog.openInformation(parent.getShell(), "对不起", "请选择用户再修改！");
				} else {
					TEmp newuser = new TEmp();
					newuser = setTEmp(parent, user);
					MessageDialog.openInformation(parent.getShell(), "对不起", text_1.getText());
					if (newuser.getOperatorid() != null) {
						try {
							empservice.updateEmp(newuser);
							MessageBox messaa = new MessageBox(parent.getShell(), SWT.OK | SWT.ICON_INFORMATION);
							messaa.setText("修改成功");
							messaa.setMessage("修改用户成功");
							messaa.open();
							List<Object> allEmp = empservice.getAllEmp();
							refresh(table, allEmp, parent);
							// MessageDialog.openInformation(window.getShell(), "测试",
							// "卡片管理,只有管理员能点击这个，有木有?");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

				}
			}
		});
		button_modefy.setText("\u4FEE\u6539");

		Button button_modefy_pass = new Button(group_1, SWT.NONE);
		GridData gd_button_modefy_pass = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 1);
		gd_button_modefy_pass.widthHint = 68;
		button_modefy_pass.setLayoutData(gd_button_modefy_pass);
		button_modefy_pass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (user == null) {
					MessageDialog.openInformation(parent.getShell(), "对不起", "请选择用户再修改密码！");
				} else {
					PasswordDialog passworddia = new PasswordDialog(new Shell(), SWT.NONE);
					passworddia.open();
//				password = passworddia.getPassword();
//				password = PasswordDialog.password;
					if (password != null && password != "") {
						user.setPassword(password);
						try {
							empservice.updateEmp(user);
							MessageBox messaa = new MessageBox(parent.getShell(), SWT.OK | SWT.ICON_INFORMATION);
							messaa.setText("修改成功");
							messaa.setMessage("密码修改成功" + password);
							messaa.open();
							password = "";
//						MessageDialog.openInformation(window.getShell(), "测试",
//						"卡片管理,只有管理员能点击这个，有木有?");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		button_modefy_pass.setText("\u4FEE\u6539\u5BC6\u7801");

		Button button_delete = new Button(group_1, SWT.NONE);
		GridData gd_button_delete = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 1);
		gd_button_delete.widthHint = 70;
		button_delete.setLayoutData(gd_button_delete);
		button_delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (user == null) {
					MessageDialog.openInformation(parent.getShell(), "对不起", "请选择用户再修改！");
				} else {
					try {
						MessageBox messageBox = new MessageBox(parent.getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
						messageBox.setMessage("确定删除吗？");
						int rc = messageBox.open();
						if (rc == SWT.YES) {
							empservice.delEmp(user);
							MessageBox messaa = new MessageBox(parent.getShell(), SWT.OK | SWT.ICON_INFORMATION);
							messaa.setText("删除成功");
							messaa.setMessage("删除成功");
							messaa.open();
							List<Object> allEmp = empservice.getAllEmp();
							refresh(table, allEmp, parent);
//						MessageDialog.openInformation(window.getShell(), "测试",
//						"卡片管理,只有管理员能点击这个，有木有?");
						} else {

						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		button_delete.setText("\u5220\u9664");

		Label label_1 = new Label(group_1, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));
		label_1.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label_1.setText("\u67E5\u8BE2\u6761\u4EF6");

		combo_find = new Combo(group_1, SWT.NONE);
		GridData gd_combo_find = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gd_combo_find.widthHint = 50;
		combo_find.setLayoutData(gd_combo_find);
		combo_find.add("操作员ID", 0);
		combo_find.add("操作员姓名", 1);
		combo_find.add("管理员", 2);
		combo_find.add("操作员", 3);
		combo_find.add("证件号码", 4);
		combo_find.select(0);

		text_find = new Text(group_1, SWT.BORDER);
		GridData gd_text_find = new GridData(SWT.LEFT, SWT.FILL, false, true, 2, 1);
		gd_text_find.widthHint = 140;
		text_find.setLayoutData(gd_text_find);

		Button button_find = new Button(group_1, SWT.NONE);
		GridData gd_button_find = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 1);
		gd_button_find.widthHint = 67;
		button_find.setLayoutData(gd_button_find);
		button_find.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int se = combo_find.getSelectionIndex();
				find_express = text_find.getText();
				if (se == 2 | se == 3) {
					find_what = "priority";
					find_express = String.valueOf(se - 2);
					List<Object> allEmp;
					try {
						allEmp = empservice.getEmpByFindExpress(find_what, find_express);
						refresh(table, allEmp, parent);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					if (text_find.getText() == null | text_find.getText() == "") {
						MessageDialog.openInformation(parent.getShell(), "对不起", "查询条件不能为空！");
					} else {
						find_express = text_find.getText();
						if (se == 0) {
							find_what = "operatorid";
						} else if (se == 1)
							find_what = "operatorname";
						else if (se == 4) {
							find_what = "identification";
						}
						List<Object> allEmp;
						try {
							allEmp = empservice.getEmpByFindExpress(find_what, find_express);
							refresh(table, allEmp, parent);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		button_find.setText("\u67E5\u8BE2");
		find_express = text_find.getText();
		combo_find.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// text_1.setText("你选的是"+combo.getSelectionIndex()+ combo.getText());
				int se = combo_find.getSelectionIndex();
				find_express = text_find.getText();
				if (se == 2 | se == 3) {
					find_what = "priority";
					find_express = String.valueOf(se - 2);
				} else if (se == 0) {
					find_what = "operatorid";
				} else if (se == 1)
					find_what = "operatorname";
				else if (se == 4)
					find_what = "identification";

			}
		});
		new Label(group, SWT.NONE);

		TableViewer tv = new TableViewer(group, SWT.BORDER | SWT.FULL_SELECTION);
		// table = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
		// composite_1.setLayoutData(new GridData(GridData.FILL_BOTH));
		table = tv.getTable();
		GridData gd_table = new GridData(GridData.FILL_BOTH);
		gd_table.heightHint = 200;
		gd_table.horizontalIndent = 3;
		gd_table.grabExcessVerticalSpace = true;
		gd_table.grabExcessHorizontalSpace = true;
		gd_table.verticalAlignment = SWT.FILL;
		// gd_table.heightHint = 124;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(new FillLayout(SWT.HORIZONTAL));
		setTable(tv);
		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				event.height = 18;
			}
		});
		table.addListener(SWT.MouseDoubleClick, new Listener() {
			public void handleEvent(Event event) {
				TableItem[] itemList = table.getItems();
				int listHaveChouse = table.getSelectionIndex();
				/*
				 * 你可以通过这个下标来取得选中的行的数据了.例如:取得所选行的第一个列属性
				 */
				String firstInfo = itemList[listHaveChouse].getText(0);
				try {
					user = empservice.getEmpById(firstInfo);
					setStr(user);
					setText();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// 初始化
		if (user_admin.getPriority() == ADMINSTRATOR) {
			// setText();
			setStr(user_admin);
			setText();
			try {
				List<Object> allEmp = empservice.getAllEmp();
				for (int i = 0; i < allEmp.size(); i++) {
					TEmp temp = (TEmp) allEmp.get(i);
					setTableValue(table, temp);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (user_admin.getPriority() == OPERATOR) {
			text_3.setText("你是操作员");
		}
		// ValidateStatusAction.user.getPriority() ;
	}

	public void setFocus() {
	}
}
