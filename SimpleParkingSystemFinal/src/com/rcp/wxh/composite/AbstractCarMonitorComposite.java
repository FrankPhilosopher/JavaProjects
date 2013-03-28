package com.rcp.wxh.composite;

import org.eclipse.swt.widgets.Composite;

import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TCard;

/**
 * 需要处理 卡片信息的 车辆出入场监控面板
 * 这车辆出入场监控面板
 * 
 * @author wuxuehong 2011-11-23
 * 
 */
public abstract class AbstractCarMonitorComposite extends Composite {

	public AbstractCarMonitorComposite(Composite parent, int style) {
		super(parent, style);
	}

	// 处理卡片信息
	/**
	 * @param card
	 *            卡片信息
	 * @param info
	 *            提示信息
	 * @param isOk
	 *            是否能够通行
	 * @param type
	 *            出入口类型
	 * @param carRecord
	 *            车辆停车记录
	 */
	public abstract void dealWithUI(TCard card, String info, boolean isOk, byte type, TCarRecord carRecord);

}
