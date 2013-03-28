package com.rcp.wxh.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * TExpenseRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_expense_record", catalog = "carstop")
public class TExpenseRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private TCard TCard;
	private TEmp TEmp;
	private String carnumber;
	private Float dueexpense;
	private Integer duetimes;
	private Float factexpense;
	private Integer facttimes;
	private Date expensedate;
	private String remark;

	// Constructors

	/** default constructor */
	public TExpenseRecord() {
	}

	/** full constructor */
	public TExpenseRecord(TCard TCard, TEmp TEmp, String carnumber, Float dueexpense, Integer duetimes,
			Float factexpense, Integer facttimes, Date expensedate, String remark) {
		this.TCard = TCard;
		this.TEmp = TEmp;
		this.carnumber = carnumber;
		this.dueexpense = dueexpense;
		this.duetimes = duetimes;
		this.factexpense = factexpense;
		this.facttimes = facttimes;
		this.expensedate = expensedate;
		this.remark = remark;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CARDID")
	public TCard getTCard() {
		return this.TCard;
	}

	public void setTCard(TCard TCard) {
		this.TCard = TCard;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OPERATORID")
	public TEmp getTEmp() {
		return this.TEmp;
	}

	public void setTEmp(TEmp TEmp) {
		this.TEmp = TEmp;
	}

	@Column(name = "CARNUMBER", length = 30)
	public String getCarnumber() {
		return this.carnumber;
	}

	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}

	@Column(name = "DUEEXPENSE", precision = 12, scale = 0)
	public Float getDueexpense() {
		return this.dueexpense;
	}

	public void setDueexpense(Float dueexpense) {
		this.dueexpense = dueexpense;
	}

	@Column(name = "DUETIMES")
	public Integer getDuetimes() {
		return this.duetimes;
	}

	public void setDuetimes(Integer duetimes) {
		this.duetimes = duetimes;
	}

	@Column(name = "FACTEXPENSE", precision = 12, scale = 0)
	public Float getFactexpense() {
		return this.factexpense;
	}

	public void setFactexpense(Float factexpense) {
		this.factexpense = factexpense;
	}

	@Column(name = "FACTTIMES")
	public Integer getFacttimes() {
		return this.facttimes;
	}

	public void setFacttimes(Integer facttimes) {
		this.facttimes = facttimes;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPENSEDATE", length = 19)
	public Date getExpensedate() {
		return this.expensedate;
	}

	public void setExpensedate(Date expensedate) {
		this.expensedate = expensedate;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}