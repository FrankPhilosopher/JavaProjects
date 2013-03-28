package com.rcp.wxh.dialogs;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.rcp.wbw.skin.LookAndFeel;
import com.rcp.wxh.actions.ValidateStatusAction;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TChargeType;
import com.rcp.wxh.pojo.TExpenseRecord;
import com.rcp.wxh.pojo.TMember;
import com.rcp.wxh.service.impl.CardService;
import com.rcp.wxh.service.impl.ExpenseRecordService;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * 卡片缴费对话框
 * 
 * @author wuxuehong 2011-11-14
 * 
 */
public class PayDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;

	private TCard card; // 卡片信息
	private TMember member; // 会员信息
	private Text text_8;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public PayDialog(Shell parent, int style, TCard card) {
		super(parent, style);
		this.card = card;
		Iterator<TMember> it = card.getTMembers().iterator();
		if (it.hasNext())
			member = it.next();
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
		shell.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/EXPENSE_MANAGE.png"));
		shell.setSize(433, 444);
		shell.setText("\u7F34\u8D39");
		shell.setLocation(getParent().getLocation().x + 300, getParent().getLocation().y + 150);
		shell.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		Group group = new Group(shell, SWT.NONE);
		group.setText("\u586B\u5199\u7F34\u8D39\u4FE1\u606F");
		group.setBounds(10, 10, 407, 364);

		Label label = new Label(group, SWT.HORIZONTAL | SWT.CENTER);
		label.setBounds(34, 30, 92, 18);
		label.setText("\u5361    \u53F7:");

		text = new Text(group, SWT.BORDER);
		text.setEnabled(false);
		text.setEditable(false);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text.setBounds(143, 30, 161, 18);
		if (card != null)
			text.setText(card.getCardid()); // 卡号

		Label label_1 = new Label(group, SWT.HORIZONTAL | SWT.CENTER);
		label_1.setBounds(34, 57, 92, 18);
		label_1.setText("\u8F66\u724C\u53F7\u7801:");

		text_1 = new Text(group, SWT.BORDER);
		text_1.setEditable(false);
		text_1.setEnabled(false);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_1.setBounds(143, 57, 161, 18);
		if (member != null)
			text_1.setText(member.getCarnumber()); // 车牌号码

		Label label_2 = new Label(group, SWT.HORIZONTAL | SWT.CENTER);
		label_2.setBounds(34, 84, 92, 18);
		label_2.setText("\u8F66\u4E3B\u59D3\u540D:");

		text_2 = new Text(group, SWT.BORDER);
		text_2.setEnabled(false);
		text_2.setEditable(false);
		text_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_2.setBounds(143, 84, 161, 18); // 车主姓名
		if (member != null)
			text_2.setText(member.getName()); // 车主姓名

		Label label_3 = new Label(group, SWT.HORIZONTAL | SWT.CENTER);
		label_3.setBounds(34, 111, 92, 18);
		label_3.setText("\u5361    \u578B:");

		text_3 = new Text(group, SWT.BORDER);
		text_3.setEnabled(false);
		text_3.setEditable(false);
		text_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_3.setBounds(143, 111, 161, 18);
		if (card != null)
			text_3.setText(card.getTCardType().getName() + "--" + card.getTCardType().getTChargeType().getName()); // 卡型

		Label label_4 = new Label(group, SWT.HORIZONTAL | SWT.CENTER);
		label_4.setBounds(34, 138, 92, 18);
		label_4.setText("\u4F59    \u989D:"); // 余额

		text_4 = new Text(group, SWT.BORDER);
		text_4.setEnabled(false);
		text_4.setEditable(false);
		text_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_4.setBounds(143, 138, 161, 18);

		Label label_5 = new Label(group, SWT.HORIZONTAL | SWT.CENTER);
		label_5.setBounds(34, 165, 92, 18);
		label_5.setText("\u5E94\u7F34\u8D39\u91D1\u989D:");

		Label label_6 = new Label(group, SWT.HORIZONTAL | SWT.CENTER);
		label_6.setBounds(34, 192, 92, 18);
		label_6.setText("\u5B9E\u7F34\u8D39\u91D1\u989D:");

		text_5 = new Text(group, SWT.BORDER);
		text_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_5.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				text_6.setText(text_5.getText());
			}
		});
		text_5.setBounds(143, 165, 161, 18);

		text_6 = new Text(group, SWT.BORDER);
		text_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_6.setBounds(143, 192, 161, 18);

		Label label_7 = new Label(group, SWT.NONE);
		label_7.setBounds(310, 165, 54, 18);
		label_7.setText("\u5143");

		Label label_8 = new Label(group, SWT.NONE);
		label_8.setBounds(310, 192, 54, 18);
		label_8.setText("\u5143");

		// 停车次数
		Label label_9 = new Label(group, SWT.HORIZONTAL | SWT.CENTER);
		label_9.setBounds(34, 219, 92, 18);
		label_9.setText("\u505C\u8F66\u6B21\u6570:");

		text_7 = new Text(group, SWT.BORDER);
		text_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_7.setBounds(143, 219, 161, 18);

		// 服务起止日期
		Label label_10 = new Label(group, SWT.HORIZONTAL | SWT.CENTER);
		label_10.setBounds(34, 246, 92, 18);
		label_10.setText("\u670D\u52A1\u8D77\u59CB\u65E5\u671F:");

		final DateTime dateTime = new DateTime(group, SWT.BORDER);
		dateTime.setBounds(143, 243, 91, 21);

		final DateTime dateTime_1 = new DateTime(group, SWT.BORDER);
		dateTime_1.setBounds(253, 243, 91, 21);

		Label label_11 = new Label(group, SWT.NONE);
		label_11.setFont(SWTResourceManager.getFont("宋体", 14, SWT.NORMAL));
		label_11.setBounds(240, 243, 17, 21);
		label_11.setText("-");

		Label label_12 = new Label(group, SWT.HORIZONTAL | SWT.CENTER);
		label_12.setBounds(34, 273, 92, 18);
		label_12.setText("\u5907    \u6CE8:");

		text_8 = new Text(group, SWT.BORDER | SWT.MULTI);
		text_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_8.setBounds(143, 273, 161, 81);
		text_8.setTextLimit(150);

		Button button = new Button(shell, SWT.NONE);
		button.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/money.ico"));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 缴费啦。。。。。。
				String due = text_5.getText();// 应缴费用
				String fact = text_6.getText(); // 实际缴费
				if ("".equals(due) || "".equals(fact)) {
					MessageDialogUtil.showWarningMessage(shell, "请输入费用信息!");
					return;
				}
				float duee = 0, facte = 0;
				try {
					duee = Float.parseFloat(due);
					facte = Float.parseFloat(fact);
				} catch (Exception e1) {
					MessageDialogUtil.showWarningMessage(shell, "请输入正确的费用信息!");
					return;
				}
				// 根据不同的收费方式更新card实例
				int type = card.getTCardType().getTChargeType().getType().intValue(); // 获取收费方式
				switch (type) {
				case TChargeType.MINUTE:
				case TChargeType.HOUR:
					card.setCardprice(card.getCardprice() + facte);
					break;
				case TChargeType.TIME:
					int time = 0;
					try {
						time = Integer.parseInt(text_7.getText().trim());
					} catch (Exception e2) {
						MessageDialogUtil.showWarningMessage(shell, "请输入正确的停车次数!");
						return;
					}
					card.setCardtimes(card.getCardtimes() + time);
					break;
				case TChargeType.PERIOD: // 时间段
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, dateTime.getYear());
					cal.set(Calendar.MONTH, dateTime.getMonth());
					cal.set(Calendar.DATE, dateTime.getDay());
					cal.set(Calendar.HOUR, -12);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					Date startTime = cal.getTime();
					Calendar cal2 = Calendar.getInstance();
					cal2.set(Calendar.YEAR, dateTime_1.getYear());
					cal2.set(Calendar.MONTH, dateTime_1.getMonth());
					cal2.set(Calendar.DATE, dateTime_1.getDay());
					cal2.set(Calendar.HOUR, -12);
					cal2.set(Calendar.MINUTE, 0);
					cal2.set(Calendar.SECOND, 0);
					Date endTime = cal2.getTime();
					card.setCardstarttime(startTime);
					card.setCardendtime(endTime);
					break;
				}
				CardService cs = new CardService();
				try {
					cs.updateCard(card);
				} catch (Exception e1) {
					MessageDialogUtil.showWarningMessage(shell, "缴费失败!");
					return;
				}
				// 创建费用记录实例
				TExpenseRecord expenseRecord = new TExpenseRecord();
				expenseRecord.setTEmp(ValidateStatusAction.user); // 设置操作人
				expenseRecord.setTCard(card); // 设置卡片信息
				expenseRecord.setCarnumber(member.getCarnumber()); // 设置车牌号码
				expenseRecord.setDueexpense(duee); // 应付金额
				expenseRecord.setFactexpense(facte); // 实付金额
				expenseRecord.setRemark(text_8.getText());// 备注信息
				expenseRecord.setExpensedate(new Date());// 日期
				// TODO：yinger
