package cn.edu.csu.dbhospital.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.wb.swt.RcpTask;

import cn.edu.csu.dbhospital.db.TDirectoryManager;
import cn.edu.csu.dbhospital.pojo.TDirectory;
import cn.edu.csu.dbhospital.util.MessageDialogUtil;
import cn.edu.csu.dbhospital.util.SystemUtil;

public class DirectoryManageEditor extends EditorPart {

	public static final String ID = DirectoryManageEditor.class.getName();
	private Text txt_count1;
	private Text txt_count2;
	private Text txt_count3;

	private TDirectoryManager manager;

	public DirectoryManageEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Group group = new Group(parent, SWT.NONE);
		group.setLayout(new FormLayout());
		GridData gd_group = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_group.heightHint = 171;
		group.setLayoutData(gd_group);
		group.setText("\u9884\u7EA6\u6B21\u6570\u7684\u9650\u5236");

		Label lblNewLabel = new Label(group, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(0, 10);
		fd_lblNewLabel.left = new FormAttachment(0, 10);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel
				.setText("\u540C\u4E00\u60A3\u8005\u5B9E\u540D\uFF08\u6709\u6548\u8BC1\u4EF6\u53F7\uFF09\u5728\u540C\u4E00\u5C31\u8BCA\u65E5\u3001\u540C\u4E00\u79D1\u5BA4\u6700\u591A\u9884\u7EA6\u6B21\u6570");

		Label label = new Label(group, SWT.NONE);
		fd_lblNewLabel.bottom = new FormAttachment(label, -20);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0, 47);
		fd_label.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		label.setLayoutData(fd_label);
		label.setText("\u540C\u4E00\u60A3\u8005\u5B9E\u540D\uFF08\u6709\u6548\u8BC1\u4EF6\u53F7\uFF09\u5728\u540C\u4E00\u5C31\u8BCA\u65E5\u7684\u6700\u591A\u9884\u7EA6\u6B21\u6570");

		Label label_1 = new Label(group, SWT.NONE);
		label_1.setText("\u540C\u4E00\u60A3\u8005\u5B9E\u540D\uFF08\u6709\u6548\u8BC1\u4EF6\u53F7\uFF09\u5728\u540C\u4E00\u4E2A\u6708\u5185\u7684\u6700\u591A\u9884\u7EA6\u6B21\u6570");
		FormData fd_label_1 = new FormData();
		fd_label_1.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		fd_label_1.top = new FormAttachment(label, 20);
		label_1.setLayoutData(fd_label_1);

		txt_count1 = new Text(group, SWT.BORDER);
		FormData fd_txt_count1 = new FormData();
		fd_txt_count1.width = 50;
		fd_txt_count1.bottom = new FormAttachment(lblNewLabel, 0, SWT.BOTTOM);
		fd_txt_count1.left = new FormAttachment(lblNewLabel, 17);
		txt_count1.setLayoutData(fd_txt_count1);

		txt_count2 = new Text(group, SWT.BORDER);
		FormData fd_txt_count2 = new FormData();
		fd_txt_count2.width = 50;
		fd_txt_count2.left = new FormAttachment(label, 65);
		fd_txt_count2.bottom = new FormAttachment(label, 0, SWT.BOTTOM);
		txt_count2.setLayoutData(fd_txt_count2);

		txt_count3 = new Text(group, SWT.BORDER);
		FormData fd_txt_count3 = new FormData();
		fd_txt_count3.width = 50;
		fd_txt_count3.left = new FormAttachment(label_1, 65);
		fd_txt_count3.bottom = new FormAttachment(label_1, 0, SWT.BOTTOM);
		txt_count3.setLayoutData(fd_txt_count3);

		Button btn_save = new Button(group, SWT.NONE);
		FormData fd_btn_save = new FormData();
		fd_btn_save.top = new FormAttachment(label_1, 24);
		fd_btn_save.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		fd_btn_save.right = new FormAttachment(0, 100);
		btn_save.setLayoutData(fd_btn_save);
		btn_save.setText("\u4FDD\u5B58\u8BBE\u7F6E");
		btn_save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveData();
			}
		});

		initData();
	}

	// 初始化数据
	private void initData() {
		txt_count1.setText(SystemUtil.COUNT1_VALUE + "");
		txt_count2.setText(SystemUtil.COUNT2_VALUE + "");
		txt_count3.setText(SystemUtil.COUNT3_VALUE + "");
	}

	// 保存数据
	private void saveData() {
		manager = new TDirectoryManager();
		final String count1 = txt_count1.getText();
		final String count2 = txt_count2.getText();
		final String count3 = txt_count3.getText();

		new RcpTask() {

			@Override
			protected void doTaskInBackground(StringBuffer message) {
				try {
					manager.update(new TDirectory(SystemUtil.COUNT1, count1));
					manager.update(new TDirectory(SystemUtil.COUNT2, count2));
					manager.update(new TDirectory(SystemUtil.COUNT3, count3));
					message.append(RcpTask.RESULT_OK);
				} catch (Exception e) {
					message.append(RcpTask.RESULT_FAIL);
					return;
				}
			}

			@Override
			protected void doBeforeTask() {
				SystemUtil.showSystemMessage("正在保存配置...");
			}

			@Override
			protected void doAfterTask(String result) {
				if (result.equalsIgnoreCase(RcpTask.RESULT_OK)) {
					SystemUtil.showSystemMessage("保存成功");
				} else {
					MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "保存失败!");
				}
			}
		}.execTask();

	}

	@Override
	public void setFocus() {
	}
}
