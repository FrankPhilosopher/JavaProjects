package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import tool.SystemTool;
import util.AppliactionUtil;
import util.ProtocolUtil;
import util.ResouceManager;
import util.UiUtil;
import app.SmartHome;

public class MainPageController implements Initializable {

	private static MainPageController controller;
	private UiUtil uiUtil;

	private Stage stage;
	private double initX;
	private double initY;

	private int index;// index for loop
	private int size;// size of imageviewlist

	private static final String WIN_ID = "Windows";
	private static final String HOMESITE = "http://www.iever.cn/";

	private boolean isRight = false;

	@FXML
	private Hyperlink companyUrl;

	@FXML
	private HBox hbWarning;
	@FXML
	private Label labelMessage;
	@FXML
	private AnchorPane mainPane;// ����壬�������ô����¼�
	@FXML
	private AnchorPane centerPane;// �������
	@FXML
	private Label currentUser;
	@FXML
	private ImageView lightCoverLay;
	@FXML
	private ImageView lightImageView;
	@FXML
	private ImageView doorCoverLay;
	@FXML
	private ImageView doorImageView;
	@FXML
	private ImageView securityCoverLay;
	@FXML
	private ImageView securityImageView;
	@FXML
	private ImageView cameraCoverLay;
	@FXML
	private ImageView cameraImageView;
	@FXML
	private ImageView applianceCoverLay;
	@FXML
	private ImageView applianceImageView;
	@FXML
	private ImageView sceneCoverLay;
	@FXML
	private ImageView sceneImageView;
	@FXML
	private ImageView musicImageView;
	@FXML
	private ImageView musicCoverLay;
	@FXML
	private ImageView settingCoverLay;
	@FXML
	private ImageView settingImageView;

	@FXML
	private ImageView homeImageView;

	@FXML
	private ImageView ivWinmin;
	@FXML
	private ImageView ivWinclose;

	// Ϊ�˷��㣬��imageview�ŵ�list�н��в���
	private List<ImageView> coverLayList = new ArrayList<ImageView>();
	private List<ImageView> deviceImageViewList = new ArrayList<ImageView>();

	private AnchorPane lightPane;
	private AnchorPane doorPane;
	private AnchorPane securityPane;
	private AnchorPane cameraPane;
	private AnchorPane appliancePane;
	private AnchorPane sceneModePane;
	private AnchorPane settingPane;
	private AnchorPane roomPane;
	private AnchorPane musicPane;

	/**
	 * �õ���������Ŀ�����
	 */
	public static MainPageController getInstance() {
		return controller;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initValues();
		dealWithEvents();
	}

	/**
	 * ��ʼ������
	 */
	private void initValues() {
		controller = this;
		uiUtil = UiUtil.getInstance();
		stage = SmartHome.getAppStage();
		currentUser.setText(SystemTool.CURRENT_USER.getName());
		hbWarning.setOpacity(0);// ����Ϊ͸�������ǲ���ʾ
		labelMessage.setTextFill(Color.RED);// ��ʾ��ɫΪ��ɫ��

		companyUrl.setVisible(false);

		loadPanes();

		// create list
		Collections.addAll(coverLayList, lightCoverLay, doorCoverLay, securityCoverLay, cameraCoverLay,
				applianceCoverLay, sceneCoverLay,musicCoverLay, settingCoverLay);// coverlay
		Collections.addAll(deviceImageViewList, lightImageView, doorImageView, securityImageView, cameraImageView,
				applianceImageView, sceneImageView, musicImageView,settingImageView);// imageview
		// init
		showCoverLay(-1);
		// set imageview user data
		lightImageView.setUserData(ProtocolUtil.DEVICETYPE_LIGHT);
		doorImageView.setUserData(ProtocolUtil.DEVICETYPE_DOOR);
		applianceImageView.setUserData(ProtocolUtil.DEVICETYPE_APPLIANCE);
		securityImageView.setUserData(ProtocolUtil.DEVICETYPE_SECURITY);
		cameraImageView.setUserData(ProtocolUtil.DEVICETYPE_CAMERA);
		sceneImageView.setUserData(ProtocolUtil.DEVICETYPE_SCENE);
		musicImageView.setUserData(ProtocolUtil.DEVICETYPE_MUSIC);
		settingImageView.setUserData(ProtocolUtil.DEVICETYPE_SETTING);

		// Ĭ�ϴ򿪵����
		openDefaultPane();
	}

