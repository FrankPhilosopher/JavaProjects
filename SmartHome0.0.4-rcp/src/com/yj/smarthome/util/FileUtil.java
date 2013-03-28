package com.yj.smarthome.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import org.eclipse.swt.graphics.Image;

/**
 * ר�����������ļ��Ĺ�����<br/>
 * ͬʱ���������˱���Ŀ�ĺܶ���ļ����ļ����йصĹ̶����ַ���
 * 
 * @author yinger
 * 
 */
public class FileUtil {

	public static HashMap<Integer, String> DEVICEID_XML;//������������һ����豸idȡʲô���ֵ�xml�ļ�
	public static final String DOWN_DIRECTORY = "down" + File.separator;
	public static final String LOCAL_DIRECTORY = "local" + File.separator;
	public static final String SCENE_DIRECTORY = "scene" + File.separator;
	public static final String VERSION_XML = "version.xml";
	public static final String STATE_XML = "state.xml";
	public static final String SYSTEM_XML = "system.xml";//ϵͳ�������ļ�

	//��̬����飬���й����ļ������Ա��ڷ�ɢ�������Է��ڸ��Ե�bean��
	static {
		DEVICEID_XML = new HashMap<Integer, String>();
//		DeviceId_XmlFile.put(Version_Xml, "version.xml");
//		DeviceId_XmlFile.put(State_Xml, "state.xml");
		DEVICEID_XML.put(ProtocolUtil.DEVICETYPE_LIGHT, "lightcontrolview.xml");//2
		DEVICEID_XML.put(ProtocolUtil.DEVICETYPE_DOOR, "doorwindowsview.xml");//3
		DEVICEID_XML.put(ProtocolUtil.DEVICETYPE_SECURITY, "securityview.xml");//4
		DEVICEID_XML.put(ProtocolUtil.DEVICETYPE_SENSOR, "sensorview.xml");//5
		DEVICEID_XML.put(ProtocolUtil.DEVICETYPE_APPLIANCE, "applianceview.xml");//6
	}

	//�õ����ص�ĳ���豸��xml�ļ�
	public static String getLocalXmlFile(int id) {
		return LOCAL_DIRECTORY + DEVICEID_XML.get(id);//E:\Java_Study\Eclipse\Eclipse for rcp\eclipse_rcp\local\lightcontrolview.xml
	}

	//�õ�����Ŀ¼�е�ĳ���豸��xml�ļ�
	public static String getDownXmlFile(int id) {
		return DOWN_DIRECTORY + DEVICEID_XML.get(id);//E:\Java_Study\Eclipse\Eclipse for rcp\eclipse_rcp\down\lightcontrolview.xml
	}

	//�ܶ�ľ�̬��������Ϊ�˷���ά���ķ���
	//�õ�״̬�ļ�����һ���Ǵ����down�ļ�����  TODO�����ĳһ������û�гɹ�����������ļ�û����ô�죿״̬Ĭ��ֵ
	public static String getStateXmlFile() {
		return DOWN_DIRECTORY + STATE_XML;
	}

	//�õ�down�е�version.xml
	public static String getDownVersionXmlFile() {
		return DOWN_DIRECTORY + VERSION_XML;
	}

	//�õ�local�е�version.xml
	public static String getLocalVersionXmlFile() {
		return LOCAL_DIRECTORY + VERSION_XML;
	}

	//test
	public static void main(String[] args) {
//		try {
//			copyFile("down/state.xml", "local/state.xml");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		String fileName = FileUtil.DEVICEID_XML.get(ProtocolUtil.DEVICETYPE_LIGHT);
		System.out.println(fileName.substring(0, fileName.indexOf(".xml")));//lightcontrolview

	}

	//����ͼ��ָ����λ��
	public static void saveImage(String path, Image image) {

	}

	//�����ļ���Դ�ļ���Ŀ���ļ���
	public static void copyFile(String srcFile, String desFile) throws Exception {
		try {
			FileInputStream fis = new FileInputStream(srcFile);//���ԭ�ļ������ڵĻ����׳��쳣
			FileOutputStream fos = new FileOutputStream(desFile);//���Ŀ���ļ������ڵĻ����Զ��½�һ���ļ�
			byte[] bytes = new byte[1024];
			int len = fis.read(bytes);
			while (len != -1) {//eof ����ֵ��-1��������0
				fos.write(bytes, 0, len);
				len = fis.read(bytes);
			}
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("�ļ�" + srcFile + "����ʧ��!");
		}
	}

}
