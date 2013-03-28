package beans;

import java.io.Serializable;

/**
 * �û���
 * 
 * @author yinger
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String password;// ��������Ǳ������˵��ַ���
	private int login = -1;// ���������û��ĵ�¼ģʽ --- �Զ���½����ס���룬��
	private int loginType = 0;// ���ӷ�ʽ
	private boolean autodownable = true; // �Ƿ��Զ����أ�Ĭ����true
	private boolean firstRun = true;// Ĭ�ϲ��ǵ�һ��ʹ��

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
