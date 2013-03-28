package com.yj.smarthome.view;

import java.util.List;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.yj.smarthome.editor.DoorControlEditor;
import com.yj.smarthome.editor.LightControlEditor;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.util.SystemUtil;
import com.yj.smarthome.view.entities.DeviceTypeEntity;
import com.yj.smarthome.view.entities.DeviceTypeEntityFactory;

/**
 * 导航视图，内部是一个TableViewer组件
 * 
 * @author yinger
 * 
 */
public class NavigatorView extends ViewPart {

	public static final String ID = NavigatorView.class.getName();

	private TableViewer viewer;//使用 TableViewer

	class ViewContentProvider implements IStructuredContentProvider {
		@Override
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		//这个方法很重要，要将元素还原成原始的对象
		@Override
		public Object[] getElements(Object obj) {
			return ((List) obj).toArray();
		}
	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return ((DeviceTypeEntity) obj).getName();
		}

		// 暂时使用相同的图片
		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		@Override
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());

		// Provide the input to the ContentProvider
//		viewer.setInput(new String[] {"One", "Two", "Three"});
		viewer.setInput(DeviceTypeEntityFactory.createDeviceTypeEntities());

		hookDoubleClickAction();
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				// 得到选中的项 
				ISelection selection = viewer.getSelection();
				Object object = ((IStructuredSelection) selection).getFirstElement();
				// 再将对象转为实际的节点对象
				DeviceTypeEntity element = (DeviceTypeEntity) object;
				// 得到该对象的editorInput
				IEditorInput editorInput = element.getInput();
				// 得到当前工作台的page
				IWorkbenchPage workbenchPage = getViewSite().getPage();
				// 通过editorinput来找到editor
				IEditorPart editorPart = workbenchPage.findEditor(editorInput);
				if (editorPart != null) {//已经打开了所需的编辑器
					workbenchPage.bringToTop(editorPart);
				} else {//没有打开就打开来
					try {
						String editorID = null;

						//TODO：这里也是存在着遍历循环的问题！
						if (element.getName().equals(SystemUtil.DEVICENAMEARRAY[ProtocolUtil.DEVICETYPE_LIGHT])) {//灯光
							editorID = LightControlEditor.ID;
						} else if (element.getName().equals(SystemUtil.DEVICENAMEARRAY[ProtocolUtil.DEVICETYPE_DOOR])) {//门窗
							editorID = DoorControlEditor.ID;
						}

						editorPart = workbenchPage.openEditor(editorInput, editorID);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void setFocus() {
//		viewer.getControl().setFocus();
	}
}