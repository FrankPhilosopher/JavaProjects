package com.rcp.wxh.dialogs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
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
import com.rcp.wxh.pojo.TMember;
import com.rcp.wxh.service.impl.CardService;
import com.rcp.wxh.service.impl.CardTypeService;
import com.rcp.wxh.service.impl.MemberService;
import com.rcp.wxh.utils.CardUtil;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * 卡片信息编辑对话框
 * 
 * @author wuxuehong 2011-11-16
 * 
 */
public class TCardEditDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Combo combo;
	private Text text_1;
	private Label label_2;
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

	private TCard card;
	private TMember member;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public TCardEditDialog(Shell parent, int style, TCard card) {
		super(parent, style);
		this.card = card;
		member = card.getTMembers().iterator().next();
		CardTypeService cts = new CardTypeService();
		try {
			carTypes = cts.getAllCardTypes();
		} catch (Exception e) {
			MessageDialogUtil.showErrorMessage(parent, "获取卡片类型失败!");
			carTypes = new ArrayList<Object>();
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
		shell.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/CARD_MANAGE.png"));
		shell.setSize(436, 429);
		shell.setLocation(getParent().getLocation().x + 300, getParent().getLocation().y + 150);
		shell.setText("\u7F16\u8F91\u5361\u7247\u4FE1\u606F");

		shell.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);

		Group group = new Group(shell, SWT.NONE);
		group.setText("\u7F16\u8F91\u4F1A\u5458\u4FE1\u606F");
		group.setBounds(10, 10, 410, 342);

		Label label = new Label(group, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		label.setBounds(45, 35, 69, 18);
		label.setText("\u5361    \u53F7\uFF1A");

		text = new Text(group, SWT.BORDER);
		text.setBounds(120, 36, 199, 18);
		text.setEditable(false);
		text.setTextLimit(25);
		text.setText(card.getCardid());// 初始化卡片编号

		Label label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(45, 101, 69, 18);
		label_1.setText("\u8F66 \u724C \u53F7\uFF1A");
		label_1.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		text_1 = new Text(group, SWT.BORDER);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_1.setBounds(120, 101, 199, 18);
		text_1.setTextLimit(25);
		if (member != null)
			text_1.setText(member.getCarnumber()); // 初始化车牌号码

		label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(45, 68, 69, 18);
		label_2.setText("\u5361    \u578B\uFF1A");
		label_2.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		combo = new Combo(group, SWT.READ_ONLY);
		combo.setBounds(120, 68, 199, 18);
		if (carTypes != null) {
			for (int i = 0; i < carTypes.size(); i++) {
				TCardType tct = (TCardType) carTypes.get(i);
				combo.add(tct.getName()); // 卡片类型名称
				combo.setData(i + "", tct);
				if (card.getTCardType().getCardtypeid().intValue() == tct.getCardtypeid().intValue()) {
					combo.setText(tct.getName());
				}
			}
		}

		label_3 = new Label(group, SWT.NONE);
		label_3.setBounds(45, 133, 69, 18);
		label_3.setText("\u8F66\u4E3B\u59D3\u540D\uFF1A");
		label_3.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		text_2 = new Text(group, SWT.BORDER);
		text_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_2.setBounds(120, 133, 199, 18);
		text_2.setTextLimit(25);
		if (member != null)
			text_2.setText(member.getName()); // 初始化车主姓名

		label_4 = new Label(group, SWT.NONE);
		label_4.setBounds(45, 166, 69, 18);
		label_4.setText("\u8F66    \u578B\uFF1A");
		label_4.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		text_4 = new Text(group, SWT.BORDER);
		text_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_4.setBounds(120, 199, 199, 18);
		text_4.setTextLimit(25);
		if (member != null) {
			text_4.setText(member.getPhonenumber()); // 初始化联系方式
		}

		label_5 = new Label(group, SWT.NONE);
		label_5.setBounds(45, 199, 69, 18);
		label_5.setText("\u8054\u7CFB\u65B9\u5F0F\uFF1A");
		label_5.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		label_6 = new Label(group, SWT.NONE);
		label_6.setBounds(45, 232, 69, 18);
		label_6.setText("\u5907    \u6CE8\uFF1A");
		label_6.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));

		text_5 = new Text(group, SWT.BORDER | SWT.WRAP);
		text_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_5.setBounds(120, 232, 199, 94);
		text_5.setTextLimit(100);
		if (member != null) // 初始化备注信息
			text_5.setText(member.getRemark());

		combo_1 = new Combo(group, SWT.READ_ONLY);
		combo_1.setItems(new String[] { "\u5C0F\u6C7D\u8F66", "\u6469\u6258\u8F66", "\u5BA2\u8F66", "\u5361\u8F66",
				"\u91CD\u578B\u673A\u68B0", "\u5176\u4ED6" });
		combo_1.setBounds(120, 166, 199, 18);
		combo_1.setText("\u5C0F\u6C7D\u8F66");
		if (member != null) {
			combo_1.setText(member.getCartype()); // 初始化车型信息
		}

		Button button = new Button(group, SWT.NONE);
		button.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/exchange.ico"));
		button.addSelectionListener(new SelectionAdapter() { // 换卡
			@Override
			public void widgetSelected(SelectionEvent e) {
				final long a = CardUtil.getCardNum();
				if (a == -1) {
					MessageDialogUtil.showWarningMessage(shell, "请将新卡放入感应区!");
					return;
				} else {
					shell.getDisplay().asyncExec(new Runnable() {
						public void run() {
							text.setText(a + "");
						}
					});
				}
			}
		});
		button.setBounds(333, 30, 68, 23);
		button.setText("\u6362\u5361");

		button_2 = new Button(shell, SWT.NONE);
		button_2.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/confirm1.ico"));
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 更新卡片信息
				String cardID = text.getText().trim(); // 获取卡ID
				if ("".equals(cardID)) {
					MessageDialogUtil.showWarningMessage(shell, "请先读取卡号!");
					return;
				}

				int index = combo.getSelectionIndex();
				TCardType tct = (TCardType) combo.getData(index + "");
				if (tct == null) {
					MessageDialogUtil.showWarningMessage(shell, "请选择卡类型!");
					return;
				}

				String carnumber = text_1.getText();
				if ("".equals(carnumber)) {
					MessageDialogUtil.showWarningMessage(shell, "请输入车牌号码!");
					return;
				}

				MemberService ms = new MemberService(); // 会员服务
				TCard checkedCard = null;

				CardService cs = new CardService(); // 卡片服务
				member.setCarnumber(carnumber);// 车牌号码
				member.setName(text_2.getText());// 车主姓名
				member.setCartype(combo_1.getText()); // 车型
				member.setPhonenumber(text_4.getText()); // 联系方式
				member.setRemark(text_5.getText());// 备注信息

				if (!cardID.equals(card.getCardid())) { // 如果更换了卡片
					try {
						checkedCard = cs.getCardById(cardID); // 查询该卡片是否已经注册
					} catch (Exception e1) {
						MessageDialogUtil.showWarningMessage(shell, "新卡信息验证失败!");
						return;
					}
					if (checkedCard != null) { // 如果卡片已经注册
						MessageDialogUtil.showWarningMessage(shell, "该卡已经注册!");
						return;
					}
					TCard newcard = new TCard();
					newcard.setCardid(cardID); // 卡片ID
					newcard.setCarddate(new Date()); // 卡片日期
					newcard.setTCardType(tct); // 卡片类型
					newcard.setCardstatus(TCard.AVILIABLE); // 卡片可用
					newcard.setCardprice(0f); // 卡片余额 0
					newcard.setCardtimes(0); // 次数0
					newcard.setCardstarttime(new Date()); // 起始日期
					newcard.setCardendtime(new Date()); // 结束日期
					newcard.setCardtype(TCard.LONG_CARD); // 长期卡
					try {
						cs.addCard(newcard);
					} catch (Exception e1) {
						e1.printStackTrace();
						MessageDialogUtil.showWarningMessage(shell, "新卡注册失败!");
						return;
					}
					// 对象更新
					card.setCardid(newcard.getCardid());
					member.setTCard(newcard);
				} else {
					if (card.getTCardType().getCardtypeid().intValue() != tct.getCardtypeid().intValue()) { // 如果卡片类型换了
																											// 则需要更新卡片类型
						card.setTCardType(tct);
						try {
							cs.updateCard(card);
						} catch (Exception e1) {
							MessageDialogUtil.showWarningMessage(shell, "卡片信息更新失败!");
							return;
						}
					}
				}

				try {
					ms.updateMember(member);
				} catch (Exception e1) {
					MessageDialog.openWarning(shell, "提示", "会员信息更新失败!");
					return;
				}
				result = card;
				MessageDialogUtil.showInfoMessage(shell, "卡片信息更新成功!");
				shell.dispose();
			}
		});
		button_2.setBounds(217, 358, 72, 22);
		button_2.setText("\u786E\u5B9A");

		button_3 = new Button(shell, SWT.NONE);
		button_3.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/cancel.ico"));
		button_3.addSelectionListener(new SelectionAdapter() { // 取消按钮
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		button_3.setBounds(323, 358, 72, 22);
		button_3.setText("\u53D6\u6D88");

	}
}
