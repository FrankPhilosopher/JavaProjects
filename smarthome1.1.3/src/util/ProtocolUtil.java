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
 * ������Э���йصĹ�����
 * 
 * @author yinger
 * 
 */
public class ProtocolUtil {

	/* ����ͷ��β */
	public final byte FRAME_YONG = 0x59;// Y һ���ֽ�
	public final byte FRAME_JING = 0x4a;// J һ���ֽ�
	public final byte FRAME_END = 0x01;// һ���ֽ�

	/* ������������ RT = request type */
	public final byte RT_CHECK_LOGIN = (byte) 0xF0;// �����¼ һ���ֽ�
	public final byte RT_COMMAND_RESPONSE = (byte) 0xF1;// ����ָ����Ӧ һ���ֽ�
	public final byte RT_STATE_RESPONSE = (byte) 0xF2;// ����״̬���ݷ��� һ���ֽ�
	public final byte RT_HEARTPACKAGE = (byte) 0xF3;// ������ һ���ֽ�
	public final byte RT_GET_GATEWAYIP = (byte) 0xF4;// ��ȡ����IP һ���ֽ�

	// ��¼�����ַ������ LS = login result
	public final byte LS_LOGIN_OK = (byte) 0xE1;
	public final byte LS_INFO_ERROR = (byte) 0xE0;
	public final byte LS_INFO_NOEXIST = (byte) 0xE2;
	public final byte LS_NOT_AVAILABLE = (byte) 0xE3;

	/* �̶����� */
	public final byte LENGTH_COMMAND = 0x04;// һ���ֽ�,Ϊ4
	public final int LENGTH_HEARTPACKAGE = 6;// �������ĳ�����6
	public final int LENGTH_GETIP = 5;// ����ָ����Ӧ����ĳ�����9
	public final int LENGTH_COMMAND_RESPONSE = 9;// ����ָ����Ӧ����ĳ�����9

	/* �豸���� */
	public static final int DEVICETYPE_ROOM = 0x01;// �������Э���У�Ϊ�˴򿪷����������õ�
	public static final int DEVICETYPE_LIGHT = 0x02;// �豸����-�� һ���ֽ�
	public static final int DEVICETYPE_DOOR = 0x03;// �豸����-�� һ���ֽ�
	public static final int DEVICETYPE_SECURITY = 0x04;// �豸����-���� һ���ֽ�
	public static final int DEVICETYPE_SENSOR = 0x05;// �豸����-������ һ���ֽ�
	public static final int DEVICETYPE_APPLIANCE = 0x06;// �豸����-�ҵ� һ���ֽ�
	public static final int DEVICETYPE_CAMERA = 0x07;// �豸����-����ͷ һ���ֽ�
	public static final int DEVICETYPE_SCENE = 0x08;// �龰ģʽ�������豸����
	public static final int DEVICETYPE_SETTING = 0x09;// ���ã������豸����
	public static final int DEVICETYPE_MUSIC = 0x10;//�豸����-����һ���ֽ�

	/* �̶��ķ�����Ϣ */
	public final byte SP_COMMAND_FAIL = (byte) 0xE0;// fail
	public final byte SP_COMMAND_OK = (byte) 0xE1;// ok
	public final byte SP_GATEWAY_ERROR = (byte) 0xE3;// gateway error
	public final byte SP_COMMAND_ERROR = (byte) 0xE2;// �������������ip�ķ����У���ʾ�û������벻����
	public final byte SP_COMMAND_LOCK = (byte) 0xF7;  //Զ������IPʱ����������Զ�̷�������
	
	/* �������� */
	public final byte SP_OTHER_ERROR = (byte) 0xF5;
	public final byte SP_SECURITY_INFO = (byte) 0xF6;
	public final byte SP_GATEWAY_LOCK = (byte) 0xF7;

	/* ������������ */
	public final byte LOCK_E0 = (byte) 0xE0; // ���ؼ�����
	public final byte LOCK_E1 = (byte) 0xE1; // ���س������������
	
	/* �̶������� */
	public static final int COMMAND_EMPTY = -1;// Ĭ��������������Ϊ�գ���������-1
	public static final byte COMMAND_ON = (byte) 0x02;
	public static final byte COMMAND_OFF = (byte) 0x03;

	/* ״̬�̶�ֵ */
	public static final int STATE_ON = 1;// on
	public static final int STATE_OFF = 0;// off
	public static final int STATE_EMPTY = -1;// ���������Э���ж���ģ�Ŀ����Ϊ�����龰ģʽ����Ϊû������

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
	 * ��װ����������
	 */
	public byte[] packHeartPackage() {
		byte[] bytes = new byte[LENGTH_HEARTPACKAGE];
		bytes[0] = FRAME_YONG;
		bytes[1] = FRAME_JING;
		bytes[2] = RT_HEARTPACKAGE;
		bytes[3] = 0;// ������������0
		bytes[4] = FRAME_END;
		return bytes;
	}

