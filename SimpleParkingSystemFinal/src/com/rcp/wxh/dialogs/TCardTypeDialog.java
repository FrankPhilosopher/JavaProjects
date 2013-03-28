package com.rcp.wxh.dialogs;

import java.util.ArrayList;
import java.util.List;

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
import com.rcp.wxh.pojo.TCardType;
import com.rcp.wxh.pojo.TChargeType;
import com.rcp.wxh.service.impl.CardTypeService;
import com.rcp.wxh.service.impl.ChargeTypeService;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * 新增卡片类型对话框
 * 
 * @author wuxuehong 2011-11-16
 * 
 */
public class TCardTypeDialog extends Dialog {

	protected Object result = null; // 默认值 如果操作成功返回1
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Combo combo;
	private List<Object> chargeTypes;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public TCardTypeDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		loadChargeTypes(); // 加载收费方式
	}

	/**
	 * 加载收费方式
	 */
	public void loadChargeTypes() {
		ChargeTypeService cts = new ChargeTypeService();
		try {
			chargeTypes = cts.getAllChargeTypes();
		} catch (Exception e) {
			MessageDialogUtil.showErrorMessage(shell, "获取收费方式失败!");
			chargeTypes = new ArrayList<Object>();
		}
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
		shell.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/CARDTYPE_MANAGE.gif"));

		shell.setSize(409, 282);
		shell.setLocation(getParent().getLocation().x + 300, getParent().getLocation().y + 200);
		shell.setText("\u5361\u7247\u7C7B\u578B");
		shell.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);

		Button button = new Button(shell, SWT.NONE);
		button.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/confirm1.ico"));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = text.getText();
				if ("".equals(name)) {
					MessageDialogUtil.showWarningMessage(shell, "卡片类型名称不可以为空!");
					return;
				}
				TCardType tct = new TCardType();
				tct.setName(name); // 卡片类型名称
				int index = combo.getSelectionIndex();
				TChargeType chargeType = (TChargeType) combo.getData(index + "");
				if (chargeType == null) {
					MessageDialogUtil.showWarningMessage(shell, "请选择收费方式!");
					return;
				}
				tct.setTChargeType(chargeType); // 设置收费方式
				tct.setRemark(text_1.getText());// 备注
				CardTypeService cts = new CardTypeService();
				try {
					cts.addCardType(tct);
				} catch (Exception e1) {
					MessageDialogUtil.showErrorMessage(shell, "卡片类型新增失败!");
					return;
				}
				MessageDialogUtil.showInfoMessage(shell, "卡片类型新增成功");
				result = tct;// 新增成功
				shell.dispose();
			}
		});
		button.setBounds(204, 219, 72, 22);
		button.setText("\u786E\u5B9A");

		Button button_1 = new Button(shell, SWT.NONE);
		button_1.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/cancel.ico"));
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		button_1.setBounds(309, 219, 72, 22);
		button_1.setText("\u53D6\u6D88");

		Group group = new Group(shell, SWT.NONE);
		group.setText("\u586B\u5199\u5361\u7247\u7C7B\u578B\u4FE1\u606F");
		group.setBounds(10, 10, 383, 190);

		Label label = new Label(group, SWT.CENTER);
		label.setLocation(26, 33);
		label.setSize(89, 19);
		label.setAlignment(SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label.setText("\u7C7B\u578B\u540D\u79F0\uFF1A");

		text = new Text(group, SWT.BORDER);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text.setLocation(120, 30);
		text.setSize(159, 18);

		Label label_1 = new Label(group, SWT.NONE);
		label_1.setLocation(25, 67);
		label_1.setSize(89, 19);
		label_1.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label_1.setAlignment(SWT.CENTER);
		label_1.setText("\u6536\u8D39\u65B9\u5F0F\uFF1A");

		combo = new Combo(group, SWT.READ_ONLY);
		combo.setLocation(120, 66);
		combo.setSize(159, 20);
		for (int i = 0; i < chargeTypes.size(); i++) {
			TChargeType tct = (TChargeType) chargeTypes.get(i);
			combo.add(tct.getName(), i);
			combo.setData(i + "", tct); // 设置主键关联 键值对应关系
			combo.setText(tct.getName());
		}

		Label label_2 = new Label(group, SWT.NONE);
		label_2.setLocation(25, 99);
		label_2.setSize(89, 19);
		label_2.setText("\u5907    \u6CE8\uFF1A");
		label_2.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label_2.setAlignment(SWT.CENTER);

		text_1 = new Text(group, SWT.BORDER | SWT.WRAP);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_1.setLocation(120, 102);
		text_1.setSize(160, 78);

	}
}
