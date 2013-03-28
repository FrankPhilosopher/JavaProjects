package com.rcp.wxh.editors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
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
import com.rcp.wxh.actions.ExcelExportAction;
import com.rcp.wxh.composite.PageComposite;
import com.rcp.wxh.dialogs.PayDialog;
import com.rcp.wxh.dialogs.TCardDialog;
import com.rcp.wxh.dialogs.TCardEditDialog;
import com.rcp.wxh.dialogs.TCardTypeDialog;
import com.rcp.wxh.dialogs.TCardTypeEditDialog;
import com.rcp.wxh.dialogs.TChargeTypeDialog;
import com.rcp.wxh.dialogs.TChargeTypeEditDialog;
import com.rcp.wxh.fortables.CardContentProvider;
import com.rcp.wxh.fortables.CardLabelProvider;
import com.rcp.wxh.fortables.CardSorter;
import com.rcp.wxh.fortables.CardTypeContentProvider;
import com.rcp.wxh.fortables.CardTypeLabelProvider;
import com.rcp.wxh.fortables.ChargeTypeContentProvider;
import com.rcp.wxh.fortables.ChargeTypeLabelProvider;
import com.rcp.wxh.fortables.TableListener;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TCardType;
import com.rcp.wxh.pojo.TChargeType;
import com.rcp.wxh.pojo.TMember;
import com.rcp.wxh.resource.IimageKeys;
import com.rcp.wxh.service.impl.CardService;
import com.rcp.wxh.service.impl.CardTypeService;
import com.rcp.wxh.service.impl.ChargeTypeService;
import com.rcp.wxh.utils.CardUtil;
import com.rcp.wxh.utils.MessageDialogUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * 
 * 卡片信息管理编辑器
 * 
 * @author wuxuehong 2011-11-2
 * 
 */
public class CardEditor extends EditorPart {
	public CardEditor() {
	}

	public static final String ID = CardEditor.class.getName();
	private Table table;
	private Table table_1;
	private Table table_2;
	private TableViewer tableViewer;
	private TableViewer tableViewer_1;
	private TableViewer tableViewer_2;
	private boolean a = true;

	private PageComposite pageComposite; // 分页板块
	private int sizePage = 25;

	// ///////////////////卡片右键菜单//////////////////
	private Action editAction; // 编辑卡片
	private Action deleteAction; // 删除卡片
	private Action payAction; // 缴费
	private Action lockAction; // 解卡 锁卡
	private Text text;

	private Action editChargeAction; // 编辑收费方式信息
	private Action delChargeAction; // 删除收费方式信息

	private Action editCardTypeAction; // 编辑卡片类型信息
	private Action delCardTypeAction; // 删除卡片类型信息

	private List<Object> cardTypes; // 所有卡片类型
	private TCardType curCardType; // 当前卡片类型

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

