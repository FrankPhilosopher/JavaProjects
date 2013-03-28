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
 * ������ͼ���ڲ���һ��TableViewer���
 * 
 * @author yinger
 * 
 */
public class NavigatorView extends ViewPart {

	public static final String ID = NavigatorView.class.getName();

	private TableViewer viewer;//ʹ�� TableViewer

	class ViewContentProvider implements IStructuredContentProvider {
		@Override
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		//�����������Ҫ��Ҫ��Ԫ�ػ�ԭ��ԭʼ�Ķ���
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

		// ��ʱʹ����ͬ��ͼƬ
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
				// �õ�ѡ�е��� 
				ISelection selection = viewer.getSelection();
				Object object = ((IStructuredSelection) selection).getFirstElement();
				// �ٽ�����תΪʵ�ʵĽڵ����
				DeviceTypeEntity element = (DeviceTypeEntity) object;
				// �õ��ö����editorInput
				IEditorInput editorInput = element.getInput();
				// �õ���ǰ����̨��page
				IWorkbenchPage workbenchPage = getViewSite().getPage();
				// ͨ��editorinput���ҵ�editor
				IEditorPart editorPart = workbenchPage.findEditor(editorInput);
				if (editorPart != null) {//�Ѿ���������ı༭��
					workbenchPage.bringToTop(editorPart);
				} else {//û�д򿪾ʹ���
					try {
						String editorID = null;

						//TODO������Ҳ�Ǵ����ű���ѭ�������⣡
						if (element.getName().equals(SystemUtil.DEVICENAMEARRAY[ProtocolUtil.DEVICETYPE_LIGHT])) {//�ƹ�
							editorID = LightControlEditor.ID;
						} else if (element.getName().equals(SystemUtil.DEVICENAMEARRAY[ProtocolUtil.DEVICETYPE_DOOR])) {//�Ŵ�
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