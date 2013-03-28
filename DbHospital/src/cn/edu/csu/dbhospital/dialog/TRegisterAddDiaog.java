package cn.edu.csu.dbhospital.dialog;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.internal.handlers.WizardHandler.New;
import org.eclipse.wb.swt.SWTResourceManager;

import cn.edu.csu.dbhospital.db.TArrangementManager;
import cn.edu.csu.dbhospital.db.TRegisterManager;
import cn.edu.csu.dbhospital.db.TRoomManager;
import cn.edu.csu.dbhospital.db.TUserManager;
import cn.edu.csu.dbhospital.pojo.TRegister;
import cn.edu.csu.dbhospital.pojo.TRoom;
import cn.edu.csu.dbhospital.pojo.TTableGuahao;
import cn.edu.csu.dbhospital.pojo.TTableRegister;
import cn.edu.csu.dbhospital.pojo.TUser;
import cn.edu.csu.dbhospital.util.SystemUtil;

public class TRegisterAddDiaog extends TitleAreaDialog {
	private Text txt_cardnum;
	private Table table_yuyue;
	private Text txt_expense;
	private Table table_paiban;
	private Combo cb_room;

	private TUserManager userManager = new TUserManager();
	private TRoomManager roomManager = new TRoomManager();
	private TRegisterManager registerManager = new TRegisterManager();
	private TArrangementManager arrangementManager = new TArrangementManager();

	private Label lbl_number;
	private Text txt_doctor;
	private Text txt_room;
	private TableCursor cursor_yuyue;
	private TableCursor cursor_paiban;
	private Button btn_register;

