package com.rcp.wxh.event;

import java.util.ArrayList;
import java.util.List;

import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TExceptionRecord;
import com.rcp.wxh.pojo.TExpenseRecord;
import com.rcp.wxh.pojo.TSysRecord;

/**
 * 事件通知器
 * 单例
 * 
 * @author wuxuehong 2011-11-15
 * 
 */
public class Notifier {

	private static Notifier notifier = new Notifier();

	private Notifier() {
	} // 禁用构造函数

	/**
	 * 监听集合
	 */
	private List<ParkingSystemListener> listeners = new ArrayList<ParkingSystemListener>();

	/**
	 * 返回唯一实例
	 * 
	 * @return
	 */
	public static Notifier getInstance() {
		if (notifier == null) {
			return new Notifier();
		} else
			return notifier;
	}

	/**
	 * 增加监听器
	 * 
	 * @param listener
	 */
	public void addListener(ParkingSystemListener listener) {
		listeners.add(listener);
	}

	/**
	 * 触发停车事件响应
	 * 
	 * @param tcr
	 */
	public void fireCarPark(TCarRecord tcr) {
		for (ParkingSystemListener l : listeners) {
			l.onCarPark(tcr);
		}
	}

	/**
	 * 触发异常事件响应
	 * 
	 * @param ter
	 */
	public void fireException(TExceptionRecord ter) {
		for (ParkingSystemListener l : listeners) {
			l.onException(ter);
		}
	}

	/**
	 * 触发异常事件响应
	 * 
	 * @param ter
	 */
	public void fireExpense(TExpenseRecord ter) {
		for (ParkingSystemListener l : listeners) {
			l.onExpense(ter);
		}
	}

	/**
	 * 触发系统操作事件响应
	 * 
	 * @param tsr
	 */
	public void fireSystemOperation(TSysRecord tsr) {
		for (ParkingSystemListener l : listeners) {
			l.onSysOperation(tsr);
		}
	}

}
