package com.yj.smarthome.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.yj.smarthome.beans.User;

/**
 * 自动登录控制工具类
 * 
 * @author yinger
 * 
 */

public class AutomLoginUtil {

	/* user中 login -1表示无 0-表示记住密码 1-表示自动登录 */
	private String filename = "login.cfg";

	public static final int NoLogin = -1;
	public static final int RemPwd = 0;
	public static final int AutoLogin = 1;

//	private boolean rePwd; // 是否记住密码
//	private boolean autLogin; // 是否自动登陆

//	public boolean isRePwd(User user) {
//		if (user.getLogin() == NoLogin)
//			return false;
//		else
//			return true;
//	}
//
//	// 设置是否记住密码
//	public void setRePwd(boolean rePwd, User user) {
//		if (rePwd)
//			user.setLogin(RemPwd);
//		else
//			user.setLogin(NoLogin);
//	}
//
//	public boolean isAutLogin(User user) {
//		if (user.getLogin() == AutoLogin)
//			return true;
//		else
//			return false;
//	}
//
//	// 设置是否自动登录
//	public void setAutLogin(boolean autLogin, User user) {
//		if (autLogin) {
//			user.setLogin(AutoLogin);
//		} else {
//			user.setLogin(NoLogin);
//		}
//	}

	//从指定的文件中读取登录用户的对象
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
			e.printStackTrace();
			throw e;
		}
	}

	//将对象写入指定文件
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
			e.printStackTrace();
			throw e;
		}
	}

	//test
	public static void main(String args[]) throws Exception {
		AutomLoginUtil alu = new AutomLoginUtil();
		User user = new User("admin", "123456");
		user.setLogin(AutoLogin);
		alu.writeObject(user);
		Object object = alu.readObject();
		System.out.println(((User) object).getLogin());
	}

}
