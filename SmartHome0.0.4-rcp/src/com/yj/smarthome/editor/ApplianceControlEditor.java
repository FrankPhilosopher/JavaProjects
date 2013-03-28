package com.yj.smarthome.editor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.yj.smarthome.beans.Appliance;
import com.yj.smarthome.composite.ApplianceControlComposite;
import com.yj.smarthome.interfaces.IDeviceMap;
import com.yj.smarthome.util.SystemUtil;
import com.yj.smarthome.util.XmlUtil;
import com.yj.smarthome.xmlImpls.ApplianceControlXmlImpl;

/**
 * 家电控制的编辑器
 * 
 * @author yinger
 * 
 */
public class ApplianceControlEditor extends EditorPart implements IDeviceMap {

	public static final String ID = ApplianceControlEditor.class.getName();
//	public static final int DeviceType_ID = ProtocolUtil.DeviceType_Door;//门窗控制的编辑器

	public Map<Integer, Object> deviceMap = new HashMap<Integer, Object>();

	private ViewForm viewForm;

	private Composite container;

	public ApplianceControlEditor() {
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
//		this.setPartName(SystemUtil.DeviceNameArray[ProtocolUtil.DeviceType_Door]);
		this.setPartName(SystemUtil.APPLIANCE_CONTROL);
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
		container.setLayout(new GridLayout(2, true));
		loadApplianceControls();
		container.setSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));//这句很重要的
	}

	@Override
	public void setFocus() {
	}

	@Override
	public Map getDeviceMap() {
		return deviceMap;
	}

	@Override
	public void setDeviceMap(Map map) {
		this.deviceMap = map;
	}

	//布置设备map
	public void loadApplianceControls() {
//		if (container != null) {
//			container.dispose();
//		}
//		container = new Composite(viewForm, SWT.NONE);
//		container.setLayout(new RowLayout(SWT.VERTICAL));
//		viewForm.setContent(container);

		//可以在这里开始读取本地xml文件，并得到一个hashmap
		try {
			deviceMap = XmlUtil.loadDevices(ApplianceControlXmlImpl.getInstance());
		} catch (Exception e) {
			//e.printStackTrace();
			//抛出异常！提示！比如文件不存在！
			MessageDialog.openError(Display.getDefault().getActiveShell(), "出错啦！", e.getMessage());
		}
		//遍历map中的元素生成灯控组件
		for (Map.Entry<Integer, Object> entry : deviceMap.entrySet()) {
			ApplianceControlComposite composite = new ApplianceControlComposite(container, SWT.NONE, (Appliance) entry.getValue());
			deviceMap.put(entry.getKey(), composite);//注意这里，重新设置了一个map
		}
		container.layout();//如果设备太多了，要提供滚动条
	}

}
