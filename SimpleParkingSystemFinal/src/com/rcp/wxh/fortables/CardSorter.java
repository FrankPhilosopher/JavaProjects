package com.rcp.wxh.fortables;

import java.util.Date;
import java.util.Iterator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TMember;

public class CardSorter extends ViewerSorter {

	private int column;

	public void doSort(int column) {
		this.column = column;
	}

	public int compare(Viewer viewer, Object e1, Object e2) {
		TCard n1 = (TCard) e1;
		TCard n2 = (TCard) e2;
		TMember m1 = null;
		TMember m2 = null;
		Iterator<TMember> it = n1.getTMembers().iterator();
		if (it.hasNext())
			m1 = n1.getTMembers().iterator().next();
		it = n2.getTMembers().iterator();
		if (it.hasNext())
			m2 = n2.getTMembers().iterator().next();
		switch (column) {
		case 1: { // 卡号
			String str1 = n1.getCardid();
			String str2 = n2.getCardid();
			return str1.compareTo(str2);
		}
		case -1: {
			String str1 = n1.getCardid();
			String str2 = n2.getCardid();
			return str2.compareTo(str1);
		}
		case 2: { // 卡片类型
			String str1 = n1.getTCardType().getName();
			String str2 = n2.getTCardType().getName();
			return str1.compareTo(str2);
		}
		case -2: {
			String str1 = n1.getTCardType().getName();
			String str2 = n2.getTCardType().getName();
			return str2.compareTo(str1);
		}
		case 3: { // 车牌号
			String str1, str2;
			if (m1 == null)
				str1 = "";
			else
				str1 = m1.getCarnumber();
			if (m2 == null)
				str2 = "";
			else
				str2 = m2.getCarnumber();
			return str1.compareTo(str2);
		}
		case -3: {
			String str1, str2;
			if (m1 == null)
				str1 = "";
			else
				str1 = m1.getCarnumber();
			if (m2 == null)
				str2 = "";
			else
				str2 = m2.getCarnumber();
			return str2.compareTo(str1);
		}
		case 4: { // 车主姓名
			String str1, str2;
			if (m1 == null)
				str1 = "";
			else
				str1 = m1.getName();
			if (m2 == null)
				str2 = "";
			else
				str2 = m2.getName();
			return str1.compareTo(str2);
		}
		case -4: {
			String str1, str2;
			if (m1 == null)
				str1 = "";
			else
				str1 = m1.getName();
			if (m2 == null)
				str2 = "";
			else
				str2 = m2.getName();
			return str2.compareTo(str1);
		}
		case 5: { // 车型
			String str1, str2;
			if (m1 == null)
				str1 = "";
			else
				str1 = m1.getCartype();
			if (m2 == null)
				str2 = "";
			else
				str2 = m2.getCartype();
			return str1.compareTo(str2);
		}
		case -5: {
			String str1, str2;
			if (m1 == null)
				str1 = "";
			else
				str1 = m1.getCartype();
			if (m2 == null)
				str2 = "";
			else
				str2 = m2.getCartype();
			return str2.compareTo(str1);
		}
		case 6: { // 开卡日期
			System.out.println(6);
			Date str1, str2;
			str1 = n1.getCarddate();
			str2 = n2.getCarddate();
			return str1.compareTo(str2);
		}
		case -6: {
			System.out.println(-6);
			Date str1, str2;
			str1 = n1.getCarddate();
			str2 = n2.getCarddate();
			return str2.compareTo(str1);
		}
		case 7: { // 卡片状态
			Integer str1, str2;
			str1 = n1.getCardstatus();
			str2 = n2.getCardstatus();
			return str1.compareTo(str2);
		}
		case -7: {
			Integer str1, str2;
			str1 = n1.getCardstatus();
			str2 = n2.getCardstatus();
			return str2.compareTo(str1);
		}
		}
		return super.compare(viewer, e1, e2);
	}

}