	/**
	 * 初始化编辑器界面
	 */
	public void createPartControl(final Composite parent) {
		parent.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
		// 获取卡片类型
		CardTypeService cts = new CardTypeService();
		try {
			cardTypes = cts.getAllCardTypes();
		} catch (Exception e) {
			MessageDialogUtil.showWarningMessage(new Shell(), "获取卡片类型失败");
			cardTypes = new ArrayList<Object>();
		}

		FillLayout fillLayout = (FillLayout) parent.getLayout();
		fillLayout.type = SWT.VERTICAL;
		parent.setLayout(new FillLayout());
		SashForm sashForm = new SashForm(parent, SWT.VERTICAL);

		Group group_1 = new Group(sashForm, SWT.NONE);
		group_1.setText("\u5361\u7247\u4FE1\u606F");
		group_1.setLayout(new GridLayout(1, true));

		Composite composite_2 = new Composite(group_1, SWT.NONE);
		composite_2.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd_composite_2 = new GridData(GridData.FILL_HORIZONTAL);
		gd_composite_2.verticalAlignment = SWT.TOP;
		composite_2.setLayoutData(gd_composite_2);

		final Combo combo_1 = new Combo(composite_2, SWT.NONE);
		combo_1.setItems(new String[] { "\u6240\u6709\u5361\u7247\u7C7B\u578B" });
		combo_1.setText("\u6240\u6709\u5361\u7247\u7C7B\u578B");
		if (cardTypes != null) {
			for (int i = 0; i < cardTypes.size(); i++) {
				TCardType tct = (TCardType) cardTypes.get(i);
				combo_1.add(tct.getName());
				combo_1.setData((i + 1) + "", tct);
			}
		}

		final Combo combo = new Combo(composite_2, SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
		combo.setItems(new String[] { "\u5168\u90E8", "\u5361\u53F7", "\u8F66\u724C\u53F7", "\u8F66\u4E3B\u59D3\u540D" });
		combo.setText("\u5168\u90E8");

		Label label = new Label(composite_2, SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("宋体", 12, SWT.NORMAL));
		label.setLayoutData(new RowData(13, 17));
		label.setText("=");

		text = new Text(composite_2, SWT.BORDER);
		text.setLayoutData(new RowData(113, -1));
		text.setEnabled(false);

		Label label_1 = new Label(composite_2, SWT.NONE);
		label_1.setLayoutData(new RowData(7, 12));

		Button button = new Button(composite_2, SWT.NONE);
		button.setLayoutData(new RowData(79, 20));
		button.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/find.ico"));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {// 查询所有卡片信息
				// 清空列表中的信息
				tableViewer.setInput(null);
				// 获取查询卡片类型
				int index = combo_1.getSelectionIndex();
				curCardType = (TCardType) combo_1.getData(index + "");

				if ("全部".equals(combo.getText())) { // 如果是查询所有卡信息
					CardService cs = new CardService();
					List<Object> cards = null;
					try {
						if (curCardType == null) { // 查询所有卡片类型
							cards = cs.getAllCards();
						} else {
							cards = cs.getCardByCardTypeId(curCardType.getCardtypeid().intValue()); // 根据卡片类型ID查询
						}
					} catch (Exception e1) {
						MessageDialogUtil.showWarningMessage(parent.getShell(), "获取卡片信息失败!");
						return;
					}
					pageComposite.setData(cards, sizePage);
				} else if ("卡号".equals(combo.getText())) { // 如果是查询卡号
					long cardID = CardUtil.getCardNum();
					if (cardID == -1) {
						text.setText("请将卡放入感应区");
						MessageDialogUtil.showWarningMessage(parent.getShell(), "请将卡放入感应区后点击再查询!");
						return;
					}
					text.setText(cardID + "");
					CardService cs = new CardService();
					List<Object> cards = new ArrayList<Object>();
					try {
						if (curCardType == null) { // 所有卡片类型
							TCard card = null;
							card = cs.getCardById(cardID + "");
							if (card != null)
								cards.add(card);
						} else {
							cards = cs.getCardByCardidAndType(cardID + "", curCardType.getCardtypeid().intValue());
						}
					} catch (Exception e1) {
						MessageDialogUtil.showWarningMessage(parent.getShell(), "卡片信息查询失败!");
						return;
					}
					if (cards.size() == 0) {
						MessageDialogUtil.showWarningMessage(parent.getShell(), "该卡不存在!");
						text.setText("请将卡放入感应区");
						return;
					} else {
						pageComposite.setData(cards, sizePage);
					}
				} else if ("车牌号".equals(combo.getText())) { // 如果是查询车牌号
					String carnumber = text.getText().trim();
					if ("".equals(carnumber)) {
						MessageDialogUtil.showWarningMessage(parent.getShell(), "请输入车牌号!");
						return;
					}
					CardService cs = new CardService();
					List<Object> cards = new ArrayList<Object>();
					try {
						if (curCardType == null) {
							cards = cs.getCardByCarnumber(carnumber);
						} else {
							cards = cs.getCardByCarnumberAndType(carnumber, curCardType.getCardtypeid());
						}
					} catch (Exception e1) {
						MessageDialogUtil.showWarningMessage(parent.getShell(), "卡片信息查询失败!");
						return;
					}
					if (cards.size() == 0) {
						MessageDialogUtil.showWarningMessage(parent.getShell(), "该卡不存在!");
						return;
					} else {
						pageComposite.setData(cards, sizePage);
					}
				} else if ("车主姓名".equals(combo.getText())) { // 如果是按车主姓名查询
					String ownerName = text.getText().trim();
					if ("".equals(ownerName)) {
						MessageDialogUtil.showWarningMessage(parent.getShell(), "请输入车主姓名!");
						return;
					}
					CardService cs = new CardService();
					List<Object> cards = new ArrayList<Object>();
					try {
						if (curCardType == null) {
							cards = cs.getCardByOwnerName("%" + ownerName + "%");
						} else {
							cards = cs.getCardByOwnerNameAndType("%" + ownerName + "%", curCardType.getCardtypeid());
						}
					} catch (Exception e1) {
						MessageDialogUtil.showWarningMessage(parent.getShell(), "卡片信息查询失败!");
						return;
					}
					if (cards.size() == 0) {
						MessageDialogUtil.showWarningMessage(parent.getShell(), "该卡不存在!");
						return;
					} else {
						pageComposite.setData(cards, sizePage);
					}
				}
			}
		});
		button.setText("\u67E5  \u8BE2");

		Button button_9 = new Button(composite_2, SWT.NONE);
		button_9.setLayoutData(new RowData(84, 20));
		button_9.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/add.ico"));
		button_9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TCardDialog tcd = new TCardDialog(parent.getShell(), SWT.NONE); // 新注册卡片
				Object result = tcd.open();
				if (result != null) {
					TCard card = (TCard) result;
					if (curCardType == null
							|| (curCardType != null && curCardType.getCardtypeid().intValue() == card.getTCardType()
									.getCardtypeid().intValue())) {
						pageComposite.updateAfterAdding(result);
					}
				}
			}
		});
		button_9.setText("\u65B0\u5361\u6CE8\u518C");

		Button button_2 = new Button(composite_2, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableViewer.getInput() == null) {
					MessageDialogUtil.showWarningMessage(parent.getShell(), "暂无可导出数据!");
					return;
				}
				String title = combo.getText();
				String filename = combo_1.getText() + "-" + combo.getText() + "-卡片信息";
				ExcelExportAction eea = new ExcelExportAction(parent.getShell(), filename, filename, pageComposite);
				try {
					eea.run();
				} catch (Exception e1) {
					e1.printStackTrace();
					MessageDialogUtil.showErrorMessage(parent.getShell(), "卡片信息导出错误!");
					return;
				}
			}
		});
		button_2.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/excel.ico"));
		button_2.setLayoutData(new RowData(SWT.DEFAULT, 20));
		button_2.setText("\u5BFC  \u51FA");

		tableViewer = new TableViewer(group_1, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table = tableViewer.getTable();
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));// 设置table颜色，不会被背景色覆盖
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setLabelProvider(new CardLabelProvider());
		tableViewer.setContentProvider(new CardContentProvider());
		tableViewer.setSorter(new CardSorter());
		// table.setBackground(Color.WHITE)
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(96);
		tableColumn.setText("卡号");
		tableColumn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				a = !a;
				((CardSorter) tableViewer.getSorter()).doSort(a ? -1 : 1);
				tableViewer.refresh();
			}
		});

		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(81);
		tableColumn_2.setText("卡类型");
		tableColumn_2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				a = !a;
				((CardSorter) tableViewer.getSorter()).doSort(a ? -2 : 2);
				tableViewer.refresh();
			}
		});

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(78);
		tableColumn_1.setText("\u8F66\u724C\u53F7");
		tableColumn_1.addSelectionListener(new SelectionAdapter() { // 车牌号
					public void widgetSelected(SelectionEvent e) {
						a = !a;
						((CardSorter) tableViewer.getSorter()).doSort(a ? -3 : 3);
						tableViewer.refresh();
					}
				});

		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(85);
		tableColumn_3.setText("\u6301\u5361\u4EBA");
		tableColumn_3.addSelectionListener(new SelectionAdapter() { // 车主姓名
					public void widgetSelected(SelectionEvent e) {
						a = !a;
						((CardSorter) tableViewer.getSorter()).doSort(a ? -4 : 4);
						tableViewer.refresh();
					}
				});

		TableColumn tableColumn_10 = new TableColumn(table, SWT.NONE);
		tableColumn_10.setWidth(85);
		tableColumn_10.setText("\u8F66\u578B");
		tableColumn_10.addSelectionListener(new SelectionAdapter() { // 车型
					public void widgetSelected(SelectionEvent e) {
						a = !a;
						((CardSorter) tableViewer.getSorter()).doSort(a ? -5 : 5);
						tableViewer.refresh();
					}
				});

		TableColumn tableColumn_11 = new TableColumn(table, SWT.NONE);
		tableColumn_11.setWidth(102);
		tableColumn_11.setText("\u8054\u7CFB\u65B9\u5F0F");

		TableColumn tableColumn_12 = new TableColumn(table, SWT.NONE);
		tableColumn_12.setWidth(176);
		tableColumn_12.setText("\u5F00\u5361\u65E5\u671F");
		tableColumn_12.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				a = !a;
				((CardSorter) tableViewer.getSorter()).doSort(a ? -6 : 6);
				tableViewer.refresh();
			}
		});

		TableColumn tableColumn_13 = new TableColumn(table, SWT.NONE);
		tableColumn_13.setWidth(109);
		tableColumn_13.setText("\u5361\u7247\u72B6\u6001");
		tableColumn_13.addSelectionListener(new SelectionAdapter() { // 卡片状态
					public void widgetSelected(SelectionEvent e) {
						a = !a;
						((CardSorter) tableViewer.getSorter()).doSort(a ? -7 : 7);
						tableViewer.refresh();
					}
				});

		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		CTabFolder tabFolder = new CTabFolder(composite, SWT.BORDER | SWT.FLAT);
		tabFolder.setSimple(false);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tabItem_2 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_2.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		tabItem_2.setImage(ResourceManager.getPluginImage("ParkingSystem", IimageKeys.CHARGE_MANAGE_TRAY));
		tabItem_2.setText("\u6536\u8D39\u65B9\u5F0F\u7BA1\u7406");
		tabFolder.setSelection(tabItem_2);

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tabItem_2.setControl(composite_1);
		composite_1.setLayout(new GridLayout(1, true));

		final Composite composite_3 = new Composite(composite_1, SWT.NONE);
		composite_3.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd_composite_3 = new GridData(GridData.FILL_HORIZONTAL);
		gd_composite_3.verticalAlignment = SWT.TOP;
		composite_3.setLayoutData(gd_composite_3);

		Button button_1 = new Button(composite_3, SWT.NONE);
		button_1.setLayoutData(new RowData(SWT.DEFAULT, 24));
		button_1.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/add.ico"));
		button_1.addSelectionListener(new SelectionAdapter() { // 新增收费方式
			@Override
			public void widgetSelected(SelectionEvent e) {
				TChargeTypeDialog ttd = new TChargeTypeDialog(parent.getShell(), SWT.NONE);
				Object result = ttd.open(); //
				if (result != null) { // 收费方式新增成功
					List<Object> tcts = (List<Object>) tableViewer_1.getInput();
					tcts.add(result);
					tableViewer_1.refresh();
				}
			}
		});
		button_1.setText("\u65B0\u589E\u6536\u8D39\u65B9\u5F0F");

		Button button_4 = new Button(composite_3, SWT.NONE);
		button_4.setLayoutData(new RowData(95, 24));
		button_4.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/find.ico"));
		button_4.setText("\u67E5\u8BE2\u6240\u6709");

		Button button_3 = new Button(composite_3, SWT.NONE);
		button_3.setLayoutData(new RowData(SWT.DEFAULT, 24));
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableViewer_1.getInput() == null) {
					MessageDialogUtil.showWarningMessage(parent.getShell(), "暂无可导出数据!");
					return;
				}
				String title = "收费方式记录";
				String filename = "收费方式记录";
				ExcelExportAction eea = new ExcelExportAction(parent.getShell(), title, filename, tableViewer_1);
				try {
					eea.run2();
				} catch (Exception e1) {
					e1.printStackTrace();
					MessageDialogUtil.showErrorMessage(parent.getShell(), "收费方式记录导出错误!");
					return;
				}
			}
		});
		button_3.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/excel.ico"));
		button_3.setText("\u5BFC  \u51FA");

		tableViewer_1 = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table_1 = tableViewer_1.getTable();
		// table_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		table_1.setLayoutData(new GridData(GridData.FILL_BOTH));
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		
		tableViewer_1.setContentProvider(new ChargeTypeContentProvider());
		tableViewer_1.setLabelProvider(new ChargeTypeLabelProvider());

		button_4.addSelectionListener(new SelectionAdapter() { // 查询所有收费方式
			@Override
			public void widgetSelected(SelectionEvent e) {
				ChargeTypeService cts = new ChargeTypeService();
				List<Object> input;
				try {
					input = cts.getAllChargeTypes();
				} catch (Exception e1) {
					MessageDialogUtil.showErrorMessage(parent.getShell(), "获取所有收费方式失败!");
					return;
				}
				tableViewer_1.setInput(input);
				tableViewer_1.refresh();
			}
		});

		TableColumn tableColumn_4 = new TableColumn(table_1, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("\u6536\u8D39\u540D\u79F0");

		TableColumn tableColumn_5 = new TableColumn(table_1, SWT.NONE);
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("\u6536\u8D39\u65B9\u5F0F");

		TableColumn tableColumn_6 = new TableColumn(table_1, SWT.NONE);
		tableColumn_6.setWidth(373);
		tableColumn_6.setText("\u8BE6\u7EC6\u4FE1\u606F");

		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		tabItem.setImage(ResourceManager.getPluginImage("ParkingSystem", IimageKeys.CARDTYPE_MANAGE_TRAY));
		tabItem.setText("\u5361\u7247\u7C7B\u578B\u7BA1\u7406");

		Composite composite_4 = new Composite(tabFolder, SWT.NONE);
		tabItem.setControl(composite_4);
		composite_4.setLayout(new GridLayout(1, true));

		Composite composite_5 = new Composite(composite_4, SWT.NONE);
		composite_5.setLayout(new RowLayout(SWT.HORIZONTAL));
		composite_5.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button button_5 = new Button(composite_5, SWT.NONE);
		button_5.setLayoutData(new RowData(108, 24));
		button_5.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/add.ico"));
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 新增卡片类型
				TCardTypeDialog tctd = new TCardTypeDialog(parent.getShell(), SWT.NONE);
				Object result = tctd.open();
				if (result != null) {
					List<Object> cardTypes = (List<Object>) tableViewer_2.getInput();
					cardTypes.add(result);
					tableViewer_2.refresh();
				}
			}
		});
		button_5.setText("\u65B0\u589E\u5361\u7247\u7C7B\u578B");

		Button button_8 = new Button(composite_5, SWT.NONE);
		button_8.setLayoutData(new RowData(95, 24));
		button_8.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/find.ico"));
		button_8.setText("\u67E5\u8BE2\u6240\u6709");

		Button button_6 = new Button(composite_5, SWT.NONE);
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { // 导出卡片类型 Excel
				if (tableViewer_2.getInput() == null) {
					MessageDialogUtil.showWarningMessage(parent.getShell(), "暂无可导出数据!");
					return;
				}
				String title = "卡片类型记录";
				String filename = "卡片类型记录";
				ExcelExportAction eea = new ExcelExportAction(parent.getShell(), title, filename, tableViewer_2);
				try {
					eea.run2();
				} catch (Exception e1) {
					e1.printStackTrace();
					MessageDialogUtil.showErrorMessage(parent.getShell(), "卡片类型记录导出错误!");
					return;
				}
			}
		});
		button_6.setLayoutData(new RowData(68, 24));
		button_6.setText("\u5BFC  \u51FA");
		button_6.setImage(ResourceManager.getPluginImage("ParkingSystem", "icons/excel.ico"));

		tableViewer_2 = new TableViewer(composite_4, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table_2 = tableViewer_2.getTable();
		// table_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		table_2.setLayoutData(new GridData(GridData.FILL_BOTH));
		table_2.setHeaderVisible(true);
		table_2.setLinesVisible(true);
		tableViewer_2.setLabelProvider(new CardTypeLabelProvider());
		tableViewer_2.setContentProvider(new CardTypeContentProvider());

		button_8.addSelectionListener(new SelectionAdapter() { // 查询所有卡片类型
			@Override
			public void widgetSelected(SelectionEvent e) {
				CardTypeService cts = new CardTypeService();
				List<Object> input;
				try {
					input = cts.getAllCardTypes();
				} catch (Exception e1) {
					MessageDialogUtil.showErrorMessage(parent.getShell(), "获取所有卡片类型失败!");
					return;
				}
				tableViewer_2.setInput(input);
				tableViewer_2.refresh();
			}
		});

		TableColumn tableColumn_7 = new TableColumn(table_2, SWT.NONE);
		tableColumn_7.setWidth(100);
		tableColumn_7.setText("\u5361\u7247\u7C7B\u578B\u540D\u79F0");

		TableColumn tableColumn_8 = new TableColumn(table_2, SWT.NONE);
		tableColumn_8.setWidth(100);
		tableColumn_8.setText("\u6536\u8D39\u65B9\u5F0F");

		TableColumn tableColumn_9 = new TableColumn(table_2, SWT.NONE);
		tableColumn_9.setWidth(377);
		tableColumn_9.setText("\u5907\u6CE8\u4FE1\u606F");

		composite_1.layout();
		TableListener.tableCellHeight(table);
		TableListener.tableColor(table);

		pageComposite = new PageComposite(group_1, SWT.NONE, tableViewer); // 创建分页模块
		pageComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		TableListener.tableCellHeight(table_1);
		TableListener.tableColor(table_1);
		TableListener.tableCellHeight(table_2);
		TableListener.tableColor(table_2);
		sashForm.setWeights(new int[] { 372, 207 });
		// 卡片信息
		makeActions(parent);
		hookContextMenu();
		hookDoubleClickAction(parent);

		// 收费方式
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
		tableViewer_1.addDoubleClickListener(new IDoubleClickListener() { // 鼠标双击编辑收费方式
					public void doubleClick(DoubleClickEvent event) {
						editChargeAction(parent);
					}
				});
		tableViewer_2.addDoubleClickListener(new IDoubleClickListener() { // 鼠标编辑双击卡片类型
					public void doubleClick(DoubleClickEvent event) {
						editCardTypeAction(parent);
					}
				});
	}

	/**
	 * 右键菜单
	 */
	private void hookContextMenu() {
		
		// //////////////////////////卡片信息//////////////////////////////////////
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				int count = tableViewer.getTable().getSelectionCount();
				editAction.setEnabled(false);
				deleteAction.setEnabled(false);
				payAction.setEnabled(false);
				lockAction.setEnabled(false);
				if (count > 1) { // 如果选择了多行
					deleteAction.setEnabled(true);
				} else if (count == 1) { // 如果选择了单行
					deleteAction.setEnabled(true);
					TCard card = (TCard) tableViewer.getTable().getSelection()[0].getData();
					System.out.println(card.getCardid());
					if (card.getCardtype() == TCard.LONG_CARD) { // 如果是长期卡 可以缴费 可以编辑
						editAction.setEnabled(true);
						payAction.setEnabled(true);
					}
					if (card.getCardstatus() == TCard.AVILIABLE) {
						lockAction.setText("锁卡");
					} else {
						lockAction.setText("激活");
					}
					lockAction.setEnabled(true);
				}
				manager.add(editAction);
				manager.add(deleteAction);
				manager.add(new Separator());
				manager.add(payAction);
				manager.add(new Separator());
				manager.add(lockAction);
			}
		});
		Menu menu = menuMgr.createContextMenu(tableViewer.getControl());
		tableViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, tableViewer);

		// ///////////////////////收费方式/////////////////////////////////////////
		MenuManager menuMgr1 = new MenuManager("#PopupMenu");
		menuMgr1.setRemoveAllWhenShown(true);
		menuMgr1.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				int count = tableViewer_1.getTable().getSelectionCount();
				editChargeAction.setEnabled(false);
				delChargeAction.setEnabled(false);
				if (count == 1) {
					editChargeAction.setEnabled(true);
					delChargeAction.setEnabled(true);
				}
				manager.add(editChargeAction); // 编辑
				manager.add(delChargeAction); // 删除
			}
		});
		Menu menu1 = menuMgr1.createContextMenu(tableViewer_1.getControl());
		tableViewer_1.getControl().setMenu(menu1);
		getSite().registerContextMenu(menuMgr1, tableViewer_1);
		
		// ///////////////////////卡片类型/////////////////////////////////////////
		MenuManager menuMgr2 = new MenuManager("#PopupMenu");
		menuMgr2.setRemoveAllWhenShown(true);
		menuMgr2.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				int count = tableViewer_2.getTable().getSelectionCount();
				editCardTypeAction.setEnabled(false);
				delCardTypeAction.setEnabled(false);
				if (count == 1) {
					editCardTypeAction.setEnabled(true);
					delCardTypeAction.setEnabled(true);
				}
				manager.add(editCardTypeAction); // 编辑
				manager.add(delCardTypeAction); // 删除
			}
		});
		Menu menu2 = menuMgr2.createContextMenu(tableViewer_2.getControl());
		tableViewer_2.getControl().setMenu(menu2);
		getSite().registerContextMenu(menuMgr2, tableViewer_2);
	}

	/**
	 * 编辑卡片事件响应
	 * 
	 * @param parent
	 */
	public void editAction(Composite parent) {
		TableItem[] ti = tableViewer.getTable().getSelection(); // 获取选中的行 对象
		TCard card = (TCard) ti[0].getData();
		Iterator<TMember> it = card.getTMembers().iterator();
		TMember member = null;
		if (it.hasNext()) {
			member = it.next();
		}
		if (member == null) {
			MessageDialogUtil.showWarningMessage(parent.getShell(), "该卡片未与用户绑定!");
			return;
		}
		TCardEditDialog tced = new TCardEditDialog(parent.getShell(), SWT.NONE, card);
		Object result = tced.open();
		if (result != null) { // 更新成功
			tableViewer.refresh();
		}
	}

	/**
	 * 删除卡片事件响应
	 * 
	 * @param parent
	 */
	public void deleteAction(Composite parent) {
		int a = MessageDialogUtil.showConfirmMessage(parent.getShell(), "确认删除卡片信息么?");
		if (a == SWT.CANCEL) {
			return;
		}
		TableItem[] ti = tableViewer.getTable().getSelection(); // 获取选中的行 对象
		List<Object> removes = new ArrayList<Object>(); // 删除的对象
		CardService cs = new CardService();
		for (int i = 0; i < ti.length; i++) {
			try {
				cs.delCard(ti[i].getData());
				removes.add(ti[i].getData());
			} catch (Exception e) {
				MessageDialogUtil.showWarningMessage(parent.getShell(), "卡片信息删除是失败!");
				return;
			}
		}
		pageComposite.updateAfterDeleting(removes);
	}

	/**
	 * 锁卡/解锁 事件响应
	 * 
	 * @param parent
	 */
	public void lockAction(Composite parent) {
		TableItem[] ti = tableViewer.getTable().getSelection(); // 获取选中的行 对象
		TCard card = (TCard) ti[0].getData();
		if (card.getCardstatus() == TCard.AVILIABLE) {
			card.setCardstatus(TCard.LOCKED);
		} else {
			card.setCardstatus(TCard.AVILIABLE);
		}
		CardService cs = new CardService();
		try {
			cs.updateCard(card);
		} catch (Exception e) {
			if (card.getCardstatus() == TCard.AVILIABLE)
				MessageDialogUtil.showWarningMessage(parent.getShell(), "锁卡失败!");
			else
				MessageDialogUtil.showInfoMessage(parent.getShell(), "卡片激活失败!");
			return;
		}
		if (card.getCardstatus() == TCard.AVILIABLE)
			MessageDialogUtil.showInfoMessage(parent.getShell(), "卡片激活成功!");
		else
			MessageDialogUtil.showInfoMessage(parent.getShell(), "锁卡成功!");
		tableViewer.refresh();
	}

	/**
	 * 缴费事件响应
	 * 
	 * @param parent
	 */
	public void payAction(Composite parent) {
		TableItem[] ti = tableViewer.getTable().getSelection(); // 获取选中的行 对象
		TCard card = (TCard) ti[0].getData();
		Iterator<TMember> it = card.getTMembers().iterator();
		TMember member = null;
		if (it.hasNext()) {
			member = it.next();
		}
		if (member == null) {
			MessageDialogUtil.showWarningMessage(parent.getShell(), "该卡片未与用户绑定!");
			return;
		}
		PayDialog pd = new PayDialog(parent.getShell(), SWT.NONE, card);
		pd.open();
	}

	/**
	 * 编辑收费方式信息
	 */
	public void editChargeAction(Composite parent) {
		TableItem[] ti = tableViewer_1.getTable().getSelection(); // 获取选中的行 对象
		TChargeType tct = (TChargeType) ti[0].getData();
//		ChargeTypeService cts = new ChargeTypeService();
//		MessageDialog.openWarning(parent.getShell(), "提示", "编辑收费方式："+tct.getName());
		if (tct != null) {
			TChargeTypeEditDialog tcted = new TChargeTypeEditDialog(parent.getShell(), SWT.NONE, tct);
			Object result = tcted.open();
			if (result != null) { // 如果更新成功
				tableViewer_1.refresh();
			}
		}
	}

	/**
	 * 删除收费方式信息
	 * 
	 * @param parent
	 */
	public void delChargeAction(Composite parent) {
		int a = MessageDialogUtil.showConfirmMessage(parent.getShell(), "确认删除该删除收费方式么?");
		if (a == SWT.CANCEL)
			return;
		TableItem[] ti = tableViewer_1.getTable().getSelection(); // 获取选中的行 对象
		TChargeType tct = (TChargeType) ti[0].getData();
		ChargeTypeService cts = new ChargeTypeService();
		try {
			cts.delChargeType(tct);
		} catch (Exception e) {
			MessageDialogUtil.showWarningMessage(parent.getShell(), "删除收费方式失败！");
			return;
		}
		List<Object> tcts = (List<Object>) tableViewer_1.getInput();
		tcts.remove(tct);
//		tableViewer_1.setInput(tcts);
		tableViewer_1.refresh();
	}

	/**
	 * 编辑卡型信息
	 * 
	 * @param parent
	 */
	public void editCardTypeAction(Composite parent) {
		TableItem[] ti = tableViewer_2.getTable().getSelection(); // 获取选中的行 对象
		TCardType tct = (TCardType) ti[0].getData();
		if (tct != null) {
			TCardTypeEditDialog tced = new TCardTypeEditDialog(parent.getShell(), SWT.NONE, tct);
			Object result = tced.open();
			if (result != null) { // 如果更新成功
				tableViewer_2.refresh(); // 更新表格
			}
		}
	}

	/**
	 * 删除卡型信息
	 * 
	 * @param parent
	 */
	public void delCardTypeAction(Composite parent) {
		int a = MessageDialogUtil.showConfirmMessage(parent.getShell(), "认删除该卡片类型么?");
		if (a == SWT.CANCEL)
			return;
		TableItem[] ti = tableViewer_2.getTable().getSelection(); // 获取选中的行 对象
		TCardType tct = (TCardType) ti[0].getData();
		CardTypeService cts = new CardTypeService();
		try {
			cts.delCardType(tct);
		} catch (Exception e) {
			MessageDialogUtil.showWarningMessage(parent.getShell(), "删除卡片类型失败！\n" + e.getMessage());
			return;
		}
		List<Object> tcts = (List<Object>) tableViewer_2.getInput();
		tcts.remove(tct);
		tableViewer_2.setInput(tcts);
		tableViewer_2.refresh();
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
		editAction.setToolTipText("编辑卡片信息");
		editAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.EDIT_POP_MANAGE_TRAY));

		deleteAction = new Action() {
			public void run() {
				deleteAction(parent);
			}
		};
		deleteAction.setText("删除");
		deleteAction.setToolTipText("删除卡片信息");
		deleteAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.DELETE_POP_MANAGE_TRAY));

		payAction = new Action() {
			public void run() {
				payAction(parent);
			}
		};
		payAction.setText("缴费");
		payAction.setToolTipText("会员缴费");
		payAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.PAY_POP_MANAGE_TRAY));

		lockAction = new Action() {
			public void run() {
				lockAction(parent);
			}
		};
		lockAction.setText("锁卡");
		lockAction.setToolTipText("锁定该卡");
		lockAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.LOCK_POP_MANAGE_TRAY));

		editChargeAction = new Action() {
			public void run() {
				editChargeAction(parent);
			}
		};
		editChargeAction.setText("编辑");
		editChargeAction.setToolTipText("编辑收费方式");
		editChargeAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.EDIT_POP_MANAGE_TRAY));

		delChargeAction = new Action() {
			public void run() {
				delChargeAction(parent);
			}
		};
		delChargeAction.setText("删除");
		delChargeAction.setToolTipText("删除收费方式");
		delChargeAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.DELETE_POP_MANAGE_TRAY));

		editCardTypeAction = new Action() {
			public void run() {
				editCardTypeAction(parent);
			}
		};
		editCardTypeAction.setText("编辑");
		editCardTypeAction.setToolTipText("编辑卡片类型");
		editCardTypeAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.EDIT_POP_MANAGE_TRAY));

		delCardTypeAction = new Action() {
			public void run() {
				delCardTypeAction(parent);
			}
		};
		delCardTypeAction.setText("删除");
		delCardTypeAction.setToolTipText("删除卡片类型");
		delCardTypeAction.setImageDescriptor(Activator.getImageDescriptor(IimageKeys.DELETE_POP_MANAGE_TRAY));
	}

	public void setFocus() {
	}
}
