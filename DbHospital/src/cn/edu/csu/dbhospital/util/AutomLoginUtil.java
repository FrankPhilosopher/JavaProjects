package cn.edu.csu.dbhospital.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * �Զ���¼���ƹ�����
 * 
 * @author hjw
 * 
 */

public class AutomLoginUtil {

	/* user�� login -1��ʾ�� 0-��ʾ��ס���� 1-��ʾ�Զ���¼ */
	private String filename = "login.cfg";

	public static final int NoLogin = -1;
	public static final int RemPwd = 0;
	public static final int AutoLogin = 1;

	// ��ָ�����ļ��ж�ȡ��¼�û��Ķ���
	public Object readObject() throws Exception {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
			return null;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Object o = ois.readObject();
			return o;
		} catch (Exception e) {
			throw e;
		}
	}

	// ������д��ָ���ļ�
	public void writeObject(Object obj) throws Exception {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
		} catch (Exception e) {
			throw e;
		}
	}

}
