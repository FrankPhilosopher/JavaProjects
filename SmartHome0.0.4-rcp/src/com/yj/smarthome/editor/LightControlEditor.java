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
 * �ƿصı༭��
 * 
 * @author yinger
 * 
 */
public class LightControlEditor extends EditorPart implements IDeviceMap {

	public static final String ID = LightControlEditor.class.getName();
//	public static final int DeviceType_ID = ProtocolUtil.DeviceType_Light;//�ƿ����ͣ������ʵû�ж�����;�ˣ�
//	public LightControlEditor lce = new LightControlEditor();//�ƿصı༭��

	//����Map��ʽ������list��ʽ����Ϊmap����ֱ�ӵõ���Ӧ���豸��
	public Map<Integer, Object> deviceMap = new HashMap<Integer, Object>();

	private Composite container;

	private ViewForm viewForm;

	//�������һ��Ҫ��public�ģ���Ȼ�༭���򲻿���
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
		container.setLayout(new GridLayout(4, true));
		loadLightControls();
		container.setSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));//������Ҫ��

//		// ��ӱ༭���Ĺ�������������ˢ�� ��ť
//		ToolBar toolBar = new ToolBar(viewForm, SWT.FLAT);
//		ToolBarManager toolBarManager = new ToolBarManager(toolBar);
//		toolBarManager.add(new RefreshAction());
//		toolBarManager.update(true);//����Ҫ��

		//�ⲿ�ֹ��������ڸ����豸
//		Composite toolBar = new Composite(viewForm, SWT.FLAT);
//
//		Button button_refresh = new Button(toolBar, SWT.NONE);
//		button_refresh.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
////				System.out.println("ˢ��");
//				try {
//					refreshEditor();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			}
//		});
//		button_refresh.setBounds(0, 0, 152, 50);
//		button_refresh.setText("\u5237\u65B0\u8BE5\u8BBE\u5907\u7C7B\u578B");
//		button_refresh.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, ImageManager.Refresh));//����ͼƬ
//
//		viewForm.setTopLeft(toolBar);

	}

	//ˢ��editor�Ľ����豸
	protected void refreshEditor() throws Exception {
		//���Ƚ�down�µ�version.xml�ļ�����һ�ݵ�local�ļ���
		FileUtil.copyFile(FileUtil.getDownVersionXmlFile(), FileUtil.LOCAL_DIRECTORY + FileUtil.VERSION_XML);
		//Ȼ��Ҫ�ӷ�����������version.xml�ļ�
		HttpUtil.downloadVersionXmlFile();
		//Ȼ��Ҫ�Ƚ���Ӧ���豸���͵İ汾
		int res = XmlUtil.compareVersion(ProtocolUtil.DEVICETYPE_LIGHT);
		if (res == 1) {
			HttpUtil.downloadDeviceTypeXmlFile(ProtocolUtil.DEVICETYPE_LIGHT);//Ҫ���µĻ���Ҫ�������豸�ļ�
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

	//�����豸map
	public void loadLightControls() {
//		if (container != null) {
//			container.dispose();
//		}
//		container = new Composite(viewForm, SWT.NONE);
//		container.setLayout(new RowLayout(SWT.VERTICAL));
//		viewForm.setContent(container);

		//���������￪ʼ��ȡ����xml�ļ������õ�һ��hashmap
		try {
//			deviceMap = XmlUtil.loadDevices(ProtocolUtil.DeviceType_Light);
			deviceMap = XmlUtil.loadDevices(LightControlXmlImpl.getInstance());
		} catch (Exception e) {
//			e.printStackTrace();
			//�׳��쳣����ʾ�������ļ������ڣ�
			MessageDialog.openError(Display.getDefault().getActiveShell(), "��������", e.getMessage());
		}
		//����map�е�Ԫ�����ɵƿ����
		for (Map.Entry<Integer, Object> entry : deviceMap.entrySet()) {
			LightControlComposite composite = new LightControlComposite(container, SWT.NONE, (LightButton) entry.getValue());
			deviceMap.put(entry.getKey(), composite);//ע���������������һ��map
		}
		container.layout();//����豸̫���ˣ�Ҫ�ṩ������
	}

}
