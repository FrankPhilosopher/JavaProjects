package com.yinger.patientims;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 这个类（激活器）继承自AbstractUIPlugin（抽象UI插件）
 * 它用来控制插件的生命周期，存储插件的全局信息
 * 它是一个单例类，是用来存储插件里其他类的一些静态功能函数的方便位置
 * 在整个插件的生命周期中，Eclipse只使用这个插件类的实例，并不创建其他的实例
 * 在通常情况下，插件类声明一个静态字段，用于引用这个唯一的实例
 * 所以在需要的时候它可以方便的被插件的各个地方所共享
 * 通常情况下这个类不需要改动
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.yinger.patientims"; //$NON-NLS-1$   //$NON-NLS-1$ 是用于国际化的，不能改动

	// The shared instance
	private static Activator plugin;  //唯一的实例
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * 得到唯一的实例
	 * Returns the shared instance
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * 根据路径返回相对于插件类的ImageDescriptor，这个方法很重要
	 * 很多时候设置图片或者ImageDescriptor都是调用这个方法
	 * 有一个好处就是这种方法是采用相对路径得到的
	 * 注意path的写法，如果是在项目下的icons目录中，应该是  /icons/XX.ico
	 * Returns an image descriptor for the image file at the given plug-in relative path
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
//		AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, imageFilePath)
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
