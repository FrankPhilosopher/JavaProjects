package cn.edu.csu.dbhospital.test;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import cn.edu.csu.dbhospital.dialog.TRoomAddDialog;

/**
 * @description:
 * @author Administrator
 * @version 1.0, 2010-2-19
 * @Copyright 2010-2020
 */
public class MyEditor extends EditorPart {

	public static final String ID = MyEditor.class.getName();
	public static final String[] COLUMN_NAME = { "ID", "NAME", "SEX", "COLOR" };
	private TableViewer table;
	private List persons;

	public MyEditor() {
		super();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		// TODO Auto-generated method stub
		this.setSite(site);
		this.setInput(input);
		this.setPartName(input.getName());

	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	// 创建表格的代码
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		persons = new ArrayList();
		persons.add(new PersonEO(1, "你好", "man", "black"));
		persons.add(new PersonEO(2, "wang", "man", "black"));

		table = new TableViewer(parent, SWT.FULL_SELECTION);
		for (int i = 0; i < 4; i++) {
			new TableColumn(table.getTable(), SWT.LEFT).setText("title" + i);
			table.getTable().getColumn(i).pack();
		}
		table.getTable().setHeaderVisible(true);
		table.getTable().setLinesVisible(true);
		table.setContentProvider(new MyContentProvider());
		table.setLabelProvider(new MyLabelProvider());
		table.setInput(persons);

		this.editor();
	}

	private void editor() {
		table.setColumnProperties(COLUMN_NAME);
		CellEditor[] editors = new CellEditor[4];
		editors[0] = new TextCellEditor(table.getTable());
		editors[1] = new TextCellEditor(table.getTable());
		editors[2] = new TextCellEditor(table.getTable());
		editors[3] = new DialogCellEditor(table.getTable()) {
			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				TRoomAddDialog dialog = new TRoomAddDialog(cellEditorWindow.getShell());
				return dialog.open();
			}
		};

		table.setCellEditors(editors);
		table.setCellModifier(new TableCellModifier());
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	class MyContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			// TODO Auto-generated method stub
			return ((List) inputElement).toArray();
		}

		public void dispose() {
			// TODO Auto-generated method stub

		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub

		}

	}

	class MyLabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			// TODO Auto-generated method stub

			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			// TODO Auto-generated method stub
			PersonEO person = (PersonEO) element;
			if (columnIndex == 0)
				return person.getId() + "";
			if (columnIndex == 1)
				return person.getName();
			if (columnIndex == 2)
				return person.getSex();
			if (columnIndex == 3)
				return person.getColor();
			return null;
		}

		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}

		public void dispose() {
			// TODO Auto-generated method stub

		}

		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}

	}
}