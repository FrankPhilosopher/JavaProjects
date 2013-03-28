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

	// ����һ������
	public void addArea(TArea tArea) {
		tAreaDAO.save(tArea);
	}

	// ������ɾ��һ������
	public void deleleArea(Integer areaId) {
		tAreaDAO.delete(tAreaDAO.findById(areaId));
	}

	// �޸�һ������
	public void updateArea(TArea tArea) {
		tAreaDAO.attachDirty(tArea);
	}

	// ��������ѯһ������
	public TArea findById(Integer AreaId) {
		return tAreaDAO.findById(AreaId);
	}

	// ��ѯ���е���(����ҳ)
	public List findAll() {
		return tAreaDAO.findAll(-1, -1);
	}

	// ��ѯ���е���(��ҳ)
	public List findAll(int firstResult, int maxResults) {
		return tAreaDAO.findAll(firstResult, maxResults);
	}

	// ��ѯ���е���������
	public int getCountAll() {
		return tAreaDAO.getCountAll();
	}

	// ����������ѯһ������
	public List<TArea> findByName(String areaName, int firstResult,
			int maxResults) {
		return tAreaDAO.findByName(areaName, firstResult, maxResults);
	}
	// �ҵ�ĳ��ʡ�ݶ�Ӧ�ĳ���
	public List<TArea> getCities(int province) {
		return tAreaDAO.getCities(province);
	}

}
