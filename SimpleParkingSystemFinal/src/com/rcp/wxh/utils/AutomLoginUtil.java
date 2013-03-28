package com.rcp.wxh.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.rcp.wxh.pojo.TEmp;

/**
 * 自动登录控制工具类
 * 
 * @author wuxuehong 2011-11-7
 * 
 */

public class AutomLoginUtil {

	/**
	 * emp中 priority -1表示无 0-表示记住密码 1-表示自动登录
	 */
	private String filename = "config.lib";

	private boolean rePwd; // 是否记住密码
	private boolean autLogin; // 是否自动登陆

	public boolean isRePwd(TEmp emp) {
		if (emp.getLogin() == -1)
			return false;
		else
			return true;
	}

	// 设置是否记住密码
	public void setRePwd(boolean rePwd, TEmp emp) {
		if (rePwd)
			emp.setLogin(0);
		else
			emp.setLogin(-1);
	}

	public boolean isAutLogin(TEmp emp) {
		if (emp.getLogin() == 1)
			return true;
		else
			return false;
	}

	// 设置是否自动登录
	public void setAutLogin(boolean autLogin, TEmp emp) {
		if (autLogin) {
			emp.setLogin(1);
		} else {
			emp.setLogin(-1);
		}
	}

	/**
	 * 从指定的文件中读取登录用户的对象
	 * 
	 * @param filename
	 * @return
	 */
	public Object readObject() {
		File file = new File(filename);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			try {
				Object o = ois.readObject();
				return o;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将对象写入指定文件
	 * 
	 * @param filename
	 * @param obj
	 */
	public void writeObject(Object obj) {
		File file = new File(filename);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			oos.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * test
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		AutomLoginUtil alu = new AutomLoginUtil();
//		TEmp emp = new TEmp();
//		emp.setOperatorid("admin");
//		emp.setPassword("123456");
		TEmp emp = (TEmp) alu.readObject(); // emp priority 1-表示
		System.out.println(emp.getOperatorid() + "\t\t" + emp.getPassword());
	}

}
