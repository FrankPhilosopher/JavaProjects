package com.rcp.wxh.utils;

import com.rcp.wxh.communicate.CommunicateException;
import com.rcp.wxh.communicate.MachineException;
import com.sun.jna.Native;

/**
 * 与票箱控制器 通信工具。。
 * 
 * @author wuxuehong 2011-11-17
 * 
 */
public class CardComm {

	private static CardCommLibrary cml;

	public static final String COM = "COM3"; // 通信串口

	// 反馈结果
	public static final byte SUCCESS = 0;// 成功
	public static final byte FAIL = 1; // 失败
	// 出入口控制
	public static final byte ENTERANCE = 1; // 入口
	public static final byte EXITANCE = 2; // 出口
	// 读取卡号返回信息
	public static final int EXCEPTION = 0; // 读卡异常
	public static final int GETCARDID = 1; // 读卡成功
	public static final int CARDTOOLESS = 2; // 卡量少
	public static final int CARDBLOCK = 3; // 堵卡
	public static final int CARDEMPTY = 4; // 卡箱空
	public static final int CONNECTFAIL = 5; // 连接错误

	/**
	 * 加载function.dll
	 */
	static {
		Native.setProtected(true);
		cml = (CardCommLibrary) Native.loadLibrary("CardComm", CardCommLibrary.class);
//		System.out.println("********************************load dll!!");
	}

	/**
	 * 初始化与串口连接
	 * true-成功
	 * 
	 * @return
	 * @throws CommunicateException
	 */
	public static boolean init() throws CommunicateException {
		boolean t = cml.InitComm(COM);
		if (!t)
			throw new CommunicateException("窗口初始化失败!请检查通信串口设置!");
		return t;
	}

	/**
	 * 读取终端信息包
	 * b中前四个字节存储卡号
	 * 
	 * @param data
	 * @return int 0-异常 1-卡号信息 2-卡量少提示 3-堵卡 4-卡箱空 5-连接错误
	 * @throws MachineException
	 * @throws CommunicateException
	 */
	public static int read(byte[] data) throws MachineException, CommunicateException {
		int i = cml.WaitForPack(data);
		switch (i) {
		case EXCEPTION:
			throw new MachineException("吐卡机异常!");
		case CARDTOOLESS:
			throw new MachineException("吐卡机中卡量太少!");
		case CARDBLOCK:
			throw new MachineException("吐卡机中卡片阻塞!");
		case CARDEMPTY:
			throw new MachineException("吐卡机中没卡了!");
		case CONNECTFAIL:
			throw new CommunicateException("与卡箱通信异常:获取终端信息失败!");
		}
		return i;
	}

	/**
	 * 卡号后台验证后反馈
	 * 
	 * @param carNum
	 * @param sta
	 *            0:验证成功 1:验证失败
	 * @param device
	 *            1-入口 2-出口
	 * @return
	 * @throws CommunicateException
	 */
	public static boolean write(byte[] carNum, byte sta, byte device) throws CommunicateException {
		boolean t = cml.SendCardNumRep(carNum, sta, device);
		if (!t)
			throw new CommunicateException("与卡箱通信异常:卡片验证反馈失败!");
		return t;
	}

	/**
	 * 设置系统时间
	 * 
	 * @param device
	 *            1-入口 2-出口
	 * @return
	 * @throws CommunicateException
	 */
	public static boolean setTime(byte device) throws CommunicateException {
		boolean t = cml.SetUnixTime(device);
		if (!t)
			throw new CommunicateException("与卡箱通信异常:设置系统时间失败!");
		return t;
	}

	/**
	 * 开闸命令
	 * 
	 * @param device
	 *            1-入口 2-出口
	 * @return
	 * @throws CommunicateException
	 */
	public static boolean stickUp(byte device) throws CommunicateException {
		boolean t = cml.OpenDoor(device);
		if (!t)
			throw new CommunicateException("与卡箱通信异常:通知终端开闸失败!");
		return t;
	}

	/**
	 * 关闸命令
	 * 
	 * @param device
	 *            1-入口 2-出口
	 * @return
	 * @throws CommunicateException
	 */
	public static boolean stickDown(byte device) throws CommunicateException {
		boolean t = cml.CloseDoor(device);
		if (!t)
			throw new CommunicateException("与卡箱通信异常:通知终端落闸失败!");
		return t;
	}

	public static boolean stickStop(byte device) throws CommunicateException {
		boolean t = cml.StopDoor(device);
		if (!t)
			throw new CommunicateException("与卡箱通信异常:通知终端停闸!");
		return t;
	}

	public static boolean setTimeMoney(int minute, int yuan, int jiao) throws CommunicateException {
		System.out.println("时间:" + minute + "\t\t费用:" + yuan + "元\t" + jiao + "分钟!");
		boolean t = cml.SetTimeMoney(minute, yuan, jiao);
		if (!t)
			throw new CommunicateException("与卡箱通信异常:通知停车时间及费用!");
		return t;
	}

	/**
	 * just for test!!!
	 */
	public static void read() {
		boolean init = cml.InitComm("COM1");
		System.out.println(init);
		if (init) {
			while (true) {
				byte[] b = new byte[100];
				int a = cml.WaitForPack(b);
				switch (a) {
				case 0:
					System.out.println("exception");
					break;
				case 1:
					System.out.println(bytes2long(b, 0));
					cml.SendCardNumRep(b, (byte) 0, (byte) 1);
					break;
				case 2:
					System.out.println("卡量少");
					break;
				case 3:
					System.out.println("堵卡或者故障");
					break;
				case 4:
					System.out.println("卡箱空");
					break;
				default:
					System.out.println("未知！");
				}
			}
		}
	}

	/**
	 * 字节数组转换成long
	 * 读取前四个字节
	 * 
	 * @param b
	 * @param startIndex
	 * @return
	 */
	private static long bytes2long(byte[] b, int startIndex) {
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

	public static void main(String args[]) throws InterruptedException, MachineException {
		try {
			boolean t = init();
			System.out.println(t);
//			byte[] b = new byte[100];
//			int a = read(b);
		} catch (CommunicateException e2) {
			e2.printStackTrace();
		}
//       int count = 0;
//		while(true){
//			try {
//				stickUp(EXITANCE);
//				Thread.sleep(100);
//				stickDown(EXITANCE);
//			} catch (CommunicateException e1) {
//				e1.printStackTrace();
//			}
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}

	}

}
