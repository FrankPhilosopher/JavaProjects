package beans;

import communication.CommunicationUtil;

import interfaces.IRenameable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tool.CameraTool;

/**
 * ����ͷ�豸��
 * 
 * @author yinger
 * 
 */
public class CameraControl implements IRenameable {

	public static String SMALL_RESOLUTION = "4";
	public static String MIDDLE_RESOLUTION = "8";
	public static String BIG_RESOLUTION = "32";

	public static int JPEG = 0;
	public static int H264 = 1;

	public static int NOFLIP = 0;
	public static int FLIP = 1;

	private StringProperty name = new SimpleStringProperty();// name
	private String username = "admin";// user name
	private String password = "123456";// password
	private String ipAddress = "192.168.1.181";// ip address
	private String port = "81";
	private String resolution = MIDDLE_RESOLUTION;// default value
	private int videoFlip = NOFLIP; // 0Ϊ��ת��1Ϊ��ת
	private int type = JPEG;// Ĭ����0---JPEG��Ƶ

	private String saveipAddress = ipAddress;  //AB�������趨һ��saveipaddress����Ϊ��Զ�̵�¼ʱ���ڴ����ipaddress
										//�����Զ��cacheip,��������ʱ�����ڶԻ�����ľ���Զ��IP����ʵ����Ҫ�����������Ӧ���Ǿ�����ip.
	
	public int getVideoFlip() {
		return videoFlip;
	}

	public void setVideoFlip(int videoFlip) {
		this.videoFlip = videoFlip;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		
	}
	
	public String getSaveIpAddress(){
		return saveipAddress;
	}
	//ȡ���
	public void setSaveEqualIpAddress(){
		this.saveipAddress = this.ipAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public int getId() {
		return 0;
	}

	public void setName(String value) {
		name.set(value);
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	@Override
	public void rename(String name) throws Exception {
		CameraTool.getInstance().rename(this, name);
	}

	@Override
	public String getOldName() {
		return name.get();
	}

}
