package com.rcp.wxh.communicate;

import java.util.Date;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

import parkingsystem.Activator;
import parkingsystem.ApplicationActionBarAdvisor;

import com.rcp.wxh.editors.EnterEditor;
import com.rcp.wxh.editors.EnterEditorInput;
import com.rcp.wxh.pojo.TCarEnter;
import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TMember;
import com.rcp.wxh.resource.IimageKeys;
import com.rcp.wxh.service.impl.CarEnterService;
import com.rcp.wxh.service.impl.CardService;
import com.rcp.wxh.utils.CarMonitorUtil;
import com.rcp.wxh.utils.CardComm;
import com.rcp.wxh.utils.CardUtil;
import com.rcp.wxh.utils.MessageDialogUtil;

/**
 * 与终端通信作业
 * 
 * @author wuxuehong 2011-11-18
 * 
 */
public class CommunicateJob extends Job {

	private static final Log log = LogFactory.getLog(CommunicateJob.class);

	private EnterEditor enterEditor = null;
	private boolean changeEditor = false; // 用于控制切换监控面板 默认为否  //TODO:yinger  这两个属性可以放到方法中
	private boolean validate = false; // 入场/出场验证

	public CommunicateJob(String name) {
		super(name);
	}

	/**
	 * job run
	 * 阻塞着读取终端信息
	 */
	protected IStatus run(IProgressMonitor monitor) {
		log.info("初始化通信串口...");
		boolean init = false;
		try {
			try {
				init = CardComm.init(); // 初始化 CardComm实例 ， 初始化前会加载DLL模块
			} catch (CommunicateException e) {
				showMessage(e.getMessage());// 通信过程中的报错都会显示在状态栏中
			}
		} catch (Error e) { // 捕获DLL加载错误
			showMessage("通信模块链接库加载失败!");
			return Status.OK_STATUS;
		}
		byte[] data = new byte[100];// [1, -54, 125, -4, 32, 0, 0,....]
		// [1, -22, 61, 40, 33, 0,0,...]
		int op = 0;
		if (init) {// 初始化成功
			log.info("串口初始化成功...");
			while (true) {
				op = 0;
				try {
					
					System.out.println("开始阻塞读取信息...");//TODO：yinger
					
					op = CardComm.read(data); // 获取终端信息 在此处线程阻塞，一直等待着刷卡操作
				} catch (MachineException e) {
					showMessage(e.getMessage()); // 在状态栏显示异常信息
				} catch (CommunicateException e) {
					showMessage(e.getMessage());
				} catch (Error e) {
					showMessage("通信模块错误:" + e.getMessage());
					break;
				}
				if (op == CardComm.GETCARDID && data != null) {// op=1
					new Thread(new DealWithCardThread(data)).start();// 开启新的线程进行处理，那么就可以处理多次刷卡操作
				}
			}
		}
		return Status.OK_STATUS;
	}

	/**
	 * 通信消息处理线程
	 * 
	 * @author wuxuehong 2011-11-20
	 * 
	 */
	class DealWithCardThread implements Runnable {
		byte[] data = null; // 终端反馈信息
		StringBuffer sb = new StringBuffer(); // UI提示信息
		TCard card = null; // TCard实例
		String cardid = null; // 卡号
		TCarRecord carRecord = null; // 停车记录

		public DealWithCardThread(byte[] data) {
			this.data = data;
//		System.out.println(Arrays.toString(data));
		}

