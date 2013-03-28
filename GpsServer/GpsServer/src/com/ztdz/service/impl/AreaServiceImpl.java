package com.ztdz.service.impl;

import java.util.List;

import com.ztdz.dao.impl.TAreaDAO;
import com.ztdz.pojo.TArea;

public class AreaServiceImpl {
	private TAreaDAO tAreaDAO;

	public AreaServiceImpl() {
		super();
	}

	public void settAreaDAO(TAreaDAO tAreaDAO) {
		this.tAreaDAO = tAreaDAO;
	}

	public TAreaDAO gettAreaDAO() {
		return tAreaDAO;
	}

	// 增加一个地区
	public void addArea(TArea tArea) {
		tAreaDAO.save(tArea);
	}

	// 按主键删除一个地区
	public void deleleArea(Integer areaId) {
		tAreaDAO.delete(tAreaDAO.findById(areaId));
	}

	// 修改一个地区
	public void updateArea(TArea tArea) {
		tAreaDAO.attachDirty(tArea);
	}

	// 按主键查询一个地区
	public TArea findById(Integer AreaId) {
		return tAreaDAO.findById(AreaId);
	}

	// 查询所有地区(不分页)
	public List findAll() {
		return tAreaDAO.findAll(-1, -1);
	}

	// 查询所有地区(分页)
	public List findAll(int firstResult, int maxResults) {
		return tAreaDAO.findAll(firstResult, maxResults);
	}

	// 查询所有地区的总数
	public int getCountAll() {
		return tAreaDAO.getCountAll();
	}

	// 按地区名查询一个地区
	public List<TArea> findByName(String areaName, int firstResult,
			int maxResults) {
		return tAreaDAO.findByName(areaName, firstResult, maxResults);
	}
	// 找到某个省份对应的城市
	public List<TArea> getCities(int province) {
		return tAreaDAO.getCities(province);
	}

}
