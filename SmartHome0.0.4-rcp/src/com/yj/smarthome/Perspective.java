package com.yj.smarthome;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public static final String ID = Perspective.class.getName();

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);//这里还是显示为好！
//		layout.setFixed(true);
//		layout.addStandaloneView(NavigatorView.ID, true, IPageLayout.LEFT, 0.3f, editorArea);
	}

}
