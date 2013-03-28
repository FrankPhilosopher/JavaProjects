package com.yj.smarthome.util;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPage;

import com.yj.smarthome.editor.CameraMonitorEditor;
import com.yj.smarthome.interfaces.IDeviceMap;
import com.yj.smarthome.interfaces.IResponse;

/**
 * 处理与协议有关的工具类
 * 
 * @author yinger
 * 
 */
public class ProtocolUtil {

	/* 数据头和尾 */
	public static final byte YONG = 0x59;//Y 一个字节
	public static final byte JING = 0x4a;//J 一个字节
	public static final byte FRAME_END = 0x01;//一个字节，为1

	/* 命令为空 */
	public static final int NO_COMMAND = -1;//默认情况下如果命令为空，命令码是-1

	/* 五种请求类型 */
	public static final byte CHECK_LOGIN = (byte) 0xF0;//请求登录 一个字节
	public static final byte INSTRUCTION_REQUEST = (byte) 0xF1;//请求指令响应  一个字节
	public static final byte STATE_RESPONSE = (byte) 0xF2;//请求状态返回  一个字节
	public static final byte HEARTPACKAGE = (byte) 0xF3;//心跳包  一个字节
	public static final byte GET_IP = (byte) 0xF4;//获取网关IP 一个字节

	//登录的四种反馈结果
	public static final byte LOGIN_OK = (byte) 0xE1;
	public static final byte INFO_ERROR = (byte) 0xE0;
	public static final byte INFO_NOEXIST = (byte) 0xE2;
	//Gateway_Error

	/* 长度 */
	public static final int HEARTPACKAGE_LENGTH = 6;//心跳包的长度是6，固定了
	public static final int INSTRUCTION_REQUEST_LENGTH = 9;//请求指令响应命令的长度是9，固定了
	public static final byte INSTRUCTION_LENGTH = 0x04;//一个字节,为4
	public static final int GETIP_LENGTH = 5;//请求指令响应命令的长度是9，固定了

	/* 设备类型 */
	public static final int DEVICETYPE_LIGHT = 0x02;//设备类型-灯  一个字节
	public static final int DEVICETYPE_DOOR = 0x03;//设备类型-门  一个字节
	public static final int DEVICETYPE_SECURITY = 0x04;//设备类型-家电  一个字节
	public static final int DEVICETYPE_SENSOR = 0x05;//设备类型-家电  一个字节
	public static final int DEVICETYPE_APPLIANCE = 0x06;//设备类型-家电  一个字节

	/* 固定的反馈信息 */
	public static final byte INSTRUCTION_FAIL = (byte) 0xE0;
	public static final byte INSTRUCTION_OK = (byte) 0xE1;
	public static final byte GATEWAY_ERROR = (byte) 0xEE;

	/* 反馈类型 */
	public static final byte OTHER_ERROR = (byte) 0xF5;
	public static final byte SECURITY_INFO = (byte) 0xF6;

	//心跳包数据
	public static byte[] packHeartPackage() {
		byte[] bytes = new byte[HEARTPACKAGE_LENGTH];
		bytes[0] = YONG;
		bytes[1] = JING;
		bytes[2] = HEARTPACKAGE;
		bytes[3] = 1;//数据区长度是1
		bytes[4] = 2;//内容随便，但是不可以和结尾相同
		bytes[5] = FRAME_END;
		return bytes;
	}

	//封装 请求登录响应，这个指令的长度不固定
	public static byte[] packCheckLogin(String name, String pwd) {
		int nameLength = name.toCharArray().length;
		int pwdLength = pwd.toCharArray().length;
		int length = nameLength + pwdLength + 2;//数据的长度
		byte[] bytes = new byte[length + 5];
		bytes[0] = YONG;
		bytes[1] = JING;
		bytes[2] = CHECK_LOGIN;
		bytes[3] = (byte) length;//数据区长度
		//name和pwd都是string类型
		//name
		bytes[4] = (byte) nameLength;
		copyString(name, bytes, 5);
		//password
		bytes[nameLength + 5] = (byte) pwdLength;
		copyString(pwd, bytes, nameLength + 6);
		bytes[length + 4] = FRAME_END;

		//test
//		for (int i = 0; i < bytes.length; i++) {
//			System.out.println(bytes[i]);
//		}

		return bytes;
	}

	//通过移位将字符串复制到数据流中
	private static void copyString(String name, byte[] bytes, int start) {
		//这里其实可以使用string的getBytes，然后system的copyArray方法
		char[] namechars = name.toCharArray();
		for (int i = 0; i < namechars.length; i++) {
			bytes[start + i] = (byte) namechars[i];
		}
	}

