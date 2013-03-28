package com.ztdz.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ACCOUNT", schema = "dbo", catalog = "ITGpserver")
public class TAccount implements java.io.Serializable {

	// Fields

	private Integer id;
	private TOrgainzation TOrgainzation;
	private String paider;
	private Integer expense;
	private Date paiddate;
	private String remark;

	// Constructors

	/** default constructor */
	public TAccount() {
	}

	/** full constructor */
	public TAccount(TOrgainzation TOrgainzation, String paider,
			Integer expense, Date paiddate, String remark) {
		this.TOrgainzation = TOrgainzation;
		this.paider = paider;
		this.expense = expense;
		this.paiddate = paiddate;
		this.remark = remark;
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

	@Column(name = "PAIDER")
	public String getPaider() {
		return this.paider;
	}

	public void setPaider(String paider) {
		this.paider = paider;
	}

	@Column(name = "EXPENSE")
	public Integer getExpense() {
		return this.expense;
	}

	public void setExpense(Integer expense) {
		this.expense = expense;
	}

	@Column(name = "PAIDDATE", length = 23)
	public Date getPaiddate() {
		return this.paiddate;
	}

	public void setPaiddate(Date paiddate) {
		this.paiddate = paiddate;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}