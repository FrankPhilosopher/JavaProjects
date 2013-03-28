package com.rcp.wxh.editors;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.rcp.wbw.skin.LookAndFeel;
import com.rcp.wxh.actions.ExcelExportAction;
import com.rcp.wxh.composite.PageComposite;
import com.rcp.wxh.fortables.CarRecordContentProvider;
import com.rcp.wxh.fortables.CarRecordLabelProvider;
import com.rcp.wxh.fortables.TableListener;
import com.rcp.wxh.service.impl.CarRecordService;
import com.rcp.wxh.utils.CardUtil;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * 车辆出入记录展示 编辑器
 * 
 * @author wuxuehong 2011-11-16
 * 
 */
public class CarRecordEditor extends EditorPart {

	public static final String ID = CarRecordEditor.class.getName(); // ID
	private PageComposite composite_1; // 分页面板
	private int sizePage = 40;

	public CarRecordEditor() {
	}

	private Table table;
	private TableColumn tableColumn;
	private TableColumn tableColumn_1;
	private TableColumn tableColumn_2;
	private TableColumn tableColumn_3;
	private TableColumn tableColumn_4;
	private Text text;
	private DateTime dateTime;
	private DateTime dateTime_1;
	private Label label_1;
	private Label label_2;
	private TableColumn tableColumn_5;
	private TableViewer tableViewer;
	private TableColumn tableColumn_6;
	private TableColumn tableColumn_7;
	private TableColumn tableColumn_8;
	private Button button_1;

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

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		dateTime = new DateTime(composite, SWT.BORDER);

		label_1 = new Label(composite, SWT.CENTER);
		label_1.setFont(SWTResourceManager.getFont("宋体", 14, SWT.NORMAL));
		label_1.setLayoutData(new RowData(16, 21));
		label_1.setText("\u2014");

		dateTime_1 = new DateTime(composite, SWT.BORDER);

		label_2 = new Label(composite, SWT.NONE);
		label_2.setLayoutData(new RowData(23, 21));

