package com.ztdz.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_USER", schema = "dbo", catalog = "ITGpserver")
public class TUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private TOrgainzation TOrgainzation;
	private String userid;
	private String pwd;
	private String name;
	private Date registertime;
	private String cellphone;
	private Integer oilele;//锁机解锁
	private Integer modify;//修改终端信息
	private Integer export;//导出报表
	private Set<TLog> TLogs = new HashSet<TLog>(0);

	// Constructors

	/** default constructor */
	public TUser() {
	}

	/** full constructor */
	public TUser(TOrgainzation TOrgainzation, String userid, String pwd,
			String name, Date registertime, String cellphone, Integer oilele,
			Integer modify, Integer export, Set<TLog> TLogs) {
		this.TOrgainzation = TOrgainzation;
		this.userid = userid;
		this.pwd = pwd;
		this.name = name;
		this.registertime = registertime;
		this.cellphone = cellphone;
		this.oilele = oilele;
		this.modify = modify;
		this.export = export;
		this.TLogs = TLogs;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_ID")
	public TOrgainzation getTOrgainzation() {
		return this.TOrgainzation;
	}

	public void setTOrgainzation(TOrgainzation TOrgainzation) {
		this.TOrgainzation = TOrgainzation;
	}

	@Column(name = "USERID")
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "PWD")
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "REGISTERTIME", length = 23)
	public Date getRegistertime() {
		return this.registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	@Column(name = "CELLPHONE")
	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	@Column(name = "OILELE")
	public Integer getOilele() {
		return this.oilele;
	}

	public void setOilele(Integer oilele) {
		this.oilele = oilele;
	}

	@Column(name = "MODIFY")
	public Integer getModify() {
		return this.modify;
	}

	public void setModify(Integer modify) {
		this.modify = modify;
	}

	@Column(name = "EXPORT")
	public Integer getExport() {
		return this.export;
	}

	public void setExport(Integer export) {
		this.export = export;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUser")
	public Set<TLog> getTLogs() {
		return this.TLogs;
	}

	public void setTLogs(Set<TLog> TLogs) {
		this.TLogs = TLogs;
	}

}