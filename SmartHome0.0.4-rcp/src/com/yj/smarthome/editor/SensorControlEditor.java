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
 * 传感器的编辑器
 * 
 * @author yinger
 * 
 */
public class SensorControlEditor extends EditorPart {

	public static final String ID = SensorControlEditor.class.getName();

	private Composite container;

	private ViewForm viewForm;

	//这个方法一定要是public的，不然编辑器打不开！
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
//		this.setPartName(SystemUtil.DeviceNameArray[ProtocolUtil.DeviceType_Light]);//设置title
//		this.setTitleImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageManager.Light_Editor));
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());//VERTICAL 竖直排列
		// 首先创建一个ViewForm对象，它方便于控件的布局
		viewForm = new ViewForm(parent, SWT.NONE);
		// 布局
		viewForm.setLayout(new FillLayout());

		//带有滚动条的组件
		ScrolledComposite scrolledComposite = new ScrolledComposite(viewForm, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scrolledComposite.setExpandVertical(false);
		scrolledComposite.setExpandHorizontal(false);
		viewForm.setContent(scrolledComposite);
		container = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(container);
//		container.setLayout(new RowLayout(SWT.VERTICAL));//这一句可以使得当界面中有很多个情景模式时可以换行显示
		//如果是VERTICAL则出现竖直滚动条，否则就是横着的滚动条
		container.setLayout(new GridLayout(2, true));
		createContainer();
		container.setSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));//这句很重要的
	}

	//创建内容区域
	private void createContainer() {

	}

}
