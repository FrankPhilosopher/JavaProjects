package com.yinger.patientims;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.yinger.patientims.actions.OpenExpenseInfoViewAction;
import com.yinger.patientims.actions.OpenNavigatorViewAction;
import com.yinger.patientims.actions.OpenPatientInfoViewAction;
import com.yinger.patientims.actions.OpenSearchViewAction;

/**
 * Public base class for configuring the action bars of a workbench window.
 * 该类继承了ActionBarAdvisor类，负责创建RCP应用程序窗体的菜单，工具栏，状态栏等等 以及添加相应的操作
 * 
 * 重要的方法： 1.makeActions(IWorkbenchWindow window)：填充方法 2.fillMenuBar(IMenuManager
 * menuBar)：填充主菜单栏 3.fillCoolBar(ICoolBarManager coolBarManager)：填充工具栏
 * 4.fillStatusLine(IStatusLineManager statusLineManager)：填充状态栏
 * 5.isApplicationMenu()：返回是否是给定窗体的给定菜单的ID的应用程序菜单
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction newWindowAction;
	private IWorkbenchAction exitAction;
	private IWorkbenchAction perspectiveAction;
	private IWorkbenchAction aboutAction;
	private IContributionItem showViewAction;
	private OpenNavigatorViewAction openNavigatorViewAction;
	private OpenPatientInfoViewAction openPatientInfoViewAction;
	private OpenExpenseInfoViewAction openExpenseInfoViewAction;
	private OpenSearchViewAction openSearchViewAction;
	private IWorkbenchAction preferenceAction;

	// 这个构造方法在 ApplicationWorkbenchWindowAdvisor类中调用
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	// 这个方法很重要，用于设置窗体中的操作
	protected void makeActions(IWorkbenchWindow window) {
		// ActionFactory：工作台操作工厂对象 Access to standard actions provided by
		// the workbench.
		// ActionFactory.OPEN_NEW_WINDOW ：具体化了的用于创建“新建窗口”操作的操作工厂对象
		// 定义“新建窗口”操作
		newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
		newWindowAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_DEF_VIEW));
		// 注册操作
		register(newWindowAction);

		// 定义“退出”操作
		exitAction = ActionFactory.QUIT.create(window);
		exitAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		register(exitAction);

		// 定义“打开透视图”操作
		perspectiveAction = ActionFactory.OPEN_PERSPECTIVE_DIALOG.create(window);
		register(perspectiveAction);

		// 定义“关于”操作
		aboutAction = ActionFactory.ABOUT.create(window);
//		aboutAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		aboutAction.setImageDescriptor(Activator.getImageDescriptor("/icons/small/about.gif"));
		register(aboutAction);

		// 定义“显示视图列表”操作
		showViewAction = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
//		((IAction) showViewAction).setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
		// register(showViewAction); //注意，这类视图不用注册，register方法会报错！

		// 定义打开“首选项”操作
		preferenceAction = ActionFactory.PREFERENCES.create(window);
//		preferenceAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_HOME_NAV));
		preferenceAction.setImageDescriptor(Activator.getImageDescriptor("/icons/Home.ico"));
		register(preferenceAction);

		// 自定义的操作类，分别用来打开相应的视图
		openNavigatorViewAction = new OpenNavigatorViewAction(window);
		openExpenseInfoViewAction = new OpenExpenseInfoViewAction(window);
		openPatientInfoViewAction = new OpenPatientInfoViewAction(window);
		openSearchViewAction = new OpenSearchViewAction(window);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		// IWorkbenchActionConstants:Action ids for standard actions, groups
		// in the workbench menu bar, and global actions.
		// 定义File菜单
		MenuManager fileManager = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		// 在File菜单中添加退出菜单项
		fileManager.add(exitAction);
		// 添加File菜单到菜单栏
		menuBar.add(fileManager);

		// Window菜单
		MenuManager windowManager = new MenuManager("&Window", IWorkbenchActionConstants.M_WINDOW);
		windowManager.add(newWindowAction);
		windowManager.add(perspectiveAction);
		windowManager.add(preferenceAction);
		// Window菜单的子菜单Show View菜单，创建二级菜单
		MenuManager showViewManager = new MenuManager("&Show View", "showView");
//		showViewManager.add(openExpenseInfoViewAction);
//		showViewManager.add(openNavigatorViewAction);
//		showViewManager.add(openPatientInfoViewAction);
//		showViewManager.add(openSearchViewAction);
		// 包含一个“other”，用于打开未显示的视图
		//TODO:很奇怪的是 show view会自己显示一部分的view，这些view是当前的窗口已经显示出来的view
		showViewManager.add(showViewAction);
		windowManager.add(showViewManager);
		menuBar.add(windowManager);

		// Help菜单
		MenuManager helpManager = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
		helpManager.add(aboutAction);
		menuBar.add(helpManager);
	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		// Creates a tool bar manager with the given SWT [button style]
		IToolBarManager toolbar1 = new ToolBarManager(coolBar.getStyle());
		// 在这个toolbar上面添加一个操作按钮
		toolbar1.add(openNavigatorViewAction);
		// 把这个toolbar添加到coolbar上面
		coolBar.add(toolbar1);

		IToolBarManager toolbar2 = new ToolBarManager(coolBar.getStyle());
		toolbar2.add(openSearchViewAction);
		coolBar.add(toolbar2);

		IToolBarManager toolbar3 = new ToolBarManager(coolBar.getStyle());
		toolbar3.add(perspectiveAction);
		coolBar.add(toolbar3);

	}

}
