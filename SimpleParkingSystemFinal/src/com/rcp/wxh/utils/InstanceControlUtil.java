package com.rcp.wxh.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;

/**
 * 利用该工具类 确保系统唯一实例 即：该系统只能启动一次。
 * 
 * @author wuxuehong 2011-11-3
 * 
 */
public class InstanceControlUtil {

	FileLock lock = null;

	// 判断该应用是否已启动
	public boolean isRunning() {
		try {
			// 获得实例标志文件
			File flagFile = new File("instance");
			// 如果不存在就新建一个
			if (!flagFile.exists())
				flagFile.createNewFile();
			// 获得文件锁
			lock = new FileOutputStream("instance").getChannel().tryLock();
			// 返回空表示文件已被运行的实例锁定
			if (lock == null)
				return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
