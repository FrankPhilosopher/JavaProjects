package com.ztdz.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.ztdz.tools.addr;

/**
 * ��ַ����
 * @author Administrator
 *
 */
public class AddressAction extends ActionSupport implements SessionAware{
	//ά��
	private String lat;
	//����
	private String lng;
	//���ص�ַ
	private String address;
	//��ͼ���ż���
	private int zoom;
	/**
	 * ��ȡ��ϸ��ַ
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
