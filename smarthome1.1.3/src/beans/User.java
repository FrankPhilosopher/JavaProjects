package beans;

import java.io.Serializable;

/**
 * 用户类
 * 
 * @author yinger
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String password;// 这个密码是被加密了的字符串
	private int login = -1;// 用于设置用户的登录模式 --- 自动登陆，记住密码，无
	private int loginType = 0;// 连接方式
	private boolean autodownable = true; // 是否自动下载，默认是true
	private boolean firstRun = true;// 默认不是第一次使用

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public boolean isAutodownable() {
		return autodownable;
	}

	public void setAutodownable(boolean autodownable) {
		this.autodownable = autodownable;
	}

	public boolean isFirstRun() {
		return firstRun;
	}

	public void setFirstRun(boolean firstRun) {
		this.firstRun = firstRun;
	}

}
