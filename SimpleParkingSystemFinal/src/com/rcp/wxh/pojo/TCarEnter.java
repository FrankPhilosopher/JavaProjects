package com.rcp.wxh.pojo;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TCarEnter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_car_enter", catalog = "carstop")
public class TCarEnter implements java.io.Serializable {

	// Fields

	private String cardid;
	private TCard TCard;
	private String carnumber;
	private Date entertime;
	private String remark;

	// Constructors

	/** default constructor */
	public TCarEnter() {
	}

	/** minimal constructor */
	public TCarEnter(TCard TCard) {
		this.TCard = TCard;
	}

	/** full constructor */
	public TCarEnter(TCard TCard, String carnumber, Timestamp entertime, String remark) {
		this.TCard = TCard;
		this.carnumber = carnumber;
		this.entertime = entertime;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "CARDID", unique = true, nullable = false, length = 30)
	public String getCardid() {
		return this.cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
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

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}