package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import app.SmartHome;

public class ResetConfirmController implements Initializable {

	private static ResetConfirmController controller;

	private double initX;
	private double initY;
	private Stage stage;// login stage

	public StringBuffer confirmResult = new StringBuffer("");

	@FXML
	private AnchorPane resetPane;
	@FXML
	private ImageView ivWinmin;
	@FXML
	private ImageView ivWinclose;

	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancle;

	public static ResetConfirmController getInstance() {
		return controller;
	}

	public void initialize(URL location, ResourceBundle resources) {
		initEvents();
		initValues();
	}

	private void initEvents() {
		// when mouse button is pressed, save the initial position of screen
		resetPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = me.getScreenX() - stage.getX();
				initY = me.getScreenY() - stage.getY();
				stage.toFront();
			}
		});

		// when screen is dragged, translate it accordingly
		resetPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
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
				returnNo();
				stage.close();
			}
		});

		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				returnYes();
				stage.close();
			}
		});

		btnCancle.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				returnNo();
				stage.close();
			}
		});
	}

	protected void returnYes() {
		synchronized (confirmResult) {
			confirmResult.append("YES");
			confirmResult.notify();
		}
	}

	protected void returnNo() {
		synchronized (confirmResult) {
			confirmResult.append("NO");
			confirmResult.notify();
		}
	}

	private void initValues() {
		controller = this;
		stage = SmartHome.getModelStage();
		confirmResult = new StringBuffer("");
	}

}
