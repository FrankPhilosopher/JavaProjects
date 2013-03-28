package com.rcp.wxh.event;

import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TExceptionRecord;
import com.rcp.wxh.pojo.TExpenseRecord;
import com.rcp.wxh.pojo.TSysRecord;

/**
 * 系统事件监听接口
 * 主要监听发生事件并将其持久化
 * 
 * @author wuxuehong 2011-11-2
 * 
 */
public interface ParkingSystemListener {

	/**
	 * 系统处理异常记录 当产生异常时触发该事件
	 * (主要是数据库操作异常信息记录)
	 * 
	 * @param ter
	 */
	public void onException(TExceptionRecord ter);

	/**
	 * 当产生费用记录时 触发该事件
	 * （临时卡 会员卡收费记录）
	 * 
	 * @param ter
	 */
	public void onExpense(TExpenseRecord ter);

	/**
	 * 车辆进出场处理时触发本事件
	 * （车辆进出场时日志记录）
	 * 
	 * @param tcr
	 */
	public void onCarPark(TCarRecord tcr);

	/**
	 * 当产生需要记录的系统日志时 触发该事件
	 * 
	 * @param tsr
	 */
	public void onSysOperation(TSysRecord tsr);
}
