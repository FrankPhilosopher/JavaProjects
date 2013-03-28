package com.yinger.patientims.views.viewerProvider;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 记住，这里是一个TableViewer的内容提供器，一定要是实现IStructuredContentProvider接口
 * 而不是IContentProvider，IStructuredContentProvider接口中定义了getElements方法，这个
 * 方法是要实现的！将setInput时传入的list参数转换成Object数组
 */
public class PatientInfoTableViewerContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		return ((List)inputElement).toArray();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
