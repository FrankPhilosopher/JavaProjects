package com.rcp.wxh.composite;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import com.rcp.wxh.composite.CarRecordComposite.CarEnter;
import com.rcp.wxh.dao.impl.TMemberDao;
import com.rcp.wxh.dao.impl.TTempMemberDao;
import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TMember;
import com.rcp.wxh.pojo.TTempMember;
import com.rcp.wxh.resource.IimageKeys;
import com.rcp.wxh.utils.CardComm;

/**
 * �볡������
 * �̳�AbstractCarMonitorCompositeʵ�ֶ�card��Ϣ��չʾ����
 * 
 * @author wuxuehong 2011-11-23
 * 
 */
public class EnterComposite extends AbstractCarMonitorComposite {
	private CardInfoComposite cardInfoCom; // ��Ƭ��Ϣ��� T
	private EnterControlComposite enterControlCom; // �볡����ģ��
	private SystemComposite systemComposite; // ϵͳ�������
	private CarRecordComposite carRecordComposite; // ���������¼��ʾģ��
	private VideoComposite videoComposite; // ��Ƶ��ʾģ��
	private VideoControlComposite videoControlComposite;// ��Ƶ����ģ��

	private static List<CarEnter> carrecords = new ArrayList<CarEnter>();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public EnterComposite(final Composite parent, int style) {
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
		group_1.setText("\u6700\u8FD120\u6761\u8F66\u8F86\u5165\u573A\u8BB0\u5F55");
		group_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		group_1.setLayoutData(new GridData(GridData.FILL_BOTH));

		carRecordComposite = new CarRecordComposite(group_1, SWT.NONE, carrecords);

		Composite composite_1 = new Composite(sashForm, SWT.BORDER);
		composite_1.setLayout(new GridLayout(3, true));

		Group group_2 = new Group(composite_1, SWT.NONE);
		group_2.setText("\u5361\u7247\u4FE1\u606F");
		group_2.setLayout(new GridLayout(2, false));
		group_2.setLayoutData(new GridData(GridData.FILL_BOTH));

		cardInfoCom = new CardInfoComposite(group_2, SWT.NONE);
		GridLayout gridLayout = (GridLayout) cardInfoCom.getLayout();
		gridLayout.marginBottom = 5;
		gridLayout.marginTop = 0;
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.verticalAlignment = SWT.CENTER;
		cardInfoCom.setLayoutData(gd);

		Group group_3 = new Group(composite_1, SWT.NONE);
		group_3.setText("\u5165\u573A\u63A7\u5236");
		group_3.setLayout(new GridLayout(1, true));
		group_3.setLayoutData(new GridData(GridData.FILL_BOTH));

		enterControlCom = new EnterControlComposite(group_3, SWT.NONE);
		enterControlCom.setLayoutData(new GridData(GridData.FILL_BOTH));

		Group group_4 = new Group(composite_1, SWT.NONE);
		group_4.setText("\u7CFB\u7EDF\u4FE1\u606F");
		group_4.setLayout(new FillLayout(SWT.VERTICAL));

		group_4.setLayoutData(new GridData(GridData.FILL_BOTH));

		systemComposite = new SystemComposite(group_4, SWT.BORDER);
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
		if (style == CardComm.EXITANCE) { // �����ǰ��Ƭ��Ϣ�Ƿ��� ���ڼ������
			return;
		}
		String capInfo = "";
		boolean t = false;
		// ����ɹ��볡
		if (isOk && card != null) {// isOK˵��card��Ϊnull
			CarRecordComposite.CarEnter ce = carRecordComposite.new CarEnter();// CarRecordComposite.CarEnter
			Iterator<TMember> it = card.getTMembers().iterator();
			TMember member = null;
			if (it.hasNext()) {
				member = it.next();// ��Ա��ֻ��һ��ʹ����
			}
			ce.cardid = card.getCardid();
			ce.cartype = card.getTCardType().getName();
			ce.carnum = member == null ? "δ֪" : member.getCarnumber();// ������ǻ�Ա�������ƺ��벻�ù�
			ce.date = new Date();
			
			//������ʾ������Ϣ����
			cardInfoCom.setCardInfo(card);// ��ʾ��Ƭ��Ϣ
			carRecordComposite.addCarRecord(ce);// ��ʾ���������¼
			systemComposite.updateParkNum();// ������ʾʣ�೵λ��Ŀ
			
			// �����ͼ
			if (videoComposite != null) {// ������Ƶ���� TODO��yinger Ӧ�ö���true
				// �õ�ͼƬ
				String fileName = "images/" + card.getCardid() + ".jpg";// ʹ�ÿ���id��ΪͼƬ������
				t = videoComposite.captureImage(fileName);// �����ͼ�������ļ�
				if (t) {// ����ɹ���ͼ��ͼƬ���浽���ݿ���
					// ���ȵõ��������ͣ����ھ���ͼƬ��ŵı�
					if (card.getCardtype().equals(TCard.LONG_CARD)) {// ��Ա��
						try {
							TMemberDao memberDao = new TMemberDao();
							memberDao.updateCarPic(member, fileName);
						} catch (Exception e) {
							showMessage(e.getMessage());
						}
					} else {// ��ʱ��
						try {
							TTempMemberDao tempMemberDao = new TTempMemberDao();
							TTempMember tempMember = new TTempMember();
							tempMember.setTCard(card);
							tempMemberDao.updateCarPic(tempMember, fileName);
						} catch (Exception e) {
							showMessage(e.getMessage());
						}
					}
				}
			}
		}
		if (t) {
			capInfo = "���ճɹ�!";
		} else {
			capInfo = "����ʧ��!";
		}
		enterControlCom.setStatus(info + "---" + capInfo);// �볡״̬��Ϣ
	}

	// ״̬�� ��ʾ��Ϣ
	private void showMessage(final String msg) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				ApplicationActionBarAdvisor.getInstance().getStatusLineManager()
						.setMessage(Activator.getImageDescriptor(IimageKeys.LOCK_POP_MANAGE_TRAY).createImage(), msg);
			}
		});
	}
}
