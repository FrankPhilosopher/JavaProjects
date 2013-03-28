package com.rcp.wxh.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具
 * 
 * @author wuxuehong 2011-11-14
 * 
 */
public class PageUtil {

	/**
	 * 根据 页码 每页显示数目 获取当前页对象集合
	 * 
	 * @param list
	 * @param curPage  当前页
	 * @param pageSize   每页的size
	 * @return
	 */
	public static List<Object> getPageContent(List<Object> list, int curPage, int pageSize) {
		int startIndex = pageSize * (curPage - 1);
		List<Object> rlist = new ArrayList<Object>();
		for (int i = startIndex; i < startIndex + pageSize && i < list.size(); i++) {
			rlist.add(list.get(i));
		}
		return rlist;
	}

}
