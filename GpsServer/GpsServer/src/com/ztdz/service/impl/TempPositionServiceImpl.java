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

	// Ϊĳ���ն���ӵ�ǰ��λ��Ϣ
	public void addTempPosition(TTempPosition tempPosition) {
		tempPositionDAO.save(tempPosition);
	}

	// ������Ϊĳ���ն�ɾ����ǰ��λ��Ϣ
	public void delTempPosition(String sim) {
		tempPositionDAO.delete(tempPositionDAO.findById(sim));
	}

	// �޸�ĳ���ն˵ĵ�ǰ��λ��Ϣ
	public void updateTempPosition(TTempPosition tempPosition) {
		tempPositionDAO.attachDirty(tempPosition);
	}

	// ����������ĳ���ն˵ĵ�ǰ��λ��Ϣ
	public TTempPosition findById(String sim) {
		return tempPositionDAO.findById(sim);
	}

	// ���������ն˵ĵ�ǰ��λ��Ϣ(����ҳ)
	public List<TTempPosition> findAll() {
		return tempPositionDAO.findAll(-1, -1);
	}

	// ���������ն˵ĵ�ǰ��λ��Ϣ(��ҳ)
	public List<TTempPosition> findAll(int firstResult, int maxResults) {
		return tempPositionDAO.findAll(firstResult, maxResults);
	}

	// ���������ն˵ĵ�ǰ��λ��Ϣ������
	public int getCountAll() {
		return tempPositionDAO.getCountAll();
	}

	// ����ĳ����վ�µ������ն˵ĵ�ǰ��λ��Ϣ(����ҳ)
	public List<TTempPosition> findByStationId(String stationId) {
		return tempPositionDAO.findByStationId(stationId, -1, -1);
	}

	// ����ĳ����վ�µ������ն˵ĵ�ǰ��λ��Ϣ(��ҳ)
	public List<TTempPosition> findByStationId(String stationId,
			int firstResult, int maxResults) {
		return tempPositionDAO.findByStationId(stationId, firstResult,
				maxResults);
	}

	// ����ĳ����վ�µ������ն˵ĵ�ǰ��λ��Ϣ(����ҳ)
	public int getCountByStationId(String stationId) {
		return tempPositionDAO.getCountByStationId(stationId);
	}

	// ��״̬�����̻�е����ʾ��������� ��������ʾ�ֶ���������������ĳһ״̬�������ն˵ĵ�ǰ��λ��Ϣ(����ҳ)
	public List<TTempPosition> findByStatus(String status) {
		return tempPositionDAO.findByStatus(status, -1, -1);
	}

	// ��״̬�����̻�е����ʾ��������� ��������ʾ�ֶ���������������ĳһ״̬�������ն˵ĵ�ǰ��λ��Ϣ(��ҳ)
	public List<TTempPosition> findByStatus(String status, int firstResult,
			int maxResults) {
		return tempPositionDAO.findByStatus(status, firstResult, maxResults);
	}

	// ��״̬����ĳһ״̬�������ն˵ĵ�ǰ��λ��Ϣ������
	public int getCountByStatus(String status) {
		return tempPositionDAO.getCountByStatus(status);
	}
}
