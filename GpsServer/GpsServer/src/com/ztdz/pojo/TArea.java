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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TArea entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_AREA", schema = "dbo", catalog = "ITGpserver")
public class TArea implements java.io.Serializable {

	// Fields

	private Integer areaId;
	private TArea TArea;
	private String name;
	private Set<TOrgainzation> TOrgainzations = new HashSet<TOrgainzation>(0);
	private Set<TArea> TAreas = new HashSet<TArea>(0);

	// Constructors

	/** default constructor */
	public TArea() {
	}

	/** full constructor */
	public TArea(TArea TArea, String name, Set<TOrgainzation> TOrgainzations,
			Set<TArea> TAreas) {
		this.TArea = TArea;
		this.name = name;
		this.TOrgainzations = TOrgainzations;
		this.TAreas = TAreas;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "AREA_ID", unique = true, nullable = false)
	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UP_ID")
	public TArea getTArea() {
		return this.TArea;
	}

	public void setTArea(TArea TArea) {
		this.TArea = TArea;
	}

	@Column(name = "NAME", length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TArea")
	public Set<TOrgainzation> getTOrgainzations() {
		return this.TOrgainzations;
	}

	public void setTOrgainzations(Set<TOrgainzation> TOrgainzations) {
		this.TOrgainzations = TOrgainzations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TArea")
	public Set<TArea> getTAreas() {
		return this.TAreas;
	}

	public void setTAreas(Set<TArea> TAreas) {
		this.TAreas = TAreas;
	}

}