package com.rcp.wxh.dialogs;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.rcp.wbw.skin.LookAndFeel;
import com.rcp.wxh.composite.CarEnterComposite;
import com.rcp.wxh.service.impl.CarEnterService;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;

/**
 * 卡片缴费对话框
 * 
 * @author wuxuehong 2011-11-14
 * 
 */
public class CarEnterDialog extends Dialog {

	private Shell shell;
	private Object result;
	private CarEnterComposite carEnterComposite;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public CarEnterDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		init();
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
		shell = new Shell(getParent(), SWT.DIALOG_TRIM);
		shell.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/01.ico"));
		shell.setSize(605, 469);
		shell.setText("\u5F53\u524D\u573A\u5185\u505C\u8F66\u4FE1\u606F");
		shell.setLocation(getParent().getLocation().x + 300, getParent().getLocation().y + 150);
		shell.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		carEnterComposite = new CarEnterComposite(shell, SWT.NONE);
	}

	/**
	 * 初始化并且获取停车车辆信息
	 */
	public void init() {
		CarEnterService ces = new CarEnterService();
		try {
			List<Object> carenters = ces.getCarEnters();
			carEnterComposite.setData(carenters);
		} catch (Exception e) {
//			e.printStackTrace();
			MessageDialogUtil.showWarningMessage(shell, "获取停车场内当前停车信息失败!");
			return;
		}
	}
}
