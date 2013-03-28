package item;

import com.yj.ipcamera.view.Camera;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tool.CameraTool;
import util.ResouceManager;
import util.UiUtil;
import beans.CameraControl;
import controller.CameraConfigController;
import controller.CameraPaneController;
import controller.DeleteConfirmController;
import controller.NameEditController;

/**
 * ����ͷ�豸item
 * 
 * @author yinger
 * 
 */
public class CameraItem {

	public static final double WIDTH = 120;
	public static final double HEIGHT = 120;

	// public static boolean isShowing = false;// ��̬�ģ��Ƿ��������ͷ�豸��Ĭ����false

	private CameraControl cameraControl;
	private UiUtil uiUtil;
	private CameraTool tool;

	private VBox vbox;
	private ImageView iv;
	private Image onImage;
	private ContextMenu menu;

	public CameraItem(CameraControl camera) {
		this.cameraControl = camera;
		initUI();
	}

	private void initUI() {
		uiUtil = UiUtil.getInstance();
		tool = CameraTool.getInstance();

		menu = new ContextMenu();
		MenuItem nameItem = new MenuItem("�޸�����");
		nameItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				uiUtil.openDialog(ResouceManager.NAME_EDIT_PANE);
				NameEditController.getInstance().setRenameObject(cameraControl);
			}
		});
		menu.getItems().add(nameItem);
		MenuItem configItem = new MenuItem("����");
		configItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				uiUtil.openDialog(ResouceManager.CAMERA_CONFIG_PANE);
				CameraConfigController.getInstance().setCamera(cameraControl);
			}
		});
		MenuItem deleteItem = new MenuItem("ɾ��");
		deleteItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				deleteCamera();
			}
		});
		menu.getItems().addAll(configItem, deleteItem);

		vbox = new VBox();
		vbox.setPrefSize(WIDTH, HEIGHT);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setPadding(new Insets(5));
		vbox.setSpacing(20);

		Text textName = new Text(cameraControl.getName());
		textName.getStyleClass().add(ResouceManager.ITEM_TEXT);

		onImage = ResouceManager.getImage(ResouceManager.CAMERA_DEFAULT);
		iv = new ImageView();
		iv.setImage(onImage);
		iv.setFitWidth(70);
		iv.setFitHeight(70);
		iv.setCursor(Cursor.HAND);
		iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {// �����龰ģʽ
				if (e.getButton() == MouseButton.PRIMARY) {
					// if (!isShowing) {// �����Ƶû������ʾ�ʹ�
					startVideo();
					// }
				} else if (e.getButton() == MouseButton.SECONDARY) {
					menu.show(iv, e.getScreenX(), e.getScreenY());
				}
			}
		});

		iv.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				uiUtil.playFadeTransition(iv, 2, 0.5f, 1.0f, 1, false);
			}
		});

		textName.textProperty().bind(cameraControl.nameProperty());
		vbox.getChildren().addAll(iv, textName);
	}

	/**
	 * ��������ͷ�ۿ���Ƶ
	 */
	private void startVideo() {
		if (cameraControl != null) {
			Camera camera = Camera.getInstance(cameraControl);
			// if(camera != null) camera.dispose();
			camera.setVisible(true);
			camera.startCapture();
			// Camera.getInstance(cameraControl).setVisible(true);
			// Camera.getInstance(cameraControl).startCapture();
		}
	}

	private void deleteCamera() {
		uiUtil.openDialog(ResouceManager.DELETE_CONFIRM);// ��ʾ�Ƿ����ɾ��
		new Thread(new Runnable() {// Ϊ�˷�ֹ�������������һ���߳������ɾ��ȷ�ϵĲ���
					public void run() {
						StringBuffer confirmResult = DeleteConfirmController.getInstance().confirmResult;
						synchronized (confirmResult) {// �ȴ��������
							while (confirmResult.toString().length() == 0)
								try {
									confirmResult.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();// TODO:��ô����
								}
						}
						if (confirmResult.toString().equalsIgnoreCase("YES")) {
							tool.deleteCamera(cameraControl);// ɾ��--�ļ�
							Platform.runLater(new Runnable() {// ȷ����������fx�߳���ִ�е�
								public void run() {
									CameraPaneController.getInstance().refreshItems();// ˢ���б�
								}
							});
						}// �������yes����ô��ɶҲ����--�߳��˳���
					}
				}).start();
	}

	/**
	 * �õ�����ڵ�
	 */
	public Node getNode() {
		return vbox;
	}

	/**
	 * �õ�camera�豸
	 */
	public CameraControl getCameraControl() {
		return cameraControl;
	}

}
