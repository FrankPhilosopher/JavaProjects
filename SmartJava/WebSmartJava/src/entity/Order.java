package entity;

/**
 * 订单
 * @author hjw
 *
 */
public class Order {
	
	//订单状态的静态值
	public static int UNCHECK = 0;//未审核
	public static int ATSTOP = 1;//在站点
	public static int ATTRAVEL = 2;//在途中
	public static int UNDISPATCH = 3;//未派件
	public static int INDISPATCH = 4;//派件中
	public static int RECEIVE = 5;//已收件
	public static int LOADED=6;//被装上车
	public static int WAITUNLOADED=7;//待卸货
	//编号(PK && FK)
	private int id;					//订单id
	private int userId;				//寄件人id编号
	private int sDistribution;		//寄件人配送点id编号
	private int tDistribution;		//收件人配送点	id编号
	private int carId;				//运输订单车辆id编号
	private int currentDistribution;//订单当前所在配送点id编号
	
	//寄件人信息
	private String sender;//寄件人姓名- added By lhy
	private String sCode;//寄件人邮编
	private String sAddress;//寄件人地址
	private String sPhone;//寄件人电话
	
	//收件人信息
	private String receiver;//收件人姓名- added By lhy
	private String tCode;//收件人邮编
	private String tAddress;//收件人地址
	private String tPhone;//收件人电话
	
	//订单信息
	private float volumn;//订单体积
	private float weight;//订单重量
	private float price;//订单价格
	private int status;//订单状态
	private int goodsType;//订单类别
	
	private String remark;//订单备注信息
	private String worker;//订单揽件人
	private String path;//订单路线
	private String workDate;//订单揽件日期	



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getsDistribution() {
		return sDistribution;
	}

	public void setsDistribution(int sDistribution) {
		this.sDistribution = sDistribution;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getsAddress() {
		return sAddress;
	}

	public void setsAddress(String sAddress) {
		this.sAddress = sAddress;
	}

	public String getsPhone() {
		return sPhone;
	}

	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}

	public int gettDistribution() {
		return tDistribution;
	}

	public void settDistribution(int tDistribution) {
		this.tDistribution = tDistribution;
	}

	public String gettCode() {
		return tCode;
	}

	public void settCode(String tCode) {
		this.tCode = tCode;
	}

	public String gettAddress() {
		return tAddress;
	}

	public void settAddress(String tAddress) {
		this.tAddress = tAddress;
	}

	public String gettPhone() {
		return tPhone;
	}

	public void settPhone(String tPhone) {
		this.tPhone = tPhone;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public float getVolumn() {
		return volumn;
	}

	public void setVolumn(float volumn) {
		this.volumn = volumn;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getCurrentDistribution() {
		return currentDistribution;
	}

	public void setCurrentDistribution(int currentDistribution) {
		this.currentDistribution = currentDistribution;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

}
