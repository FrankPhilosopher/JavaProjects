package com.yj.smarthome.test;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * A simple executable Java class that compares the SWT Group object with the 
 * Swing JPanel and Titled Borders.
 * 
 * @author R.J. Lorimer
 */
public class GroupTest {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("SWT Window");
		shell.setLayout(new GridLayout(1, true));
		setupSWT(shell);
		
		JFrame frame = new JFrame("Swing Window");
		Container container = frame.getContentPane();
		
		setupSwing(container);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
		
	}
	
	private static void setupSWT(Composite parent) {
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setText("Title");
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
			
		// unlike Swing, composites don't work without a layout set on them
		group.setLayout(new GridLayout(1, true));
		Button aButton = new Button(group, SWT.PUSH);
		aButton.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, true));
		aButton.setText("Test");
		group.pack();
	}
	
	private static void setupSwing(Container parent) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
		
		JPanel panel = new JPanel();
		parent.add(panel);
		//	Using overly explicit border code to show the same selections 
		//	are being used.
		Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border titledEtchedBorder = BorderFactory.createTitledBorder(etchedBorder, "Title");
		panel.setBorder(titledEtchedBorder);
		panel.add(new JButton("Test"));
	}
}