//				Notifier.getInstance().fireExpense(expenseRecord); //触发缴费事件
				// 这里实际上可以直接取到service进行操作！
				ExpenseRecordService ers = new ExpenseRecordService();
				try {
					ers.addExpenseRecord(expenseRecord);
				} catch (Exception ex) {
//					ex.printStackTrace();
					MessageDialogUtil.showInfoMessage(shell, "缴费失败！");
				}
				MessageDialogUtil.showInfoMessage(shell, "缴费成功！");
				shell.dispose();
			}
		});
		button.setBounds(243, 380, 72, 22);
		button.setText("\u7F34  \u8D39");

		Button button_1 = new Button(shell, SWT.NONE);
		button_1.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/cancel.ico"));
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 取消 关闭对话框
				shell.dispose();
			}
		});
		button_1.setBounds(332, 380, 72, 22);
		button_1.setText("\u53D6  \u6D88");

		if (card != null && member != null) {
			int type = card.getTCardType().getTChargeType().getType().intValue(); // 获取收费方式
			switch (type) {
			case TChargeType.MINUTE: // 按分钟收费
			case TChargeType.HOUR: { // 按小时收费
				label_4.setText("余    额");
				text_4.setText(card.getCardprice() + "元");

				text_7.setEnabled(false);
				dateTime.setEnabled(false);
				dateTime_1.setEnabled(false);
				break;
			}
			case TChargeType.TIME: {
				label_4.setText("剩余次数");
				text_4.setText(card.getCardtimes() + "次");

				text_7.setEnabled(true);
				dateTime.setEnabled(false);
				dateTime_1.setEnabled(false);
				break;
			}
			case TChargeType.PERIOD: {
				label_4.setText("服务截止日期");
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
//				System.out.println(sdf.format(card.getCardendtime()));
				text_4.setText(sdf.format(card.getCardendtime()));

				text_7.setEnabled(false);
				dateTime.setEnabled(true);
				dateTime_1.setEnabled(true);
				break;
			}
			case TChargeType.FREE: {
				button.setEnabled(false);// 免费用户无需缴费
			}
			}
		}
	}
}
