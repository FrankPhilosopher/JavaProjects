package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import util.BASE64Util;
import util.FileUtil;
import beans.CameraControl;

/**
 * ����ͷ�豸��xml������
 * 
 * @author yinger
 * 
 */
public class CameraTool {

	private static CameraTool cameraTool;
	private FileUtil fileUtil;

	private final String CAMERA = "camera";
	private final String NAME = "name";
	private final String USERNAME = "username";
	private final String PASSWORD = "password";
	private final String IPADDR = "ip";
	private final String PORT = "port";
	private final String TYPE = "type";
	private final String RESOLUTION = "resolution";
	private final String FLIP = "flip";

	private CameraTool() {
		fileUtil = FileUtil.getInstance();
	}

	public static CameraTool getInstance() {
		if (cameraTool == null) {
			cameraTool = new CameraTool();
		}
		return cameraTool;
	}

	/**
	 * �����µ�camera��xml�ļ�
	 */
	public void newCamera(CameraControl cameraControl) throws Exception {
		File file = new File(fileUtil.getUserDirectory(FileUtil.CAMERA_DIRECTORY) + cameraControl.getName() + ".xml");
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			file.createNewFile();
		}
		Document document = DocumentHelper.createDocument();
		writeCamera(document, cameraControl);// ���Ԫ��
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// ע��һ��Ҫ��utf-8��ʽ
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			throw new Exception("�����ļ�" + file.getName() + "ʧ�ܣ�");
		}
	}

	/**
	 * д��һ��camera���ļ���
	 */
	private void writeCamera(Document document, CameraControl cameraControl) {
		Element root = document.addElement(CAMERA);
		Element name = root.addElement(NAME);
		name.setText(cameraControl.getName());
		Element username = root.addElement(USERNAME);
		username.setText(cameraControl.getUsername());
		Element password = root.addElement(PASSWORD);
		try {
			password.setText(BASE64Util.encryptBASE64(cameraControl.getPassword().getBytes()));// ���������
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element ip = root.addElement(IPADDR);
		ip.setText(cameraControl.getIpAddress());
		Element port = root.addElement(PORT);
		port.setText(cameraControl.getPort());
		Element type = root.addElement(TYPE);
		type.setText(cameraControl.getType() + "");
		Element resolution = root.addElement(RESOLUTION);
		resolution.setText(cameraControl.getResolution());
		
		Element flip = root.addElement(FLIP);
		flip.setText(String.valueOf(cameraControl.getVideoFlip()));
	}

	/**
	 * �������е�camera
	 */
	public List<CameraControl> loadAllCameras() throws Exception {
		List<CameraControl> cameras = new ArrayList<CameraControl>();
		File dir = new File(fileUtil.getUserDirectory(FileUtil.CAMERA_DIRECTORY));
		File[] files = dir.listFiles();
		for (int i = 0, length = files.length; i < length; i++) {
			cameras.add(loadOneCamera(files[i]));
		}
		return cameras;
	}

	/**
	 * ����ָ����camera�ļ�
	 */
	private CameraControl loadOneCamera(File file) throws Exception {
		if (!file.exists() || file.length() <= 0) {// file.length = 0��ʾ�ļ�������
			throw new Exception("�ļ�" + file.getName() + "�����ڣ�");
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			CameraControl cameraControl = new CameraControl();
			Element root = document.getRootElement();
			cameraControl.setName(root.element(NAME).getTextTrim());
			cameraControl.setUsername(root.element(USERNAME).getTextTrim());
			// cameraControl.setPassword(root.element(PASSWORD).getTextTrim());//����
			cameraControl.setPassword(new String(BASE64Util.decryptBASE64(root.element(PASSWORD).getTextTrim())));
			cameraControl.setIpAddress(root.element(IPADDR).getTextTrim());
			cameraControl.setSaveEqualIpAddress();  //����ȡ���
			
			cameraControl.setPort(root.element(PORT).getTextTrim());
			cameraControl.setType(Integer.parseInt(root.element(TYPE).getTextTrim()));
			cameraControl.setResolution(root.element(RESOLUTION).getTextTrim());
			cameraControl.setVideoFlip(Integer.parseInt(root.element(FLIP).getTextTrim()));
			return cameraControl;
		} catch (Exception e) {
			throw new Exception("��ȡ�ļ�" + file.getName() + "ʧ�ܣ�");
		}
	}

	/**
	 * ɾ��һ��camera
	 */
	public void deleteCamera(CameraControl cameraControl) {
		File file = new File(fileUtil.getUserDirectory(FileUtil.CAMERA_DIRECTORY) + cameraControl.getName() + ".xml");
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * ������camera---ֱ����ɾ��ԭ�����ļ���Ȼ�����´���һ���ļ���name��������
	 */
	public void rename(CameraControl cameraControl, String name) throws Exception {
		deleteCamera(cameraControl);
		cameraControl.setName(name);
		newCamera(cameraControl);
	}

	/**
	 * ����camera
	 */
	public void save(CameraControl cameraControl) throws Exception {
		deleteCamera(cameraControl);
		newCamera(cameraControl);
	}

}
