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
 * 摄像头设备的xml处理类
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
	 * 创建新的camera的xml文件
	 */
	public void newCamera(CameraControl cameraControl) throws Exception {
		File file = new File(fileUtil.getUserDirectory(FileUtil.CAMERA_DIRECTORY) + cameraControl.getName() + ".xml");
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			file.createNewFile();
		}
		Document document = DocumentHelper.createDocument();
		writeCamera(document, cameraControl);// 添加元素
		try {
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 注意一定要是utf-8格式
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			throw new Exception("创建文件" + file.getName() + "失败！");
		}
	}

	/**
	 * 写入一个camera到文件中
	 */
	private void writeCamera(Document document, CameraControl cameraControl) {
		Element root = document.addElement(CAMERA);
		Element name = root.addElement(NAME);
		name.setText(cameraControl.getName());
		Element username = root.addElement(USERNAME);
		username.setText(cameraControl.getUsername());
		Element password = root.addElement(PASSWORD);
		try {
			password.setText(BASE64Util.encryptBASE64(cameraControl.getPassword().getBytes()));// 给密码加密
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
	 * 加载所有的camera
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
	 * 加载指定的camera文件
	 */
	private CameraControl loadOneCamera(File file) throws Exception {
		if (!file.exists() || file.length() <= 0) {// file.length = 0表示文件不存在
			throw new Exception("文件" + file.getName() + "不存在！");
		}
		try {
			InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			SAXReader reader = new SAXReader();
			Document document = reader.read(isReader);
			CameraControl cameraControl = new CameraControl();
			Element root = document.getRootElement();
			cameraControl.setName(root.element(NAME).getTextTrim());
			cameraControl.setUsername(root.element(USERNAME).getTextTrim());
			// cameraControl.setPassword(root.element(PASSWORD).getTextTrim());//解密
			cameraControl.setPassword(new String(BASE64Util.decryptBASE64(root.element(PASSWORD).getTextTrim())));
			cameraControl.setIpAddress(root.element(IPADDR).getTextTrim());
			cameraControl.setSaveEqualIpAddress();  //调用取相等
			
			cameraControl.setPort(root.element(PORT).getTextTrim());
			cameraControl.setType(Integer.parseInt(root.element(TYPE).getTextTrim()));
			cameraControl.setResolution(root.element(RESOLUTION).getTextTrim());
			cameraControl.setVideoFlip(Integer.parseInt(root.element(FLIP).getTextTrim()));
			return cameraControl;
		} catch (Exception e) {
			throw new Exception("读取文件" + file.getName() + "失败！");
		}
	}

	/**
	 * 删除一个camera
	 */
	public void deleteCamera(CameraControl cameraControl) {
		File file = new File(fileUtil.getUserDirectory(FileUtil.CAMERA_DIRECTORY) + cameraControl.getName() + ".xml");
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 重命名camera---直接先删除原来的文件，然后重新创建一个文件，name是新名称
	 */
	public void rename(CameraControl cameraControl, String name) throws Exception {
		deleteCamera(cameraControl);
		cameraControl.setName(name);
		newCamera(cameraControl);
	}

	/**
	 * 保存camera
	 */
	public void save(CameraControl cameraControl) throws Exception {
		deleteCamera(cameraControl);
		newCamera(cameraControl);
	}

}
