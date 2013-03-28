package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import app.SmartHome;

/**
 * ��Դ������
 * 
 * @author yinger
 * 
 */
public class ResouceManager {

	// fxml page
	public static final String NAME_EDIT_PANE = "NameEdit.fxml";// �޸�����
	public static final String PIC_EDIT_PANE = "PicEdit.fxml";// �޸�ͼƬ
	public static final String DELETE_CONFIRM = "DeleteConfirm.fxml";// ȷ��ɾ��
	public static final String RESET_CONFIRM = "ResetConfirm.fxml";// ȷ�ϻ�ԭ
	public static final String EXIT_CONFIRM = "ExitConfirm.fxml";// ȷ��ɾ��
	public static final String ROOM_SELECT_PANE = "RoomSelect.fxml";// �޸�����

	public static final String LIGHT_PANE = "LightPane.fxml";
	public static final String DOOR_PANE = "DoorPane.fxml";
	public static final String SECURITY_PANE = "SecurityPane.fxml";
	public static final String SECURITY_LOG_PANE = "SecurityLogPane.fxml";
	public static final String APPLIANCE_PANE = "AppliancePane.fxml";
	public static final String APPLIANCE_CONTROL_PANE = "ApplianceControlPane.fxml";// �ҵ���ư�ť���
	public static final String SCENEMODE_PANE = "ScenePane.fxml";
	public static final String SECENEMODE_CONFIG_PANE = "SceneConfig.fxml";
	public static final String CAMERA_PANE = "CameraPane.fxml";
	public static final String CAMERA_CONFIG_PANE = "CameraConfig.fxml";
	public static final String SETTING_PANE = "SettingPane.fxml";
	public static final String ABOUT_PANE = "About.fxml";
	public static final String UPDATE_PANE = "PcUpdate.fxml";
	public static final String ROOM_ADD_PANE = "RoomAdd.fxml";
	public static final String ROOM_PANE = "RoomPane.fxml";
	public static final String MUSIC_PANE = "MusicPane.fxml";

	// style class
	public static final String ITEM_TEXT = "itemtext";
	public static final String TEXT_BTN = "textbtn";
	public static final String ITEM_BTN = "itembtn";

	// image cache
	private static Map<String, Image> imageCacheMap = new HashMap<String, Image>();

	// light
	public static final String ROOM = "room.png";
	public static final String LIGHTON = "light_on.png";
	public static final String LIGHTOFF = "light_off.png";

	// outlet
	public static final String OUTLETON = "outlet_on.png";
	public static final String OUTLETOFF = "outlet_off.png";

	// window
	public static final String WINDOWOPEN = "window_on.png";
	public static final String WINDOWCLOSE = "window_off.png";

	// door
	public static final String DOOROPEN = "door_on.png";
	public static final String DOORCLOSE = "door_off.png";

	// security
	public static final String SECURITYON = "security_on.png";
	public static final String SECURITYOFF = "security_off.png";

	// appliance
	public static final String APPLIANCE_DEFAULT = "default_appliance.png";
	public static final String APPLIANCE_AIRCONDITION = "appliance_aircondition.png";
	public static final String APPLIANCE_FRIDGE = "appliance_fridge.png";
	public static final String APPLIANCE_TV = "appliance_tv.png";

	// scene
	public static final String SCENE_DEFAULT = "scene_default.png";
	public static final String SCENE_OUT = "scene_out.png";
	public static final String SCENE_LEAVE = "scene_leave.png";
	public static final String SCENE_GUEST = "scene_guest.png";

	// camera
	public static final String CAMERA_DEFAULT = "ipcamera.png";

	// logo
	public static final String LOGO = "home128.png";

