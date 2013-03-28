package entity;

import java.util.Date;

/**
 * 交接单
 * @author hjw
 *
 */
public class PassingSheet {

	private int id;//编号
	private int orderId;//订单号
	private int carId;//车辆编号
	private int distributionId;//交接地配送点
	private Date date;//日期

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getDistributionId() {
		return distributionId;
	}

	public void setDistributionId(int distributionId) {
		this.distributionId = distributionId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
