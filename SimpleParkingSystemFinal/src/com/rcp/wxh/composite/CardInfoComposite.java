package com.rcp.wxh.composite;

import java.util.Iterator;
import java.util.Timer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TMember;

/**
 * 卡片信息展示模块
 * 
 * @author wuxuehong 2011-12-11
 * 
 */
public class CardInfoComposite extends Composite {

	private CLabel label_4, label_5, label_6, label_7;
	private Timer timer = new Timer();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CardInfoComposite(Composite parent, int style) {
		super(parent, style);

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginTop = 5;
		gridLayout.marginRight = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginBottom = 5;
		this.setLayout(gridLayout);
		CLabel label = new CLabel(this, SWT.NONE);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label.heightHint = 22;
		label.setLayoutData(gd_label);
		label.setAlignment(SWT.CENTER);
		label.setText("\u5361    \u53F7\uFF1A");

		label_4 = new CLabel(this, SWT.BORDER);
		GridData gd_label_4 = new GridData(GridData.FILL_HORIZONTAL);
		gd_label_4.heightHint = 22;
		label_4.setLayoutData(gd_label_4);

		CLabel label_1 = new CLabel(this, SWT.NONE);
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_1.heightHint = 22;
		label_1.setLayoutData(gd_label_1);
		label_1.setAlignment(SWT.CENTER);
		label_1.setText("\u5361 \u7C7B \u578B\uFF1A");

		label_5 = new CLabel(this, SWT.BORDER);
		GridData gd_label_5 = new GridData(GridData.FILL_HORIZONTAL);
		gd_label_5.heightHint = 22;
		label_5.setLayoutData(gd_label_5);

		CLabel label_2 = new CLabel(this, SWT.NONE);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_2.heightHint = 22;
		label_2.setLayoutData(gd_label_2);
		label_2.setAlignment(SWT.CENTER);
		label_2.setText("\u8F66\u724C\u53F7\u7801\uFF1A");

		label_6 = new CLabel(this, SWT.BORDER);
		GridData gd_label_6 = new GridData(GridData.FILL_HORIZONTAL);
		gd_label_6.heightHint = 22;
		label_6.setLayoutData(gd_label_6);

		CLabel label_3 = new CLabel(this, SWT.NONE);
		GridData gd_label_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_3.heightHint = 22;
		label_3.setLayoutData(gd_label_3);
		label_3.setAlignment(SWT.CENTER);
		label_3.setText("\u6301 \u5361 \u4EBA\uFF1A");

		label_7 = new CLabel(this, SWT.BORDER);
		GridData gd_label_7 = new GridData(GridData.FILL_HORIZONTAL);
		gd_label_7.heightHint = 22;
		label_7.setLayoutData(gd_label_7);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * 显示卡片信息
	 * 
	 * @param card
	 */
	public void setCardInfo(TCard card) throws NullPointerException {
		if (card == null){
			throw new NullPointerException("卡片信息为空!");
		}
		label_4.setText("");
		label_5.setText("");
		label_6.setText("");
		label_7.setText("");
		String id = "", type = "", carnumber = "";
		id = card.getCardid(); // 卡号
		type = card.getTCardType().getName(); // 卡类型
		label_4.setText(id); // 卡号
		label_5.setText(type); // 卡片类型那个
		Iterator<TMember> it = card.getTMembers().iterator();
		TMember member = null;
		if (it.hasNext())
			member = it.next();
		if (member != null) {
			carnumber = member.getCarnumber();
			label_6.setText(carnumber);// 车牌号码
			label_7.setText(member.getName()); // 持卡人
		}
		// 定时器 三秒后将卡片信息清空!!!
//		timer.schedule(new TimerTask() {
//			public void run() {
//				Display.getDefault().asyncExec(new Runnable() {
//					public void run() {
//						label_4.setText("");
//						label_5.setText("");
//						label_6.setText("");
//						label_7.setText("");
//					}
//				});
//			}
//		}, 3000);
	}

}
