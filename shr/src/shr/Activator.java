package shr;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import util.ProjectUtil;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "shr"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private static ApplicationContext applicationContext;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		initialApplicationContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * ��ʼ��applicationcontext������Spring���
	 */
	private void initialApplicationContext() {
		// �õ���ǰ�̵߳��������
		ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
		try {
			// �ɲ�����������������spring
			Thread.currentThread().setContextClassLoader(getDefault().getClass().getClassLoader());
			System.out.println(ProjectUtil.toFullPath("config/applicationContext.xml"));//��ӡspring�����ļ���·��
			applicationContext = new FileSystemXmlApplicationContext(
					ProjectUtil.toFullPath("config/applicationContext.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �ָ���ǰ�̵߳��������
			Thread.currentThread().setContextClassLoader(oldLoader);
		}
	}

	/**
	 * �õ�applicationcontext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
