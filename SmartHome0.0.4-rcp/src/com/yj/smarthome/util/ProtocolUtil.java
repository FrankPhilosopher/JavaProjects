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
 * ������Э���йصĹ�����
 * 
 * @author yinger
 * 
 */
public class ProtocolUtil {

	/* ����ͷ��β */
	public static final byte YONG = 0x59;//Y һ���ֽ�
	public static final byte JING = 0x4a;//J һ���ֽ�
	public static final byte FRAME_END = 0x01;//һ���ֽڣ�Ϊ1

	/* ����Ϊ�� */
	public static final int NO_COMMAND = -1;//Ĭ��������������Ϊ�գ���������-1

	/* ������������ */
	public static final byte CHECK_LOGIN = (byte) 0xF0;//�����¼ һ���ֽ�
	public static final byte INSTRUCTION_REQUEST = (byte) 0xF1;//����ָ����Ӧ  һ���ֽ�
	public static final byte STATE_RESPONSE = (byte) 0xF2;//����״̬����  һ���ֽ�
	public static final byte HEARTPACKAGE = (byte) 0xF3;//������  һ���ֽ�
	public static final byte GET_IP = (byte) 0xF4;//��ȡ����IP һ���ֽ�

	//��¼�����ַ������
	public static final byte LOGIN_OK = (byte) 0xE1;
	public static final byte INFO_ERROR = (byte) 0xE0;
	public static final byte INFO_NOEXIST = (byte) 0xE2;
	//Gateway_Error

	/* ���� */
	public static final int HEARTPACKAGE_LENGTH = 6;//�������ĳ�����6���̶���
	public static final int INSTRUCTION_REQUEST_LENGTH = 9;//����ָ����Ӧ����ĳ�����9���̶���
	public static final byte INSTRUCTION_LENGTH = 0x04;//һ���ֽ�,Ϊ4
	public static final int GETIP_LENGTH = 5;//����ָ����Ӧ����ĳ�����9���̶���

	/* �豸���� */
	public static final int DEVICETYPE_LIGHT = 0x02;//�豸����-��  һ���ֽ�
	public static final int DEVICETYPE_DOOR = 0x03;//�豸����-��  һ���ֽ�
	public static final int DEVICETYPE_SECURITY = 0x04;//�豸����-�ҵ�  һ���ֽ�
	public static final int DEVICETYPE_SENSOR = 0x05;//�豸����-�ҵ�  һ���ֽ�
	public static final int DEVICETYPE_APPLIANCE = 0x06;//�豸����-�ҵ�  һ���ֽ�

	/* �̶��ķ�����Ϣ */
	public static final byte INSTRUCTION_FAIL = (byte) 0xE0;
	public static final byte INSTRUCTION_OK = (byte) 0xE1;
	public static final byte GATEWAY_ERROR = (byte) 0xEE;

	/* �������� */
	public static final byte OTHER_ERROR = (byte) 0xF5;
	public static final byte SECURITY_INFO = (byte) 0xF6;

	//����������
	public static byte[] packHeartPackage() {
		byte[] bytes = new byte[HEARTPACKAGE_LENGTH];
		bytes[0] = YONG;
		bytes[1] = JING;
		bytes[2] = HEARTPACKAGE;
		bytes[3] = 1;//������������1
		bytes[4] = 2;//������㣬���ǲ����Ժͽ�β��ͬ
		bytes[5] = FRAME_END;
		return bytes;
	}

