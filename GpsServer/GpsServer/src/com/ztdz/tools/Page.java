package com.ztdz.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 * 
 * @author Zahir
 * 
 */
@SuppressWarnings("serial")
public class Page implements Serializable {
	/**
	 * 默认的页面显示记录条数
	 */
	public static int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 每页的记录数
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 当前页第一条数据在List中的位置,默认从0开始
	 */
	private int start;

	/**
	 * 数据库中记录的总条数
	 */
	private int totalSize;

	/**
	 * 构造方法，只构造空页.
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 默认构造方法.
	 * 
	 * @param start
	 *            本页数据在数据库中的起始位置
	 * @param totalSize
	 *            数据库中总记录条数
	 * @param pageSize
	 *            本页容量
	 * @param rowList
	 *            本页包含的数据
	 */
	public Page(int start, int totalSize, int pageSize) {
		this.start = start;
		this.totalSize = totalSize;
		this.pageSize = pageSize;
	}

	/**
	 * 取总记录数.
	 */
	public int getTotalSize() {
		return this.totalSize;
	}

	/**
	 * 取总页数
	 */
	public int getTotalPageCount() {
		if (totalSize % pageSize == 0)
			return (int) (totalSize / pageSize);
		else
			return (int) (totalSize / pageSize + 1);
	}

	/**
	 * 取每页数据容量.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 取该页当前页码,页码从1开始.
	 */
	public long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置
	 * 
	 * @param pageNo
	 *            从1开始的页号
	 * @param pageSize
	 *            每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	public static int getDEFAULT_PAGE_SIZE() {
		return DEFAULT_PAGE_SIZE;
	}

	public static void setDEFAULT_PAGE_SIZE(int dEFAULT_PAGE_SIZE) {
		DEFAULT_PAGE_SIZE = dEFAULT_PAGE_SIZE;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
}