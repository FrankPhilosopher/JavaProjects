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
 * TLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_LOG", schema = "dbo", catalog = "ITGpserver")
public class TLog implements java.io.Serializable {

	// Fields

	private Integer logId;
	private TUser TUser;
	private String logEvent;
	private Date logTime;

	// Constructors

	/** default constructor */
	public TLog() {
	}

	/** full constructor */
	public TLog(TUser TUser, String logEvent, Date logTime) {
		this.TUser = TUser;
		this.logEvent = logEvent;
		this.logTime = logTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "LOG_ID", unique = true, nullable = false)
	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	public TUser getTUser() {
		return this.TUser;
	}

	public void setTUser(TUser TUser) {
		this.TUser = TUser;
	}

	@Column(name = "LOG_EVENT")
	public String getLogEvent() {
		return this.logEvent;
	}

	public void setLogEvent(String logEvent) {
		this.logEvent = logEvent;
	}

	@Column(name = "LOG_TIME", length = 23)
	public Date getLogTime() {
		return this.logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

}