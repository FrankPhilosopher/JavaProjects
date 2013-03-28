package com.rcp.wxh.event;

import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TExceptionRecord;
import com.rcp.wxh.pojo.TExpenseRecord;
import com.rcp.wxh.pojo.TSysRecord;

public abstract class EventAdapter implements ParkingSystemListener {

	public void onCarPark(TCarRecord tcr) {

	}

	public void onException(TExceptionRecord ter) {

	}

	public void onExpense(TExpenseRecord ter) {

	}

	public void onSysOperation(TSysRecord tsr) {

	}

}
