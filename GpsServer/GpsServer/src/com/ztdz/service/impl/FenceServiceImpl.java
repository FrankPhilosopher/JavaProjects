package com.ztdz.service.impl;

import java.util.List;

import com.ztdz.dao.impl.TFenceDAO;
import com.ztdz.pojo.TFence;

public class FenceServiceImpl {
	private TFenceDAO tFenceDAO;

	public FenceServiceImpl() {
	}

	public void settFenceDAO(TFenceDAO tFenceDAO) {
		this.tFenceDAO = tFenceDAO;
	}

	public TFenceDAO gettFenceDAO() {
		return tFenceDAO;
	}

	// Ϊһ���豸���ӵ���դ��
	public void addFence(TFence tFence) {
		tFenceDAO.save(tFence);
	}

	// ͨ������Ϊһ���豸ɾ������դ��
	public void deleteById(Integer id) {
		tFenceDAO.delete(tFenceDAO.findById(id));
	}

	// ͨ���豸SIMΪһ���豸ɾ������դ��
	public void deleteBySim(String sim) {
		tFenceDAO.delete(tFenceDAO.findBySim(sim).get(0));
	}

	// �޸�һ���豸�ĵ���դ��
	public void updateFence(TFence tFence) {
		tFenceDAO.attachDirty(tFence);
	}

	// ͨ������(ID)��ѯһ���豸�ĵ���դ��
	public TFence findById(Integer id) {
		return tFenceDAO.findById(id);
	}

	// ͨ���豸SIM����һ���豸�ĵ���դ��
	public TFence findBySim(String sim) {
		return tFenceDAO.findBySim(sim).get(0);
	}

	// ��ѯ���е���դ��(����ҳ)
	public List findAll() {
		return tFenceDAO.findAll(-1, -1);
	}

	// ��ѯ���е���դ��(��ҳ)
	public List findAll(int firstResult, int maxResults) {
		return tFenceDAO.findAll(firstResult, maxResults);
	}

	// ��ѯ���е���դ��������
	public int getCountAll() {
		return tFenceDAO.getCountAll();
	}

	// ͨ������դ���Ŀ���״̬��ѯ���п����ĵ���դ�� ���߲�ѯ���йرյĵ���դ��(����ҳ)
	public List<TFence> findByOnoff(Integer onoff) {
		return tFenceDAO.findByOnoff(onoff, -1, -1);
	}

	// ͨ������դ���Ŀ���״̬��ѯ���п����ĵ���դ�� ���߲�ѯ���йرյĵ���դ��(��ҳ)
	public List<TFence> findByOnoff(Integer onoff, int firstResult,
			int maxResults) {
		return tFenceDAO.findByOnoff(onoff, firstResult, maxResults);
	}

	// ͨ������դ���Ŀ���״̬��ѯ���п����ĵ���դ�� ���߲�ѯ���йرյĵ���դ��������
	public int getCountByOnoff(Integer onoff) {
		return tFenceDAO.getCountByOnoff(onoff);
	}
}