	/**
	 * Ĭ�ϴ򿪵����
	 */
	private void openDefaultPane() {
		showCoverLay(5);
		openPane(ProtocolUtil.DEVICETYPE_SCENE);
	}

	/**
	 * ����pane
	 */
	private void loadPanes() {
		try {
			lightPane = FXMLLoader.load(SmartHome.class.getResource(ResouceManager.LIGHT_PANE),
					SmartHome.getResourceBundle());
			doorPane = FXMLLoader.load(SmartHome.class.getResource(ResouceManager.DOOR_PANE),
					SmartHome.getResourceBundle());
			securityPane = FXMLLoader.load(SmartHome.class.getResource(ResouceManager.SECURITY_PANE),
					SmartHome.getResourceBundle());
			appliancePane = FXMLLoader.load(SmartHome.class.getResource(ResouceManager.APPLIANCE_PANE),
					SmartHome.getResourceBundle());
			sceneModePane = FXMLLoader.load(SmartHome.class.getResource(ResouceManager.SCENEMODE_PANE),
					SmartHome.getResourceBundle());
			cameraPane = FXMLLoader.load(SmartHome.class.getResource(ResouceManager.CAMERA_PANE),
					SmartHome.getResourceBundle());
			settingPane = FXMLLoader.load(SmartHome.class.getResource(ResouceManager.SETTING_PANE),
					SmartHome.getResourceBundle());
			roomPane = FXMLLoader.load(SmartHome.class.getResource(ResouceManager.ROOM_PANE),
					SmartHome.getResourceBundle());
			musicPane = FXMLLoader.load(SmartHome.class.getResource(ResouceManager.MUSIC_PANE),
					SmartHome.getResourceBundle());
		} catch (IOException e) {
			uiUtil.showSystemExceptionMessage("������س����쳣��");
		}
	}

