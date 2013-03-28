package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tool.SystemTool;
import app.SmartHome;

public class ConfigController implements Initializable {

	private double initX;
	private double initY;
	private Stage stage;// config stage
	private SystemTool systemTool;

	@FXML
	private TextField tfGatewayIP;
	@FXML
	private TextField tfGatewayPort;
	@FXML
	private TextField tfGatewayDownPort;
	@FXML
	private TextField tfServerIP;
	@FXML
	private TextField tfServerPort;
	@FXML
	private TextField tfServerDownPort;

	@FXML
	private AnchorPane configAnchorPane;

	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancle;

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
		systemTool = SystemTool.getInstance();
		// configAnchorPane.setClip(RectangleBuilder.create().width(configAnchorPane.getWidth())
		// .height(configAnchorPane.getHeight()).arcHeight(0).arcWidth(0).build());
		tfGatewayIP.setText(systemTool.getGateway_ip());
		tfGatewayPort.setText(systemTool.getGateway_port() + "");
		tfGatewayDownPort.setText(systemTool.getGateway_downport() + "");
		tfServerIP.setText(systemTool.getServer_ip());
		tfServerPort.setText(systemTool.getServer_port() + "");
		tfServerDownPort.setText(systemTool.getServer_downport() + "");
	}

	/**
	 * 处理事件
	 */
	private void initEvents() {
		// when mouse button is pressed, save the initial position of screen
		configAnchorPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = me.getScreenX() - stage.getX();
				initY = me.getScreenY() - stage.getY();
				stage.toFront();
			}
		});

		// when screen is dragged, translate it accordingly
		configAnchorPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setX(me.getScreenX() - initX);
				stage.setY(me.getScreenY() - initY);
			}
		});

		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				handleConfigOk();
			}
		});

		btnCancle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
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
	 * 配置完成
	 */
	private void handleConfigOk() {
		// TODO: check input
		systemTool.setGateway_ip(tfGatewayIP.getText().trim());
		systemTool.setGateway_port(Integer.valueOf(tfGatewayPort.getText().trim()));
		systemTool.setGateway_downport(Integer.valueOf(tfGatewayDownPort.getText().trim()));
		systemTool.setServer_ip(tfServerIP.getText().trim());
		systemTool.setServer_port(Integer.valueOf(tfServerPort.getText().trim()));
		systemTool.setServer_downport(Integer.valueOf(tfServerDownPort.getText().trim()));
		//systemTool.setCachedip(tfGatewayIP.getText().trim());// 缓存的是网关ip地址！
		try {
			systemTool.newSystemXml();// 保存配置，只是重写写入文件！
		} catch (Exception e) {
			e.printStackTrace();
		}
		stage.close();
	}

}
