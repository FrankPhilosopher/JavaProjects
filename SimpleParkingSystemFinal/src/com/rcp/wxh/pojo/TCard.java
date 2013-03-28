package com.rcp.wxh.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TCard entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_card", catalog = "carstop")
public class TCard implements java.io.Serializable {

	public static final int AVILIABLE = 1; // 可用
	public static final int LOCKED = 0; // 锁定

	public static final int LONG_CARD = 0; // 会员卡
	public static final int TEMP_CARD = 1; // 临时卡

	// Fields

	private String cardid;
	private TCardType TCardType;// 具体的卡类型，它涉及到如何收费
	private Integer cardtype;
	private Date carddate;
	private Integer cardstatus;
	private Float cardprice;
	private Integer cardtimes;
	private Date cardstarttime;
	private Date cardendtime;
	private String remark;
	private Set<TCarRecord> TCarRecords = new HashSet<TCarRecord>(0);
	private Set<TTempMember> TTempMembers = new HashSet<TTempMember>(0);
	private Set<TMember> TMembers = new HashSet<TMember>(0);
	private Set<TExpenseRecord> TExpenseRecords = new HashSet<TExpenseRecord>(0);

	// Constructors

	/** default constructor */
	public TCard() {
	}

	/** minimal constructor */
	public TCard(Integer cardtype) {
		this.cardtype = cardtype;
	}

	/** full constructor */
	public TCard(TCardType TCardType, Integer cardtype, Date carddate, Integer cardstatus, Float cardprice,
			Integer cardtimes, Date cardstarttime, Date cardendtime, String remark, Set<TCarRecord> TCarRecords,
			Set<TTempMember> TTempMembers, Set<TMember> TMembers, Set<TExpenseRecord> TExpenseRecords) {
		this.TCardType = TCardType;
		this.cardtype = cardtype;
		this.carddate = carddate;
		this.cardstatus = cardstatus;
		this.cardprice = cardprice;
		this.cardtimes = cardtimes;
		this.cardstarttime = cardstarttime;
		this.cardendtime = cardendtime;
		this.remark = remark;
		this.TCarRecords = TCarRecords;
		this.TTempMembers = TTempMembers;
		this.TMembers = TMembers;
		this.TExpenseRecords = TExpenseRecords;
	}

	// Property accessors
	// @GeneratedValue
	@Id
	@Column(name = "CARDID", unique = true, nullable = false, length = 30)
	public String getCardid() {
		return this.cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CARDTYPEID")
	public TCardType getTCardType() {
		return this.TCardType;
	}

	public void setTCardType(TCardType TCardType) {
		this.TCardType = TCardType;
	}

	@Column(name = "CARDTYPE", nullable = false)
	public Integer getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CARDDATE", length = 10)
	public Date getCarddate() {
		return this.carddate;
	}

	public void setCarddate(Date carddate) {
		this.carddate = carddate;
	}

	@Column(name = "CARDSTATUS")
	public Integer getCardstatus() {
		return this.cardstatus;
	}

	public void setCardstatus(Integer cardstatus) {
		this.cardstatus = cardstatus;
	}

	@Column(name = "CARDPRICE", precision = 12, scale = 0)
	public Float getCardprice() {
		return this.cardprice;
	}

	public void setCardprice(Float cardprice) {
		this.cardprice = cardprice;
	}

	@Column(name = "CARDTIMES")
	public Integer getCardtimes() {
		return this.cardtimes;
	}

	public void setCardtimes(Integer cardtimes) {
		this.cardtimes = cardtimes;
	}

	// @Temporal(TemporalType.DATE)
	@Column(name = "CARDSTARTTIME", length = 10)
	public Date getCardstarttime() {
		return this.cardstarttime;
	}

	public void setCardstarttime(Date cardstarttime) {
		this.cardstarttime = cardstarttime;
	}

	// @Temporal(TemporalType.DATE)
	@Column(name = "CARDENDTIME", length = 10)
	public Date getCardendtime() {
		return this.cardendtime;
	}

	public void setCardendtime(Date cardendtime) {
		this.cardendtime = cardendtime;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "TCard")
	public Set<TCarRecord> getTCarRecords() {
		return this.TCarRecords;
	}

	public void setTCarRecords(Set<TCarRecord> TCarRecords) {
		this.TCarRecords = TCarRecords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "TCard")
	public Set<TTempMember> getTTempMembers() {
		return this.TTempMembers;
	}

	public void setTTempMembers(Set<TTempMember> TTempMembers) {
		this.TTempMembers = TTempMembers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "TCard")
	public Set<TMember> getTMembers() {
		return this.TMembers;
	}

	public void setTMembers(Set<TMember> TMembers) {
		this.TMembers = TMembers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "TCard")
	public Set<TExpenseRecord> getTExpenseRecords() {
		return this.TExpenseRecords;
	}

	public void setTExpenseRecords(Set<TExpenseRecord> TExpenseRecords) {
		this.TExpenseRecords = TExpenseRecords;
	}

}