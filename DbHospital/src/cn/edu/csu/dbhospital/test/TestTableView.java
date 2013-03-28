package cn.edu.csu.dbhospital.test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class TestTableView extends ApplicationWindow {
	private Table table;

	private String[][] contents = { { "", "张三", "男", "未婚" }, { "", "李四", "女", "已婚" }, { "", "王五", "女", "未婚" } };

	private Hashtable<TableItem, TableItemControls> tableControls = new Hashtable<TableItem, TestTableView.TableItemControls>();

	/**
	 * Create the application window.
	 */
	public TestTableView() {
		super(null);
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		{
			Group group = new Group(container, SWT.NONE);
			group.setText("表");
			group.setBounds(10, 10, 481, 238);

			table = new Table(group, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
			table.setBounds(10, 20, 461, 208);
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			{
				TableColumn tableColumn = new TableColumn(table, SWT.NONE);
				tableColumn.setWidth(20);
			}
			{
				TableColumn tableColumn = new TableColumn(table, SWT.NONE);
				tableColumn.setWidth(104);
				tableColumn.setText("名");
			}
			{
				TableColumn tableColumn = new TableColumn(table, SWT.NONE);
				tableColumn.setWidth(110);
				tableColumn.setText("性别");
			}
			{
				TableColumn tableColumn = new TableColumn(table, SWT.NONE);
				tableColumn.setWidth(100);
				tableColumn.setText("婚否");
			}

		}

		for (String[] content : contents) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(content);
		}
		createCells(table);
		{
			Button button = new Button(container, SWT.PUSH);
			button.setBounds(497, 41, 72, 22);
			button.setText("追加");
			button.addMouseListener(new MouseListener() {

				@Override
				public void mouseUp(MouseEvent arg0) {
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(contents[0]);
					// 创建一个item的控件
					createOneItemCells(item);
				}

				@Override
				public void mouseDown(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
		{
			Button button = new Button(container, SWT.PUSH);
			button.setBounds(497, 98, 72, 22);
			button.setText("删除");
			button.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent arg0) {
					// 储存删除索引的list
					List<Integer> indexs = new ArrayList<Integer>();
					for (int i = 0; i < table.getItemCount(); i++) {
						TableItem item = table.getItem(i);

						if (item.getChecked()) {
							TableItemControls controls = tableControls.get(item);
							if (controls != null) {
								controls.dispose();
								tableControls.remove(item);
							}
							indexs.add(i);
						}
					}
					int idx[] = new int[indexs.size()];
					for (int i = 0; i < indexs.size(); i++) {
						idx[i] = indexs.get(i);
						System.out.println("idx[i]" + idx[i]);
					}

					// 删除对应index的表的item
					table.remove(idx);
					table.pack();
					// 表大小还原
					table.setBounds(10, 20, 461, 208);
				}

				@Override
				public void mouseDown(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
		}

		return container;
	}

	private void createCells(Table table) {
		for (int i = 0; i < table.getItemCount(); i++) {
			createOneItemCells(table.getItem(i));
		}
	}

	private void createOneItemCells(TableItem item) {
		TableEditor nameEditor = new TableEditor(table);
		// 名字的控件
		Text name = new Text(table, SWT.NONE);
		name.setText(item.getText(NAME_INDEX));
		nameEditor.grabHorizontal = true;
		nameEditor.setEditor(name, item, NAME_INDEX);
		// 性别的控件
		TableEditor sexTableEditor = new TableEditor(table);
		int sexIndex = findElement(SEXS, item.getText(SEX_INDEX));
		Combo sexCombo = new Combo(table, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (String sex : SEXS) {
			sexCombo.add(sex);

		}
		sexCombo.select(sexIndex);
		sexTableEditor.grabHorizontal = true;
		sexTableEditor.setEditor(sexCombo, item, SEX_INDEX);
		// 婚否的控件
		TableEditor marryTableEditor = new TableEditor(table);
		Button marryButton = new Button(table, SWT.CHECK);
		if (item.getText(MARRY_INDEX).equals("已婚")) {
			marryButton.setSelection(true);
		} else {
			marryButton.setSelection(false);
		}

		marryTableEditor.grabHorizontal = true;
		marryTableEditor.setEditor(marryButton, item, MARRY_INDEX);

		TableItemControls tableItemControls = new TableItemControls(name, sexCombo, marryButton, nameEditor,
				sexTableEditor, marryTableEditor);

		tableControls.put(item, tableItemControls);

	}

	private int findElement(String[] elements, String target) {
		for (int i = 0; i < elements.length; i++) {
			if (elements[i].equals(target)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			TestTableView window = new TestTableView();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class TableItemControls {
		Text nameText;
		Combo sexCombo;
		Button marryButton;
		TableEditor nameEditor;
		TableEditor sexEditor;
		TableEditor marryEditor;

		public TableItemControls(Text nameText, Combo sexCombo, Button marryButton, TableEditor nameEditor,
				TableEditor sexEditor, TableEditor marryEditor) {
			this.nameText = nameText;
			this.sexCombo = sexCombo;
			this.marryButton = marryButton;
			this.nameEditor = nameEditor;
			this.sexEditor = sexEditor;
			this.marryEditor = marryEditor;
		}

		public void dispose() {
			nameText.dispose();
			sexCombo.dispose();
			marryButton.dispose();
		}
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("表操作相关");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(604, 379);
	}

	private static final int NAME_INDEX = 1;
	private static final int SEX_INDEX = 2;
	private static final int MARRY_INDEX = 3;
	private static final int CHANCE_INDEX = 4;

	private static final String[] SEXS = { "男", "女" };
}
