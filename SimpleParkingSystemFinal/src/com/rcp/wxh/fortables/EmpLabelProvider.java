package com.rcp.wxh.fortables;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.rcp.wxh.pojo.TEmp;

/**
 * 员工 内容标签提供其
 * 
 * @author wuxuehong 2011-11-9
 * 
 */
public class EmpLabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		TEmp emp = (TEmp) element;
		String[] pri = { "管理员", "操作员" };
		switch (columnIndex) {
		case 0: // 员工ID
			return emp.getOperatorid();
		case 1: // 员工姓名
			return emp.getOperatorname();
		case 2: // 操作权限
			return pri[emp.getPriority()];
		case 3: // 联系方式
			return emp.getPhonenumber();
		case 4: // 证件号码
			return emp.getIdentification();
		case 5: // 住址
			return emp.getAddress();
		case 6: // 备注信息
			return emp.getRemark();
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
