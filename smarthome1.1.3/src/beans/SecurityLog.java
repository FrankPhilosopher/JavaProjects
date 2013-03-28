package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * bean��������־
 * 
 * @author yinger
 * 
 */
public class SecurityLog {

	private ObjectProperty<Date> date = new SimpleObjectProperty<Date>();// ʱ��
	private StringProperty deviceName = new SimpleStringProperty();// �豸
	private StringProperty logMessage = new SimpleStringProperty();// ������Ϣ
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public SecurityLog(Date date, String deviceName, String logMessage) {
		this.date.set(date);
		this.deviceName.set(deviceName);
		logMessage = "ʱ�䣺" + simpleDateFormat.format(date) + "--�����豸" + deviceName + "�����ˣ�";// �����ﹹ��logmessage
		this.logMessage.set(logMessage);
	}

	// ���������Ϊ��д��xml�ļ��е��õ�
	public Date getRealDate() {
		return date.get();
	}

	// ���������Ϊ��table�ĵ���
	public String getDate() {
		return simpleDateFormat.format(date.get());
	}

	public void setDate(Date date) {
		this.date.set(date);
	}

	public String getDeviceName() {
		return deviceName.get();
	}

	public void setDeviceName(String deviceName) {
		this.deviceName.set(deviceName);
	}

	public String getLogMessage() {
		return logMessage.get();
	}

	public void setLogMessage(String logMessage) {
		this.logMessage.set(logMessage);
	}

}
