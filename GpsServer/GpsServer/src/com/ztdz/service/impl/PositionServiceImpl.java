package com.ztdz.service.impl;

import java.util.Date;
import java.util.List;

import com.ztdz.dao.impl.TPositionDAO;
import com.ztdz.pojo.TPosition;

public class PositionServiceImpl {
	private TPositionDAO positionDAO;

	public PositionServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public void setPositionDAO(TPositionDAO positionDAO) {
		this.positionDAO = positionDAO;
	}

	public TPositionDAO getPositionDAO() {
		return positionDAO;
	}

	// 为某个终端添加历史定位信息
	public void addPosition(TPosition position) {
		positionDAO.save(position);
	}

	// 按主键删除历史定位信息
	public void deletePosition(Long id) {
		positionDAO.delete(positionDAO.findById(id));
	}

	// 修改某个终端的历史定位信息
	public void updatePosition(TPosition position) {
		positionDAO.attachDirty(position);
	}

	// 按主键查找历史定位信息
	public TPosition findById(Long id) {
		return positionDAO.findById(id);
	}

	// 查找所有终端的历史定位信息(不分页)
	public List<TPosition> findAll() {
		return positionDAO.findAll(-1, -1);
	}

	// 查找所有终端的历史定位信息(分页)
	public List<TPosition> findAll(int firstResult, int maxResults) {
		return positionDAO.findAll(firstResult, maxResults);
	}

	// 查找所有终端的历史定位信息的总数
	public int getCountAll() {
		return positionDAO.getCountAll();
	}

	// 查找某个终端的所有历史定位信息(不分页)
	public List<TPosition> findBySim(String sim) {
		return positionDAO.findBySim(sim, -1, -1);
	}

	// 查找某个终端的所有历史定位信息(分页)
	public List<TPosition> findBySim(String sim, int firstResult, int maxResults) {
		return positionDAO.findBySim(sim, firstResult, maxResults);
	}

	// 查找某个终端的所有历史定位信息的总数
	public int getCountBySim(String sim) {
		return positionDAO.getCountBySim(sim);
	}

	// 查找某个终端某个时间段的历史信息(不分页)
	public List<TPosition> findBySimBetween(String sim, Date timeStart,
			Date timeEnd) {
		return positionDAO.findBySimBetween(sim, timeStart, timeEnd, -1, -1);
	}
	
	//查找停车
	public List<TPosition> findStopBySimBetween(String sim, Date timeStart,
			Date timeEnd){
		
		return positionDAO.findStopBySimBetween(sim, timeStart, timeEnd, -1, -1);
	}

	// 查找某个终端某个时间段的历史信息(分页)
	public List<TPosition> findBySimBetween(String sim, Date timeStart,
			Date timeEnd, int firstResult, int maxResults) {
		return positionDAO.findBySimBetween(sim, timeStart, timeEnd,
				firstResult, maxResults);
	}

	// 查找某个终端某个时间段的历史信息的总数
	public int getCountBySimBetween(String sim, Date timeStart, Date timeEnd) {
		return positionDAO.getCountBySimBetween(sim, timeStart, timeEnd);
	}
}
