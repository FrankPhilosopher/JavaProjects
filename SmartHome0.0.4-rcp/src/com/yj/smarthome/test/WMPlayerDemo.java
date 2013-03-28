package com.yj.smarthome.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Display;
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
public class WMPlayerDemo {
	static OleClientSite clientSite;

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Media Player Example");
		shell.setLayout(new FillLayout());
		try {
			OleFrame frame = new OleFrame(shell, SWT.NONE);
			clientSite = new OleClientSite(frame, SWT.NONE, "WMPlayer.OCX");
			clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
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

	// try opening with wmv, mpg, mpeg, avi, asf, wav with WMPlayer
	static void fileOpen() {
//		FileDialog dialog = new FileDialog(clientSite.getShell(), SWT.OPEN);
//		dialog.setFilterExtensions(new String[] { "*.*", "*.wmv", "*.mp3", "*.swf", "*.flv" });//mp4,swf,mp3,wmv
//		String filename = dialog.open();
		String filename = "http://192.168.0.178/videostream.asf?user=admin&pwd=123456&resolution=320*240&rate=11";
		if (filename != null) {
			OleAutomation player = new OleAutomation(clientSite);

//			int loadFile[] = player.getIDsOfNames(new String[] { "launchURL" });
//			if (loadFile != null) {
//				Variant result = player.invoke(loadFile[0], new Variant[] { new Variant(filename) });
//				if (result == null)
//					disposeClient();
//				else
//					result.dispose();
//			} else {
//				disposeClient();
//			}

			int playURL[] = player.getIDsOfNames(new String[] { "URL" });
			if (playURL != null) {
				Variant theFile = new Variant(filename);
				player.setProperty(playURL[0], theFile);
			}

			player.dispose();
		}
	}

	static void disposeClient() {
		if (clientSite != null)
			clientSite.dispose();
		clientSite = null;
	}
}