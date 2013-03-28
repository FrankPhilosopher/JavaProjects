package entity;

/**
 * 车辆
 * @author hjw
 *
 */
public class Car {
	
	//车辆状态的静态值
	public static int AVAILABLE = 0;//空闲的，可以调用
	public static int ATSTOP = 1;//在站点
	public static int TRAVEL = 2;//在途中	

	private int id;//车辆在数据库表中编号
	private String number;//车牌号码
	private String driver;//司机名称
	private float weight;//车的载重量
	private float volumn;//车的载货体积	
	private int status;//车辆状态
	private int currentDistribution;//车辆当前配送点
	private float currentWeight;//当前车的剩余载重量
	private float currentVolumn;//当前车的剩余载货体积
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getVolumn() {
		return volumn;
	}

	public void setVolumn(float volumn) {
		this.volumn = volumn;
	}

	public int getCurrentDistribution() {
		return currentDistribution;
	}

	public void setCurrentDistribution(int currentDistribution) {
		this.currentDistribution = currentDistribution;
	}
	public float getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(float currentWeight) {
		this.currentWeight = currentWeight;
	}

	public float getCurrentVolumn() {
		return currentVolumn;
	}

	public void setCurrentVolumn(float currentVolumn) {
		this.currentVolumn = currentVolumn;
	}

	

}
