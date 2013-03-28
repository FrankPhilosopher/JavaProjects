package util;

import java.util.Arrays;
import java.util.Date;

import javafx.application.Platform;
import tool.SecurityLogTool;
import tool.SystemTool;
import beans.SecurityLog;

import communication.CommunicationUtil;

import controller.AppliancePaneController;
import controller.DoorPaneController;
import controller.LightPaneController;
import controller.SecurityPaneController;

/**
 * 处理与协议有关的工具类
 * 
 * @author yinger
 * 
 */
public class ProtocolUtil {

	/* 数据头和尾 */
	public final byte FRAME_YONG = 0x59;// Y 一个字节
	public final byte FRAME_JING = 0x4a;// J 一个字节
	public final byte FRAME_END = 0x01;// 一个字节

	/* 五种请求类型 RT = request type */
	public final byte RT_CHECK_LOGIN = (byte) 0xF0;// 请求登录 一个字节
	public final byte RT_COMMAND_RESPONSE = (byte) 0xF1;// 请求指令响应 一个字节
	public final byte RT_STATE_RESPONSE = (byte) 0xF2;// 请求状态数据返回 一个字节
	public final byte RT_HEARTPACKAGE = (byte) 0xF3;// 心跳包 一个字节
	public final byte RT_GET_GATEWAYIP = (byte) 0xF4;// 获取网关IP 一个字节

	// 登录的五种反馈结果 LS = login result
	public final byte LS_LOGIN_OK = (byte) 0xE1;
	public final byte LS_INFO_ERROR = (byte) 0xE0;
	public final byte LS_INFO_NOEXIST = (byte) 0xE2;
	public final byte LS_NOT_AVAILABLE = (byte) 0xE3;

	/* 固定长度 */
	public final byte LENGTH_COMMAND = 0x04;// 一个字节,为4
	public final int LENGTH_HEARTPACKAGE = 6;// 心跳包的长度是6
	public final int LENGTH_GETIP = 5;// 请求指令响应命令的长度是9
	public final int LENGTH_COMMAND_RESPONSE = 9;// 请求指令响应命令的长度是9

	/* 设备类型 */
	public static final int DEVICETYPE_ROOM = 0x01;// 这个不在协议中，为了打开房间界面而设置的
	public static final int DEVICETYPE_LIGHT = 0x02;// 设备类型-灯 一个字节
	public static final int DEVICETYPE_DOOR = 0x03;// 设备类型-门 一个字节
	public static final int DEVICETYPE_SECURITY = 0x04;// 设备类型-安防 一个字节
	public static final int DEVICETYPE_SENSOR = 0x05;// 设备类型-传感器 一个字节
	public static final int DEVICETYPE_APPLIANCE = 0x06;// 设备类型-家电 一个字节
	public static final int DEVICETYPE_CAMERA = 0x07;// 设备类型-摄像头 一个字节
	public static final int DEVICETYPE_SCENE = 0x08;// 情景模式，不是设备类型
	public static final int DEVICETYPE_SETTING = 0x09;// 配置，不是设备类型
	public static final int DEVICETYPE_MUSIC = 0x10;//设备类型-音乐一个字节

	/* 固定的反馈信息 */
	public final byte SP_COMMAND_FAIL = (byte) 0xE0;// fail
	public final byte SP_COMMAND_OK = (byte) 0xE1;// ok
	public final byte SP_GATEWAY_ERROR = (byte) 0xE3;// gateway error
	public final byte SP_COMMAND_ERROR = (byte) 0xE2;// 这个出现在请求ip的反馈中，表示用户名密码不存在
	public final byte SP_COMMAND_LOCK = (byte) 0xF7;  //远程请求IP时服务器反馈远程访问锁定
	
	/* 反馈类型 */
	public final byte SP_OTHER_ERROR = (byte) 0xF5;
	public final byte SP_SECURITY_INFO = (byte) 0xF6;
	public final byte SP_GATEWAY_LOCK = (byte) 0xF7;

	/* 被锁反馈类型 */
	public final byte LOCK_E0 = (byte) 0xE0; // 网关己被锁
	public final byte LOCK_E1 = (byte) 0xE1; // 网关超过最大连接数
	
	/* 固定的命令 */
	public static final int COMMAND_EMPTY = -1;// 默认情况下如果命令为空，命令码是-1
	public static final byte COMMAND_ON = (byte) 0x02;
	public static final byte COMMAND_OFF = (byte) 0x03;

