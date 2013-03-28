package com.rcp.wxh.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import parkingsystem.Activator;

import com.rcp.wbw.skin.LookAndFeel;
import com.rcp.wxh.composite.PageComposite;
import com.rcp.wxh.dialogs.TEmpDialog;
import com.rcp.wxh.dialogs.TEmpEditDialog;
import com.rcp.wxh.fortables.EmpContentProvider;
import com.rcp.wxh.fortables.EmpLabelProvider;
import com.rcp.wxh.fortables.TableListener;
import com.rcp.wxh.pojo.TEmp;
import com.rcp.wxh.resource.IimageKeys;
import com.rcp.wxh.service.impl.EmpService;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * 员工信息管理编辑器
 * 
 * @author wuxuehong 2011-11-16
 * 
 */
public class EmpEditor2 extends EditorPart {

	public static final String ID = EmpEditor2.class.getName();

	private Table table;
	private Text text;
	private TableViewer tableViewer;

	private Action editAction; // 编辑
	private Action deleteAction; // 删除
	private Action resetAction; // 密码重置

	private int sizePage = 25; // 每页显示数

	private PageComposite pageComposite; // 分页面板

	public EmpEditor2() {
	}

	public void doSave(IProgressMonitor monitor) {

	}

	public void doSaveAs() {
	}

	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	public boolean isDirty() {
		return false;
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	public void createPartControl(final Composite parent) {
		parent.setLayout(new GridLayout(1, true));

		parent.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		parent.setBackgroundMode(SWT.INHERIT_FORCE);

		Group group = new Group(parent, SWT.NONE);
		group.setText("\u5458\u5DE5\u4FE1\u606F");
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		Composite composite = new Composite(group, SWT.BORDER);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Combo combo = new Combo(composite, SWT.NONE);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // combo事件
				if ("全部".equals(combo.getText())) {
					text.setText("");
					text.setEnabled(false);//如果选择全部，那么后面的搜索框不可编辑
				} else {
					text.setEnabled(true);
				}
			}
		});
		combo.setItems(new String[] { "\u5168\u90E8", "\u5458\u5DE5ID", "\u5458\u5DE5\u59D3\u540D" });
		combo.setText("\u5168\u90E8");

		Label label = new Label(composite, SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("宋体", 14, SWT.NORMAL));
		label.setLayoutData(new RowData(16, 19));
		label.setText("=");

		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new RowData(120, 14));
		text.setEnabled(false);

		Button button = new Button(composite, SWT.NONE);
		button.setLayoutData(new RowData(72, 20));
		button.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/find.ico"));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 查询
				pageComposite.init(); // 分页初始化 清除表格中数据
				String op = combo.getText().trim();
				EmpService es = new EmpService();
				try {
					if ("全部".equals(combo.getText().trim())) { // 查询所有
						List<Object> emps = null;
						emps = es.getAllEmp();
						// 获取所有员工信息
						if (emps != null)
							pageComposite.setData(emps, sizePage);
					} else if ("员工ID".equals(op)) {
						TEmp emp = es.getEmpById(text.getText().trim());
						if (emp != null) {
							List<Object> emps = new ArrayList<Object>();
							emps.add(emp);
							pageComposite.setData(emps, sizePage);
						} else {
							MessageDialogUtil.showWarningMessage(parent.getShell(), "该员工ID不存在!");
						}
					} else if ("员工姓名".equals(op)) {
						List<Object> emps = es.getEmpByName(text.getText().trim());
						if (emps != null)
							pageComposite.setData(emps, sizePage);
						else
							MessageDialogUtil.showWarningMessage(parent.getShell(), "该员工姓名不存在!");
					}
				} catch (Exception e1) {
					MessageDialogUtil.showWarningMessage(parent.getShell(), "获取员工信息失败!");
					return;
				}
			}
		});
		button.setText("\u67E5  \u8BE2");

		Button button_1 = new Button(composite, SWT.NONE);
		button_1.setLayoutData(new RowData(84, 20));
		button_1.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/add.ico"));
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 新增员工 打开新增员工对话框
				TEmpDialog ted = new TEmpDialog(parent.getShell(), SWT.NONE);
				Object o = ted.open();
				if (o != null)
					pageComposite.updateAfterAdding(o);
			}
		});
		button_1.setText("\u65B0\u589E\u5458\u5DE5");

		tableViewer = new TableViewer(group, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		tableViewer.setContentProvider(new EmpContentProvider());
		tableViewer.setLabelProvider(new EmpLabelProvider());

		TableColumn tblclmnid = new TableColumn(table, SWT.NONE);
		tblclmnid.setWidth(100);
		tblclmnid.setText("\u5458\u5DE5ID");

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(78);
		tableColumn_1.setText("\u5458\u5DE5\u59D3\u540D");

		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(91);
		tableColumn_2.setText("\u64CD\u4F5C\u6743\u9650");

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("\u8054\u7CFB\u65B9\u5F0F");

		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(102);
		tableColumn_3.setText("\u8BC1\u4EF6\u53F7\u7801");

		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("\u4F4F\u5740");

		TableColumn tableColumn_5 = new TableColumn(table, SWT.NONE);
		tableColumn_5.setWidth(158);
		tableColumn_5.setText("\u5907\u6CE8\u4FE1\u606F");
		
		TableListener.tableCellHeight(table);
		TableListener.tableColor(table);

		// tableViewer是这个 editor 和 composite 的共同的部分
		pageComposite = new PageComposite(group, SWT.NONE, tableViewer);
		pageComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		makeActions(parent); // 初始化事件响应 action
		hookContextMenu(); // 右键菜单
		hookDoubleClickAction(parent);
	}

	/**
	 * 鼠标双击
	 */
	private void hookDoubleClickAction(final Composite parent) {
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {// 鼠标双击编辑卡片信息
					public void doubleClick(DoubleClickEvent event) {
						editAction(parent);
					}
				});
	}

	/**
	 * 给tableViewer添加右键菜单
	 */
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				editAction.setEnabled(false);
				deleteAction.setEnabled(false);
				resetAction.setEnabled(false);
				int count = tableViewer.getTable().getSelectionCount();
				if (count == 1) {
					editAction.setEnabled(true);
					deleteAction.setEnabled(true);
					resetAction.setEnabled(true);
				}
				manager.add(editAction);
				manager.add(deleteAction);
				manager.add(new Separator());
				manager.add(resetAction);
			}
		});
		//添加右键菜单
		Menu menu = menuMgr.createContextMenu(tableViewer.getControl());
		tableViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, tableViewer);
	}

	/**
	 * 编辑响应
	 * 
	 * @param parent
	 */
	public void editAction(Composite parent) {
		TableItem[] ti = tableViewer.getTable().getSelection();
		if (ti.length == 1) { // 如果选中了一行
			TEmp emp = (TEmp) ti[0].getData();
			if (emp != null) {
				TEmpEditDialog teed = new TEmpEditDialog(parent.getShell(), SWT.NONE, emp);
				Object o = teed.open();
				if (o != null)
					tableViewer.refresh();
			}
		}
	}

	/**
	 * 删除响应
	 * 
	 * @param parent
	 */
	public void deleteAction(Composite parent) {
//		Object object = ((IStructuredSelection)tableViewer.getSelection()).getFirstElement();
		TableItem[] ti = tableViewer.getTable().getSelection();
		if (ti.length == 1) { // 如果选中了一行
			int a = MessageDialogUtil.showConfirmMessage(parent.getShell(), "确认删除该员工信息么?删除后将无法恢复!");
			if (a == SWT.CANCEL)
				return;
			TEmp emp = (TEmp) ti[0].getData();//data？
			EmpService es = new EmpService();
			if (emp.getPriority().intValue() == TEmp.ADMINSTRATOR) { // 如果是删除管理员信息
				try {
					List<Object> admins = es.getAllAdmin();
					if (admins.size() == 1) {
						MessageDialogUtil.showWarningMessage(parent.getShell(), "系统中必须要保留一名管理员!");
						return;
					}
				} catch (Exception e) {
					MessageDialogUtil.showWarningMessage(parent.getShell(), "管理员信息获取失败!");
					return;
				}
			}
			try {
				es.delEmp(emp);
			} catch (Exception e) {
				MessageDialogUtil.showWarningMessage(parent.getShell(), "员工信息删除失败!");
				return;
			}
			List<Object> emps = new ArrayList<Object>();
			if (emp != null) {
				emps.add(emp);
				pageComposite.updateAfterDeleting(emps);
			}
		}
	}

	/**
	 * 密码重置响应
	 * 
	 * @param parent
	 */
	public void resetAction(Composite parent) {
		TableItem[] ti = tableViewer.getTable().getSelection();
		if (ti.length == 1) {
			int a = MessageDialogUtil.showConfirmMessage(parent.getShell(), "确定重置该员工密码么?");
			if (a == SWT.CANCEL)
				return;
			TEmp emp = (TEmp) ti[0].getData();
			EmpService es = new EmpService();
			if (emp != null) {
				emp.setPassword(emp.getOperatorid());//将密码重置为员工id
				try {
					es.updateEmp(emp);
				} catch (Exception e) {
					MessageDialogUtil.showWarningMessage(parent.getShell(), "员工密码重置失败!");
					return;
				}
				MessageDialogUtil.showInfoMessage(parent.getShell(), "密码重置成功!");
			}
		}
	}

	/**
	 * 初始化action对象
	 */
	public void makeActions(final Composite parent) {
		editAction = new Action() {
			public void run() {
				editAction(parent);
			}
		};
		editAction.setText("编辑");
		editAction.setToolTipText("编辑员工信息");
		editAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.EDIT_POP_MANAGE_TRAY));

		deleteAction = new Action() {
			public void run() {
				deleteAction(parent);
			}
		};
		deleteAction.setText("删除");
		deleteAction.setToolTipText("删除员工信息");
		deleteAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.DELETE_POP_MANAGE_TRAY));

		resetAction = new Action() {
			public void run() {
				resetAction(parent);
			}
		};
		resetAction.setText("密码重置");
		resetAction.setToolTipText("重置员工密码");
		resetAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.RESET_POP_MANAGE_TRAY));
	}

	@Override
	public void setFocus() {

	}
}
