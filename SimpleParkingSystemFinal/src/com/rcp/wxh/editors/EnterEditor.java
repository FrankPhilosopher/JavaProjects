package com.rcp.wxh.editors;

import javax.media.Player;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.rcp.wbw.skin.LookAndFeel;
import com.rcp.wxh.composite.AbstractCarMonitorComposite;
import com.rcp.wxh.composite.EnterComposite;
import com.rcp.wxh.composite.ExitComposite;
import com.rcp.wxh.composite.MultiComposite;
import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TCard;

/**
 * 车辆入场监控 编辑器
 * 
 * @author wuxuehong 2011-11-17
 * 
 */
public class EnterEditor extends EditorPart {

	public static final int ENTER_ONLY = 1; // 只进不出
	public static final int EXIT_ONLY = 2; // 只出不进
	public static final int ENTER_EXIT = 3; // 有出有进
	public static int STYLE = ENTER_EXIT; // default

	private static Player player;

	public EnterEditor() {

	}

	public static final String ID = EnterEditor.class.getName(); // unique ID

	private AbstractCarMonitorComposite carComposite;// 车辆出入场控制面板

	public void doSave(IProgressMonitor monitor) {
		System.out.println("doSave");
	}

	public void doSaveAs() {
		System.out.println("doSaveAs");
	}

	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
	}

	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setBackgroundImage(LookAndFeel.getDefault().getContentBgImage());
		parent.setBackgroundMode(SWT.INHERIT_FORCE);

		// new yinger 只有车辆出入场监控
		this.setPartName("车辆出入场监控");
		carComposite = new MultiComposite(parent, SWT.NONE);

		// switch (STYLE) {
		// case ENTER_ONLY:
		// this.setPartName("车辆入场监控");
		// this.setTitleToolTip("车辆入场监控");
		// carComposite = new EnterComposite(parent, SWT.NONE);
		// break;
		// case EXIT_ONLY:
		// this.setPartName("车辆出场监控");
		// this.setTitleToolTip("车辆出场监控");
		// carComposite = new ExitComposite(parent, SWT.NONE);
		// break;
		// case ENTER_EXIT:
		// this.setPartName("车辆出入场监控");
		// this.setTitleToolTip("车辆出入场监控");
		// carComposite = new MultiComposite(parent, SWT.NONE);
		// break;
		// }

		// SashForm sashForm = new SashForm(parent, SWT.NONE);
		// sashForm.setOrientation(SWT.VERTICAL);
		//
		// Composite composite = new Composite(sashForm, SWT.BORDER);
		// composite.setLayout(new GridLayout(8, true));
		//
		// //入场视频模块
		// final Group group = new Group(composite, SWT.NONE);
		// group.setText("\u5165\u573A\u89C6\u9891");
		// group.setLayout(new FillLayout(SWT.HORIZONTAL));
		// GridData gd = new GridData(GridData.FILL_BOTH);
		// gd.horizontalSpan = 3;
		// group.setLayoutData(gd);
		//
		// final Composite videoCom = new Composite(group, SWT.EMBEDDED);
		// videoCom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		// videoCom.setLayout(new FillLayout(SWT.HORIZONTAL));
		//
		// //库存图片模块
		// Group group_4 = new Group(composite, SWT.NONE);
		// group_4.setText("\u8F66\u8F86\u56FE\u7247\u4FE1\u606F");
		// GridData gd1 = new GridData(GridData.FILL_BOTH);
		// gd1.horizontalSpan = 2;
		// group_4.setLayoutData(gd1);
		//
		// Button button = new Button(group_4, SWT.NONE);
		// button.setBounds(0, 10, 72, 22);
		// button.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) { //开启视频
		// VideoPlayer getplayer = new VideoPlayer();
		// player = getplayer.Getplay();
		// player.start();
		// Component c = player.getVisualComponent();
		// java.awt.Frame frame = SWT_AWT.new_Frame(videoCom);
		// frame.add(c);
		// videoCom.layout();
		// group.layout();
		// }
		// });
		// button.setText("\u5F00\u542F\u89C6\u9891");
		//
		// //出场视频模块
		// Group group_1 = new Group(composite, SWT.NONE);
		// group_1.setText("\u51FA\u573A\u89C6\u9891");
		// GridData gd2 = new GridData(GridData.FILL_BOTH);
		// gd2.horizontalSpan = 3;
		// group_1.setLayoutData(gd2);
		// group_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		// Composite videoCom2 = new Composite(group_1, SWT.EMBEDDED);
		// videoCom2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		// videoCom2.setLayout(new FillLayout(SWT.HORIZONTAL));
		//
		// // Label logo = new Label(videoCom,SWT.NONE);
		// //
		// logo.setImage(parkingsystem.Activator.getImageDescriptor(IimageKeys.VIDEO_MANAGE_TRAY).createImage());
		// //编辑器重新打开时 验证视频是否已经开启
		// if(player != null){
		// Component c = player.getVisualComponent();
		// java.awt.Frame frame = SWT_AWT.new_Frame(videoCom);
		// frame.add(c);
		// videoCom.layout();
		// group.layout();
		// }
		//
		// //出入场车辆信息模块
		// Composite composite_1 = new Composite(sashForm, SWT.BORDER);
		// composite_1.setLayout(new GridLayout(2, true));
		//
		// //车辆入场信息模块
		// Group group_2 = new Group(composite_1, SWT.NONE);
		// group_2.setText("\u5165\u573A\u8F66\u8F86\u4FE1\u606F");
		// GridData gd3 = new GridData(GridData.FILL_BOTH);
		// gd3.horizontalSpan = 1;
		// group_2.setLayoutData(gd3);
		// group_2.setLayout(new GridLayout(2, true));
		//
		// table = new Table(group_2, SWT.BORDER | SWT.FULL_SELECTION);
		// GridData gd4 = new GridData(GridData.FILL_BOTH);
		// gd4.horizontalSpan = 2;
		// table.setLayoutData(gd4);
		// table.setHeaderVisible(true);
		// table.setLinesVisible(true);
		// TableListener.tableCellHeight(table);
		// TableListener.tableColor(table);
		//
		// TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		// tableColumn.setWidth(100);
		// tableColumn.setText("\u540D\u79F0");
		//
		// TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		// tableColumn_1.setWidth(292);
		// tableColumn_1.setText("\u5185\u5BB9");
		//
		// Label label = new Label(group_2, SWT.NONE);
		// GridData gd_label = new GridData(GridData.FILL_HORIZONTAL);
		// gd_label.horizontalAlignment = SWT.RIGHT;
		// label.setLayoutData(gd_label);
		// label.setText("\u5165\u573A\u8F66\u8F86\u8F66\u724C\u53F7\u7801\uFF1A");
		//
		// text = new Text(group_2, SWT.BORDER);
		// text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		// GridData gd_text = new GridData(GridData.FILL_HORIZONTAL);
		// gd_text.widthHint = 133;
		// gd_text.horizontalAlignment = SWT.LEFT;
		// text.setLayoutData(gd_text);
		//
		// label_1 = new Label(group_2, SWT.NONE);
		// label_1.setAlignment(SWT.CENTER);
		// label_1.setText("\u5165\u573A\u63D0\u793A...");
		// label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		// label_1.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		// GridData gd6 = new GridData(GridData.FILL_HORIZONTAL);
		// gd6.horizontalSpan = 2;
		// label_1.setLayoutData(gd6);
		//
		//
		// Button button_1 = new Button(group_2, SWT.NONE);
		// button_1.setText("\u4EBA\u5DE5\u62AC\u95F8");
		// GridData gd_button_1 = new GridData(GridData.FILL_HORIZONTAL);
		// gd_button_1.horizontalAlignment = SWT.RIGHT;
		// button_1.setLayoutData(gd_button_1);
		//
		// Button button_2 = new Button(group_2, SWT.NONE);
		// button_2.setText("\u4EBA\u5DE5\u843D\u95F8");
		// GridData gd_button_2 = new GridData(GridData.FILL_HORIZONTAL);
		// gd_button_2.horizontalAlignment = SWT.LEFT;
		// button_2.setLayoutData(gd_button_2);
		//
		// //车辆出场信息模块
		// Group group_3 = new Group(composite_1, SWT.NONE);
		// group_3.setText(" \u51FA\u573A\u8F66\u8F86\u4FE1\u606F");
		// GridData gd7 = new GridData(GridData.FILL_BOTH);
		// gd7.horizontalSpan = 1;
		// group_3.setLayoutData(gd7);
		// sashForm.setWeights(new int[] {295, 160});
		// group_3.setLayout(new GridLayout(2, true));
		//
		// table_1 = new Table(group_3, SWT.BORDER | SWT.FULL_SELECTION);
		// GridData gd8 = new GridData(GridData.FILL_BOTH);
		// gd8.horizontalSpan = 2;
		// table_1.setLayoutData(gd8);
		// table_1.setHeaderVisible(true);
		// table_1.setLinesVisible(true);
		// TableListener.tableCellHeight(table_1);
		// TableListener.tableColor(table_1);
		//
		// TableColumn tableColumn_2 = new TableColumn(table_1, SWT.NONE);
		// tableColumn_2.setWidth(100);
		// tableColumn_2.setText("\u540D\u79F0");
		//
		// TableColumn tableColumn_3 = new TableColumn(table_1, SWT.NONE);
		// tableColumn_3.setWidth(379);
		// tableColumn_3.setText("\u5185\u5BB9");
		//
		// Label label_2 = new Label(group_3, SWT.NONE);
		// label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
		// false, 1, 1));
		// label_2.setText("\u5B9E\u6536\u8D39\u7528\uFF1A");
		//
		// text_1 = new Text(group_3, SWT.BORDER);
		// text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		// GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false,
		// 1, 1);
		// gd_text_1.widthHint = 122;
		// text_1.setLayoutData(gd_text_1);
		//
		// Button button_3 = new Button(group_3, SWT.NONE);
		// button_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
		// false, 1, 1));
		// button_3.setText("\u8F66\u8F86\u653E\u884C");
		// new Label(group_3, SWT.NONE);

	}

	/**
	 * 处理入场车辆
	 * 
	 * @param card
	 *            卡片对象
	 * @param info
	 *            提示信息
	 * @param isOk
	 *            验证是否通过
	 * @param style
	 *            出入场类型
	 * @param carRecord车辆停车记录
	 */
	public void dealEnterCar(TCard card, StringBuffer info, boolean isOk,
			byte style, TCarRecord carRecord) {
		carComposite.dealWithUI(card, info.toString(), isOk, style, carRecord);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}
