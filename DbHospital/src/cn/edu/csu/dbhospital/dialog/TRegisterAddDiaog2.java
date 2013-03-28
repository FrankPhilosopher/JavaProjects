package cn.edu.csu.dbhospital.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;

public class TRegisterAddDiaog2 extends TitleAreaDialog {
	private Text text;
	private Table table;
	private Text txt_expense;
	private Table table_1;
	private Text text_1;
	private Text text_2;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public TRegisterAddDiaog2(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("\u6302\u53F7");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.widthHint = 722;
		container.setLayoutData(gd_container);
		
		Group group = new Group(container, SWT.NONE);
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_group.heightHint = 154;
		gd_group.widthHint = 681;
		group.setLayoutData(gd_group);
		group.setText("\u9884\u7EA6\u6302\u53F7");
		
		Label label = new Label(group, SWT.NONE);
		label.setBounds(10, 23, 54, 17);
		label.setText("\u8BCA\u7597\u5361\u53F7");
		
		text = new Text(group, SWT.BORDER);
		text.setBounds(70, 20, 204, 23);
		
		Button button = new Button(group, SWT.NONE);
		button.setBounds(291, 18, 80, 27);
		button.setText("\u67E5\u8BE2");
		
		table = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 56, 359, 108);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tc_room = new TableColumn(table, SWT.NONE);
		tc_room.setWidth(100);
		tc_room.setText("\u79D1\u5BA4");
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("\u533B\u751F");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(155);
		tableColumn_1.setText("\u8D39\u7528");
		
		Composite composite = new Composite(group, SWT.NONE);
		composite.setBounds(390, 56, 287, 108);
		composite.setLayout(new FormLayout());
		
		Label label_1 = new Label(composite, SWT.NONE);
		FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(0, 23);
		fd_label_1.left = new FormAttachment(0, 10);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("\u5E94\u6536\u8D39\u7528\uFF1A");
		
		txt_expense = new Text(composite, SWT.BORDER);
		FormData fd_txt_expense = new FormData();
		fd_txt_expense.top = new FormAttachment(0, 17);
		fd_txt_expense.left = new FormAttachment(label_1, 6);
		txt_expense.setLayoutData(fd_txt_expense);
		
		Label label_2 = new Label(composite, SWT.NONE);
		fd_txt_expense.right = new FormAttachment(100, -138);
		FormData fd_label_2 = new FormData();
		fd_label_2.right = new FormAttachment(txt_expense, 18, SWT.RIGHT);
		fd_label_2.left = new FormAttachment(txt_expense, 6);
		fd_label_2.top = new FormAttachment(0, 23);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("\u5143");
		
		Button button_1 = new Button(composite, SWT.NONE);
		FormData fd_button_1 = new FormData();
		fd_button_1.bottom = new FormAttachment(label_1, 0, SWT.BOTTOM);
		fd_button_1.right = new FormAttachment(100, -25);
		fd_button_1.left = new FormAttachment(0, 202);
		button_1.setLayoutData(fd_button_1);
		button_1.setText("\u6302\u53F7");
		
		Label label_3 = new Label(composite, SWT.NONE);
		FormData fd_label_3 = new FormData();
		fd_label_3.top = new FormAttachment(label_1, 31);
		fd_label_3.left = new FormAttachment(label_1, 0, SWT.LEFT);
		label_3.setLayoutData(fd_label_3);
		label_3.setText("\u751F\u6210\u7684\u5019\u8BCA\u53F7");
		
		Label lbl_number = new Label(composite, SWT.NONE);
		lbl_number.setForeground(SWTResourceManager.getColor(255, 0, 0));
		FormData fd_lbl_number = new FormData();
		fd_lbl_number.top = new FormAttachment(label_3, 0, SWT.TOP);
		fd_lbl_number.left = new FormAttachment(label_3, 6);
		fd_lbl_number.right = new FormAttachment(100, -2);
		lbl_number.setLayoutData(fd_lbl_number);
		
		Group group_1 = new Group(container, SWT.NONE);
		group_1.setLayout(null);
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		group_1.setText("\u672A\u9884\u7EA6\u6302\u53F7");
		
