package com.rcp.wxh.composite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

import parkingsystem.Activator;
import parkingsystem.ApplicationActionBarAdvisor;

import com.lti.civil.swt.CaptureControl;
import com.rcp.wxh.composite.CarRecordComposite.CarEnter;
import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.resource.IimageKeys;
import com.rcp.wxh.utils.CardComm;

/**
 * ��������������
 * 
 * @author wuxuehong 2011-11-23
 * 
 */
public class ExitComposite extends AbstractCarMonitorComposite {
	private CardInfoComposite cardInfoCom; // ��Ƭ��Ϣ��ʾ���
	private ExpenseComposite expenseCom; // ������Ϣ��ʾ���
	private ExitControlComposite exitControlCom; // �������ư��
	private CarRecordComposite carRecordComposite; // ������¼��ʾģ��
	private VideoComposite videoComposite; // ��Ƶ��ʾģ��
	private VideoControlComposite videoControlComposite; // ��Ƶ����ģ��
	private static List<CarEnter> carRecords = new ArrayList<CarEnter>();
	private CaptureControl exitVideo;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ExitComposite(final Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(this, SWT.NONE);
		sashForm.setOrientation(SWT.VERTICAL);

		Composite composite = new Composite(sashForm, SWT.BORDER);
		composite.setLayout(new GridLayout(2, true));

		Group group = new Group(composite, SWT.NONE);
		group.setText("\u89C6\u9891\u76D1\u63A7");
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(GridData.FILL_BOTH));

		videoComposite = new VideoComposite(group, SWT.NONE);
		videoComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		videoControlComposite = new VideoControlComposite(group, SWT.NONE, videoComposite);
		videoControlComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Group group_1 = new Group(composite, SWT.NONE);
		group_1.setText("\u6700\u8FD120\u6761\u8F66\u8F86\u51FA\u573A\u8BB0\u5F55");
		group_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		group_1.setLayoutData(new GridData(GridData.FILL_BOTH));

		carRecordComposite = new CarRecordComposite(group_1, SWT.NONE, carRecords);

		Composite composite_1 = new Composite(sashForm, SWT.BORDER);
		composite_1.setLayout(new GridLayout(3, true));

		Group group_2 = new Group(composite_1, SWT.NONE);
		group_2.setText("\u5361\u7247\u4FE1\u606F");
		group_2.setLayout(new GridLayout(2, false));
		group_2.setLayoutData(new GridData(GridData.FILL_BOTH));

		cardInfoCom = new CardInfoComposite(group_2, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.verticalAlignment = SWT.CENTER;
		cardInfoCom.setLayoutData(gd);

		Group group_3 = new Group(composite_1, SWT.NONE);
		group_3.setText("\u8D39\u7528\u4FE1\u606F");
		group_3.setLayout(new GridLayout(2, false));
		group_3.setLayoutData(new GridData(GridData.FILL_BOTH));

		expenseCom = new ExpenseComposite(group_3, SWT.NONE);
		GridLayout gridLayout = (GridLayout) expenseCom.getLayout();
		gridLayout.marginTop = 5;
		GridData gd2 = new GridData(GridData.FILL_BOTH);
		gd2.verticalAlignment = SWT.CENTER;
		expenseCom.setLayoutData(gd2);

		Group group_4 = new Group(composite_1, SWT.NONE);
		group_4.setText("\u51FA\u573A\u63A7\u5236");
		group_4.setLayout(new GridLayout(1, true));
		group_4.setLayoutData(new GridData(GridData.FILL_BOTH));

		exitControlCom = new ExitControlComposite(group_4, SWT.NONE, expenseCom);
		GridData gd3 = new GridData(GridData.FILL_BOTH);
		gd3.verticalAlignment = SWT.CENTER;
		exitControlCom.setLayoutData(gd3);

		sashForm.setWeights(new int[] { 427, 163 });

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * @param card
	 *            ��Ƭ����
	 * @param info������Ϣ
	 * @param isOK��֤���
	 *            true/false
	 * @param style���������
	 *            1/2
	 * @param carRecord
	 *            ����ͣ����¼
	 */
	public void dealWithUI(TCard card, String info, boolean isOk, byte style, TCarRecord carRecord) {
		if (style == CardComm.ENTERANCE) { // ����ÿ�����Ϣ�Ƿ��� �볡��������
			return;
		}
		if (card != null){
			cardInfoCom.setCardInfo(card);
		}
		if (carRecord != null) {
			expenseCom.setExpense(carRecord);//��һ��
			
			CarEnter carenter = carRecordComposite.new CarEnter();
			carenter.cardid = carRecord.getTCard().getCardid();
			carenter.cartype = carRecord.getTCard().getTCardType().getName();
			carenter.carnum = carRecord.getCarnumber();
			carenter.date = carRecord.getEntertime();
			// ����������¼
			carRecordComposite.addCarRecord(carenter);
			// ���ó��ڿ�������������
			exitControlCom.setCurInfor(carRecord);
		}
	}
	
//	// ״̬�� ��ʾ��Ϣ
//	private void showMessage(final String msg) {
//		Display.getDefault().asyncExec(new Runnable() {
//			public void run() {
//				ApplicationActionBarAdvisor.getInstance().getStatusLineManager()
//						.setMessage(Activator.getImageDescriptor(IimageKeys.LOCK_POP_MANAGE_TRAY).createImage(), msg);
//			}
//		});
//	}
}
