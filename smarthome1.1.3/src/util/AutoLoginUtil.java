package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import beans.User;

import communication.CommunicationUtil;

/**
 * 自动登录控制工具类<br/>
 * 单例模式
 * 
 * @author yinger
 * 
 */

public class AutoLoginUtil {

	private static AutoLoginUtil instance;

	private AutoLoginUtil() {
	}

	public static AutoLoginUtil getInstance() {
		if (instance == null) {
			instance = new AutoLoginUtil();
		}
		return instance;
	}

	/* user中 login -1表示无 0-表示记住密码 1-表示自动登录 */
	private String filename = FileUtil.LOGIN_CONFIG;// 控制登录的文件

	public static final int NoLogin = -1;
	public static final int RemPwd = 0;
	public static final int AutoLogin = 1;

	/**
	 * 从指定的文件中读取登录用户的对象
	 */
	public User readObject() throws Exception {
		File file = new File(filename);// 文件在项目的根目录下
		if (!file.exists()) {
			return null;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Object o = ois.readObject();
			ois.close();
			User user = (User) o;
			if(AppliactionUtil.DEBUG) System.out.println("user.getPwd"+user.getPassword());
			user.setPassword(new String(BASE64Util.decryptBASE64(user.getPassword())));
			if(AppliactionUtil.DEBUG) System.out.println("user.getPwd"+user.getPassword());
			return user;
		} catch (Exception e) {
			throw new Exception("读取用户信息失败！");
		}
	}

	/**
	 * 将对象写入指定文件
	 */
	public void writeObject(User obj) throws Exception {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		String pwd = obj.getPassword();
		obj.setPassword(BASE64Util.encryptBASE64(pwd.getBytes()));
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
			obj.setPassword(pwd);// 这里是这样的：保存到文件中时obj的密码是加密了的，其他时候是没有加密的
		} catch (Exception e) {
			throw new Exception("保存用户信息失败！");
		}
	}

	// test
	public static void main(String args[]) throws Exception {
		AutoLoginUtil alu = new AutoLoginUtil();
		User user = new User("admin", "admin");
		user.setAutodownable(false);
		user.setFirstRun(true);
		user.setLoginType(CommunicationUtil.GATEWAY_LOGIN);
		user.setLogin(NoLogin);
		alu.writeObject(user);
		Object object = alu.readObject();
		if(AppliactionUtil.DEBUG) System.out.println(((User) object).getPassword());
		if(AppliactionUtil.DEBUG) System.out.println(((User) object).isAutodownable());
	}

}
