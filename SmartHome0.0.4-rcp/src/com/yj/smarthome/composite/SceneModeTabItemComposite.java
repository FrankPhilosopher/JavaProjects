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
 * 情景模式设置对话框中的一个TabItem组件
 * 
 * @author yinger
 * 
 */
public class SceneModeTabItemComposite extends Composite {

	private int type;//设备类型
	private SceneMode sceneMode;//情景模式
	private Composite content;//内容区域

	public SceneModeTabItemComposite(Composite parent, int style, SceneMode sceneMode, int type) {
		super(parent, style);
		this.sceneMode = sceneMode;//传入进来的scenemode肯定不是null，一定是有名称，但是不一定有items
		this.type = type;
		try {
			initUI();
		} catch (Exception e) {
			e.printStackTrace();//TODO：异常！这里比较特殊
		}
	}

	//初始化界面
	private void initUI() throws Exception {
//		setLayout(new GridLayout(1, false));
		setLayout(new FillLayout());
		//带有滚动条的组件
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
//		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scrolledComposite.setExpandVertical(false);
		scrolledComposite.setExpandHorizontal(false);
		scrolledComposite.setLayout(new GridLayout(1, false));
		content = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(content);
		content.setLayoutData(new GridData(GridData.FILL_BOTH));
		content.setLayout(new GridLayout(1, false));
		creatContent();//创建content区域
		content.layout();//在动态的改变组件之后，最好是加上layout！
		content.setSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	//创建content区域
	private void creatContent() throws Exception {
		//这个label显得多余，不用了
//		Label cap = new Label(content, SWT.LEFT);
//		cap.setFont(ResourceManager.getFont("Verdana", 14, SWT.BOLD, false, false));
//		cap.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
//		cap.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
//		cap.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		List<SceneModeItem> deviceItems = null;
		switch (type) {//根据设备类型的不同，从本地文件中加载不同设备类型的所有设备
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
		if (deviceItems == null || deviceItems.size() <= 0) {//如果本地没有该类型的设备，那么就返回
			return;
		}
		System.out.println(deviceItems.size());
		//加载完成之后，就要对应每一个设备生成一个组件，用于选择它的命令
		SceneModeItem item = null;
		for (int i = 0, size = deviceItems.size(); i < size; i++) {
			item = deviceItems.get(i);
			SceneModeItem sceneModeItem = null;//这个必须要放在循环体内部，每次都要重新清理
			//判断该情景模式下是否具有这个item，如果有的话就得到它，并把它传入到下一个组件的构造函数中
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