	/**
	 * ��װ�����¼��Ӧ�����ָ��ĳ��Ȳ��̶�����Ϊ�û��������볤�Ȳ��̶�
	 */
	public byte[] packCheckLogin(String name, String pwd) {
		int nameLength = name.toCharArray().length;
		int pwdLength = pwd.toCharArray().length;
		int length = nameLength + pwdLength + 2;// ���ݵĳ���
		byte[] bytes = new byte[length + 5];
		bytes[0] = FRAME_YONG;
		bytes[1] = FRAME_JING;
		bytes[2] = RT_CHECK_LOGIN;
		bytes[3] = (byte) length;// ����������
		// name��pwd����string����
		bytes[4] = (byte) nameLength;
		writeString(name, bytes, 5);
		bytes[nameLength + 5] = (byte) pwdLength;
		writeString(pwd, bytes, nameLength + 6);
		bytes[length + 4] = FRAME_END;
		return bytes;
	}

	// ͨ����λ���ַ������Ƶ���������
	private void writeString(String name, byte[] bytes, int start) {
		// ������ʵ����ʹ��string��getBytes��Ȼ��system��copyArray����
		char[] namechars = name.toCharArray();
		for (int i = 0; i < namechars.length; i++) {
			bytes[start + i] = (byte) namechars[i];
		}
	}

	/**
	 * ��װ����ָ����Ӧ���������ָ��ĳ����ǹ̶���
	 */
	public byte[] packInstructionRequest(int command) {
		byte[] bytes = new byte[LENGTH_COMMAND_RESPONSE];
		bytes[0] = FRAME_YONG;
		bytes[1] = FRAME_JING;
		bytes[2] = RT_COMMAND_RESPONSE;
		bytes[3] = LENGTH_COMMAND;
		// command��int����
		writeInteger(command, bytes, 4);
		bytes[8] = FRAME_END;
		return bytes;
	}

	// ͨ����λ�õ�int���ĸ��ֽ�,��start��ʼ
	private void writeInteger(int command, byte[] bytes, int start) {
		bytes[start] = (byte) (command >> 24);
		bytes[start + 1] = (byte) (command >> 16);
		bytes[start + 2] = (byte) (command >> 8);
		bytes[start + 3] = (byte) command;
	}

