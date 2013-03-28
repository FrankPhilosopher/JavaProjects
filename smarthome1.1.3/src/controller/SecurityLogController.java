package controller;

import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tool.SecurityLogTool;
import app.SmartHome;
import beans.SecurityLog;

public class SecurityLogController implements Initializable {

	private double initX;
	private double initY;
	private Stage stage;// log view stage

	private SecurityLogTool tool;

	@FXML
	private AnchorPane configPane;
	@FXML
	private TableView<SecurityLog> logTable;// 界面中的table
	@FXML
	private TableColumn<SecurityLog, Date> colLogDate;
	@FXML
	private TableColumn<SecurityLog, String> colLogDevice;
	@FXML
	private TableColumn<SecurityLog, String> colLogMessage;

	@FXML
	private ImageView ivWinmin;
	@FXML
	private ImageView ivWinclose;

	public void initialize(URL location, ResourceBundle resources) {
		initValues();
		initEvents();
	}

	private void initValues() {
		stage = SmartHome.getModelStage();
		tool = SecurityLogTool.getInstance();
		LinkedList<SecurityLog> logList = null;
		try {
			logList = tool.getAllLogs();
		} catch (Exception e) {
			e.printStackTrace();// TOOD
			return;
		}
		if (logList == null || logList.size() == 0) {
			return;
		}
		// 设置table中显示的内容
		ObservableList<SecurityLog> logs = FXCollections.observableList(logList);
		logTable.setItems(logs);
		// 设置列中显示的对应bean的内容
		colLogDate.setCellValueFactory(new PropertyValueFactory<SecurityLog, Date>("date"));
		colLogDevice.setCellValueFactory(new PropertyValueFactory<SecurityLog, String>("deviceName"));
		colLogMessage.setCellValueFactory(new PropertyValueFactory<SecurityLog, String>("logMessage"));
	}

	// add event handlers
	private void initEvents() {
		// when mouse button is pressed, save the initial position of screen
		configPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = me.getScreenX() - stage.getX();
				initY = me.getScreenY() - stage.getY();
				stage.toFront();
			}
		});

		// when screen is dragged, translate it accordingly
		configPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setX(me.getScreenX() - initX);
				stage.setY(me.getScreenY() - initY);
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

}
