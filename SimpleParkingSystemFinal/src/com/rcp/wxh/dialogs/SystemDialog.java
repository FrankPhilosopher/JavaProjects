package com.rcp.wxh.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.rcp.wxh.pojo.TSystem;
import com.rcp.wxh.service.impl.CarEnterService;
import com.rcp.wxh.service.impl.SystemService;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

public class SystemDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text totalnumber;
	private CLabel tip;
	private CLabel usednumber;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public SystemDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
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
		shell.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/SYSTEM_MANAGE.png"));
		shell.setSize(492, 369);
		shell.setLocation(getParent().getLocation().x + 300, getParent().getLocation().y + 150);
		shell.setText("\u7CFB\u7EDF\u8BBE\u7F6E");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		CTabFolder tabFolder = new CTabFolder(shell, SWT.BORDER);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("\u8F66\u4F4D\u4FE1\u606F");
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tabItem.setControl(composite);
		composite.setLayout(null);
		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (updateSysteminfo()) {
					MessageDialogUtil.showInfoMessage(shell, "车位信息更新成功!");
				}
			}
		});
		button.setBounds(294, 266, 80, 27);
		button.setText("\u5E94    \u7528");
		Button button_1 = new Button(composite, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (updateSysteminfo()) {
					MessageDialogUtil.showInfoMessage(shell, "车位信息更新成功!");
					shell.dispose();
				}
			}
		});
		button_1.setBounds(390, 266, 80, 27);
		button_1.setText("\u786E    \u5B9A");
		tip = new CLabel(composite, SWT.CENTER);
		tip.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tip.setBounds(10, 281, 236, 23);
		tip.setText("");

		Label label_2 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(0, 248, 480, 2);

		Group group = new Group(composite, SWT.NONE);
		group.setBounds(10, 10, 460, 232);
		CLabel label = new CLabel(group, SWT.NONE);
		label.setBounds(106, 82, 90, 23);
		label.setText("\u603B  \u8F66  \u4F4D  \u6570:");
		totalnumber = new Text(group, SWT.BORDER);
		totalnumber.setBounds(218, 82, 130, 23);
		CLabel label_1 = new CLabel(group, SWT.NONE);
		label_1.setBounds(106, 126, 90, 23);
		label_1.setText("\u5F53\u524D\u5DF2\u505C\u8F66\u6570\uFF1A");
		usednumber = new CLabel(group, SWT.NONE);
		usednumber.setBounds(218, 126, 130, 23);
		usednumber.setText("0");

//		CTabItem tabItem_1 = new CTabItem(tabFolder, SWT.NONE);
//		tabItem_1.setText("New Item");
//		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
//		tabItem_1.setControl(composite_1);

		init();
	}

	/**
	 * 初始化
	 */
	public void init() {
		SystemService ss = new SystemService();
		TSystem system = null;
		try {
			system = ss.getSystemInfo();
		} catch (Exception e) {
		}
		// 如果系统信息还不存在
		if (system == null) {
			system = new TSystem();
			system.setTotalnum(0);
		}
		totalnumber.setText(system.getTotalnum() + "");

		CarEnterService ces = new CarEnterService();
		int count = 0;
		try {
			count = ces.getCarEnterNum();
		} catch (Exception e) {
		}
		usednumber.setText(count + "");
	}

	/**
	 * 设置系统信息
	 * 设置总车位数
	 */
	public boolean updateSysteminfo() {
		if (totalnumber.isDisposed() || shell.isDisposed())
			return false;
		int total = 0;
		try {
			total = Integer.parseInt(totalnumber.getText().trim());
		} catch (Exception e) {
			tip.setText("请输入正确的数据格式!");
			return false;
		}
		SystemService ss = new SystemService();
		TSystem system = new TSystem();
		system.setId(0);
		system.setTotalnum(total);
		system.setOccupiednum(0);
		try {
			ss.updateSystem(system);
		} catch (Exception e) {
			e.printStackTrace();
			tip.setText("系统信息更新失败!");
			return false;
		}
		return true;
	}
}
