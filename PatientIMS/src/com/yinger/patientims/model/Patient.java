package com.yinger.patientims.model;

import java.util.Date;

public class Patient {

	private Long id;// id编号，对应数据库中bigint
	private String name;// 姓名，varchar
	private String sex;// 性别，varchar
	private int age;// 年龄，int
	private String phone;// 电话，varchar
	private Date logtime;// 登记时间，datetime
	private String address;// 地址，varchar
	private SickBed sickBed = new SickBed();// 病床，bigint
	private SickRoom sickRoom = new SickRoom();// 病房
	private Doctor doctor = new Doctor();// 医生
	private Diagnose diagnose = new Diagnose();// 诊断
	private Department department = new Department();// 科室
	private Expense expense = new Expense();// 费用

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public SickBed getSickbed() {
		return sickBed;
	}

	public void setSickbed(SickBed sickbed) {
		this.sickBed = sickbed;
	}

	public SickRoom getSickroom() {
		return sickRoom;
	}

	public void setSickroom(SickRoom sickroom) {
		this.sickRoom = sickroom;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Diagnose getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(Diagnose diagnose) {
		this.diagnose = diagnose;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

}
