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
 * TOrgainzation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ORGAINZATION", schema = "dbo", catalog = "ITGpserver")
public class TOrgainzation implements java.io.Serializable {

	// Fields

	private Integer orgId;
	private TArea TArea;
	private TOrgainzation TOrgainzation;
	private Integer orgLevel;
	private String name = "";
	private String address = "";
	private String telephone = "";
	private String linkman = "";
	private String cellphone = "";
	private Date registertime = new Date();
	private String warnphone = "15111151443";
	private Integer feestandard = 500;
	private Integer balance = 0;
	private String shortName;
	private String r1;
	private String r2;
	private String remark;
	private Set<TAccount> TAccounts = new HashSet<TAccount>(0);
	private Set<TTerminal> TTerminals = new HashSet<TTerminal>(0);
	private Set<TOrgainzation> TOrgainzations = new HashSet<TOrgainzation>(0);
	private Set<TUser> TUsers = new HashSet<TUser>(0);

	// Constructors

	/** default constructor */
	public TOrgainzation() {
	}

	/** full constructor */
	public TOrgainzation(TArea TArea, TOrgainzation TOrgainzation,
			Integer orgLevel, String name, String address, String telephone,
			String linkman, String cellphone, Date registertime,
			String warnphone, Integer feestandard, Integer balance,
			String shortName, String r1, String r2, String remark,
			Set<TAccount> TAccounts, Set<TTerminal> TTerminals,
			Set<TOrgainzation> TOrgainzations, Set<TUser> TUsers) {
		this.TArea = TArea;
		this.TOrgainzation = TOrgainzation;
		this.orgLevel = orgLevel;
		this.name = name;
		this.address = address;
		this.telephone = telephone;
		this.linkman = linkman;
		this.cellphone = cellphone;
		this.registertime = registertime;
		this.warnphone = warnphone;
		this.feestandard = feestandard;
		this.balance = balance;
		this.shortName = shortName;
		this.r1 = r1;
		this.r2 = r2;
		this.remark = remark;
		this.TAccounts = TAccounts;
		this.TTerminals = TTerminals;
		this.TOrgainzations = TOrgainzations;
		this.TUsers = TUsers;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ORG_ID", unique = true, nullable = false)
	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AREA_ID")
	public TArea getTArea() {
		return this.TArea;
	}

	public void setTArea(TArea TArea) {
		this.TArea = TArea;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPORG_ID")
	public TOrgainzation getTOrgainzation() {
		return this.TOrgainzation;
	}

	public void setTOrgainzation(TOrgainzation TOrgainzation) {
		this.TOrgainzation = TOrgainzation;
	}

	@Column(name = "ORG_LEVEL")
	public Integer getOrgLevel() {
		return this.orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "TELEPHONE")
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "LINKMAN")
	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "CELLPHONE")
	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	@Column(name = "REGISTERTIME", length = 23)
	public Date getRegistertime() {
		return this.registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	@Column(name = "WARNPHONE")
	public String getWarnphone() {
		return this.warnphone;
	}

	public void setWarnphone(String warnphone) {
		this.warnphone = warnphone;
	}

	@Column(name = "FEESTANDARD")
	public Integer getFeestandard() {
		return this.feestandard;
	}

	public void setFeestandard(Integer feestandard) {
		this.feestandard = feestandard;
	}

	@Column(name = "BALANCE")
	public Integer getBalance() {
		return this.balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	@Column(name = "SHORT_NAME")
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "R1")
	public String getR1() {
		return this.r1;
	}

	public void setR1(String r1) {
		this.r1 = r1;
	}

	@Column(name = "R2")
	public String getR2() {
		return this.r2;
	}

	public void setR2(String r2) {
		this.r2 = r2;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TOrgainzation")
	public Set<TAccount> getTAccounts() {
		return this.TAccounts;
	}

	public void setTAccounts(Set<TAccount> TAccounts) {
		this.TAccounts = TAccounts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TOrgainzation")
	public Set<TTerminal> getTTerminals() {
		return this.TTerminals;
	}

	public void setTTerminals(Set<TTerminal> TTerminals) {
		this.TTerminals = TTerminals;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TOrgainzation")
	public Set<TOrgainzation> getTOrgainzations() {
		return this.TOrgainzations;
	}

	public void setTOrgainzations(Set<TOrgainzation> TOrgainzations) {
		this.TOrgainzations = TOrgainzations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TOrgainzation")
	public Set<TUser> getTUsers() {
		return this.TUsers;
	}

	public void setTUsers(Set<TUser> TUsers) {
		this.TUsers = TUsers;
	}

}