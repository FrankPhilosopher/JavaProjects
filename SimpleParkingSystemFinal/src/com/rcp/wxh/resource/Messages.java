package com.rcp.wxh.resource;

import org.eclipse.osgi.util.NLS;

/**
 * 字符串变量资源
 * 
 * @author Administrator
 * 
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "messages";

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	/**
	 * for system
	 */
	public static String SOFT_NAME;

	/**
	 * for menu
	 */
	public static String PASSWORD_MANAGE_LABEL; // 个人密码管理
	public static String LOGINOUT_MANAGE_LABEL; // 注销登陆

	public static String LOGIN_LABEL; // 登录名
	public static String ENTER_MANAGE_LABEL; // 车辆入场管理
	public static String LEAVE_MANAGE_LABEL; // 车辆出场管理
	public static String ENTER_EXIT_MANAGE_LABEL; // 车辆出入场管理

	public static String STATISTIC_MANAGE_LABEL; // 数据统计
	public static String PERSONAL_MANAGE_LABEL; // 个人信息管理
	public static String EMP_MANAGE_LABEL; // 员工信息管理

	public static String CARD_MANAGE_LABEL; // 卡片管理
	public static String SYSTEM_MANAGE_LABEL; // 系统管理
	public static String EXCEPTION_MANAGE_LABEL; // 异常记录管理
	public static String EXPENSE_MANAGE_LABEL; // 费用记录管理
	public static String CARRECORD_MANAGE_LABEL; // 车辆出入记录管理
	public static String CARENTER_MANAGE_LABEL; // 当前停车信息查询
	/**
	 * for dialog 
	 */
	public static String LOGIN_TITLE;
	public static String LOGIN_TIPS;
	public static String LOGIN_USERNAME;
	public static String LOGIN_PASSWORD;
	public static String LOGIN_CONFIRM;
	public static String LOGIN_CANCEL;
	public static String LOGIN_REMPSW;
	public static String LOGIN_AUTOLOGIN;
	public static String LOGIN_CANCELLOGIN;

	public static void main(String args[]) {
		char c = 0;
		System.out.println(c);
	}

}
