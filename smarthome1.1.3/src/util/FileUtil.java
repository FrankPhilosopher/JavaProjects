package util;

import java.io.File;

import tool.SystemTool;

/**
 * ר�����������ļ��Ĺ�����<br/>
 * ͬʱ���������˱���Ŀ�ĺܶ���ļ����ļ����йصĹ̶����ַ���
 * 
 * @author yinger
 * 
 */
public class FileUtil {

	public static final String SCENE_DIRECTORY = "scene" + File.separator;// ��������λ��Ҫ���ݾ����½���û���������
	public static final String CAMERA_DIRECTORY = "camera" + File.separator;
	public static final String DOWN_DIRECTORY = "down" + File.separator;
	public static final String LOCAL_DIRECTORY = "local" + File.separator;
	public static final String APPLIANCE_DIRECTORY = "appliance" + File.separator;

	public static final String PCVERSION_XML = "pcversion.xml";// �汾�����ļ�

	public static final String VERSION_XML = "version.xml";// �汾�����ļ�//�ɴ����ط���������
	public static final String STATE_XML = "state.xml";// ״̬�ļ�//�ɴ����ط���������
	public static final String SYSTEM_XML = "system.xml";// ϵͳ�������ļ�--root
	public static final String LOGIN_CONFIG = "login.cfg";// ��½�����ļ�

	public static final String CONFIG_XML = "config.xml";// �û��������ļ�
	public static final String LOG_XML = "log.xml";// ������־�ļ�
	public static final String LIGHT_XML = "lightcontrolview.xml";// �ƿ��ļ�//�ɴ����ط���������
	public static final String DOOR_XML = "doorwindowsview.xml";// �Ŵ��ļ�//�ɴ����ط���������
	public static final String SECURITY_XML = "securityview.xml";// �����ļ�//�ɴ����ط���������
	public static final String SENSOR_XML = "sensorview.xml";// �������ļ�//�ɴ����ط���������
	public static final String APPLIANCE_XML = "applianceview.xml";// �ҵ��ļ�//�ɴ����ط���������
	public static final String SCENEMODE_DIRECTORY = "scenemode" + File.separator;
	public static final String ROOM_XML = "room.xml";// �����ļ�

	private static FileUtil instance = null;

	public static FileUtil getInstance() {
		if (instance == null)
			instance = new FileUtil();
		return instance;
	}

	private FileUtil() {// ������ҪĿ¼����������ڵĻ���
		File downDir = new File(DOWN_DIRECTORY);// ����down�ļ���
		if (!downDir.exists()) {
			downDir.mkdir();
		}
		File localDir = new File(LOCAL_DIRECTORY);// ����local�ļ���
		if (!localDir.exists()) {
			localDir.mkdir();
		}
	}

	/**
	 * �õ��û���Ŀ¼
	 */
	private String getUserRoot() {
		if (SystemTool.CURRENT_USER != null) {
			File file = new File(LOCAL_DIRECTORY + SystemTool.CURRENT_USER.getName() + File.separator);
			if (!file.exists() || file.isFile()) {
				file.mkdir();
			}
			return LOCAL_DIRECTORY + SystemTool.CURRENT_USER.getName() + File.separator;
		}
		return null;
	}

	/**
	 * �õ��û���ĳ���ļ�·��
	 */
	public String getUserFile(String filename) {
		return getUserRoot() + filename;
	}

	/**
	 * �õ��û���ĳ���ļ��У���������ڵĻ��ʹ���
	 */
	public String getUserDirectory(String dirname) {
		String path = getUserRoot() + dirname + File.separator;
		File file = new File(getUserRoot() + dirname + File.separator);
		if (!file.exists()) {
			file.mkdir();
		}
		return path;
	}

	/**
	 * �õ�״̬�ļ�����һ���Ǵ����down�ļ�����
	 */
	public String getStateXmlFile() {
		return DOWN_DIRECTORY + STATE_XML;
	}

	/**
	 * �õ�down�е�version.xml
	 */
	public String getDownVersionXmlFile() {
		return DOWN_DIRECTORY + VERSION_XML;
	}

	/**
	 * �õ�local�е�version.xml
	 */
	public String getLocalVersionXmlFile() {
		return LOCAL_DIRECTORY + VERSION_XML;
	}

	/**
	 * ��ʼ������
	 */
	public void resetConfig() {
		deleteDirectory(getUserRoot());// ɾ���û��ĸ�Ŀ¼�µ��ļ�
		deleteFile(LOG_XML);
		deleteFile(SYSTEM_XML);
		deleteFile(LOGIN_CONFIG);
	}

	/**
	 * ɾ�������ļ�
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		if (file.isFile() && file.exists()) {// ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * ɾ��Ŀ¼�µ��ļ�
	 */
	public boolean deleteDirectory(String sPath) {
		boolean flag = true;
		if (!sPath.endsWith(File.separator)) {// ���sPath�����ļ��ָ�����β���Զ�����ļ��ָ���
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {// ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
			return false;
		}
		File[] files = dirFile.listFiles();// ɾ���ļ����µ������ļ�(������Ŀ¼)
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {// ɾ�����ļ�
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {// ɾ����Ŀ¼
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		return true;
	}

}
