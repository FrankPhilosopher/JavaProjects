package com.yinger.patientims.model;

import java.util.Date;

public class Expense {
	
	private Long id;//id编号，bigint
	private String expenseDesc;//费用说明
	private String expenseName;//费用名称
	private float unitPrice;//单价
	private int number;//数量
	private float expenseSum;//实际的费用
	private Date takeDate;//费用发生时间
	private Patient patient;//病人
	private Doctor doctor;//医生
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getExpenseDesc() {
		return expenseDesc;
	}
	public void setExpenseDesc(String expenseDesc) {
		this.expenseDesc = expenseDesc;
	}
	public String getExpenseName() {
		return expenseName;
	}
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public float getExpenseSum() {
		return expenseSum;
	}
	public void setExpenseSum(float expenseSum) {
		this.expenseSum = expenseSum;
	}
	public Date getTakeDate() {
		return takeDate;
	}
	public void setTakeDate(Date takeDate) {
		this.takeDate = takeDate;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

}
