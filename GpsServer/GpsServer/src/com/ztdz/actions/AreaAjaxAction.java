package com.ztdz.actions;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.ztdz.pojo.TArea;
import com.ztdz.service.impl.AreaServiceImpl;

public class AreaAjaxAction extends ActionSupport {
	private AreaServiceImpl areaService;
	private int provinceId;
	private List<TArea> cities;

	public String getCitiesByProvince() {
		cities=areaService.getCities(provinceId);
		return SUCCESS;
	}

	public void setCities(List<TArea> cities) {
		this.cities = cities;
	}

	public List<TArea> getCities() {
		return cities;
	}

	public AreaServiceImpl getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaServiceImpl areaService) {
		this.areaService = areaService;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getProvinceId() {
		return provinceId;
	}
}
