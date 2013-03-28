package com.ztdz.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTempLatlng entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TEMP_LATLNG", schema = "dbo", catalog = "ITGpserver")
public class TTempLatlng implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer lat;
	private Integer lng;
	private Integer offsetX;
	private Integer offsetY;
	private String offsetLng;
	private String offsetLat;

	// Constructors

	/** default constructor */
	public TTempLatlng() {
	}

	/** full constructor */
	public TTempLatlng(Integer lat, Integer lng, Integer offsetX,
			Integer offsetY, String offsetLng, String offsetLat) {
		this.lat = lat;
		this.lng = lng;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetLng = offsetLng;
		this.offsetLat = offsetLat;
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

	@Column(name = "LAT")
	public Integer getLat() {
		return this.lat;
	}

	public void setLat(Integer lat) {
		this.lat = lat;
	}

	@Column(name = "LNG")
	public Integer getLng() {
		return this.lng;
	}

	public void setLng(Integer lng) {
		this.lng = lng;
	}

	@Column(name = "OFFSET_X")
	public Integer getOffsetX() {
		return this.offsetX;
	}

	public void setOffsetX(Integer offsetX) {
		this.offsetX = offsetX;
	}

	@Column(name = "OFFSET_Y")
	public Integer getOffsetY() {
		return this.offsetY;
	}

	public void setOffsetY(Integer offsetY) {
		this.offsetY = offsetY;
	}

	@Column(name = "OFFSET_LNG")
	public String getOffsetLng() {
		return this.offsetLng;
	}

	public void setOffsetLng(String offsetLng) {
		this.offsetLng = offsetLng;
	}

	@Column(name = "OFFSET_LAT")
	public String getOffsetLat() {
		return this.offsetLat;
	}

	public void setOffsetLat(String offsetLat) {
		this.offsetLat = offsetLat;
	}

}