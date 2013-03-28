package entity;

/**
 * 配送点之间的价格
 * @author hjw
 *
 */
public class DistributionPrice {
	private int id;//配送点价格id
	private int sDistribution;//起始配送点id
	private int tDistribution;//相邻配送点id
	private float firstKilo;//首公斤价格
	private float secondKilo;//次公斤价格
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getsDistribution() {
		return sDistribution;
	}
	public void setsDistribution(int sDistribution) {
		this.sDistribution = sDistribution;
	}
	public int getDistribution() {
		return tDistribution;
	}
	public void setDistribution(int distribution) {
		tDistribution = distribution;
	}
	public float getFirstKilo() {
		return firstKilo;
	}
	public void setFirstKilo(float firstKilo) {
		this.firstKilo = firstKilo;
	}
	public float getSecondKilo() {
		return secondKilo;
	}
	public void setSecondKilo(float secondKilo) {
		this.secondKilo = secondKilo;
	}	
}
