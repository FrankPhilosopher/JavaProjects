package mydip;

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
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {

		exitAction = ActionFactory.QUIT.create(window);
		exitAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		register(exitAction);

		aboutAction = ActionFactory.ABOUT.create(window);
		// aboutAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		aboutAction.setImageDescriptor(Activator.getImageDescriptor("/icons/small/about.gif"));// ��������ͼ��ķ�ʽ
		register(aboutAction);

	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileManager = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		// ��File�˵�������˳��˵���
		fileManager.add(exitAction);

		// ���File�˵����˵���
		menuBar.add(fileManager);

		MenuManager aboutManager = new MenuManager("&About", IWorkbenchActionConstants.M_HELP);
		aboutManager.add(aboutAction);
		menuBar.add(aboutManager);

	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar1 = new ToolBarManager(coolBar.getStyle());
		// �����toolbar�������һ��������ť
		// toolbar1.add(openNavigatorViewAction);
		// �����toolbar��ӵ�coolbar����
		coolBar.add(toolbar1);

	}

}
