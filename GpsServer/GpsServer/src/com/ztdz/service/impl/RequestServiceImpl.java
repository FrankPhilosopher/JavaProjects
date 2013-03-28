package com.ztdz.service.impl;

import java.util.Date;
import java.util.List;

import com.ztdz.dao.impl.TRequestDAO;
import com.ztdz.pojo.TRequest;

public class RequestServiceImpl {
	private TRequestDAO tRequestDAO;

	public RequestServiceImpl() {

	}

	public void settRequestDAO(TRequestDAO tRequestDAO) {
		this.tRequestDAO = tRequestDAO;
	}

	public TRequestDAO gettRequestDAO() {
		return tRequestDAO;
	}

	// 添加一个请求
	public void addRequest(TRequest tRequest) {
		tRequestDAO.save(tRequest);
	}

	// 按主键删除一个请求
	public void deleteRequest(String sim) {
		tRequestDAO.delete(tRequestDAO.findById(sim));
	}

	// 修改一个请求信息
	public void updateRequest(TRequest tRequest) {
		tRequestDAO.attachDirty(tRequest);
	}

	// 根据请求信息的主键查找一个请求信息
	public TRequest findById(String sim) {
		return tRequestDAO.findById(sim);
	}
	/**
	 * 删除对象
	 * @param request
	 */
	public void deleteRequest(TRequest request){
		tRequestDAO.delete(request);
	}

}