	//��װ �����¼��Ӧ�����ָ��ĳ��Ȳ��̶�
	public static byte[] packCheckLogin(String name, String pwd) {
		int nameLength = name.toCharArray().length;
		int pwdLength = pwd.toCharArray().length;
		int length = nameLength + pwdLength + 2;//���ݵĳ���
		byte[] bytes = new byte[length + 5];
		bytes[0] = YONG;
		bytes[1] = JING;
		bytes[2] = CHECK_LOGIN;
		bytes[3] = (byte) length;//����������
		//name��pwd����string����
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

	//ͨ����λ���ַ������Ƶ���������
	private static void copyString(String name, byte[] bytes, int start) {
		//������ʵ����ʹ��string��getBytes��Ȼ��system��copyArray����
		char[] namechars = name.toCharArray();
		for (int i = 0; i < namechars.length; i++) {
			bytes[start + i] = (byte) namechars[i];
		}
	}

	//��װ ����ָ����Ӧ���������ָ��ĳ����ǹ̶���
	public static byte[] packInstructionRequest(int command) {
		byte[] bytes = new byte[INSTRUCTION_REQUEST_LENGTH];
		bytes[0] = YONG;
		bytes[1] = JING;
		bytes[2] = INSTRUCTION_REQUEST;
		bytes[3] = INSTRUCTION_LENGTH;
		//command��int����
		copyInteger(command, bytes, 4);
		bytes[8] = FRAME_END;

		//test
//		for (int i = 0; i < bytes.length; i++) {
//			System.out.println(bytes[i]);
//		}

		return bytes;
	}

	//ͨ����λ�õ�int���ĸ��ֽ�,��start��ʼ
	private static void copyInteger(int command, byte[] bytes, int start) {
		bytes[start] = (byte) (command >> 24);
		bytes[start + 1] = (byte) (command >> 16);
		bytes[start + 2] = (byte) (command >> 8);
		bytes[start + 3] = (byte) command;
	}

	//���ֽ�����ת����int
	public static int convertByteArrayToInt(byte[] bytes) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;
		}
		return value;
	}

	//��ȡIP��ַ������
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

	//������յ������Է������˵���Ϣ
	//��Ҫע����ǣ�������Դ���һ���յ����������Ϣ�õ�����
	public static void processData(byte[] response) throws Exception {
		System.out.println(Arrays.toString(response));
		if (response[0] != YONG || response[1] != JING) {
			return;
		}
		byte responseType = response[2];
		//�����ȷ�ǿ���д���ģ���ΪЭ���ǹ̶�����
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
		if (response[5 + len] == FRAME_END && response[6 + len] != 0) {//�ж������Ƿ��з�������
			byte[] dest = new byte[1024];
			System.arraycopy(response, len + 6, dest, 0, response.length - (len + 6));
			processData(dest);
		}

	}

	//��������Ϣ
	private static void processSecurityInfo(byte[] response) throws Exception {
		int length = convertByteArrayToInt(new byte[] { 0, 0, 0, response[4] });
		byte[] bytes = new byte[length];
		System.arraycopy(response, 5, bytes, 0, length);
		final String info = new String(bytes, "UTF-8");
		System.out.println("������Ϣ�ǣ�" + info);

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() == null) {//����Ƿ�UI�߳��е�������õ����� null
					return;
				}
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() == null) {
					return;
				}
				//�����Ӧ�ı༭���Ǵ��ŵģ���ô���ɽ����editor������
				WorkbenchPage page = (WorkbenchPage) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				page.getActionBars().getStatusLineManager().setMessage("������Ϣ��" + info);
			}
		});
	}

	//�����ȡIP��ַ
	private static void processGetIp(byte[] response) {
		int a = convertByteArrayToInt(new byte[] { 0, 0, 0, response[4] });
		int b = convertByteArrayToInt(new byte[] { 0, 0, 0, response[5] });
		int c = convertByteArrayToInt(new byte[] { 0, 0, 0, response[6] });
		int d = convertByteArrayToInt(new byte[] { 0, 0, 0, response[7] });
		final String ip = a + "." + b + "." + c + "." + d;
		//��ʹ�����ַ�ʽ
//		synchronized (SystemUtil.message) {
////			SystemUtil.message.append(ip);
//			SystemUtil.message = new StringBuffer(ip);
////			if (data == Login_Ok) {
////				SystemUtil.message.append(SystemUtil.Loginok);//��¼�ɹ�
////			} else if (data == Gateway_Error) {
////				SystemUtil.message.append("���ع��ϣ����������Ƿ�������");
////			}
//			SystemUtil.message.notify();//�������ڵȴ����߳�
//		}

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				System.out.println("response");
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() == null) {//����Ƿ�UI�߳��е�������õ����� null
					return;
				}
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() == null) {
					return;
				}
				//�����Ӧ�ı༭���Ǵ��ŵģ���ô���ɽ����editor������
				WorkbenchPage page = (WorkbenchPage) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IEditorReference[] references = page.getEditorReferences();
				for (int i = 0; i < references.length; i++) {
					IEditorPart editorPart = references[i].getEditor(false);
					IEditorInput input = editorPart.getEditorInput();
					//�������Ӧ���������͵ı༭��
					if (input.getName().equals(SystemUtil.CAMERAMONITOR_CONTROL)) {
						//����������ʾ��ǰ��
						CameraMonitorEditor editor = (CameraMonitorEditor) editorPart;
						editor.setIP(ip);
						page.bringToTop(editorPart);//��ʾ����ǰ��
						return;//�����˳���
					}
				}
				//����Ļ����϶��Ǳ༭���Ѿ��ر��ˣ���ô��Ҫ�޸��豸״̬
			}
		});

	}

	//����״̬������Ϣ
	private static void processStateResponse(byte[] response) {

	}

	//��������ָ����Ӧ������Ϣ
	private static void processInstructionRequest(final byte[] response) {
		final byte deviceType = response[5];
		byte deviceId = response[6];
		final int deviceIdInt = convertByteArrayToInt(new byte[] { 0, 0, 0, deviceId });
		System.out.println("id=" + deviceIdInt);
		//�����������ﴦ�������״̬�����ǣ�ʵ���ϲ����У����ﲻ��������豸�ľ����command
		System.out.println("here");
		//�õ���ǰ�򿪵ı༭�����ⲿ�����ݱ���Ҫ��UI�߳��в��ܹ�ִ��
		//getActiveWorkbenchWindow() Returns null if called from a non-UI thread.
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				System.out.println("response");
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() == null) {//����Ƿ�UI�߳��е�������õ����� null
					return;
				}
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() == null) {
					return;
				}
				//�����Ӧ�ı༭���Ǵ��ŵģ���ô���ɽ����editor������
				WorkbenchPage page = (WorkbenchPage) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IEditorReference[] references = page.getEditorReferences();
				for (int i = 0; i < references.length; i++) {
					IEditorPart editorPart = references[i].getEditor(false);
					IEditorInput input = editorPart.getEditorInput();
					//�������Ӧ���������͵ı༭��
					if (input.getName().equals(SystemUtil.DEVICENAMEARRAY[deviceType])) {
						//����������ʾ��ǰ��
						Map map = ((IDeviceMap) editorPart).getDeviceMap();
						IResponse responseComposite = (IResponse) map.get(deviceIdInt);//���������
						responseComposite.dealWithResponse(response);
						page.bringToTop(editorPart);//�ŵ���ǰ��  �����е����⣬Ӧ�÷��ں���ִ��
						return;//�����˳���
					}
				}
				//����Ļ����϶��Ǳ༭���Ѿ��ر��ˣ���ô��Ҫ�޸��豸״̬
				//ע�⣺���ﲢû���޸��ļ����豸��״̬����Ϊû�б�Ҫ���������п���״̬������������
				SystemUtil.ALL_DEVICE_STATE[deviceType][deviceIdInt] = 1 - SystemUtil.ALL_DEVICE_STATE[deviceType][deviceIdInt];
			}
		});
		System.out.println("ok");
		//���浽�ļ��У�����Ҳ���Ǵ����ˣ����Ƿŵ�ǰ̨��ȥ
	}

	//�����¼�ķ�����Ϣ
	private static void processCheckLogin(byte[] response) {
		byte data = response[5];
//		data = Login_Ok;
		synchronized (SystemUtil.MESSAGE) {
			if (data == LOGIN_OK) {
				SystemUtil.MESSAGE.append(SystemUtil.LOGIN_OK);//��¼�ɹ�
			} else if (data == INFO_ERROR) {
				SystemUtil.MESSAGE.append("�û����������������ȷ���������룡");
			} else if (data == INFO_NOEXIST) {
				SystemUtil.MESSAGE.append("�û���Ϣ�����ڣ���ȷ���������룡");
			} else if (data == GATEWAY_ERROR) {
				SystemUtil.MESSAGE.append("���ع��ϣ����������Ƿ�������");
			}
			SystemUtil.MESSAGE.notify();//�������ڵȴ����߳�
		}
	}

}