	//封装 请求指令响应，这个请求指令的长度是固定的
	public static byte[] packInstructionRequest(int command) {
		byte[] bytes = new byte[INSTRUCTION_REQUEST_LENGTH];
		bytes[0] = YONG;
		bytes[1] = JING;
		bytes[2] = INSTRUCTION_REQUEST;
		bytes[3] = INSTRUCTION_LENGTH;
		//command是int类型
		copyInteger(command, bytes, 4);
		bytes[8] = FRAME_END;

		//test
//		for (int i = 0; i < bytes.length; i++) {
//			System.out.println(bytes[i]);
//		}

		return bytes;
	}

	//通过移位得到int的四个字节,从start开始
	private static void copyInteger(int command, byte[] bytes, int start) {
		bytes[start] = (byte) (command >> 24);
		bytes[start + 1] = (byte) (command >> 16);
		bytes[start + 2] = (byte) (command >> 8);
		bytes[start + 3] = (byte) command;
	}

	//将字节数组转换成int
	public static int convertByteArrayToInt(byte[] bytes) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;
		}
		return value;
	}

	//获取IP地址的请求
	public static byte[] packGetIp() {
		byte[] bytes = new byte[GETIP_LENGTH];
		bytes[0] = YONG;
		bytes[1] = JING;
		bytes[2] = GET_IP;
		bytes[3] = 0;
		bytes[4] = FRAME_END;
		return bytes;
	}

	public static void main(String[] args) {
//		int command = 131586;
//		byte[] bytes = packInstructionRequest(command);
//		String nameString = "a0001";
//		String pwdString = "123456";
//		byte[] bytes = ProtocolUtil.packCheckLogin(nameString, pwdString);
//		for (int i = 0; i < bytes.length; i++) {
//			System.out.println(bytes[i]);
//		}
//		byte[] bytes = {0,2,2,4};
//		System.out.println(convertByteArrayToInt(bytes));
	}

	//处理接收到的来自服务器端的信息
	//需要注意的是：这里可以处理一次收到多个反馈信息得到处理！
	public static void processData(byte[] response) throws Exception {
		System.out.println(Arrays.toString(response));
		if (response[0] != YONG || response[1] != JING) {
			return;
		}
		byte responseType = response[2];
		//这里的确是可以写死的，因为协议是固定的了
		if (responseType == CHECK_LOGIN) {
			processCheckLogin(response);
		} else if (responseType == INSTRUCTION_REQUEST) {
			processInstructionRequest(response);
		} else if (responseType == STATE_RESPONSE) {
			processStateResponse(response);
		} else if (responseType == GET_IP) {
			processGetIp(response);
		} else if (responseType == SECURITY_INFO) {
			processSecurityInfo(response);
		}

		int len = convertByteArrayToInt(new byte[] { 0, 0, 0, response[3] });
		if (response[5 + len] == FRAME_END && response[6 + len] != 0) {//判断下面是否还有反馈数据
			byte[] dest = new byte[1024];
			System.arraycopy(response, len + 6, dest, 0, response.length - (len + 6));
			processData(dest);
		}

	}

	//处理报警信息
	private static void processSecurityInfo(byte[] response) throws Exception {
		int length = convertByteArrayToInt(new byte[] { 0, 0, 0, response[4] });
		byte[] bytes = new byte[length];
		System.arraycopy(response, 5, bytes, 0, length);
		final String info = new String(bytes, "UTF-8");
		System.out.println("报警信息是：" + info);

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() == null) {//如果是非UI线程中调用这里得到的是 null
					return;
				}
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() == null) {
					return;
				}
				//如果相应的编辑器是打开着的，那么就由界面的editor来处理
				WorkbenchPage page = (WorkbenchPage) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				page.getActionBars().getStatusLineManager().setMessage("报警信息：" + info);
			}
		});
	}

	//处理获取IP地址
	private static void processGetIp(byte[] response) {
		int a = convertByteArrayToInt(new byte[] { 0, 0, 0, response[4] });
		int b = convertByteArrayToInt(new byte[] { 0, 0, 0, response[5] });
		int c = convertByteArrayToInt(new byte[] { 0, 0, 0, response[6] });
		int d = convertByteArrayToInt(new byte[] { 0, 0, 0, response[7] });
		final String ip = a + "." + b + "." + c + "." + d;
		//不使用这种方式
//		synchronized (SystemUtil.message) {
////			SystemUtil.message.append(ip);
//			SystemUtil.message = new StringBuffer(ip);
////			if (data == Login_Ok) {
////				SystemUtil.message.append(SystemUtil.Loginok);//登录成功
////			} else if (data == Gateway_Error) {
////				SystemUtil.message.append("网关故障！请检查网关是否正常！");
////			}
//			SystemUtil.message.notify();//唤醒正在等待的线程
//		}

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				System.out.println("response");
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() == null) {//如果是非UI线程中调用这里得到的是 null
					return;
				}
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() == null) {
					return;
				}
				//如果相应的编辑器是打开着的，那么就由界面的editor来处理
				WorkbenchPage page = (WorkbenchPage) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IEditorReference[] references = page.getEditorReferences();
				for (int i = 0; i < references.length; i++) {
					IEditorPart editorPart = references[i].getEditor(false);
					IEditorInput input = editorPart.getEditorInput();
					//如果是相应的设置类型的编辑器
					if (input.getName().equals(SystemUtil.CAMERAMONITOR_CONTROL)) {
						//打开来，并显示在前面
						CameraMonitorEditor editor = (CameraMonitorEditor) editorPart;
						editor.setIP(ip);
						page.bringToTop(editorPart);//显示到最前面
						return;//可以退出啦
					}
				}
				//否则的话，肯定是编辑器已经关闭了，那么就要修改设备状态
			}
		});

	}

	//处理状态反馈信息
	private static void processStateResponse(byte[] response) {

	}

	//处理请求指令响应反馈信息
	private static void processInstructionRequest(final byte[] response) {
		final byte deviceType = response[5];
		byte deviceId = response[6];
		final int deviceIdInt = convertByteArrayToInt(new byte[] { 0, 0, 0, deviceId });
		System.out.println("id=" + deviceIdInt);
		//本来想在这里处理等最后的状态，但是，实际上不可行，这里不清楚具体设备的具体的command
		System.out.println("here");
		//得到当前打开的编辑器，这部分内容必须要在UI线程中才能够执行
		//getActiveWorkbenchWindow() Returns null if called from a non-UI thread.
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				System.out.println("response");
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() == null) {//如果是非UI线程中调用这里得到的是 null
					return;
				}
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() == null) {
					return;
				}
				//如果相应的编辑器是打开着的，那么就由界面的editor来处理
				WorkbenchPage page = (WorkbenchPage) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IEditorReference[] references = page.getEditorReferences();
				for (int i = 0; i < references.length; i++) {
					IEditorPart editorPart = references[i].getEditor(false);
					IEditorInput input = editorPart.getEditorInput();
					//如果是相应的设置类型的编辑器
					if (input.getName().equals(SystemUtil.DEVICENAMEARRAY[deviceType])) {
						//打开来，并显示在前面
						Map map = ((IDeviceMap) editorPart).getDeviceMap();
						IResponse responseComposite = (IResponse) map.get(deviceIdInt);//这里出错了
						responseComposite.dealWithResponse(response);
						page.bringToTop(editorPart);//放到最前面  这里有点问题，应该放在后面执行
						return;//可以退出啦
					}
				}
				//否则的话，肯定是编辑器已经关闭了，那么就要修改设备状态
				//注意：这里并没有修改文件中设备的状态，因为没有必要！本次运行看的状态都存在数组中
				SystemUtil.ALL_DEVICE_STATE[deviceType][deviceIdInt] = 1 - SystemUtil.ALL_DEVICE_STATE[deviceType][deviceIdInt];
			}
		});
		System.out.println("ok");
		//保存到文件中，这里也还是处理不了，还是放到前台中去
	}

	//处理登录的反馈信息
	private static void processCheckLogin(byte[] response) {
		byte data = response[5];
//		data = Login_Ok;
		synchronized (SystemUtil.MESSAGE) {
			if (data == LOGIN_OK) {
				SystemUtil.MESSAGE.append(SystemUtil.LOGIN_OK);//登录成功
			} else if (data == INFO_ERROR) {
				SystemUtil.MESSAGE.append("用户名或者密码错误！请确认您的输入！");
			} else if (data == INFO_NOEXIST) {
				SystemUtil.MESSAGE.append("用户信息不存在！请确认您的输入！");
			} else if (data == GATEWAY_ERROR) {
				SystemUtil.MESSAGE.append("网关故障！请检查网关是否正常！");
			}
			SystemUtil.MESSAGE.notify();//唤醒正在等待的线程
		}
	}

}
