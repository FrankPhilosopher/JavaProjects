package cn.edu.csu.dbhospital.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.RcpTask;

import cn.edu.csu.dbhospital.Activator;
import cn.edu.csu.dbhospital.db.TCardManager;
import cn.edu.csu.dbhospital.db.TRegisterManager;
import cn.edu.csu.dbhospital.db.TUserManager;
import cn.edu.csu.dbhospital.dialog.TRegisterAddDiaog;
import cn.edu.csu.dbhospital.pojo.TTableRegister;
import cn.edu.csu.dbhospital.pojo.TUser;
import cn.edu.csu.dbhospital.util.MessageDialogUtil;
import cn.edu.csu.dbhospital.util.SystemUtil;

/**
 * 挂号管理editor
 * 
 * @author hjw
 * 
 */
public class RegisterManageEditor extends EditorPart {

	public static final String ID = RegisterManageEditor.class.getName();
	private Table table;
	private TableViewer tableViewer;
	private Action deleteAction;
	private Action editAction;
	private TUserManager userManager = new TUserManager();
	private TCardManager cardManager = new TCardManager();
	private TRegisterManager registerManager = new TRegisterManager();

	public RegisterManageEditor() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public void createPartControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));

		Composite searchcomposite = new Composite(container, SWT.NONE);
		searchcomposite.setLayout(new FormLayout());
		GridData gd_searchcomposite = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_searchcomposite.heightHint = 33;
		gd_searchcomposite.widthHint = 485;
		searchcomposite.setLayoutData(gd_searchcomposite);

		Button btnAdd = new Button(searchcomposite, SWT.NONE);
		FormData fd_btnAdd = new FormData();
		fd_btnAdd.right = new FormAttachment(0, 90);
		fd_btnAdd.left = new FormAttachment(0, 10);
		fd_btnAdd.top = new FormAttachment(0, 3);
		btnAdd.setLayoutData(fd_btnAdd);
		btnAdd.setText("\u6302\u53F7");
		btnAdd.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				TRegisterAddDiaog dialog = new TRegisterAddDiaog(Display.getDefault().getActiveShell());
				int result = dialog.open();
				if (result == IDialogConstants.OK_ID) {
					SystemUtil.showSystemMessage("挂号成功");
					loadData();
				} else if (result == SystemUtil.RESULT_FAIL) {
					MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "挂号失败!");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Composite tablecomposite = new Composite(container, SWT.NONE);
		tablecomposite.setLayout(new GridLayout(1, false));
		tablecomposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		tableViewer = new TableViewer(tablecomposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setLabelProvider(new RegisterTableViewerLabelProvider());
		tableViewer.setContentProvider(new RegisterTableViewerContentProvider());
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableColumn tc_number = new TableColumn(table, SWT.LEFT);
		tc_number.setWidth(153);
		tc_number.setText("\u5019\u8BCA\u53F7");

		TableColumn tc_cardnum = new TableColumn(table, SWT.LEFT);
		tc_cardnum.setWidth(151);
		tc_cardnum.setText("\u8BCA\u7597\u5361\u53F7");

		TableColumn tc_realname = new TableColumn(table, SWT.LEFT);
		tc_realname.setWidth(90);
		tc_realname.setText("\u7528\u6237\u59D3\u540D");

		TableColumn tc_room = new TableColumn(table, SWT.NONE);
		tc_room.setWidth(100);
		tc_room.setText("\u79D1\u5BA4");

		TableColumn tc_doctor = new TableColumn(table, SWT.NONE);
		tc_doctor.setWidth(133);
		tc_doctor.setText("\u533B\u751F");

		TableColumn tc_expense = new TableColumn(table, SWT.NONE);
		tc_expense.setWidth(121);
		tc_expense.setText("\u8D39\u7528");

		loadData();
		makeActions();
		hookContextMenu();

	}

	private void loadData() {
		new RcpTask() {
			private ArrayList<TTableRegister> list;

			@Override
			protected void doTaskInBackground(StringBuffer message) {
				try {
					list = registerManager.searchGuahao();
					message.append(RcpTask.RESULT_OK);
				} catch (Exception e) {
					message.append(RcpTask.RESULT_FAIL);
					return;
				}
			}

			@Override
			protected void doBeforeTask() {
				SystemUtil.showSystemMessage("正在加载数据...");
			}

			@Override
			protected void doAfterTask(String result) {
				if (result.equalsIgnoreCase(RcpTask.RESULT_OK)) {
					tableViewer.setInput(list);
					tableViewer.refresh();
					SystemUtil.showSystemMessage("数据加载完毕");
				} else {
					MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "数据加载失败!");
				}
			}
		}.execTask();

	}

	// 初始化action对象
	private void makeActions() {
		deleteAction = new Action() {
			public void run() {
				delete();
			}
		};
		deleteAction.setText("删除");
		deleteAction.setImageDescriptor(Activator.getImageDescriptor(ImageKeyManager.POP_DELETE));

		editAction = new Action() {
			public void run() {
				edit();
			}
		};
		editAction.setText("修改");
		editAction.setImageDescriptor(Activator.getImageDescriptor(ImageKeyManager.POP_EDIT));
	}

	// 给tableViewer添加右键菜单
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				int count = tableViewer.getTable().getSelectionCount();
				if (count == 1) {
					manager.add(editAction);
					manager.add(deleteAction);
				}
			}
		});
		Menu menu = menuMgr.createContextMenu(tableViewer.getControl());
		tableViewer.getControl().setMenu(menu);// 添加右键菜单
	}

	// 删除
	public void delete() {
		TableItem[] tableItems = tableViewer.getTable().getSelection();
		if (tableItems.length == 1) { // 如果选中了一行
			int result = MessageDialogUtil
					.showConfirmMessage(Display.getDefault().getActiveShell(), "确认要删除么？删除后将无法恢复!");
			if (result == SWT.CANCEL) {
				return;
			}
			TTableRegister tableRegister = (TTableRegister) tableItems[0].getData();
			try {
				registerManager.delete(tableRegister.registerId);
			} catch (Exception e1) {
				MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "删除失败!");
				return;
			}
			loadData();
		}
	}

	// 修改
	public void edit() {
		TableItem[] tableItems = tableViewer.getTable().getSelection();
		if (tableItems.length == 1) { // 如果选中了一行
			TTableRegister tableRegister = (TTableRegister) tableItems[0].getData();
			// TCardEditDialog dialog = new TCardEditDialog(Display.getDefault().getActiveShell(), card);
			// int result = dialog.open();
			// if (result == IDialogConstants.OK_ID) {
			// SystemUtil.showSystemMessage("修改成功");
			// searchByText(txtSearch.getText());
			// } else if (result == SystemUtil.RESULT_FAIL) {
			// MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "修改失败!");
			// }
		}
	}

	@Override
	public void setFocus() {
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	// 内容提供者
	class RegisterTableViewerContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return ((List) inputElement).toArray();
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	// 标签提供者
	class RegisterTableViewerLabelProvider implements ITableLabelProvider {

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		// 得到列中要显示的文本
		public String getColumnText(Object element, int columnIndex) {
			TTableRegister tableRegister = (TTableRegister) element;
			TUser user = null;
			try {
				user = userManager.searchById(tableRegister.userId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			switch (columnIndex) {// 注意：列的索引是从0开始的
			case 0:
				return tableRegister.number;
			case 1:
				if (user != null) {
					return user.getCardnum();
				}
			case 2:
				if (user != null) {
					return user.getRealname();
				}
			case 3:
				return tableRegister.roomName;
			case 4:
				return tableRegister.doctorName;
			case 5:
				return tableRegister.expense + "";
			}
			return "";// 默认返回空字符串
		}

	}
}
