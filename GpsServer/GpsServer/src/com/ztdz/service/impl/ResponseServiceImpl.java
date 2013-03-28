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

	// ���һ����Ӧ��Ϣ
	public void addResponse(TResponse tResponse) {
		tResponseDAO.save(tResponse);
	}

	// ������ɾ��һ����Ӧ��Ϣ
	public void deleteResponse(String sim) {
		tResponseDAO.delete(tResponseDAO.findById(sim));
	}

	// �޸�һ����Ӧ��Ϣ
	public void updateResponse(TResponse tResponse) {
		tResponseDAO.attachDirty(tResponse);
	}

	// ����������һ����Ӧ��Ϣ
	public TResponse findById(String sim) {
		return tResponseDAO.findById(sim);
	}

	/**
	 * ɾ������
	 * @param response
	 */
	public void deleteResponse(TResponse response){
		tResponseDAO.delete(response);
	}
}
