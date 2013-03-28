package cn.edu.csu.dbhospital.editor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.wb.swt.SWTResourceManager;

import cn.edu.csu.dbhospital.db.TArrangementManager;
import cn.edu.csu.dbhospital.db.TDoctorManager;
import cn.edu.csu.dbhospital.db.TRoomManager;
import cn.edu.csu.dbhospital.dialog.TArrangementDialog;
import cn.edu.csu.dbhospital.pojo.TArrangement;
import cn.edu.csu.dbhospital.pojo.TRoom;
import cn.edu.csu.dbhospital.pojo.TTableArrangement;
import cn.edu.csu.dbhospital.util.MessageDialogUtil;
import cn.edu.csu.dbhospital.util.SystemUtil;

public class ArrangementManageEditor extends EditorPart {

	private static final int MONTH_DAYS = 30;
	public static final String ID = ArrangementManageEditor.class.getName();
	private Table table;
	private TableViewer tableViewer;
	private TRoomManager roomManager = new TRoomManager();
	private TDoctorManager doctorManager = new TDoctorManager();
	private TArrangementManager arrangementManager = new TArrangementManager();
	private ArrayList<TRoom> roomList;
	private ArrayList<TArrangement> arrangementList;
	private Combo cb_room;
	private Date fromDate;
	private Date endDate;
	private int currentRoom = 0;
	private boolean firstin = true;

	public final String[] COLUMN_NAMES = new String[] { "DATE", "ARRANGEMENT_ONE", "ARRANGEMENT_TWO" };
	private TableCursor cursor;
	private ArrayList<TTableArrangement> tableArrangementList;