	// get image from the specified class
	@SuppressWarnings("rawtypes")
	public static Image getImage(Class clazz, String imageFile) {
		if (imageCacheMap.get(imageFile) == null) {
			//����������if�жϣ���Ҫ��Ϊ������app������scenemode��ͼƬ�ظ�������
			if(imageFile.startsWith("scene_")){
				try {
					imageCacheMap.put(imageFile, new Image(new FileInputStream(new File(FileUtil.SCENEMODE_DIRECTORY
							+ imageFile))));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else
			imageCacheMap.put(imageFile, new Image(clazz.getResourceAsStream(imageFile)));
		}
		return imageCacheMap.get(imageFile);
	}

	public static Image getImage(String imageFile) {
		return getImage(SmartHome.class, imageFile);
	}

	/**
	 * ���ڼҵ��ͼƬ��Ҫ���������������Ϊ���Ƚ����⣬�ļ����Ƿ����� appliance �ļ�������
	 */
	public static Image getApplianceImage(String imageFile) {
		if (imageCacheMap.get(imageFile) == null) {
			// InputStream is = SmartHome.class.getResourceAsStream("appliance/" + imageFile);
			// if (is == null) {// �ļ������ھͷ���null
			// return null;
			// }
			// imageCacheMap.put(imageFile, new Image(is));// ����õ�image
			// imageCacheMap.put(imageFile, new Image(ResouceManager.class.getResource("appliance/" + imageFile)
			// .toString()));// ����õ�image
			// ����ʹ��FileUtil.APPLIANCE_DIRECTORY������
			// new File(FileUtil.APPLIANCE_DIRECTORY+imageFile).toURI()
			// System.out.println(new File(FileUtil.APPLIANCE_DIRECTORY + imageFile).getAbsolutePath());
			try {
				imageCacheMap.put(imageFile, new Image(new FileInputStream(new File(FileUtil.APPLIANCE_DIRECTORY
						+ imageFile))));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return imageCacheMap.get(imageFile);
	}

	/**
	 * ���ؼҵ�ͼƬ
	 */
	public static Map<String, Image> loadAppImages() {
		Map<String, Image> imageMap = new HashMap<String, Image>();
		// File appDir = new File(SmartHome.class.getResource("appliance").getFile());// ���������ַ����õ����ǵ�ǰ��Ŀ¼
		// File appDir = new File(ResouceManager.class.getResource("appliance/").getFile());//
		// ע�⣬��Ҫappliance���治Ҫ����б�ܣ������Ҳ����ļ�
		// ͨ��SmartHome�õ�appliance�ļ���--SmartHome.class.getResource("appliance").getFile()
		File appDir = new File(FileUtil.APPLIANCE_DIRECTORY);
		if (!appDir.exists()) {
			return imageMap;
		}
		// System.out.println(appDir.getAbsolutePath());// D:\Work\JavaWorkspace\smarthome0.0.9\bin\app\appliance
		File[] imageFiles = appDir.listFiles(new FilenameFilter() {
			public boolean accept(File file, String filename) {
				if (filename.startsWith("appliance")) {// ��appliance��ͷ��ͼƬ
					return true;
				}
				return false;
			}
		});
		for (int i = 0; i < imageFiles.length; i++) {
			imageMap.put(imageFiles[i].getName(), ResouceManager.getApplianceImage(imageFiles[i].getName()));
		}
		return imageMap;
	}
	
	public static Image getSceneImage(String imageFile) {
		if (imageCacheMap.get(imageFile) == null) {
			// InputStream is = SmartHome.class.getResourceAsStream("appliance/" + imageFile);
			// if (is == null) {// �ļ������ھͷ���null
			// return null;
			// }
			// imageCacheMap.put(imageFile, new Image(is));// ����õ�image
			// imageCacheMap.put(imageFile, new Image(ResouceManager.class.getResource("appliance/" + imageFile)
			// .toString()));// ����õ�image
			// ����ʹ��FileUtil.APPLIANCE_DIRECTORY������
			// new File(FileUtil.APPLIANCE_DIRECTORY+imageFile).toURI()
			// System.out.println(new File(FileUtil.APPLIANCE_DIRECTORY + imageFile).getAbsolutePath());
			try {
				imageCacheMap.put(imageFile, new Image(new FileInputStream(new File(FileUtil.SCENEMODE_DIRECTORY
						+ imageFile))));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return imageCacheMap.get(imageFile);
	}
	
	public static Map<String ,Image> loadSceneImage(){
		Map<String, Image> imageMap = new HashMap<String, Image>();
		// File appDir = new File(SmartHome.class.getResource("appliance").getFile());// ���������ַ����õ����ǵ�ǰ��Ŀ¼
		// File appDir = new File(ResouceManager.class.getResource("appliance/").getFile());//
		// ע�⣬��Ҫappliance���治Ҫ����б�ܣ������Ҳ����ļ�
		// ͨ��SmartHome�õ�appliance�ļ���--SmartHome.class.getResource("appliance").getFile()
		File appDir = new File(FileUtil.SCENEMODE_DIRECTORY);
		if (!appDir.exists()) {
			return imageMap;
		}
		// System.out.println(appDir.getAbsolutePath());// D:\Work\JavaWorkspace\smarthome0.0.9\bin\app\appliance
		File[] imageFiles = appDir.listFiles(new FilenameFilter() {
			public boolean accept(File file, String filename) {
				if (filename.startsWith("scene_")) {// ��appliance��ͷ��ͼƬ
					return true;
				}
				return false;
			}
		});
		for (int i = 0; i < imageFiles.length; i++) {
			imageMap.put(imageFiles[i].getName(), ResouceManager.getSceneImage(imageFiles[i].getName()));
		}
		return imageMap;
	}

	public static void main(String[] args) {
		Image image = ResouceManager.getApplianceImage(ResouceManager.APPLIANCE_FRIDGE);
		if(AppliactionUtil.DEBUG) System.out.println(image);
	}

}
