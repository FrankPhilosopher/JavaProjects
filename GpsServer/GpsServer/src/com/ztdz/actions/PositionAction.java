package com.ztdz.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javassist.tools.reflect.Sample;
import org.apache.struts2.json.annotations.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.ztdz.pojo.TTempPosition;
import com.ztdz.service.impl.TempPositionServiceImpl;
import com.ztdz.tools.PositionConvertUtil;

 
public class PositionAction extends ActionSupport {
	
	private TempPositionServiceImpl tempPositionService;
	private String sim;	//�ն˺�
	private String locationMode; //��λģʽ  ��վ/����
	private String p_time;	//��λʱ��
	private String lati_direction;  //γ�ȷ���
	private Float latitude;	//γ��
	private String long_direction; //���ȷ���
	private Float longitude;	//����
	private Float speed;	//�ٶ�
	private String direction;	//����
	private String netstatus;		//����״̬
	private String pstatus;//����״̬
	
	
	public String getPosition() {
		// TODO Auto-generated method stub
		TTempPosition position = tempPositionService.findById(sim);
		PositionConvertUtil.convert(position);
		sim = position.getSim();
		locationMode = position.getLocationModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		p_time = sdf.format(position.getPTime());
		lati_direction = position.getLatiDirection();
		latitude = (float) position.getLatitude();
		long_direction = position.getLongDirection();
		longitude = (float) position.getLongitude();
		speed = (float)position.getSpeed();
		direction = position.getDirection();
		netstatus = position.getTTerminal().getNetstatus()+"";
		pstatus = position.getTTerminal().getStatus();
		System.out.println(sim+"***************"+position);
		return SUCCESS;
	}
	
	//@JSON(name="ISBN")
	public String getSim() {
		return sim;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public String getP_time() {
		return p_time;
	}
	public void setP_time(String pTime) {
		p_time = pTime;
	}
	public String getLati_direction() {
		return lati_direction;
	}
	public void setLati_direction(String latiDirection) {
		lati_direction = latiDirection;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public String getLong_direction() {
		return long_direction;
	}
	public void setLong_direction(String longDirection) {
		long_direction = longDirection;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getSpeed() {
		return speed;
	}
	public void setSpeed(Float speed) {
		this.speed = speed;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getPstatus() {
		return pstatus;
	}

	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}

	public String getNetstatus() {
		return netstatus;
	}

	public void setNetstatus(String netstatus) {
		this.netstatus = netstatus;
	}

//	public TempPositionServiceImpl getTempPositionService() {
//		return tempPositionService;
//	}

	public void setTempPositionService(TempPositionServiceImpl tempPositionService) {
		this.tempPositionService = tempPositionService;
	}

	public String getLocationMode() {
		return locationMode;
	}

	public void setLocationMode(String locationMode) {
		this.locationMode = locationMode;
	}
	
}
