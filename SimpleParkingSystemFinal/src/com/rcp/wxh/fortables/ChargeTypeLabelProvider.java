package com.rcp.wxh.fortables;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.rcp.wxh.pojo.TChargeType;

/**
 * 收费方式 内容标签提供其
 * 
 * @author wuxuehong 2011-11-9
 * 
 */
public class ChargeTypeLabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		TChargeType tct = (TChargeType) element;
		String[] str = { "按分钟收费", "按小时收费", "按次数收费", "按时间段收费", "免费" };
		String temp = "";
		switch (columnIndex) {
		case 0:
			return tct.getName();
		case 1:
			return str[tct.getType() - 1];
		case 2: {//详细信息是通过得到的收费方式进行设置的，并不是表中的remark字段
			temp = "超过" + tct.getMinutecount() + "分钟开始计费,";
			switch (tct.getType()) {
			case TChargeType.MINUTE:
				return temp + "每" + tct.getMinute() + "分钟收费" + tct.getFee() + "元,超过" + tct.getOverminute() + "秒为一个计费单位";
			case TChargeType.HOUR:
				return temp + "每" + tct.getHour() + "小时收费" + tct.getFee() + "元,超过" + tct.getOverhour() + "分钟为一个计费单位";
			case TChargeType.TIME:
				return temp + "每次停车扣除卡内停车次数一次";
			case TChargeType.PERIOD:
				return "在有效时间段内停车免费";
			case TChargeType.FREE:
				return "任何时间停车免费";
			default:
				return "未知";
			}
		}
		default:
			return "未知";
		}
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

}
