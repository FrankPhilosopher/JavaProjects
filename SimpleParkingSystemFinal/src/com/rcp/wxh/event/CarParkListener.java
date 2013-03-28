package com.rcp.wxh.event;

import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.service.impl.CarRecordService;

/**
 * 停车事件监听器
 * 
 * @author wuxuehong 2011-11-15
 * 
 */
public class CarParkListener extends EventAdapter {

	@Override
	public void onCarPark(TCarRecord tcr) { // 记录停车信息
		super.onCarPark(tcr);
		CarRecordService crs = new CarRecordService();
		try {
			crs.addCarRecord(tcr);
		} catch (Exception e) {
//			e.printStackTrace();		
		}
	}

}
