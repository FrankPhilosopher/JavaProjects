package cn.edu.csu.dbhospital.pojo;

// default package

/**
 * TRoom entity. @author MyEclipse Persistence Tools
 */

public class TRoom implements java.io.Serializable {

	// Fields

	private int id;
	private String name;
	private String info;

	// Constructors

	/** default constructor */
	public TRoom() {
	}

	// Property accessors

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}