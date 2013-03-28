package com.ztdz.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TFence entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_FENCE", schema = "dbo", catalog = "ITGpserver")
public class TFence implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer onoff;
	private double longitude;
	private double latitude;
	private double radious;
	private String sim;

	// Constructors

	/** default constructor */
	public TFence() {
	}

	/** full constructor */
	public TFence(Integer onoff, double longitude, double latitude,
			double radious, String sim) {
		this.onoff = onoff;
		this.longitude = longitude;
		this.latitude = latitude;
		this.radious = radious;
		this.sim = sim;
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

	@Column(name = "ONOFF")
	public Integer getOnoff() {
		return this.onoff;
	}

	public void setOnoff(Integer onoff) {
		this.onoff = onoff;
	}

	@Column(name = "LONGITUDE", precision = 53, scale = 0)
	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "LATITUDE", precision = 53, scale = 0)
	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "RADIOUS", precision = 53, scale = 0)
	public double getRadious() {
		return this.radious;
	}

	public void setRadious(double radious) {
		this.radious = radious;
	}

	@Column(name = "SIM", length = 30)
	public String getSim() {
		return this.sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

}