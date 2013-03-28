package cn.edu.csu.dbhospital;

import java.util.Date;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import cn.edu.csu.dbhospital.action.AboutAction;
import cn.edu.csu.dbhospital.action.ArrangementManageAction;
import cn.edu.csu.dbhospital.action.CardManageAction;
import cn.edu.csu.dbhospital.action.DirectoryManageAction;
import cn.edu.csu.dbhospital.action.DoctorManageAction;
import cn.edu.csu.dbhospital.action.RegisterManageAction;
import cn.edu.csu.dbhospital.action.RoomManageAction;
import cn.edu.csu.dbhospital.action.StaticsAction;
import cn.edu.csu.dbhospital.action.UserManageAction;
import cn.edu.csu.dbhospital.action.WorkerManageAction;
import cn.edu.csu.dbhospital.pojo.TWorker;
import cn.edu.csu.dbhospital.util.SystemUtil;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of the actions added to a workbench window.
 * Each window will be populated with new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	public static ApplicationActionBarAdvisor instance;
	private CardManageAction cardManageAction;
	private RegisterManageAction registerManageAction;
	private DoctorManageAction doctorManageAction;
	private UserManageAction userManageAction;
	private WorkerManageAction workerManageAction;
	private ArrangementManageAction arrangementManageAction;
	private RoomManageAction roomManageAction;
	private DirectoryManageAction directoryManageAction;
	private StaticsAction staticsAction;
	private AboutAction aboutAction;

	private int type;
	private StatusLineContributionItem messageItem;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
		instance = this;
	}

	@Override
	protected void makeActions(IWorkbenchWindow window) {
		type = SystemUtil.LOGIN_WORKER.getType();
		if (type == TWorker.TYPE_ADMIN) {// 管理员才可以看见
			doctorManageAction = new DoctorManageAction(window);
			userManageAction = new UserManageAction(window);
			workerManageAction = new WorkerManageAction(window);
			arrangementManageAction = new ArrangementManageAction(window);
			roomManageAction = new RoomManageAction(window);
			directoryManageAction = new DirectoryManageAction(window);
			staticsAction = new StaticsAction(window);
		} else {
			cardManageAction = new CardManageAction(window);
			registerManageAction = new RegisterManageAction(window);
		}
		aboutAction = new AboutAction(window);
	}

	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		if (type == TWorker.TYPE_ADMIN) {
			MenuManager menuManager2 = new MenuManager("用户管理");
			menuManager2.add(doctorManageAction);
			menuManager2.add(userManageAction);
			menuManager2.add(workerManageAction);

			MenuManager menuManager3 = new MenuManager("参数维护");
			menuManager3.add(arrangementManageAction);
			menuManager3.add(roomManageAction);
			menuManager3.add(directoryManageAction);

			MenuManager menuManager4 = new MenuManager("数据统计");
			menuManager4.add(staticsAction);

			menuBar.add(menuManager2);
			menuBar.add(menuManager3);
			menuBar.add(menuManager4);
		} else {
			MenuManager menuManager = new MenuManager("基本操作");
			menuManager.add(cardManageAction);
			menuManager.add(registerManageAction);
			menuBar.add(menuManager);
		}

		MenuManager menuManager5 = new MenuManager("关于");
		menuManager5.add(aboutAction);
		menuBar.add(menuManager5);

	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(coolBar.getStyle());
		if (type == TWorker.TYPE_ADMIN) {
			toolbar.add(doctorManageAction);
			toolbar.add(arrangementManageAction);
			toolbar.add(staticsAction);
			toolbar.add(roomManageAction);
			toolbar.add(directoryManageAction);
		} else {
			toolbar.add(cardManageAction);
			toolbar.add(registerManageAction);
		}
		coolBar.add(toolbar);
	}

	@Override
	protected void fillStatusLine(IStatusLineManager statusLine) {
		messageItem = new StatusLineContributionItem("message");
		messageItem.setText("");
		statusLine.add(messageItem);
		StatusLineContributionItem statusItem = new StatusLineContributionItem("userinfo");
		statusLine.getProgressMonitor();
		StringBuffer stringBuffer = new StringBuffer("当前用户：");
		if (type == TWorker.TYPE_ADMIN) {
			stringBuffer.append("管理员 ");
		} else {
			stringBuffer.append("操作员 ");
		}
		stringBuffer.append(SystemUtil.LOGIN_WORKER.getRealname() + " ");
		stringBuffer.append(SystemUtil.formatDate(new Date()));
		statusItem.setText(stringBuffer.toString());
		statusLine.add(statusItem);
	}

	// 用于在窗体左下角显示信息
	public void showMessage(String message) {
		messageItem.setText(message);
	}

}
