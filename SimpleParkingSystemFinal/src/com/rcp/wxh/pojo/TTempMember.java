package com.rcp.wxh.pojo;

import java.sql.Blob;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * TTempMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_temp_member", catalog = "carstop")
public class TTempMember implements java.io.Serializable {

	// Fields

	private Integer memberid;
	private TCard TCard;
	private String carnumber;
//	 private String carpicture;// TODO:yinger
	private Blob carpicture;
//	private byte[] carpicture;
	private String remark;

	// Constructors

	/** default constructor */
	public TTempMember() {
	}

	/** full constructor */
	public TTempMember(TCard TCard, String carnumber, Blob carpicture, String remark) {
		this.TCard = TCard;
		this.carnumber = carnumber;
		this.carpicture = carpicture;
		this.remark = remark;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "MEMBERID", unique = true, nullable = false)
	public Integer getMemberid() {
		return this.memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CARDID")
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

	// 使用String类型
//	@Lob
//	@Column(name = "CARPICTURE")
//	public String getCarpicture() {
//		return this.carpicture;
//	}
//
//	public void setCarpicture(String carpicture) {
//		this.carpicture = carpicture;
//	}

	// 使用Blob类型
	// @Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CARPICTURE", columnDefinition = "BLOB")
	public Blob getCarpicture() {
		return this.carpicture;
	}

	public void setCarpicture(Blob carpicture) {
		this.carpicture = carpicture;
	}

	// 使用byte[]
	// @Lob
	// @Column(name = "CARPICTURE")
	// public byte[] getCarpicture() {
	// return this.carpicture;
	// }
	//
	// public void setCarpicture(byte[] carpicture) {
	// this.carpicture = carpicture;
	// }

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}