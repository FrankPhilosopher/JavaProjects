package com.rcp.wxh.composite;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

import com.rcp.wxh.composite.CarRecordComposite.CarEnter;
import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TMember;
import com.rcp.wxh.utils.CardComm;

/**
 * 车辆出入场监控面板
 * 
 * @author wuxuehong 2011-11-23
 * 
 */
public class MultiComposite extends AbstractCarMonitorComposite {

	private Table table;
	private CardInfoComposite cardEnterCom; // 入场卡片信息
	private CardInfoComposite cardExitCom; // 出场卡片信息
	private EnterControlComposite enterControlCom; // 入场控制板块
	private ExitControlComposite exitControlCom; // 出场控制板块
	private ExpenseComposite expenseCom; // 费用信息板块
	private SystemComposite systeCom; // 系统信息展示板块
	private CarRecordComposite enterCarRecordComposite;
	private CarRecordComposite exitCarRecordComposite;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public MultiComposite(final Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(this, SWT.NONE);
		sashForm.setOrientation(SWT.VERTICAL);

		Composite composite = new Composite(sashForm, SWT.BORDER);
		composite.setLayout(new GridLayout(3, true));

		final Group group = new Group(composite, SWT.NONE);
		group.setText("\u6700\u8FD1\u5165\u573A\u768420\u8F86\u8F66\u8F86");
		group.setLayout(new FillLayout(SWT.HORIZONTAL));
		group.setLayoutData(new GridData(GridData.FILL_BOTH));

		enterCarRecordComposite = new CarRecordComposite(group, SWT.NONE, new ArrayList<CarEnter>());

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new GridLayout(1, true));
		composite_2.setLayoutData(new GridData(GridData.FILL_BOTH));

		Group group_2 = new Group(composite_2, SWT.NONE);
		group_2.setText("\u5361\u7247\u4FE1\u606F");
		GridData gd_group_2 = new GridData(GridData.FILL_BOTH);
		gd_group_2.verticalAlignment = SWT.CENTER;
		group_2.setLayoutData(gd_group_2);
		group_2.setLayout(new GridLayout(1, true));

		cardEnterCom = new CardInfoComposite(group_2, SWT.NONE);
		cardEnterCom.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite composite_3 = new Composite(composite, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.VERTICAL));
		composite_3.setLayoutData(new GridData(GridData.FILL_BOTH));

		Group group_4 = new Group(composite_3, SWT.NONE);
		group_4.setText("\u7CFB\u7EDF\u4FE1\u606F");
		group_4.setLayout(new FillLayout(SWT.HORIZONTAL));
		systeCom = new SystemComposite(group_4, SWT.NONE);

		Group group_3 = new Group(composite_3, SWT.NONE);
		group_3.setText("\u5165\u573A\u63A7\u5236");
		group_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		enterControlCom = new EnterControlComposite(group_3, SWT.NONE);

		Composite composite_1 = new Composite(sashForm, SWT.BORDER);
		composite_1.setLayout(new GridLayout(3, true));

		final Group group_5 = new Group(composite_1, SWT.NONE);
		group_5.setText("\u6700\u8FD1\u51FA\u573A\u768420\u8F86\u8F66\u8F86");
		group_5.setLayout(new FillLayout(SWT.HORIZONTAL));
		group_5.setLayoutData(new GridData(GridData.FILL_BOTH));

		exitCarRecordComposite = new CarRecordComposite(group_5, SWT.NONE, new ArrayList<CarEnter>());

		Composite composite_4 = new Composite(composite_1, SWT.NONE);
		composite_4.setLayout(new GridLayout(1, true));
		composite_4.setLayoutData(new GridData(GridData.FILL_BOTH));

		Group group_7 = new Group(composite_4, SWT.NONE);
		group_7.setText("\u5361\u7247\u4FE1\u606F");
		group_7.setLayout(new GridLayout(1, true));
		GridData gd_group_7 = new GridData(GridData.FILL_BOTH);
		gd_group_7.verticalAlignment = SWT.CENTER;
		group_7.setLayoutData(gd_group_7);
		cardExitCom = new CardInfoComposite(group_7, SWT.NONE);
		cardExitCom.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite composite_5 = new Composite(composite_1, SWT.NONE);
		composite_5.setLayout(new GridLayout(1, true));
		composite_5.setLayoutData(new GridData(GridData.FILL_BOTH));

		Group group_8 = new Group(composite_5, SWT.NONE);
		group_8.setText("\u8D39\u7528\u4FE1\u606F");
		group_8.setLayout(new GridLayout(1, true));
		group_8.setLayoutData(new GridData(GridData.FILL_BOTH));
		expenseCom = new ExpenseComposite(group_8, SWT.NONE);
		// gde.verticalAlignment = SWT.CENTER;
		expenseCom.setLayoutData(new GridData(GridData.FILL_BOTH));

		Group group_9 = new Group(composite_5, SWT.NONE);
		group_9.setText("\u51FA\u573A\u63A7\u5236");
		group_9.setLayout(new FillLayout(SWT.HORIZONTAL));
		group_9.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		exitControlCom = new ExitControlComposite(group_9, SWT.NONE, expenseCom);
		sashForm.setWeights(new int[] { 1, 1 });

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * @param card
	 *            卡片对象
	 * @param info反馈信息
	 * @param isOK验证结果
	 *            true/false
	 * @param style出入口类型
	 *            1/2
	 * @param carRecord
	 *            车辆停车记录
	 */
	public void dealWithUI(TCard card, String info, boolean isOk, byte style, TCarRecord carRecord) {
		switch (style) {
		case CardComm.ENTERANCE: // 入口信息 TODO:yinger 复合面板没有对图片进行处理！
			if (card != null){
				cardEnterCom.setCardInfo(card);// 显示卡片信息
			}
			enterControlCom.setStatus(info);//显示入场信息
			if (isOk && card != null) {
				CarRecordComposite.CarEnter ce = enterCarRecordComposite.new CarEnter();// CarRecordComposite.CarEnter
				Iterator<TMember> it = card.getTMembers().iterator();
				TMember member = null;
				if (it.hasNext()) {
					member = it.next();// 会员卡只有一个使用者
				}
				ce.cardid = card.getCardid();
				ce.cartype = card.getTCardType().getName();
				ce.carnum = member == null ? "未知" : member.getCarnumber();// 如果不是会员卡，车牌号码不用管
				ce.date = new Date();
				enterCarRecordComposite.addCarRecord(ce);// 显示车辆入场记录
			}
			break;
		case CardComm.EXITANCE: // 出口信息
			if (card != null)
				cardExitCom.setCardInfo(card);// 显示卡片信息
			if (carRecord != null) {
				expenseCom.setExpense(carRecord);
				CarEnter carenter = exitCarRecordComposite.new CarEnter();
				carenter.cardid = carRecord.getTCard().getCardid();
				carenter.cartype = carRecord.getTCard().getTCardType().getName();
				carenter.carnum = carRecord.getCarnumber();
				carenter.date = carRecord.getEntertime();
				exitCarRecordComposite.addCarRecord(carenter);// 车辆出厂记录
				// 设置出口控制面板操作对象
				exitControlCom.setCurInfor(carRecord);
			}
			break;
		}
		systeCom.updateParkNum();//更新剩余停车位数
	}
}
