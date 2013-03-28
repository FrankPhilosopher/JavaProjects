package cn.edu.csu.dbhospital.composite;

import java.util.ArrayList;
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

/**
 * ��ҳ����ģ��
 */
public class PageComposite extends Composite {

	private TableViewer tableView; // ������
	private List<Object> objs; // ��ʾ�б����
	private int sizePage = 20; // ÿҳ��ʾ����Ŀ
	private int curPage = 0; // ��ǰ��ʾҳ��
	private int totalPage = 0; // �ܹ�ҳ��
	private int totalRecords = 0; // �ܼ�¼��

	private Button btnFirst, btnPrev, btnNext, btnLast;
	private Label lbl;

	/**
	 * �������� ����ÿҳ��ʾ�Ĵ�С
	 */
	public void setData(List<Object> objs, int sizePage) {
		this.objs = objs;
		totalRecords = objs.size();
		if (totalRecords == 0)
			return;// ���û�м�¼�򷵻�
		totalPage = totalRecords % sizePage == 0 ? totalRecords / sizePage : totalRecords / sizePage + 1;
		curPage = 1;
		this.sizePage = sizePage;
		setButtonStatus(); // ��ʼ����ť״̬
	}

	/**
	 * ��ʼ�� �������Ϣ���
	 */
	public void init() {
		if (objs == null)
			return;
		objs.clear();
		curPage = 0;
		totalRecords = objs.size();// 0
		totalPage = 0;
		tableView.setInput(null);
		setButtonStatus();
	}

	/**
	 * ��ɾ��������б�
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
	 * ���������������б�
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
	 * ���ð�ť״̬ �Լ� ˢ��Ҫ��ʾ�ļ�¼��Ϣ
	 */
	private void setButtonStatus() {
		// ��ȡ��ҳ���list����
		if (curPage != 0) {
			List<Object> list = getPageContent(objs, curPage, sizePage);
			tableView.setInput(list);
			tableView.refresh();
		}
		// ��ҳ
		if (curPage > 1)
			btnFirst.setEnabled(true);
		else
			btnFirst.setEnabled(false);
		// ��һҳ
		if (curPage > 1)
			btnPrev.setEnabled(true);
		else
			btnPrev.setEnabled(false);
		// ��һҳ
		if (curPage < totalPage)
			btnNext.setEnabled(true);
		else
			btnNext.setEnabled(false);
		// βҳ
		if (curPage != totalPage)
			btnLast.setEnabled(true);
		else
			btnLast.setEnabled(false);

		lbl.setText("\u603B\u8BB0\u5F55-" + totalRecords + "-\uFF0C\u5171-" + totalPage
				+ "-\u9875\uFF0C\u5F53\u524D\u7B2C-" + curPage + "-\u9875");
		this.layout();
	}

	/**
	 * ����һ����ҳ���
	 */
	public PageComposite(Composite parent, int style, TableViewer tableView) {
		super(parent, style);
		this.tableView = tableView;
		GridLayout grid = new GridLayout();
		grid.horizontalSpacing = 0;
		grid.marginHeight = 0;
		setLayout(grid);

		Composite pager = new Composite(this, SWT.NONE);
		GridLayout gl_pager = new GridLayout(5, false);
		gl_pager.verticalSpacing = 0;
		gl_pager.marginHeight = 0;
		gl_pager.horizontalSpacing = 0;
		gl_pager.marginHeight = 0;
		gl_pager.marginBottom = 0;
		gl_pager.marginTop = 0;
		pager.setLayout(gl_pager);
		GridData gd_pager = new GridData(GridData.FILL_BOTH);
		gd_pager.grabExcessVerticalSpace = false;
		gd_pager.heightHint = 26;
		gd_pager.horizontalAlignment = SWT.RIGHT;
		pager.setLayoutData(gd_pager);

		lbl = new Label(pager, SWT.CENTER);
		lbl.setText("\u603B\u8BB0\u5F55-" + totalRecords + "-\uFF0C\u5171-" + totalPage
				+ "-\u9875\uFF0C\u5F53\u524D\u7B2C-" + curPage + "-\u9875");

		btnFirst = new Button(pager, SWT.CENTER);
		btnFirst.addSelectionListener(new SelectionAdapter() { // ��ҳ��Ӧ
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (curPage == 1)
					return;
				curPage = 1;
				setButtonStatus();
			}
		});
		btnFirst.setEnabled(false);
		btnFirst.setText("\u9996\u9875");

		btnPrev = new Button(pager, SWT.CENTER);
		btnPrev.addSelectionListener(new SelectionAdapter() { // ��һҳ��Ӧ
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (curPage - 1 < 1)
					return;
				curPage--;
				setButtonStatus();
			}
		});
		btnPrev.setEnabled(false);
		btnPrev.setText("\u4E0A\u4E00\u9875");

		btnNext = new Button(pager, SWT.CENTER);
		btnNext.addSelectionListener(new SelectionAdapter() { // ��һҳ��Ӧ
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (curPage + 1 > totalPage)
					return;
				curPage++;
				setButtonStatus();
			}
		});
		btnNext.setEnabled(false);
		btnNext.setText("\u4E0B\u4E00\u9875");

		btnLast = new Button(pager, SWT.CENTER);
		btnLast.addSelectionListener(new SelectionAdapter() { // βҳ��Ӧ
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (curPage == totalPage)
					return;
				curPage = totalPage;
				setButtonStatus();
			}
		});
		btnLast.setEnabled(false);
		btnLast.setText("\u5C3E\u9875");
	}

	/**
	 * �õ���ҳ��ʾ������
	 */
	public List<Object> getPageContent(List<Object> list, int curPage, int pageSize) {
		int startIndex = pageSize * (curPage - 1);
		List<Object> rlist = new ArrayList<Object>();
		for (int i = startIndex; i < startIndex + pageSize && i < list.size(); i++) {
			rlist.add(list.get(i));
		}
		return rlist;
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

}