		public void run() {
			final byte type = data[0]; // 出入口方式 1-入口 2-出口
			changeEditor = false; // 是否要切换监控面板 默认为否

			// ////////////////////////////////////检测是否需要切换监控面板//////////////////////////////////////////
//			switch (type) {
//			case CardComm.ENTERANCE: // 入口控制信息
//				if (EnterEditor.STYLE == EnterEditor.EXIT_ONLY) { // 如果当前不是打开的入口监控面板
//					EnterEditor.STYLE = EnterEditor.ENTER_EXIT; // 规定 只要当前监控面板不与接收信息冲突 统一转向 出入口监控面板
//					changeEditor = true; // 需要切换控制器面板
//				}
//				break;
//			case CardComm.EXITANCE: // 出口控制信息
//				if (EnterEditor.STYLE == EnterEditor.ENTER_ONLY) { // 如果当前打开的入口监控面板
//					EnterEditor.STYLE = EnterEditor.ENTER_EXIT;
//					changeEditor = true; // 需要切换控制器面板
//				}
//				break;
//			default: // 异常包 丢弃！
//				showMessage("异常卡！");
//				return;
//			}
			
			//////////////////////////////////////////将监控面板显示到最前/////////////////////////////////////////
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage workbenchPage = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow()
							.getActivePage();
					IEditorPart editor = workbenchPage.findEditor(EnterEditorInput.eei); // 获取编辑器
					try {
						if (editor != null) { // 如果监控面板已经打开
//							if (changeEditor) { // 需要切换监控面板
//								workbenchPage.closeEditor(editor, true); // 关闭当前监控面板
//								editor = workbenchPage.openEditor(EnterEditorInput.eei, EnterEditor.ID);
//							} else
								workbenchPage.bringToTop(editor); // 将监控面板显示到最前
						} else { // 重新初始化该编辑器
							editor = workbenchPage.openEditor(EnterEditorInput.eei, EnterEditor.ID);
						}
					} catch (PartInitException ei) {
						log.info("监控面板切换异常!");
						showMessage("监控面板切换异常!");
					}
					enterEditor = (EnterEditor) editor;
				}
			});

			// /////////////////////////////////////////////验证卡片有效性///////////////////////////////////////////////
			// 根据card对象 处理卡片信息
			byte[] cardNum = new byte[4];
			System.arraycopy(data, 1, cardNum, 0, 4); // 将data中1-4个字节(即卡号)拷贝到cardNum中
			cardid = CardUtil.bytes2long(data, 1) + ""; // 1-4个字节为卡号
			try {
				card = getCard(cardid);
			} catch (Exception e) {
				sb.append("获取卡片信息失败!请检查与服务器的连接!");
				// 注意，这里提示信息是异步执行的，不能得到e，所以使用类的字符串属性sb
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), sb.toString());
					}
				});
				return;
			}
			TCarEnter carEnter = null;
			if (card != null) {
				carEnter = getCarEnter(card);//这里得到的carEnter可能是null
			}

			// 出入口 验证 出入口验证方式不同
			switch (type) {
			case CardComm.ENTERANCE: // 入口验证
				// 获取卡片信息后 对卡片进行验证 (是否注册，是否激活，余额信息,是否已经入场)
				validate = CarMonitorUtil.isAviliable(card, carEnter, sb);
				try {
					if (validate) { // 如果卡片有效
						CardComm.write(cardNum, CardComm.SUCCESS, type); // 反馈信息给终端 卡号 成功/失败 入口/出口
						addCarEnter(card);// 新增车辆入场信息
					} else {
						CardComm.write(cardNum, CardComm.FAIL, type); // 反馈信息给终端 卡号 成功/失败 入口/出口
					}
				} catch (CommunicateException e) {
//					e.printStackTrace();
					showMessage(e.getMessage());
					log.info(e.getMessage());
				}
				break;
			case CardComm.EXITANCE:// 如果是出场，计算费用！
				if (carEnter == null) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "无法获取该车辆入场信息!");
						}
					});
					return;
				} else { // 终端显示停车情况
//						  delCarEnter(carEnter);
					carRecord = CarMonitorUtil.getCarRecord(carEnter); // 获取车辆停车记录包括费用信息
					if (carRecord != null) {
//							  addCarRecord(carRecord);
						float exp = carRecord.getFactexpense();
						try {
							CardComm.setTimeMoney(carRecord.getStoptime().intValue(), (int) (exp * 10 / 10),
									(int) (exp * 10 % 10));// 元 角
						} catch (CommunicateException e) {
							showMessage(e.getMessage());
							log.info(e.getMessage());
						}
					}
				}
				break;
			}

			// //////////////////////////////////////////////UI展示验证结果信息//////////////////////////////////
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					enterEditor.dealEnterCar(card, sb, validate, type, carRecord); // param:卡片对象 卡片验证信息 验证结果
				}
			});

		}

	}

	/**
	 * 获取卡片信息
	 * 
	 * @param cardID
	 * @return
	 * @throws Exception
	 */
	public TCard getCard(String cardid) throws Exception {
		CardService cs = new CardService();
		TCard card = null;
		try {
			card = cs.getCardById(cardid);
		} catch (Exception e) {
			log.info("卡片信息获取异常!");
			throw e;
		}
		return card;
	}

	/**
	 * 获取车辆入场记录，根据卡的id
	 * 
	 * @return
	 */
	private TCarEnter getCarEnter(TCard card) {
		CarEnterService ces = new CarEnterService();
		TCarEnter tce = null;
		try {
			tce = ces.getCarEnterByCardid(card.getCardid());
		} catch (Exception e) {
			log.info("车辆入场信息获取异常!");
//			throw e;//这里异常不抛出去
		}
		return tce;
	}

	/**
	 * 新增车辆入场记录
	 * 
	 * @param tce
	 */
	private void addCarEnter(TCard card) {
		TCarEnter tce = new TCarEnter();
		tce.setCardid(card.getCardid());
		tce.setTCard(card);
		Iterator<TMember> it = card.getTMembers().iterator();
		TMember member = null;
		if (it.hasNext()) {
			member = it.next();
		}
		tce.setCarnumber(member == null ? "未知" : member.getCarnumber());
		tce.setEntertime(new Date());
		CarEnterService ces = new CarEnterService();
		try {
			ces.addCarEnter(tce);
		} catch (Exception e) {
			log.info("新增车辆入场记录失败!");
		}
	}

	// 状态栏 提示信息，出现了错误就进行警告！
	private void showMessage(final String msg) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				// 信息显示在默认的位置
				ApplicationActionBarAdvisor.getInstance().getStatusLineManager()
						.setMessage(Activator.getImageDescriptor(IimageKeys.STATUS_WARN_TRAY).createImage(), msg);
			}
		});
	}

}
