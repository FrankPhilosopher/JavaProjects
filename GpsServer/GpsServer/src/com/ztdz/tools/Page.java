package com.ztdz.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ��ҳ��
 * 
 * @author Zahir
 * 
 */
@SuppressWarnings("serial")
public class Page implements Serializable {
	/**
	 * Ĭ�ϵ�ҳ����ʾ��¼����
	 */
	public static int DEFAULT_PAGE_SIZE = 10;

	/**
	 * ÿҳ�ļ�¼��
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * ��ǰҳ��һ��������List�е�λ��,Ĭ�ϴ�0��ʼ
	 */
	private int start;

	/**
	 * ���ݿ��м�¼��������
	 */
	private int totalSize;

	/**
	 * ���췽����ֻ�����ҳ.
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE);
	}

	/**
	 * Ĭ�Ϲ��췽��.
	 * 
	 * @param start
	 *            ��ҳ���������ݿ��е���ʼλ��
	 * @param totalSize
	 *            ���ݿ����ܼ�¼����
	 * @param pageSize
	 *            ��ҳ����
	 * @param rowList
	 *            ��ҳ����������
	 */
	public Page(int start, int totalSize, int pageSize) {
		this.start = start;
		this.totalSize = totalSize;
		this.pageSize = pageSize;
	}

	/**
	 * ȡ�ܼ�¼��.
	 */
	public int getTotalSize() {
		return this.totalSize;
	}

	/**
	 * ȡ��ҳ��
	 */
	public int getTotalPageCount() {
		if (totalSize % pageSize == 0)
			return (int) (totalSize / pageSize);
		else
			return (int) (totalSize / pageSize + 1);
	}

	/**
	 * ȡÿҳ��������.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * ȡ��ҳ��ǰҳ��,ҳ���1��ʼ.
	 */
	public long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * ��ҳ�Ƿ�����һҳ.
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * ��ҳ�Ƿ�����һҳ.
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * ��ȡ��һҳ��һ�����������ݼ���λ��
	 * 
	 * @param pageNo
	 *            ��1��ʼ��ҳ��
	 * @param pageSize
	 *            ÿҳ��¼����
	 * @return ��ҳ��һ������
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