	// ���ֽ�����ת����int
	private int convertByteArrayToInt(byte[] bytes) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (3 - i) * 8;//����ط�û��Ҫÿ�ζ���4-1��ֱ�Ӹ�Ϊ3 
			value += (bytes[i] & 0x000000FF) << shift;
		}
		return value;
	}

	// ��һ���ֽ�ת��int�����ֽ���int�����һ���ֽ�
	private int convertByteToInt(byte oneByte) {
		return convertByteArrayToInt(new byte[] { 0, 0, 0, oneByte });
	}

	/**
	 * ��װ��ȡ���ص�IP��ַ������
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
	 * ������յ������Է������˵���Ϣ
	 */
	public void processResponseData(byte[] response, int offset, int len) throws Exception {
		byte[][] command = resorve(response, offset, len); // ��� ���Ҵ���ճ������
		if (command == null)
			return;
		for (int i = 0; i < command.length; i++) {
			byte[] res = command[i];
			if (res[0] != FRAME_YONG || res[1] != FRAME_JING)
				continue;
			byte responseType = res[2];// ���ݵڶ����ֽ��ж���Ӧ����
			switch (responseType) {
			case RT_CHECK_LOGIN: // ��¼����
				processCheckLogin(res);
				break;
			case RT_COMMAND_RESPONSE: // ���������
				processCommandResponse(res);
				break;
			case RT_STATE_RESPONSE: // ����״̬���ݷ���
				processStateResponse(res);
				break;
			case RT_GET_GATEWAYIP: // ��ȡ����Զ�̷���IP
				processGetGatewayIp(res);
				break;
			case SP_SECURITY_INFO: // ��ȡ����������Ϣ
				processSecurityInfo(res);
				break;
			case SP_GATEWAY_LOCK:  // ���ر� ��
				processLock(res);  //������,Ҫ������һ���ǵ�¼��һ��������������
				break;
			}
		}
	}

	/**
	 * ���������� len��ȫ�����ݰ�����ʵ����
	 */
	private byte[][] resorve(byte[] data, int offset, int len) {
		//byte[][] command = null;�ĳ��������ʽ
		byte[][] command = {};//���ﲻ�ܸ�ֵΪ�գ�Ϊ�ջ����ָ���쳣
		int size = 0;
		// �������ݰ��ĸ���
//		for (int i = offset; i < len; i++) {
//			if (data[i] == FRAME_YONG && i + 1 < len && data[i + 1] == FRAME_JING)
//				size++;
//		}
//		// �ö�ά���鱣����������ķ�����
//		if (size != 0)
//			command = new byte[size][];
//		size = 0;
//		int length;
//		for (int i = offset; i < len; i++) {
//			if (data[i] == FRAME_YONG && i + 1 < len && data[i + 1] == FRAME_JING) {
//				length = data[i + 3]; // ���ݳ���
//				command[size++] = Arrays.copyOfRange(data, i, i + 5 + length);
//				// offset
//				// i += length + 4;
//			}
//		}
		int length;
		for (int i = offset; i < len; i++) {
			if (data[i] == FRAME_YONG && i + 1 < len && data[i + 1] == FRAME_JING){
				size++;
				command = Arrays.copyOf(command,command.length+1);//sizeÿ����һ��,�͸������һ��λ��
				length = data[i + 3]; // ���ݳ���
				command[size-1] = Arrays.copyOfRange(data, i, i + 5 + length);//���������size-1��Ҳ������command.length-1�����
				// offset
				// i += length + 4;
				
			}
		}
		if(AppliactionUtil.DEBUG){
			if(command != null){
				if(AppliactionUtil.DEBUG) System.out.println("���յ������ݰ�����Ϊ��"+command.length);
				for(int i=0;i<command.length;i++){
					byte[] res = command[i];
					if(AppliactionUtil.DEBUG) System.out.println("��"+(i+1)+"�����ݰ������ǣ�"+Arrays.toString(res));					
				}
			}
		}
		return command;
	}

	/**
	 * ��������Ϣ
	 */
	private void processSecurityInfo(byte[] response) throws Exception {
		// �����豸id
		int deviceId = convertByteArrayToInt(new byte[] { response[5], response[6], response[7], response[8] });
		if(AppliactionUtil.DEBUG) System.out.println(deviceId);
		String name = "";
		if (SecurityPaneController.getInstance() != null) {// �����ж��Ƿ�Ϊnull
			name = SecurityPaneController.getInstance().getSecurityName(deviceId);// �õ�����豸������
		}
		if ("".equalsIgnoreCase(name)) {// nameΪ�գ���ʾ����������豸
			return;
		}
		Date date = new Date();
		final SecurityLog log = new SecurityLog(date, name, "");
		SecurityLogTool.getInstance().saveLog(log);// ���汨����Ϣ���ļ���
		Platform.runLater(new Runnable() {
			public void run() {
				uiUtil.showSystemExceptionMessage(log.getLogMessage());
			}
		});
	}

	/**
	 * �����ȡIP��ַ
	 */
	private void processGetGatewayIp(byte[] response) {
		byte data = response[4];// �������������
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
			communicationUtil.processGetGatewayIp(result); // ����Զ������IP ���
		}
	}

	/**
	 * ��ȡ������������Զ��IP
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
	 * ����״̬������Ϣ
	 */
	private void processStateResponse(byte[] response) {

	}

	/**
	 * ��������ָ����Ӧ������Ϣ
	 */
	private void processCommandResponse(final byte[] response) {
		if(AppliactionUtil.DEBUG) System.out.println(Arrays.toString(response));
		if (response[8] != SP_COMMAND_OK) {
			if (response[8] == SP_COMMAND_FAIL) {
				uiUtil.showSystemExceptionMessage("���Է���������Ϣ�������ʧ�ܣ�");
				return;
			} else if (response[8] == SP_COMMAND_ERROR) {
				uiUtil.showSystemExceptionMessage("���Է���������Ϣ�����ط��������ϣ�");
				return;
			}
		}
		int deviceType = convertByteToInt(response[5]);
		int deviceId = convertByteToInt(response[6]);
		// ָ�����ͣ������ǹ�
		int state = STATE_ON;
		int command = convertByteToInt(response[7]);
		if (command == COMMAND_ON) {
			state = STATE_ON;
		} else if (command == COMMAND_OFF) {
			state = STATE_OFF;
		} else {
			return;// ����Ҫ���Ŵ�������Ҫ�����
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
	 * �����¼�ķ�����Ϣ
	 */
	private void processCheckLogin(byte[] response) {
		byte data = response[5];
		String result = null;
		switch (data) {
		case LS_LOGIN_OK:
			result = "OK";
			break;
		case LS_INFO_ERROR:
			result = "�û��������������";
			break;
		case LS_INFO_NOEXIST:
			result = "�û���Ϣ�����ڣ�";
			break;
		case SP_GATEWAY_ERROR:
			result = "���ط��������ϣ�";
			break;
		}
		if (result != null) {
			communicationUtil.processCheckLogin(result);
		}
	}
	
	/**
	 * ���� ����������һ���ǵ�¼��һ���Ǽ��������Ȼ�� ����
	 * ��ô���������������һ�ֳ��Է�������ǰ�û�
	 * @param response
	 */
	private void processLock(byte[] response){
		if(SystemTool.CURRENT_USER == null){  //��¼ʱ
			byte data = response[5];
			String result = null;
			switch (data) {
			case LOCK_E0:
				result = "���ؼ�������";
				break;
			case LOCK_E1:
				result = "���س������������";
				if(AppliactionUtil.DEBUG) System.out.println("���س���������");
				break;
			}
			if (result != null) {
				communicationUtil.processCheckLogin(result);
			}
		}else{
			uiUtil.showSystemExceptionMessage("���ؼ�����������ϵ��Ӧ�̽�����");
		}
	}

}
