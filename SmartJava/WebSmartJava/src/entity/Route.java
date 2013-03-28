package entity;

/**
 * 路线
 * @author hjw
 *
 */
public class Route {
	private int id;//编号
	private String name;//名称
	private int sDistribution;//起点配送点
	private int tDistribution;//终点配送点
	private int length;//总长度

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

	public int getsDistribution() {
		return sDistribution;
	}

	public void setsDistribution(int sDistribution) {
		this.sDistribution = sDistribution;
	}

	public int gettDistribution() {
		return tDistribution;
	}

	public void settDistribution(int tDistribution) {
		this.tDistribution = tDistribution;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
