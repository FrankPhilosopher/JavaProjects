package entity;

/**
 * 省公司
 * @author hjw
 *
 */
public class Company {

	private int id;//省公司ID，默认只有一个
	private String name;//省公司名称

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

}
