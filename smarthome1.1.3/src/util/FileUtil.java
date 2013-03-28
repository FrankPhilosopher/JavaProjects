package util;

import java.io.File;

import tool.SystemTool;

/**
 * 专门用来处理文件的工具类<br/>
 * 同时它还保存了本项目的很多和文件及文件夹有关的固定的字符串
 * 
 * @author yinger
 * 
 */
public class FileUtil {

	public static final String SCENE_DIRECTORY = "scene" + File.separator;// 这两个的位置要根据具体登陆的用户来决定！
	public static final String CAMERA_DIRECTORY = "camera" + File.separator;
	public static final String DOWN_DIRECTORY = "down" + File.separator;
	public static final String LOCAL_DIRECTORY = "local" + File.separator;
	public static final String APPLIANCE_DIRECTORY = "appliance" + File.separator;

	public static final String PCVERSION_XML = "pcversion.xml";// 版本控制文件

	public static final String VERSION_XML = "version.xml";// 版本控制文件//可从网关服务器下载
	public static final String STATE_XML = "state.xml";// 状态文件//可从网关服务器下载
	public static final String SYSTEM_XML = "system.xml";// 系统的配置文件--root
	public static final String LOGIN_CONFIG = "login.cfg";// 登陆配置文件

	public static final String CONFIG_XML = "config.xml";// 用户的配置文件
	public static final String LOG_XML = "log.xml";// 报警日志文件
	public static final String LIGHT_XML = "lightcontrolview.xml";// 灯控文件//可从网关服务器下载
	public static final String DOOR_XML = "doorwindowsview.xml";// 门窗文件//可从网关服务器下载
	public static final String SECURITY_XML = "securityview.xml";// 安防文件//可从网关服务器下载
	public static final String SENSOR_XML = "sensorview.xml";// 传感器文件//可从网关服务器下载
	public static final String APPLIANCE_XML = "applianceview.xml";// 家电文件//可从网关服务器下载
	public static final String SCENEMODE_DIRECTORY = "scenemode" + File.separator;
	public static final String ROOM_XML = "room.xml";// 房间文件

	private static FileUtil instance = null;

	public static FileUtil getInstance() {
		if (instance == null)
			instance = new FileUtil();
		return instance;
	}

	private FileUtil() {// 创建重要目录（如果不存在的话）
		File downDir = new File(DOWN_DIRECTORY);// 创建down文件夹
		if (!downDir.exists()) {
			downDir.mkdir();
		}
		File localDir = new File(LOCAL_DIRECTORY);// 创建local文件夹
		if (!localDir.exists()) {
			localDir.mkdir();
		}
	}

	/**
	 * 得到用户根目录
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
	 * 得到用户的某个文件路径
	 */
	public String getUserFile(String filename) {
		return getUserRoot() + filename;
	}

	/**
	 * 得到用户的某个文件夹，如果不存在的话就创建
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
	 * 得到状态文件，它一定是存放在down文件夹中
	 */
	public String getStateXmlFile() {
		return DOWN_DIRECTORY + STATE_XML;
	}

	/**
	 * 得到down中的version.xml
	 */
	public String getDownVersionXmlFile() {
		return DOWN_DIRECTORY + VERSION_XML;
	}

	/**
	 * 得到local中的version.xml
	 */
	public String getLocalVersionXmlFile() {
		return LOCAL_DIRECTORY + VERSION_XML;
	}

	/**
	 * 初始化配置
	 */
	public void resetConfig() {
		deleteDirectory(getUserRoot());// 删除用户的根目录下的文件
		deleteFile(LOG_XML);
		deleteFile(SYSTEM_XML);
		deleteFile(LOGIN_CONFIG);
	}

	/**
	 * 删除单个文件
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录下的文件
	 */
	public boolean deleteDirectory(String sPath) {
		boolean flag = true;
		if (!sPath.endsWith(File.separator)) {// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {// 如果dir对应的文件不存在，或者不是一个目录，则退出
			return false;
		}
		File[] files = dirFile.listFiles();// 删除文件夹下的所有文件(包括子目录)
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {// 删除子文件
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {// 删除子目录
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
