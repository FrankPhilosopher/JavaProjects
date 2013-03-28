package com.rcp.wxh.composite;

import java.sql.Blob;
import java.util.Timer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.rcp.wxh.dialogs.ImageDialog;
import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TMember;
import com.rcp.wxh.pojo.TTempMember;
import com.rcp.wxh.service.impl.MemberService;
import com.rcp.wxh.service.impl.TempMemberService;
import com.swtdesigner.SWTResourceManager;

/**
 * 车辆出场费用信息展示面板
 * 
 * @author wuxuehong 2011-12-11
 * 
 */
public class ExpenseComposite extends Composite {

	private CLabel label_10;
	private Text text, text_1, text_2;
	private Timer timer = new Timer();
	private Button button;

	private TCard card;// 卡

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ExpenseComposite(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(2, false);
		// gridLayout.marginRight = 15;
		// gridLayout.marginLeft = 15;
		// gridLayout.marginBottom = 15;
		// gridLayout.marginTop = 15;
		this.setLayout(gridLayout);

		CLabel label_9 = new CLabel(this, SWT.NONE);
		GridData gd_label_9 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_label_9.heightHint = 22;
		label_9.setLayoutData(gd_label_9);
		label_9.setText("\u505C\u8F66\u65F6\u95F4\uFF1A");

		label_10 = new CLabel(this, SWT.BORDER);
		GridData gd_label_10 = new GridData(GridData.FILL_HORIZONTAL);
		gd_label_10.heightHint = 22;
		label_10.setLayoutData(gd_label_10);

		// CLabel label_8 = new CLabel(this, SWT.NONE);
		// GridData gd_label_8 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		// gd_label_8.heightHint = 22;
		// label_8.setLayoutData(gd_label_8);
		// label_8.setText("\u8F66\u724C\u53F7\u7801\uFF1A");
		//
		// text = new Text(this, SWT.BORDER);
		// text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		// text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		// text.setTextLimit(25);

		CLabel label_11 = new CLabel(this, SWT.NONE);
		GridData gd_label_11 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_label_11.heightHint = 22;
		label_11.setLayoutData(gd_label_11);
		label_11.setText("\u5E94\u6536\u8D39\u7528(\u5143)\uFF1A");

		text_1 = new Text(this, SWT.BORDER);
		text_1.setText("0");
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text_1.widthHint = 260;
		text_1.setLayoutData(gd_text_1);
		text_1.setTextLimit(8);

		CLabel label_12 = new CLabel(this, SWT.NONE);
		GridData gd_label_12 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_label_12.heightHint = 22;
		label_12.setLayoutData(gd_label_12);
		label_12.setText("\u5B9E\u6536\u8D39\u7528(\u5143)\uFF1A");

		text_2 = new Text(this, SWT.BORDER);
		text_2.setText("0");
		text_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text_2.setTextLimit(8);

		// 查看入场图片
//		button = new Button(this, SWT.NONE);
//		button.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if (card == null) {
//					return;
//				}
//				try {
//					Blob blob = null;
//					
//					//首先要根据card的类型决定从哪个表中得到入场图片
//					if (card.getCardtype().intValue() == TCard.LONG_CARD) {
//						MemberService memberService = new MemberService();
//						TMember member = memberService.getMemberByCard(card);
//						blob = member.getCarpicture();
//					} else {
//						TempMemberService tempMemberService = new TempMemberService();
//						TTempMember tempMember = tempMemberService.getMemberByCard(card);
//						blob = tempMember.getCarpicture();
//					}
//					if (blob == null) {
//						return;
//					}
//					// blob->image 显示出来
//					Image image = new Image(Display.getDefault(), blob.getBinaryStream());
//					ImageDialog dialog = new ImageDialog(Display.getDefault().getActiveShell());
//					dialog.setImage(image);
//					dialog.open();
//					
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//		});
//		button.setText("\u67E5\u770B\u5165\u573A\u56FE\u7247");
		new Label(this, SWT.NONE);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * 显示费用信息,这个方法在包含了出场面板的组件中调用
	 */
	public void setExpense(TCarRecord carRecord) {
//		if (carRecord == null)
//			throw new NullPointerException("费用信息为空!"); //这里不用判断，因为前面判断了才进入这个方法
		// 首先设置card
		this.card = carRecord.getTCard();
		int time = carRecord.getStoptime();
		label_10.setText(time / 60 > 0 ? time / 60 + "小时" + time % 60 + "分钟" : time % 60 + "分钟");
		text_1.setText(carRecord.getDueexpense() + ""); // 应收费用
		text_2.setText(carRecord.getFactexpense() + ""); // 是收费用
		// timer.schedule(new TimerTask() {
		// public void run() {
		// Display.getDefault().asyncExec(new Runnable() {
		// @Override
		// public void run() {
		// label_10.setText("");// TODO：yinger
		// }
		// });
		// }
		// }, 3000);// Schedules the specified task for execution after the specified delay.
	}

	/**
	 * 获取实际收费用信息
	 */
	public String getFactExpense() {
		return text_2.getText().trim();
	}

}
