package com.ztdz.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TRequest entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_REQUEST", schema = "dbo", catalog = "ITGpserver")
public class TRequest implements java.io.Serializable {

	// Fields

	private String sim;
	private Date time;
	private String command;

	// Constructors

	/** default constructor */
	public TRequest() {
	}

	/** full constructor */
	public TRequest(Date time, String command) {
		this.time = time;
		this.command = command;
	}

	// Property accessors
	@Id
	@Column(name = "sim", unique = true, nullable = false, length = 30)
	public String getSim() {
		return this.sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	@Column(name = "TIME", length = 23)
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "COMMAND")
	public String getCommand() {
		return this.command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}