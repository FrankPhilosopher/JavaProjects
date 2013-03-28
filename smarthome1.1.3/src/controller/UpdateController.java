package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tool.UpdateTool;
import util.UiUtil;
import app.SmartHome;

/**
 * 更新窗体控制器 <br/>
 * 
 * @author yinger
 * 
 */
public class UpdateController implements Initializable {

	private double initX;
	private double initY;
	private Stage stage;

	private static final String WIN_ID = "Windows";

	private UiUtil uiUtil;
	private UpdateTool updateTool;

	@FXML
	private Label lblMessage;
	@FXML
	private Label lblEdition;
	@FXML
	private Label lblUrl;
	@FXML
	private TextArea taDiscription;

	@FXML
	private AnchorPane updatePane;
	@FXML
	private ImageView ivWinmin;
	@FXML
	private ImageView ivWinclose;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initEvents();
		initValues();
	}

	/**
	 * 初始化数据
	 */
	private void initValues() {
		stage = SmartHome.getModelStage();
		uiUtil = UiUtil.getInstance();
		updateTool = UpdateTool.getInstance();

		lblEdition.setText(updateTool.getVersionCode());
		lblUrl.setText(updateTool.getUrl());
		taDiscription.setText(updateTool.getDescription());
		taDiscription.setEditable(false);//
	}

	/**
	 * 事件处理
	 */
	private void initEvents() {
		// when mouse button is pressed, save the initial position of screen
		updatePane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = me.getScreenX() - stage.getX();
				initY = me.getScreenY() - stage.getY();
				stage.toFront();
			}
		});

		// when screen is dragged, translate it accordingly
		updatePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setX(me.getScreenX() - initX);
				stage.setY(me.getScreenY() - initY);
			}
		});

		lblUrl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				String os = System.getProperty("os.name");
				if (os != null && os.startsWith(WIN_ID)) {
					String cmd = "rundll32 url.dll,FileProtocolHandler " + updateTool.getUrl();
					try {
						Runtime.getRuntime().exec(cmd);
					} catch (IOException e) {

					}
				}
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
