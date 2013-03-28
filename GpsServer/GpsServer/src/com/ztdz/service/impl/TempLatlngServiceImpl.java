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

	// ����һ����γ�Ⱦ�ƫ��Ϣ
	public void addTempLatlng(TTempLatlng tTempLatlng) {
		tempLatlngDAO.save(tTempLatlng);
	}

	// ������ɾ��һ����γ�Ⱦ�ƫ��Ϣ
	public void deleteTempLatlng(Integer id) {
		tempLatlngDAO.delete(tempLatlngDAO.findById(id));
	}

	// �޸�һ����γ�Ⱦ�ƫ��Ϣ
	public void updateTempLatlng(TTempLatlng tempLatlng) {
		tempLatlngDAO.attachDirty(tempLatlng);
	}

	// ����������һ����γ�Ⱦ�ƫ��Ϣ
	public TTempLatlng findById(Integer id) {
		return tempLatlngDAO.findById(id);
	}

	// �������о�γ�Ⱦ�ƫ��Ϣ(����ҳ)
	public List<TTempLatlng> findAll() {
		return tempLatlngDAO.findAll(-1, -1);
	}

	// �������о�γ�Ⱦ�ƫ��Ϣ(��ҳ)
	public List<TTempLatlng> findAll(int firstResult, int maxResults) {
		return tempLatlngDAO.findAll(firstResult, maxResults);
	}

	// �������о�γ�Ⱦ�ƫ��Ϣ������
	public int getCountAll() {
		return tempLatlngDAO.getCountAll();
	}
}