		final Combo combo = new Combo(composite, SWT.NONE);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 下拉框选择响应
				if ("全部".equals(combo.getText())) {
					text.setText("");
					text.setEnabled(false);
				} else if ("卡号".equals(combo.getText().trim())) {
					text.setText("请将卡放入感应区");
					text.setEnabled(false);
				} else {
					text.setText("");
					text.setEnabled(true);
				}
			}
		});
		combo.setLayoutData(new RowData(88, 20));
		combo.setItems(new String[] { "\u5168\u90E8", "\u5361\u53F7", "\u8F66\u724C\u53F7\u7801",
				"\u8F66\u4E3B\u59D3\u540D" });
		combo.setText("\u5168\u90E8");

		Label label = new Label(composite, SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("宋体", 14, SWT.NORMAL));
		label.setLayoutData(new RowData(17, 19));
		label.setText("=");

		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new RowData(102, 14));
		text.setTextLimit(20);
		text.setEnabled(false);

		Button button = new Button(composite, SWT.NONE);
		button.setLayoutData(new RowData(SWT.DEFAULT, 20));
		button.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/find.ico"));
		button.addSelectionListener(new SelectionAdapter() { // 查询车辆出入记录
			@Override
			public void widgetSelected(SelectionEvent e) {
				composite_1.init();// 清空列表

				CarRecordService ers = new CarRecordService();
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, dateTime.getYear());
				cal.set(Calendar.MONTH, dateTime.getMonth());
				cal.set(Calendar.DATE, dateTime.getDay());
				cal.set(Calendar.HOUR, -12);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				Date startTime = cal.getTime();

				Calendar cal2 = Calendar.getInstance();
				cal2.set(Calendar.YEAR, dateTime_1.getYear());
				cal2.set(Calendar.MONTH, dateTime_1.getMonth());
				cal2.set(Calendar.DATE, dateTime_1.getDay());
				cal2.set(Calendar.HOUR, 12);
				cal2.set(Calendar.MINUTE, 0);
				cal2.set(Calendar.SECOND, 0);
				Date endTime = cal2.getTime();
//java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//System.out.println(sdf.format(startTime)+"-----"+sdf.format(endTime));
//				
//System.out.println();
				List<Object> carRecords = null;
				CarRecordService crs = new CarRecordService(); // 车辆出入记录查询服务
				try {
					if ("全部".equals(combo.getText())) { // 查询全部
						carRecords = crs.getAllCarRecords(startTime, endTime);
					} else if ("卡号".equals(combo.getText())) {
						long cardID = CardUtil.getCardNum();
						if (cardID == -1) {
							text.setText("请将卡放入感应区!");
							MessageDialogUtil.showWarningMessage(parent.getShell(), "请将卡放入感应区后点击再查询!");
							return;
						}
						text.setText(cardID + "");
						carRecords = crs.getCarRecordsByCardID(startTime, endTime, cardID + "");
					} else if ("车牌号码".equals(combo.getText())) {
						String carnumber = text.getText().trim();
						if ("".equals(carnumber)) {
							MessageDialogUtil.showWarningMessage(parent.getShell(), "请输入要查询的车牌号码!");
							return;
						}
						carRecords = crs.getCarRecordsByCarnumber(startTime, endTime, carnumber);
					} else if ("车主姓名".equals(combo.getText())) {
						String ownername = text.getText().trim();
						if ("".equals(ownername)) {
							MessageDialogUtil.showWarningMessage(parent.getShell(), "请输入要查询的车主姓名!");
							return;
						}
						carRecords = ers.getCarRecordsByOwnername(startTime, endTime, ownername);
					}
					if (carRecords == null)
						throw new Exception();
				} catch (Exception e1) {
					e1.printStackTrace();
					MessageDialogUtil.showWarningMessage(parent.getShell(), "车辆出入记录获取失败!");
					return;
				}
				if (carRecords.size() == 0) {
					MessageDialogUtil.showInfoMessage(parent.getShell(), "暂无车辆出入记录!");
					return;
				}
				// 分页面板设置
				composite_1.setData(carRecords, sizePage);
			}
		});
		button.setText("\u67E5  \u8BE2");

		button_1 = new Button(composite, SWT.NONE);
		button_1.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/excel.ico"));
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableViewer.getInput() == null) {
					MessageDialogUtil.showWarningMessage(parent.getShell(), "暂无可导出数据!");
					return;
				}
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, dateTime.getYear());
				cal.set(Calendar.MONTH, dateTime.getMonth());
				cal.set(Calendar.DATE, dateTime.getDay());
				cal.set(Calendar.HOUR, -12);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				Date startTime = cal.getTime();

				Calendar cal2 = Calendar.getInstance();
				cal2.set(Calendar.YEAR, dateTime_1.getYear());
				cal2.set(Calendar.MONTH, dateTime_1.getMonth());
				cal2.set(Calendar.DATE, dateTime_1.getDay());
				cal2.set(Calendar.HOUR, 12);
				cal2.set(Calendar.MINUTE, 0);
				cal2.set(Calendar.SECOND, 0);
				Date endTime = cal2.getTime();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
				String title = "车辆出入记录";
				String filename = sdf.format(startTime) + "至" + sdf.format(endTime) + "车辆出入记录";
				ExcelExportAction eea = new ExcelExportAction(parent.getShell(), filename, filename, composite_1);
				try {
					eea.run();
				} catch (Exception e1) {
					e1.printStackTrace();
					MessageDialogUtil.showErrorMessage(parent.getShell(), "车辆出入记录导出错误!");
					return;
				}
			}
		});
		button_1.setLayoutData(new RowData(SWT.DEFAULT, 20));
		button_1.setText("\u5BFC  \u51FA");

		Group group = new Group(parent, SWT.NONE);
		group.setText("\u8F66\u8F86\u51FA\u5165\u4FE1\u606F");
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(GridData.FILL_BOTH));

		tableViewer = new TableViewer(group, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table = tableViewer.getTable();
		// table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		tableViewer.setLabelProvider(new CarRecordLabelProvider());
		tableViewer.setContentProvider(new CarRecordContentProvider());

		composite_1 = new PageComposite(group, SWT.NONE, tableViewer);

		tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(93);
		tableColumn.setText("\u5361\u53F7");

		tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(96);
		tableColumn_1.setText("\u8F66\u724C\u53F7\u7801");

		tableColumn_5 = new TableColumn(table, SWT.NONE);
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("\u5361\u7C7B\u578B");

		tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(138);
		tableColumn_2.setText("\u5165\u573A\u65F6\u95F4");

		tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(142);
		tableColumn_3.setText("\u51FA\u573A\u65F6\u95F4");

		tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("\u505C\u8F66\u65F6\u95F4");
		composite_1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		TableListener.tableCellHeight(table);
		TableListener.tableColor(table);

		tableColumn_6 = new TableColumn(table, SWT.NONE);
		tableColumn_6.setWidth(100);
		tableColumn_6.setText("\u5E94\u6536\u8D39\u7528(\u5143)");

		tableColumn_7 = new TableColumn(table, SWT.NONE);
		tableColumn_7.setWidth(100);
		tableColumn_7.setText("\u5B9E\u6536\u8D39\u7528(\u5143)");

		tableColumn_8 = new TableColumn(table, SWT.NONE);
		tableColumn_8.setWidth(144);
		tableColumn_8.setText("\u5907\u6CE8\u4FE1\u606F");
	}

	public void setFocus() {

	}
}
