package com.rcp.wxh.dialogs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TCardType;
import com.rcp.wxh.pojo.TChargeType;
import com.rcp.wxh.pojo.TMember;
import com.rcp.wxh.service.impl.CardService;
import com.rcp.wxh.service.impl.CardTypeService;
import com.rcp.wxh.service.impl.MemberService;
import com.rcp.wxh.utils.CardUtil;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * 新注册卡片信息对话框
 * 
 * @author wuxuehong 2011-11-16
 * 
 */
public class TCardDialog extends Dialog {

	protected Object result = null;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Label label_2;
	private Button button;
	private Button button_1;
	private Combo combo;
	private Label label_3;
	private Text text_2;
	private Label label_4;
	private Text text_4;
	private Label label_5;
	private Label label_6;
	private Text text_5;
	private Button button_2;
	private Button button_3;
	private List<Object> carTypes;
	private Combo combo_1;

	private boolean isRunning = false;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public TCardDialog(Shell parent, int style) {
		super(parent, style);
		// 初始化时要读取出所有的卡片类型信息
		CardTypeService cts = new CardTypeService();
		try {
			carTypes = cts.getAllCardTypes();
		} catch (Exception e) {
			MessageDialogUtil.showErrorMessage(parent, "获取卡片类型失败!");
			carTypes = new ArrayList<Object>();
		}
	}

	/**
	 * 读取卡片线程
	 * 
	 * @author wuxuehong 2011-11-10
	 * 
	 */
	class ReadCardThread implements Runnable {

		public void run() {
			while (isRunning) {
				final long a = CardUtil.getCardNum();
				System.out.println("read card num:" + a);
				if (a != -1) {
					if (!shell.isDisposed())
						shell.getDisplay().asyncExec(new Runnable() {
							public void run() {
								text.setText(a + "");
							}
						});
					// break; //线程停止
				} else {
					if (!shell.isDisposed())
						shell.getDisplay().asyncExec(new Runnable() {
							public void run() {
								text.setText("请将IC卡放入读卡器感应区!");
							}
						});
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//
			}
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
		
		//启动线程去读取卡信息
		Thread t = new Thread(new ReadCardThread());
		isRunning = true;
		t.start();
		
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		isRunning = false;
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/CARD_MANAGE.png"));
		shell.setSize(450, 450);
		shell.setLocation(getParent().getLocation().x + 300, getParent().getLocation().y + 150);
		shell.setText("\u5361\u7247\u4FE1\u606F\u6CE8\u518C");
		shell.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		Group group = new Group(shell, SWT.NONE);
		group.setText("\u586B\u5199\u5361\u7247\u6CE8\u518C\u4FE1\u606F");
		group.setBounds(10, 10, 424, 359);

		Label label = new Label(group, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label.setBounds(45, 36, 69, 18);
		label.setText("\u5361    \u53F7\uFF1A");

		text = new Text(group, SWT.BORDER);
		text.setEnabled(false);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text.setBounds(120, 36, 199, 18);
		text.setEditable(false);
		text.setTextLimit(25);

		Label label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(45, 119, 69, 18);
		label_1.setText("\u8F66 \u724C \u53F7\uFF1A");
		label_1.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		text_1 = new Text(group, SWT.BORDER);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_1.setEnabled(false);
		text_1.setBounds(120, 119, 199, 18);
		text_1.setTextLimit(25);

		label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(45, 68, 69, 18);
		label_2.setText("\u5361    \u578B\uFF1A");
		label_2.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		button = new Button(group, SWT.RADIO);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 临时卡单选按钮
				combo.removeAll();
				if (button.getSelection()) {
					if (carTypes != null) { // 下拉列表更新
						for (int i = 0; i < carTypes.size(); i++) {
							TCardType tct = (TCardType) carTypes.get(i);
							// 收费方式
							if (tct.getTChargeType().getType() == TChargeType.MINUTE
									|| tct.getTChargeType().getType() == TChargeType.HOUR) {
								combo.add(tct.getName()); // 卡片类型名称
								combo.setData(i + "", tct);
								combo.setText(tct.getName());
							}
						}
					}
				}
				text_1.setEnabled(false);
				text_2.setEnabled(false);
				text_4.setEnabled(false);
				combo_1.setEnabled(false);
			}
		});
		button.setBounds(120, 67, 65, 16);
		button.setText("\u4E34\u65F6\u5361");
		button.setSelection(true);

