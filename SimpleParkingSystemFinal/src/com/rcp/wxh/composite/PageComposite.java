package com.rcp.wxh.composite;

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.rcp.wxh.utils.PageUtil;

/**
 * 分页控制模块
 * 
 * @author wuxuehong 2011-11-14
 * 
 */
public class PageComposite extends Composite {

	private TableViewer tableView; // 表格对象
	private List<Object> objs; // 显示列表对象
	private int sizePage = 20; // 每页显示的数目
	private int curPage = 0; // 当前显示页面
	private int totalPage = 0; // 总共页面
	private int totalRecords = 0; // 总记录数

	private Button button, button_1, button_2, button_3;
	private Label label;

	/**
	 * 设置数据 包括每页显示的大小
	 */
	public void setData(List<Object> objs, int sizePage) {
		this.objs = objs;
		totalRecords = objs.size();
		if (totalRecords == 0)
			return;// 如果没有记录则返回
		totalPage = totalRecords % sizePage == 0 ? totalRecords / sizePage : totalRecords / sizePage + 1;
		curPage = 1;
		this.sizePage = sizePage;
		setButtonStatus(); // 初始化按钮状态
	}

	/**
	 * 初始化 将表格信息清空
	 */
	public void init() {
		if (objs == null)
			return;
		objs.clear();
		curPage = 0;
		totalRecords = objs.size();//0
		totalPage = 0;
		tableView.setInput(null);
		setButtonStatus();
	}

	/**
	 * 当删除后更新列表
	 */
	public void updateAfterDeleting(List<Object> obj) {
		if (objs == null)
			return;
		objs.removeAll(obj);
		totalRecords = objs.size();
		totalPage = totalRecords % sizePage == 0 ? totalRecords / sizePage : totalRecords / sizePage + 1;
		if (curPage > totalPage)
			curPage--;
		tableView.setInput(objs);
		tableView.refresh();
		setButtonStatus();
	}

	/**
	 * 当新增对象后更新列表
	 * 
	 * @param obj
	 */
	public void updateAfterAdding(Object obj) {
		if (objs == null)
			return;
		objs.add(0, obj);
		totalRecords = objs.size();
		totalPage = totalRecords % sizePage == 0 ? totalRecords / sizePage : totalRecords / sizePage + 1;
		if (curPage > totalPage)
			curPage--;
		tableView.setInput(objs);
		tableView.refresh();
		setButtonStatus();
	}

	/**
	 * 设置按钮状态 以及 刷新要显示的记录信息
	 */
	private void setButtonStatus() {
		// 获取分页后的list对象
		if (curPage != 0) {
			List<Object> list = PageUtil.getPageContent(objs, curPage, sizePage);
			tableView.setInput(list);
			tableView.refresh();
		}
		// 首页
		if (curPage > 1)
			button.setEnabled(true);
		else
			button.setEnabled(false);
		// 上一页
		if (curPage > 1)
			button_1.setEnabled(true);
		else
			button_1.setEnabled(false);
		// 下一页
		if (curPage < totalPage)
			button_2.setEnabled(true);
		else
			button_2.setEnabled(false);
		// 尾页
		if (curPage != totalPage)
			button_3.setEnabled(true);
		else
			button_3.setEnabled(false);

		label.setText("\u603B\u8BB0\u5F55-" + totalRecords + "-\uFF0C\u5171-" + totalPage
				+ "-\u9875\uFF0C\u5F53\u524D\u7B2C-" + curPage + "-\u9875");
		this.layout();
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PageComposite(Composite parent, int style, TableViewer tableView) {
		super(parent, style);
		this.tableView = tableView;
		GridLayout grid = new GridLayout();
		grid.horizontalSpacing = 0;
		grid.marginHeight = 0;
		setLayout(grid);

		Composite c = new Composite(this, SWT.NONE);
		GridLayout gl_c = new GridLayout(5, false);
		gl_c.verticalSpacing = 0;
		gl_c.marginHeight = 0;
		gl_c.horizontalSpacing = 0;
		gl_c.marginHeight = 0;
		gl_c.marginBottom = 0;
		gl_c.marginTop = 0;
		c.setLayout(gl_c);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = false;
		gd.heightHint = 26;
		gd.horizontalAlignment = SWT.RIGHT;
		c.setLayoutData(gd);

		label = new Label(c, SWT.CENTER);
		label.setText("\u603B\u8BB0\u5F55-" + totalRecords + "-\uFF0C\u5171-" + totalPage
				+ "-\u9875\uFF0C\u5F53\u524D\u7B2C-" + curPage + "-\u9875");

		button = new Button(c, SWT.CENTER);
		button.addSelectionListener(new SelectionAdapter() { // 首页响应
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (curPage == 1)
					return;
				curPage = 1;
				setButtonStatus();
			}
		});
		button.setEnabled(false);
		button.setText("\u9996\u9875");

		button_1 = new Button(c, SWT.CENTER);
		button_1.addSelectionListener(new SelectionAdapter() { // 上一页响应
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (curPage - 1 < 1)
					return;
				curPage--;
				setButtonStatus();
			}
		});
		button_1.setEnabled(false);
		button_1.setText("\u4E0A\u4E00\u9875");

		button_2 = new Button(c, SWT.CENTER);
		button_2.addSelectionListener(new SelectionAdapter() { // 下一页响应
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (curPage + 1 > totalPage)
					return;
				curPage++;
				setButtonStatus();
			}
		});
		button_2.setEnabled(false);
		button_2.setText("\u4E0B\u4E00\u9875");

		button_3 = new Button(c, SWT.CENTER);
		button_3.addSelectionListener(new SelectionAdapter() { // 尾页响应
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (curPage == totalPage)
					return;
				curPage = totalPage;
				setButtonStatus();
			}
		});
		button_3.setEnabled(false);
		button_3.setText("\u5C3E\u9875");
	}

	public TableViewer getTableView() {
		return tableView;
	}

	public void setTableView(TableViewer tableView) {
		this.tableView = tableView;
	}

	public List<Object> getObjs() {
		return objs;
	}

	public void setObjs(List<Object> objs) {
		this.objs = objs;
	}

	protected void checkSubclass() {
	}

}
