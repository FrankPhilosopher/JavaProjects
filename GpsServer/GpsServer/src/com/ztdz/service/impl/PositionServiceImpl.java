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

	// Ϊĳ���ն������ʷ��λ��Ϣ
	public void addPosition(TPosition position) {
		positionDAO.save(position);
	}

	// ������ɾ����ʷ��λ��Ϣ
	public void deletePosition(Long id) {
		positionDAO.delete(positionDAO.findById(id));
	}

	// �޸�ĳ���ն˵���ʷ��λ��Ϣ
	public void updatePosition(TPosition position) {
		positionDAO.attachDirty(position);
	}

	// ������������ʷ��λ��Ϣ
	public TPosition findById(Long id) {
		return positionDAO.findById(id);
	}

	// ���������ն˵���ʷ��λ��Ϣ(����ҳ)
	public List<TPosition> findAll() {
		return positionDAO.findAll(-1, -1);
	}

	// ���������ն˵���ʷ��λ��Ϣ(��ҳ)
	public List<TPosition> findAll(int firstResult, int maxResults) {
		return positionDAO.findAll(firstResult, maxResults);
	}

	// ���������ն˵���ʷ��λ��Ϣ������
	public int getCountAll() {
		return positionDAO.getCountAll();
	}

	// ����ĳ���ն˵�������ʷ��λ��Ϣ(����ҳ)
	public List<TPosition> findBySim(String sim) {
		return positionDAO.findBySim(sim, -1, -1);
	}

	// ����ĳ���ն˵�������ʷ��λ��Ϣ(��ҳ)
	public List<TPosition> findBySim(String sim, int firstResult, int maxResults) {
		return positionDAO.findBySim(sim, firstResult, maxResults);
	}

	// ����ĳ���ն˵�������ʷ��λ��Ϣ������
	public int getCountBySim(String sim) {
		return positionDAO.getCountBySim(sim);
	}

	// ����ĳ���ն�ĳ��ʱ��ε���ʷ��Ϣ(����ҳ)
	public List<TPosition> findBySimBetween(String sim, Date timeStart,
			Date timeEnd) {
		return positionDAO.findBySimBetween(sim, timeStart, timeEnd, -1, -1);
	}
	
	//����ͣ��
	public List<TPosition> findStopBySimBetween(String sim, Date timeStart,
			Date timeEnd){
		
		return positionDAO.findStopBySimBetween(sim, timeStart, timeEnd, -1, -1);
	}

	// ����ĳ���ն�ĳ��ʱ��ε���ʷ��Ϣ(��ҳ)
	public List<TPosition> findBySimBetween(String sim, Date timeStart,
			Date timeEnd, int firstResult, int maxResults) {
		return positionDAO.findBySimBetween(sim, timeStart, timeEnd,
				firstResult, maxResults);
	}

	// ����ĳ���ն�ĳ��ʱ��ε���ʷ��Ϣ������
	public int getCountBySimBetween(String sim, Date timeStart, Date timeEnd) {
		return positionDAO.getCountBySimBetween(sim, timeStart, timeEnd);
	}
}
