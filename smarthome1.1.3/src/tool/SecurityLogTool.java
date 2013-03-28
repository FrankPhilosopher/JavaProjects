package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import util.FileUtil;
import beans.SecurityLog;

/**
 * ������־��XML�ļ������� ����ģʽ
 * 
 * @author yinger
 * 
 */
public class SecurityLogTool {

	private static SecurityLogTool securityLogTool;
	private FileUtil fileUtil;

	private static final String DATE = "date";
	private static final String DEVICE = "device";
	private static final String MESSAGE = "message";
	private static final String NODES = "logs";
	private static final String NODE = "log";

	private int MAXSIZE = 15;// ��ౣ������־��Ŀ
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private SecurityLogTool() {
		fileUtil = FileUtil.getInstance();
	}

	public static SecurityLogTool getInstance() {
		if (securityLogTool == null) {
			securityLogTool = new SecurityLogTool();
		}
		return securityLogTool;
	}

	/**
	 * �õ����е���־
	 */
	public LinkedList<SecurityLog> getAllLogs() throws Exception {
		File file = new File(fileUtil.getUserFile(FileUtil.LOG_XML));
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������// ����ļ������ڵĻ������½�һ���ļ�
			try {
				file.createNewFile();
				return new LinkedList<SecurityLog>();// ������null
			} catch (IOException e) {
				throw new Exception("������־�ļ�ʧ�ܣ�");
			}
		}
		return readLogFile(file);
	}

	/**
	 * ��ȡ��־�ļ��е���־
	 */
	private LinkedList<SecurityLog> readLogFile(File file) throws Exception {
		LinkedList<SecurityLog> logList = new LinkedList<>();
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			Element root = document.getRootElement();
			List list = root.elements();
			Element subElement;
			SecurityLog log;
			for (int i = 0; i < list.size(); i++) {
				subElement = (Element) list.get(i);
				Date date = simpleDateFormat.parse(subElement.elementTextTrim(DATE));
				String device = subElement.elementTextTrim(DEVICE);
				String message = subElement.elementTextTrim(MESSAGE);
				log = new SecurityLog(date, device, message);
				logList.addLast(log);
			}
			isReader.close();
		} catch (Exception e) {
			throw new Exception("��ȡ��־�ļ�ʧ�ܣ�");
		}
		return logList;
	}

	/**
	 * ����һ��������־��Ϣ
	 */
	public void saveLog(SecurityLog log) throws Exception {
		LinkedList<SecurityLog> logList = getAllLogs();
		if (logList.size() == MAXSIZE) {// �ﵽ������־��Ŀ��
			logList.removeLast();//�Ƴ����һ����־
		}
		logList.addFirst(log);
		writeLogFile(logList);
	}

	/**
	 * ����־�б�д�뵽�ļ��С�Ϊ�˷��㣬��ֻ����д�ļ�����
	 */
	private void writeLogFile(LinkedList<SecurityLog> logList) throws Exception {
		File file = new File(fileUtil.getUserFile(FileUtil.LOG_XML));
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(NODES);
		SecurityLog securityLog;
		Element element;
		for (int i = 0; i < logList.size(); i++) {
			securityLog = (SecurityLog) logList.get(i);
			element = root.addElement(NODE);
			Element date = element.addElement(DATE);
			date.setText(simpleDateFormat.format(securityLog.getRealDate()));
			Element device = element.addElement(DEVICE);
			device.setText(securityLog.getDeviceName());
			Element message = element.addElement(MESSAGE);
			message.setText(securityLog.getLogMessage());
		}
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// ע��һ��Ҫ��utf-8��ʽ
			writer.write(document);
		} catch (Exception ex) {
			throw new Exception("������־ʧ�ܣ�");
		}finally{
			if(writer!=null){
				writer.close();
			}
		}
	}

}
