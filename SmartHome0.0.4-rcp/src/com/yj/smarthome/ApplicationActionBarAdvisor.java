package com.yj.smarthome;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.yj.smarthome.action.ApplianceControlAction;
import com.yj.smarthome.action.CameraMonitorAction;
import com.yj.smarthome.action.DoorControlAction;
import com.yj.smarthome.action.IpCameraMonitorAction;
import com.yj.smarthome.action.LightControlAction;
import com.yj.smarthome.action.RefreshDeviceAction;
import com.yj.smarthome.action.SceneModeAction;
import com.yj.smarthome.action.SecurityControlAction;
import com.yj.smarthome.action.SensorControlAction;
import com.yj.smarthome.util.SystemUtil;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of the actions added to a workbench window.
 * Each window will be populated with new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private RefreshDeviceAction refreshDeviceAction;
	private LightControlAction lightControlAction;
	private DoorControlAction doorControlAction;
	private ApplianceControlAction applianceControlAction;
	private SecurityControlAction securityControlAction;
	private CameraMonitorAction cameraMonitorAction;
	private IpCameraMonitorAction ipCameraMonitorAction;
	private SceneModeAction sceneModeAction;
	private SensorControlAction sensorControlAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	@Override
	protected void makeActions(IWorkbenchWindow window) {
		refreshDeviceAction = new RefreshDeviceAction(window);
		lightControlAction = new LightControlAction(window);
		doorControlAction = new DoorControlAction(window);
		applianceControlAction = new ApplianceControlAction(window);
		securityControlAction = new SecurityControlAction(window);
		cameraMonitorAction = new CameraMonitorAction(window);
		ipCameraMonitorAction = new IpCameraMonitorAction(window);
		sceneModeAction = new SceneModeAction(window);
		sensorControlAction = new SensorControlAction(window);
	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
//		coolBar.setLockLayout(false);//没有用，左边还是有那个虚线
		IToolBarManager toolbar = new ToolBarManager(coolBar.getStyle());
		// 在这个toolbar上面添加一个操作按钮
		toolbar.add(lightControlAction);
		toolbar.add(doorControlAction);
		toolbar.add(applianceControlAction);
		toolbar.add(securityControlAction);
		toolbar.add(cameraMonitorAction);
		toolbar.add(ipCameraMonitorAction);
		toolbar.add(sceneModeAction);
		toolbar.add(sensorControlAction);
//		toolbar.add(refreshDeviceAction);
		// 把这个toolbar添加到coolbar上面
		coolBar.add(toolbar);
//		coolBar.getStyle();
	}

	/* 初始化状态栏 */
	@Override
	protected void fillStatusLine(IStatusLineManager statusLine) {
		super.fillStatusLine(statusLine);
		// 注意这里创建了一个item，所以会将状态栏分出一部分专门显示该信息
		StatusLineContributionItem statusItem = new StatusLineContributionItem("msg");
//		statusLine.getProgressMonitor();// Returns a progress monitor which reports progress in the status line.
		statusItem.setText("智能家居欢迎您：" + SystemUtil.LOGIN_USER.getName());
		statusLine.add(statusItem);
	}

}
