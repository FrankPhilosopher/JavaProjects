package cn.edu.csu.dbhospital.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 自动登录控制工具类
 * 
 * @author hjw
 * 
 */

public class AutomLoginUtil {

	/* user中 login -1表示无 0-表示记住密码 1-表示自动登录 */
	private String filename = "login.cfg";

	public static final int NoLogin = -1;
	public static final int RemPwd = 0;
	public static final int AutoLogin = 1;

	// 从指定的文件中读取登录用户的对象
	public Object readObject() throws Exception {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
			return null;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Object o = ois.readObject();
			return o;
		} catch (Exception e) {
			throw e;
		}
	}

	// 将对象写入指定文件
	public void writeObject(Object obj) throws Exception {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
		} catch (Exception e) {
			throw e;
		}
	}

}
