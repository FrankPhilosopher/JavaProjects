package com.ztdz.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TPosition entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_POSITION", schema = "dbo", catalog = "ITGpserver")
public class TPosition implements java.io.Serializable {

	// Fields

	private long id;
	private String sim;
	private String locationModel;
	private String stationId;
	private String plotId;
	private String latiDirection;
	private double latitude;
	private String longDirection;
	private double longitude;
	private String direction;
	private double speed;
	private Date PTime;
	private double latoffset;
	private double lngoffset;

	// Constructors

	/** default constructor */
	public TPosition() {
	}

	/** full constructor */
	public TPosition(String sim, String locationModel, String stationId,
			String plotId, String latiDirection, double latitude,
			String longDirection, double longitude, String direction,
			double speed, Date PTime, double latoffset, double lngoffset) {
		this.sim = sim;
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
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "SIM")
	public String getSim() {
		return this.sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
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