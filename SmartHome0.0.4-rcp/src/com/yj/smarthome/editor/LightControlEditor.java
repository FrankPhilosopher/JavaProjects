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

import com.yj.smarthome.beans.LightButton;
import com.yj.smarthome.composite.LightControlComposite;
import com.yj.smarthome.interfaces.IDeviceMap;
import com.yj.smarthome.util.FileUtil;
import com.yj.smarthome.util.HttpUtil;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.util.SystemUtil;
import com.yj.smarthome.util.XmlUtil;
import com.yj.smarthome.xmlImpls.LightControlXmlImpl;

/**
 * 灯控的编辑器
 * 
 * @author yinger
 * 
 */
public class LightControlEditor extends EditorPart implements IDeviceMap {

	public static final String ID = LightControlEditor.class.getName();
//	public static final int DeviceType_ID = ProtocolUtil.DeviceType_Light;//灯控类型，这个其实没有多少用途了！
//	public LightControlEditor lce = new LightControlEditor();//灯控的编辑器

	//采用Map方式而不是list方式是因为map可以直接得到相应的设备！
	public Map<Integer, Object> deviceMap = new HashMap<Integer, Object>();

	private Composite container;

	private ViewForm viewForm;

	//这个方法一定要是public的，不然编辑器打不开！
	public LightControlEditor() {
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
		this.setPartName(SystemUtil.LIGHT_CONTROL);
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
		container.setLayout(new GridLayout(4, true));
		loadLightControls();
		container.setSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));//这句很重要的

//		// 添加编辑器的工具栏，包括了刷新 按钮
//		ToolBar toolBar = new ToolBar(viewForm, SWT.FLAT);
//		ToolBarManager toolBarManager = new ToolBarManager(toolBar);
//		toolBarManager.add(new RefreshAction());
//		toolBarManager.update(true);//必须要有

		//这部分功能是用于更新设备
//		Composite toolBar = new Composite(viewForm, SWT.FLAT);
//
//		Button button_refresh = new Button(toolBar, SWT.NONE);
//		button_refresh.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
////				System.out.println("刷新");
//				try {
//					refreshEditor();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			}
//		});
//		button_refresh.setBounds(0, 0, 152, 50);
//		button_refresh.setText("\u5237\u65B0\u8BE5\u8BBE\u5907\u7C7B\u578B");
//		button_refresh.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageManager.Refresh));//设置图片
//
//		viewForm.setTopLeft(toolBar);

	}

	//刷新editor的界面设备
	protected void refreshEditor() throws Exception {
		//首先将down下的version.xml文件复制一份到local文件夹
		FileUtil.copyFile(FileUtil.getDownVersionXmlFile(), FileUtil.LOCAL_DIRECTORY + FileUtil.VERSION_XML);
		//然后要从服务器上下载version.xml文件
		HttpUtil.downloadVersionXmlFile();
		//然后要比较相应的设备类型的版本
		int res = XmlUtil.compareVersion(ProtocolUtil.DEVICETYPE_LIGHT);
		if (res == 1) {
			HttpUtil.downloadDeviceTypeXmlFile(ProtocolUtil.DEVICETYPE_LIGHT);//要更新的话就要先下载设备文件
			loadLightControls();
		}
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
	public void loadLightControls() {
//		if (container != null) {
//			container.dispose();
//		}
//		container = new Composite(viewForm, SWT.NONE);
//		container.setLayout(new RowLayout(SWT.VERTICAL));
//		viewForm.setContent(container);

		//可以在这里开始读取本地xml文件，并得到一个hashmap
		try {
//			deviceMap = XmlUtil.loadDevices(ProtocolUtil.DeviceType_Light);
			deviceMap = XmlUtil.loadDevices(LightControlXmlImpl.getInstance());
		} catch (Exception e) {
//			e.printStackTrace();
			//抛出异常！提示！比如文件不存在！
			MessageDialog.openError(Display.getDefault().getActiveShell(), "出错啦！", e.getMessage());
		}
		//遍历map中的元素生成灯控组件
		for (Map.Entry<Integer, Object> entry : deviceMap.entrySet()) {
			LightControlComposite composite = new LightControlComposite(container, SWT.NONE, (LightButton) entry.getValue());
			deviceMap.put(entry.getKey(), composite);//注意这里，重新设置了一个map
		}
		container.layout();//如果设备太多了，要提供滚动条
	}

}
