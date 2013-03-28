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

import com.yj.smarthome.beans.DoorWindow;
import com.yj.smarthome.composite.DoorWindowControlComposite;
import com.yj.smarthome.interfaces.IDeviceMap;
import com.yj.smarthome.util.SystemUtil;
import com.yj.smarthome.util.XmlUtil;
import com.yj.smarthome.xmlImpls.DoorControlXmlImpl;

/**
 * �Ŵ����Ƶı༭��
 * 
 * @author yinger
 * 
 */
public class DoorControlEditor extends EditorPart implements IDeviceMap {

	public static final String ID = DoorControlEditor.class.getName();
//	public static final int DeviceType_ID = ProtocolUtil.DeviceType_Door;//�Ŵ����Ƶı༭��

	public Map<Integer, Object> deviceMap = new HashMap<Integer, Object>();

	private ViewForm viewForm;

	private Composite container;

	public DoorControlEditor() {
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
		this.setPartName(SystemUtil.DOOR_CONTROL);
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
//		layoutDeviceMap();

		//���й����������
		ScrolledComposite scrolledComposite = new ScrolledComposite(viewForm, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scrolledComposite.setExpandVertical(false);
		scrolledComposite.setExpandHorizontal(false);
		viewForm.setContent(scrolledComposite);
		container = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(container);
//				container.setLayout(new RowLayout(SWT.VERTICAL));//��һ�����ʹ�õ��������кܶ���龰ģʽʱ���Ի�����ʾ
		//�����VERTICAL�������ֱ��������������Ǻ��ŵĹ�����
		container.setLayout(new GridLayout(4, true));
		loadDoorControls();
		container.setSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));//������Ҫ��
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

	//�����豸map
	public void loadDoorControls() {
//		if (container != null) {
//			container.dispose();
//		}
//		container = new Composite(viewForm, SWT.NONE);
//		container.setLayout(new RowLayout(SWT.VERTICAL));
//		viewForm.setContent(container);

		//���������￪ʼ��ȡ����xml�ļ������õ�һ��hashmap
		try {
//			deviceMap = XmlUtil.loadDevices(ProtocolUtil.DeviceType_Door);
			deviceMap = XmlUtil.loadDevices(DoorControlXmlImpl.getInstance());
		} catch (Exception e) {
			//			e.printStackTrace();
			//�׳��쳣����ʾ�������ļ������ڣ�
			MessageDialog.openError(Display.getDefault().getActiveShell(), "��������", e.getMessage());
		}
		//����map�е�Ԫ�����ɵƿ����
		for (Map.Entry<Integer, Object> entry : deviceMap.entrySet()) {
			DoorWindowControlComposite composite = new DoorWindowControlComposite(container, SWT.NONE, (DoorWindow) entry.getValue());
			deviceMap.put(entry.getKey(), composite);//ע���������������һ��map
		}
		container.layout();//����豸̫���ˣ�Ҫ�ṩ������
	}

}
