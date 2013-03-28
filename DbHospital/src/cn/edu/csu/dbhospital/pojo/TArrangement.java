package cn.edu.csu.dbhospital.pojo;

import java.util.Date;

// default package

/**
 * TArrangement entity. @author MyEclipse Persistence Tools
 */

public class TArrangement implements java.io.Serializable {

	// Fields

	private int id;
	private int doctorId;
	private int roomId;
	private float expense;
	private int limit;
	private Date date;
	private int num;

	// Constructors

	/** default constructor */
	public TArrangement() {
	}

	// Property accessors

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public float getExpense() {
		return expense;
	}

	public void setExpense(float expense) {
		this.expense = expense;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}