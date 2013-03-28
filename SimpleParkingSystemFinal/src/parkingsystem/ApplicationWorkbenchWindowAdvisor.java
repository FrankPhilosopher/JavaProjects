package parkingsystem;

import java.lang.reflect.Method;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.rcp.wbw.skin.LookAndFeel;
import com.rcp.wxh.communicate.CommunicateJob;
import com.rcp.wxh.editors.EnterEditor;
import com.rcp.wxh.editors.EnterEditorInput;
import com.rcp.wxh.resource.IimageKeys;
import com.rcp.wxh.resource.Messages;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	private TrayItem trayItem;
	private Image trayImage;
	private IWorkbenchWindow window;

	@Override
	public boolean preWindowShellClose() {
		boolean confirm = MessageDialog.openConfirm(getWindowConfigurer().getWindow().getShell(), "提示", "确认退出系统么?");
		if (confirm)
			return super.preWindowShellClose();
//    		return true;
		else
			return false;
	}

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1100, 750));
		// 菜单栏可见
		configurer.setShowMenuBar(true);
		// 工具栏可见
		configurer.setShowCoolBar(true);
		// 设置状态栏可见
		configurer.setShowStatusLine(true);
		// 设置工作进度可见
		configurer.setShowProgressIndicator(true);
		// 设置快速试图可见
		configurer.setShowFastViewBars(true);
		IPreferenceStore preStore = PrefUtil.getAPIPreferenceStore();
		preStore.setValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
		configurer.setTitle(Messages.SOFT_NAME);
	}

	// 设置菜单背景图片代码片段
	public void setMenuBG(Image mimage) {
		// 这里执行了，并且没有报错！
		org.eclipse.jface.action.MenuManager mm = (MenuManager) ApplicationActionBarAdvisor.getDefault().getMenubar();
		final Menu menu = mm.getMenu();
		invoke("setBackgroundImage", menu, new Class[] { Image.class }, new Image[] { mimage });
	}

	//java反射   此段代码执行了也没有报错，但是没有产生效果
	Object invoke(String methodName, Object object, Class<?>[] argsTypes, Object[] args) {
		Object result = null;
		try {
			Method m = object.getClass().getDeclaredMethod(methodName, argsTypes);
			m.setAccessible(true);
			result = m.invoke(object, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;//result = null
	}

	// 美化工具栏代码片段,其设置背景图片
	public void setToorbarBG(Image timage) {
		Object[] childrens = getWindowConfigurer().getWindow().getShell().getChildren();
		for (int i = 0; i < childrens.length; i++) {
			String clazz = childrens[i].getClass().getName();
			if (clazz.endsWith("CBanner")) {
				((Composite) childrens[i]).setBackgroundImage(timage);
				((Composite) childrens[i]).setBackgroundMode(SWT.INHERIT_FORCE);
			}
		}
	}

	public void setEditorTabFolderColor(Color color) {

		if (getWindowConfigurer().getWindow() == null) {
			return;
		}
		if (getWindowConfigurer().getWindow().getActivePage() == null) {
			return;
		}
		WorkbenchPage page = (WorkbenchPage) getWindowConfigurer().getWindow().getActivePage();
		Composite client = page.getClientComposite();

		Control[] children = client.getChildren();
		Composite child = (Composite) children[0];
		Control[] controls = child.getChildren();
		for (final Control control : controls) {
			if (control instanceof CTabFolder) {
				control.setBackground(color);

			}
		}
	}

	public void setEditorAreaBG(Image image) {

		if (getWindowConfigurer().getWindow() == null) {
			return;
		}
		if (getWindowConfigurer().getWindow().getActivePage() == null) {
			return;
		}
		WorkbenchPage page = (WorkbenchPage) getWindowConfigurer().getWindow().getActivePage();
		Composite client = page.getClientComposite();
		Control[] children = client.getChildren();
		Composite child = (Composite) children[0];
		Control[] controls = child.getChildren();

		for (final Control control : controls) {
			if (control instanceof CTabFolder) {
				CTabFolder tabfolder = (CTabFolder) control;
				Listener[] listeners = tabfolder.getListeners(SWT.MenuDetect);
				if (listeners != null) {
					for (int i = 0; i < listeners.length; i++) {
						// 屏蔽系统右键菜单
						tabfolder.removeListener(SWT.MenuDetect, listeners[i]);
					}
				}
				Listener[] listeners2 = tabfolder.getListeners(SWT.DragDetect);
				if (listeners2 != null) {
					for (int i = 0; i < listeners2.length; i++) {
						// 屏蔽编辑器默认可拖动的属性
						tabfolder.removeListener(SWT.DragDetect, listeners2[i]);
					}
				}
				tabfolder.setBackgroundImage(image);
				tabfolder.setBackgroundMode(SWT.INHERIT_FORCE);
			}
		}

	}

	public void addPartListener(final Color color) {
		getWindowConfigurer().getWindow().getActivePage().addPartListener(new IPartListener() {

			public void partActivated(IWorkbenchPart part) {
				if (part instanceof EditorPart) {
					setEditorTabFolderColor(color);
				}
			}

			public void partBroughtToTop(IWorkbenchPart part) {
				if (part instanceof EditorPart) {
					setEditorTabFolderColor(color);
				}
			}

			public void partClosed(IWorkbenchPart part) {
				if (part instanceof EditorPart) {
					setEditorTabFolderColor(color);
				}
			}

			public void partDeactivated(IWorkbenchPart part) {
				if (part instanceof EditorPart) {
					setEditorTabFolderColor(color);
				}
			}

			public void partOpened(IWorkbenchPart part) {
				if (part instanceof EditorPart) {
					setEditorTabFolderColor(color);
				}
			}
		});
	}

	public void setProgressIndicatorBG(Image image) {
		Object[] childrens = getWindowConfigurer().getWindow().getShell().getChildren();
		for (int i = 0; i < childrens.length; i++) {
			String clazz = childrens[i].getClass().getName();

			if (clazz.endsWith("ProgressRegion$1")) {
				((Composite) childrens[i]).setBackgroundImage(image);
				((Composite) childrens[i]).setBackgroundMode(SWT.INHERIT_FORCE);
				//The Composite constant to indicate that an attribute (such as background) is inherited by all children.
			}
		}
	}

	public void setStausLineBG(Image image) {
		Object[] childrens = getWindowConfigurer().getWindow().getShell().getChildren();
		for (int i = 0; i < childrens.length; i++) {
			String clazz = childrens[i].getClass().getName();

			if (clazz.endsWith("StatusLine")) {
				((Composite) childrens[i]).setBackgroundImage(image);
				((Composite) childrens[i]).setBackgroundMode(SWT.INHERIT_FORCE);
			}
		}
	}

	/**
	 * 窗口启动后响应
	 */
	public void postWindowOpen() {
		super.postWindowOpen();
		// getWindowConfigurer().getWindow().getShell().setVisible(false);
		setMenuBG(LookAndFeel.getDefault().getMenuImage());// 调用菜单背景图片
		setToorbarBG(LookAndFeel.getDefault().getToolBarImage());// 调用工具栏背景图片
		setStausLineBG(LookAndFeel.getDefault().getToolBarImage());
		setProgressIndicatorBG(LookAndFeel.getDefault().getToolBarImage());

		setEditorAreaBG(LookAndFeel.getDefault().getContentBgImage());
		setEditorTabFolderColor(LookAndFeel.getDefault().getTabFolderColor());
		addPartListener(LookAndFeel.getDefault().getTabFolderColor());

		getWindowConfigurer().getWindow().getShell().setBackground(LookAndFeel.getDefault().getShellColor());
		getWindowConfigurer().getWindow().getShell().setBackgroundMode(SWT.INHERIT_FORCE);
		getWindowConfigurer().getWindow().getShell().redraw();

		// getWindowConfigurer().getWindow().getShell().setVisible(true);
		window = getWindowConfigurer().getWindow();
		trayItem = initTaskItem(window);
		if (trayItem != null) {
			careateMinimize();
		}

		// 启动车辆出入监控编辑器
		IWorkbenchPage workbenchPage = window.getActivePage();
		IEditorPart editor = workbenchPage.findEditor(EnterEditorInput.eei); // 获取编辑器
		if (editor != null) { // 如果编辑器已经打开
			workbenchPage.bringToTop(editor); // 则显示该编辑器
		} else { // 重新初始化该编辑器
			try {
				editor = workbenchPage.openEditor(EnterEditorInput.eei, EnterEditor.ID);
			} catch (PartInitException ei) {
				ei.printStackTrace();
			}
		}

		// 通信初始化
		CommunicateJob job = new CommunicateJob("与终端通信中...");//参数会作为状态栏的显示信息一直显示着
		job.schedule();
	}

	// 初始化系统托盘对象
	private TrayItem initTaskItem(IWorkbenchWindow window) {
		final Tray tray = window.getShell().getDisplay().getSystemTray();
		TrayItem trayItem = new TrayItem(tray, SWT.NONE);
		try {
			trayImage = Activator.getImageDescriptor(IimageKeys.SYSTEM_TRAY).createImage();
		} catch (Exception e) {
			System.out.println("托盘文件加载错误！");
		}
		trayItem.setImage(trayImage);
		trayItem.setToolTipText("");
		return trayItem;
	}

	/**
	 * 为当前窗口增加监听 监听最小化事件，以及点击托盘图标响应事件
	 */
	private void careateMinimize() {
		window.getShell().addShellListener(new ShellAdapter() {
			public void shellIconified(ShellEvent e) {
//    			window.getShell().setVisible(false);//图标在任务栏隐藏起来
			}
		});
		trayItem.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event arg0) {
				Shell shell = window.getShell();
				if (!shell.isVisible()) {
					shell.setVisible(true);
					shell.setMinimized(false);
				}
			}
		});
	}

}
