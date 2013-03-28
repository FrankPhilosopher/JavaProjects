package entity;

/**
 * 配送点
 * @author hjw
 *
 */
public class Distribution {


	private int id;//配送点编号
	private String name;//配送点名称
	private String address;//配送点地址
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
