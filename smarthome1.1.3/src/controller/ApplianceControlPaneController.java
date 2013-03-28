package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.ProtocolUtil;
import util.ResouceManager;
import util.UiUtil;
import app.SmartHome;
import beans.ApplianceAction;
import beans.ApplianceControl;
import beans.SubAction;

import communication.CommunicationUtil;

/**
 * 家电控制面板控制器
 * 
 * @author yinger
 * 
 */
public class ApplianceControlPaneController implements Initializable {

	private static ApplianceControlPaneController controller;
	private UiUtil uiUtil;
	private CommunicationUtil communicationUtil;
	private ProtocolUtil protocolUtil;
	private List<ApplianceAction> actions;
	private Stage stage;
	private double initX;
	private double initY;
	@FXML
	private AnchorPane shellPane;
	@FXML
	private ImageView ivPrev;
	@FXML
	private ImageView ivNext;
	@FXML
	private ImageView ivWinmin;
	@FXML
	private ImageView ivWinclose;

	@FXML
	private AnchorPane topPane;
	@FXML
	private AnchorPane centerPane;
	@FXML
	private TilePane tilePane;

	private final int COLS = 2;
	private final int ROWS = 5;

	private int PAGESIZE = COLS * ROWS;
	private int itemSum;
	private int totalPage;
	private int pageIndex;

	public static ApplianceControlPaneController getInstance() {
		return controller;
	}

	public void initialize(URL location, ResourceBundle resources) {
		initEvents();
		initValues();
	}

	/**
	 * 事件处理
	 */
	private void initEvents() {
		// when mouse button is pressed, save the initial position of screen
		shellPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = me.getScreenX() - stage.getX();
				initY = me.getScreenY() - stage.getY();
				stage.toFront();
			}
		});

		// when screen is dragged, translate it accordingly
		shellPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setX(me.getScreenX() - initX);
				stage.setY(me.getScreenY() - initY);
			}
		});

		ivPrev.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (ivPrev.getOpacity() == 0) {// 要加上这个判断，因为如果看不见的话，这个imageview是不生效的
					arg0.consume();
				} else {
					pageIndex--;
					refreshPage();
//					new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(tilePane.translateXProperty(), 40)),
//							new KeyFrame(Duration.seconds(0.5), new KeyValue(tilePane.translateXProperty(), 20)),
//							new KeyFrame(Duration.seconds(1), new KeyValue(tilePane.translateXProperty(), 0))).play();
				}
			}
		});

		ivNext.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (ivNext.getOpacity() == 0) {
					arg0.consume();
				} else {
					pageIndex++;
					refreshPage();
//					new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(tilePane.translateXProperty(), -40)),
//							new KeyFrame(Duration.seconds(0.5), new KeyValue(tilePane.translateXProperty(), -20)),
//							new KeyFrame(Duration.seconds(1), new KeyValue(tilePane.translateXProperty(), 0))).play();
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

	/**
	 * 初始化数据
	 */
	private void initValues() {
		controller = this;
		this.stage = SmartHome.getModelStage();
		uiUtil = UiUtil.getInstance();
		protocolUtil = ProtocolUtil.getInstance();
		communicationUtil = CommunicationUtil.getInstance();
		ivPrev.setOpacity(0);
		ivNext.setOpacity(0);
		totalPage = 1;
		pageIndex = 1;
		tilePane.setPrefTileWidth(140);
		tilePane.setPrefTileHeight(40);
		tilePane.setPrefColumns(COLS);
		tilePane.setHgap(4);
		tilePane.setVgap(16);
		actions = new ArrayList<ApplianceAction>();
		refreshPage();
	}

	/**
	 * 刷新界面
	 */
	public void refreshPage() {
		itemSum = actions.size();
		if (itemSum < 1) {// 没有action就返回
			return;
		}
		if (itemSum % PAGESIZE == 0) {
			totalPage = itemSum / PAGESIZE;
		} else {
			totalPage = itemSum / PAGESIZE + 1;
		}
		if (totalPage > pageIndex) {// 调整左右方向按钮的显示状况
			ivNext.setOpacity(1);
		} else {
			ivNext.setOpacity(0);
		}
		if (pageIndex > 1) {
			ivPrev.setOpacity(1);
		} else {
			ivPrev.setOpacity(0);
		}
		tilePane.getChildren().clear();// 首先清空
		int start = (pageIndex - 1) * PAGESIZE;// 从0开始的
		int end = (itemSum - 1) <= (pageIndex * PAGESIZE - 1) ? (itemSum - 1) : (pageIndex * PAGESIZE - 1);
		ApplianceActionEventHandler handler = new ApplianceActionEventHandler();
		for (int i = start; i <= end; i++) {// 加入到tilepane
			final Button button = new Button();
			final ApplianceAction action = actions.get(i);
			button.setPrefSize(120, 30);
			button.getStyleClass().add(ResouceManager.ITEM_BTN);
			button.setText(action.getName());// action name
			action.reset();// 重置action中的index
			// 对于递增递减响应和循环响应，按钮的显示文本是需要根据subaction进行改变的
			action.btntextProperty().addListener(new ChangeListener<String>() {// 一旦btntext发生改变，button的text就跟着发生改变
						public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
							button.setText(action.getName() + ":" + action.btntextProperty().get());
						}
					});
			button.setUserData(actions.get(i));
			button.setOnAction(handler);// all buttons share the same handler
			tilePane.getChildren().add(button);
		}
	}

	/**
	 * 设置家电设备
	 */
	public void setApplianceControl(ApplianceControl control) {
		actions = control.getActions();
		refreshPage();
	}

	/**
	 * 发送命令
	 */
	private void sendData(int command) {
		if (command == ProtocolUtil.COMMAND_EMPTY) {
			uiUtil.showSystemExceptionMessage("该设备还未入网！");
			return;
		}
		try {
			communicationUtil.sendData(protocolUtil.packInstructionRequest(command));
		} catch (Exception e) {
			uiUtil.showSystemExceptionMessage("指令发送失败！");
		}
	}

	/**
	 * 用于处理家电命令发送的处理类
	 * 
	 * @author yinger
	 * 
	 */
	class ApplianceActionEventHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Button button = ((Button) event.getSource());
			ApplianceAction action = (ApplianceAction) button.getUserData();// appliance action
			if (action.getStyle() == ApplianceAction.ACTIONTYPE_SINGLE) {
				sendData(action.getCode());// 直接发送信息
			} else {// 其他类型的action比较复杂
				SubAction subAction = action.getNextSubAction();// 首先得到当前需要发送的子命令
				if (subAction != null) {
					sendData(subAction.getCode());
				}
			}
		}
	}

}
