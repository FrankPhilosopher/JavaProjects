package com.rcp.wxh.event;

import com.rcp.wxh.pojo.TSysRecord;
import com.rcp.wxh.service.impl.SysRecordService;

/**
 * 系统操作事件监听
 * 
 * @author wuxuehong 2011-11-15
 * 
 */
public class SystemOperationListener extends EventAdapter {

	@Override
	public void onSysOperation(TSysRecord tsr) {
		super.onSysOperation(tsr);
		SysRecordService srs = new SysRecordService();
		try {
			srs.addSysRecord(tsr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
