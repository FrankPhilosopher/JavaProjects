package cn.edu.csu.dbhospital.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import cn.edu.csu.dbhospital.ApplicationActionBarAdvisor;
import cn.edu.csu.dbhospital.db.TDirectoryManager;
import cn.edu.csu.dbhospital.pojo.TDirectory;
import cn.edu.csu.dbhospital.pojo.TWorker;

public class SystemUtil {

	public static TWorker LOGIN_WORKER;
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat simpleDateFormatWithWeek = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat simpleDateFormatWithoutYear = new SimpleDateFormat("M月dd日");

	// null value
	public static final int NULL_VALUE = -1;

	// 权限有为1，没有为0
	public static final int RIGHT_YES = 1;
	public static final int RIGHT_NO = 0;

	// 用于对话框的返回值中
	public static final int RESULT_FAIL = 0;

	// 上午班次和下午班次
	public static final int ARRANGEMENT_ONE = 0;// 第一班
	public static final int ARRANGEMENT_TWO = 1;// 第二班

	// 定义性别
	public static final int GANSER_MALE_VALUE = 1;
	public static final int GANSER_FEMALE_VALUE = 0;
	public static final String GANSER_FEMALE = "女";
	public static final String GANSER_MALE = "男";

	// 用在字典中的数据
	public static final String COUNT1 = "COUNT1";
	public static final String COUNT2 = "COUNT2";
	public static final String COUNT3 = "COUNT3";
	public static int COUNT1_VALUE = 1;
	public static int COUNT2_VALUE = 2;
	public static int COUNT3_VALUE = 3;

	// 默认的值
	public static final float EXPENSE_DEFAULT = 200f;
	public static final int LIMIT_DEFAULT = 2;

	public static final int YES_VALUE = 1;
	public static final int NO_VALUE = 0;
	public static final String YES = "是";
	public static final String NO = "否";

	// 格式化日期
	public static String formatDate(Date date) {
		return simpleDateFormat.format(date);
	}

	// 格式化日期
	public static Date parseDate(String date) {
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	// 格式化日期，结果中包含星期
	public static String formatDateWithWeek(Date date) {
		return simpleDateFormatWithWeek.format(date);
	}

	// 格式化日期，结果中不包含年
	public static String formatDateWithoutYear(Date date) {
		return simpleDateFormatWithoutYear.format(date);
	}

	// 随机生成 序号
	public static String buildCardNumber() {
		String cardid = UUID.randomUUID().toString().substring(0, 7);
		return "ZL" + cardid;
	}

	// 随机生成 序号
	public static String buildRegisterNumber() {
		String cardid = UUID.randomUUID().toString().substring(0, 7);
		return "HD" + cardid;
	}
	
	// 显示信息
	public static void showSystemMessage(String message) {
		ApplicationActionBarAdvisor.instance.showMessage(message);
	}

	// 系统启动时初始化数据
	public static void initData() {
		TDirectoryManager directoryManager = new TDirectoryManager();
		ArrayList<TDirectory> list;
		try {
			list = directoryManager.searchByName(COUNT1);
			if (list.size() > 0) {
				COUNT1_VALUE = Integer.valueOf(list.get(0).getValue()).intValue();
			} else {
				directoryManager.add(new TDirectory(COUNT1, COUNT1_VALUE + ""));
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		try {
			list = directoryManager.searchByName(COUNT2);
			if (list.size() > 0) {
				COUNT2_VALUE = Integer.valueOf(list.get(0).getValue()).intValue();
			} else {
				directoryManager.add(new TDirectory(COUNT2, COUNT2_VALUE + ""));
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		try {
			list = directoryManager.searchByName(COUNT3);
			if (list.size() > 0) {
				COUNT3_VALUE = Integer.valueOf(list.get(0).getValue()).intValue();
			} else {
				directoryManager.add(new TDirectory(COUNT3, COUNT3_VALUE + ""));
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

}
