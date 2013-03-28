package com.rcp.wxh.fortables;

import java.util.Iterator;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TMember;

/**
 * 卡片 内容标签提供其
 * 
 * @author wuxuehong 2011-11-9
 * 
 */
public class CardLabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		TCard card = (TCard) element;
		Iterator<TMember> it = card.getTMembers().iterator();
		TMember member = null;
		if (it.hasNext()) {
			member = it.next();
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		switch (columnIndex) {
		case 0: // 卡号
			return card.getCardid();
		case 1: // 卡类型
			return card.getTCardType().getName();
		case 2: // 车牌号
			if (member != null) {
				return member.getCarnumber();
			} else
				return "";
		case 3: // 车主姓名
			if (member != null)
				return member.getName();
			else
				return "";
		case 4: // 车型
			if (member != null) {
				return member.getCartype();
			} else
				return "";
		case 5: // 联系方式
			if (member != null) {
				return member.getPhonenumber();
			} else
				return "";
		case 6: // 开卡日期
			return sdf.format(card.getCarddate());
		case 7: // 卡片状态
		{
			switch (card.getCardstatus()) {
			case TCard.AVILIABLE: {
				return "已激活";
			}
			case TCard.LOCKED: {
				return "已锁";
			}
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
