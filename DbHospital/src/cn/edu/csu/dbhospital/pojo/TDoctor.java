package cn.edu.csu.dbhospital.pojo;

// default package

import java.sql.Date;

/**
 * TDoctor entity. @author MyEclipse Persistence Tools
 */

public class TDoctor implements java.io.Serializable {

	// Fields

	private int id;
	private int roomId;
	private String name;
	private int gander;
	private String title;
	private String phone;
	private String info;
	private Date regtime;

	// Constructors

	/** default constructor */
	public TDoctor() {
	}

	// Property accessors

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGander() {
		return gander;
	}

	public void setGander(int gander) {
		this.gander = gander;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

}