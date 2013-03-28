package com.yj.smarthome.beans;

import java.io.Serializable;

/**
 * 用户类
 * 
 * @author yinger
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = -7667914828593010187L;
	private String name;
	private String password;
	private int login = -1;//用于设置用户的登录模式

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

}
