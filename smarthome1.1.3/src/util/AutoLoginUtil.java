package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import beans.User;

import communication.CommunicationUtil;

/**
 * �Զ���¼���ƹ�����<br/>
 * ����ģʽ
 * 
 * @author yinger
 * 
 */

public class AutoLoginUtil {

	private static AutoLoginUtil instance;

	private AutoLoginUtil() {
	}

	public static AutoLoginUtil getInstance() {
		if (instance == null) {
			instance = new AutoLoginUtil();
		}
		return instance;
	}

	/* user�� login -1��ʾ�� 0-��ʾ��ס���� 1-��ʾ�Զ���¼ */
	private String filename = FileUtil.LOGIN_CONFIG;// ���Ƶ�¼���ļ�

	public static final int NoLogin = -1;
	public static final int RemPwd = 0;
	public static final int AutoLogin = 1;

	/**
	 * ��ָ�����ļ��ж�ȡ��¼�û��Ķ���
	 */
	public User readObject() throws Exception {
		File file = new File(filename);// �ļ�����Ŀ�ĸ�Ŀ¼��
		if (!file.exists()) {
			return null;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Object o = ois.readObject();
			ois.close();
			User user = (User) o;
			if(AppliactionUtil.DEBUG) System.out.println("user.getPwd"+user.getPassword());
			user.setPassword(new String(BASE64Util.decryptBASE64(user.getPassword())));
			if(AppliactionUtil.DEBUG) System.out.println("user.getPwd"+user.getPassword());
			return user;
		} catch (Exception e) {
			throw new Exception("��ȡ�û���Ϣʧ�ܣ�");
		}
	}

	/**
	 * ������д��ָ���ļ�
	 */
	public void writeObject(User obj) throws Exception {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		String pwd = obj.getPassword();
		obj.setPassword(BASE64Util.encryptBASE64(pwd.getBytes()));
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
			obj.setPassword(pwd);// �����������ģ����浽�ļ���ʱobj�������Ǽ����˵ģ�����ʱ����û�м��ܵ�
		} catch (Exception e) {
			throw new Exception("�����û���Ϣʧ�ܣ�");
		}
	}

	// test
	public static void main(String args[]) throws Exception {
		AutoLoginUtil alu = new AutoLoginUtil();
		User user = new User("admin", "admin");
		user.setAutodownable(false);
		user.setFirstRun(true);
		user.setLoginType(CommunicationUtil.GATEWAY_LOGIN);
		user.setLogin(NoLogin);
		alu.writeObject(user);
		Object object = alu.readObject();
		if(AppliactionUtil.DEBUG) System.out.println(((User) object).getPassword());
		if(AppliactionUtil.DEBUG) System.out.println(((User) object).isAutodownable());
	}

}
