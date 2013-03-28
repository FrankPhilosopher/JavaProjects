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

	private IWorkbenchAction passwordManageAction; // �������
	private IWorkbenchAction loginoutManageAction; // ע����½

//	private IWorkbenchAction loginAction;
//	private IWorkbenchAction enterManageAction; // �����볡�����Ӧ
//	private IWorkbenchAction leaveManageAction; // �������������Ӧ
	private IWorkbenchAction enterExitManageAction; // �������볡���
	private IWorkbenchAction statisticManageAction; // ����ͳ����Ӧ
	private IWorkbenchAction empManageAction; // Ա����Ϣ����
	private IWorkbenchAction cardManageAction; // ��Ƭ������Ӧ
	private IWorkbenchAction systemManageAction; // ϵͳ������Ӧ
	private IWorkbenchAction exceptionManageAction; // �쳣��¼������Ӧ
	private IWorkbenchAction expenseManageAction; // ���ü�¼������Ӧ
	private IWorkbenchAction carRecordManageAction; // ���������¼������Ӧ
	private IWorkbenchAction carEnterManageAction; // ��ǰͣ��������Ϣ��ѯ��Ӧ

	private IWorkbenchAction aboutAction; // ���ڶԻ���

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
			aboutAction.setText("����...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �˵�����ʼ��
	 */
	protected void fillMenuBar(IMenuManager menuBar) {
		try {
			menubar = menuBar;
			MenuManager personalMenu = new MenuManager("������Ϣ����(P)", "personal");
			menuBar.add(personalMenu);
			personalMenu.add(passwordManageAction);
			personalMenu.add(loginoutManageAction);

			MenuManager carMenu = new MenuManager("ͣ������(C)", "parking");
			menuBar.add(carMenu);
//			carMenu.add(enterManageAction);
//			carMenu.add(leaveManageAction);
			carMenu.add(enterExitManageAction);

			MenuManager recordMenu = new MenuManager("��Ϣ��¼����(I)", "information");
			menuBar.add(recordMenu);
			recordMenu.add(carEnterManageAction);
			recordMenu.add(carRecordManageAction);
			recordMenu.add(expenseManageAction);
			recordMenu.add(exceptionManageAction);
			recordMenu.add(statisticManageAction);

			MenuManager sysMenu = new MenuManager("ϵͳ����(S)", "system");
			menuBar.add(sysMenu);
			sysMenu.add(cardManageAction);
			sysMenu.add(empManageAction);
			sysMenu.add(systemManageAction);

			MenuManager helpMenu = new MenuManager("����(H)", IWorkbenchActionConstants.M_HELP);
			menuBar.add(helpMenu);
			helpMenu.add(aboutAction);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��������ʼ��
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
	 * ��ʼ��״̬��
	 */
	protected void fillStatusLine(IStatusLineManager statusLine) {
		super.fillStatusLine(statusLine);
		// ע�����ﴴ����һ��item�����ԻὫ״̬���ֳ�һ����ר����ʾ����Ϣ
		StatusLineContributionItem statusItem = new StatusLineContributionItem("msg");
		statusLine.getProgressMonitor();// Returns a progress monitor which reports progress in the status line.
//		statusItem.setText("��ǰ��¼:" + ValidateStatusAction.user.getOperatorname());//yinger Ϊ�˲���ɾ����
		statusLine.add(statusItem);
	}

	public static IActionBarConfigurer getInstance() {
		return configure;
	}

	public static ApplicationActionBarAdvisor getDefault() {// ����ʵ��
		return instance;
	}

	public static IMenuManager getMenubar() {// ���ز˵�������
		return menubar;
	}
}
