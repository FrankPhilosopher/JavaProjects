package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tool.RoomTool;
import util.UiUtil;
import app.SmartHome;
import beans.Room;

public class RoomAddController implements Initializable {

	private static RoomAddController instance;

	private RoomTool tool;

	private UiUtil uiUtil;
	private double initX;
	private double initY;
	private Stage stage;// login stage

	@FXML
	private AnchorPane namePane;
	@FXML
	private Label lblMessage;

	@FXML
	private TextField tfName;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancle;
	@FXML
	private ImageView ivWinmin;
	@FXML
	private ImageView ivWinclose;

	public static RoomAddController getInstance() {
		return instance;
	}

	public void initialize(URL location, ResourceBundle resources) {
		initValues();
		initEvents();
	}

	private void initEvents() {
		// when mouse button is pressed, save the initial position of screen
		namePane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = me.getScreenX() - stage.getX();
				initY = me.getScreenY() - stage.getY();
				stage.toFront();
			}
		});

		// when screen is dragged, translate it accordingly
		namePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setX(me.getScreenX() - initX);
				stage.setY(me.getScreenY() - initY);
			}
		});
		
		namePane.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
				handleOk();
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

		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				handleOk();
			}
		});
	}

	private void initValues() {
		instance = this;
		uiUtil = UiUtil.getInstance();
		stage = SmartHome.getModelStage();
		tool = RoomTool.getInstance();
	}
	public void handleOk(){
		if (tfName.getText() == null || "".equalsIgnoreCase(tfName.getText().trim())) {
			uiUtil.showErrorMessage(lblMessage, "请输入名称！");
			return;
		}
		// 新建房间
		Room room = new Room();
		room.setId(UUID.randomUUID().toString());
		room.setName(tfName.getText().trim());
		try {
			tool.addRoom(room);
		} catch (Exception e) {
			uiUtil.showErrorMessage(lblMessage, "添加房间失败！");
		}
		// 刷新界面
		LightPaneController.getInstance().refreshItems();
		stage.close();
	}
}
