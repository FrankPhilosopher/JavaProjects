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

	// ���һ������
	public void addRequest(TRequest tRequest) {
		tRequestDAO.save(tRequest);
	}

	// ������ɾ��һ������
	public void deleteRequest(String sim) {
		tRequestDAO.delete(tRequestDAO.findById(sim));
	}

	// �޸�һ��������Ϣ
	public void updateRequest(TRequest tRequest) {
		tRequestDAO.attachDirty(tRequest);
	}

	// ����������Ϣ����������һ��������Ϣ
	public TRequest findById(String sim) {
		return tRequestDAO.findById(sim);
	}
	/**
	 * ɾ������
	 * @param request
	 */
	public void deleteRequest(TRequest request){
		tRequestDAO.delete(request);
	}

}
