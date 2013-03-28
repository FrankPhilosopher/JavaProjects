package com.ztdz.service.impl;

import java.util.Date;
import java.util.List;

import com.ztdz.dao.impl.TResponseDAO;
import com.ztdz.pojo.TResponse;

public class ResponseServiceImpl {
	private TResponseDAO tResponseDAO;

	public ResponseServiceImpl() {
	}

	public void settResponseDAO(TResponseDAO tResponseDAO) {
		this.tResponseDAO = tResponseDAO;
	}

	public TResponseDAO gettResponseDAO() {
		return tResponseDAO;
	}

	// 添加一个回应信息
	public void addResponse(TResponse tResponse) {
		tResponseDAO.save(tResponse);
	}

	// 按主键删除一个回应信息
	public void deleteResponse(String sim) {
		tResponseDAO.delete(tResponseDAO.findById(sim));
	}

	// 修改一个回应信息
	public void updateResponse(TResponse tResponse) {
		tResponseDAO.attachDirty(tResponse);
	}

	// 按主键查找一个回应信息
	public TResponse findById(String sim) {
		return tResponseDAO.findById(sim);
	}

	/**
	 * 删除独享
	 * @param response
	 */
	public void deleteResponse(TResponse response){
		tResponseDAO.delete(response);
	}
}