	private int type;// ‘§‘ºπ“∫≈ ªπ « Œ¥‘§‘ºπ“∫≈
	private int id;// ‘§‘º£∫‘§‘ºπ“∫≈µƒid Œ¥‘§‘º£∫≈≈∞‡id

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public TRegisterAddDiaog(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("\u6302\u53F7");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.widthHint = 722;
		container.setLayoutData(gd_container);

		Group group = new Group(container, SWT.NONE);
		GridData gd_group = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_group.heightHint = 142;
		gd_group.widthHint = 415;
		group.setLayoutData(gd_group);
		group.setText("\u9884\u7EA6\u6302\u53F7");

		table_yuyue = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
		table_yuyue.setBounds(10, 31, 359, 108);
		table_yuyue.setHeaderVisible(true);
		table_yuyue.setLinesVisible(true);
		cursor_yuyue = new TableCursor(table_yuyue, SWT.NONE);
		cursor_yuyue.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (cursor_yuyue.getRow().getData() == null) {
					txt_doctor.setText("");
					txt_room.setText("");
					txt_expense.setText("");
					btn_register.setEnabled(false);
					return;
				}
				TTableRegister register = (TTableRegister) cursor_yuyue.getRow().getData();
				txt_doctor.setText(register.doctorName);
				txt_room.setText(register.roomName);
				txt_expense.setText(register.expense + "");
				btn_register.setEnabled(true);
				type = TRegister.TYPE_YUYUE;
				id = register.registerId;
			}
		});

		TableColumn tc_room = new TableColumn(table_yuyue, SWT.NONE);
		tc_room.setWidth(100);
		tc_room.setText("\u79D1\u5BA4");

		TableColumn tableColumn = new TableColumn(table_yuyue, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("\u533B\u751F");

		TableColumn tableColumn_1 = new TableColumn(table_yuyue, SWT.NONE);
		tableColumn_1.setWidth(155);
		tableColumn_1.setText("\u8D39\u7528");

		Composite composite_2 = new Composite(container, SWT.NONE);
		GridData gd_composite_2 = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_composite_2.widthHint = 261;
		composite_2.setLayoutData(gd_composite_2);

		txt_cardnum = new Text(composite_2, SWT.BORDER);
		txt_cardnum.setText("ZL37d7936");
		txt_cardnum.setBounds(10, 41, 204, 23);

		Button btn_search = new Button(composite_2, SWT.NONE);
		btn_search.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// ≤È—Ø÷∏∂®µƒ’Ô¡∆ø®–Ú∫≈µƒ‘§‘º
				if (txt_cardnum.getText().equalsIgnoreCase("")) {
					setErrorMessage("«Î ‰»Î“™≤È—Øµƒ’Ô¡∆ø®–Ú∫≈");
				}
				try {
					ArrayList<TTableRegister> tableRegisterList = registerManager.searchYuyueByCardnum(txt_cardnum
							.getText());
					table_yuyue.removeAll();
					for (int i = 0; i < tableRegisterList.size(); i++) {
						TableItem item = new TableItem(table_yuyue, SWT.NONE);
						item.setText(new String[] { tableRegisterList.get(i).roomName,
								tableRegisterList.get(i).doctorName, tableRegisterList.get(i).expense + "" });
						item.setData(tableRegisterList.get(i));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_search.setBounds(10, 89, 80, 27);
		btn_search.setText("\u67E5\u8BE2");

		Label label = new Label(composite_2, SWT.NONE);
		label.setText("\u8BCA\u7597\u5361\u53F7");
		label.setBounds(10, 18, 48, 17);

		Group group_1 = new Group(container, SWT.NONE);
		group_1.setLayout(null);
		GridData gd_group_1 = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_group_1.widthHint = 410;
		gd_group_1.heightHint = 161;
		group_1.setLayoutData(gd_group_1);
		group_1.setText("\u672A\u9884\u7EA6\u6302\u53F7");

		Label label_4 = new Label(group_1, SWT.NONE);
		label_4.setBounds(10, 29, 53, 17);
		label_4.setText("\u9009\u62E9\u79D1\u5BA4");

		cb_room = new Combo(group_1, SWT.NONE);
		cb_room.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				// ∏ƒ±‰ø∆ “¥•∑¢
				searchPaibanByRoom();
			}
		});
		cb_room.setBounds(69, 26, 88, 25);
		try {
			for (TRoom room : roomManager.searchAll()) {
				cb_room.add(room.getName());
				cb_room.setData(room.getName(), room);
			}
			if (cb_room.getItems().length > 0) {
				cb_room.setText(cb_room.getItem(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();//
		}

		table_paiban = new Table(group_1, SWT.BORDER | SWT.FULL_SELECTION);
		table_paiban.setBounds(10, 62, 356, 101);
		table_paiban.setHeaderVisible(true);
		table_paiban.setLinesVisible(true);

		cursor_paiban = new TableCursor(table_paiban, SWT.NONE);
		cursor_paiban.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (cursor_paiban.getRow().getData() == null) {
					txt_doctor.setText("");
					txt_room.setText("");
					txt_expense.setText("");
					btn_register.setEnabled(false);
					return;
				}
				TTableGuahao register = (TTableGuahao) cursor_paiban.getRow().getData();
				txt_doctor.setText(register.doctorName);
				txt_room.setText(cb_room.getText());
				txt_expense.setText(register.expense + "");
				btn_register.setEnabled(true);
				type = TRegister.TYPE_GUAHAO;
				id = register.aid;
			}
		});

		TableColumn tableColumn_2 = new TableColumn(table_paiban, SWT.NONE);
		tableColumn_2.setWidth(53);
		tableColumn_2.setText("\u73ED\u6B21");

		TableColumn tableColumn_3 = new TableColumn(table_paiban, SWT.NONE);
		tableColumn_3.setWidth(80);
		tableColumn_3.setText("\u533B\u751F");

		TableColumn tableColumn_5 = new TableColumn(table_paiban, SWT.NONE);
		tableColumn_5.setWidth(107);
		tableColumn_5.setText("\u4EBA\u6570");

		TableColumn tableColumn_4 = new TableColumn(table_paiban, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("\u8D39\u7528");

		Composite composite = new Composite(container, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
		gd_composite.widthHint = 259;
		gd_composite.heightHint = 204;
		composite.setLayoutData(gd_composite);
		composite.setLayout(new FormLayout());

		Label label_1 = new Label(composite, SWT.NONE);
		FormData fd_label_1 = new FormData();
		fd_label_1.left = new FormAttachment(0, 10);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("\u5E94\u6536\u8D39\u7528\uFF1A");

		txt_expense = new Text(composite, SWT.BORDER);
		txt_expense.setEnabled(false);
		txt_expense.setEditable(false);
		FormData fd_txt_expense = new FormData();
		fd_txt_expense.left = new FormAttachment(label_1, 6);
		fd_txt_expense.top = new FormAttachment(label_1, -3, SWT.TOP);
		txt_expense.setLayoutData(fd_txt_expense);

		Label label_2 = new Label(composite, SWT.NONE);
		fd_txt_expense.right = new FormAttachment(100, -125);
		FormData fd_label_2 = new FormData();
		fd_label_2.top = new FormAttachment(label_1, 0, SWT.TOP);
		fd_label_2.left = new FormAttachment(txt_expense, 6);
		fd_label_2.right = new FormAttachment(100, -100);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("\u5143");

		btn_register = new Button(composite, SWT.NONE);
		btn_register.setEnabled(false);
		btn_register.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// π“∫≈≤Ÿ◊˜
				String number = SystemUtil.buildRegisterNumber();
				lbl_number.setText(number);
				try {
					if (type == TRegister.TYPE_YUYUE) {
						TRegister register = registerManager.searchByID(id);
						register.setNumber(number);
						register.setType(TRegister.TYPE_GUAHAO);
						registerManager.update(register);
					} else {
						TUser user = userManager.searchByCardnum(txt_cardnum.getText());
						TRegister register = new TRegister();
						register.setArrangementId(id);
						register.setNumber(number);
						register.setType(TRegister.TYPE_GUAHAO);
						register.setUserId(user.getId());
						register.setValidate(TRegister.VALIDATE_YES);
						registerManager.add(register);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		FormData fd_btn_register = new FormData();
		fd_btn_register.top = new FormAttachment(label_1, -5, SWT.TOP);
		fd_btn_register.right = new FormAttachment(100, -10);
		fd_btn_register.left = new FormAttachment(0, 179);
		btn_register.setLayoutData(fd_btn_register);
		btn_register.setText("\u6302\u53F7");

		Label label_3 = new Label(composite, SWT.NONE);
		fd_label_1.bottom = new FormAttachment(label_3, -26);
		FormData fd_label_3 = new FormData();
		fd_label_3.left = new FormAttachment(0, 10);
		label_3.setLayoutData(fd_label_3);
		label_3.setText("\u751F\u6210\u7684\u5019\u8BCA\u53F7");

		lbl_number = new Label(composite, SWT.NONE);
		fd_label_3.bottom = new FormAttachment(lbl_number, -19);
		lbl_number.setForeground(SWTResourceManager.getColor(255, 0, 0));
		FormData fd_lbl_number = new FormData();
		fd_lbl_number.right = new FormAttachment(100, -61);
		fd_lbl_number.left = new FormAttachment(0, 10);
		fd_lbl_number.top = new FormAttachment(0, 170);
		fd_lbl_number.bottom = new FormAttachment(100, -28);
		lbl_number.setLayoutData(fd_lbl_number);

		Label label_5 = new Label(composite, SWT.NONE);
		FormData fd_label_5 = new FormData();
		fd_label_5.left = new FormAttachment(0, 10);
		fd_label_5.top = new FormAttachment(0, 20);
		label_5.setLayoutData(fd_label_5);
		label_5.setText("\u533B\u751F");

		txt_doctor = new Text(composite, SWT.BORDER);
		fd_label_5.right = new FormAttachment(txt_doctor, -16);
		txt_doctor.setEnabled(false);
		txt_doctor.setEditable(false);
		FormData fd_txt_doctor = new FormData();
		fd_txt_doctor.left = new FormAttachment(0, 73);
		txt_doctor.setLayoutData(fd_txt_doctor);

		Label label_6 = new Label(composite, SWT.NONE);
		FormData fd_label_6 = new FormData();
		fd_label_6.top = new FormAttachment(label_5, 12);
		fd_label_6.left = new FormAttachment(label_1, 0, SWT.LEFT);
		fd_label_6.right = new FormAttachment(0, 57);
		label_6.setLayoutData(fd_label_6);
		label_6.setText("\u79D1\u5BA4");

		txt_room = new Text(composite, SWT.BORDER);
		fd_txt_doctor.bottom = new FormAttachment(txt_room, -9);
		txt_room.setEnabled(false);
		txt_room.setEditable(false);
		FormData fd_txt_room = new FormData();
		fd_txt_room.left = new FormAttachment(txt_doctor, 0, SWT.LEFT);
		fd_txt_room.top = new FormAttachment(label_6, -3, SWT.TOP);
		txt_room.setLayoutData(fd_txt_room);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		initData();

		return area;
	}

	private void initData() {
		searchPaibanByRoom();
	}

	protected void searchPaibanByRoom() {
		if (table_paiban == null) {
			return;
		}
		int roomid = ((TRoom) cb_room.getData(cb_room.getText())).getId();
		try {
			ArrayList<TTableGuahao> tableGuahaoList = arrangementManager.searchByRoom(roomid);
			table_paiban.removeAll();
			for (int i = 0; i < tableGuahaoList.size(); i++) {
				TableItem item = new TableItem(table_paiban, SWT.NONE);
				item.setText(new String[] { tableGuahaoList.get(i).num == SystemUtil.ARRANGEMENT_ONE ? "…œŒÁ∞‡" : "œ¬ŒÁ∞‡",
						tableGuahaoList.get(i).doctorName, tableGuahaoList.get(i).limit + "",
						tableGuahaoList.get(i).expense + "" });
				item.setData(tableGuahaoList.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			okPressed();
		} else {
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
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		button.setText("\u786E\u5B9A");
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		button_1.setText("\u53D6\u6D88");
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(700, 537);
	}
}
