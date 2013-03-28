package com.rcp.wxh.service.impl;

import com.rcp.wxh.dao.impl.TSystemDao;
import com.rcp.wxh.pojo.TSystem;

public class SystemService extends ExceptionEvent {

	private final String module = "系统管理模块";

	/**
	 * 获取系统信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public TSystem getSystemInfo() throws Exception {
		try {
			TSystemDao tsd = new TSystemDao();
			TSystem system = tsd.getSystemInfo();
			return system;
		} catch (Exception e) {
			fireExceptionRecord("获取系统信息异常!", module);
			throw e;
		}
	}

	/**
	 * 更新系统信息
	 * 
	 * @param system
	 * @throws Exception
	 */
	public void updateSystem(TSystem system) throws Exception {
		TSystemDao tsd = new TSystemDao();
		try {
			tsd.updateSystem(system);
		} catch (Exception e) {
			fireExceptionRecord("更新系统信息异常!", module);
			throw e;
		}
	}

}
