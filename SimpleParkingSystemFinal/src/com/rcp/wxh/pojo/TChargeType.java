package com.rcp.wxh.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * TChargeType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_charge_type", catalog = "carstop")
public class TChargeType implements java.io.Serializable {

	public static final int MINUTE = 1; // 按分钟收费
	public static final int HOUR = 2; // 按小时收费
	public static final int TIME = 3; // 按次数收费
	public static final int PERIOD = 4; // 按时间段收费
	public static final int FREE = 5; // 永久免费
	// Fields

	private Integer chargetypeid;
	private String name;
	private Integer type;
	private Integer minutecount;
	private Float fee;
	private Integer minute;
	private Integer overminute;
	private Integer hour;
	private Integer overhour;
	private Integer times;
	private Date starttime;
	private Date endtime;
	private String remark;
	private Set<TCardType> TCardTypes = new HashSet<TCardType>(0);

	// Constructors

	/** default constructor */
	public TChargeType() {
	}

	/** minimal constructor */
	public TChargeType(String name, Integer type) {
		this.name = name;
		this.type = type;
	}

	/** full constructor */
	public TChargeType(String name, Integer type, Float fee, Integer minutecount, Integer minute, Integer overminute,
			Integer hour, Integer overhour, Integer times, Date starttime, Date endtime, String remark,
			Set<TCardType> TCardTypes) {
		this.name = name;
		this.type = type;
		this.fee = fee;
		this.minutecount = minutecount;
		this.minute = minute;
		this.overminute = overminute;
		this.hour = hour;
		this.overhour = overhour;
		this.times = times;
		this.starttime = starttime;
		this.endtime = endtime;
		this.remark = remark;
		this.TCardTypes = TCardTypes;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "CHARGETYPEID", unique = true, nullable = false)
	public Integer getChargetypeid() {
		return this.chargetypeid;
	}

	public void setChargetypeid(Integer chargetypeid) {
		this.chargetypeid = chargetypeid;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TYPE", nullable = false)
	public Integer getType() {
		return this.type;
	}

	@Column(name = "MINUTECOUNT", nullable = false)
	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMinutecount() {
		return minutecount;
	}

	public void setMinutecount(Integer minutecount) {
		this.minutecount = minutecount;
	}

	@Column(name = "FEE", precision = 12, scale = 0)
	public Float getFee() {
		return this.fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	@Column(name = "MINUTE")
	public Integer getMinute() {
		return this.minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	@Column(name = "OVERMINUTE")
	public Integer getOverminute() {
		return this.overminute;
	}

	public void setOverminute(Integer overminute) {
		this.overminute = overminute;
	}

	@Column(name = "HOUR")
	public Integer getHour() {
		return this.hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	@Column(name = "OVERHOUR")
	public Integer getOverhour() {
		return this.overhour;
	}

	public void setOverhour(Integer overhour) {
		this.overhour = overhour;
	}

	@Column(name = "TIMES")
	public Integer getTimes() {
		return this.times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "STARTTIME", length = 10)
	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ENDTIME", length = 10)
	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "TChargeType")
	public Set<TCardType> getTCardTypes() {
		return this.TCardTypes;
	}

	public void setTCardTypes(Set<TCardType> TCardTypes) {
		this.TCardTypes = TCardTypes;
	}

}