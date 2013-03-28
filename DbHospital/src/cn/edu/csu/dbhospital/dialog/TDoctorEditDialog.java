package cn.edu.csu.dbhospital.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cn.edu.csu.dbhospital.db.TDoctorManager;
import cn.edu.csu.dbhospital.db.TRoomManager;
import cn.edu.csu.dbhospital.pojo.TDoctor;
import cn.edu.csu.dbhospital.pojo.TRoom;
import cn.edu.csu.dbhospital.util.SystemUtil;

public class TDoctorEditDialog extends TitleAreaDialog {
	private Text txt_name;
	private Text txt_info;
	private Text txt_phone;
	private Text txt_title;
	private Combo cb_room;
	private Combo cb_gander;
	private TRoomManager roomManager = new TRoomManager();
	private TDoctor doctor;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public TDoctorEditDialog(Shell parentShell, TDoctor doctor) {
		super(parentShell);
		setHelpAvailable(false);
		this.doctor = doctor;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("\u4FEE\u6539\u533B\u751F\u4FE1\u606F");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FormLayout());
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.widthHint = 341;
		container.setLayoutData(gd_container);

		Label label = new Label(container, SWT.NONE);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0, 26);
		label.setLayoutData(fd_label);
		label.setText("\u533B\u751F\u59D3\u540D");

		txt_name = new Text(container, SWT.BORDER);
		fd_label.right = new FormAttachment(txt_name, -18);
		FormData fd_txt_name = new FormData();
		fd_txt_name.top = new FormAttachment(label, -3, SWT.TOP);
		fd_txt_name.left = new FormAttachment(0, 98);
		fd_txt_name.right = new FormAttachment(100, -36);
		txt_name.setLayoutData(fd_txt_name);

		Label label_2 = new Label(container, SWT.NONE);
		FormData fd_label_2 = new FormData();
		fd_label_2.top = new FormAttachment(label, 25);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("\u533B\u751F\u6027\u522B");

		cb_gander = new Combo(container, SWT.NONE);
		fd_label_2.right = new FormAttachment(cb_gander, -18);
		FormData fd_cb_gander = new FormData();
		fd_cb_gander.right = new FormAttachment(0, 161);
		fd_cb_gander.top = new FormAttachment(txt_name, 18);
		fd_cb_gander.left = new FormAttachment(0, 98);
		cb_gander.setLayoutData(fd_cb_gander);
		cb_gander.add(SystemUtil.GANSER_MALE);
		cb_gander.add(SystemUtil.GANSER_FEMALE);
		cb_gander.setData(SystemUtil.GANSER_FEMALE, SystemUtil.GANSER_FEMALE_VALUE);
		cb_gander.setData(SystemUtil.GANSER_MALE, SystemUtil.GANSER_MALE_VALUE);
		cb_gander.setText(SystemUtil.GANSER_MALE);

		Label label_3 = new Label(container, SWT.NONE);
		FormData fd_label_3 = new FormData();
		fd_label_3.bottom = new FormAttachment(100, -72);
		label_3.setLayoutData(fd_label_3);
		label_3.setText("\u8054\u7CFB\u65B9\u5F0F");

		txt_phone = new Text(container, SWT.BORDER);
		fd_label_3.right = new FormAttachment(txt_phone, -18);
		FormData fd_txt_phone = new FormData();
		fd_txt_phone.top = new FormAttachment(label_3, -3, SWT.TOP);
		fd_txt_phone.left = new FormAttachment(0, 98);
		fd_txt_phone.right = new FormAttachment(100, -36);
		txt_phone.setLayoutData(fd_txt_phone);

		Label label_4 = new Label(container, SWT.NONE);
		FormData fd_label_4 = new FormData();
		fd_label_4.right = new FormAttachment(0, 80);
		fd_label_4.left = new FormAttachment(0, 32);
		fd_label_4.bottom = new FormAttachment(label_3, -26);
		label_4.setLayoutData(fd_label_4);
		label_4.setText("\u533B\u751F\u804C\u79F0");

		txt_title = new Text(container, SWT.BORDER);
		FormData fd_txt_title = new FormData();
		fd_txt_title.right = new FormAttachment(100, -36);
		fd_txt_title.left = new FormAttachment(0, 98);
		txt_title.setLayoutData(fd_txt_title);

		Label label_5 = new Label(container, SWT.NONE);
		FormData fd_label_5 = new FormData();
		fd_label_5.top = new FormAttachment(label_2, 29);
		fd_label_5.left = new FormAttachment(label, 0, SWT.LEFT);
		label_5.setLayoutData(fd_label_5);
		label_5.setText("\u6240\u5728\u79D1\u5BA4");

		cb_room = new Combo(container, SWT.NONE);
		fd_txt_title.top = new FormAttachment(cb_room, 14);
		FormData fd_cb_room = new FormData();
		fd_cb_room.top = new FormAttachment(label_5, -4, SWT.TOP);
		fd_cb_room.right = new FormAttachment(txt_name, -74, SWT.RIGHT);
		fd_cb_room.left = new FormAttachment(label_5, 18);
		cb_room.setLayoutData(fd_cb_room);

		Label label_1 = new Label(container, SWT.NONE);
		FormData fd_label_1 = new FormData();
		fd_label_1.bottom = new FormAttachment(100, -29);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("\u533B\u751F\u4FE1\u606F");
		fd_label_1.top = new FormAttachment(label_3, 26);

		txt_info = new Text(container, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		FormData fd_txt_infos = new FormData();
		fd_txt_infos.bottom = new FormAttachment(100, -10);
		fd_txt_infos.right = new FormAttachment(100, -36);
		fd_txt_infos.left = new FormAttachment(0, 98);
		txt_info.setLayoutData(fd_txt_infos);
		fd_label_1.right = new FormAttachment(txt_info, -18);
		fd_txt_infos.top = new FormAttachment(label_1, -8, SWT.TOP);
		container.setTabList(new Control[] { txt_name, cb_gander, cb_room, txt_title, txt_phone, txt_info });

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

		initData();

		return area;
	}

	// 初始化数据
	private void initData() {
		if (doctor != null) {
			txt_name.setText(doctor.getName());
			txt_info.setText(doctor.getInfo());
			txt_title.setText(doctor.getTitle());
			txt_phone.setText(doctor.getPhone());
			if (doctor.getGander() == SystemUtil.GANSER_MALE_VALUE) {
				cb_gander.setText(SystemUtil.GANSER_MALE);
			} else {
				cb_gander.setText(SystemUtil.GANSER_FEMALE);
			}
			try {
				cb_room.setText(roomManager.searchByID(doctor.getRoomId()).getName());
			} catch (Exception e) {
				// e.printStackTrace();//
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
		createButton(parent, IDialogConstants.OK_ID, "\u4FEE\u6539", true);
		createButton(parent, IDialogConstants.CANCEL_ID, "\u53D6\u6D88", false);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			if ("".equalsIgnoreCase(txt_name.getText()) || "".equalsIgnoreCase(txt_info.getText())
					|| "".equalsIgnoreCase(txt_phone.getText()) || "".equalsIgnoreCase(txt_title.getText())
					|| "".equalsIgnoreCase(cb_gander.getText()) || "".equalsIgnoreCase(cb_room.getText())) {
				setMessage("所有信息都不能为空");
				return;
			}
			TDoctorManager manager = new TDoctorManager();
			doctor.setName(txt_name.getText());
			doctor.setInfo(txt_info.getText());
			doctor.setGander((int) (cb_gander.getData(cb_gander.getText())));
			doctor.setPhone(txt_phone.getText());
			doctor.setTitle(txt_title.getText());
			doctor.setRoomId(((TRoom) cb_room.getData(cb_room.getText())).getId());
			try {
				manager.update(doctor);
			} catch (Exception e) {
				// e.printStackTrace();
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
		return new Point(350, 440);
	}
}
