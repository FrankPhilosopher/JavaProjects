package com.rcp.wxh.utils;

import java.util.Date;

import com.rcp.wxh.pojo.TCarEnter;
import com.rcp.wxh.pojo.TCarRecord;
import com.rcp.wxh.pojo.TCard;
import com.rcp.wxh.pojo.TChargeType;

/**
 * 车辆出入场监控工具
 * 1-卡片有效性验证
 * 
 * @author wuxuehong 2011-11-18
 * 
 */
public class CarMonitorUtil {

	/**
	 * 车辆入场 验证该卡的有效性
	 * 
	 * @param cardID
	 *            卡号
	 * @param retMsg
	 *            返回信息
	 * @return true/false
	 */
	public static boolean isAviliable(TCard card, TCarEnter carEnter, StringBuffer retMsg) {
		if (card == null) {
			retMsg.append("该卡未注册!");
			return false;
		}
		if (carEnter != null) {
			retMsg.append("该卡已经检测入场,无法重复入场!");
			return false;
		}
		if (card.getCardstatus().intValue() == TCard.LOCKED) {
			retMsg.append("该卡已锁定,暂无法使用!");
			return false;
		}
		if (card.getCardtype().intValue() == TCard.TEMP_CARD) { // 如果是临时卡则立马放行
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			retMsg.append("验证通过!入场时间" + sdf.format(new Date()));
			return true;
		} else { // 如果是会员卡
			int chargeType = card.getTCardType().getTChargeType().getType().intValue();
			switch (chargeType) {
			case TChargeType.MINUTE:
			case TChargeType.HOUR:
				if (card.getCardprice() <= 0) { // 按分钟/小时收费 验证其余额
					retMsg.append("卡片余额不足!");
					return false;
				}
				break;
			case TChargeType.PERIOD: // 按照服务时间段收费 验证其服务是否过期
				if (card.getCardendtime().getTime() < new Date().getTime()) { // 服务过期
					retMsg.append("该卡服务已经过期!");
					return false;
				}
				break;
			case TChargeType.TIME: // 按照次数计费 验证其剩余停车次数
				if (card.getCardtimes().intValue() < 1) {
					retMsg.append("该卡停车次数已用完!");
					return false;
				}
				break;
			case TChargeType.FREE: // 免费
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				retMsg.append("验证通过!入场时间" + sdf.format(new Date()));
				return true;
			}
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			retMsg.append("验证通过!入场时间" + sdf.format(new Date()));
			return true;
		}
	}

	/**
	 * 根据车辆入场信息 获取车辆停车记录
	 * 注意TCarRecord此时并不在数据库中，它是new出来的！
	 * 
	 * @param tce
	 * @return
	 */
	public static TCarRecord getCarRecord(TCarEnter tce) {
		TCarRecord tcr = new TCarRecord();
		tcr.setCarnumber(tce.getCarnumber()); // 车牌号码
		tcr.setEntertime(tce.getEntertime()); // 入场时间
		tcr.setExittime(new Date()); // 出场时间
		tcr.setTCard(tce.getTCard()); // 卡片信息
		getExpense(tcr);
		return tcr;
	}

	/**
	 * 根据停车记录计算费用
	 * 
	 * @param carRecord
	 * @return
	 */
	private static void getExpense(TCarRecord carRecord) {
		long entertime = carRecord.getEntertime().getTime();
		long exittime = carRecord.getExittime().getTime();
		int period = (int) ((exittime - entertime) / 60000); // 停车时间 mins
		carRecord.setStoptime(period);
		// 获取收费方式
		TChargeType chargeType = carRecord.getTCard().getTCardType().getTChargeType();
		if (period <= chargeType.getMinutecount().intValue()) {
			carRecord.setDueexpense(0f);
			carRecord.setFactexpense(0f);
			carRecord.setRemark("停车时间未超过计费时间!");
		} else {
			long pe = exittime - entertime; // 毫秒
			int count, over, add;
			switch (chargeType.getType().intValue()) {
			case TChargeType.MINUTE: // 按分钟计费
				int seconds = (int) (pe / 1000); // 秒数
				count = seconds / (chargeType.getMinute() * 60); // 计费单位数
				over = seconds % (chargeType.getMinute() * 60); // 超时秒数
				add = over > chargeType.getOverminute().intValue() ? 1 : 0; // 超时 算一个计费单位
				count += add;
				carRecord.setDueexpense(count * chargeType.getFee()); // 应付费用
				carRecord.setFactexpense(count * chargeType.getFee());
				break;
			case TChargeType.HOUR: // 按小时计费
				count = period / (chargeType.getHour() * 60); // 计费单位数
				over = period % (chargeType.getHour() * 60); // 超时分钟数
				add = over > chargeType.getOverhour().intValue() ? 1 : 0;
				count += add;
				carRecord.setDueexpense(count * chargeType.getFee());
				carRecord.setFactexpense(carRecord.getDueexpense());
				break;
			case TChargeType.TIME: // 按次数
				carRecord.setDueexpense(0f);
				carRecord.setFactexpense(0f);
				break;
			case TChargeType.FREE: // 免费
				carRecord.setDueexpense(0f);
				carRecord.setFactexpense(0f);
				break;
			case TChargeType.PERIOD:// 服务期内
				carRecord.setDueexpense(0f);
				carRecord.setFactexpense(0f);
				break;
			}
		}
	}
}
