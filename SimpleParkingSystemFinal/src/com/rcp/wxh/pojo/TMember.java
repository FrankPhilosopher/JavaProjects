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
 * TMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_member", catalog = "carstop")
public class TMember implements java.io.Serializable {

	// Fields

	private Integer memberid;
	private TCard TCard;
	private String name;
	private String carnumber;
	private String cartype;
	private String identification;
	private String phonenumber;
	private String workplace;
//	private String carpicture;// TODO:yinger
	private Blob carpicture;
	// private byte[] carpicture;
	private String remark;

	// Constructors

	/** default constructor */
	public TMember() {
	}

	/** full constructor */
	public TMember(TCard TCard, String name, String carnumber, String cartype, String identification,
			String phonenumber, String workplace, Blob carpicture, String remark) {
		this.TCard = TCard;
		this.name = name;
		this.carnumber = carnumber;
		this.cartype = cartype;
		this.identification = identification;
		this.phonenumber = phonenumber;
		this.workplace = workplace;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CARDID")
	public TCard getTCard() {
		return this.TCard;
	}

	public void setTCard(TCard TCard) {
		this.TCard = TCard;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CARNUMBER", length = 50)
	public String getCarnumber() {
		return this.carnumber;
	}

	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}

	@Column(name = "CARTYPE", length = 20)
	public String getCartype() {
		return this.cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	@Column(name = "IDENTIFICATION", length = 30)
	public String getIdentification() {
		return this.identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	@Column(name = "PHONENUMBER", length = 30)
	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Column(name = "WORKPLACE", length = 50)
	public String getWorkplace() {
		return this.workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	// 使用String类型
	/* 有了@Lob它会自动将Blob类型转成String类型 */
//	@Lob
//	@Column(name = "CARPICTURE")
//	public String getCarpicture() {
//		return this.carpicture;
//	}
//
//	public void setCarpicture(String carpicture) {
//		this.carpicture = carpicture;
//	}

	// 使用Blob类型：Could not execute JDBC batch update
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