package com.rcp.wxh.utils;

import com.sun.jna.Native;

/**
 * IC卡读卡机 通信工具
 * 
 * @author wuxuehong 2011-11-17
 * 
 */
public class CardUtil {

	private static CardLibrary cl;

	/**
	 * 加载function.dll
	 */
	static {
		Native.setProtected(true);
		cl = (CardLibrary) Native.loadLibrary("function", CardLibrary.class);
	}

	/**
	 * 获取卡号(可用)
	 */
	public static long getCardNum() {
		byte[] snr = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };
		byte[] buffer = new byte[100];
		int a = 1; // initialize as 1 fail
		// 0-success 1-fail
		try {
			a = cl.MF_Read((byte) 0, (byte) 0, (byte) 1, snr, buffer);
		} catch (Exception e) {
			a = 1;
		}
		if (a == 1) {
			return -1;
		} else {
			return bytes2long(snr, 0);
		}
	}

	/**
	 * 字节数组转换成long
	 * @param b
	 * @param startIndex
	 * @return
	 */
	public static long bytes2long(byte[] b, int startIndex) {
		long re = 0;
		for (int i = 0; i < 3; i++) {
			re <<= 8;
		}
		re <<= 8;
		re += 1;
		for (int i = startIndex; i < startIndex + 4; i++) {
			re <<= 8;
			re += b[i];
		}
		return re;
	}

	/**
	 * 获取卡号  //TODO:yinger 没有使用！
	 * @return
	 */
	public static byte[] getCardNumByte() {
		byte[] snr = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };
		byte[] buffer = new byte[100];
		int a = 1; // initialize as 1 fail
		// 0-success 1-fail
		try {
			a = cl.MF_Read((byte) 0, (byte) 0, (byte) 1, snr, buffer);
		} catch (Exception e) {
			a = -1;
		}
		snr[4] = (byte) a;
		return snr;
	}

	public static void main(String args[]) {
		System.out.println(getCardNum());
	}

//	public static void main(String args[]){
//	  byte[] snr = {(byte) 0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
//	  byte[] buffer = new byte[1024];
////	  for(int i=0;i<snr.length;i++)
////		  System.out.print(snr[i]+"  ");
//	  try
//	  {
//	  int a = cl.MF_Read((byte)0, (byte)0, (byte)1, snr, buffer);
//	  System.out.println(a);
//	  }catch(Exception e){
//		  e.printStackTrace();
//	  }
//	  for(int i=0;i<snr.length;i++)
//		  System.out.print(snr[i]+"  ");
//	  System.out.println();
//	  System.out.println(new String(snr,0,4));
//	  for(int i=0;i<20;i++)
//		  System.out.print(buffer[i]+"  ");
////	  int r = cl.add(1, 24);
////	  System.out.println(r);
////		byte[] buffer = new byte[4096];
////		cl.GetSerNum("abc");
//		
////	  byte[] ch = {'c','b',0};
////	  System.out.println(ch.length);
////	  int a =  cl.str(ch);
////	  System.out.println(ch.length);
////	  for(int i=0;i<ch.length;i++)System.out.print(ch[i]+" ");
////	  System.out.println();
////	  System.out.println(a);
//	}
}
