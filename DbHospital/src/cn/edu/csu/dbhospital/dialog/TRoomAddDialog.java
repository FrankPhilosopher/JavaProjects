package cn.edu.csu.dbhospital.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cn.edu.csu.dbhospital.db.TRoomManager;
import cn.edu.csu.dbhospital.pojo.TRoom;
import cn.edu.csu.dbhospital.util.SystemUtil;

public class TRoomAddDialog extends TitleAreaDialog {
	private Text txt_name;
	private Text txt_info;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public TRoomAddDialog(Shell parentShell) {
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
		setTitle("\u6DFB\u52A0\u79D1\u5BA4");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FormLayout());
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.widthHint = 341;
		container.setLayoutData(gd_container);

		Label label = new Label(container, SWT.NONE);
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(0, 32);
		fd_label.bottom = new FormAttachment(100, -89);
		label.setLayoutData(fd_label);
		label.setText("\u79D1\u5BA4\u540D\u79F0");

		txt_name = new Text(container, SWT.BORDER);
		FormData fd_txt_name = new FormData();
		fd_txt_name.left = new FormAttachment(label, 18);
		fd_txt_name.right = new FormAttachment(100, -36);
		txt_name.setLayoutData(fd_txt_name);

		Label label_1 = new Label(container, SWT.NONE);
		FormData fd_label_1 = new FormData();
		fd_label_1.bottom = new FormAttachment(100, -40);
		fd_label_1.top = new FormAttachment(label, 32);
		fd_label_1.left = new FormAttachment(label, 0, SWT.LEFT);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("\u79D1\u5BA4\u4FE1\u606F");

		txt_info = new Text(container, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		fd_txt_name.bottom = new FormAttachment(txt_info, -26);
		FormData fd_txt_infos = new FormData();
		fd_txt_infos.right = new FormAttachment(txt_name, 0, SWT.RIGHT);
		fd_txt_infos.bottom = new FormAttachment(100, -10);
		fd_txt_infos.left = new FormAttachment(label_1, 18);
		fd_txt_infos.top = new FormAttachment(0, 84);
		txt_info.setLayoutData(fd_txt_infos);

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "\u6DFB\u52A0", true);
		createButton(parent, IDialogConstants.CANCEL_ID, "\u53D6\u6D88", false);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			if ("".equalsIgnoreCase(txt_name.getText()) || "".equalsIgnoreCase(txt_info.getText())) {
				setMessage("科室名称和信息不能为空");
				return;
			}
			TRoomManager tRoomManager = new TRoomManager();
			TRoom room = new TRoom();
			room.setName(txt_name.getText());
			room.setInfo(txt_info.getText());
			try {
				tRoomManager.add(room);// 按理说，这里对数据库的操作也是要放到线程中去执行的，但是由于时间太短，就算了！
			} catch (Exception e) {
				// e.printStackTrace();
				// cancelPressed();//not
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
		return new Point(350, 300);
	}
}
