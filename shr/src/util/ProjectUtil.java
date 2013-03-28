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
 * 用于插件项目和非插件项目，提供两者通用的方法接口
 * 
 * @author yinger
 */
public class ProjectUtil {

	private static AbstractUIPlugin plugin = Activator.getDefault();

	private ProjectUtil() {
	}

	/**
	 * 根据指定的path得到输入流
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
	 * 根据指定的path得到URL
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
	 * 判断是否是插件运行
	 */
	private static boolean isPlugin() {
		return plugin != null;
	}

	/**
	 * 得到完整的路径
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