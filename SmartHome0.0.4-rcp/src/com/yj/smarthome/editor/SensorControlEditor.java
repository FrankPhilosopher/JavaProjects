package com.yj.smarthome.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.yj.smarthome.util.SystemUtil;

/**
 * �������ı༭��
 * 
 * @author yinger
 * 
 */
public class SensorControlEditor extends EditorPart {

	public static final String ID = SensorControlEditor.class.getName();

	private Composite container;

	private ViewForm viewForm;

	//�������һ��Ҫ��public�ģ���Ȼ�༭���򲻿���
	public SensorControlEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
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
	public void setFocus() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.setSite(site);
		this.setInput(input);
		this.setPartName(SystemUtil.SENSOR_CONTROL);
//		this.setPartName(SystemUtil.DeviceNameArray[ProtocolUtil.DeviceType_Light]);//����title
//		this.setTitleImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageManager.Light_Editor));
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());//VERTICAL ��ֱ����
		// ���ȴ���һ��ViewForm�����������ڿؼ��Ĳ���
		viewForm = new ViewForm(parent, SWT.NONE);
		// ����
		viewForm.setLayout(new FillLayout());

		//���й����������
		ScrolledComposite scrolledComposite = new ScrolledComposite(viewForm, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scrolledComposite.setExpandVertical(false);
		scrolledComposite.setExpandHorizontal(false);
		viewForm.setContent(scrolledComposite);
		container = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(container);
//		container.setLayout(new RowLayout(SWT.VERTICAL));//��һ�����ʹ�õ��������кܶ���龰ģʽʱ���Ի�����ʾ
		//�����VERTICAL�������ֱ��������������Ǻ��ŵĹ�����
		container.setLayout(new GridLayout(2, true));
		createContainer();
		container.setSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));//������Ҫ��
	}

	//������������
	private void createContainer() {

	}

}
