package com.ztdz.pojo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TCarInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CAR_INFO", schema = "dbo", catalog = "ITGpserver")
public class TCarInfo implements java.io.Serializable {

	// Fields

	private Integer carTypeId;
	private String typeName;      //类型名称
	private String outline;       //信号弱或丢失电源
	private String onlineOn;      //在线
	private String onlineStop;     //在线空闲
	private String onlineWork;     //在线工作
	private String r1;
	private String remark;
	private Set<TTerminal> TTerminals = new HashSet<TTerminal>(0);

	// Constructors

	/** default constructor */
	public TCarInfo() {
	}

	/** full constructor */
	public TCarInfo(String typeName, String outline, String onlineOn,
			String onlineStop, String onlineWork, String r1, String remark,
			Set<TTerminal> TTerminals) {
		this.typeName = typeName;
		this.outline = outline;
		this.onlineOn = onlineOn;
		this.onlineStop = onlineStop;
		this.onlineWork = onlineWork;
		this.r1 = r1;
		this.remark = remark;
		this.TTerminals = TTerminals;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CAR_TYPE_ID", unique = true, nullable = false)
	public Integer getCarTypeId() {
		return this.carTypeId;
	}

	public void setCarTypeId(Integer carTypeId) {
		this.carTypeId = carTypeId;
	}

	@Column(name = "TYPE_NAME")
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "OUTLINE")
	public String getOutline() {
		return this.outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	@Column(name = "ONLINE_ON")
	public String getOnlineOn() {
		return this.onlineOn;
	}

	public void setOnlineOn(String onlineOn) {
		this.onlineOn = onlineOn;
	}

	@Column(name = "ONLINE_STOP")
	public String getOnlineStop() {
		return this.onlineStop;
	}

	public void setOnlineStop(String onlineStop) {
		this.onlineStop = onlineStop;
	}

	@Column(name = "ONLINE_WORK")
	public String getOnlineWork() {
		return this.onlineWork;
	}

	public void setOnlineWork(String onlineWork) {
		this.onlineWork = onlineWork;
	}

	@Column(name = "R1")
	public String getR1() {
		return this.r1;
	}

	public void setR1(String r1) {
		this.r1 = r1;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TCarInfo")
	public Set<TTerminal> getTTerminals() {
		return this.TTerminals;
	}

	public void setTTerminals(Set<TTerminal> TTerminals) {
		this.TTerminals = TTerminals;
	}

}