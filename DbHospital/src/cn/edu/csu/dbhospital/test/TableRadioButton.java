package cn.edu.csu.dbhospital.test;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableRadioButton extends ApplicationWindow {
	private Table table;

	/**
	 * Create the application window.
	 */
	public TableRadioButton() {
		super(null);
		createActions();
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
			Group grpRadiobutton = new Group(container, SWT.NONE);
			grpRadiobutton.setText("RadioButton");
			grpRadiobutton.setBounds(10, 10, 422, 176);

			table = new Table(grpRadiobutton, SWT.BORDER | SWT.FULL_SELECTION);
			table.setBounds(10, 20, 402, 146);
			table.setHeaderVisible(true);
			table.setLinesVisible(true);

			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText("RadioButton");
			createRadioButton(table);

			TableCursor tableCursor = new TableCursor(table, SWT.NONE);

			TableItem tableItem_1 = new TableItem(table, SWT.NONE);
			tableItem_1.setText("New TableItem");

			TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
			tblclmnNewColumn.setWidth(100);
			tblclmnNewColumn.setText("New Column");
		}

		return container;
	}

	private void createRadioButton(Table table) {
		TableEditor editor = new TableEditor(table);

		for (int i = 0; i < table.getItemCount(); i++) {
			editor = new TableEditor(table);
			Button button = new Button(table, SWT.RADIO);
			button.pack();
			editor.minimumWidth = button.getSize().x;
			editor.horizontalAlignment = SWT.LEFT;
			editor.setEditor(button, table.getItem(i), RADIOBUTTON_INDEX);
		}
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			TableRadioButton window = new TableRadioButton();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
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
		newShell.setText("Radio Button");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	private static final int RADIOBUTTON_INDEX = 0;
}
