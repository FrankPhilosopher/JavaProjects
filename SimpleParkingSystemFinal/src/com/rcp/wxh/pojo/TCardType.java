package com.rcp.wxh.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * TCardType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_card_type", catalog = "carstop")
public class TCardType implements java.io.Serializable {

	// Fields

	private Integer cardtypeid;
	private TChargeType TChargeType;
	private String name;
	private String remark;
	private Set<TCard> TCards = new HashSet<TCard>(0);

//	private Set<TExpenseRecord> TExpenseRecords = new HashSet<TExpenseRecord>(0);

	// Constructors

	/** default constructor */
	public TCardType() {
	}

	/** full constructor */
	public TCardType(TChargeType TChargeType, String name, String remark, Set<TCard> TCards) {
		this.TChargeType = TChargeType;
		this.name = name;
		this.remark = remark;
		this.TCards = TCards;
//		this.TExpenseRecords = TExpenseRecords;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "CARDTYPEID", unique = true, nullable = false)
	public Integer getCardtypeid() {
		return this.cardtypeid;
	}

	public void setCardtypeid(Integer cardtypeid) {
		this.cardtypeid = cardtypeid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CHARGETYPEID")
	public TChargeType getTChargeType() {
		return this.TChargeType;
	}

	public void setTChargeType(TChargeType TChargeType) {
		this.TChargeType = TChargeType;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TCardType")
	public Set<TCard> getTCards() {
		return this.TCards;
	}

	public void setTCards(Set<TCard> TCards) {
		this.TCards = TCards;
	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TCardType")
//	public Set<TExpenseRecord> getTExpenseRecords() {
//		return this.TExpenseRecords;
//	}
//
//	public void setTExpenseRecords(Set<TExpenseRecord> TExpenseRecords) {
//		this.TExpenseRecords = TExpenseRecords;
//	}

}