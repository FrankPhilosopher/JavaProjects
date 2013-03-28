package com.yinger.patientims;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
/**
 * Public base class for configuring the workbench. 
 * 该类继承了WorkbenchAdvisor类，在主程序Application类中引用了ApplicationWorkbenchAdvisor类
 * WorkbenchAdvisor类主要用于配置工作台，它告诉工作台如何显示，显示什么信息
 * WorkbenchAdvisor类主要初始化默认的透视图和使用WorkbenchWindowAdvisor类
 * 
 * 重要方法：
 * 1.initialize(IWorkbenchConfigurer configurer)：在工作台启动之前进行初始化
 * 2.openWindows()：用于在启动时打开工作台窗体
 * 3.postShutdown()：用于在所有窗口关闭之后，关闭工作台之前调用，用来保存当前的应用程序的状态，清除初始化方法创建的内容
 * 4.preShutdown()：工作台将要关闭之前
 * 5.postStartup()：用于在工作台窗口关闭之后，在主循环事件运行之前调用
 * 6.preStartup()：用于在初始化结束之后，第一个窗口打开之前调用
 * 7.getDefaultPageInput()：返回默认的输入
 * 8.getInitialWindowPerspectiveId()：用于为新的工作台窗口返回透视图ID
 * 9.getMainPreferencePageId()：返回首选项ID
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "com.yinger.patientims.perspective"; //$NON-NLS-1$

    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}
}
