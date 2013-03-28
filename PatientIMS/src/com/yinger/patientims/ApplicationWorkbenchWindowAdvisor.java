package com.yinger.patientims;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.util.PrefUtil;
/**
 * Public base class for configuring a workbench window. 
 * 该类继承了WorkbenchWindowAdvisor类，主要是对工作台窗口进行控制
 * 包括状态栏，工具栏，透视图栏，快速试图栏，窗口尺寸，标题等等
 * 该类还有一个重要的作用就是创建ActionBarAdvisor类
 * 这个类的方法在整个工作台的生命周期中起着重要作用
 *
 * 重要方法：
 * 1.createEmptyWindowContents（Composite composite）：返回当窗口没有页面显示时要显示的控件
 * 2.createWindowContents（Shell shell）：创建窗口内容
 * 3.getWindowConfigurer（）：返回工作台窗口配置
 * 4.openIntro（）：打开初始内容
 * 5.postWindowClose（）：在窗口关闭之后调用
 * 6.postWindowCreate（）：在窗口创建以后，打开之前调用 或者 窗口恢复到保存状态后，在执行postWindowRestor方法之后调用
 * 7.postWindowRestor（）：在窗口被恢复到保存状态之后，打开之前调用
 * 8.perWindowOpen（）：在窗口被打开之前调用
 * 9.preWindowShellClose（）：在窗口的Shell被关闭之前调用
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	//这个构造方法在ApplicationWorkbenchAdvisor中调用的
    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    //这个方法是在窗口打开之前调用的，用来设置窗口的内容显示情况
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        //设置窗体的标题
        configurer.setTitle("Hospital Management System"); //$NON-NLS-1$
        //设置窗体的大小
        configurer.setInitialSize(new Point(800, 600));
        //设置透视图栏
        configurer.setShowMenuBar(true);
        //设置工具栏
        configurer.setShowCoolBar(true);
        //设置状态线
        configurer.setShowStatusLine(true);
        //设置快速视图栏
        configurer.setShowFastViewBars(true);       
        
        //定制应用程序的外观
        IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
        //设置选项卡的样式，不是矩形的边框，而是弧形的
        preferenceStore.setValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
        //设置透视图按钮的位置，默认是左上角，改为放置在右上角
        preferenceStore.setValue(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR, IWorkbenchPreferenceConstants.TOP_RIGHT);
        
    }
}
