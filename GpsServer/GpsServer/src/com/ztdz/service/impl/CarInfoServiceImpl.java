package com.ztdz.service.impl;

import java.util.List;

import com.ztdz.dao.impl.TCarInfoDAO;
import com.ztdz.pojo.TCarInfo;

public class CarInfoServiceImpl {
	private TCarInfoDAO tCarInfoDAO;

	public CarInfoServiceImpl() {
		super();
	}

	public void settCarInfoDAO(TCarInfoDAO tCarInfoDAO) {
		this.tCarInfoDAO = tCarInfoDAO;
	}

	public TCarInfoDAO gettCarInfoDAO() {
		return tCarInfoDAO;
	}

	// 增加车辆信息
	public void addCarInfo(TCarInfo tCarInfo) {
		tCarInfoDAO.save(tCarInfo);
	}

	// 按主键删除车辆信息
	public void deleteCarInfo(Integer carId) {
		tCarInfoDAO.delete(tCarInfoDAO.findById(carId));
	}

	// 修改车辆信息
	public void updateCarInfo(TCarInfo tCarInfo) {
		tCarInfoDAO.attachDirty(tCarInfo);
	}

	// 按主键查询车辆信息
	public TCarInfo findById(Integer carId) {
		return tCarInfoDAO.findById(carId);
	}

	// 查询所有车辆信息(不分页)
	public List<TCarInfo> findAll() {
		return tCarInfoDAO.findAll(-1, -1);
	}

	// 查询所有车辆信息(分页)
	public List<TCarInfo> findAll(int firstResult, int maxResults) {
		return tCarInfoDAO.findAll(firstResult, maxResults);
	}

	// 查询所有车辆信息的总数
	public int getCountAll() {
		return tCarInfoDAO.getCountAll();
	}

	// 按车辆类型查询车辆信息(不分页)
	public List<TCarInfo> findByType(String typeName) {
		return tCarInfoDAO.findByTypeName(typeName, -1, -1);
	}

	// 按车辆类型查询车辆信息(分页)
	public List<TCarInfo> findByType(String typeName, int firstResult,
			int maxResults) {
		return tCarInfoDAO.findByTypeName(typeName, firstResult, maxResults);
	}

	// 按车辆类型查询车辆信息的总数
	public int getCountByType(String typeName) {
		return tCarInfoDAO.getCountByTypeName(typeName);
	}
}
