package com.rcp.wxh.event;

import com.rcp.wxh.pojo.TExpenseRecord;
import com.rcp.wxh.service.impl.ExpenseRecordService;

/**
 * 费用事件监听器
 * 
 * @author wuxuehong 2011-11-15
 * 
 */
public class ExpenseListener extends EventAdapter {

	@Override
	public void onExpense(TExpenseRecord ter) {
		super.onExpense(ter);
		ExpenseRecordService ers = new ExpenseRecordService();
		try {
			ers.addExpenseRecord(ter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
