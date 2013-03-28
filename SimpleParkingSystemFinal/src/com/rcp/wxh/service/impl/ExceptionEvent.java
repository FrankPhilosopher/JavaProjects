package com.rcp.wxh.service.impl;

import java.util.Date;

import com.rcp.wxh.event.Notifier;
import com.rcp.wxh.pojo.TExceptionRecord;

/**
 * 异常信息处理事件
 * 
 * @author wuxuehong 2011-11-15
 * 
 */
public class ExceptionEvent {

	/**
	 * 处理异常信息
	 * 
	 * @param event
	 */
	/**
	 * 触发异常事件响应
	 * 
	 * @param msg
	 */
	public void fireExceptionRecord(String event, String remark) {
		TExceptionRecord ter = new TExceptionRecord();
		ter.setException(event);
		ter.setExceptiontime(new Date());
		ter.setRemark(remark);
		Notifier.getInstance().fireException(ter);
	}

}
