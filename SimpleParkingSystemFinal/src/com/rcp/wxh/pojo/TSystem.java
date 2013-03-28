package com.rcp.wxh.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TSystem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_system", catalog = "carstop")
public class TSystem implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer totalnum;
	private Integer occupiednum;

	// Constructors

	/** default constructor */
	public TSystem() {
	}

	/** full constructor */
	public TSystem(Integer totalnum, Integer occupiednum) {
		this.totalnum = totalnum;
		this.occupiednum = occupiednum;
	}

	// Property accessors
	// @GenericGenerator(name = "generator", strategy = "increment")
	// @GeneratedValue(generator = "generator")
	@Id
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TOTALNUM", nullable = false)
	public Integer getTotalnum() {
		return this.totalnum;
	}

	public void setTotalnum(Integer totalnum) {
		this.totalnum = totalnum;
	}

	@Column(name = "OCCUPIEDNUM", nullable = false)
	public Integer getOccupiednum() {
		return this.occupiednum;
	}

	public void setOccupiednum(Integer occupiednum) {
		this.occupiednum = occupiednum;
	}

}