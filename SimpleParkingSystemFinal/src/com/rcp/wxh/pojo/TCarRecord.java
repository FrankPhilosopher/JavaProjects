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
 * TCarRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_car_record", catalog = "carstop")
public class TCarRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private TCard TCard;
	private String carnumber;
	private Date entertime;
	private Date exittime;
	private Integer stoptime;
	private Float factexpense;
	private Float dueexpense;
	private String remark;

	// Constructors

	/** default constructor */
	public TCarRecord() {
	}

	/** minimal constructor */
	public TCarRecord(TCard TCard, Integer stoptime, Float factexpense, Float dueexpense) {
		this.TCard = TCard;
		this.stoptime = stoptime;
		this.factexpense = factexpense;
		this.dueexpense = dueexpense;
	}

	/** full constructor */
	public TCarRecord(TCard TCard, String carnumber, Date entertime, Date exittime, Integer stoptime,
			Float factexpense, Float dueexpense, String remark) {
		this.TCard = TCard;
		this.carnumber = carnumber;
		this.entertime = entertime;
		this.exittime = exittime;
		this.stoptime = stoptime;
		this.factexpense = factexpense;
		this.dueexpense = dueexpense;
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
	@JoinColumn(name = "CARDID", nullable = false)
	public TCard getTCard() {
		return this.TCard;
	}

	public void setTCard(TCard TCard) {
		this.TCard = TCard;
	}

	@Column(name = "CARNUMBER", length = 30)
	public String getCarnumber() {
		return this.carnumber;
	}

	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTERTIME", length = 19)
	public Date getEntertime() {
		return this.entertime;
	}

	public void setEntertime(Date entertime) {
		this.entertime = entertime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXITTIME", length = 19)
	public Date getExittime() {
		return this.exittime;
	}

	public void setExittime(Date exittime) {
		this.exittime = exittime;
	}

	@Column(name = "STOPTIME", nullable = false)
	public Integer getStoptime() {
		return this.stoptime;
	}

	public void setStoptime(Integer stoptime) {
		this.stoptime = stoptime;
	}

	@Column(name = "FACTEXPENSE", nullable = false, precision = 12, scale = 0)
	public Float getFactexpense() {
		return this.factexpense;
	}

	public void setFactexpense(Float factexpense) {
		this.factexpense = factexpense;
	}

	@Column(name = "DUEEXPENSE", nullable = false, precision = 12, scale = 0)
	public Float getDueexpense() {
		return this.dueexpense;
	}

	public void setDueexpense(Float dueexpense) {
		this.dueexpense = dueexpense;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}