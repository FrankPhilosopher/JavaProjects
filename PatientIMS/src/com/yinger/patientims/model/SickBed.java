package com.yinger.patientims.model;

public class SickBed {

	private Long id;//id±àºÅ£¬bigint
	private int sickBedNo;//²¡´²±àºÅ£¬int
	private SickRoom sickRoom = new SickRoom();//²¡·¿£¬²¡´²ËùÔÚµÄ²¡·¿

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSickBedNo() {
		return sickBedNo;
	}

	public void setSickBedNo(int sickBedNo) {
		this.sickBedNo = sickBedNo;
	}

	public SickRoom getSickRoom() {
		return sickRoom;
	}

	public void setSickRoom(SickRoom sickRoom) {
		this.sickRoom = sickRoom;
	}

}
