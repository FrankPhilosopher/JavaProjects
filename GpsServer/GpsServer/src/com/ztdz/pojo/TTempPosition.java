package com.ztdz.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TTempPosition entity. @aut2hor MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TEMP_POSITION", schema = "dbo", catalog = "ITGpserver")
public class TTempPosition implements java.io.Serializable {

	// Fields

	private String sim;
	private TTerminal TTerminal;
	private String locationModel = "0";//定位模式   0-GPS定位  1-基站定位
	private String stationId = "0";//基站编号    
	private String plotId = "0";//小区编号
	private String latiDirection = "0";
	private double latitude = -1;//纬度
	private String longDirection = "0";
	private double longitude = -1;//经度
	private String direction = "0";
	private double speed = 0;
	private Date PTime;//最后定位时间
	private double latoffset = 0;
	private double lngoffset = 0;

	// Constructors

	/** default constructor */
	public TTempPosition() {
	}

	/** full constructor */
	public TTempPosition(TTerminal TTerminal, String locationModel,
			String stationId, String plotId, String latiDirection,
			double latitude, String longDirection, double longitude,
			String direction, double speed, Date PTime, double latoffset,
			double lngoffset) {
		this.TTerminal = TTerminal;
		this.locationModel = locationModel;
		this.stationId = stationId;
		this.plotId = plotId;
		this.latiDirection = latiDirection;
		this.latitude = latitude;
		this.longDirection = longDirection;
		this.longitude = longitude;
		this.direction = direction;
		this.speed = speed;
		this.PTime = PTime;
		this.latoffset = latoffset;
		this.lngoffset = lngoffset;
	}

	// Property accessors
	@Id
	@Column(name = "SIM", unique = true, nullable = false)
	public String getSim() {
		return this.sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SIM_ID")
	public TTerminal getTTerminal() {
		return this.TTerminal;
	}

	public void setTTerminal(TTerminal TTerminal) {
		this.TTerminal = TTerminal;
	}

	@Column(name = "LOCATION_MODEL")
	public String getLocationModel() {
		return this.locationModel;
	}

	public void setLocationModel(String locationModel) {
		this.locationModel = locationModel;
	}

	@Column(name = "STATION_ID")
	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	@Column(name = "PLOT_ID")
	public String getPlotId() {
		return this.plotId;
	}

	public void setPlotId(String plotId) {
		this.plotId = plotId;
	}

	@Column(name = "LATI_DIRECTION")
	public String getLatiDirection() {
		return this.latiDirection;
	}

	public void setLatiDirection(String latiDirection) {
		this.latiDirection = latiDirection;
	}

	@Column(name = "LATITUDE", precision = 53, scale = 0)
	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "LONG_DIRECTION")
	public String getLongDirection() {
		return this.longDirection;
	}

	public void setLongDirection(String longDirection) {
		this.longDirection = longDirection;
	}

	@Column(name = "LONGITUDE", precision = 53, scale = 0)
	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "DIRECTION")
	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Column(name = "SPEED", precision = 53, scale = 0)
	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Column(name = "P_TIME", length = 23)
	public Date getPTime() {
		return this.PTime;
	}

	public void setPTime(Date PTime) {
		this.PTime = PTime;
	}

	@Column(name = "LATOFFSET", precision = 53, scale = 0)
	public double getLatoffset() {
		return this.latoffset;
	}

	public void setLatoffset(double latoffset) {
		this.latoffset = latoffset;
	}

	@Column(name = "LNGOFFSET", precision = 53, scale = 0)
	public double getLngoffset() {
		return this.lngoffset;
	}

	public void setLngoffset(double lngoffset) {
		this.lngoffset = lngoffset;
	}

}