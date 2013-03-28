package com.yinger.patientims.views.viewerProvider;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.yinger.patientims.navigator.NavigatorEntityElement;

/**
 * 导航视图中TreeViewer的标签提供器 
 * 
 * 标签提供器提供的是用于显示的文本或者图片
 * 注意：TreeViewer的标签提供器最好是实现ILabelProvider，而不是IBaseLabelProvider
 * 
 * 标签提供器可以帮助保存图标资源，因为它可以确保图标的相同实例用于查看器中所有的相似类型
 */
public class NavigatorTreeViewerLabelProvider implements ILabelProvider {
	
	public NavigatorTreeViewerLabelProvider(){
		//最好是写上一个默认的构造器
	}

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

	//重点：学会得到系统提供的图片的方法：PlatformUI.getWorkbench().getSharedImages().getImage( ISharedImages.XX)
	@Override//要显示的图片
	public Image getImage(Object element) {
		String image1 = ISharedImages.IMG_OBJ_FOLDER;
		String image2 = ISharedImages.IMG_OBJ_FILE;
		NavigatorEntityElement entityElement = (NavigatorEntityElement) element;
		if(entityElement.hasChildren()){
			return PlatformUI.getWorkbench().getSharedImages().getImage(image1);
		}else{
			return PlatformUI.getWorkbench().getSharedImages().getImage(image2);
		}
	}

	@Override//要显示的文本
	public String getText(Object element) {
		NavigatorEntityElement entityElement = (NavigatorEntityElement) element;
		return entityElement.getName();
	}

}
