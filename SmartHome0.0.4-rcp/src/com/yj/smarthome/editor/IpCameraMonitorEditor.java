package com.yj.smarthome.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.yj.smarthome.camera.IpCameraMonitorComposite;
import com.yj.smarthome.util.SystemUtil;

/**
 * IP����ͷ��Ƶ��صı༭��
 * 
 * @author yinger
 * 
 */
public class IpCameraMonitorEditor extends EditorPart {

	public static final String ID = IpCameraMonitorEditor.class.getName();

	private ViewForm viewForm;

	private Composite container;

	private IpCameraMonitorComposite cameraMonitorComposite;

	public IpCameraMonitorEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.setSite(site);
		this.setInput(input);
		this.setPartName(SystemUtil.IPCAMERAMONITOR_CONTROL);
	}

	@Override
	public boolean isDirty() {

		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {

		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());//VERTICAL ��ֱ����
		// ���ȴ���һ��ViewForm�����������ڿؼ��Ĳ���
		viewForm = new ViewForm(parent, SWT.NONE);
		// ����
		viewForm.setLayout(new FillLayout());
		// ������Ƶ������
		createVedioFrame();
	}

	@Override
	public void setFocus() {
	}

	// ������Ƶ������
	public void createVedioFrame() {
		if (container != null) {
			container.dispose();
		}
		container = new Composite(viewForm, SWT.NONE);
		viewForm.setContent(container);
//		container.setLayout(new RowLayout(SWT.VERTICAL));
		container.setLayout(new GridLayout(1, false));

		cameraMonitorComposite = new IpCameraMonitorComposite(container, SWT.BORDER);
//		demo.setLayoutData(new RowData(583, 463));
		cameraMonitorComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

//		container.layout();//����豸̫���ˣ�Ҫ�ṩ������
	}

//	public void setIP(String ip) {
//		cameraMonitorComposite.setIp(ip);
//	}

}
