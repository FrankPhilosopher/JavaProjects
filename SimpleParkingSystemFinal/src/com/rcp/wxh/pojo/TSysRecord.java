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
 * TSysRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_sys_record", catalog = "carstop")
public class TSysRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private TEmp TEmp;
	private String event;
	private Date eventtime;
	private String remark;

	// Constructors

	/** default constructor */
	public TSysRecord() {
	}

	/** full constructor */
	public TSysRecord(TEmp TEmp, String event, Date eventtime, String remark) {
		this.TEmp = TEmp;
		this.event = event;
		this.eventtime = eventtime;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATORID")
	public TEmp getTEmp() {
		return this.TEmp;
	}

	public void setTEmp(TEmp TEmp) {
		this.TEmp = TEmp;
	}

	@Column(name = "EVENT", length = 250)
	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EVENTTIME", length = 10)
	public Date getEventtime() {
		return this.eventtime;
	}

	public void setEventtime(Date eventtime) {
		this.eventtime = eventtime;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}