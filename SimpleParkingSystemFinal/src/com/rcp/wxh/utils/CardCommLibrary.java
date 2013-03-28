package com.rcp.wxh.utils;

import com.sun.jna.Library;

/**
 * 与票箱控制板通讯 函数库
 * 
 * @author wuxuehong 2011-11-17
 * 
 */

//DLL中包含了相应的方法
public interface CardCommLibrary extends Library {

	/**
	 * 初始化通信串口
	 * 
	 * @param com
	 *            串口号 入"COM1","COM2"
	 * @return false:初始化失败 true:初始化成功
	 */
	public boolean InitComm(String com);

	/**
	 * 读取接收到的数据包
	 * 
	 * @param data
	 *            出入的数据包 data[0]: 1-入口 2：出口
	 * @return 0:意外错误
	 *         1:卡号信息 data 1-4字节存储卡号
	 *         2:卡量少
	 *         3:堵卡或者故障
	 *         4:卡箱空
	 *         5:与控制器连接错误
	 */
	public int WaitForPack(byte[] data);

	/**
	 * 发送卡号验证反馈
	 * 
	 * @param carNum
	 *            四个字节的卡号
	 * @param sta
	 *            0:验证成功 1：验证失败
	 * @param device
	 *            1：入口 2：出口
	 * @return true:发送成功 false:发送失败
	 */
	public boolean SendCardNumRep(byte[] carNum, byte sta, byte device);

	/**
	 * 设置系统时间
	 * 
	 * @param device
	 *            1:入口 2：出口
	 * @return true:发送成功 false：发送失败
	 */
	public boolean SetUnixTime(byte device);

	/**
	 * 抬闸
	 * 
	 * @param device
	 *            1：入口 2：出口
	 * @return true:发送成功 false:发送失败
	 */
	public boolean OpenDoor(byte device);

	/**
	 * 落闸
	 * 
	 * @param device
	 *            1：入口 2：出口
	 * @return true:发送成功 false:发送失败
	 */
	public boolean CloseDoor(byte device);

	/**
	 * 停闸
	 * 
	 * @param device
	 *            1：入口 2：出口
	 * @return true:发送成功 false:发送失败
	 */
	public boolean StopDoor(byte device);

	/**
	 * 发送停车时间和费用到终端
	 * 
	 * @param minute
	 *            分钟
	 * @param yuan
	 *            元
	 * @param jiao
	 *            角
	 * @return
	 */
	public boolean SetTimeMoney(int minute, int yuan, int jiao);

}