	/* 状态固定值 */
	public static final int STATE_ON = 1;// on
	public static final int STATE_OFF = 0;// off
	public static final int STATE_EMPTY = -1;// 这个不是在协议中定义的，目的是为了在情景模式中作为没有启用

	private static ProtocolUtil instance;
	private CommunicationUtil communicationUtil;
	private UiUtil uiUtil;

	private ProtocolUtil() {
		communicationUtil = CommunicationUtil.getInstance();
		uiUtil = UiUtil.getInstance();
	}

	public static ProtocolUtil getInstance() {
		if (instance == null)
			instance = new ProtocolUtil();
		return instance;
	}

	/**
	 * 封装心跳包数据
	 */
	public byte[] packHeartPackage() {
		byte[] bytes = new byte[LENGTH_HEARTPACKAGE];
		bytes[0] = FRAME_YONG;
		bytes[1] = FRAME_JING;
		bytes[2] = RT_HEARTPACKAGE;
		bytes[3] = 0;// 数据区长度是0
		bytes[4] = FRAME_END;
		return bytes;
	}

	/**
	 * 封装请求登录响应，这个指令的长度不固定，因为用户名和密码长度不固定
	 */
	public byte[] packCheckLogin(String name, String pwd) {
		int nameLength = name.toCharArray().length;
		int pwdLength = pwd.toCharArray().length;
		int length = nameLength + pwdLength + 2;// 数据的长度
		byte[] bytes = new byte[length + 5];
		bytes[0] = FRAME_YONG;
		bytes[1] = FRAME_JING;
		bytes[2] = RT_CHECK_LOGIN;
		bytes[3] = (byte) length;// 数据区长度
		// name和pwd都是string类型
		bytes[4] = (byte) nameLength;
		writeString(name, bytes, 5);
		bytes[nameLength + 5] = (byte) pwdLength;
		writeString(pwd, bytes, nameLength + 6);
		bytes[length + 4] = FRAME_END;
		return bytes;
	}

	// 通过移位将字符串复制到数据流中
	private void writeString(String name, byte[] bytes, int start) {
		// 这里其实可以使用string的getBytes，然后system的copyArray方法
		char[] namechars = name.toCharArray();
		for (int i = 0; i < namechars.length; i++) {
			bytes[start + i] = (byte) namechars[i];
		}
	}

	/**
	 * 封装请求指令响应，这个请求指令的长度是固定的
	 */
	public byte[] packInstructionRequest(int command) {
		byte[] bytes = new byte[LENGTH_COMMAND_RESPONSE];
		bytes[0] = FRAME_YONG;
		bytes[1] = FRAME_JING;
		bytes[2] = RT_COMMAND_RESPONSE;
		bytes[3] = LENGTH_COMMAND;
		// command是int类型
		writeInteger(command, bytes, 4);
		bytes[8] = FRAME_END;
		return bytes;
	}

	// 通过移位得到int的四个字节,从start开始
	private void writeInteger(int command, byte[] bytes, int start) {
		bytes[start] = (byte) (command >> 24);
		bytes[start + 1] = (byte) (command >> 16);
		bytes[start + 2] = (byte) (command >> 8);
		bytes[start + 3] = (byte) command;
	}

