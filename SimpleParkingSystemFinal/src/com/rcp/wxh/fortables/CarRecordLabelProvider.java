package com.rcp.wxh.fortables;

import java.util.Iterator;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TMember;

/**
 * 车辆出入记录 内容标签提供其
 * 
 * @author wuxuehong 2011-11-9
 * 
 */
public class CarRecordLabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		TCarRecord record = (TCarRecord) element;
		Iterator<TMember> it = record.getTCard().getTMembers().iterator();
		TMember member = null;
		if (it.hasNext()) {
			member = it.next();
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		switch (columnIndex) {
		case 0: // 卡号
			return record.getTCard().getCardid();
		case 1: // 车牌号码
			return record.getCarnumber();
		case 2: // 卡类型
			return record.getTCard().getTCardType().getName();
		case 3: // 入场时间
			return sdf.format(record.getEntertime());
		case 4: // 出场时间
			return sdf.format(record.getExittime());
		case 5: // 停车时间
			int times = record.getStoptime(); // 分钟
			int hour = (int) (times / 60); // 小时数
			int minute = (int) (times % 60); // 分钟数
			return hour > 0 ? hour + "小时" + minute + "分钟" : minute + "分钟";
		case 6: // 应收费用
			return record.getDueexpense() + "";
		case 7: // 实收费用
			return record.getFactexpense() + "";
		case 8: // 备注信息
			return record.getRemark();
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
