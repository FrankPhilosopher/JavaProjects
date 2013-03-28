package com.rcp.wxh.fortables;

import java.util.Iterator;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.rcp.wxh.pojo.TExpenseRecord;
import com.rcp.wxh.pojo.TMember;

/**
 * 费用信息记录 内容标签提供其
 * 
 * @author wuxuehong 2011-11-9
 * 
 */
public class ExpenseLabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		TExpenseRecord expense = (TExpenseRecord) element;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Iterator<TMember> it = expense.getTCard().getTMembers().iterator();
		TMember member = null;
		if (it.hasNext()) {
			member = it.next();
		}
		switch (columnIndex) {
		case 0: // 操作人
			return expense.getTEmp().getOperatorname();
		case 1: // 卡型
			return expense.getTCard().getTCardType().getName();
		case 2: // 车主姓名
			if (member != null)
				return member.getName();
			else
				return "";
		case 3: // 车牌号码
			if (member != null)
				return member.getCarnumber();
			else
				return "";
		case 4: // 应付费用
			return expense.getDueexpense() + "";
		case 5: // 实付费用
			return expense.getFactexpense() + "";
		case 6: // 缴费日期
			return sdf.format(expense.getExpensedate());
		case 7: // 备注信息
			return expense.getRemark();
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
