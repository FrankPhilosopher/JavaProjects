package parkingsystem;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.rcp.wxh.actions.ValidateStatusAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction passwordManageAction; // 密码管理
	private IWorkbenchAction loginoutManageAction; // 注销登陆

//	private IWorkbenchAction loginAction;
//	private IWorkbenchAction enterManageAction; // 车辆入场监控响应
//	private IWorkbenchAction leaveManageAction; // 车辆出场监控响应
	private IWorkbenchAction enterExitManageAction; // 车辆出入场监控
	private IWorkbenchAction statisticManageAction; // 数据统计响应
	private IWorkbenchAction empManageAction; // 员工信息管理
	private IWorkbenchAction cardManageAction; // 卡片管理响应
	private IWorkbenchAction systemManageAction; // 系统管理响应
	private IWorkbenchAction exceptionManageAction; // 异常记录管理响应
	private IWorkbenchAction expenseManageAction; // 费用记录管理响应
	private IWorkbenchAction carRecordManageAction; // 车辆出入记录管理响应
	private IWorkbenchAction carEnterManageAction; // 当前停车车辆信息查询响应

	private IWorkbenchAction aboutAction; // 关于对话框

	private static ApplicationActionBarAdvisor instance;
	private static IActionBarConfigurer configure;
	private static IMenuManager menubar;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
//		this.configure = configurer;
	}

	protected void makeActions(IWorkbenchWindow window) {
		try {
			passwordManageAction = ApplicationActionFactory.PASSWORD_MANAGE.create(window);
			register(passwordManageAction);
			loginoutManageAction = ApplicationActionFactory.LOGINOUT_MANAGE.create(window);
			register(loginoutManageAction);
//			loginAction = ApplicationActionFactory.LOGIN_WINDOW.create(window);
//			register(loginAction);
//			enterManageAction = ApplicationActionFactory.ENTER_MANAGE.create(window);
//			register(enterManageAction);
//			leaveManageAction = ApplicationActionFactory.LEAVE_MANAGE.create(window);
//			register(leaveManageAction);
			enterExitManageAction = ApplicationActionFactory.ENTER_EXIT_MANAGE.create(window);
			register(enterExitManageAction);
			statisticManageAction = ApplicationActionFactory.STATISTIC_MANAGE.create(window);
			register(statisticManageAction);
			empManageAction = ApplicationActionFactory.EMP_MANAGE.create(window);
			register(empManageAction);
			cardManageAction = ApplicationActionFactory.CARD_MANAGE.create(window);
			register(cardManageAction);
			systemManageAction = ApplicationActionFactory.SYSTEM_MANAGE.create(window);
			register(systemManageAction);
			exceptionManageAction = ApplicationActionFactory.EXCEPTION_MANAGE.create(window);
			register(exceptionManageAction);
			expenseManageAction = ApplicationActionFactory.EXPENSE_MANAGE.create(window);
			register(expenseManageAction);
			carRecordManageAction = ApplicationActionFactory.CARRECORD_MANAGE.create(window);
			register(carRecordManageAction);
			carEnterManageAction = ApplicationActionFactory.CARENTER_MANAGE.create(window);
			register(carEnterManageAction);
			aboutAction = ApplicationActionFactory.ABOUT.create(window);
			register(aboutAction);
			aboutAction.setText("关于...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 菜单栏初始化
	 */
	protected void fillMenuBar(IMenuManager menuBar) {
		try {
			menubar = menuBar;
			MenuManager personalMenu = new MenuManager("个人信息管理(P)", "personal");
			menuBar.add(personalMenu);
			personalMenu.add(passwordManageAction);
			personalMenu.add(loginoutManageAction);

			MenuManager carMenu = new MenuManager("停车管理(C)", "parking");
			menuBar.add(carMenu);
//			carMenu.add(enterManageAction);
//			carMenu.add(leaveManageAction);
			carMenu.add(enterExitManageAction);

			MenuManager recordMenu = new MenuManager("信息记录管理(I)", "information");
			menuBar.add(recordMenu);
			recordMenu.add(carEnterManageAction);
			recordMenu.add(carRecordManageAction);
			recordMenu.add(expenseManageAction);
			recordMenu.add(exceptionManageAction);
			recordMenu.add(statisticManageAction);

			MenuManager sysMenu = new MenuManager("系统管理(S)", "system");
			menuBar.add(sysMenu);
			sysMenu.add(cardManageAction);
			sysMenu.add(empManageAction);
			sysMenu.add(systemManageAction);

			MenuManager helpMenu = new MenuManager("帮助(H)", IWorkbenchActionConstants.M_HELP);
			menuBar.add(helpMenu);
			helpMenu.add(aboutAction);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 工具栏初始化
	 */
	protected void fillCoolBar(ICoolBarManager coolBar) {
		try {
			IToolBarManager toolbar = new ToolBarManager(coolBar.getStyle());
			coolBar.add(toolbar);
//			toolbar.add(enterManageAction);
//			toolbar.add(leaveManageAction);
			toolbar.add(enterExitManageAction);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化状态栏
	 */
	protected void fillStatusLine(IStatusLineManager statusLine) {
		super.fillStatusLine(statusLine);
		// 注意这里创建了一个item，所以会将状态栏分出一部分专门显示该信息
		StatusLineContributionItem statusItem = new StatusLineContributionItem("msg");
		statusLine.getProgressMonitor();// Returns a progress monitor which reports progress in the status line.
//		statusItem.setText("当前登录:" + ValidateStatusAction.user.getOperatorname());//yinger 为了测试删除了
		statusLine.add(statusItem);
	}

	public static IActionBarConfigurer getInstance() {
		return configure;
	}

	public static ApplicationActionBarAdvisor getDefault() {// 返回实例
		return instance;
	}

	public static IMenuManager getMenubar() {// 返回菜单管理工具
		return menubar;
	}
}
