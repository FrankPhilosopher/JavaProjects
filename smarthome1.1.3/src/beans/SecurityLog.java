package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * bean：报警日志
 * 
 * @author yinger
 * 
 */
public class SecurityLog {

	private ObjectProperty<Date> date = new SimpleObjectProperty<Date>();// 时间
	private StringProperty deviceName = new SimpleStringProperty();// 设备
	private StringProperty logMessage = new SimpleStringProperty();// 报警信息
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public SecurityLog(Date date, String deviceName, String logMessage) {
		this.date.set(date);
		this.deviceName.set(deviceName);
		logMessage = "时间：" + simpleDateFormat.format(date) + "--报警设备" + deviceName + "报警了！";// 在这里构造logmessage
		this.logMessage.set(logMessage);
	}

	// 这个方法是为了写入xml文件中调用的
	public Date getRealDate() {
		return date.get();
	}

	// 这个方法是为了table的调用
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
