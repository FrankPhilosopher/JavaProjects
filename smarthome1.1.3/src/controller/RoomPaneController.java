package controller;

import item.LightItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tool.LightTool;
import util.ProtocolUtil;
import util.UiUtil;
import beans.Room;

/**
 * 进入某个房间的面板控制器
 * 
 * @author yinger
 * 
 */
public class RoomPaneController implements Initializable {

	private static RoomPaneController controller;
	private UiUtil uiUtil;

	private Room room;

	@FXML
	private ImageView ivPrev;
	@FXML
	private ImageView ivNext;

	@FXML
	private AnchorPane topPane;
	@FXML
	private AnchorPane centerPane;
	@FXML
	private TilePane tilePane;

	@FXML
	private Text roomName;
	@FXML
	private Button btnExitRoom;

	private final int COLS = 5;
	private final int ROWS = 2;

	private int PAGESIZE = COLS * ROWS;
	private int itemSum;
	private int totalPage;
	private int pageIndex;

	private List<LightItem> items;
	private LightTool lightTool;

	public static RoomPaneController getInstance() {
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

		btnExitRoom.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				// 退出该房间，进入灯控界面！
				MainPageController.getInstance().openPane(ProtocolUtil.DEVICETYPE_LIGHT);
				LightPaneController.getInstance().refreshItems();// 要刷新一次！
			}
		});

	}

	/**
	 * 设置房间
	 */
	public void setRoom(Room room) {
		this.room = room;
		roomName.setText(room.getName());
		refreshItems();
	}

	/**
	 * 初始化数据
	 */
	private void initValues() {
		controller = this;
		uiUtil = UiUtil.getInstance();
		lightTool = LightTool.getInstance();
		room = new Room();
		items = new ArrayList<LightItem>();
		roomName.setText("");
		ivPrev.setOpacity(0);
		ivNext.setOpacity(0);
		totalPage = 1;
		tilePane.setPrefTileWidth(LightItem.WIDTH);
		tilePane.setPrefTileHeight(LightItem.HEIGHT);
		tilePane.setPrefColumns(COLS);
		tilePane.setHgap(20);
		tilePane.setVgap(50);
	}

	/**
	 * 刷新设备列表
	 */
	public void refreshItems() {
		pageIndex = 1;// 回到第一页
		items = room.getLightItems();
		itemSum = items.size();
		if (itemSum % PAGESIZE == 0) {
			totalPage = itemSum / PAGESIZE;
		} else {
			totalPage = itemSum / PAGESIZE + 1;
		}
		refreshPage();
	}

	/**
	 * 刷新界面
	 */
	public void refreshPage() {
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

		for (int i = start; i <= end; i++) {// 加入到tilepane
			tilePane.getChildren().add(((LightItem) items.get(i)).getNode());// 后面是light
		}
	}

}
