package com.ztdz.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.ztdz.tools.addr;

/**
 * 地址解析
 * @author Administrator
 *
 */
public class AddressAction extends ActionSupport implements SessionAware{
	//维度
	private String lat;
	//经度
	private String lng;
	//返回地址
	private String address;
	//地图缩放级别
	private int zoom;
	/**
	 * 获取详细地址
	 * @return
	 */
	public String getDetailAddress(){
		System.out.println(lat+"\t\t"+lng);
		address = addr.getAdress(Double.parseDouble(lat), Double.parseDouble(lng), zoom);
System.err.println(address);
		return SUCCESS;
	}

	@JSON(name="ISBN")
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
