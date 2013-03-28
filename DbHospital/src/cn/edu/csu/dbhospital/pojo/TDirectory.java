package cn.edu.csu.dbhospital.pojo;

// default package

/**
 * TDirectory entity. @author MyEclipse Persistence Tools
 */

public class TDirectory implements java.io.Serializable {

	// Fields

	private int id;
	private String name;
	private String value;

	// Constructors

	/** default constructor */
	public TDirectory() {
	}

	public TDirectory(String name, String value) {
		this.name = name;
		this.value = value;
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

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}