package com.rcp.wxh.composite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import parkingsystem.Activator;

import com.rcp.wxh.fortables.TableListener;
import com.rcp.wxh.pojo.TCarEnter;
import com.rcp.wxh.pojo.TEmp;
import com.rcp.wxh.resource.IimageKeys;
import com.rcp.wxh.service.impl.CarEnterService;
import com.rcp.wxh.service.impl.EmpService;
import com.rcp.wxh.utils.MessageDialogUtil;

/**
 * 当前停车记录面板
 * 
 * @author wuxuehong
 * 
 *         2012-1-29
 */
public class CarEnterComposite extends Composite {

	private PageComposite pageComposite; // 分页控制面板
	private TableViewer tableViewer;
	private Table table;
	private boolean a = false;
	private Action deleteAction;

	public CarEnterComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, true));

		Group group = new Group(this, SWT.NONE);
		group.setText("\u5F53\u524D\u505C\u8F66\u8F66\u8F86\u4FE1\u606F");
		group.setLayout(new FillLayout(SWT.HORIZONTAL));
		group.setLayoutData(new GridData(GridData.FILL_BOTH));

		tableViewer = new TableViewer(group, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		tableViewer.setContentProvider(new carContentProvider());
		tableViewer.setLabelProvider(new CarLabelProvider());
		tableViewer.setSorter(new CarSorter());
		TableListener.tableCellHeight(table);
		TableListener.tableColor(table);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(80);
		tableColumn.setText("卡号");
		tableColumn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				a = !a;
				((CarSorter) tableViewer.getSorter()).doSort(a ? -100 : 100);
				tableViewer.refresh();
			}
		});

		TableColumn tableColumn_0 = new TableColumn(table, SWT.NONE);
		tableColumn_0.setWidth(120);
		tableColumn_0.setText("\u8F66\u724C\u53F7\u7801");
		tableColumn_0.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				a = !a;
				((CarSorter) tableViewer.getSorter()).doSort(a ? -1 : 1);
				tableViewer.refresh();
			}
		});

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(80);
		tableColumn_1.setText("\u5361\u7247\u7C7B\u578B");
		tableColumn_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				a = !a;
				((CarSorter) tableViewer.getSorter()).doSort(a ? -2 : 2);
				tableViewer.refresh();
			}
		});

		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(140);
		tableColumn_3.setText("\u5165\u573A\u65F6\u95F4");
		tableColumn_3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				a = !a;
				((CarSorter) tableViewer.getSorter()).doSort(a ? -3 : 3);
				tableViewer.refresh();
			}
		});

		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(120);
		tableColumn_4.setText("\u5907\u6CE8");

		pageComposite = new PageComposite(this, SWT.NONE, tableViewer);
		pageComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		makeActions(parent); // 初始化事件响应
		hookContextMenu();// 右键菜单
	}
	
	/**
	 * 初始化action对象
	 */
	public void makeActions(final Composite parent) {
		deleteAction = new Action() {
			public void run() {
				deleteAction(parent);
			}
		};
		deleteAction.setText("删除");
		deleteAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.DELETE_POP_MANAGE_TRAY));
	}
	
	/**
	 * 给tableViewer添加右键菜单
	 */
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				int count = tableViewer.getTable().getSelectionCount();
				if (count == 1) {
					deleteAction.setEnabled(true);
				}
				manager.add(deleteAction);
			}
		});
		//添加右键菜单
		Menu menu = menuMgr.createContextMenu(tableViewer.getControl());
		tableViewer.getControl().setMenu(menu);
//		getSite().registerContextMenu(menuMgr, tableViewer);//这一句没有同样可以出现删除，并且给出响应
	}
	
	
	/**
	 * 删除响应，删除一个车辆的入场信息
	 */
	public void deleteAction(Composite parent) {
		TableItem[] ti = tableViewer.getTable().getSelection();
		if (ti.length == 1) { // 如果选中了一行
			int a = MessageDialogUtil.showConfirmMessage(parent.getShell(), "确认删除该入场信息么？删除后将无法恢复！");
			if (a == SWT.CANCEL){
				return;
			}
			TCarEnter carEnter = (TCarEnter) ti[0].getData();
			CarEnterService carEnterService = new CarEnterService();
			try {
				carEnterService.delCarEnter(carEnter);
			} catch (Exception e1) {
				MessageDialogUtil.showWarningMessage(parent.getShell(), "信息删除失败!");
				return;
			}
			List<Object> carEnters = new ArrayList<Object>();
			if (carEnter != null) {
				carEnters.add(carEnter);
				pageComposite.updateAfterDeleting(carEnters);
			}
		}
	}

	public void setData(List<Object> carenters) {
		if (pageComposite != null && !pageComposite.isDisposed()) {
			pageComposite.setData(carenters, 30);
		}
	}

	/**
	 * 内容提供器
	 * 
	 * @author wuxuehong
	 * 
	 *         2012-1-29
	 */
	class carContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return ((List) inputElement).toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	/**
	 * 标签提供器
	 * 
	 * @author wuxuehong
	 * 
	 *         2012-1-29
	 */
	class CarLabelProvider implements ITableLabelProvider {
		public org.eclipse.swt.graphics.Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			TCarEnter carenter = (TCarEnter) element;
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			switch (columnIndex) {
			case 0:
				return carenter.getCardid();
			case 1: // 卡类型
				return carenter.getCarnumber();
			case 2: // 车牌号码
				return carenter.getTCard().getTCardType().getName();
			case 3: // 入场时间
				return sdf.format(carenter.getEntertime());
			case 4: // 备注信息
				return carenter.getRemark();
			default:
				break;
			}
			return null;
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}
	}

	/**
	 * 排序器
	 * 
	 * @author wuxuehong  2012-1-29
	 */
	class CarSorter extends ViewerSorter {
		private int column;

		public void doSort(int column) {
			this.column = column;
		}

		public int compare(Viewer viewer, Object e1, Object e2) {
			TCarEnter n1 = (TCarEnter) e1;
			TCarEnter n2 = (TCarEnter) e2;
			switch (column) {
			case 100: { // 卡号
				String str1 = n1.getCardid();
				String str2 = n2.getCardid();
				return str1.compareTo(str2);
			}
			case -100: {
				String str1 = n1.getCardid();
				String str2 = n2.getCardid();
				return str2.compareTo(str1);
			}
			case 1: { // 车牌号
				String str1 = n1.getCarnumber();
				String str2 = n2.getCarnumber();
				return str1.compareTo(str2);
			}
			case -1: {
				String str1 = n1.getCarnumber();
				String str2 = n2.getCarnumber();
				return str2.compareTo(str1);
			}
			case 2: { // 卡片类型
				String str1 = n1.getTCard().getTCardType().getName();
				String str2 = n2.getTCard().getTCardType().getName();
				return str1.compareTo(str2);
			}
			case -2: {
				String str1 = n1.getTCard().getTCardType().getName();
				String str2 = n2.getTCard().getTCardType().getName();
				return str2.compareTo(str1);
			}
			case 3: { // 入场日期
				Date str1 = n1.getEntertime();
				Date str2 = n2.getEntertime();
				return str1.compareTo(str2);
			}
			case -3: {
				Date str1 = n1.getEntertime();
				Date str2 = n2.getEntertime();
				return str2.compareTo(str1);
			}
			}
			return super.compare(viewer, e1, e2);
		}
	}
}
