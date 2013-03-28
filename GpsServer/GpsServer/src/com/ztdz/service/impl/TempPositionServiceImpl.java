package com.ztdz.service.impl;

import java.util.List;

import com.ztdz.dao.impl.TTempPositionDAO;
import com.ztdz.pojo.TTempPosition;

public class TempPositionServiceImpl {
	private TTempPositionDAO tempPositionDAO;

	public TempPositionServiceImpl() {

	}

	public void setTempPositionDAO(TTempPositionDAO tempPositionDAO) {
		this.tempPositionDAO = tempPositionDAO;
	}

	public TTempPositionDAO getTempPositionDAO() {
		return tempPositionDAO;
	}

	// 为某个终端添加当前定位信息
	public void addTempPosition(TTempPosition tempPosition) {
		tempPositionDAO.save(tempPosition);
	}

	// 按主键为某个终端删除当前定位信息
	public void delTempPosition(String sim) {
		tempPositionDAO.delete(tempPositionDAO.findById(sim));
	}

	// 修改某个终端的当前定位信息
	public void updateTempPosition(TTempPosition tempPosition) {
		tempPositionDAO.attachDirty(tempPosition);
	}

	// 按主键查找某个终端的当前定位信息
	public TTempPosition findById(String sim) {
		return tempPositionDAO.findById(sim);
	}

	// 查找所有终端的当前定位信息(不分页)
	public List<TTempPosition> findAll() {
		return tempPositionDAO.findAll(-1, -1);
	}

	// 查找所有终端的当前定位信息(分页)
	public List<TTempPosition> findAll(int firstResult, int maxResults) {
		return tempPositionDAO.findAll(firstResult, maxResults);
	}

	// 查找所有终端的当前定位信息的总数
	public int getCountAll() {
		return tempPositionDAO.getCountAll();
	}

	// 查找某个基站下的所有终端的当前定位信息(不分页)
	public List<TTempPosition> findByStationId(String stationId) {
		return tempPositionDAO.findByStationId(stationId, -1, -1);
	}

	// 查找某个基站下的所有终端的当前定位信息(分页)
	public List<TTempPosition> findByStationId(String stationId,
			int firstResult, int maxResults) {
		return tempPositionDAO.findByStationId(stationId, firstResult,
				maxResults);
	}

	// 查找某个基站下的所有终端的当前定位信息(不分页)
	public int getCountByStationId(String stationId) {
		return tempPositionDAO.getCountByStationId(stationId);
	}

	// 按状态（工程机械：表示工作或空闲 车辆：表示手动报警或解除）查找某一状态的所有终端的当前定位信息(不分页)
	public List<TTempPosition> findByStatus(String status) {
		return tempPositionDAO.findByStatus(status, -1, -1);
	}

	// 按状态（工程机械：表示工作或空闲 车辆：表示手动报警或解除）查找某一状态的所有终端的当前定位信息(分页)
	public List<TTempPosition> findByStatus(String status, int firstResult,
			int maxResults) {
		return tempPositionDAO.findByStatus(status, firstResult, maxResults);
	}

	// 按状态查找某一状态的所有终端的当前定位信息的总数
	public int getCountByStatus(String status) {
		return tempPositionDAO.getCountByStatus(status);
	}
}
