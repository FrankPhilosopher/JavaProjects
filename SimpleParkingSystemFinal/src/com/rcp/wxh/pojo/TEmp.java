package com.rcp.wxh.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TEmp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_emp", catalog = "carstop")
public class TEmp implements java.io.Serializable {

	public static final int ADMINSTRATOR = 0; // 管理员
	public static final int OPERATOR = 1; // 操作员
	private int login = -1;//用于设置员工的登录模式

	// Fields

	private String operatorid;
	private String password;
	private String operatorname;
	private Integer priority;
	private String identification;
	private String phonenumber;
	private String address;
	private String remark;
	private Set<TExpenseRecord> TExpenseRecords = new HashSet<TExpenseRecord>(0);
	private Set<TSysRecord> TSysRecords = new HashSet<TSysRecord>(0);

	// Constructors

	/** default constructor */
	public TEmp() {
	}

	/** full constructor */
	public TEmp(String password, String operatorname, Integer priority, String identification, String phonenumber,
			String address, String remark, Set<TExpenseRecord> TExpenseRecords, Set<TSysRecord> TSysRecords) {
		this.password = password;
		this.operatorname = operatorname;
		this.priority = priority;
		this.identification = identification;
		this.phonenumber = phonenumber;
		this.address = address;
		this.remark = remark;
		this.TExpenseRecords = TExpenseRecords;
		this.TSysRecords = TSysRecords;
	}

	// Property accessors
//	@GenericGenerator(name = "generator", strategy = "increment")
//	@GeneratedValue(generator = "generator")
	@Id
	@Column(name = "OPERATORID", unique = true, nullable = false, length = 30)
	public String getOperatorid() {
		return this.operatorid;
	}

	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}

	@Column(name = "PASSWORD", length = 30)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "OPERATORNAME", length = 30)
	public String getOperatorname() {
		return this.operatorname;
	}

	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}

	@Column(name = "PRIORITY")
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "IDENTIFICATION", length = 30)
	public String getIdentification() {
		return this.identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	@Column(name = "PHONENUMBER", length = 30)
	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Column(name = "ADDRESS", length = 80)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TEmp")
	public Set<TExpenseRecord> getTExpenseRecords() {
		return this.TExpenseRecords;
	}

	public void setTExpenseRecords(Set<TExpenseRecord> TExpenseRecords) {
		this.TExpenseRecords = TExpenseRecords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TEmp")
	public Set<TSysRecord> getTSysRecords() {
		return this.TSysRecords;
	}

	public void setTSysRecords(Set<TSysRecord> TSysRecords) {
		this.TSysRecords = TSysRecords;
	}

	@Transient
	public int getLogin() {
		return login;
	}

	@Transient
	public void setLogin(int login) {
		this.login = login;
	}

}