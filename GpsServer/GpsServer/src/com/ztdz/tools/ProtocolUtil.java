package com.ztdz.tools;

/**
 * 协议封装
 * @author Administrator
 *
 */
public class ProtocolUtil {
	
	private static final String HEAD = "CSZY";
	private static final String END = "\\";
	/**
	 * 获取锁机指令
	 * 【实例一：CSZYC00001QC2\ 二级锁机】
     *【实例二：CSZYC00001QC3\ 一级锁机】
     *
	 * @param sim   终端
	 * @param level  锁机级别
	 * @return
	 */
	public static String getStopOilEleCom(String sim, int level){
		StringBuffer com = new StringBuffer("");
		com.append(HEAD);
		com.append(sim);
		if(level == 1){
			com.append("QC3");
		}else if(level == 2)
			com.append("QC2");
		com.append(END);
		return com.toString();
	}
	
	/**
	 * 获取解锁指令
	 * 【实例三：CSZYC00001QC0\ 解锁】
	 * @param sim
	 * @return
	 */
	public static  String getRecOilEleCom(String sim){
		StringBuffer com = new StringBuffer("");
		com.append(HEAD);
		com.append(sim);
		com.append("QC0");
		com.append(END);
		return com.toString();
	}
	
	//【实例一：CSZYC00001ST073189790556\ 绑定语音报警号码】
	/**
	 * 获取绑定语音报警号码
	 * @sim
	 * @phone 
	 */
	public static String getSetWarnPhoneCom(String sim, String phone){
		StringBuffer com = new StringBuffer("");
		com.append(HEAD);
		com.append(sim);
		com.append("ST"); 
		com.append(phone);
		com.append(END);
		return com.toString();
	}
	//【实例二：CSZYC00001SB13873133917,00000000000,00000000000\ 设置特权号码】
	/**
	 * 获取设置特权号码的命令
	 * @sim
	 * @p1
	 * @p2
	 * @p3
	 */
	public static String getSetPrivilegeCom(String sim, String p1,String p2,String p3){
		StringBuffer com = new StringBuffer("");
		com.append(HEAD);
		com.append(sim);
		com.append("SB");
		com.append(p1+","+p2+","+p3);
		com.append(END);
		return com.toString();
		
	}
	//【实例三：CSZYC00001SI0002\  设置位置信息自动上传时间间隔】
	/**
	 * 获取设置终端发送间隔的命令
	 * @sim
	 * @period
	 */
	public static String getSetSeconCom(String sim, int period){
		StringBuffer com = new StringBuffer("");
		com.append(HEAD);
		com.append(sim);
		com.append("SI");
		if(period<10)com.append("00"+period%10);
		else if(period<100)com.append("0"+period%100);
		else com.append(""+period%1000);
		com.append(END);
		return com.toString();
	}
	
	
	///////////////////////////指令解析//////////////////////////////
	/**
	 * 解析终端对请求指令的应答
	 */
	public static boolean getCommResult(String com){
System.out.println(com+"***************************************************"+com.charAt(com.length()-1));
		if(com.startsWith(HEAD)){
			if(com.charAt(com.length()-2)=='S')
				return true;
		}
		return false;
	}
	
	public static void main(String args[]){
		String a = "abc";
		a = a+END;
		System.out.println(a);
	}

}
