package com.yinger.patientims.model;

public class SickRoom {
	
	private Long id;//id编号，bigint
	private int sickRoomNo;//病房编号，int
	private Department department = new Department();//科室，病房所在的科室，bigint

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSickRoomNo() {
		return sickRoomNo;
	}

	public void setSickRoomNo(int sickRoomNo) {
		this.sickRoomNo = sickRoomNo;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
