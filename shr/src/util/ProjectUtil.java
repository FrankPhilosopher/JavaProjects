package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import shr.Activator;

/**
 * ���ڲ����Ŀ�ͷǲ����Ŀ���ṩ����ͨ�õķ����ӿ�
 * 
 * @author yinger
 */
public class ProjectUtil {

	private static AbstractUIPlugin plugin = Activator.getDefault();

	private ProjectUtil() {
	}

	/**
	 * ����ָ����path�õ�������
	 */
	public static InputStream getInputStream(String path) {
		URL url = getURL(path);
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����ָ����path�õ�URL
	 */
	public static URL getURL(String path) {
		if (isPlugin())
			return FileLocator.find(plugin.getBundle(), new Path(path), null);
		else
			try {
				return new URL("file:" + path);
			} catch (MalformedURLException e) {
				throw new RuntimeException(path + " is error", e);
			}
	}

	/**
	 * �ж��Ƿ��ǲ������
	 */
	private static boolean isPlugin() {
		return plugin != null;
	}

	/**
	 * �õ�������·��
	 */
	public static String toFullPath(String path) {
		if (isPlugin()) {
			try {
				return FileLocator.toFileURL(ProjectUtil.getURL(path)).getPath();
			} catch (IOException e) {
				throw new RuntimeException(path + " toFullPath is fault", e);
			}
		} else {
			return path;
		}
	}

}