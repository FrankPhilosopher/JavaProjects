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
import cn.edu.csu.dbhospital.db.TCardManager;
import cn.edu.csu.dbhospital.db.TUserManager;
import cn.edu.csu.dbhospital.dialog.TCardAddDialog;
import cn.edu.csu.dbhospital.dialog.TCardEditDialog;
import cn.edu.csu.dbhospital.pojo.TCard;
import cn.edu.csu.dbhospital.pojo.TUser;
import cn.edu.csu.dbhospital.util.MessageDialogUtil;
import cn.edu.csu.dbhospital.util.SystemUtil;

/**
 * ���ƿ�����editor
 * 
 * @author hjw
 * 
 */
public class CardManageEditor extends EditorPart {

	public static final String ID = CardManageEditor.class.getName();
	private Table table;
	private Text txtSearch;
	private TableViewer tableViewer;
	private TCardManager manager;
	private Action deleteAction;
	private Action editAction;
	private TUserManager userManager = new TUserManager();

	public CardManageEditor() {
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
		label.setText("\u67E5\u8BE2\u6761\u4EF6\uFF1A\u8BCA\u7597\u5361\u5E8F\u53F7");

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
		btnAdd.setText("\u6DFB\u52A0\u8BCA\u7597\u5361");
		btnAdd.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				TCardAddDialog dialog = new TCardAddDialog(Display.getDefault().getActiveShell());
				int result = dialog.open();
				if (result == IDialogConstants.OK_ID) {
					SystemUtil.showSystemMessage("������ƿ��ɹ�");
					txtSearch.setText("");
					searchByText("");
				} else if (result == SystemUtil.RESULT_FAIL) {
					MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "������ƿ�ʧ��!");
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
		tableViewer.setLabelProvider(new CardTableViewerLabelProvider());
		tableViewer.setContentProvider(new CardTableViewerContentProvider());
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableColumn tc_cardnum = new TableColumn(table, SWT.LEFT);
		tc_cardnum.setWidth(100);
		tc_cardnum.setText("\u8BCA\u7597\u5361\u5E8F\u53F7");

		TableColumn tc_realname = new TableColumn(table, SWT.LEFT);
		tc_realname.setWidth(107);
		tc_realname.setText("\u7528\u6237\u59D3\u540D");
		
		TableColumn tc_gander = new TableColumn(table, SWT.NONE);
		tc_gander.setWidth(85);
		tc_gander.setText("\u6027\u522B");

		TableColumn tc_card = new TableColumn(table, SWT.LEFT);
		tc_card.setWidth(211);
		tc_card.setText("\u8EAB\u4EFD\u8BC1\u53F7");

		TableColumn tc_phone = new TableColumn(table, SWT.NONE);
		tc_phone.setWidth(133);
		tc_phone.setText("\u8054\u7CFB\u7535\u8BDD");

		TableColumn tc_deal = new TableColumn(table, SWT.NONE);
		tc_deal.setWidth(100);
		tc_deal.setText("\u662F\u5426\u5F00\u5177");

		searchByText("");
		makeActions();
		hookContextMenu();

	}

	// ��������������ѯ
	private void searchByText(final String text) {
		manager = new TCardManager();

		new RcpTask() {
			private ArrayList<TCard> list;

			@Override
			protected void doTaskInBackground(StringBuffer message) {
				try {
					if ("".equalsIgnoreCase(text)) {
						list = manager.searchAll();
					} else {
						list = manager.searchByCardnum(text);
					}
					message.append(RcpTask.RESULT_OK);
				} catch (Exception e) {
					message.append(RcpTask.RESULT_FAIL);
					return;
				}
			}

			@Override
			protected void doBeforeTask() {
				SystemUtil.showSystemMessage("���ڼ�������...");
			}

			@Override
			protected void doAfterTask(String result) {
				if (result.equalsIgnoreCase(RcpTask.RESULT_OK)) {
					tableViewer.setInput(list);
					tableViewer.refresh();
					SystemUtil.showSystemMessage("���ݼ������");
				} else {
					MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "���ݼ���ʧ��!");
				}
			}
		}.execTask();

	}

	// ��ʼ��action����
	private void makeActions() {
		deleteAction = new Action() {
			public void run() {
				delete();
			}
		};
		deleteAction.setText("ɾ��");
		deleteAction.setImageDescriptor(Activator.getImageDescriptor(ImageKeyManager.POP_DELETE));

		editAction = new Action() {
			public void run() {
				edit();
			}
		};
		editAction.setText("�޸�");
		editAction.setImageDescriptor(Activator.getImageDescriptor(ImageKeyManager.POP_EDIT));
	}

	// ��tableViewer����Ҽ��˵�
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
		tableViewer.getControl().setMenu(menu);// ����Ҽ��˵�
	}

	// ɾ��
	public void delete() {
		TableItem[] tableItems = tableViewer.getTable().getSelection();
		if (tableItems.length == 1) { // ���ѡ����һ��
			int result = MessageDialogUtil.showConfirmMessage(Display.getDefault().getActiveShell(),
					"ȷ��Ҫɾ��ô��ɾ����Ҳ��ɾ����Ӧ���û�!");
			if (result == SWT.CANCEL) {
				return;
			}
			TCard card = (TCard) tableItems[0].getData();
			try {
				manager.delete(card);
				userManager.delete(card.getUserId());
			} catch (Exception e1) {
				MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "ɾ��ʧ��!");
				return;
			}
			searchByText(txtSearch.getText());
		}
	}

	// �޸�
	public void edit() {
		TableItem[] tableItems = tableViewer.getTable().getSelection();
		if (tableItems.length == 1) { // ���ѡ����һ��
			TCard card = (TCard) tableItems[0].getData();
			TCardEditDialog dialog = new TCardEditDialog(Display.getDefault().getActiveShell(), card);
			int result = dialog.open();
			if (result == IDialogConstants.OK_ID) {
				SystemUtil.showSystemMessage("�޸ĳɹ�");
				searchByText(txtSearch.getText());
			} else if (result == SystemUtil.RESULT_FAIL) {
				MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "�޸�ʧ��!");
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

	// �����ṩ��
	class CardTableViewerContentProvider implements IStructuredContentProvider {

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

	// ��ǩ�ṩ��
	class CardTableViewerLabelProvider implements ITableLabelProvider {

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
		// �õ�����Ҫ��ʾ���ı�
		public String getColumnText(Object element, int columnIndex) {
			TCard card = (TCard) element;
			TUser user = null;
			try {
				user = userManager.searchById(card.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			switch (columnIndex) {// ע�⣺�е������Ǵ�0��ʼ��
			case 0:
				return card.getCardnum();
			case 1:
				if (user != null) {
					return user.getRealname();
				}
			case 2:
				if (user != null) {
					if (user.getGander() == SystemUtil.GANSER_MALE_VALUE) {
						return SystemUtil.GANSER_MALE;
					}else {
						return SystemUtil.GANSER_FEMALE;
					}
				}
			case 3:
				if (user != null) {
					return user.getCard();
				}
			case 4:
				if (user != null) {
					return user.getPhone();
				}
			case 5:
				if (card.getDealed() == SystemUtil.YES_VALUE) {
					return "�Ѿ�������";
				} else {
					return "��û�п���";
				}
			}
			return "";// Ĭ�Ϸ��ؿ��ַ���
		}

	}
}
