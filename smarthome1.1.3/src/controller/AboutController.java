package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tool.SystemTool;
import app.SmartHome;

/**
 * 关于窗体控制器 <br/>
 * 
 * @author yinger
 * 
 */
public class AboutController implements Initializable {

	private double initX;
	private double initY;
	private Stage stage;

	private static final String WIN_ID = "Windows";
	private static final String HOMESITE = "http://www.iever.cn/";

	@FXML
	private Label lblEdition;
	@FXML
	private Label lblUrl;
	@FXML
	private Label lblContact;

	@FXML
	private AnchorPane aboutPane;
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
		lblEdition.setText(SystemTool.getInstance().getVersion());
//		lblUrl.setText("www.iever.cn");//去年这两句,因为在fxml文件中已经配置了
//		lblContact.setText("0731-89922669");
	}

	/**
	 * 事件处理
	 */
	private void initEvents() {
		// when mouse button is pressed, save the initial position of screen
		aboutPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = me.getScreenX() - stage.getX();
				initY = me.getScreenY() - stage.getY();
				stage.toFront();
			}
		});

		// when screen is dragged, translate it accordingly
		aboutPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setX(me.getScreenX() - initX);
				stage.setY(me.getScreenY() - initY);
			}
		});

		lblUrl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				String os = System.getProperty("os.name");
				if (os != null && os.startsWith(WIN_ID)) {
					String cmd = "rundll32 url.dll,FileProtocolHandler " + HOMESITE;
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
