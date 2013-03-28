package mydip;

import mydip.views.NavigatorView;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		// layout.setEditorAreaVisible(false);//设置可编辑区域不显示！不要关闭，关闭了的话一开始显示就只有左侧的导航栏，并且是扩展整个屏幕的！
//		layout.addView(NavigatorView.ID, IPageLayout.LEFT, 0.3f, editorArea);// 将导航视图添加到透视图中
		//将导航视图定义成StandaloneView更好，不太会出现问题
		layout.addStandaloneView(NavigatorView.ID, true, IPageLayout.LEFT, 0.3f, editorArea);
	}
}
