package com.yinger.patientims.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import com.yinger.patientims.actions.OpenSearchViewAction;
import com.yinger.patientims.navigator.NavigatorEntityElement;
import com.yinger.patientims.navigator.NavigatorEntityFactory;
import com.yinger.patientims.util.PluginUtil;
import com.yinger.patientims.views.viewerProvider.NavigatorTreeViewerContentProvider;
import com.yinger.patientims.views.viewerProvider.NavigatorTreeViewerLabelProvider;

/**
 * 导航视图
 * 
 */
public class NavigatorView extends ViewPart {

	private TreeViewer treeViewer; // 树查看器

	@Override
	public void createPartControl(Composite parent) {
		// 创建树查看器
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		// 设置内容提供其和标签提供器
		treeViewer.setContentProvider(new NavigatorTreeViewerContentProvider());
		treeViewer.setLabelProvider(new NavigatorTreeViewerLabelProvider());
		// 读入数据
		treeViewer.setInput(NavigatorEntityFactory.setNavigatorEntity());
		// 自定义的方法
		// 设置视图的工具栏
		setViewToolBar();
		// 自定义的方法，实现双击打开相应的编辑器的功能
		hookDoubleClickAction();
	}

	// 这个方法实际上就是给treeviewer添加了一个处理双击事件的监听器
	private void hookDoubleClickAction() {
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = treeViewer.getSelection();
				// 得到选中的项，注意方法是将得到的选项转换成 IStructuredSelection，在调用 getFirstElement 方法
				Object object = ((IStructuredSelection) selection).getFirstElement();
				// 再将对象转为实际的树节点对象
				NavigatorEntityElement element = (NavigatorEntityElement) object;
				// 得到该对象的editorInput
				IEditorInput editorInput = element.getEditorInput();
				// 得到当前工作台的page
				IWorkbenchPage workbenchPage = getViewSite().getPage();
				// 如果点击的是 信息查询
				if (element.getName().equals("Information Search")) {
					new OpenSearchViewAction(getSite().getWorkbenchWindow()).run();
					return;
				}
				String editorID = null;
				// 这里要结合NavigatorEntityFactory类的setNavigatorEntity方法
				// 这一部分对原书作了修改，化繁为简
				if (element.getName().equals("Patient Information")) {// 病人信息管理
					editorID = PluginUtil.PatientInfoEditor_ID;
				}else if (element.getName().equals("Diagnose Information")) {// 诊断信息管理
					editorID = PluginUtil.DiagnoseInfoEditor_ID;
				}else if (element.getName().equals("Expense Information")) {// 费用信息管理
					editorID = PluginUtil.ExpenseInfoEditor_ID;
				}else{
					return;
				}
				//IEditorPart:An editor is a visual component within a workbench page.
				IEditorPart editorPart = workbenchPage.findEditor(editorInput);
				if(editorPart != null){//已经打开了所需的编辑器
					workbenchPage.bringToTop(editorPart);
				}else {//没有打开就打开来
					try {
						editorPart = workbenchPage.openEditor(editorInput, editorID);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void setViewToolBar() {
		// IActionBars:Used by a part to access its menu, toolbar, and
		// status line managers.
		IActionBars bars = getViewSite().getActionBars();
		// 定义工具栏
		IToolBarManager toolBarManager = bars.getToolBarManager();
		// 定义下拉菜单栏
		IMenuManager menuManager = bars.getMenuManager();
		// DrillDownAdapter:Implements a simple web style navigation
		// metaphor for a TreeViewer. Home, back,
		// and "drill into" functions are supported for the viewer,
		DrillDownAdapter drillDownAdapter = new DrillDownAdapter(treeViewer);
		// 为工具栏添加“goHome”，“goBack”，“goInto”操作
		drillDownAdapter.addNavigationActions(menuManager);
		// 为菜单栏添加“goHome”，“goBack”，“goInto”操作
		drillDownAdapter.addNavigationActions(toolBarManager);
	}

	@Override
	public void setFocus() {

	}

}
