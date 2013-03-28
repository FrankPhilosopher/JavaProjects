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

	// 为一个设备增加电子栅栏
	public void addFence(TFence tFence) {
		tFenceDAO.save(tFence);
	}

	// 通过主键为一个设备删除电子栅栏
	public void deleteById(Integer id) {
		tFenceDAO.delete(tFenceDAO.findById(id));
	}

	// 通过设备SIM为一个设备删除电子栅栏
	public void deleteBySim(String sim) {
		tFenceDAO.delete(tFenceDAO.findBySim(sim).get(0));
	}

	// 修改一个设备的电子栅栏
	public void updateFence(TFence tFence) {
		tFenceDAO.attachDirty(tFence);
	}

	// 通过主键(ID)查询一个设备的电子栅栏
	public TFence findById(Integer id) {
		return tFenceDAO.findById(id);
	}

	// 通过设备SIM查找一个设备的电子栅栏
	public TFence findBySim(String sim) {
		return tFenceDAO.findBySim(sim).get(0);
	}

	// 查询所有电子栅栏(不分页)
	public List findAll() {
		return tFenceDAO.findAll(-1, -1);
	}

	// 查询所有电子栅栏(分页)
	public List findAll(int firstResult, int maxResults) {
		return tFenceDAO.findAll(firstResult, maxResults);
	}

	// 查询所有电子栅栏的总数
	public int getCountAll() {
		return tFenceDAO.getCountAll();
	}

	// 通过电子栅栏的开关状态查询所有开启的电子栅栏 或者查询所有关闭的电子栅栏(不分页)
	public List<TFence> findByOnoff(Integer onoff) {
		return tFenceDAO.findByOnoff(onoff, -1, -1);
	}

	// 通过电子栅栏的开关状态查询所有开启的电子栅栏 或者查询所有关闭的电子栅栏(分页)
	public List<TFence> findByOnoff(Integer onoff, int firstResult,
			int maxResults) {
		return tFenceDAO.findByOnoff(onoff, firstResult, maxResults);
	}

	// 通过电子栅栏的开关状态查询所有开启的电子栅栏 或者查询所有关闭的电子栅栏的总数
	public int getCountByOnoff(Integer onoff) {
		return tFenceDAO.getCountByOnoff(onoff);
	}
}