	/**
	 * �����¼�
	 */
	private void dealWithEvents() {
		// when mouse button is pressed, save the initial position of screen
		mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (me.getButton() == MouseButton.PRIMARY) {
					initX = me.getScreenX() - stage.getX();
					initY = me.getScreenY() - stage.getY();
					isRight = false;
				} else if (me.getButton() == MouseButton.SECONDARY) {
					isRight = true;
				}
			}
		});

		// when screen is dragged, translate it accordingly
		mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (isRight) {
					isRight = false;
					return;
				}
				if (me.getButton() == MouseButton.PRIMARY) {
					stage.setX(me.getScreenX() - initX);
					stage.setY(me.getScreenY() - initY);
					isRight = false;
				}
			}
		});

		companyUrl.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				String os = System.getProperty("os.name");
				if (os != null && os.startsWith(WIN_ID)) {
					String cmd = "rundll32 url.dll,FileProtocolHandler " + HOMESITE;
					try {
						Runtime.getRuntime().exec(cmd);
					} catch (IOException e) {
						uiUtil.showSystemExceptionMessage("����ַʧ�ܣ�");
					}
				}
			}
		});

		homeImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				uiUtil.openDialog(ResouceManager.ABOUT_PANE);
			}
		});

		ivWinmin.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setIconified(true);
			}
		});

		ivWinclose.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				uiUtil.openDialog(ResouceManager.EXIT_CONFIRM);// ��ʾ�Ƿ�����˳�
				new Thread(new Runnable() {// Ϊ�˷�ֹ�������������һ���߳�������˳�ȷ�ϵĲ���
							public void run() {
								StringBuffer confirmResult = ExitConfirmController.getInstance().confirmResult;
								synchronized (confirmResult) {// �ȴ��������
									while (confirmResult.toString().length() == 0)
										try {
											confirmResult.wait();
										} catch (InterruptedException e) {
											e.printStackTrace();// TODO:��ô����
										}
								}
								if (confirmResult.toString().equalsIgnoreCase("YES")) {
									System.exit(0);
								}// �������yes����ô��ɶҲ����--�߳��˳���
							}
						}).start();
				// System.exit(0);//
			}
		});

		// ����imageview����궯���¼�
		for (index = 0, size = deviceImageViewList.size(); index < size; index++) {
			mouseEventImageView(deviceImageViewList.get(index));
		}
	}

	/**
	 * ����������imageview����¼�
	 */
	private void mouseEventImageView(final ImageView imageView) {
		imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				uiUtil.playFadeTransition(imageView, 2, 0.5, 1.0f, 1, false);
			}
		});

		imageView.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				showCoverLay(deviceImageViewList.indexOf(imageView));
				openPane((int) imageView.getUserData());
			}
		});
	}

	/**
	 * ��ʾ����ı���ͼƬ
	 */
	private void showCoverLay(int index) {
		for (ImageView coverlay : coverLayList) {
			coverlay.setVisible(false);
		}
		if (index >= 0) {
			coverLayList.get(index).setVisible(true);
		}
	}

	/**
	 * ��pane
	 */
	public void openPane(int type) {
		final AnchorPane pane;
		switch (type) {
		case ProtocolUtil.DEVICETYPE_LIGHT:
			pane = lightPane;
			break;
		case ProtocolUtil.DEVICETYPE_DOOR:
			pane = doorPane;
			break;
		case ProtocolUtil.DEVICETYPE_SECURITY:
			pane = securityPane;
			break;
		case ProtocolUtil.DEVICETYPE_APPLIANCE:
			pane = appliancePane;
			break;
		case ProtocolUtil.DEVICETYPE_CAMERA:
			pane = cameraPane;
			break;
		case ProtocolUtil.DEVICETYPE_SCENE:
			if(AppliactionUtil.DEBUG) System.out.println("aa");
			pane = sceneModePane;
			ScenePaneController.getInstance().refreshItems();
			break;
		case ProtocolUtil.DEVICETYPE_SETTING:
			pane = settingPane;
			break;
		case ProtocolUtil.DEVICETYPE_ROOM:
			pane = roomPane;
			break;
		case ProtocolUtil.DEVICETYPE_MUSIC:
			pane = musicPane;
			break;
		default:
			pane = sceneModePane;
			break;
		}
		// paneΪ�ջ��߾��ǵ�ǰ��ʾ�����Ļ��ͷ���
		if (pane == null || (centerPane.getChildren().size() > 0 && centerPane.getChildren().get(0) == pane)) {
			return;
		}
		EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					centerPane.getChildren().clear();
					centerPane.getChildren().add(pane);
					// centerPane.translateXProperty().add(2000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		// if (centerPane.getChildren().size() > 0) {// ԭ����panel
		// new Timeline(new KeyFrame(Duration.seconds(0.1), new KeyValue(centerPane.translateXProperty(), -1000),
		// new KeyValue(centerPane.opacityProperty(), 0)), new KeyFrame(Duration.seconds(0.15), eh,
		// new KeyValue(centerPane.translateXProperty(), 2000)), new KeyFrame(Duration.seconds(0.2),
		// new KeyValue(centerPane.translateXProperty(), 0), new KeyValue(centerPane.opacityProperty(), 1)))
		// .play();
		// } else {// ��û�м��ع�panel// �����Ч��
		// new Timeline(new KeyFrame(Duration.seconds(0.1), new KeyValue(centerPane.opacityProperty(), 0)),
		// new KeyFrame(Duration.seconds(0.15), eh), new KeyFrame(Duration.seconds(0.25), new KeyValue(
		// centerPane.opacityProperty(), 1))).play();
		// }
		new Timeline(new KeyFrame(Duration.seconds(0.15), new KeyValue(centerPane.opacityProperty(), 0)), new KeyFrame(
				Duration.seconds(0.16), eh), new KeyFrame(Duration.seconds(0.3), new KeyValue(
				centerPane.opacityProperty(), 1))).play();
	}

	/**
	 * �õ���ʾϵͳ��Ϣ��label
	 */
	public Label getLabelMessage() {
		return labelMessage;
	}

	/**
	 * �õ���ʾ�쳣��Ϣ��hbox
	 */
	public HBox getWaringBox() {
		return hbWarning;
	}

}
