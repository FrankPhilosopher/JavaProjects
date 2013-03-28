package com.ztdz.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TResponse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RESPONSE", schema = "dbo", catalog = "ITGpserver")
public class TResponse implements java.io.Serializable {

	// Fields

	private String sim;
	private Date tme;
	private String response;

	// Constructors

	/** default constructor */
	public TResponse() {
	}

	/** full constructor */
	public TResponse(Date tme, String response) {
		this.tme = tme;
		this.response = response;
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

	@Column(name = "TME", length = 23)
	public Date getTme() {
		return this.tme;
	}

	public void setTme(Date tme) {
		this.tme = tme;
	}

	@Column(name = "RESPONSE")
	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}