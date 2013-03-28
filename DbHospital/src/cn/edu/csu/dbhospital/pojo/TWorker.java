package cn.edu.csu.dbhospital.pojo;

// default package

import java.util.Date;

/**
 * TWorker entity. @author MyEclipse Persistence Tools
 */

public class TWorker implements java.io.Serializable {

	public static final int TYPE_ADMIN = 0;
	public static final int TYPE_OPERATOR = 1;

	// Fields

	private int id;
	private String name;
	private String realname;
	private String password;
	private int gander;
	private String phone;
	private Date regtime;
	private int canStatics;
	private int canModify;
	private int type;

	private int login;// 登录类型--非数据库字段

	// Constructors

	/** default constructor */
	public TWorker() {
	}

	// Property accessors

	public int getId() {
		return id;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getGander() {
		return gander;
	}

	public void setGander(int gander) {
		this.gander = gander;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	public int getCanStatics() {
		return canStatics;
	}

	public void setCanStatics(int canStatics) {
		this.canStatics = canStatics;
	}

	public int getCanModify() {
		return canModify;
	}

	public void setCanModify(int canModify) {
		this.canModify = canModify;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

}