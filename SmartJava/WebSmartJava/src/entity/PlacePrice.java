package entity;

import java.util.Date;

/**
 * 配送范围和价格
 * @author hjw
 *
 */
public class PlacePrice {

	private int id;//编号
	private int distributionId;//配送点编号
	private String placeName;//配送范围名称
	private float placePrice;//配送价格
	private String placeTime;//配送时间
	private String remark;//配送范围备注

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDistributionId() {
		return distributionId;
	}

	public void setDistributionId(int distributionId) {
		this.distributionId = distributionId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public float getPlacePrice() {
		return placePrice;
	}

	public void setPlacePrice(float placePrice) {
		this.placePrice = placePrice;
	}

	public String getPlaceTime() {
		return placeTime;
	}

	public void setPlaceTime(String placeTime) {
		this.placeTime = placeTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
