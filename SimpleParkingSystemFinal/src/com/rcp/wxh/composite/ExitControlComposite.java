package com.rcp.wxh.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import parkingsystem.Activator;
import parkingsystem.ApplicationActionBarAdvisor;

import com.rcp.wxh.communicate.CommunicateException;
import com.rcp.wxh.pojo.TCarEnter;
import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TChargeType;
import com.rcp.wxh.resource.IimageKeys;
import com.rcp.wxh.service.impl.CarEnterService;
import com.rcp.wxh.service.impl.CarRecordService;
import com.rcp.wxh.service.impl.CardService;
import com.rcp.wxh.utils.CardComm;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;

/**
 * 车辆出场控制面板
 * 
 * @author wuxuehong 2011-12-11
 * 
 */
public class ExitControlComposite extends Composite {

	private ExpenseComposite expenseCom;
	private TCarRecord carRecord;

	/**
	 * 设置当前控制面板操作的对象
	 * 
	 * @param carRecord
	 */
	public void setCurInfor(TCarRecord carRecord) {
		this.carRecord = carRecord;
	}

	public ExitControlComposite(final Composite parent, int style, ExpenseComposite expenseComposite) {
		super(parent, style);
		this.expenseCom = expenseComposite;
		setLayout(new GridLayout(1, true));

		Composite composite = new Composite(this, SWT.NONE);
		RowLayout rl = new RowLayout(SWT.HORIZONTAL);
		rl.marginTop = 5;
		rl.justify = true;
		composite.setLayout(rl);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Button button = new Button(composite, SWT.NONE);
		button.setLayoutData(new RowData(-1, 70));
		button.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/up.gif"));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 出口开闸
				if (carRecord != null) { // 停车放行前： (1)删除入场记录 (2)存储停车记录　(3)扣除停车费用
					float factExp = 0;
					try {
						factExp = Float.parseFloat(expenseCom.getFactExpense());
					} catch (Exception e2) {
						MessageDialogUtil.showWarningMessage(ExitControlComposite.this.getShell(), "请输入正确的实际金额!");
						return;
					}
					TCarEnter carEnter = new TCarEnter();
					carEnter.setCardid(carRecord.getTCard().getCardid());
					delCarEnter(carEnter); // 删除车辆入场信息
					carRecord.setFactexpense(factExp);
					addCarRecord(carRecord); // 新增车辆出入记录
					TCard card = carRecord.getTCard();
					if (card.getCardtype().intValue() == TCard.LONG_CARD) {
						int chargeType = card.getTCardType().getTChargeType().getType().intValue(); // 收费类型
						if (chargeType == TChargeType.HOUR || chargeType == TChargeType.MINUTE) { // 如果是按时/分收费
							card.setCardprice(card.getCardprice() - factExp); // 扣除费用
							updateCard(card);
						}
						if (chargeType == TChargeType.TIME) {// 如实是按照次数来收费
							card.setCardtimes(card.getCardtimes() - 1); // 扣除一次停车次数
							updateCard(card);
						}
					}
					carRecord = null; // 注意 清零！！！
				}
				boolean a = false;
				try {
					a = CardComm.stickUp(CardComm.EXITANCE);
				} catch (CommunicateException e1) {
					MessageDialogUtil.showWarningMessage(parent.getShell(), e1.getMessage());
					showMessage(e1.getMessage());
					return;
				}
				System.out.println("出口开闸:" + a);
			}
		});
		button.setText("\u4EBA\u5DE5\u62AC\u95F8");

		Button button_1 = new Button(composite, SWT.NONE);
		button_1.setLayoutData(new RowData(-1, 70));
		button_1.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/down.gif"));
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 出口关闸
				boolean a = false;
				try {
					a = CardComm.stickDown(CardComm.EXITANCE);
				} catch (CommunicateException e1) {
					MessageDialogUtil.showWarningMessage(parent.getShell(), e1.getMessage());
					showMessage(e1.getMessage());
					return;
				}
				System.out.println("出口关闸:" + a);
			}
		});
		button_1.setText("\u4EBA\u5DE5\u843D\u95F8");

		Button button_2 = new Button(composite, SWT.NONE);
		button_2.setLayoutData(new RowData(-1, 70));
		button_2.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/pause.gif"));
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 出口停闸
				boolean a = false;
				try {
					a = CardComm.stickStop(CardComm.EXITANCE);
				} catch (CommunicateException e1) {
					MessageDialogUtil.showWarningMessage(parent.getShell(), e1.getMessage());
					showMessage(e1.getMessage());
					return;
				}
				System.out.println("出口停闸:" + a);
			}
		});
		button_2.setText("\u6682    \u505C");
	}

	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	// 状态栏 提示信息
	private void showMessage(final String msg) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				ApplicationActionBarAdvisor.getInstance().getStatusLineManager()
						.setMessage(Activator.getImageDescriptor(IimageKeys.LOCK_POP_MANAGE_TRAY).createImage(), msg);
			}
		});
	}

	/**
	 * 新增车辆出入场记录
	 * 
	 * @param carRecord
	 */
	public void addCarRecord(TCarRecord carRecord) {
		CarRecordService crs = new CarRecordService();
		try {
			crs.addCarRecord(carRecord);
		} catch (Exception e) {
			showMessage("新增车辆出入记录异常!");
		}
	}

	/**
	 * 删除车辆入场记录，根据的是卡的id
	 * 
	 * @param carEnter
	 */
	private void delCarEnter(TCarEnter carEnter) {
		CarEnterService ces = new CarEnterService();
		// TCarEnter tce = null;//yinger 米用
		try {
			ces.delCarEnter(carEnter);// 添加是在 CommunicateJob 中增加的！
		} catch (Exception e) {
			showMessage("车辆入场信息删除异常!");
		}
	}

	/**
	 * 更新卡片信息，这里更新的是卡的余额
	 * 
	 * @param card
	 */
	private void updateCard(TCard card) {
		CardService cs = new CardService();
		try {
			cs.updateCard(card);
		} catch (Exception e) {
			showMessage("更新卡片信息异常!");
		}
	}
}
