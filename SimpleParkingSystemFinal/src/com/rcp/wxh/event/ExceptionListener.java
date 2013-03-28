package com.rcp.wxh.event;

import com.rcp.wxh.pojo.TExceptionRecord;
import com.rcp.wxh.service.impl.ExceptionRecordService;

/**
 * 异常时间监听
 * 
 * @author wuxuehong 2011-11-15
 * 
 */
public class ExceptionListener extends EventAdapter {

	@Override
	public void onException(TExceptionRecord ter) { // 记录异常事件
		super.onException(ter);
		ExceptionRecordService ers = new ExceptionRecordService();
		try {
			ers.addExceptionRecord(ter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
