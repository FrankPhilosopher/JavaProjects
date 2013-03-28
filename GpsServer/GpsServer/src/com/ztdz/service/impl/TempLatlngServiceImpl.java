package com.ztdz.service.impl;

import java.util.List;

import com.ztdz.dao.impl.TTempLatlngDAO;
import com.ztdz.pojo.TTempLatlng;

public class TempLatlngServiceImpl {
	private TTempLatlngDAO tempLatlngDAO;

	public TempLatlngServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public void setTempLatlngDAO(TTempLatlngDAO tempLatlngDAO) {
		this.tempLatlngDAO = tempLatlngDAO;
	}

	public TTempLatlngDAO getTempLatlngDAO() {
		return tempLatlngDAO;
	}

	// 增加一个经纬度纠偏信息
	public void addTempLatlng(TTempLatlng tTempLatlng) {
		tempLatlngDAO.save(tTempLatlng);
	}

	// 按主键删除一个经纬度纠偏信息
	public void deleteTempLatlng(Integer id) {
		tempLatlngDAO.delete(tempLatlngDAO.findById(id));
	}

	// 修改一个经纬度纠偏信息
	public void updateTempLatlng(TTempLatlng tempLatlng) {
		tempLatlngDAO.attachDirty(tempLatlng);
	}

	// 按主键查找一个经纬度纠偏信息
	public TTempLatlng findById(Integer id) {
		return tempLatlngDAO.findById(id);
	}

	// 查找所有经纬度纠偏信息(不分页)
	public List<TTempLatlng> findAll() {
		return tempLatlngDAO.findAll(-1, -1);
	}

	// 查找所有经纬度纠偏信息(分页)
	public List<TTempLatlng> findAll(int firstResult, int maxResults) {
		return tempLatlngDAO.findAll(firstResult, maxResults);
	}

	// 查找所有经纬度纠偏信息的总数
	public int getCountAll() {
		return tempLatlngDAO.getCountAll();
	}
}