		Label label_4 = new Label(group_1, SWT.NONE);
		label_4.setBounds(10, 29, 53, 17);
		label_4.setText("\u9009\u62E9\u79D1\u5BA4");
		
		Combo combo = new Combo(group_1, SWT.NONE);
		combo.setBounds(69, 26, 88, 25);
		
		table_1 = new Table(group_1, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(10, 62, 356, 101);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		
		TableColumn tableColumn_2 = new TableColumn(table_1, SWT.NONE);
		tableColumn_2.setWidth(53);
		tableColumn_2.setText("\u73ED\u6B21");
		
		TableColumn tableColumn_3 = new TableColumn(table_1, SWT.NONE);
		tableColumn_3.setWidth(80);
		tableColumn_3.setText("\u533B\u751F");
		
		TableColumn tableColumn_5 = new TableColumn(table_1, SWT.NONE);
		tableColumn_5.setWidth(107);
		tableColumn_5.setText("\u4EBA\u6570");
		
		TableColumn tableColumn_4 = new TableColumn(table_1, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("\u8D39\u7528");
		
		Composite composite_1 = new Composite(group_1, SWT.NONE);
		composite_1.setBounds(392, 62, 287, 101);
		composite_1.setLayout(new FormLayout());
		
		Label label_5 = new Label(composite_1, SWT.NONE);
		label_5.setText("\u5E94\u6536\u8D39\u7528\uFF1A");
		FormData fd_label_5 = new FormData();
		fd_label_5.top = new FormAttachment(0, 23);
		fd_label_5.left = new FormAttachment(0, 10);
		label_5.setLayoutData(fd_label_5);
		
		text_1 = new Text(composite_1, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.top = new FormAttachment(0, 17);
		fd_text_1.right = new FormAttachment(100, -138);
		fd_text_1.left = new FormAttachment(label_5, 6);
		text_1.setLayoutData(fd_text_1);
		
		Label label_6 = new Label(composite_1, SWT.NONE);
		label_6.setText("\u5143");
		FormData fd_label_6 = new FormData();
		fd_label_6.top = new FormAttachment(0, 23);
		fd_label_6.right = new FormAttachment(text_1, 18, SWT.RIGHT);
		fd_label_6.left = new FormAttachment(text_1, 6);
		label_6.setLayoutData(fd_label_6);
		
		Button button_2 = new Button(composite_1, SWT.NONE);
		button_2.setText("\u6302\u53F7");
		FormData fd_button_2 = new FormData();
		fd_button_2.bottom = new FormAttachment(label_5, 0, SWT.BOTTOM);
		fd_button_2.right = new FormAttachment(100, -25);
		fd_button_2.left = new FormAttachment(0, 202);
		button_2.setLayoutData(fd_button_2);
		
		Label label_7 = new Label(composite_1, SWT.NONE);
		label_7.setText("\u751F\u6210\u7684\u5019\u8BCA\u53F7");
		FormData fd_label_7 = new FormData();
		fd_label_7.top = new FormAttachment(label_5, 26);
		fd_label_7.left = new FormAttachment(label_5, 0, SWT.LEFT);
		label_7.setLayoutData(fd_label_7);
		
		Label label_8 = new Label(composite_1, SWT.NONE);
		label_8.setForeground(SWTResourceManager.getColor(255, 0, 0));
		FormData fd_label_8 = new FormData();
		fd_label_8.top = new FormAttachment(label_7, 0, SWT.TOP);
		fd_label_8.right = new FormAttachment(100);
		fd_label_8.left = new FormAttachment(0, 91);
		label_8.setLayoutData(fd_label_8);
		
		Label label_9 = new Label(group_1, SWT.NONE);
		label_9.setBounds(176, 29, 48, 17);
		label_9.setText("\u8BCA\u7597\u5361\u53F7");
		
		text_2 = new Text(group_1, SWT.BORDER);
		text_2.setBounds(230, 26, 204, 23);
		
		Button button_3 = new Button(group_1, SWT.NONE);
		button_3.setText("\u67E5\u8BE2");
		button_3.setBounds(449, 24, 80, 27);

		return area;
	}

	/**
	 * Create contents of the button bar.
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
