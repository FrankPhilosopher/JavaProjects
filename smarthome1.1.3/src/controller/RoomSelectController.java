package controller;

import item.RoomSelectItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import tool.LightTool;
import util.UiUtil;
import app.SmartHome;
import beans.LightControl;
import beans.Room;

public class RoomSelectController implements Initializable {

	private static RoomSelectController instance;
	private UiUtil uiUtil;
	private LightTool tool;
	private List<RoomSelectItem> items;
	private RoomSelectItem selectedItem;
	private LightControl control;

	private double initX;
	private double initY;
	private Stage stage;// login stage

	@FXML
	private AnchorPane picPane;

	@FXML
	private TilePane picTilePane;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancle;
	@FXML
	private ImageView ivWinmin;
	@FXML
	private ImageView ivWinclose;

	public static RoomSelectController getInstance() {
		return instance;
	}

	public void initialize(URL location, ResourceBundle resources) {
		initValues();
		initEvents();
	}

	private void initEvents() {
		// when mouse button is pressed, save the initial position of screen
		picPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = me.getScreenX() - stage.getX();
				initY = me.getScreenY() - stage.getY();
				stage.toFront();
			}
		});

		// when screen is dragged, translate it accordingly
		picPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				stage.setX(me.getScreenX() - initX);
				stage.setY(me.getScreenY() - initY);
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
				if (selectedItem == null) {
					return;
				}
				control.setRoomid(selectedItem.getRoom().getId());
				try {
					tool.enterRoom(control);
				} catch (Exception e) {
					uiUtil.showSystemExceptionMessage("加入房间失败！");
				}
				LightPaneController.getInstance().refreshItems();// 刷新界面
				stage.close();
			}
		});
	}

	private void initValues() {
		instance = this;
		stage = SmartHome.getModelStage();
		tool = LightTool.getInstance();
		uiUtil = UiUtil.getInstance();
		picTilePane.setPrefColumns(4);
		picTilePane.setHgap(8);
		items = new ArrayList<RoomSelectItem>();
		initItems();
	}

	/**
	 * 加载所有的房间
	 */
	private void initItems() {
		List<Room> rooms = LightPaneController.getInstance().getRooms();
		RoomSelectItem item;
		for (int i = 0; i < rooms.size(); i++) {
			item = new RoomSelectItem(rooms.get(i));
			items.add(item);
			picTilePane.getChildren().add(item.getNode());
		}
		setSelectedItem(items.get(0));// 第一张图片是默认选中的
	}

	/**
	 * 得到items
	 */
	public List<RoomSelectItem> getItems() {
		return items;
	}

	/**
	 * 设置选中的item
	 */
	public void setSelectedItem(RoomSelectItem item) {
		selectedItem = item;
		for (int i = 0; i < items.size(); i++) {
			items.get(i).hideRectangle();
		}
		item.showRectangle();
	}

	public void setLightControl(LightControl control) {
		this.control = control;
	}

}
