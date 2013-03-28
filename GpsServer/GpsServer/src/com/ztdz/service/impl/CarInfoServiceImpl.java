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

	// ���ӳ�����Ϣ
	public void addCarInfo(TCarInfo tCarInfo) {
		tCarInfoDAO.save(tCarInfo);
	}

	// ������ɾ��������Ϣ
	public void deleteCarInfo(Integer carId) {
		tCarInfoDAO.delete(tCarInfoDAO.findById(carId));
	}

	// �޸ĳ�����Ϣ
	public void updateCarInfo(TCarInfo tCarInfo) {
		tCarInfoDAO.attachDirty(tCarInfo);
	}

	// ��������ѯ������Ϣ
	public TCarInfo findById(Integer carId) {
		return tCarInfoDAO.findById(carId);
	}

	// ��ѯ���г�����Ϣ(����ҳ)
	public List<TCarInfo> findAll() {
		return tCarInfoDAO.findAll(-1, -1);
	}

	// ��ѯ���г�����Ϣ(��ҳ)
	public List<TCarInfo> findAll(int firstResult, int maxResults) {
		return tCarInfoDAO.findAll(firstResult, maxResults);
	}

	// ��ѯ���г�����Ϣ������
	public int getCountAll() {
		return tCarInfoDAO.getCountAll();
	}

	// ���������Ͳ�ѯ������Ϣ(����ҳ)
	public List<TCarInfo> findByType(String typeName) {
		return tCarInfoDAO.findByTypeName(typeName, -1, -1);
	}

	// ���������Ͳ�ѯ������Ϣ(��ҳ)
	public List<TCarInfo> findByType(String typeName, int firstResult,
			int maxResults) {
		return tCarInfoDAO.findByTypeName(typeName, firstResult, maxResults);
	}

	// ���������Ͳ�ѯ������Ϣ������
	public int getCountByType(String typeName) {
		return tCarInfoDAO.getCountByTypeName(typeName);
	}
}
