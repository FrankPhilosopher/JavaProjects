package com.yj.smarthome.composite;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.yj.smarthome.beans.SceneMode;
import com.yj.smarthome.beans.SceneModeItem;
import com.yj.smarthome.util.ProtocolUtil;
import com.yj.smarthome.xmlImpls.ApplianceControlXmlImpl;
import com.yj.smarthome.xmlImpls.DoorControlXmlImpl;
import com.yj.smarthome.xmlImpls.LightControlXmlImpl;
import com.yj.smarthome.xmlImpls.SecurityControlXmlImpl;

/**
 * �龰ģʽ���öԻ����е�һ��TabItem���
 * 
 * @author yinger
 * 
 */
public class SceneModeTabItemComposite extends Composite {

	private int type;//�豸����
	private SceneMode sceneMode;//�龰ģʽ
	private Composite content;//��������

	public SceneModeTabItemComposite(Composite parent, int style, SceneMode sceneMode, int type) {
		super(parent, style);
		this.sceneMode = sceneMode;//���������scenemode�϶�����null��һ���������ƣ����ǲ�һ����items
		this.type = type;
		try {
			initUI();
		} catch (Exception e) {
			e.printStackTrace();//TODO���쳣������Ƚ�����
		}
	}

	//��ʼ������
	private void initUI() throws Exception {
//		setLayout(new GridLayout(1, false));
		setLayout(new FillLayout());
		//���й����������
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
//		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scrolledComposite.setExpandVertical(false);
		scrolledComposite.setExpandHorizontal(false);
		scrolledComposite.setLayout(new GridLayout(1, false));
		content = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(content);
		content.setLayoutData(new GridData(GridData.FILL_BOTH));
		content.setLayout(new GridLayout(1, false));
		creatContent();//����content����
		content.layout();//�ڶ�̬�ĸı����֮������Ǽ���layout��
		content.setSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	//����content����
	private void creatContent() throws Exception {
		//���label�Եö��࣬������
//		Label cap = new Label(content, SWT.LEFT);
//		cap.setFont(ResourceManager.getFont("Verdana", 14, SWT.BOLD, false, false));
//		cap.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
//		cap.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
//		cap.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		List<SceneModeItem> deviceItems = null;
		switch (type) {//�����豸���͵Ĳ�ͬ���ӱ����ļ��м��ز�ͬ�豸���͵������豸
		case ProtocolUtil.DEVICETYPE_LIGHT:
//			cap.setText(SystemUtil.LightControl + "\n");
			deviceItems = LightControlXmlImpl.getInstance().getLightItems(type);
			break;
		case ProtocolUtil.DEVICETYPE_DOOR:
//			cap.setText(SystemUtil.DoorControl + "\n");
			deviceItems = DoorControlXmlImpl.getInstance().getDoorItems(type);
			break;
		case ProtocolUtil.DEVICETYPE_APPLIANCE:
//			cap.setText(SystemUtil.ApplianceControl + "\n");
			deviceItems = ApplianceControlXmlImpl.getInstance().getApplianceItems(type);
			break;
		case ProtocolUtil.DEVICETYPE_SECURITY:
			deviceItems = SecurityControlXmlImpl.getInstance().getSecurityItems(type);
			break;
		default:
			break;
		}

//		List<SceneModeItem> lightItems = LightControlXmlImpl.getInstance().getLightItems(type);
		if (deviceItems == null || deviceItems.size() <= 0) {//�������û�и����͵��豸����ô�ͷ���
			return;
		}
		System.out.println(deviceItems.size());
		//�������֮�󣬾�Ҫ��Ӧÿһ���豸����һ�����������ѡ����������
		SceneModeItem item = null;
		for (int i = 0, size = deviceItems.size(); i < size; i++) {
			item = deviceItems.get(i);
			SceneModeItem sceneModeItem = null;//�������Ҫ����ѭ�����ڲ���ÿ�ζ�Ҫ��������
			//�жϸ��龰ģʽ���Ƿ�������item������еĻ��͵õ��������������뵽��һ������Ĺ��캯����
			for (SceneModeItem sceneitem : sceneMode.getItems()) {
				if (sceneitem.equals(item)) {
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					sceneModeItem = sceneitem;
					break;
				}
			}
			Composite composite = new SceneModeItemComposite(content, SWT.NONE, item, sceneModeItem, sceneMode);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
	}
}
