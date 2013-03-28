package cn.edu.csu.dbhospital.dialog;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cn.edu.csu.dbhospital.db.TArrangementManager;
import cn.edu.csu.dbhospital.db.TDoctorManager;
import cn.edu.csu.dbhospital.pojo.TArrangement;
import cn.edu.csu.dbhospital.pojo.TDoctor;
import cn.edu.csu.dbhospital.pojo.TTableArrangement;
import cn.edu.csu.dbhospital.util.SystemUtil;

public class TArrangementDialog extends TitleAreaDialog {
	private Text txt_expense;
	private Text txt_limit;
	private TArrangement arrangement;
	private TTableArrangement tableArrangement;
	private Button cbtn_use;
	private Combo cb_doctor;
	private TArrangementManager arrangementManager;
	private int column;
	private int type;// 0 添加，1修改
	private TDoctorManager doctorManager;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @param arrangement
	 * @param tableArrangement
	 * @param column
	 */
	public TArrangementDialog(Shell parentShell, TArrangement arrangement, TTableArrangement tableArrangement,
			int column) {
		super(parentShell);
		setHelpAvailable(false);
		this.arrangement = arrangement;
		this.tableArrangement = tableArrangement;
		this.column = column;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("\u8C03\u6574\u6392\u73ED");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FormLayout());
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.widthHint = 341;
		container.setLayoutData(gd_container);

