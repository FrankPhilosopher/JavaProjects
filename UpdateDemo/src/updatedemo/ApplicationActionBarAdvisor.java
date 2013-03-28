package updatedemo;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.update.ui.UpdateJob;
import org.eclipse.update.ui.UpdateManagerUI;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction updateAction;
	private IWorkbenchAction perspectiveAction;
	private IContributionItem showViewAction;
	private IWorkbenchAction preferenceAction;

	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	@Override
	protected void makeActions(IWorkbenchWindow window) {
		updateAction = createUpdateAction(window);
		register(updateAction);
		
		// 定义“打开透视图”操作
		perspectiveAction = ActionFactory.OPEN_PERSPECTIVE_DIALOG.create(window);
		register(perspectiveAction);

		// 定义“显示视图列表”操作
		showViewAction = ContributionItemFactory.VIEWS_SHORTLIST.create(window);

		// 定义打开“首选项”操作
		preferenceAction = ActionFactory.PREFERENCES.create(window);
		// preferenceAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_HOME_NAV));
		register(preferenceAction);

	}

	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		// 定义Window菜单
		MenuManager windowManager = new MenuManager("&Window",
				IWorkbenchActionConstants.M_WINDOW);
		windowManager.add(perspectiveAction);
		windowManager.add(preferenceAction);
		windowManager.add(updateAction);
		
		// Window菜单的子菜单Show View菜单，创建二级菜单
		MenuManager showViewManager = new MenuManager("&Show View", "showView");
		// 包含一个“other”，用于打开未显示的视图
		showViewManager.add(showViewAction);
		windowManager.add(showViewManager);
		
		menuBar.add(windowManager);
	}

	// 不能显示出来！
	private IWorkbenchAction createUpdateAction(final IWorkbenchWindow window) {
		IWorkbenchAction action = new IWorkbenchAction() {
			@Override
			public void run() {
				BusyIndicator.showWhile(window.getShell().getDisplay(), new Runnable() {
				    public void run() {
				      UpdateJob job = new UpdateJob("Search for update", false, false);//$NON-NLS-1$
				      UpdateManagerUI.openInstaller(window.getShell(), job);
				      PlatformUI.getWorkbench().getProgressService().showInDialog(window.getShell(), job);
				}
				});
			}
			@Override
			public String getText() {
				return "Find and Install";
			}
			@Override
			public String getId() {
				return "update";
			}
			@Override
			public ImageDescriptor getDisabledImageDescriptor() {
				return null;
			}
			@Override
			public void addPropertyChangeListener(
					IPropertyChangeListener listener) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public int getAccelerator() {
				// TODO Auto-generated method stub
				return 0;
			}
			@Override
			public String getActionDefinitionId() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public HelpListener getHelpListener() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public ImageDescriptor getHoverImageDescriptor() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public ImageDescriptor getImageDescriptor() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public IMenuCreator getMenuCreator() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public int getStyle() {
				// TODO Auto-generated method stub
				return 0;
			}
			@Override
			public String getToolTipText() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public boolean isChecked() {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean isHandled() {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public void removePropertyChangeListener(
					IPropertyChangeListener listener) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void runWithEvent(Event event) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setActionDefinitionId(String id) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setChecked(boolean checked) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setDescription(String text) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setDisabledImageDescriptor(ImageDescriptor newImage) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setEnabled(boolean enabled) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setHelpListener(HelpListener listener) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setHoverImageDescriptor(ImageDescriptor newImage) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setId(String id) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setImageDescriptor(ImageDescriptor newImage) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setMenuCreator(IMenuCreator creator) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setText(String text) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setToolTipText(String text) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setAccelerator(int keycode) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}
		};
		return action;
	}

}
