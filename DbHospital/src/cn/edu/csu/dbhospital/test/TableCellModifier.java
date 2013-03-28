package cn.edu.csu.dbhospital.test;

import org.eclipse.jface.viewers.ICellModifier;

/**
 * @description: 编辑单元格内容
 * @author Administrator
 * @version 1.0, 2010-2-19
 * @Copyright 2010-2020
 */
public class TableCellModifier implements ICellModifier {

	public boolean canModify(Object element, String property) {
		// TODO Auto-generated method stub
		if (property.equalsIgnoreCase("COLOR")) {
			return false;
		}
		return true;
	}

	public Object getValue(Object element, String property) {
		// TODO Auto-generated method stub
		System.out.println(element);
		System.out.println(property);
		// cn.edu.csu.dbhospital.test.PersonEO@1676908
		// SEX
		if (property.equalsIgnoreCase("COLOR")) {
			return "COLOR";
		}
		return "...";
	}

	public void modify(Object element, String property, Object value) {
		// TODO Auto-generated method stub

	}

}