	public ArrangementManageEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
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
	public void createPartControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(container, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_composite.heightHint = 37;
		composite.setLayoutData(gd_composite);

		Label label = new Label(composite, SWT.NONE);
		label.setBounds(10, 10, 54, 17);
		label.setText("\u9009\u62E9\u79D1\u5BA4");

		cb_room = new Combo(composite, SWT.NONE);
		cb_room.setBounds(69, 6, 88, 25);
		cb_room.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (firstin) {
					return;
				}
				currentRoom = cb_room.getSelectionIndex();
				fillTableData();
			}
		});
		try {
			roomList = roomManager.searchAll();
			for (TRoom room : roomList) {
				cb_room.add(room.getName());
				cb_room.setData(room.getName(), room);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_1.setBounds(184, 10, 490, 17);
		label_1.setText("\u6CE8\uFF1A\u8FD9\u91CC\u663E\u793A\u7684\u662F\u53EF\u9884\u7EA6\u7684\u4E00\u4E2A\u6708\u5185\u7684\u6392\u73ED\u60C5\u51B5\uFF0C\u53CC\u51FB\u5355\u5143\u683C\u5373\u53EF\u5BF9\u6392\u73ED\u8FDB\u884C\u6DFB\u52A0\u548C\u4FEE\u6539");

		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableColumn tc_day = new TableColumn(table, SWT.NONE);
		tc_day.setWidth(140);
		tc_day.setText("\u6708\u4EFD\u4E2D\u7684\u65E5\u671F");

		TableColumn tc_one = new TableColumn(table, SWT.NONE);
		tc_one.setWidth(325);
		tc_one.setText("\u7B2C\u4E00\u73ED\u7684\u6392\u73ED\u60C5\u51B5");

		TableColumn tc_two = new TableColumn(table, SWT.NONE);
		tc_two.setWidth(325);
		tc_two.setText("\u7B2C\u4E8C\u73ED\u7684\u6392\u73ED\u60C5\u51B5");

		cursor = new TableCursor(table, SWT.NONE);
		cursor.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if (cursor.getColumn() == 0) {
					return;
				}
				TTableArrangement tableArrangement = (TTableArrangement) cursor.getData(cursor.getRow().getText());
				TArrangement arrangement = null;
				if (cursor.getColumn() == 1) {
					arrangement = tableArrangement.arrangementOne;
				} else {
					arrangement = tableArrangement.arrangementTwo;
				}
				TArrangementDialog dialog = new TArrangementDialog(Display.getDefault().getActiveShell(), arrangement,
						tableArrangement, cursor.getColumn());
				int result = dialog.open();// todo
				if (result == IDialogConstants.OK_ID) {
					SystemUtil.showSystemMessage("操作成功");
				} else if (result == SystemUtil.RESULT_FAIL) {
					MessageDialogUtil.showWarningMessage(Display.getDefault().getActiveShell(), "操作失败!");
				}
				// tableViewer.setInput(tableArrangementList);//这种刷新没有用
				// tableViewer.refresh();
				fillTableData();
			}
		});

		tableViewer.setLabelProvider(new ArrangementTableViewerLabelProvider());
		tableViewer.setContentProvider(new ArrangementTableViewerContentProvider());

		fillTableData();
	}

	// 填充表格中的数据
	private void fillTableData() {
		if (roomList.size() <= 0) {
			return;
		}
		if (currentRoom == -1) {// currentRoom可能会被置为0
			currentRoom = 0;
		}
		if (firstin) {
			cb_room.setText(roomList.get(currentRoom).getName());
			firstin = false;// 使用它就是为了进来的时候默认是选中了第一个
		}
		Calendar calendarToday = Calendar.getInstance();
		fromDate = calendarToday.getTime();
		calendarToday.add(Calendar.DATE, MONTH_DAYS);
		endDate = calendarToday.getTime();
		try {
			arrangementList = arrangementManager.searchAll(((TRoom) cb_room.getData(cb_room.getText())).getId(),
					fromDate, endDate);
			System.out.println(arrangementList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		tableArrangementList = new ArrayList<TTableArrangement>(MONTH_DAYS);
		TTableArrangement tableArrangement = null;
		TArrangement arrangement = null;
		for (int i = 0; i < MONTH_DAYS; i++) {
			calendar.add(Calendar.DATE, 1);// 每次增加一天
			tableArrangement = new TTableArrangement();
			tableArrangement.date = calendar.getTime();
			tableArrangement.room = roomList.get(currentRoom);

			for (int j = 0; j < arrangementList.size(); j++) {
				arrangement = arrangementList.get(j);
				if (SystemUtil.formatDate(arrangement.getDate()).equals(SystemUtil.formatDate(tableArrangement.date))) {// 日期相同
					if (arrangement.getNum() == SystemUtil.ARRANGEMENT_ONE) {// 第一班
						tableArrangement.arrangementOne = arrangement;
					} else if (arrangement.getNum() == SystemUtil.ARRANGEMENT_TWO) {// 第二班
						tableArrangement.arrangementTwo = arrangement;
					}
				}
			}

			tableArrangementList.add(tableArrangement);
			cursor.setData(SystemUtil.formatDate(tableArrangement.date), tableArrangement);
		}

		tableViewer.setInput(tableArrangementList);
		// tableViewer.setColumnProperties(COLUMN_NAMES);
		//
		// CellEditor[] cellEditors = new CellEditor[3];
		// cellEditors[1] = new DialogCellEditor(table) {
		// protected Object openDialogBox(Control cellEditorWindow) {
		// System.out.println(cellEditorWindow);
		// TArrangementAddDialog dialog = new TArrangementAddDialog(cellEditorWindow.getShell());
		// return dialog.open();
		// }
		// };
		//
		// cellEditors[2] = new DialogCellEditor(table) {
		// protected Object openDialogBox(Control cellEditorWindow) {
		// System.out.println(cellEditorWindow);
		// TArrangementAddDialog dialog = new TArrangementAddDialog(cellEditorWindow.getShell());
		// return dialog.open();
		// }
		// };
		//
		// tableViewer.setCellEditors(cellEditors);
		// tableViewer.setCellModifier(new TableCellModifier());
	}

	@Override
	public void setFocus() {
	}

	// 内容提供者
	class ArrangementTableViewerContentProvider implements IStructuredContentProvider {

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
	class ArrangementTableViewerLabelProvider implements ITableLabelProvider {

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
			TTableArrangement tableArrangement = (TTableArrangement) element;
			try {
				switch (columnIndex) {// 注意：列的索引是从0开始的
				case 0:
					return SystemUtil.formatDateWithWeek(tableArrangement.date);
				case 1:
					if (tableArrangement.arrangementOne != null) {
						return "医生:"
								+ doctorManager.searchById(tableArrangement.arrangementOne.getDoctorId()).getName()
								+ "   诊疗费:" + tableArrangement.arrangementOne.getExpense() + "元    人数上限:"
								+ tableArrangement.arrangementOne.getLimit() + "人";
					} else {
						return "未排班";
					}
				case 2:
					if (tableArrangement.arrangementTwo != null) {
						return "医生:"
								+ doctorManager.searchById(tableArrangement.arrangementTwo.getDoctorId()).getName()
								+ "   诊疗费:" + tableArrangement.arrangementTwo.getExpense() + "元    人数上限:"
								+ tableArrangement.arrangementTwo.getLimit() + "人";
					} else {
						return "未排班";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";// 默认返回空字符串
		}

	}

	class TableCellModifier implements ICellModifier {

		public boolean canModify(Object element, String property) {
			if (property.equalsIgnoreCase(COLUMN_NAMES[0])) {
				return false;
			}
			return true;
		}

		public Object getValue(Object element, String property) {
			return "点击小按钮添加或者修改排班信息";
		}

		public void modify(Object element, String property, Object value) {

		}

	}

}
