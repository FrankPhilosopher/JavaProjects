package com.ztdz.tools;

/**
 * Э���װ
 * @author Administrator
 *
 */
public class ProtocolUtil {
	
	private static final String HEAD = "CSZY";
	private static final String END = "\\";
	/**
	 * ��ȡ����ָ��
	 * ��ʵ��һ��CSZYC00001QC2\ ����������
     *��ʵ������CSZYC00001QC3\ һ��������
     *
	 * @param sim   �ն�
	 * @param level  ��������
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
	 * ��ȡ����ָ��
	 * ��ʵ������CSZYC00001QC0\ ������
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
	
	//��ʵ��һ��CSZYC00001ST073189790556\ �������������롿
	/**
	 * ��ȡ��������������
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
	//��ʵ������CSZYC00001SB13873133917,00000000000,00000000000\ ������Ȩ���롿
	/**
	 * ��ȡ������Ȩ���������
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
	//��ʵ������CSZYC00001SI0002\  ����λ����Ϣ�Զ��ϴ�ʱ������
	/**
	 * ��ȡ�����ն˷��ͼ��������
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
	
	
	///////////////////////////ָ�����//////////////////////////////
	/**
	 * �����ն˶�����ָ���Ӧ��
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