		button_1 = new Button(group, SWT.RADIO);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 长期卡单选按钮
				combo.removeAll();
				if (button_1.getSelection()) {
					if (carTypes != null) { // 下拉列表更新
						for (int i = 0; i < carTypes.size(); i++) {
							TCardType tct = (TCardType) carTypes.get(i);
							combo.add(tct.getName()); // 卡片类型名称
							combo.setData(i + "", tct);
							combo.setText(tct.getName());
						}
					}
				}
				text_1.setEnabled(true);
				text_2.setEnabled(true);
				text_4.setEnabled(true);
				combo_1.setEnabled(true);
			}
		});
		button_1.setBounds(199, 67, 95, 16);
		button_1.setText("\u957F\u671F\u5361");

		combo = new Combo(group, SWT.READ_ONLY);
		combo.setBounds(120, 89, 199, 20);
		if (carTypes != null) {
			for (int i = 0; i < carTypes.size(); i++) {
				TCardType tct = (TCardType) carTypes.get(i);
				if (tct.getTChargeType().getType() == TChargeType.MINUTE
						|| tct.getTChargeType().getType() == TChargeType.HOUR) {
					combo.add(tct.getName()); // 卡片类型名称
					combo.setData(i + "", tct);
					combo.setText(tct.getName());
				}
			}
		}

		label_3 = new Label(group, SWT.NONE);
		label_3.setBounds(45, 150, 65, 18);
		label_3.setText("\u8F66\u4E3B\u59D3\u540D\uFF1A");
		label_3.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		text_2 = new Text(group, SWT.BORDER);
		text_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_2.setEnabled(false);
		text_2.setBounds(120, 150, 199, 18);
		text_2.setTextLimit(25);

		label_4 = new Label(group, SWT.NONE);
		label_4.setBounds(45, 180, 69, 18);
		label_4.setText("\u8F66    \u578B\uFF1A");
		label_4.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		text_4 = new Text(group, SWT.BORDER);
		text_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_4.setEnabled(false);
		text_4.setBounds(120, 214, 199, 18);
		text_4.setTextLimit(25);

		label_5 = new Label(group, SWT.NONE);
		label_5.setBounds(45, 216, 65, 18);
		label_5.setText("\u8054\u7CFB\u65B9\u5F0F\uFF1A");
		label_5.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		label_6 = new Label(group, SWT.NONE);
		label_6.setBounds(45, 250, 65, 18);
		label_6.setText("\u5907    \u6CE8\uFF1A");
		label_6.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		text_5 = new Text(group, SWT.BORDER | SWT.WRAP);
		text_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_5.setBounds(120, 250, 199, 94);
		text_5.setTextLimit(100);

		combo_1 = new Combo(group, SWT.READ_ONLY);
		combo_1.setEnabled(false);
		combo_1.setItems(new String[] { "\u5C0F\u6C7D\u8F66", "\u6469\u6258\u8F66", "\u5BA2\u8F66", "\u5361\u8F66",
				"\u91CD\u578B\u673A\u68B0" });
		combo_1.setBounds(120, 180, 199, 20);
		combo_1.setText("\u5C0F\u6C7D\u8F66");

		button_2 = new Button(shell, SWT.NONE);
		button_2.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/confirm1.ico"));
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 新增卡片
				String cardID = text.getText(); // 获取卡ID
				if (cardID.equals("") || cardID.startsWith("请将")) {
					MessageDialogUtil.showWarningMessage(shell, "请先读取卡号!");
					return;
				}
				int index = combo.getSelectionIndex();
				TCardType tct = (TCardType) combo.getData(index + "");
				if (tct == null) {
					MessageDialogUtil.showWarningMessage(shell, "请选择卡类型!");
					return;
				}

				CardService cs = new CardService(); // 卡片服务
				MemberService ms = new MemberService(); // 会员服务
				TCard card = null;
				TCard checkedCard = null;

				// //////////////////////////检查卡片是否已经注册///////////////////////////////////
				try {
					checkedCard = cs.getCardById(cardID); // 查询该卡片是否已经注册
				} catch (Exception e1) {
					MessageDialogUtil.showWarningMessage(shell, "卡片信息获取失败!");
					return;
				}
				if (checkedCard != null) { // 如果卡片已经注册
					MessageDialogUtil.showWarningMessage(shell, "该卡已经注册!");
					return;
				}

				// ///////////////////////////临时卡注册///////////////////////////////////////////
				if (button.getSelection()) { // 如果是需要注册临时卡
					card = new TCard();
					card.setCardid(cardID); // 卡片ID
					card.setCarddate(new Date()); // 卡片日期
					card.setTCardType(tct); // 卡片类型
					card.setCardstatus(TCard.AVILIABLE); // 卡片可用
					card.setCardprice(0f); // 卡片余额 0
					card.setCardtimes(0); // 次数0
					card.setCardstarttime(new Date()); // 起始日期
					card.setCardendtime(new Date()); // 结束日期
					card.setCardtype(TCard.TEMP_CARD); // 临时卡
					try {
						cs.addCard(card);
					} catch (Exception e1) {
						e1.printStackTrace();
						MessageDialogUtil.showWarningMessage(shell, "新卡注册失败!");
						return;
					}
				}
				// /////////////////////////////////长期卡注册///////////////////////////////////
				else {
					String carnumber = null;
					carnumber = text_1.getText();
					if ("".equals(carnumber)) {
						MessageDialogUtil.showWarningMessage(shell, "请输入车牌号码!");
						return;
					}
					card = new TCard();
					card.setCardid(cardID); // 卡片ID
					card.setCarddate(new Date()); // 卡片日期
					card.setTCardType(tct); // 卡片类型
					card.setCardstatus(TCard.AVILIABLE); // 卡片可用
					card.setCardprice(0f); // 卡片余额 0
					card.setCardtimes(0); // 次数0
					card.setCardstarttime(new Date()); // 起始日期
					card.setCardendtime(new Date()); // 结束日期
					card.setCardtype(TCard.LONG_CARD); // 长期卡
					try {
						cs.addCard(card);
					} catch (Exception e1) {
						MessageDialogUtil.showWarningMessage(shell, "新卡注册失败!");
						return;
					}
					TMember member = new TMember();
					member.setTCard(card);
					member.setCarnumber(carnumber);// 车牌号码
					member.setName(text_2.getText());// 车主姓名
					member.setCartype(combo_1.getText()); // 车型
					member.setPhonenumber(text_4.getText()); // 联系方式
					member.setRemark(text_5.getText());// 备注信息
					try {
						ms.addMember(member);
					} catch (Exception e1) {
						MessageDialogUtil.showWarningMessage(shell, "会员注册失败!");
						return;
					}
					Set<TMember> memset = new HashSet<TMember>();
					memset.add(member);
					card.setTMembers(memset);
				}
				result = card;
				MessageDialogUtil.showInfoMessage(shell, "新卡注册成功!");
				shell.dispose();
			}
		});
		button_2.setBounds(217, 386, 72, 22);
		button_2.setText("\u786E\u5B9A");

		button_3 = new Button(shell, SWT.NONE);
		button_3.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/cancel.ico"));
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		button_3.setBounds(321, 386, 72, 22);
		button_3.setText("\u53D6\u6D88");

	}

}
