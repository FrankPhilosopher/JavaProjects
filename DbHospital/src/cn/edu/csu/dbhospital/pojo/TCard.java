package cn.edu.csu.dbhospital.pojo;

// default package

import java.util.Date;

/**
 * TCard entity. @author MyEclipse Persistence Tools
 */

public class TCard implements java.io.Serializable {
	
	public static final int DEAL_YES = 1;
	public static final int DEAL_NO = 0;

	// Fields

	private int id;
	private int userId;
	private String cardnum;
	private Date datetime;
	private int dealed;

	// Constructors

	/** default constructor */
	public TCard() {
	}

	// Property accessors

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public int getDealed() {
		return dealed;
	}

	public void setDealed(int dealed) {
		this.dealed = dealed;
	}

}