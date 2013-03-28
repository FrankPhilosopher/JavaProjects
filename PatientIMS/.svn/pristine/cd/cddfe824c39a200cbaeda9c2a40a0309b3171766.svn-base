package com.yinger.patientims;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.yinger.patientims.util.PluginUtil;

/**
 * A perspective factory generates the initial page layout and visible action set for a page. 
 * 透视图类要实现 IPerspectiveFactory 接口
 * createInitialLayout方法可以对透视图进行布局
 */
public class Perspective implements IPerspectiveFactory {

	//这个方法用来初始化透视图的布局，可以在这里对项目中的视图进行注册
	public void createInitialLayout(IPageLayout layout) {
		//得到编辑区域id
		String editorArea = layout.getEditorArea();//Returns the special identifier for the editor area in this page layout
		
		//添加导航视图
		//String viewId, int relationship, float ratio, String refId
		layout.addView(PluginUtil.NavigatorView_ID, IPageLayout.LEFT, 0.3f, editorArea);
		
		//添加搜索视图，添加到导航视图的下方
		//An IFolderLayout is used to define the initial views within a folder. The folder itself is contained within an IPageLayout. 
		//IFolderLayout用来给已有的View添加一个folder（文件夹功能），这个folder本身就包含有IPageLayout
		IFolderLayout leftbottom = layout.createFolder("left", IPageLayout.BOTTOM, 0.4f, PluginUtil.NavigatorView_ID);
		leftbottom.addView(PluginUtil.SearchView_ID);
		
		//添加病人信息和费用信息的视图，并实现两个视图叠加的效果
		IFolderLayout rightbottom = layout.createFolder("rightbottom", IPageLayout.BOTTOM, 0.7f, editorArea);
		rightbottom.addView(PluginUtil.PatientInfoView_ID);
		rightbottom.addView(PluginUtil.ExpenseInfoView_ID);
		
	}
}
