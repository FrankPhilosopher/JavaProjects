package cn.edu.csu.dbhospital.test;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class RadioButtonCellEditor extends CellEditor {

	private Button button;

	private Boolean isSelected;

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.button.setText(this.text);
	}

	public RadioButtonCellEditor() {
		setStyle(0);
	}

	public RadioButtonCellEditor(Composite composite) {
		this(composite, 0);
	}

	public RadioButtonCellEditor(Composite composite, int style) {
		super(composite, style);
	}

	@Override
	protected Control createControl(Composite parent) {
		// TODO Auto-generated method stub
		this.button = new Button(parent, SWT.RADIO);
		this.button.setFont(parent.getFont());

		this.button.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				RadioButtonCellEditor.this.isSelected = RadioButtonCellEditor.this.button.getSelection();
			}
		});
		// this.button.setFocus();
		return this.button;
	}

	@Override
	protected Object doGetValue() {
		// TODO Auto-generated method stub
		return this.isSelected;
	}

	@Override
	protected void doSetFocus() {
		this.button.setFocus();

	}

	@Override
	protected void doSetValue(Object value) {
		this.isSelected = (Boolean) value;
		this.button.setSelection((Boolean) value);
	}

}