		cbtn_use = new Button(container, SWT.CHECK);
		FormData fd_cbtn_use = new FormData();
		fd_cbtn_use.left = new FormAttachment(0, 49);
		cbtn_use.setLayoutData(fd_cbtn_use);
		cbtn_use.setText("\u542F\u7528\u8BE5\u73ED\u6B21\u7684\u6392\u73ED");
		cbtn_use.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (cbtn_use.getSelection()) {
					cb_doctor.setEnabled(true);
					txt_expense.setEnabled(true);
					txt_limit.setEnabled(true);
				} else {
					cb_doctor.setEnabled(false);
					txt_expense.setEnabled(false);
					txt_limit.setEnabled(false);
				}
			}
		});

		Label label = new Label(container, SWT.NONE);
		FormData fd_label = new FormData();
		label.setLayoutData(fd_label);
		label.setText("\u8BE5\u73ED\u533B\u751F");

		cb_doctor = new Combo(container, SWT.NONE);
		fd_cbtn_use.bottom = new FormAttachment(cb_doctor, -21);
		fd_label.right = new FormAttachment(100, -249);
		FormData fd_cb_doctor = new FormData();
		fd_cb_doctor.right = new FormAttachment(100, -145);
		fd_cb_doctor.left = new FormAttachment(label, 31);
		cb_doctor.setLayoutData(fd_cb_doctor);

		Label label_1 = new Label(container, SWT.NONE);
		fd_label.bottom = new FormAttachment(label_1, -18);
		FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(0, 103);
		fd_label_1.left = new FormAttachment(label, 0, SWT.LEFT);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("\u8BCA\u7597\u8D39\u7528");

		txt_expense = new Text(container, SWT.BORDER);
		fd_cb_doctor.bottom = new FormAttachment(txt_expense, -11);
		FormData fd_txt_expense = new FormData();
		fd_txt_expense.width = 60;
		fd_txt_expense.top = new FormAttachment(label_1, -3, SWT.TOP);
		txt_expense.setLayoutData(fd_txt_expense);

		Label label_2 = new Label(container, SWT.NONE);
		FormData fd_label_2 = new FormData();
		label_2.setLayoutData(fd_label_2);
		label_2.setText("\u4EBA\u6570\u4E0A\u9650");

		txt_limit = new Text(container, SWT.BORDER);
		fd_label_2.right = new FormAttachment(txt_limit, -27);
		fd_txt_expense.left = new FormAttachment(0, 126);
		fd_label_2.top = new FormAttachment(0, 147);
		FormData fd_txt_limit = new FormData();
		fd_txt_limit.left = new FormAttachment(0, 126);
		fd_txt_limit.width = 60;
		fd_txt_limit.top = new FormAttachment(label_2, -3, SWT.TOP);
		txt_limit.setLayoutData(fd_txt_limit);

		Label label_3 = new Label(container, SWT.NONE);
		fd_txt_expense.right = new FormAttachment(label_3, -19);
		FormData fd_label_3 = new FormData();
		fd_label_3.top = new FormAttachment(0, 103);
		fd_label_3.right = new FormAttachment(100, -84);
		fd_label_3.left = new FormAttachment(0, 214);
		label_3.setLayoutData(fd_label_3);
		label_3.setText("\u5143");

		Label label_4 = new Label(container, SWT.NONE);
		FormData fd_label_4 = new FormData();
		fd_label_4.top = new FormAttachment(label_2, 0, SWT.TOP);
		fd_label_4.right = new FormAttachment(100, -84);
		fd_label_4.left = new FormAttachment(0, 212);
		label_4.setLayoutData(fd_label_4);
		label_4.setText("\u4EBA");

		initData();

		return area;
	}

	// 初始化数据
	private void initData() {
		doctorManager = new TDoctorManager();
		try {
			ArrayList<TDoctor> doctorList = doctorManager.searchByRoom(tableArrangement.room.getId());
			for (TDoctor doctor : doctorList) {
				cb_doctor.add(doctor.getName());
				cb_doctor.setData(doctor.getName(), doctor);
			}
			if (doctorList.size() > 0) {
				cb_doctor.setText(doctorList.get(0).getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		txt_expense.setText(SystemUtil.EXPENSE_DEFAULT + "");
		txt_limit.setText(SystemUtil.LIMIT_DEFAULT + "");
		// 初始化界面
		if (arrangement == null) {
			type = 0;
			cbtn_use.setSelection(false);
			cb_doctor.setEnabled(false);
			txt_expense.setEnabled(false);
			txt_limit.setEnabled(false);
		} else {
			type = 1;
			cbtn_use.setSelection(true);
			cb_doctor.setEnabled(true);
			txt_expense.setEnabled(true);
			txt_limit.setEnabled(true);

			txt_expense.setText(arrangement.getExpense() + "");
			txt_limit.setText(arrangement.getLimit() + "");
			try {
				cb_doctor.setText(doctorManager.searchById(arrangement.getDoctorId()).getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, "\u6DFB\u52A0", true);
		button.setText("\u786E\u5B9A");
		createButton(parent, IDialogConstants.CANCEL_ID, "\u53D6\u6D88", false);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		arrangementManager = new TArrangementManager();
		if (buttonId == IDialogConstants.OK_ID) {
			if (!cbtn_use.getSelection()) {// 没有启用排班，则删除原有排班，并设置排班为null
				if (type == 1) {
					arrangementManager.delete(arrangement);// 删除！可能已经有预约了！
				}
				arrangement = null;
				okPressed();
				return;// 这句还是要有的
			}
			// 启用了
			if ("".equalsIgnoreCase(txt_limit.getText()) || "".equalsIgnoreCase(txt_expense.getText())
					|| "".equalsIgnoreCase(cb_doctor.getText())) {
				setMessage("所有信息不能为空");
				return;
			}
			if (type == 0) {
				arrangement = new TArrangement();
			}
			arrangement.setDate(tableArrangement.date);
			arrangement.setDoctorId(((TDoctor) cb_doctor.getData(cb_doctor.getText())).getId());
			arrangement.setExpense(Float.parseFloat(txt_expense.getText()));
			arrangement.setLimit(Integer.parseInt(txt_limit.getText()));
			arrangement.setNum(column - 1);
			arrangement.setRoomId(tableArrangement.room.getId());

			try {
				if (type == 0) {
					arrangementManager.add(arrangement);
					arrangement = arrangementManager.searchByRoomDateNum(arrangement.getRoomId(),
							arrangement.getDate(), arrangement.getNum());// 这个时候就有id值了
				} else {
					arrangementManager.update(arrangement);
				}
			} catch (Exception e) {
				setReturnCode(SystemUtil.RESULT_FAIL);
				close();
			}
			okPressed();
		} else {
			cancelPressed();
		}
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(350, 330);
	}
}
