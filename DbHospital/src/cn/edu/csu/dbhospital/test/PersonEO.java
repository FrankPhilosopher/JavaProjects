package cn.edu.csu.dbhospital.test;

public class PersonEO {

	private int id;
	private String name;
	private String sex;
	private String color;

	public PersonEO() {

	}

	public PersonEO(int id, String name, String sex, String color) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
