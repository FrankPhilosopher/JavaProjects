package com.ztdz.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TTerminal entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TERMINAL", schema = "dbo", catalog = "ITGpserver")
public class TTerminal implements java.io.Serializable {

	// Fields

	private Integer id;
	private TCarInfo TCarInfo;
	private TOrgainzation TOrgainzation;
	private TArea TArea;
	private String sim;
	private String phone="";
	private String model="";
	private String carnumber="";
	private String gas="";
	private Date registertime=new Date();
	private Date startTime=new Date();
	private Date endTime=new Date();
	private String privilege1= "15111151443";
	private String privilege2= "15111151443";
	private String privilege3= "15111151443";
	private Integer lock= 0;
	private Integer PPeriod= 0;
	private Integer basekilo=0;
	private Integer maintenance=0;
	private String username= "";
	private String cellphone= "";
	private String principal= "";
	private String remark= "";
	private String status= "0"; //¹¤×÷×´Ì¬
	private Integer worktime= 0;
	private String elepress= "0";
	private Integer signal= 0;
	private Integer netstatus=0;
	private List<TTempPosition> TTempPositions = new ArrayList<TTempPosition>(0);

	// Constructors

	/** default constructor */
	public TTerminal() {
	}

	/** full constructor */
	public TTerminal(TCarInfo TCarInfo, TOrgainzation TOrgainzation,
			TArea TArea, String sim, String phone, String model,
			String carnumber, String gas, Date registertime, Date startTime,
			Date endTime, String privilege1, String privilege2,
			String privilege3, Integer lock, Integer PPeriod, Integer basekilo,
			Integer maintenance, String username, String cellphone,
			String principal, String remark, String status, Integer worktime,
			String elepress, Integer signal, Integer netstatus,
			List<TTempPosition> TTempPositions) {
		this.TCarInfo = TCarInfo;
		this.TOrgainzation = TOrgainzation;
		this.TArea = TArea;
		this.sim = sim;
		this.phone = phone;
		this.model = model;
		this.carnumber = carnumber;
		this.gas = gas;
		this.registertime = registertime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.privilege1 = privilege1;
		this.privilege2 = privilege2;
		this.privilege3 = privilege3;
		this.lock = lock;
		this.PPeriod = PPeriod;
		this.basekilo = basekilo;
		this.maintenance = maintenance;
		this.username = username;
		this.cellphone = cellphone;
		this.principal = principal;
		this.remark = remark;
		this.status = status;
		this.worktime = worktime;
		this.elepress = elepress;
		this.signal = signal;
		this.netstatus = netstatus;
		this.TTempPositions = TTempPositions;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAR_TYPE_ID")
	public TCarInfo getTCarInfo() {
		return this.TCarInfo;
	}

	public void setTCarInfo(TCarInfo TCarInfo) {
		this.TCarInfo = TCarInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_ID")
	public TOrgainzation getTOrgainzation() {
		return this.TOrgainzation;
	}

	public void setTOrgainzation(TOrgainzation TOrgainzation) {
		this.TOrgainzation = TOrgainzation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AREA_ID")
	public TArea getTArea() {
		return this.TArea;
	}

	public void setTArea(TArea TArea) {
		this.TArea = TArea;
	}

	@Column(name = "SIM", length = 30)
	public String getSim() {
		return this.sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "MODEL", length = 30)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "CARNUMBER", length = 40)
	public String getCarnumber() {
		return this.carnumber;
	}

	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}

	@Column(name = "GAS", length = 30)
	public String getGas() {
		return this.gas;
	}

	public void setGas(String gas) {
		this.gas = gas;
	}

	@Column(name = "REGISTERTIME", length = 23)
	public Date getRegistertime() {
		return this.registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	@Column(name = "START_TIME", length = 23)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", length = 23)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "PRIVILEGE1", length = 30)
	public String getPrivilege1() {
		return this.privilege1;
	}

	public void setPrivilege1(String privilege1) {
		this.privilege1 = privilege1;
	}

	@Column(name = "PRIVILEGE2", length = 30)
	public String getPrivilege2() {
		return this.privilege2;
	}

	public void setPrivilege2(String privilege2) {
		this.privilege2 = privilege2;
	}

	@Column(name = "PRIVILEGE3", length = 30)
	public String getPrivilege3() {
		return this.privilege3;
	}

	public void setPrivilege3(String privilege3) {
		this.privilege3 = privilege3;
	}

	@Column(name = "LOCK")
	public Integer getLock() {
		return this.lock;
	}

	public void setLock(Integer lock) {
		this.lock = lock;
	}

	@Column(name = "P_PERIOD")
	public Integer getPPeriod() {
		return this.PPeriod;
	}

	public void setPPeriod(Integer PPeriod) {
		this.PPeriod = PPeriod;
	}

	@Column(name = "BASEKILO")
	public Integer getBasekilo() {
		return this.basekilo;
	}

	public void setBasekilo(Integer basekilo) {
		this.basekilo = basekilo;
	}

	@Column(name = "MAINTENANCE")
	public Integer getMaintenance() {
		return this.maintenance;
	}

	public void setMaintenance(Integer maintenance) {
		this.maintenance = maintenance;
	}

	@Column(name = "USERNAME", length = 30)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "CELLPHONE", length = 30)
	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	@Column(name = "PRINCIPAL", length = 30)
	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STATUS", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "WORKTIME")
	public Integer getWorktime() {
		return this.worktime;
	}

	public void setWorktime(Integer worktime) {
		this.worktime = worktime;
	}

	@Column(name = "ELEPRESS", length = 30)
	public String getElepress() {
		return this.elepress;
	}

	public void setElepress(String elepress) {
		this.elepress = elepress;
	}

	@Column(name = "SIGNAL")
	public Integer getSignal() {
		return this.signal;
	}

	public void setSignal(Integer signal) {
		this.signal = signal;
	}

	@Column(name = "NETSTATUS")
	public Integer getNetstatus() {
		return this.netstatus;
	}

	public void setNetstatus(Integer netstatus) {
		this.netstatus = netstatus;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TTerminal")
	public List<TTempPosition> getTTempPositions() {
		return this.TTempPositions;
	}

	public void setTTempPositions(List<TTempPosition> TTempPositions) {
		this.TTempPositions = TTempPositions;
	}

}