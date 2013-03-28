package controller;

import interfaces.IRenameable;

import java.net.URL;
import java.util.ResourceBundle;

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
import util.UiUtil;
import app.SmartHome;

public class NameEditController implements Initializable {

	private static NameEditController instance;
	private IRenameable renameObject;

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

	public static NameEditController getInstance() {
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
				if(e.getCode() == KeyCode.ENTER){
					handleOk();
				}
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
	}

	public IRenameable getRenameObject() {
		return renameObject;
	}

	public void setRenameObject(IRenameable renameObject) {
		this.renameObject = renameObject;
		if (renameObject != null) {
			tfName.setText(renameObject.getOldName());
		}
	}
	
	public void handleOk(){
		if (tfName.getText() == null || "".equalsIgnoreCase(tfName.getText().trim())) {
			uiUtil.showErrorMessage(lblMessage, "请输入名称！");
			return;
		}
		try {
			renameObject.rename(tfName.getText().trim());
		} catch (Exception e) {
			uiUtil.showErrorMessage(lblMessage, "重命名失败！");
		}
		stage.close();
	}
}
