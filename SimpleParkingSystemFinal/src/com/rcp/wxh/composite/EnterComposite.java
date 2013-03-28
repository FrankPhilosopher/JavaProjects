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
 * 入场监控面板
 * 继承AbstractCarMonitorComposite实现对card信息的展示处理
 * 
 * @author wuxuehong 2011-11-23
 * 
 */
public class EnterComposite extends AbstractCarMonitorComposite {
	private CardInfoComposite cardInfoCom; // 卡片信息面板 T
	private EnterControlComposite enterControlCom; // 入场控制模块
	private SystemComposite systemComposite; // 系统控制面板
	private CarRecordComposite carRecordComposite; // 车辆进入记录显示模块
	private VideoComposite videoComposite; // 视频显示模块
	private VideoControlComposite videoControlComposite;// 视频控制模块

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
		if (style == CardComm.EXITANCE) { // 如果当前卡片信息是发往 出口监控面板的
			return;
		}
		String capInfo = "";
		boolean t = false;
		// 如果成功入场
		if (isOk && card != null) {// isOK说明card不为null
			CarRecordComposite.CarEnter ce = carRecordComposite.new CarEnter();// CarRecordComposite.CarEnter
			Iterator<TMember> it = card.getTMembers().iterator();
			TMember member = null;
			if (it.hasNext()) {
				member = it.next();// 会员卡只有一个使用者
			}
			ce.cardid = card.getCardid();
			ce.cartype = card.getTCardType().getName();
			ce.carnum = member == null ? "未知" : member.getCarnumber();// 如果不是会员卡，车牌号码不用管
			ce.date = new Date();
			
			//控制显示面板的信息更新
			cardInfoCom.setCardInfo(card);// 显示卡片信息
			carRecordComposite.addCarRecord(ce);// 显示车辆出入记录
			systemComposite.updateParkNum();// 更新显示剩余车位数目
			
			// 保存截图
			if (videoComposite != null) {// 打开了视频窗口 TODO：yinger 应该都是true
				// 得到图片
				String fileName = "images/" + card.getCardid() + ".jpg";// 使用卡的id作为图片的名称
				t = videoComposite.captureImage(fileName);// 保存截图到本地文件
				if (t) {// 如果成功截图，图片保存到数据库中
					// 首先得到卡的类型，用于决定图片存放的表
					if (card.getCardtype().equals(TCard.LONG_CARD)) {// 会员卡
						try {
							TMemberDao memberDao = new TMemberDao();
							memberDao.updateCarPic(member, fileName);
						} catch (Exception e) {
							showMessage(e.getMessage());
						}
					} else {// 临时卡
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
			capInfo = "拍照成功!";
		} else {
			capInfo = "拍照失败!";
		}
		enterControlCom.setStatus(info + "---" + capInfo);// 入场状态信息
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
}
