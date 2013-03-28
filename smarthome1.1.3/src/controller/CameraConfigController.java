package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tool.CameraTool;
import util.UiUtil;
import app.SmartHome;
import beans.CameraControl;

public class CameraConfigController implements Initializable {

	private static CameraConfigController controller;
	private CameraControl cameraControl;
	private CameraTool tool;
	private UiUtil uiUtil;
	private int type = 0;// 0��ʾ���camera��1��ʾ�޸�camera

	private double initX;
	private double initY;
	private Stage stage;

	@FXML
	private Label lblMessage;
	@FXML
	private AnchorPane cameraPane;
	@FXML
	private TextField tfName;
	@FXML
	private TextField tfUserName;
	@FXML
	private PasswordField pfPassword;
	@FXML
	private TextField tfIP;
	@FXML
	private TextField tfPort;

	@FXML
	private Label lblName;
	
	@FXML
	private CheckBox cbFlip;

	@FXML
	private RadioButton rbA;
	@FXML
	private RadioButton rbB;

	@FXML
	private RadioButton rbSmall;
	@FXML
	private RadioButton rbMiddle;
	@FXML
	private RadioButton rbBig;

	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancle;

	@FXML
	private ImageView ivWinmin;
	@FXML
	private ImageView ivWinclose;

	public static CameraConfigController getInstance() {
		return controller;
	}

	public void initialize(URL location, ResourceBundle resources) {
		initValues();
		initEvents();
	}

	/**
	 * ��ʼ������
	 */
	private void initValues() {
		controller = this;
		stage = SmartHome.getModelStage();
		tool = CameraTool.getInstance();
		uiUtil = UiUtil.getInstance();
		lblMessage.setText("");
		lblName.setText("");
		tfName.setText("");
		lblName.setVisible(false);
		tfName.setVisible(false);
		rbA.setSelected(true);
		rbB.setSelected(false);

		rbA.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				rbSmall.setDisable(false);
			}
		});

		rbB.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				rbSmall.setDisable(true);
			}
		});
	}

	// add event handlers
	private void initEvents() {
		// when mouse button is pressed, save the initial position of screen
		cameraPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = me.getScreenX() - stage.getX();
				initY = me.getScreenY() - stage.getY();
				stage.toFront();
			}
		});

		// when screen is dragged, translate it accordingly
		cameraPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setX(me.getScreenX() - initX);
				stage.setY(me.getScreenY() - initY);
			}
		});

		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				handleConfigOk();
			}
		});

		btnCancle.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				stage.close();
			}
		});

		ivWinmin.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setIconified(true);
			}
		});

		ivWinclose.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.close();
			}
		});
	}

	/**
	 * ����camera�������������½������޸�camera
	 */
	public void setCamera(CameraControl cameraControl) {
		if (cameraControl == null) {// new
			type = 0;
			this.cameraControl = new CameraControl();
			this.cameraControl.setName("");
			tfName.setVisible(true);
			lblName.setVisible(false);
		} else {// edit
			type = 1;
			this.cameraControl = cameraControl;
			lblName.setText(cameraControl.getName());
			tfName.setVisible(false);
			lblName.setVisible(true);
		}
		resetCameraControl();// ���cameracontrol��new�ģ�����Ϊ�յĻ�����ֿ�ָ���쳣
	}

	/**
	 * ��������ֵ
	 */
	private void resetCameraControl() {
		lblMessage.setText("");
		tfName.setText(cameraControl.getName());// ��һ�����֣�����Ƿ�������Ļ�����ô�ı����ܱ��༭��Ҳ������Ϊ����Ϊnull��Ե��
		tfUserName.setText(cameraControl.getUsername());
		pfPassword.setText(cameraControl.getPassword());
		tfIP.setText(cameraControl.getSaveIpAddress());
		tfPort.setText(cameraControl.getPort());

		if (cameraControl.getType() == CameraControl.JPEG) {
			rbA.setSelected(true);
		} else if (cameraControl.getType() == CameraControl.H264) {
			rbB.setSelected(true);
		}
		
		if (cameraControl.getVideoFlip() == CameraControl.NOFLIP) {//set flip
			cbFlip.setSelected(false);
		} else {
			cbFlip.setSelected(true);
		}

		if (cameraControl.getResolution().equals(CameraControl.SMALL_RESOLUTION)) {
			rbSmall.setSelected(true);
		} else if (cameraControl.getResolution().equals(CameraControl.MIDDLE_RESOLUTION)) {
			rbMiddle.setSelected(true);
		} else {
			rbBig.setSelected(true);
		}
	}

	/**
	 * ���ȷ��
	 */
	private void handleConfigOk() {
		String name = tfName.getText().trim();
		if (name == null || name.equalsIgnoreCase("")) {
			lblMessage.setText("����������ͷ����");
			return;
		}
		cameraControl.setIpAddress(tfIP.getText().trim());
		cameraControl.setSaveEqualIpAddress();  //����ȡ���
		cameraControl.setPort(tfPort.getText().trim());
		cameraControl.setUsername(tfUserName.getText().trim());
		cameraControl.setPassword(pfPassword.getText().trim());

		if (rbA.isSelected()) {
			cameraControl.setType(CameraControl.JPEG);
		} else if (rbB.isSelected()) {
			cameraControl.setType(CameraControl.H264);
		}
		
		if (cbFlip.isSelected()) {
			cameraControl.setVideoFlip(CameraControl.FLIP);
		}else {
			cameraControl.setVideoFlip(CameraControl.NOFLIP);
		}

		if (rbMiddle.isSelected()) {
			cameraControl.setResolution(CameraControl.MIDDLE_RESOLUTION);
		} else if (rbSmall.isSelected()) {
			cameraControl.setResolution(CameraControl.SMALL_RESOLUTION);
		} else {
			cameraControl.setResolution(CameraControl.BIG_RESOLUTION);
		}
		if (type == 0) {// new
			cameraControl.setName(name);
			if (!CameraPaneController.getInstance().validate(name)) {
				uiUtil.showErrorMessage(lblMessage, "������ͷ�豸�Ѿ�����!");
				return;
			}
			try {
				tool.newCamera(cameraControl);
				CameraPaneController.getInstance().refreshItems();
			} catch (Exception e) {
				uiUtil.showErrorMessage(lblMessage, "�½�����ͷʧ�ܣ�");
				return;
			}
		} else {// save
			try {
				tool.save(cameraControl);
			} catch (Exception e) {
				uiUtil.showErrorMessage(lblMessage, "��������ͷʧ�ܣ�");
				return;
			}
		}
		stage.close();
	}

}