	// 将字节数组转换成int
	private int convertByteArrayToInt(byte[] bytes) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (3 - i) * 8;//这个地方没必要每次多搞个4-1，直接改为3 
			value += (bytes[i] & 0x000000FF) << shift;
		}
		return value;
	}

	// 将一个字节转成int，该字节是int的最后一个字节
	private int convertByteToInt(byte oneByte) {
		return convertByteArrayToInt(new byte[] { 0, 0, 0, oneByte });
	}

	/**
	 * 封装获取网关的IP地址的请求
	 */
	public byte[] packGetGatewayIp(String name, String pwd) {
		int nameLength = name.toCharArray().length;
		int pwdLength = pwd.toCharArray().length;
		int length = nameLength + pwdLength + 7;
		byte[] bytes = new byte[length];
		bytes[0] = FRAME_YONG;
		bytes[1] = FRAME_JING;
		bytes[2] = RT_GET_GATEWAYIP;
		bytes[3] = (byte) (nameLength + pwdLength + 2);
		bytes[4] = (byte) nameLength;
		writeString(name, bytes, 5);
		bytes[5 + nameLength] = (byte) pwdLength;
		writeString(pwd, bytes, 6 + nameLength);
		bytes[length - 1] = FRAME_END;
		return bytes;
	}

	/**
	 * 处理接收到的来自服务器端的信息
	 */
	public void processResponseData(byte[] response, int offset, int len) throws Exception {
		byte[][] command = resorve(response, offset, len); // 解包 并且处理粘包问题
		if (command == null)
			return;
		for (int i = 0; i < command.length; i++) {
			byte[] res = command[i];
			if (res[0] != FRAME_YONG || res[1] != FRAME_JING)
				continue;
			byte responseType = res[2];// 根据第二个字节判断响应类型
			switch (responseType) {
			case RT_CHECK_LOGIN: // 登录反馈
				processCheckLogin(res);
				break;
			case RT_COMMAND_RESPONSE: // 请求命令反馈
				processCommandResponse(res);
				break;
			case RT_STATE_RESPONSE: // 请求状态数据反馈
				processStateResponse(res);
				break;
			case RT_GET_GATEWAYIP: // 获取网关远程访问IP
				processGetGatewayIp(res);
				break;
			case SP_SECURITY_INFO: // 获取安防报警信息
				processSecurityInfo(res);
				break;
			case SP_GATEWAY_LOCK:  // 网关被 锁
				processLock(res);  //处理被锁,要分两种一种是登录，一种是在主界面上
				break;
			}
		}
	}

	/**
	 * 解析反馈包 len是全部数据包的真实长度
	 */
	private byte[][] resorve(byte[] data, int offset, int len) {
		//byte[][] command = null;改成下面的形式
		byte[][] command = {};//这里不能赋值为空，为空会出空指针异常
		int size = 0;
		// 计算数据包的个数
//		for (int i = offset; i < len; i++) {
//			if (data[i] == FRAME_YONG && i + 1 < len && data[i + 1] == FRAME_JING)
//				size++;
//		}
//		// 用二维数组保存解析出来的反馈包
//		if (size != 0)
//			command = new byte[size][];
//		size = 0;
//		int length;
//		for (int i = offset; i < len; i++) {
//			if (data[i] == FRAME_YONG && i + 1 < len && data[i + 1] == FRAME_JING) {
//				length = data[i + 3]; // 数据长度
//				command[size++] = Arrays.copyOfRange(data, i, i + 5 + length);
//				// offset
//				// i += length + 4;
//			}
//		}
		int length;
		for (int i = offset; i < len; i++) {
			if (data[i] == FRAME_YONG && i + 1 < len && data[i + 1] == FRAME_JING){
				size++;
				command = Arrays.copyOf(command,command.length+1);//size每增加一次,就给数组加一个位置
				length = data[i + 3]; // 数据长度
				command[size-1] = Arrays.copyOfRange(data, i, i + 5 + length);//这里可以用size-1，也可以用command.length-1都差不多
				// offset
				// i += length + 4;
				
			}
		}
		if(AppliactionUtil.DEBUG){
			if(command != null){
				if(AppliactionUtil.DEBUG) System.out.println("接收到的数据包个数为："+command.length);
				for(int i=0;i<command.length;i++){
					byte[] res = command[i];
					if(AppliactionUtil.DEBUG) System.out.println("第"+(i+1)+"个数据包内容是："+Arrays.toString(res));					
				}
			}
		}
		return command;
	}

	/**
	 * 处理报警信息
	 */
	private void processSecurityInfo(byte[] response) throws Exception {
		// 报警设备id
		int deviceId = convertByteArrayToInt(new byte[] { response[5], response[6], response[7], response[8] });
		if(AppliactionUtil.DEBUG) System.out.println(deviceId);
		String name = "";
		if (SecurityPaneController.getInstance() != null) {// 首先判断是否为null
			name = SecurityPaneController.getInstance().getSecurityName(deviceId);// 得到这个设备的名称
		}
		if ("".equalsIgnoreCase(name)) {// name为空，表示不存在这个设备
			return;
		}
		Date date = new Date();
		final SecurityLog log = new SecurityLog(date, name, "");
		SecurityLogTool.getInstance().saveLog(log);// 保存报警信息到文件中
		Platform.runLater(new Runnable() {
			public void run() {
				uiUtil.showSystemExceptionMessage(log.getLogMessage());
			}
		});
	}

	/**
	 * 处理获取IP地址
	 */
	private void processGetGatewayIp(byte[] response) {
		byte data = response[4];// 反馈结果的类型
		if(AppliactionUtil.DEBUG) System.out.println("data:"+data);
		String result = null;
		switch (data) {
		case SP_COMMAND_OK:
			result = getGatewayIp(response);
			break;
		case SP_COMMAND_FAIL:
			result = "0";
			break;
		case SP_COMMAND_ERROR:
			result = "2";
			break;
		case SP_GATEWAY_ERROR:
			result = "3";
			break;
		case SP_COMMAND_LOCK:
			result = "7";
			break;
		}
		if (result != null) {
			if(AppliactionUtil.DEBUG) System.out.println("result:"+result );
			communicationUtil.processGetGatewayIp(result); // 处理远程网关IP 结果
		}
	}

	/**
	 * 获取并且设置网关远程IP
	 */
	private String getGatewayIp(byte[] response) {
		int a = convertByteToInt(response[5]);
		int b = convertByteToInt(response[6]);
		int c = convertByteToInt(response[7]);
		int d = convertByteToInt(response[8]);
		String ip = a + "." + b + "." + c + "." + d;
		return ip;
	}

	/**
	 * 处理状态反馈信息
	 */
	private void processStateResponse(byte[] response) {

	}

	/**
	 * 处理请求指令响应反馈信息
	 */
	private void processCommandResponse(final byte[] response) {
		if(AppliactionUtil.DEBUG) System.out.println(Arrays.toString(response));
		if (response[8] != SP_COMMAND_OK) {
			if (response[8] == SP_COMMAND_FAIL) {
				uiUtil.showSystemExceptionMessage("来自服务器的信息：命令发送失败！");
				return;
			} else if (response[8] == SP_COMMAND_ERROR) {
				uiUtil.showSystemExceptionMessage("来自服务器的信息：网关服务器故障！");
				return;
			}
		}
		int deviceType = convertByteToInt(response[5]);
		int deviceId = convertByteToInt(response[6]);
		// 指令类型，开还是关
		int state = STATE_ON;
		int command = convertByteToInt(response[7]);
		if (command == COMMAND_ON) {
			state = STATE_ON;
		} else if (command == COMMAND_OFF) {
			state = STATE_OFF;
		} else {
			return;// 很重要，门窗那里需要这个！
		}
		switch (deviceType) {
		case ProtocolUtil.DEVICETYPE_LIGHT:
			if (LightPaneController.getInstance() != null) {
				LightPaneController.getInstance().doResponse(deviceId, state);
				break;
			}
		case ProtocolUtil.DEVICETYPE_DOOR:
			if (DoorPaneController.getInstance() != null) {
				DoorPaneController.getInstance().doResponse(deviceId, state);
				break;
			}
		case ProtocolUtil.DEVICETYPE_SECURITY:
			if (SecurityPaneController.getInstance() != null) {
				SecurityPaneController.getInstance().doResponse(deviceId, state);
				break;
			}
		case ProtocolUtil.DEVICETYPE_APPLIANCE:
			if (AppliancePaneController.getInstance() != null) {
				AppliancePaneController.getInstance().doResponse(deviceId, state);
				break;
			}
		}
	}

	/**
	 * 处理登录的反馈信息
	 */
	private void processCheckLogin(byte[] response) {
		byte data = response[5];
		String result = null;
		switch (data) {
		case LS_LOGIN_OK:
			result = "OK";
			break;
		case LS_INFO_ERROR:
			result = "用户名或者密码错误！";
			break;
		case LS_INFO_NOEXIST:
			result = "用户信息不存在！";
			break;
		case SP_GATEWAY_ERROR:
			result = "网关服务器故障！";
			break;
		}
		if (result != null) {
			communicationUtil.processCheckLogin(result);
		}
	}
	
	/**
	 * 处理被 锁，分两种一种是登录，一种是己进入界面然后被 锁，
	 * 怎么区分两种情况，第一种尝试方法，当前用户
	 * @param response
	 */
	private void processLock(byte[] response){
		if(SystemTool.CURRENT_USER == null){  //登录时
			byte data = response[5];
			String result = null;
			switch (data) {
			case LOCK_E0:
				result = "网关己被锁定";
				break;
			case LOCK_E1:
				result = "网关超过最大连接数";
				if(AppliactionUtil.DEBUG) System.out.println("网关超过连接数");
				break;
			}
			if (result != null) {
				communicationUtil.processCheckLogin(result);
			}
		}else{
			uiUtil.showSystemExceptionMessage("网关己被锁，请联系供应商解锁！");
		}
	}

}
