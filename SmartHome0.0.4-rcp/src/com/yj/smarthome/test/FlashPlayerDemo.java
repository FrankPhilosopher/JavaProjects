package com.yj.smarthome.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/*
 * Open an OLE Windows Media Player.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */
public class FlashPlayerDemo {
//	static OleClientSite clientSite;
	static OleControlSite controlSite;

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Flash Player Example");
		shell.setLayout(new FillLayout());
		try {
			OleFrame frame = new OleFrame(shell, SWT.NONE);
//			controlSite = new OleControlSite(frame, SWT.NONE, "ShockwaveFlash.ShockwaveFlash");
			controlSite = new OleControlSite(frame, SWT.NONE, "RealPlayX.OCX");
//			clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
			controlSite.doVerb(OLE.OLEIVERB_SHOW); //����Ϊ��ʾ�ؼ�
			addFileMenu(frame);
		} catch (SWTError e) {
			System.out.println("Unable to open activeX control");
			display.dispose();
			return;
		}
		shell.setSize(800, 600);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	static void addFileMenu(OleFrame frame) {
		final Shell shell = frame.getShell();
		Menu menuBar = shell.getMenuBar();
		if (menuBar == null) {
			menuBar = new Menu(shell, SWT.BAR);
			shell.setMenuBar(menuBar);
		}
		MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
		fileMenu.setText("&File");
		Menu menuFile = new Menu(fileMenu);
		fileMenu.setMenu(menuFile);
		frame.setFileMenus(new MenuItem[] { fileMenu });

		MenuItem menuFileOpen = new MenuItem(menuFile, SWT.CASCADE);
		menuFileOpen.setText("Open...");
		menuFileOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fileOpen();
			}
		});
		MenuItem menuFileExit = new MenuItem(menuFile, SWT.CASCADE);
		menuFileExit.setText("Exit");
		menuFileExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
	}

	static void fileOpen() {
		FileDialog dialog = new FileDialog(controlSite.getShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.*" });//mp4,swf,mp3,wmv
		String filename = dialog.open();
		if (filename != null) {
			OleAutomation automation = new OleAutomation(controlSite);
			int[] methodIDs = automation.getIDsOfNames(new String[] { "LoadMovie" });
//			int playURL[] = player.getIDsOfNames(new String[] { "LoadMovie" });
//			if (playURL != null) {
//				Variant theFile = new Variant(filename);
//				player.setProperty(playURL[0], theFile);
//			}
			Variant[] methodArgs = { new Variant(0), new Variant(filename) };
			automation.invoke(methodIDs[0], methodArgs);
			automation.dispose();
		}
	}
}