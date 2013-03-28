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
import org.eclipse.wb.swt.ImageKeyManager;
import org.eclipse.wb.swt.RcpTask;

import cn.edu.csu.dbhospital.Activator;
import cn.edu.csu.dbhospital.db.TDoctorManager;
import cn.edu.csu.dbhospital.db.TRoomManager;
import cn.edu.csu.dbhospital.dialog.TDoctorAddDialog;
import cn.edu.csu.dbhospital.dialog.TDoctorEditDialog;
import cn.edu.csu.dbhospital.pojo.TDoctor;
import cn.edu.csu.dbhospital.util.MessageDialogUtil;
import cn.edu.csu.dbhospital.util.SystemUtil;

/**
 * 医生管理editor
 * 
 * @author hjw
 * 
 */
public class DoctorManageEditor extends EditorPart {

	public static final String ID = DoctorManageEditor.class.getName();
	private Table table;
	private Text txtSearch;
	private TableViewer tableViewer;
	private TDoctorManager manager;
	private Action deleteAction;
	private Action editAction;

	public DoctorManageEditor() {
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

		Label label = new Label(searchcomposite, SWT.NONE);
		FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(0, 131);
		fd_label.left = new FormAttachment(0, 10);
		fd_label.top = new FormAttachment(0, 8);
		label.setLayoutData(fd_label);
		label.setText("\u67E5\u8BE2\u6761\u4EF6\uFF1A\u533B\u751F\u59D3\u540D");

		txtSearch = new Text(searchcomposite, SWT.BORDER);
		FormData fd_txtSearch = new FormData();
		fd_txtSearch.left = new FormAttachment(label, 6);
		fd_txtSearch.top = new FormAttachment(0, 5);
		txtSearch.setLayoutData(fd_txtSearch);

		Button btnSearch = new Button(searchcomposite, SWT.NONE);
		fd_txtSearch.right = new FormAttachment(btnSearch, -6);
		FormData fd_btnSearch = new FormData();
		fd_btnSearch.right = new FormAttachment(0, 341);
		fd_btnSearch.left = new FormAttachment(0, 261);
		fd_btnSearch.top = new FormAttachment(0, 3);
		btnSearch.setLayoutData(fd_btnSearch);
		btnSearch.setText("\u67E5\u8BE2");

		btnSearch.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				searchByText(txtSearch.getText());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Button btnAdd = new Button(searchcomposite, SWT.NONE);
		FormData fd_btnAdd = new FormData();
		fd_btnAdd.left = new FormAttachment(100, -90);
		fd_btnAdd.top = new FormAttachment(0, 3);
		fd_btnAdd.right = new FormAttachment(100, -10);
		btnAdd.setLayoutData(fd_btnAdd);
		btnAdd.setText("\u6DFB\u52A0\u533B\u751F");
		btnAdd.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				TDoctorAddDialog dialog = new TDoctorAddDialog(Display.getDefault().getActiveShell());
				int result = dialog.open();
				if (result == IDialogConstants.OK_ID) {
					SystemUtil.showSystemMessage("添加医生成功");
					txtSearch.setText("");
					searchByText("");
				} else if (result == SystemUtil.RESULT_FAIL) {
					MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "添加医生失败!");
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
		tableViewer.setLabelProvider(new DoctorTableViewerLabelProvider());
		tableViewer.setContentProvider(new DoctorTableViewerContentProvider());
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableColumn tc_name = new TableColumn(table, SWT.LEFT);
		tc_name.setWidth(100);
		tc_name.setText("\u533B\u751F\u59D3\u540D");

		TableColumn tc_room = new TableColumn(table, SWT.LEFT);
		tc_room.setWidth(100);
		tc_room.setText("\u6240\u5728\u79D1\u5BA4");

		TableColumn tc_title = new TableColumn(table, SWT.LEFT);
		tc_title.setWidth(100);
		tc_title.setText("\u533B\u751F\u804C\u79F0");

		TableColumn tc_phone = new TableColumn(table, SWT.NONE);
		tc_phone.setWidth(100);
		tc_phone.setText("\u8054\u7CFB\u7535\u8BDD");

		TableColumn tc_info = new TableColumn(table, SWT.NONE);
		tc_info.setWidth(200);
		tc_info.setText("\u4E2A\u4EBA\u4FE1\u606F");

		TableColumn tc_time = new TableColumn(table, SWT.NONE);
		tc_time.setWidth(150);
		tc_time.setText("\u6CE8\u518C\u65F6\u95F4");

		searchByText("");
		makeActions();
		hookContextMenu();

	}

	// 根据名称条件查询
	private void searchByText(final String text) {
		manager = new TDoctorManager();
		new RcpTask() {
			private ArrayList<TDoctor> list;

			@Override
			protected void doTaskInBackground(StringBuffer message) {
				try {
					if ("".equalsIgnoreCase(text)) {
						list = manager.searchAll();
					} else {
						list = manager.searchByName(text);
					}
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
			TDoctor doctor = (TDoctor) tableItems[0].getData();
			try {
				manager.delete(doctor);
			} catch (Exception e1) {
				MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "删除失败!");
				return;
			}
			searchByText(txtSearch.getText());
		}
	}

	// 修改
	public void edit() {
		TableItem[] tableItems = tableViewer.getTable().getSelection();
		if (tableItems.length == 1) { // 如果选中了一行
			TDoctor doctor = (TDoctor) tableItems[0].getData();
			TDoctorEditDialog dialog = new TDoctorEditDialog(Display.getDefault().getActiveShell(), doctor);
			int result = dialog.open();
			if (result == IDialogConstants.OK_ID) {
				SystemUtil.showSystemMessage("修改成功");
				searchByText(txtSearch.getText());
			} else if (result == SystemUtil.RESULT_FAIL) {
				MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "修改失败!");
			}
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
	class DoctorTableViewerContentProvider implements IStructuredContentProvider {

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
	class DoctorTableViewerLabelProvider implements ITableLabelProvider {

		private TRoomManager roomManager = new TRoomManager();

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
			TDoctor doctor = (TDoctor) element;
			switch (columnIndex) {// 注意：列的索引是从0开始的
			case 0:
				return doctor.getName();
			case 1:
				try {
					return roomManager.searchByID(doctor.getRoomId()).getName();
				} catch (Exception e) {
					// e.printStackTrace();
					return "未知科室";
				}
			case 2:
				return doctor.getTitle();
			case 3:
				return doctor.getPhone();
			case 4:
				return doctor.getInfo();
			case 5:
				return SystemUtil.formatDate(doctor.getRegtime());
			}
			return "";// 默认返回空字符串
		}

	}
}
