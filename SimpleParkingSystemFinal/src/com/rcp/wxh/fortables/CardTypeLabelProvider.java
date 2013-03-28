package com.rcp.wxh.fortables;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.rcp.wxh.pojo.TCardType;

/**
 * 收费方式 内容标签提供其
 * 
 * @author wuxuehong 2011-11-9
 * 
 */
public class CardTypeLabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		TCardType tct = (TCardType) element;
		switch (columnIndex) {
		case 0:
			return tct.getName();
		case 1:
			return tct.getTChargeType().getName();
		case 2: {
			return tct.getRemark();
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
