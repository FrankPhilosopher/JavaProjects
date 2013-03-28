package util;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import app.SmartHome;
import controller.MainPageController;

public class UiUtil {

	private static UiUtil instance;

	private UiUtil() {
	}

	public static UiUtil getInstance() {
		if (instance == null) {
			instance = new UiUtil();
		}
		return instance;
	}

	/**
	 * 打开指定fxml文件的窗体，但是这个窗体stage是系统的modelStage
	 */
	public void openDialog(String file) {
		Stage stage = SmartHome.getModelStage();
		Parent parent = null;
		try {
			parent = FXMLLoader.load(SmartHome.class.getResource(file), SmartHome.getResourceBundle());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(parent, Color.TRANSPARENT);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.centerOnScreen();
		stage.show();
		stage.toFront();
	}

	/**
	 * 打开指定fxml文件的窗体，这个窗体是自由的，即stage是新建的
	 */
	public void openFreeDialog(String file) {
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.initOwner(SmartHome.getAppStage());
		Parent parent = null;
		try {
			parent = FXMLLoader.load(SmartHome.class.getResource(file), SmartHome.getResourceBundle());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(parent, Color.TRANSPARENT);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	/**
	 * 显示渐变效果的动画
	 */
	public void playFadeTransition(Node node, double time, double x, double y, int count, boolean autoReverse) {
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(time), node);
		fadeTransition.setFromValue(x);
		fadeTransition.setToValue(y);
		fadeTransition.setCycleCount(count);
		fadeTransition.setAutoReverse(autoReverse);
		fadeTransition.play();
	}

	/**
	 * 显示放大放小的动画
	 */
	public void playScaleTransition(Node node, double time, double x, double y, int count, boolean autoReverse) {
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(time), node);// time and node
		scaleTransition.setToX(x);// x的变化量
		scaleTransition.setToY(y);// y的变化量
		scaleTransition.setCycleCount(count);// Timeline.INDEFINITE表示无限次
		scaleTransition.setAutoReverse(autoReverse);// true false
		scaleTransition.play();
	}

	/**
	 * 显示旋转的动画
	 */
	public void playRotateTransition(Node node, double time, double x, double y, int count, boolean autoReverse) {
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(time), node);
		rotateTransition.setFromAngle(x);
		rotateTransition.setToAngle(y);
		rotateTransition.setCycleCount(count);
		rotateTransition.setAutoReverse(autoReverse);
		rotateTransition.play();
	}

	/**
	 * 指定的label显示指定的信息
	 */
	public void showMessage(final Label lblMessage, final String msg) {
		Platform.runLater(new Runnable() {// 确保这一步是在Fx线程中执行的，因为protocolutil中也调用了这个方法
			public void run() {
				lblMessage.setTextFill(Color.BLUE);
				lblMessage.setText(msg);
				Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(lblMessage
						.opacityProperty(), 1), new KeyValue(lblMessage.translateZProperty(), 10)), new KeyFrame(
						Duration.seconds(3.0), new KeyValue(lblMessage.opacityProperty(), 0), new KeyValue(lblMessage
								.translateZProperty(), 0)));
				tl.play();
			}
		});
	}

	/**
	 * 指定的label显示指定的错误信息
	 */
	public void showErrorMessage(final Label lblMessage, final String msg) {
		Platform.runLater(new Runnable() {// 确保这一步是在Fx线程中执行的，因为protocolutil中也调用了这个方法
			public void run() {
				lblMessage.setTextFill(Color.RED);
				lblMessage.setText(msg);
				Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(lblMessage
						.opacityProperty(), 1), new KeyValue(lblMessage.translateZProperty(), 10)), new KeyFrame(
						Duration.seconds(4.0), new KeyValue(lblMessage.opacityProperty(), 0), new KeyValue(lblMessage
								.translateZProperty(), 0)));
				tl.play();
			}
		});
	}

	/**
	 * 显示系统的异常出错信息
	 */
	public void showSystemExceptionMessage(final String msg) {
		Platform.runLater(new Runnable() {// 确保这一步是在Fx线程中执行的，因为protocolutil中也调用了这个方法
			public void run() {
				if (MainPageController.getInstance() == null) {
					return;
				}
				MainPageController.getInstance().getLabelMessage().setText(msg);
				Node box = MainPageController.getInstance().getWaringBox();
				Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(box.opacityProperty(), 1),
						new KeyValue(box.translateZProperty(), 20)), new KeyFrame(Duration.seconds(6.0), new KeyValue(
						box.opacityProperty(), 0), new KeyValue(box.translateZProperty(), 0)));
				tl.play();
			}
		});
	}
}
