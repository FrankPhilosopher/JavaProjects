package com.rcp.wxh.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * TExceptionRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_exception_record", catalog = "carstop")
public class TExceptionRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String exception;
	private Date exceptiontime;
	private String remark;

	// Constructors

	/** default constructor */
	public TExceptionRecord() {
	}

	/** full constructor */
	public TExceptionRecord(String exception, Date exceptiontime, String remark) {
		this.exception = exception;
		this.exceptiontime = exceptiontime;
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

	@Column(name = "EXCEPTION", length = 250)
	public String getException() {
		return this.exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXCEPTIONTIME", length = 10)
	public Date getExceptiontime() {
		return this.exceptiontime;
	}

	public void setExceptiontime(Date exceptiontime) {
		this.exceptiontime = exceptiontime;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}